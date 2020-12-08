import java.io.*;
import java.util.regex.*;
import java.util.*;

public class Day8part1 {
   private static ArrayList<Instruction> bootCode = new ArrayList<>();
   private static ArrayList<Integer> executedLines = new ArrayList<>();
   public static void main(String []args) {
      try {
         int acc = 0;
         FileInputStream fis = new FileInputStream("day8_input.txt");
         Scanner sc = new Scanner(fis);
         while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.trim() != "") {
               String[] s = line.trim().split(" ");
               bootCode.add(new Instruction(s[0], Integer.parseInt(s[1])));
            }
         }

         //System.out.println(bootCode);

         for(int i = 0; i < bootCode.size(); i++) {
            if(executedLines.contains(i)) {
               break;
            }
            executedLines.add(i);
            Instruction ins = bootCode.get(i);
            //System.out.println(ins);
            if(ins.name.contentEquals("acc")) {
               //System.out.printf("Line: %d acc += %d\n", i, ins.value);
               acc += ins.value;
            }
            if(ins.name.contentEquals("jmp")) {
               //System.out.printf("Line: %d jmp to %d\n", i, i + ins.value);
               i += ins.value - 1; //subtract one to account for loop++
            }
            //System.out.printf("i: %d acc: %d\n", i, acc);
         }

         System.out.printf("acc =  %d\n", acc);

         sc.close();
         fis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}

class Instruction {
   public String name;
   public Integer value;
   public Instruction(String name, Integer value) {
      this.name = name;
      this.value = value;
   }

   @Override
   public String toString() {
      return "Instruction{" +
              "name='" + name + '\'' +
              ", value=" + value +
              '}';
   }
}