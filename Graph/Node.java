package com.metacube.AlgorithmDesignAssn2.Graph;
import java.util.*;
public class Node {
	private String vertex;
	private LinkedList<Edge> edgeList;

	public Node(String vertex) {
		this.vertex = vertex;
		edgeList = new LinkedList<>();
	}

	public String getVertex() {
		return vertex;
	}

	public LinkedList<Edge> getEdges() {
		return edgeList;
	}
}
