import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10Java {

    private int[][] map;
    private int maxX;
    private int maxY;
    private Map<Integer, List<Pair<Integer, Integer>>> reached = new HashMap<>();

    public long part1(List<String> input) {
        maxY = input.size() - 1;
        maxX = input.getFirst().length() - 1;
        map = new int[maxY + 1][maxX + 1];
        List<Pair<Integer, Integer>> starts = new ArrayList<>();
        int startId = 0;
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                char ch = input.get(y).charAt(x);
                map[y][x] = ch == '.' ? -3 : ch - '0';

                if (ch == '0') {
                    starts.add(new Pair<>(x, y));
                    reached.put(startId, new ArrayList<>());
                    startId++;
                }
            }
        }

        long sum = 0;
        startId = 0;
        for (Pair<Integer, Integer> start : starts) {
            sum += recursiveSearch(start.getFirst(), start.getSecond(), -1, startId);
            startId++;
        }

        return sum;
    }

    public long recursiveSearch(int x, int y, int prevLevel, int startId) {
        if (x < 0 || x > maxX || y < 0 || y > maxY) {
            return 0;
        }
        int currentLevel = map[y][x];
        if (currentLevel != prevLevel+1) {
            return 0;
        }
        if (prevLevel == 8 && currentLevel == 9) {
            if (reached.get(startId).contains(new Pair<>(x, y))) {
                return 0;
            } else {
                reached.get(startId).add(new Pair<>(x, y));
                return 1;
            }
        }
        return recursiveSearch(x+1, y, currentLevel, startId) +
                recursiveSearch(x-1, y, currentLevel, startId) +
                recursiveSearch(x, y+1, currentLevel, startId) +
                recursiveSearch(x, y-1, currentLevel, startId);
    }
}
