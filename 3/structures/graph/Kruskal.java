package structures.graph;

import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;
import structures.queue.QueueElementInterface;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {
    private int v;
    private List<GraphEdge> edges = new ArrayList<>();

    public Kruskal(UndirectedGraph graph) {
        this.v = graph.getEdges().length;
        //creating a simpler graph representation
        for (List<GraphEdge> l : graph.getEdges()) {
            for (GraphEdge e : l) {
                this.edges.add(new GraphEdge(e.from, e.to, e.weight));
                for (GraphEdge ee : graph.getEdges()[e.to]) {
                    if (ee.to == e.from) {
                        graph.getEdges()[e.to].remove(ee);
                        break;
                    }
                }
            }
        }
    }

    public UndirectedGraph mst() {
        PriorityQueueInterface q = new HeapPriorityQueue();
        for (int i = 0; i < edges.size(); i++) {
            q.insert(new BasicQueueElement<GraphEdge>(edges.get(i), edges.get(i).weight));
        }

        int[] parent = new int[v];
        makeSet(parent);

        UndirectedGraph g = new UndirectedGraph(v);
        int index = 0;
        while (index < v - 1) {
            GraphEdge edge = (GraphEdge) q.pop().getValue();
            int x = find(parent, edge.from);
            int y = find(parent, edge.to);

            if (x != y) {
                g.addEdge(new GraphEdge(edge.from, edge.to, edge.weight));
                index++;
                union(parent, x, y);
            }
        }

        return g;
    }

    private void makeSet(int[] parent) {
        for (int i = 0; i < v; i++) {
            parent[i] = i;
        }
    }

    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) return find(parent, parent[vertex]);
        return vertex;
    }

    private void union(int[] parent, int x, int y) {
        int xx = find(parent, x);
        int yy = find(parent, y);

        parent[yy] = xx;
    }
}
