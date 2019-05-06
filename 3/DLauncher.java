import structures.graph.Dijkstra;
import structures.graph.DirectedGraph;
import structures.graph.GraphEdge;

import java.util.List;
import java.util.Scanner;

public class DLauncher {
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
                d.addEdge(new GraphEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Float.parseFloat(line[2])));
            } catch (NumberFormatException e) {
                System.out.println(e);
                return;
            }
        }

        int start = in.nextInt();

        Dijkstra di = new Dijkstra(d);
        List<Integer>[] paths = di.shortestPath(start);

        for (List<Integer> l : paths) System.out.println(l);
    }
}
