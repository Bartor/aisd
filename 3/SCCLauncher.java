import structures.graph.*;

import java.util.Scanner;

public class SCCLauncher {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();

        in.nextLine();

        DirectedGraph d = new DirectedGraph(n);

        for (int i = 0; i < m ; i++) {
            String input = in.nextLine();
            String[] line = input.split(" ");
            try {
                d.addEdge(new GraphEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1]), 0));
            } catch (NumberFormatException e) {
                System.out.println(e);
                return;
            }
        }

        SCC scc = new SCC(d);
        System.out.println(scc.scc());
    }
}
