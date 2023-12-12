package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class CustomInvocationHandler implements InvocationHandler {
    private final Object target;

    public CustomInvocationHandler(Object target) {
        this.target = target;
        this.filePathForSave = DEFAULT_PATH;
    }

    public CustomInvocationHandler(Object target, Path pathFileForSave) {
        this.target = target;
        this.filePathForSave = pathFileForSave;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class) && method.getAnnotation(Cache.class).persist()) {
            System.out.println("Save to file result of method: " + method.getName());
        }

        System.out.println("Before invoking independent custom method: " + method.getName());

        var newCallInfo = new MethodCallInfo(method, args);
        Object result;
        if (methodsCallCache.containsKey(newCallInfo)) {
            result = methodsCallCache.get(newCallInfo);
        } else {
            result = method.invoke(target, args);
            methodsCallCache.put(newCallInfo, result);

            saveData();
        }

        System.out.println("After invoking independent custom method: " + method.getName());

        return result;
    }

    private void saveData() {
        try {
            if (Files.notExists(this.filePathForSave)) {
                Files.createFile(this.filePathForSave);
            }

            String json = convertCacheToJson();
            Files.writeString(this.filePathForSave, json, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String convertCacheToJson() {
        StringBuilder jsonBuilder = new StringBuilder("[\n");

        methodsCallCache.forEach((methodCallInfo, value) -> {
            String method = methodCallInfo.method().getName();
            Object[] args = methodCallInfo.args();
            String argsJson = convertArgsToJson(args);

            jsonBuilder.append(String.format("  {\"method\": \"%s\", \"args\": %s},\n", method, argsJson));
        });

        if (!methodsCallCache.isEmpty()) {
            jsonBuilder.delete(jsonBuilder.length() - 2, jsonBuilder.length());
        }

        jsonBuilder.append("]\n");
        return jsonBuilder.toString();
    }

    private static String convertArgsToJson(Object[] args) {
        if (args == null) {
            return "[]";
        }

        StringBuilder argsJsonBuilder = new StringBuilder("[");

        for (Object arg : args) {
            argsJsonBuilder.append(String.format("\"%s\",", arg.toString()));
        }

        if (args.length > 0) {
            argsJsonBuilder.delete(argsJsonBuilder.length() - 1, argsJsonBuilder.length());
        }

        argsJsonBuilder.append("]");
        return argsJsonBuilder.toString();
    }

    private final Map<MethodCallInfo, Object> methodsCallCache = new HashMap<>();
    private final Path filePathForSave;
    private final Path DEFAULT_PATH = Paths.get(System.getProperty("user.home"), "saving.json");

    private static record MethodCallInfo(Method method, Object[] args) {}
}
