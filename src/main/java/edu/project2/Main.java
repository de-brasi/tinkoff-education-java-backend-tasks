package edu.project2;

import edu.project2.generators.RandomizedDFSGenerator;
import edu.project2.prettyprinter.PrettyPrinter;
import edu.project2.util.Coordinate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final var generator = new RandomizedDFSGenerator();
        final var printer = new PrettyPrinter();

        final var maze = generator.generate(20, 5);
        final var emptyCoordinates = new ArrayList<Coordinate>();
        printer.Print(maze, emptyCoordinates);
    }
}
