package edu.project2.generators;

import edu.project2.util.Cell;
import edu.project2.util.Coordinate;
import edu.project2.util.Direction;
import edu.project2.util.Generator;
import edu.project2.util.Maze;
import java.util.Random;
import java.util.Stack;

public class RandomizedDFSGenerator implements Generator {
    public RandomizedDFSGenerator() {}

    @Override
    public Maze generate(int width, int height) {
        if (height < 0 || width < 0) {
            throw new IllegalArgumentException(
                "Height and width must be more or equal zero. "
                    + "Got height=" + height + " width=" + width
            );
        }

        return generateStaticMethod(width, height);
    }

    private static Maze generateStaticMethod(int width, int height) {
        var maze = new Maze(width, height);

        if (width == 1 && height == 1) {
            return maze;
        }

        var backTrack = new Stack<Coordinate>();

        var start = new Coordinate(
            0 /* todo: random near border */,
            0 /* todo: random near border */
        );
        maze.getCell(start).visit();
        backTrack.push(start);

        Coordinate curCoordinate;
        Direction chosenDirection;
        Coordinate newCoordinate;
        Cell newCell;
        Direction[] unvisitedNeighboursDirection;
        Cell curCell;
        var random = new Random();
        while (!backTrack.isEmpty()) {
            curCoordinate = backTrack.pop();
            curCell = maze.getCell(curCoordinate);

            unvisitedNeighboursDirection = maze.getNotVisitedNeighboursDirection(curCoordinate);
            if (unvisitedNeighboursDirection.length != 0) {
                backTrack.push(curCoordinate);

                chosenDirection = unvisitedNeighboursDirection[
                    random.nextInt(unvisitedNeighboursDirection.length)
                    ];

                curCell.destroyWall(chosenDirection);

                newCoordinate = maze.getCoordinate(curCoordinate, chosenDirection);
                newCell = maze.getCell(newCoordinate);

                newCell.destroyWall(chosenDirection.getOppositeDirection());
                newCell.visit();

                backTrack.push(newCoordinate);
            }
        }

        setAllCellsNotVisited(maze);

        return maze;
    }

    private static void setAllCellsNotVisited(Maze toSetMaze) {
        toSetMaze.resetCells();
    }
}
