import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Java {

    public static final int BOARD_SIZE_X = 101;
    public static final int BOARD_SIZE_Y = 103;

    public long part12(List<String> input, boolean isPart2) {
        List<Robot> robots = new ArrayList<>();
        Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");

        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                robots.add(new Robot(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4))
                ));
            }
        }
        assert robots.size() == input.size();

        if(!isPart2) {
            for(int seconds = 1; seconds <= 100; seconds++) {
                for (Robot robot : robots) {
                    robot.move();
                }
            }
            int xMiddleIndex = (BOARD_SIZE_X -1 ) / 2;
            int yMiddleIndex = (BOARD_SIZE_Y -1 ) / 2;

            long sumInFirstQuadrant = 0;
            long sumInSecondQuadrant = 0;
            long sumInThirdQuadrant = 0;
            long sumInFourthQuadrant = 0;

            for (Robot robot : robots) {
                if (robot.x < xMiddleIndex && robot.y < yMiddleIndex) {
                    sumInFirstQuadrant++;
                } else if (robot.x > xMiddleIndex && robot.y < yMiddleIndex) {
                    sumInSecondQuadrant++;
                } else if (robot.x < xMiddleIndex && robot.y > yMiddleIndex) {
                    sumInThirdQuadrant++;
                } else if (robot.x > xMiddleIndex && robot.y > yMiddleIndex) {
                    sumInFourthQuadrant++;
                }
            }

            return sumInFirstQuadrant * sumInSecondQuadrant * sumInThirdQuadrant * sumInFourthQuadrant;
        }


        // PART2
        Pattern inLinePattern = Pattern.compile("XXXXXX");
        int bestScore = 0;
        int bestSecond = 0;
        for(int seconds = 1; seconds <= 500000; seconds++) {
            System.out.println("Second: " + seconds);
            for (Robot robot : robots) {
                robot.move();
            }

            char[][] board = new char[BOARD_SIZE_Y][BOARD_SIZE_X];
            for (int y = 0; y < BOARD_SIZE_Y; y++) {
                Arrays.fill(board[y], '.');
            }
            for (Robot robot : robots) {
                 board[robot.y][robot.x] = 'X';
            }

            int score = 0;
            for (int y = 0; y < BOARD_SIZE_Y; y++) {
                String line = new String(board[y]);
                Matcher matcher = inLinePattern.matcher(line);
                if (matcher.find()) {
                    score++;
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestSecond = seconds;
            }


        }
        System.out.println("Best score: " + bestScore + " at second: " + bestSecond);
        return bestSecond;
    }

    private static class Robot {
        int x;
        int y;
        int vx;
        int vy;

        public Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public void move() {
            x += vx;
            y += vy;

            if (x < 0) {
                x = BOARD_SIZE_X + x;
            } else if (x >= BOARD_SIZE_X) {
                x = x - BOARD_SIZE_X;
            }
            if (y < 0) {
                y = BOARD_SIZE_Y + y;
            } else if (y >= BOARD_SIZE_Y) {
                y = y - BOARD_SIZE_Y;
            }
        }
    }
}
