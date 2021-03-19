package com.metacube.AlgorithmDesignAssn2;

public class QuickSortLinkedList {
	static class Node {
		int sal;
		int age;
		Node next;

		Node(int sal, int age) {
			this.sal = sal;
			this.age = age;
			this.next = null;
		}
	}

	Node head;

	void addNode(int sal, int age) {
		if (head == null) {
			head = new Node(sal, age);
			return;
		}

		Node curr = head;
		while (curr.next != null)
			curr = curr.next;
		Node newNode = new Node(sal, age);
		curr.next = newNode;
	}

	void printList(Node n) {
		if(n==null)
			throw new AssertionError("Empty List");
		while (n != null) {
			System.out.print("Salary : " + n.sal + "   ");
			System.out.print("Age : " + n.age);
			System.out.println();
			n = n.next;
		}
	}

	// takes first and last node,
	// but do not break any links in
	// the whole linked list

	Node PartionList(Node start, Node end) {
		if (start == end || start == null || end == null)
			return start;

		Node pivot_prev = start;
		Node curr = start;
		int pivot = end.sal;
		int ages = end.age;
		// iterate till one before the end,
		// no need to iterate till the end
		// because end is pivot
		while (start != end) {
			if (start.sal < pivot) {
				// keep tracks of last modified item
				pivot_prev = curr;
				int temp = curr.sal;
				int temp1 = curr.age;
				curr.sal = start.sal;
				curr.age = start.age;
				start.sal = temp;
				start.age = temp1;
				curr = curr.next;
			} else if (start.sal == pivot) {
				if (start.age > ages) {
					int temp = curr.sal;
					int temp1 = curr.age;
					curr.sal = start.sal;
					curr.age = start.age;
					start.sal = temp;
					start.age = temp1;
					curr = curr.next;
				}
			}
			start = start.next;
		}

		// swap the position of curr i.e.
		// next suitable index and pivot
		int temp = curr.sal;
		int temp1 = curr.age;
		curr.sal = pivot;
		curr.age = ages;
		end.sal = temp;
		end.age = temp1;

		// return one previous to current
		// because current is now pointing to pivot
		return pivot_prev;
	}

	void sort(Node start, Node end) {
		if (start == null || start == end || start == end.next)
			return;

		// split list and partion recurse
		Node pivot_prev = PartionList(start, end);
		sort(start, pivot_prev);

		// if pivot is picked and moved to the start,
		// that means start and pivot is same
		// so pick from next of pivot
		if (pivot_prev != null && pivot_prev == start)
			sort(pivot_prev.next, end);

		// if pivot is in between of the list,
		// start from next of pivot,
		// since we have pivot_prev, so we move two nodes
		else if (pivot_prev != null && pivot_prev.next != null)
			sort(pivot_prev.next.next, end);
	}

	Node reverse(Node node) {
		Node prev = null;
		Node current = node;
		Node next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		node = prev;
		return node;
	}

	public static void main(String... arg) {
		QuickSortLinkedList qsc = new QuickSortLinkedList();
		qsc.addNode(12000, 27);
		qsc.addNode(22000, 25);
		qsc.addNode(42000, 22);
		qsc.addNode(28000, 20);
		qsc.addNode(12000, 22);
		qsc.addNode(15000, 20);
		qsc.addNode(25000, 25);
		Node n = qsc.head;
		while (n.next != null)
			n = n.next;

		qsc.sort(qsc.head, n);

		qsc.head = qsc.reverse(qsc.head);
		qsc.printList(qsc.head);
	}
}
