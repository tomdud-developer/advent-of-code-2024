import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Day12Java {

    private final static List<Pair<Integer, Integer>> directionsToSearch = List.of(new Pair<>(0, 1),new Pair<>(-1, 0), new Pair<>(1, 0), new Pair<>(0, -1));

    public Long part1(List<String> input) {
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        char[][] map = new char[maxY + 1][maxX + 1];
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                map[y][x] = input.get(y).charAt(x);
            }
        }

        List<Region> regions = new ArrayList<>();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                boolean attached = attachPointToRegions(regions, new Pair<>(x, y), map[y][x]);
                if (!attached) {
                    List<Pair<Integer, Integer>> list = new ArrayList<>();
                    list.add(new Pair<>(x, y));
                    regions.add(new Region(map[y][x], list));
                }
            }
        }

        long price = 0L;
        for (Region region : regions) {
            char regionType = region.type;
            int regionArea = region.getArea();
            int regionPerimeter = region.getPerimeter();
            System.out.println("Region type: " + regionType + " Area: " + regionArea + " Perimeter: " + regionPerimeter);
            price += (long) regionArea * regionPerimeter;
        }

        return price;
    }

    private record Region (char type, List<Pair<Integer, Integer>> points) {
        boolean canBeAttached(char type, Pair<Integer, Integer> point) {
            if (this.type != type) {
                return false;
            }
            for (Pair<Integer, Integer> p : points) {
                int x1 = p.getFirst();
                int y1 = p.getSecond();
                int x2 = point.getFirst();
                int y2 = point.getSecond();

                if(x1+1 == x2 && y1 == y2) {
                    return true;
                }
                if(x1-1 == x2 && y1 == y2) {
                    return true;
                }
                if(x1 == x2 && y1+1 == y2) {
                    return true;
                }
                if(x1 == x2 && y1-1 == y2) {
                    return true;
                }

            }
            return false;
        }

        int getArea() {
            return points.size();
        }

        int getPerimeter() {
            int sum = 0;
            for (Pair<Integer, Integer> point : points) {
                int x = point.getFirst();
                int y = point.getSecond();

                for (Pair<Integer, Integer> direction : directionsToSearch) {
                    int x1 = x + direction.getFirst();
                    int y1 = y + direction.getSecond();
                    if (!points.contains(new Pair<>(x1, y1))) {
                        sum++;
                    }
                }
            }
            return sum;
        }
    }

    public static boolean attachPointToRegions(List<Region> regions, Pair<Integer, Integer> point, char type) {
        List<Region> regionsToMerge = new ArrayList<>();
        for (Region region : regions) {
            if (region.canBeAttached(type, point)) {
                regionsToMerge.add(region);
            }
        }

        if(regionsToMerge.isEmpty()) {
            return false;
        }

        if(regionsToMerge.size() == 1) {
            regionsToMerge.getFirst().points.add(point);
            return true;
        }

        Region newRegion = new Region(type, new ArrayList<>());
        for (Region region : regionsToMerge) {
            newRegion.points.addAll(region.points);
            regions.remove(region);
        }
        newRegion.points.add(point);
        regions.add(newRegion);
        return true;
    }
}
