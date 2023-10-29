package edu.hw3;

import java.util.ArrayList;
import java.util.HashMap;

public class Task2 {
    private Task2() {
        // not allowed
    }

    public static String[] clusterize(final String src) {
        final var toClusterizeContent = src.toCharArray();

        var clusterizedContent = new ArrayList<String>();
        var currentCluster = new ArrayList<Character>();

        var openBraceStatistics = new HashMap<Character, Integer>() {{
            put('(', 0);
            put('[', 0);
            put('{', 0);
        }};


        for (char curLetter : toClusterizeContent) {
            currentCluster.add(curLetter);

            if (isOpenBrace(curLetter)) {
                openBraceStatistics.put(
                    curLetter,
                    openBraceStatistics.get(curLetter) + 1
                );
            } else if (isCloseBrace(curLetter)) {
                char relativeBrace = getRelativeBrace(curLetter);
                openBraceStatistics.put(
                    relativeBrace,
                    openBraceStatistics.get(relativeBrace) - 1
                );

                // all open-braces was closed
                if (openBraceStatistics.get('(') == 0
                    && openBraceStatistics.get('[') == 0
                    && openBraceStatistics.get('{') == 0) {
                    // flash cluster

                    StringBuilder builder = new StringBuilder(currentCluster.size());
                    for(Character ch: currentCluster) {
                        builder.append(ch);
                    }

                    clusterizedContent.add(builder.toString());

                    currentCluster.clear();
                }
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

    private static char getRelativeBrace(char brace) {
        if (isOpenBrace(brace)) {
            return switch (brace) {
                case '(' -> ')';
                case '{' -> '}';
                case '[' -> ']';
                default -> throw new IllegalArgumentException("");
            };
        } else {
            return switch (brace) {
                case ')' -> '(';
                case '}' -> '{';
                case ']' -> '[';
                default -> throw new IllegalArgumentException("");
            };
        }
    }
}
