package edu.hw2;

public class Task4 {
    private Task4() {}

    public static CallingInfo whoCallTheFunction() {
        var stackTrace = new Throwable().getStackTrace();
        var caller = stackTrace[1];
        return new CallingInfo(caller.getClassName(), caller.getMethodName());
    }
}
