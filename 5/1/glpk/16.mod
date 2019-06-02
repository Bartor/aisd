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

param n := 16;

param : E : a :=
	1 2 1
	1 3 4
	1 5 11
	1 9 8
	2 4 1
	2 6 1
	2 10 1
	3 4 1
	3 7 4
	3 11 6
	4 8 2
	4 12 4
	5 6 1
	5 7 2
	5 13 5
	6 8 1
	6 14 2
	7 8 1
	7 15 1
	8 16 16
	9 10 5
	9 11 4
	10 12 1
	10 14 2
	11 12 2
	11 15 4
	12 16 9
	13 14 5
	13 15 4
	14 16 7
	15 16 5
;
end;

