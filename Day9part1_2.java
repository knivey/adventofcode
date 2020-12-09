import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day9part1_2 {
    private static int PRESIZE = 25;
    private static ArrayList<Long> allnums;

    public static void main(String[] args) {
        try {
            var input = Files.lines(Path.of("day9_input.txt"));
            allnums = input.mapToLong(Long::parseUnsignedLong)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
            input.close();
            //No idea if we actually needed the entire list loaded for part2
            for (int i = 0; i < allnums.size(); i++) {
                if (i > PRESIZE) {
                    var num = allnums.get(i);
                    var preamb = new ArrayList<Long>(allnums.subList(i - PRESIZE, i));
                    if (!checkNum(num, preamb)) {
                        System.out.printf("%d doesn't check out\n", num);
                        System.out.printf("Part 2: %d\n", partTwo(num));
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkNum(long num, ArrayList<Long> preamb) {
        for (int i = 0; i < preamb.size(); i++) {
            for (int c = i; c < preamb.size(); c++) {
                if (preamb.get(i) + preamb.get(c) == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public static long partTwo(long num) {
        long sum = 0;
        int start = 0;
        for (int i = 0; i < allnums.size(); i++) {
            var n = allnums.get(i);
            sum += n;
            while (sum > num && start <= i) {
                sum -= allnums.get(start);
                start++;
            }

            //var ubl = allnums.subList(start, i+1);
            //System.out.printf("%d  %s\n", sum, ubl);
            if (num == sum) {
                var subl = allnums.subList(start, i + 1);
                return Collections.max(subl) + Collections.min(subl);
            }
        }
        return -1;
    }
}

