package com.metacube.AlgorithmDesignAssn2.Graph;

import java.util.*;

public class Graph implements GraphInterface {
	private HashSet<Node> nodes;
	private Node startNode;

	// Default Constructor
	public Graph() {
		nodes = new HashSet<>();
	}

	/*
	 * Adds an edge between two nodes of the graph.
	 * 
	 * @param Node 1.
	 * 
	 * @param Node 2.
	 * 
	 * @param weight Weight of edge.
	 * 
	 * @return boolean True if edge added successfully, False otherwise.
	 */
	public boolean addEdge(Node v1, Node v2, int weight) {
		return v1.getEdges().add(new Edge(v1, v2, weight))
				&& v2.getEdges().add(new Edge(v2, v1, weight));
	}

	/*
	 * Adds a new node to the graph.
	 * 
	 * @param v The node to be added.
	 * 
	 * @return boolean True if node added successfully, False otherwise.
	 */
	public boolean addVertex(Node v) {
		if (startNode == null) {
			startNode = v;
		}
		return nodes.add(v);
	}

	/*
	 * Prints all the nodes and edges of the graph.
	 */
	public void printGraph() {
		for (Node v : nodes) {
			System.out.println("Src. Vertex -> " + v.getVertex() + ":");
			for (Edge e : v.getEdges()) {
				System.out.println(" Vertex -> "
						+ e.getDestVertex().getVertex() + ", Weight: "
						+ e.getWeight());
			}
			System.out.println();
		}
	}

	public boolean isConnected() {
		HashSet<Node> visitedNodes = dfs(startNode);
		if (visitedNodes.size() == nodes.size()) {
			return true;
		}
		return false;
	}

	public HashSet<Node> reachable(String vertex) {
		Node node = getNodeFromVertex(vertex);
		if (node != null) {
			return dfs(node);
		}
		return null;
	}

	public ArrayList<Edge> minSpanningTree() {
		HashSet<Node> visitedNodes = new HashSet<Node>();
		ArrayList<Edge> edgesInMST = new ArrayList<Edge>();

		HashMap<String, String> parents = new HashMap<String, String>();
		for (Node node : nodes) {
			parents.put(node.getVertex(), node.getVertex());
		}

		Node firstNode = startNode;
		PriorityQueue<Edge> edgeQueue = new PriorityQueue<Edge>();
		for (Edge edge : firstNode.getEdges()) {
			edgeQueue.add(edge);

		}

		Node secondNode;
		int nodesInMST = 1;
		visitedNodes.add(firstNode);
		while (nodesInMST < nodes.size() && !edgeQueue.isEmpty()) {
			Edge minWeightEdge = edgeQueue.poll();
			firstNode = minWeightEdge.getSourceVertex();
			secondNode = minWeightEdge.getDestVertex();

			String firstNodeParent = getParent(firstNode.getVertex(), parents);
			String secondNodeParent = getParent(secondNode.getVertex(), parents);

			if (!firstNodeParent.equals(secondNodeParent)) {
				edgesInMST.add(minWeightEdge);

				if (!visitedNodes.contains(firstNode)) {
					visitedNodes.add(firstNode);

					for (Edge edge : firstNode.getEdges()) {
						edgeQueue.add(edge);
					}
				}

				if (!visitedNodes.contains(secondNode)) {
					visitedNodes.add(secondNode);

					for (Edge edge : secondNode.getEdges()) {
						edgeQueue.add(edge);
					}
				}

				unionVertices(firstNodeParent, secondNodeParent, parents);
				nodesInMST++;
			}
		}
		if (nodesInMST != nodes.size()) {
			return null;
		}
		return edgesInMST;
	}

