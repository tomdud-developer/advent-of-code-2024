import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Day15Java {

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point getPointAtLeft() {
            return new Point(x - 1, y);
        }

        public Point getPointAtRight() {
            return new Point(x + 1, y);
        }
    }

    private static class Vector {
        int dx;
        int dy;

        public Vector(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public boolean isVertical() {
            return dx == 0;
        }
    }

    public long part1(List<String> input, char[] moves) {
        char[][] map = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            map[i] = input.get(i).toCharArray();
        }
        printMap(map);


        for (char move : moves) {
            Point position = findCurrentPosition(map);
            System.out.println("Move: " + move);
            Vector vector = translateMove(move);
            makeMoveRecursive(map, position, vector);
            //printMap(map);
        }

        long sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'O') {
                    sum += (100L * i + j);
                }
            }
        }

        return sum;
    }

    public long part2(List<String> inputOrg, char[] moves) {
        char[][] map = new char[inputOrg.size()][inputOrg.get(0).length()*2];
        for(int i = 0; i < inputOrg.size(); i++) {
            String line = inputOrg.get(i);
            for(int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                map[i][j*2] = c == 'O' ? '[' : c;
                map[i][j*2+1] = c == '@' ? '.' : c == 'O' ? ']' : c;
            }
        }

        int progress = 0;
        for (char move : moves) {
            Point position = findCurrentPosition(map);
            System.out.println("Move: " + move);
            Vector vector = translateMove(move);
            if (!vector.isVertical())
                makeMoveRecursiveHorizontally(map, position, vector);
            else {
                boolean isPossibleToMove = makeMoveRecursiveVerticallyByRobot(map, position, vector, false);
                if (isPossibleToMove) {
                    makeMoveRecursiveVerticallyByRobot(map, position, vector, true);
                }
            }


            //printMap(map);
            progress++;
            System.out.println("Progress: " + (double)progress/moves.length * 100.0 + "%");
        }

        printMap(map);


        long sum = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '[') {
                    sum += (100L * y + x);
                }
            }
        }
        return sum;
    }

    private void makeMove2(char[][] map, Point position, Vector vector, boolean shouldMove) {
        if (!shouldMove) {
            return;
        }

        char object = map[position.y][position.x];
        map[position.y + vector.dy][position.x + vector.dx] = object;
        map[position.y][position.x] = '.';
    }

    /*
    shouldMove - if false it will only check if it is possible to move
     */
    private boolean makeMoveRecursiveVerticallyByBox(char[][] map, Point position, Vector vector, boolean shouldMove) {
        Point nextPositionLeft = new Point(position.x + vector.dx, position.y + vector.dy);
        Point nextPositionRight = nextPositionLeft.getPointAtRight();
        char symbolAtNextPositionLeft = map[nextPositionLeft.y][nextPositionLeft.x];
        char symbolAtNextPositionRight = map[nextPositionRight.y][nextPositionRight.x];
        if (symbolAtNextPositionLeft == '#' || symbolAtNextPositionRight == '#') {
            return false;
        }
        if (symbolAtNextPositionLeft == '.' && symbolAtNextPositionRight == '.') {
            makeMove2(map, position, vector, shouldMove);
            makeMove2(map, position.getPointAtRight(), vector, shouldMove);
            return true;
        }
        if (symbolAtNextPositionLeft == ']' && symbolAtNextPositionRight == '[') {
            boolean isMovedLeft = makeMoveRecursiveVerticallyByBox(map, nextPositionLeft.getPointAtLeft(), vector, shouldMove);
            boolean isMovedRight = makeMoveRecursiveVerticallyByBox(map, nextPositionRight, vector,shouldMove );
            if (isMovedLeft && isMovedRight) {
                makeMove2(map, position, vector, shouldMove);
                makeMove2(map, position.getPointAtRight(), vector, shouldMove);
                return true;
            } else {
                return false;
            }
        }
        if (symbolAtNextPositionLeft == '[') {
            boolean isMoved = makeMoveRecursiveVerticallyByBox(map, nextPositionLeft, vector, shouldMove);
            if (isMoved) {
                makeMove2(map, position, vector, shouldMove);
                makeMove2(map, position.getPointAtRight(), vector, shouldMove);
                return true;
            } else {
                return false;
            }
        }
        if (symbolAtNextPositionLeft == ']') {
            boolean isMoved = makeMoveRecursiveVerticallyByBox(map, nextPositionLeft.getPointAtLeft(), vector, shouldMove);
            if (isMoved) {
                makeMove2(map, position, vector, shouldMove);
                makeMove2(map, position.getPointAtRight(), vector, shouldMove);
                return true;
            } else {
                return false;
            }
        }
        if (symbolAtNextPositionRight == '[') {
            boolean isMoved = makeMoveRecursiveVerticallyByBox(map, nextPositionRight, vector, shouldMove);
            if (isMoved) {
                makeMove2(map, position, vector, shouldMove);
                makeMove2(map, position.getPointAtRight(), vector, shouldMove);
                return true;
            } else {
                return false;
            }
        }
        if (symbolAtNextPositionRight == ']') {
            boolean isMoved = makeMoveRecursiveVerticallyByBox(map, nextPositionRight.getPointAtLeft(), vector, shouldMove);
            if (isMoved) {
                makeMove2(map, position, vector, shouldMove);
                makeMove2(map, position.getPointAtRight(), vector, shouldMove);
                return true;
            } else {
                return false;
            }
        }

        throw new IllegalArgumentException("Invalid object: " + map[nextPositionLeft.y][nextPositionLeft.x]);
    }

    private boolean makeMoveRecursiveVerticallyByRobot(char[][] map, Point position, Vector vector, boolean shouldMove) {
        Point nextPosition = new Point(position.x + vector.dx, position.y + vector.dy);
        char symbolAtNextPosition = map[nextPosition.y][nextPosition.x];
        if (symbolAtNextPosition == '#') {
            return false;
        }
        if (symbolAtNextPosition == '.') {
            makeMove2(map, position, vector, shouldMove);
            return true;
        }
        if (symbolAtNextPosition == '[') {
            boolean isMoved = makeMoveRecursiveVerticallyByBox(map, nextPosition, vector, shouldMove);
            if (isMoved) {
                makeMove2(map, position, vector, shouldMove);
                return true;
            } else {
                return false;
            }
        }
        if (symbolAtNextPosition == ']') {
            boolean isMoved = makeMoveRecursiveVerticallyByBox(map, nextPosition.getPointAtLeft(), vector, shouldMove);
            if (isMoved) {
                makeMove2(map, position, vector, shouldMove);
                return true;
            } else {
                return false;
            }
        }

        throw new IllegalArgumentException("Invalid object: " + map[nextPosition.y][nextPosition.x]);
    }

    private boolean makeMoveRecursiveHorizontally(char[][] map, Point position, Vector vector) {
        Point nextPosition = new Point(position.x + vector.dx, position.y + vector.dy);
        char symbolAtNextPosition = map[nextPosition.y][nextPosition.x];
        if (symbolAtNextPosition == '#') {
            return false;
        }
        if (symbolAtNextPosition == '.') {
            makeMove2(map, position, vector, true);
            return true;
        }
        if (symbolAtNextPosition == '[' || symbolAtNextPosition == ']') {
            boolean isMoved = makeMoveRecursiveHorizontally(map, nextPosition, vector);
            if (isMoved) {
                makeMove2(map, position, vector, true);
                return true;
            } else {
                return false;
            }
        }

        throw new IllegalArgumentException("Invalid object: " + map[nextPosition.y][nextPosition.x]);
    }



    private Point findCurrentPosition(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '@') {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    private Vector translateMove(char move) {
        return switch (move) {
            case '^' -> new Vector(0, -1);
            case 'v' -> new Vector(0, 1);
            case '<' -> new Vector(-1, 0);
            case '>' -> new Vector(1, 0);
            default -> throw new IllegalArgumentException("Invalid move: " + move);
        };
    }

    private void makeMove(char[][] map, Point position, Vector vector) {
        char object = map[position.y][position.x];
        map[position.y + vector.dy][position.x + vector.dx] = object;
        map[position.y][position.x] = '.';
    }

    private boolean makeMoveRecursive(char[][] map, Point position, Vector vector) {
        Point nextPosition = new Point(position.x + vector.dx, position.y + vector.dy);
        if (map[nextPosition.y][nextPosition.x] == '#') {
            return false;
        }
        if (map[nextPosition.y][nextPosition.x] == '.') {
            makeMove(map, position, vector);
            return true;
        }
        if (map[nextPosition.y][nextPosition.x] == 'O') {
            boolean isMoved = makeMoveRecursive(map, nextPosition, vector);
            if (isMoved) {
                makeMove(map, position, vector);
                return true;
            } else {
                return false;
            }
        }

        throw new IllegalArgumentException("Invalid object: " + map[nextPosition.y][nextPosition.x]);
    }

    private void printMap (char[][] map) {
        System.out.println("Map:");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
