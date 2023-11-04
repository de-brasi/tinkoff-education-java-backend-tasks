package edu.project2.util;

import java.util.ArrayList;

public interface Solver {
    ArrayList<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
