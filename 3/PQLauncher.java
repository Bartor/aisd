import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;
import structures.queue.QueueElementInterface;

import java.util.Scanner;

public class PQLauncher {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = "";

        PriorityQueueInterface q = new HeapPriorityQueue();

        while(!input.equals("exit")) {
            input = in.nextLine();
            String[] words = input.split(" ");
            if (words.length == 0) continue;
            switch(words[0]) {
                case "insert":
                    if (words.length >= 3) {
                        int value;
                        float priority;
                        try {
                            value = Integer.parseInt(words[1]);
                            priority = Float.parseFloat(words[2]);
                        } catch (NumberFormatException e) {
                            System.out.println("incorrect numbers");
                            break;
                        }
                        q.insert(new BasicQueueElement<Integer, Float>(value, priority));
                    }
                    break;
                case "empty":
                    System.out.println(q.empty());
                    break;
                case "top":
                    System.out.println(q.top() != null ? q.top().getValue() : "");
                    break;
                case "pop":
                    QueueElementInterface e = q.pop();
                    System.out.println(e != null ? e : "");
                    break;
                case "priority":
                    if (words.length >= 3) {
                        int value;
                        float priority;
                        try {
                            value = Integer.parseInt(words[1]);
                            priority = Float.parseFloat(words[2]);
                        } catch (NumberFormatException ee) {
                            System.out.println("incorrect numbers");
                            break;
                        }
                        q.priority(new BasicQueueElement<Integer, Float>(value, priority));
                    }
                    break;
                case "print":
                    System.out.println(((HeapPriorityQueue) q).print());
                    break;
                default:
                    break;
            }
        }
    }
}
