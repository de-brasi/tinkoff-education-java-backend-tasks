package edu.project1;

public class Main {
    private Main() {}

    public static void main(String[] args) {
        // CLI version test
        var gameInterface = new CommandLineGameInterface();
        gameInterface.startGame();

        // ASCII Interface version test
        gameInterface = new AsciiInterface();
        gameInterface.startGame();
    }
}