	public ArrayList<Edge> shortestPath(String source, String dest) {
		Node sourceVertex = getNodeFromVertex(source);
		Node destVertex = getNodeFromVertex(dest);

		if (null == sourceVertex || null == destVertex) {
			return null;
		}

		ArrayList<Edge> shortestPathEdges = new ArrayList<Edge>();
		HashMap<String, String> parents = new HashMap<String, String>();
		HashMap<String, Edge> edgeFromParent = new HashMap<String, Edge>();
		HashSet<String> visitedVertices = new HashSet<String>();

		HashMap<String, Integer> cost = new HashMap<String, Integer>();
		for (Node node : nodes) {
			cost.put(node.getVertex(), Integer.MAX_VALUE);
		}

		String initialVertex = sourceVertex.getVertex();
		cost.put(initialVertex, 0);
		parents.put(initialVertex, initialVertex);

		while (initialVertex != destVertex.getVertex()) {
			visitedVertices.add(initialVertex);
			int initialVertexCost = cost.get(initialVertex);

			for (Edge edge : sourceVertex.getEdges()) {
				Node otherVertex = edge.getDestVertex();

				if (!visitedVertices.contains(otherVertex.getVertex())
						&& initialVertexCost + edge.getWeight() < cost
								.get(otherVertex.getVertex())) {
					parents.put(otherVertex.getVertex(), initialVertex);
					cost.put(otherVertex.getVertex(),
							initialVertexCost + edge.getWeight());
					edgeFromParent.put(otherVertex.getVertex(), edge);
				}
			}

			initialVertex = minCodeVertex(cost, visitedVertices);
			if (null == initialVertex) {
				return null;
			}
			sourceVertex = getNodeFromVertex(initialVertex);
		}

		while (!initialVertex.equals(source)) {
			shortestPathEdges.add(edgeFromParent.get(initialVertex));
			initialVertex = parents.get(initialVertex);
		}

		return shortestPathEdges;
	}

	private String minCodeVertex(HashMap<String, Integer> cost,
			HashSet<String> visitedVertices) {
		int minCost = Integer.MAX_VALUE;
		String minCostVertex = null;

		for (Node node : nodes) {
			String vertex = node.getVertex();
			if (!visitedVertices.contains(vertex) && cost.get(vertex) < minCost) {
				minCost = cost.get(vertex);
				minCostVertex = vertex;
			}
		}

		return minCostVertex;
	}

	/*
	 * Finds the node with a given name of vertex.
	 * 
	 * @param vertex Name of the node.
	 * 
	 * @return Node The node with the given name.
	 */
	private Node getNodeFromVertex(String vertex) {
		for (Node node : nodes) {
			if (node.getVertex().equals(vertex)) {
				return node;
			}
		}
		return null;
	}

	/*
	 * Depth First Search to find connectivity of graph.
	 * 
	 * @param startNode The starting node for DFS.
	 * 
	 * @return HashSet<Node> Set of all the traversed node.
	 */
	private HashSet<Node> dfs(Node startNode) {
		Stack<Node> nodesToVisit = new Stack<Node>();
		HashSet<Node> visitedNodes = new HashSet<Node>();
		Node currentNode = startNode;

		nodesToVisit.push(currentNode);

		while (!nodesToVisit.isEmpty()) {
			currentNode = nodesToVisit.pop();
			visitedNodes.add(currentNode);

			for (Edge edge : currentNode.getEdges()) {
				if (!visitedNodes.contains(edge.getDestVertex())) {
					nodesToVisit.push(edge.getDestVertex());
				}
			}
		}

		return visitedNodes;
	}

	/*
	 * Part of Union Set Algorithm.<br> Finds the parent of a given vertex.
	 * 
	 * @param vertex The vertex whose parent needs to be found.
	 * 
	 * @param parents HashMap of all the vertices and their parents.
	 * 
	 * @return String The name of parent of given vertex.
	 */
	private String getParent(String vertex, HashMap<String, String> parents) {
		while (!vertex.equals(parents.get(vertex))) {
			vertex = parents.get(vertex);
		}
		return vertex;
	}

