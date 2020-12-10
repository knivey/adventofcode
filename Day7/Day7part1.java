import java.io.*;
import java.util.regex.*;
import java.util.*;

public class Day7part1 {
   private static HashMap<String, HashMap<String, Integer>> bagRules = new HashMap<String, HashMap<String, Integer>>();
   private static List<String> searched = new ArrayList<String>();
   private static List<String> yesbag = new ArrayList<String>();

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
               //System.out.printf("pbag: %s\n", p.group("name"));
               //System.out.printf("  rest: \"%s\"\n", p.group("rest"));
               Matcher c = cbag.matcher(p.group("rest"));
               while(c.find()) {
                  bagRules.get(p.group("name")).put(c.group("name"), Integer.parseInt(c.group("num")));
                  //System.out.printf("  cbag: %s num: %d\n", c.group("name"), Integer.parseInt(c.group("num")));
               }
            } else {
               System.err.println("Failed match: " + line);
            }
         }

         for(Map.Entry bag : bagRules.entrySet()) {
            goldcnt += searchBag(bag.getKey().toString());
         }

         System.out.printf("%d bags may hold shiny gold bags.\n", goldcnt);

         sc.close();
         fis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static int searchBag(String bag) {
      //System.out.printf("Searching %s \n", bag);
      if(yesbag.contains(bag)) {
         return 1;
      }
      if(bagRules.get(bag).keySet().contains("shiny gold")) {
         yesbag.add(bag);
         return 1;
      }
      if(searched.contains(bag) || bag == "shiny gold") {
         return 0;
      }
      searched.add(bag);

      for(String b : bagRules.get(bag).keySet()) {
         if(searchBag(b) == 1) {
            yesbag.add(bag);
            return 1;
         }
      }
      return 0;
   }
}
