import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Day15Java {


    private static final boolean IS_PART_2 = true;

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Vector {
        int dx;
        int dy;

        public Vector(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public long part12(List<String> input, char[] moves, boolean b) {
        char[][] map = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            map[i] = input.get(i).toCharArray();
        }
        printMap(map);


        for (char move : moves) {
            Point position = findCurrentPosition(map);
            System.out.println("Move: " + move);
            Vector vector = translateMove(move);
            Point nextPosition = new Point(position.x + vector.dx, position.y + vector.dy);

            if (map[nextPosition.y][nextPosition.x] == '#') {
                System.out.println("Hit wall");
                continue;
            } else if (map[nextPosition.y][nextPosition.x] == '.') {
                makeMove(map, position, vector);
            } else if (map[nextPosition.y][nextPosition.x] == 'O') {
                boolean isMoved = makeMoveRecursive(map, nextPosition, vector);
                if (isMoved) {
                    makeMove(map, position, vector);
                }
            }

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
