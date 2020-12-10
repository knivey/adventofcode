import java.io.*;
import java.lang.*;
import java.util.*;

public class Day5part2 {
   public static void main(String []args) {
      int maxId = 0;
      ArrayList<Integer> ids = new ArrayList<Integer>();
      try {
         FileInputStream fis = new FileInputStream("day5_input.txt");
         Scanner sc = new Scanner(fis);
         while (sc.hasNextLine()) {
            String line = sc.nextLine();
            int row = 0;
            int col = 0;
            int id;
            String b = "";
            for(char c : line.substring(0,7).toCharArray()) {
               row = row << 1;
               if(c == 'B') {
                  row++;
               }
            }
            for(char c : line.substring(7,10).toCharArray()) {
               col = col << 1;
               if(c == 'R') {
                  col++;
               }
            }
            id = row * 8 + col;
            ids.add(id);
            if(id > maxId) {
               maxId = id;
            }
            System.out.println(line+" Row "+row+ " Col " + col+" ID " + id);
         }
         sc.close();
         fis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      int lastId = 0;
      Collections.sort(ids);
      for(int id : ids) {
         if(id-2 == lastId)
            System.out.println("Missing ID " + (id-1));
         lastId = id;
      }
      System.out.println("Max ID " + maxId);
   }
}