	/*
	 * Part of Union Set Algorithm.<br> Replaces the parent of one of the
	 * vertices.
	 * 
	 * @param firstNodeParent Parent of first node.
	 * 
	 * @param secondNodeParent Parent of second node.
	 * 
	 * @param parents HashMap of all vertices and their parents.
	 */
	private void unionVertices(String firstNodeParent, String secondNodeParent,
			HashMap<String, String> parents) {
		parents.put(firstNodeParent, secondNodeParent);
	}

	public static void main(String... arg) {
		Scanner sc = new Scanner(System.in);
		Graph graph = new Graph();

		// creating graph manually
		// creating nodes
		Node v0 = new Node("0");
		Node v1 = new Node("1");
		Node v2 = new Node("2");
		Node v3 = new Node("3");

		// adding nodes to graph
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);

		// adding edges between nodes
		graph.addEdge(v0, v1, 2);
		graph.addEdge(v1, v2, 3);
		graph.addEdge(v2, v0, 1);
		graph.addEdge(v2, v3, 1);
		graph.addEdge(v3, v1, 4);

		int opt;
		while (true) {
			System.out.println("1. Is Graph Connected ");
			System.out.println("2. Reachable from vertices ");
			System.out.println("3. Minimum Spanning Tree");
			System.out.println("4. Shortest Path Algorithm");
			System.out.println("5. Print Graph");
			System.out.println("6. Exit");
			System.out.print("Enter choice ");
			opt = sc.nextInt();
			switch (opt) {
			case 1:
				CheckisConnected(graph);
				break;
			case 2:
				findReachable(graph);
				break;
			case 3:
				minimumspannindTree(graph);
				break;
			case 4:
				shortestPath(graph);
				break;
			case 5:
				System.out.println("Structure....");
				graph.printGraph();
				break;
			case 6:
				sc.close();
				System.exit(0);
				break;
			default:
				System.err.println("Invalid Choice");
			}
		}

	}

	private static void CheckisConnected(Graph graph) {
		if (graph.isConnected()) {
			System.out.println("Connected Graph");
		} else {
			System.out.println("Not Connectd Graph");
		}
	}

	private static void findReachable(Graph graph) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter vertex: ");
		String vertex = sc.next().trim();

		HashSet<Node> nodes = graph.reachable(vertex);
		if (null != nodes) {
			for (Node node : nodes) {
				System.out.println("Vertex: " + node.getVertex());
			}
		} else {
			System.out.println("\nEntered vertex does not exist!");
		}
	}

	private static void minimumspannindTree(Graph graph) {
		ArrayList<Edge> edges = graph.minSpanningTree();
		int cost = 0;

		if (null != edges) {
			System.out.println("\nEdges in MST:\n");

			for (Edge edge : edges) {
				System.out.println(edge.getSourceVertex().getVertex() + " - "
						+ edge.getDestVertex().getVertex());
				cost += edge.getWeight();
			}

			System.out.println("\nMinimum Cost Spanning Tree: " + cost);
		} else {
			System.out.println("\nMinimum Spanning Tree could not be found!");
		}
	}

	private static void shortestPath(Graph graph) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter Source Vertex: ");
		String source = sc.nextLine().trim();

		System.out.print("Enter Destination Vertex: ");
		String dest = sc.nextLine().trim();

		ArrayList<Edge> path = graph.shortestPath(source, dest);

		if (null != path) {
			System.out.println("\nShortest Path:");

			int cost = 0;
			String sourceToDest = "";
			for (Edge edge : path) {
				sourceToDest = "\n" + edge.getSourceVertex().getVertex() + "->"
						+ edge.getDestVertex().getVertex() + sourceToDest;
				cost += edge.getWeight();
			}
			System.out.println(sourceToDest);
			System.out.println("\nTotal Cost: " + cost);
		} else {
			System.out.println("\nPath could not be found!");
		}

	}

}
