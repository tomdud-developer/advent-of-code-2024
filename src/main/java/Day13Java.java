import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day13Java {

    Pattern buttonAPattern = Pattern.compile("Button A: X\\+(-?\\d+), Y\\+(-?\\d+)");
    Pattern buttonBPattern = Pattern.compile("Button B: X\\+(-?\\d+), Y\\+(-?\\d+)");
    Pattern prizePattern = Pattern.compile("Prize: X=(-?\\d+), Y=(-?\\d+)");

    public long part12(List<String> input, boolean isPart2) {

        List<Game> games = new ArrayList<>();
        for (int i = 0; i < input.size(); i+=4) {
            var bA = buttonAPattern.matcher(input.get(i));
            var bB = buttonBPattern.matcher(input.get(i+1));
            var prize = prizePattern.matcher(input.get(i+2));

            if (!(bA.matches() && bB.matches() && prize.matches()))
                throw new RuntimeException("Invalid input");
            games.add(new Game(
                    new BigDecimal(prize.group(1)).add(isPart2 ? new BigDecimal("10000000000000") : BigDecimal.ZERO),
                    new BigDecimal(prize.group(2)).add(isPart2 ? new BigDecimal("10000000000000") : BigDecimal.ZERO),
                    new BigDecimal(bA.group(1)),
                    new BigDecimal(bA.group(2)),
                    new BigDecimal(bB.group(1)),
                    new BigDecimal(bB.group(2)),
                    isPart2
            ));
        }

        return games.stream().map(Game::calculateTokensToWin).reduce(0L, Long::sum);
    }

    private record Game(
            BigDecimal Px,
            BigDecimal Py,
            BigDecimal x1,
            BigDecimal y1,
            BigDecimal x2,
            BigDecimal y2,
            boolean isPart2) {

        long calculateTokensToWin() {
            BigDecimal n2 = (Px.multiply(y1).subtract(Py.multiply(x1))).setScale(10, RoundingMode.HALF_UP).divide(x2.multiply(y1).subtract(y2.multiply(x1)), RoundingMode.HALF_UP);
            BigDecimal n1 = (Px.subtract(n2.multiply(x2))).setScale(10, RoundingMode.HALF_UP).divide(x1, RoundingMode.HALF_UP);

            if (n1.compareTo(BigDecimal.valueOf(100)) > 0 || n2.compareTo(BigDecimal.valueOf(100)) > 0) {
                if(!isPart2)
                    return 0;
            }


            BigInteger n1i = n1.setScale(0, RoundingMode.DOWN).toBigInteger();
            BigInteger n2i = n2.setScale(0, RoundingMode.DOWN).toBigInteger();

            BigDecimal n1iDecimal = new BigDecimal(n1i);
            BigDecimal n2iDecimal = new BigDecimal(n2i);

            if (n1.compareTo(n1iDecimal) == 0 && n2.compareTo(n2iDecimal) == 0) {
                return n1i.multiply(BigInteger.valueOf(3)).add(n2i).longValue();
            }



            return 0;
        }

    }
}

