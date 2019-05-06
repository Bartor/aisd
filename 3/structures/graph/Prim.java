package structures.graph;

import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;

public class Prim {
    private UndirectedGraph graph;

    public Prim(UndirectedGraph graph) {
        this.graph = graph;
    }

    public UndirectedGraph mst() {
        UndirectedGraph g = new UndirectedGraph(graph.getEdges().length);

        boolean[] mst = new boolean[graph.getEdges().length];
        int[] key = new int[graph.getEdges().length];
        int[] parent = new int[graph.getEdges().length];

        for (int i = 0; i < graph.getEdges().length; i++) {
            mst[i] = false;
            key[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        mst[0] = true;
        key[0] = 0;

        PriorityQueueInterface q = new HeapPriorityQueue();

        for (int i = 0; i < graph.getEdges().length; i++) q.insert(new BasicQueueElement<Integer>(i, key[i]));

        while (!q.empty()) {
            int vertex = (Integer) q.pop().getValue();
            mst[vertex] = true;
            for (GraphEdge edge : graph.getEdges()[vertex]) {
                if (!mst[edge.to]) {
                    if (key[edge.to] > edge.weight) {
                        q.priority(new BasicQueueElement<Integer>(edge.to, edge.weight));
                        parent[edge.to] = vertex;
                    }
                }
            }
        }

        for (int i = 1; i < graph.getEdges().length; i++) {
            int weight = -1;
            for (GraphEdge e : graph.getEdges()[parent[i]]) {
                if (e.to == i) {
                    weight = e.weight;
                    break;
                }
            }
            g.addEdge(new GraphEdge(i, parent[i], weight));
            //System.out.println(parent[i] + " " + "-" + " " + i);
        }

        return g;
    }
}
