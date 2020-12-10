import java.io.*;
import java.util.regex.*;
import java.util.*;

public class Day8part2 {
   private static ArrayList<Instruction> bootCode = new ArrayList<>();
   private static ArrayList<Integer> executedLines = new ArrayList<>();
   private static int acc = 0;

   public static void main(String []args) {
      try {
         FileInputStream fis = new FileInputStream("day8_input.txt");
         Scanner sc = new Scanner(fis);
         while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.trim() != "") {
               String[] s = line.trim().split(" ");
               bootCode.add(new Instruction(s[0], Integer.parseInt(s[1])));
            }
         }

         for(int i = 0; i < bootCode.size(); i++) {
            ArrayList<Instruction> code = new ArrayList<>(bootCode);
            Instruction ins = new Instruction(code.get(i));
            if(ins.op == Op.JMP && ins.value < 0) {
               ins.op = Op.NOP;
               code.set(i, ins);
               if(!tryIt(code)) {
                  break;
               }
            }
         }

         System.out.printf("acc =  %d\n", acc);

         sc.close();
         fis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static boolean tryIt(ArrayList<Instruction> code) {
      acc = 0;
      executedLines.clear();
      boolean failed = false;
      for(int i = 0; i < code.size(); i++) {
         Instruction ins = code.get(i);
         if(executedLines.contains(i)) {
            failed = true;
            break;
         }
         executedLines.add(i);
         if(ins.op == Op.ACC) {
            acc += ins.value;
         }
         if(ins.op == Op.JMP) {
            i += ins.value - 1; //subtract one to account for loop++
         }
      }
      return failed;
   }

}

enum Op {
   ACC,
   JMP,
   NOP
}

class Instruction {
   public Op op;
   public Integer value;
   public Instruction(String op, Integer value) {
      this.op = Op.valueOf(op.toUpperCase());
      this.value = value;
   }

   public Instruction(Instruction ins) {
      this.op = ins.op;
      this.value = ins.value;
   }

   @Override
   public String toString() {
      return "Instruction{" +
              "op='" + op.toString() + '\'' +
              ", value=" + value +
              '}';
   }
}