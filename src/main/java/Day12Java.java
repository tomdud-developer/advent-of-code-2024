import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Day12Java {

    private final static List<Pair<Integer, Integer>> directionsToSearch = List.of(new Pair<>(0, 1),new Pair<>(-1, 0), new Pair<>(1, 0), new Pair<>(0, -1));
    private final static List<Pair<String,Pair<Integer, Integer>>> directionsToSearchWithDirections = List.of(new Pair<>("UP", new Pair<>(0, 1)),new Pair<>("LEFT", new Pair<>(-1, 0)), new Pair<>("RIGHT", new Pair<>(1, 0)), new Pair<>("DOWN", new Pair<>(0, -1)));

    public Long part12(List<String> input, boolean isPart2) {
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
            int multiplier = isPart2 ? region.getSides() : region.getPerimeter();
            System.out.println("Region type: " + regionType + " Area: " + regionArea + " Perimeter/Sides: " + multiplier);
            price += (long) regionArea * multiplier;
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

        int getSides() {
            List<Bound> bounds = new ArrayList<>();
            for (Pair<Integer, Integer> point : points) {
                int x = point.getFirst();
                int y = point.getSecond();

                for (Pair<String, Pair<Integer, Integer>> direction : directionsToSearchWithDirections) {
                    int x1 = x + direction.getSecond().getFirst();
                    int y1 = y + direction.getSecond().getSecond();
                    if (!points.contains(new Pair<>(x1, y1))) {
                        bounds.add(new Bound(x, y, direction.getFirst()));
                    }
                }
            }

            List<Side> sides = new ArrayList<>();
            for (Bound bound : bounds) {
                boolean found = false;
                for (Side side : sides) {
                    if (side.canBeAttached(bound)) {
                        side.bounds.add(bound);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    List<Bound> list = new ArrayList<>();
                    list.add(bound);
                    sides.add(new Side(list, bound.o));
                }
            }

            return sides.size();
        }
    }

    record Bound(int x, int y, String o) {}
    record Side(List<Bound> bounds, String o) {
        boolean canBeAttached(Bound bound) {
            if (!o.equals(bound.o)) {
                return false;
            }
            for (Bound b : bounds) {
                if (b.x == bound.x) {
                    if(b.y - 1 == bound.y || b.y + 1 == bound.y) {
                        return true;
                    }
                }
                if (b.y == bound.y) {
                    if (b.x - 1 == bound.x || b.x + 1 == bound.x) {
                        return true;
                    }
                }
            }
            return false;
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
