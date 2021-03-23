package com.metacube.AlgorithmDesignAssn2.Graph;

public class Edge implements Comparable<Edge> {
	private int weight;
	private Node sourceVertex;
	private Node destVertex;

	public Edge(Node source, Node dest, int weight) {
		this.sourceVertex = source;
		this.destVertex = dest;
		this.weight = weight;
	}

	public Edge(Node dest) {
		this.destVertex = dest;
		this.weight = 1;
	}

	@Override
	public int compareTo(Edge edge) {
		return this.weight - edge.weight;
	}

	public int getWeight() {
		return weight;
	}

	public Node getSourceVertex() {
		return sourceVertex;
	}

	public Node getDestVertex() {
		return destVertex;
	}
}
