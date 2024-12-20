import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Day11Java {

    public static Map<Long, Long> transform(long num) {
        Map<Long, Long> hist = new HashMap<>();

        if (num == 0L) {
            hist.put(1L, hist.getOrDefault(1L, 0L) + 1);
        } else if (String.valueOf(num).length() % 2 == 0) {
            String str = String.valueOf(num);
            long leftPart = Long.parseLong(str.substring(0, str.length() / 2));
            long rightPart = Long.parseLong(str.substring(str.length() / 2));

            hist.put(leftPart, hist.getOrDefault(leftPart, 0L) + 1);
            hist.put(rightPart, hist.getOrDefault(rightPart, 0L) + 1);

        } else {
            hist.put(num * 2024L, hist.getOrDefault(num, 0L) + 1);
        }

        return hist;
    }

    public Long part2(List<Long> input) {
        Map<Long, Long> histogram = new HashMap<>();
        for (Long num : input) {
            histogram.put(num, histogram.getOrDefault(num, 0L) + 1);
        }

        for (int i = 1; i <= 75; i++) {
            Map<Long, Long> newHistogram = new HashMap<>();
            for (Long num : histogram.keySet()) {
                Map<Long, Long> transformed = transform(num);
                for (Long key : transformed.keySet()) {
                    newHistogram.put(key, newHistogram.getOrDefault(key, 0L) + transformed.get(key) * histogram.get(num));
                }
            }
            histogram = newHistogram;
            System.out.println("Length of histogram at level " + i + " is " + calcLengthOfHistogram(histogram));
        }

        return calcLengthOfHistogram(histogram);
    }

    private long calcLengthOfHistogram(Map<Long, Long> histogram) {
        long sum = 0;
        for (Long key : histogram.keySet()) {
            sum += histogram.get(key);
        }
        return sum;
    }

}
