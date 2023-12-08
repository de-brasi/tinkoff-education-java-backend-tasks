package edu.hw9.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileSystemSearcher {
    private FileSystemSearcher() {}

    public static List<Path> findDirectoriesWithMoreThanFiles(int filesCount, Path root) {
        try (final ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            return forkJoinPool.invoke(new DirectorySearchTask(root, filesCount));
        }
    }

    public static List<Path> findFiles(int filesCount, Predicate<Path> predicate) {
        // TODO:
        return List.of();
    }

    private static class DirectorySearchTask extends RecursiveTask<List<Path>> {
        DirectorySearchTask(Path root, int count) {
            this.root = root;
            this.filesCountCondition = count;
        }

        @Override
        protected List<Path> compute() {
            try {
                if (root.toFile().isFile()) {
                    return null;
                }

                try (var children = Files.list(root)) {
                    if (children.findAny().isEmpty()) {
                        return null;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
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

                if (!subTasks.isEmpty()) {
                    var pivotTask = subTasks.get(0);
                    pivotTask.fork();

                    var otherTaskResults = subTasks
                        .stream()
                        .skip(1)
                        .map(DirectorySearchTask::compute)
                        .toList();

                    var pivotResult = pivotTask.join();

                    if (pivotResult != null) {
                        res.addAll(pivotResult);
                    }

                    for (var otherRes: otherTaskResults) {
                        if (otherRes != null) {
                            res.addAll(otherRes);
                        }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return res;
        }

        final Path root;
        final int filesCountCondition;
    }
}
