import structures.graph.*;

import java.util.Scanner;

public class MSTLauncher {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();

        in.nextLine();

        UndirectedGraph d = new UndirectedGraph(n);

        for (int i = 0; i < m ; i++) {
            String input = in.nextLine();
            String[] line = input.split(" ");
            try {
                d.addEdge(new GraphEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])));
            } catch (NumberFormatException e) {
                System.out.println(e);
                return;
            }
        }

        if (args.length == 0) {
            System.out.println("wrong parameters");
        } else {
            switch (args[0]) {
                case "-k":
                    Kruskal k = new Kruskal(d);
                    System.out.println(k.mst());
                    break;
                case "-p":
                    Prim p = new Prim(d);
                    System.out.println(p.mst());
                    break;
                default:
                    System.out.println("wrong parameters");
                    break;
            }
        }
    }
}
