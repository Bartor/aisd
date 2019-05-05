package structures.graph;

import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;
import structures.queue.QueueElementInterface;

public class Prim {
    private UndirectedGraph graph;

    public Prim(UndirectedGraph graph) {
        this.graph = graph;
    }

    public UndirectedGraph mst() {
        UndirectedGraph g = new UndirectedGraph(graph.getEdges().length);

        boolean[] mst = new boolean[graph.getEdges().length];
        int[] key = new int[graph.getEdges().length];

        PriorityQueueInterface q = new HeapPriorityQueue();
        for (int i = 0; i < graph.getEdges().length; i++) {
            if (i == 0) {
                key[0] = 0;
                mst[0] = true;
            } else {
                key[i] = Integer.MAX_VALUE;
                mst[i] = false;
            }
            q.insert(new BasicQueueElement<Integer>(i, key[i]));
        }

        while (!q.empty()) {
            QueueElementInterface e = q.pop();
            int vertex = (Integer) e.getValue();

            mst[vertex] = true;

            for (GraphEdge edge : graph.getEdges()[vertex]) {
                if (!mst[edge.to]) {
                    if (key[edge.to] > edge.weight) {
                        q.insert(new BasicQueueElement<Integer>(edge.to, edge.weight));
                        g.addEdge(new GraphEdge(edge.from, edge.to, edge.weight));
                        key[edge.to] = edge.weight;
                    }
                }
            }
        }

        return g;
    }
}
