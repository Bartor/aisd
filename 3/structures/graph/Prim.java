package structures.graph;

import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;

public class Prim {
    private UndirectedGraph graph;

    public Prim(UndirectedGraph graph) {
        this.graph = graph;
    }

    private class node {
        int vertex;
        float key;
    }

    public UndirectedGraph mst() {
        UndirectedGraph g = new UndirectedGraph(graph.getEdges().length);

        boolean[] mst = new boolean[graph.getEdges().length];
        node[] e = new node[graph.getEdges().length];
        int[] parent = new int[graph.getEdges().length];

        for (int i = 0; i < graph.getEdges().length; i++) {
            mst[i] = false;
            e[i] = new node();
            e[i].vertex = i;
            e[i].key = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        mst[0] = true;
        e[0].key = 0;

        PriorityQueueInterface q = new HeapPriorityQueue();

        for (int i = 0; i < graph.getEdges().length; i++) q.insert(new BasicQueueElement<Integer, Float>(e[i].vertex, e[i].key));

        while (!q.empty()) {
            int vertex = (Integer) q.pop().getValue();
            mst[vertex] = true;
            for (GraphEdge edge : graph.getEdges()[vertex]) {
                if (!mst[edge.to]) {
                    if (e[edge.to].key > edge.weight) {
                        e[edge.to].key = edge.weight;
                        q.priority(new BasicQueueElement<Integer, Float>(edge.to, edge.weight));
                        parent[edge.to] = vertex;
                    }
                }
            }
        }

        float total = 0;
        for (int i = 1; i < graph.getEdges().length; i++) {
            float weight = -1;
            for (GraphEdge ee : graph.getEdges()[parent[i]]) {
                if (ee.to == i) {
                    weight = ee.weight;
                    break;
                }
            }
            total += weight;
            g.addEdge(new GraphEdge(i, parent[i], weight));
            System.out.println(parent[i] + " " + i + " : " + weight);
        }

        System.out.println(total);
        return g;
    }
}
