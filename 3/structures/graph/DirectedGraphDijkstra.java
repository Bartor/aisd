package structures.graph;

import structures.queue.BasicQueueElement;
import structures.queue.HeapPriorityQueue;
import structures.queue.PriorityQueueInterface;
import structures.queue.QueueElementInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectedGraphDijkstra implements GraphInterface {
    private int[][] edges;

    public DirectedGraphDijkstra(int verticesCount) {
        edges = new int[verticesCount][verticesCount];
        for (int[] e : edges) for (int i = 0; i < e.length; i++) e[i] = -1;
    }

    @Override
    public void addEdge(GraphEdge edge) {
        edges[edge.from][edge.to] = edge.weight;
    }

    public List<Integer>[] shortestPath(int from) {
        int[] d = new int[edges.length];
        int[] prev = new int[edges.length];
        for (int i = 0; i < edges.length; i++) {
            d[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        d[from] = 0;

        PriorityQueueInterface q = new HeapPriorityQueue();
        for (int i = 0; i < edges.length; i++) {
            q.insert(new BasicQueueElement<Integer>(i, d[i]));
        }

        while (!q.empty()) {
            QueueElementInterface e = q.pop();
            int u = (Integer) e.getValue();
            for (int i = 0; i < edges.length; i++) {
                if (edges[u][i] > -1) {
                    if (d[i] > d[u] + edges[u][i]) {
                        d[i] = d[u] + edges[u][i];
                        prev[i] = u;
                        q.priority(new BasicQueueElement<Integer>(i, d[i]));
                    }
                }
            }
        }

        List<Integer>[] res = new List[edges.length];
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

    @Override
    public int getVerticesCount() {
        return edges.length;
    }
}
