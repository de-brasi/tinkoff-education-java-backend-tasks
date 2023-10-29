package edu.hw3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

public class Task2 {
    private Task2() {
        // not allowed
    }

    public static String[] clusterize(final String src) {
        final var toClusterizeContent = src.toCharArray();
        var clusterizedContent = new ArrayList<String>();

        var currentCluster = new ArrayDeque<Character>();
        var openBraces = new Stack<Character>();

        for (char curLetter : toClusterizeContent) {
            if (isOpenBrace(curLetter)) {
                openBraces.push(curLetter);
            } else if (isCloseBrace(curLetter)) {
                assert bracesHasSameTypes(curLetter, openBraces.peek());
                currentCluster.push(openBraces.pop());
                currentCluster.add(curLetter);

                if (openBraces.isEmpty()) {
                    var cluster = new StringBuilder();

                    for (var letter : currentCluster) {
                        cluster.append(letter);
                    }

                    // current cluster handled
                    clusterizedContent.add(cluster.toString());

                    currentCluster.clear();
                }
            } else {
                currentCluster.add(curLetter);
            }
        }

        String[] result = new String[clusterizedContent.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = clusterizedContent.get(i);
        }

        return result;
    }

    private static boolean isOpenBrace(char toCheck) {
        return (toCheck == '(') || (toCheck == '{') || (toCheck == '[');
    }

    private static boolean isCloseBrace(char toCheck) {
        return (toCheck == ')') || (toCheck == '}') || (toCheck == ']');
    }

    private static boolean bracesHasSameTypes(char lhs, char rhs) {
        assert (isOpenBrace(lhs) || isCloseBrace(lhs)) && (isOpenBrace(rhs) || isCloseBrace(rhs));
        if (isOpenBrace(lhs)) {
            return switch (lhs) {
                case '(' -> (rhs == ')');
                case '{' -> (rhs == '}');
                case '[' -> (rhs == ']');
                default -> false;
            };
        } else {
            return switch (lhs) {
                case ')' -> (rhs == '(');
                case '}' -> (rhs == '{');
                case ']' -> (rhs == '[');
                default -> false;
            };
        }
    }
}
