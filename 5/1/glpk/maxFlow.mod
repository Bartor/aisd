param n, integer, >= 2;

set V, default {1..n};

set E, within V cross V;

param a{(i,j) in E}, > 0;

param s, symbolic, in V, default 1;

param t, symbolic, in V, != s, default n;

var x{(i,j) in E}, >= 0, <= a[i,j];

var flow, >= 0;

s.t. node{i in V}:

   sum{(j,i) in E} x[j,i] + (if i = s then flow)

   sum{(i,j) in E} x[i,j] + (if i = t then flow);

maximize obj: flow;

solve;

printf{1..56} "="; printf "\n";
printf "Maximum flow from node %s to node %s is %g\n\n", s, t, flow;
printf "Starting node   Ending node   Arc capacity   Flow in arc\n";
printf "-------------   -----------   ------------   -----------\n";
printf{(i,j) in E: x[i,j] != 0}: "%13s   %11s   %12g   %11g\n", i, j,
   a[i,j], x[i,j];
printf{1..56} "="; printf "\n";

data;
