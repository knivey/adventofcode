import java.io.*;
import java.util.Scanner;

public class Day5part1 {
   public static void main(String []args) {
      int maxId = 0;
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
      System.out.println("Max ID " + maxId);
   }
}
