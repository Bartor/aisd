package structures.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SCC {
    private DirectedGraph graph;

    public SCC(DirectedGraph graph) {
        this.graph = graph;
    }

    private DirectedGraph transposed() {
        DirectedGraph g = new DirectedGraph(graph.getVerticesCount());

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            for (GraphEdge e : graph.getEdges()[i]) {
                g.addEdge(new GraphEdge(e.to, e.from, e.weight));
            }
        }

        return g;
    }

    private void fillOrder(int v, boolean[] visited, Stack stack) {
        visited[v] = true;
        for (GraphEdge edge : graph.getEdges()[v]) {
            if (!visited[edge.to]) fillOrder(edge.to, visited, stack);
        }

        stack.push(v);
    }

    public void lookUp(int v, boolean[] visited, List<Integer> current) {
        visited[v] = true;
        current.add(v);
        //System.out.println(v + " ");

        for (GraphEdge edge : graph.getEdges()[v]) {
            if (!visited[edge.to]) lookUp(edge.to, visited, current);
        }
    }

    public List<List<Integer>> scc() {
        Stack stack = new Stack();
        boolean[] visited = new boolean[graph.getVerticesCount()];

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            visited[i] = false;
        }

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (!visited[i]) fillOrder(i, visited, stack);
        }

        DirectedGraph gt = transposed();

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            visited[i] = false;
        }

        SCC scct = new SCC(gt);

        List<List<Integer>> res = new ArrayList<>();

        while(!stack.empty()) {
            int v = (int) stack.pop();

            List<Integer> c = new ArrayList<>();

            if (!visited[v]) {
                scct.lookUp(v, visited, c);
                //System.out.println();
            }

            if (!c.isEmpty()) res.add(c);
        }

        return res;
    }
}
