package edu.project2;

import edu.project2.generators.RandomizedDFSGenerator;
import edu.project2.prettyprinter.PrettyPrinter;
import edu.project2.util.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final var generator = new RandomizedDFSGenerator();
        final var maze = generator.generate(5, 5);
        final var emptyCoordinates = new ArrayList<Coordinate>();
        PrettyPrinter.Print(maze, emptyCoordinates);
    }
}
