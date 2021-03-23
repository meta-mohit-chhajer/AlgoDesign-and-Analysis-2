package com.metacube.AlgorithmDesignAssn2.Graph;

import java.util.*;

public interface GraphInterface {
	boolean isConnected();

	HashSet<Node> reachable(String vertex);

	ArrayList<Edge> minSpanningTree();

	ArrayList<Edge> shortestPath(String src, String dest);
}
