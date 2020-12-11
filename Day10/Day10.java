
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Day10 {

    public static void main(String[] args) throws IOException {
        var input = Files.lines(Path.of("input.txt"));
        ArrayList<Long> allnums = new ArrayList<>();
        allnums.add((long) 0);
        input.mapToLong(Long::parseUnsignedLong)
                .boxed()
                .sorted()
                .forEach(allnums::add);
        input.close();
        allnums.add(allnums.get(allnums.size()-1) + 3);
        long ones = 0;
        long threes = 0;
        long prev = 0;
        for(Long i : allnums) {
            if(i-3 == prev)
                threes++;
            if(i-1 == prev)
                ones++;
            prev = i;
        }

        System.out.printf("%d ones, %d threes. Answer: %d \n", ones, threes, ones*threes);

        var m = allnums.stream()
                .collect(
                        () -> {return new long[] {0,0,0};},
                        (long[] a, Long c) -> {
                            if (c-3 == a[0])
                                a[2]++;
                            if (c-1 == a[0])
                                a[1]++;
                            a[0] = c;
                        },
                        (a, b) -> {
                            a[1] += b[1];
                            a[2] += b[2];
                        }
                );

        System.out.println(m[1]);
        System.out.println(m[2]);

        allnums.add((long)0);
        allnums.add((long)0);
        Collections.sort(allnums);
        ArrayList<Long> lol = new ArrayList<>(3);
        lol.add((long)0);
        lol.add((long)0);
        lol.add((long)1);
        for(int i = 3; i < allnums.size(); i++) {
            long num = allnums.get(i);
            long a = 0;
            for(int x = i-3; x < i; x++) {
                if(num <= allnums.get(x) + 3) {
                    a += lol.get(3-(i-x));
                }
            }
            lol.remove(0);
            lol.add(a);
        }
        System.out.println(lol);
    }

}

