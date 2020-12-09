import java.io.*;
import java.util.*;

public class Day9part1_2 {
    private static int PRESIZE = 25;
    private static ArrayList<Long> allnums = new ArrayList<>();

    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("day9_input.txt");
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim() != "") {
                    long num = Long.parseUnsignedLong(line.trim());
                    allnums.add(num);
                }
            }
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

            sc.close();
            fis.close();
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

