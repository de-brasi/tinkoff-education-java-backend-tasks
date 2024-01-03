package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class FileSystemSearcher {
    private FileSystemSearcher() {}

    public static List<Path> findDirectoriesWithMoreThanFiles(int filesCount, Path root) {
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            return forkJoinPool.invoke(new DirectorySearchTask(root, filesCount));
        }
    }

    public static List<Path> findFiles(Predicate<Path> predicate, Path root) {
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            return forkJoinPool.invoke(new FileSearchTask(root, predicate));
        }
    }

    private static class DirectorySearchTask extends RecursiveTask<List<Path>> {
        DirectorySearchTask(Path root, int count) {
            this.root = root;
            this.filesCountCondition = count;
        }

        @Override
        protected List<Path> compute() {
            if (testPathContainsFileOrEmptyDir(root)) {
                return null;
            }

            var res = new ArrayList<Path>();

            try (var children = Files.list(root)) {
                var content = children.toList();
                var filesCount = content.stream().filter(path -> path.toFile().isFile()).count();

                if (filesCount >= filesCountCondition) {
                    res.add(root);
                }

                var subTasks = content.stream()
                    .filter(path -> path.toFile().isDirectory())
                    .filter(path -> {
                        try (var subContent = Files.list(path)) {
                            return subContent.findAny().isPresent();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(
                        path -> new DirectorySearchTask(path, filesCountCondition)
                    )
                    .toList();

                var taskResult = executeTasks(subTasks);

                if (taskResult != null) {
                    res.addAll(taskResult);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return res;
        }

        private static boolean testPathContainsFileOrEmptyDir(Path path) {
            try {
                if (path.toFile().isFile()) {
                    return true;
                }

                try (var children = Files.list(path)) {
                    if (children.findAny().isEmpty()) {
                        return true;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return false;
        }

        private static List<Path> executeTasks(List<DirectorySearchTask> tasks) {
            if (tasks.isEmpty()) {
                return null;
            }

            List<Path> collected = new ArrayList<>();

            var pivotTask = tasks.getFirst();
            pivotTask.fork();

            var otherTaskResults = tasks
                .stream()
                .skip(1)
                .map(DirectorySearchTask::compute)
                .toList();

            var pivotResult = pivotTask.join();

            if (pivotResult != null) {
                collected.addAll(pivotResult);
            }

            for (var otherRes : otherTaskResults) {
                if (otherRes != null) {
                    collected.addAll(otherRes);
                }
            }

            return collected;
        }

        final Path root;
        final int filesCountCondition;
    }

    private static class FileSearchTask extends RecursiveTask<List<Path>> {
        FileSearchTask(Path root, Predicate<Path> predicate) {
            this.root = root;
            this.predicate = predicate;
        }

        @Override
        protected List<Path> compute() {
            if (root.toFile().isFile() && predicate.test(root)) {
                return List.of(root);
            } else if (testDirectoryEmpty(root)) {
                return null;
            }

            List<Path> res;
            try (var children = Files.list(root)) {
                var content = children.toList();
                var filesInRootDirectory = content
                    .stream()
                    .filter(path -> path.toFile().isFile())
                    .filter(predicate);
                var subDirectoriesInRootDirectory = content
                    .stream()
                    .filter(
                        path -> path.toFile().isDirectory()
                    )
                    .map(item -> new FileSearchTask(item, predicate))
                    .toList();

                res = new ArrayList<>(filesInRootDirectory.toList());
                var tasksRes = executeTasks(subDirectoriesInRootDirectory);

                if (tasksRes != null) {
                    res.addAll(tasksRes);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return res;
        }

        private static boolean testDirectoryEmpty(Path path) {
            try (var children = Files.list(path)) {
                if (children.findAny().isEmpty()) {
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return false;
        }

        private static List<Path> executeTasks(List<FileSearchTask> tasks) {
            if (tasks.isEmpty()) {
                return null;
            }

            List<Path> collected = new ArrayList<>();

            var pivotTask = tasks.getFirst();
            pivotTask.fork();

            var otherTaskResults = tasks
                .stream()
                .skip(1)
                .map(FileSearchTask::compute)
                .toList();

            var pivotResult = pivotTask.join();

            if (pivotResult != null) {
                collected.addAll(pivotResult);
            }

            for (var otherRes : otherTaskResults) {
                if (otherRes != null) {
                    collected.addAll(otherRes);
                }
            }

            return collected;
        }

        private final Path root;
        private final Predicate<Path> predicate;
    }
}
