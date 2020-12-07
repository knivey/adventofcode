import java.io.*;
import java.util.regex.*;
import java.util.*;

public class Day7part2 {
   private static HashMap<String, HashMap<String, Integer>> bagRules = new HashMap<>();

   public static void main(String []args) {
      try {
         int goldcnt = 0;
         FileInputStream fis = new FileInputStream("day7_input.txt");
         Scanner sc = new Scanner(fis);
         Pattern pbag = Pattern.compile("(?<name>\\w+ \\w+) bags contain (?<rest>.+)");
         Pattern cbag = Pattern.compile("(?<num>\\d) (?<name>\\w+ \\w+) bags?[.,] ?");
         while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Matcher p = pbag.matcher(line);
            if(p.find()) {
               bagRules.put(p.group("name"), new HashMap<String, Integer>());
               Matcher c = cbag.matcher(p.group("rest"));
               while(c.find()) {
                  bagRules.get(p.group("name")).put(c.group("name"), Integer.parseInt(c.group("num")));
               }
            } else {
               System.err.println("Failed match: " + line);
            }
         }

         for(Map.Entry<String, Integer> e : bagRules.get("shiny gold").entrySet()) {
            goldcnt += e.getValue();
            goldcnt += e.getValue() * bagCount(e.getKey());
         }

         System.out.printf("Gold bags will contain %d other bags\n", goldcnt);

         sc.close();
         fis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static int bagCount(String bag) {
      int cnt = 0;
      for(var b : bagRules.get(bag).entrySet()) {
         cnt += b.getValue();
         cnt += b.getValue() * bagCount(b.getKey());
      }
      return cnt;
   }
}
