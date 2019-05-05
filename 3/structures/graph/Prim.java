package structures.graph;

import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;

public class Prim {
    private Integer[] parent;
    private int[] keys;
    private UndirectedGraph graph;

    public Prim(UndirectedGraph graph) {
        this.graph = graph;
        parent = new Integer[graph.getEdges().length];
        keys = new int[graph.getEdges().length];
    }

    public UndirectedGraph mst(int root) {
        UndirectedGraph g = new UndirectedGraph(graph.getEdges().length);

        PriorityQueueInterface q = new HeapPriorityQueue();
        for (int i = 0; i < graph.getEdges().length; i++) {
            keys[i] = Integer.MAX_VALUE;
        }
        keys[root] = 0;
        for (int i = 0; i < graph.getEdges().length; i++) {
            q.insert(new BasicQueueElement<Integer>(i, keys[i]));
        }
        parent[root] = null;

        while (!q.empty()) {
            int u = (Integer) q.top().getValue();
            for (GraphEdge e : graph.getEdges()[u]) {
                if (q.contains(new BasicQueueElement<Integer>(e.from, 0)) && e.weight < keys[e.from]) {
                    parent[e.from] = e.to;
                    keys[e.from] = e.weight;
                    q.priority(new BasicQueueElement<Integer>(e.from, keys[e.from]));
                    g.addEdge(e);
                }
            }
            q.pop();
        }
        return g;
    }
}
