/* MAXFLOW, Maximum Flow Problem */

/* Written in GNU MathProg by Andrew Makhorin <mao@gnu.org> */

/* The Maximum Flow Problem in a network G = (V, E), where V is a set
   of nodes, E within V x V is a set of arcs, is to maximize the flow
   from one given node s (source) to another given node t (sink) subject
   to conservation of flow constraints at each node and flow capacities
   on each arc. */

param n, integer, >= 2;
/* number of nodes */

set V, default {1..n};
/* set of nodes */

set E, within V cross V;
/* set of arcs */

param a{(i,j) in E}, > 0;
/* a[i,j] is capacity of arc (i,j) */

param s, symbolic, in V, default 1;
/* source node */

param t, symbolic, in V, != s, default n;
/* sink node */

var x{(i,j) in E}, >= 0, <= a[i,j];
/* x[i,j] is elementary flow through arc (i,j) to be found */

var flow, >= 0;
/* total flow from s to t */

s.t. node{i in V}:
/* node[i] is conservation constraint for node i */

   sum{(j,i) in E} x[j,i] + (if i = s then flow)
   /* summary flow into node i through all ingoing arcs */

   = /* must be equal to */

   sum{(i,j) in E} x[i,j] + (if i = t then flow);
   /* summary flow from node i through all outgoing arcs */

maximize obj: flow;
/* objective is to maximize the total flow through the network */

solve;

printf{1..56} "="; printf "\n";
printf "Maximum flow from node %s to node %s is %g\n\n", s, t, flow;

data;

/* These data correspond to an example from [Christofides]. */

/* Optimal solution is 29 */

param n := 66;

param : E : a :=
        1 2 1
        1 3 1
        1 4 1
        1 5 1
        1 6 1
        1 7 1
        1 8 1
        1 9 1
        1 10 1
        1 11 1
        1 12 1
        1 13 1
        1 14 1
        1 15 1
        1 16 1
        1 17 1
        1 18 1
        1 19 1
        1 20 1
        1 21 1
        1 22 1
        1 23 1
        1 24 1
        1 25 1
        1 26 1
        1 27 1
        1 28 1
        1 29 1
        1 30 1
        1 31 1
        1 32 1
        1 33 1
        2 48 1
        2 38 1
        2 40 1
        3 58 1
        3 42 1
        3 55 1
        4 57 1
        4 52 1
        4 41 1
        5 62 1
        5 43 1
        5 50 1
        6 36 1
        6 55 1
        6 45 1
        7 51 1
        7 56 1
        7 40 1
        8 43 1
        8 54 1
        8 61 1
        9 54 1
        9 61 1
        9 34 1
        10 40 1
        10 62 1
        10 53 1
        11 51 1
        11 59 1
        11 46 1
        12 44 1
        12 56 1
        12 51 1
        13 35 1
        13 56 1
        13 46 1
        14 50 1
        14 53 1
        14 40 1
        15 48 1
        15 40 1
        15 60 1
        16 65 1
        16 46 1
        16 60 1
        17 49 1
        17 37 1
        17 51 1
        18 39 1
        18 34 1
        18 58 1
        19 45 1
        19 46 1
        19 47 1
        20 59 1
        20 63 1
        20 60 1
        21 44 1
        21 49 1
        21 65 1
        22 51 1
        22 53 1
        22 56 1
        23 35 1
        23 54 1
        23 60 1
        24 36 1
        24 49 1
        24 57 1
        25 50 1
        25 62 1
        25 38 1
        26 45 1
        26 51 1
        26 44 1
        27 65 1
        27 62 1
        27 60 1
        28 52 1
        28 36 1
        28 62 1
        29 46 1
        29 42 1
        29 64 1
        30 35 1
        30 42 1
        30 64 1
        31 59 1
        31 63 1
        31 65 1
        32 43 1
        32 55 1
        32 54 1
        33 39 1
        33 40 1
        33 37 1
        34 66 1
        35 66 1
        36 66 1
        37 66 1
        38 66 1
        39 66 1
        40 66 1
        41 66 1
        42 66 1
        43 66 1
        44 66 1
        45 66 1
        46 66 1
        47 66 1
        48 66 1
        49 66 1
        50 66 1
        51 66 1
        52 66 1
        53 66 1
        54 66 1
        55 66 1
        56 66 1
        57 66 1
        58 66 1
        59 66 1
        60 66 1
        61 66 1
        62 66 1
        63 66 1
        64 66 1
        65 66 1
;
end;