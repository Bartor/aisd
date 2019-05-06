//import structures.graph.*;
//import structures.queue.BasicQueueElement;
//import structures.queue.HeapPriorityQueue;
//
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        //examples of use
//
//        /***
//         * PRIORITY QUEUE
//         */
//        HeapPriorityQueue q = new HeapPriorityQueue();
//        q.insert(new BasicQueueElement<Integer, Float>(1, 1));
//        System.out.println(q.print());
////        q.insert(new BasicQueueElement<Integer, Float>(2, 3));
//        System.out.println(q.print());
//        q.insert(new BasicQueueElement<Integer>(3, 6));
//        System.out.println(q.print());
//        q.insert(new BasicQueueElement<Integer>(4, 5));
//        System.out.println(q.print());
//        q.insert(new BasicQueueElement<Integer>(5, 9));
//        System.out.println(q.print());
//        q.insert(new BasicQueueElement<Integer>(6, 8));
//        System.out.println(q.print());
//        System.out.println(q.top().getValue());
//        q.priority(new BasicQueueElement<Integer>(5, -2));
//        System.out.println(q.top().getValue());
//
//        /***
//         * Dijkstra
//         */
//        DirectedGraph dijkstra = new DirectedGraph(6);
//        dijkstra.addEdge(new GraphEdge(0, 1, 1));
//        dijkstra.addEdge(new GraphEdge(1, 2, 1));
//        dijkstra.addEdge(new GraphEdge(2, 5, 1));
//        dijkstra.addEdge(new GraphEdge(0, 3, 3));
//        dijkstra.addEdge(new GraphEdge(0, 4, 5));
//        dijkstra.addEdge(new GraphEdge(3, 4, 1));
//        dijkstra.addEdge(new GraphEdge(3, 1, 2));
//        dijkstra.addEdge(new GraphEdge(1, 5, 3));
//
//        Dijkstra d = new Dijkstra(dijkstra);
//
//        System.out.println("Dijkstra shortest paths:");
//        for (List<Integer> l : d.shortestPath(0)) System.out.println(l);
//
//        /***
//         * KRUSKAL AND PRIM
//         */
//        UndirectedGraph g = new UndirectedGraph(5);
//        g.addEdge(new GraphEdge(0, 2, 6));
//        g.addEdge(new GraphEdge(0, 3, 1));
//        g.addEdge(new GraphEdge(1, 2, 3));
//        g.addEdge(new GraphEdge(3, 2, 2));
//        g.addEdge(new GraphEdge(4, 2, 8));
//        System.out.println("Original graph:");
//        System.out.println(g);
//
//        System.out.println("Prim's MST:");
//        Prim p = new Prim(g);
//        System.out.println(p.mst());
//
//        System.out.println("Kruskal's MST:");
//        Kruskal k = new Kruskal(g);
//        System.out.println(k.mst());
//
//        /***
//         * SCC
//         */
//        DirectedGraph dg = new DirectedGraph(5);
//        dg.addEdge(new GraphEdge(1, 0, 0));
//        dg.addEdge(new GraphEdge(0, 2, 0));
//        dg.addEdge(new GraphEdge(2, 1, 0));
//        dg.addEdge(new GraphEdge(0, 3, 0));
//        dg.addEdge(new GraphEdge(3, 4, 0));
//
//        System.out.println("Original graph:");
//        System.out.println(dg);
//
//        System.out.println("SCC:");
//        SCC scc = new SCC(dg);
//        System.out.println(scc.scc());
//    }
//}