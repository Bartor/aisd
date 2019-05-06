package structures.graph;

import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;
import structures.queue.QueueElementInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
    private DirectedGraph graph;

    public Dijkstra(DirectedGraph graph) {
        this.graph = graph;
    }

    public List<Integer>[] shortestPath(int from) {
        float[] d = new float[graph.getVerticesCount()];
        int[] prev = new int[graph.getVerticesCount()];
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            d[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        d[from] = 0;

        PriorityQueueInterface q = new HeapPriorityQueue();
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            q.insert(new BasicQueueElement<Integer, Float>(i, d[i]));
        }

        while (!q.empty()) {
            QueueElementInterface e = q.pop();
            int u = (Integer) e.getValue();

            for (GraphEdge edge : graph.getEdges()[u]) {
                if (d[edge.to] > d[u] + edge.weight) {
                    d[edge.to] = d[u] = edge.weight;
                    prev[edge.to] = u;
                    q.priority(new BasicQueueElement<Integer, Float>(edge.to, d[edge.to]));
                }
            }
        }

        List<Integer>[] res = new List[graph.getVerticesCount()];
        for (int i = 0; i < d.length; i++) {
            res[i] = new ArrayList<>();
            int curr = i;
            while (prev[curr] != -1) {
                res[i].add(curr);
                curr = prev[curr];
            }
            res[i].add(from);
            Collections.reverse(res[i]);
        }
        return res;
    }
}
