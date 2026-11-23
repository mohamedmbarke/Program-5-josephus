import java.util.*;
import java.io.*;

public class JosephusSim {
   private PersonNode front;      // a PersonNode pointer that tracks first node in the list
   private int size;              // the number of people in the circle
   private int eliminationCount;  // the number to count to for elimination

   public JosephusSim(String fileName) {
      try {
         // load names from the file in order, generating a singly linked list of PersonNodes
         Scanner file = new Scanner(new File(fileName));
         PersonNode last = null;

         while (file.hasNextLine()) {
            String name = file.nextLine();
            PersonNode newNode = new PersonNode(name);

            if (front == null) {
               front = newNode;
            } else {
               last.next = newNode;
            }

            last = newNode;
            size++;
         }
         file.close();

         // make the ring circular by attaching last node's next to front
         if (last != null) {
            last.next = front;
         }

         // generate, print, and save the random elimination count
         if (size >= 2) {
            Random rand = new Random();
            eliminationCount = rand.nextInt(size / 2) + 1;
            System.out.println("Elimination count: " + eliminationCount);
         }

      } catch (FileNotFoundException e) {
         System.out.println("Something went wrong with " + fileName);
      }
   }

   // optional helper method for constructing the circle
   private void add(String val) {
      front = new PersonNode(val, front);
      size++;
   }

   public void eliminate() {
      if (size <= 1) {
         return;
      }

      // find the last node in the circle (the node just before front)
      PersonNode prev = front;
      for (int i = 0; i < size - 1; i++) {
         prev = prev.next;
      }

      // count to the elimination count
      for (int i = 0; i < eliminationCount - 1; i++) {
         prev = prev.next;
      }

      // print who will be eliminated
      PersonNode victim = prev.next;
      System.out.println(victim.name + " is eliminated!");

      // eliminate the person and update "front" of the circle and size
      prev.next = victim.next;
      front = victim.next;
      size--;
   }

   public boolean isOver() {
      // check if there's only one person left in the circle
      return size <= 1;
   }

   public String toString() {
      // if there's only one person left, print them as the last survivor
      if (size == 1) {
         return "Last survivor: " + front.name;
      }

      // if many, print survivors (watch out for infinite loop since list is circular)
      if (size == 0) {
         return "No people in the circle.";
      }

      String result = "Remaining people: ";
      PersonNode current = front;
      for (int i = 0; i < size; i++) {
         if (i > 0) {
            result += ", ";
         }
         result += current.name;
         current = current.next;
      }
      return result;
   }
}
