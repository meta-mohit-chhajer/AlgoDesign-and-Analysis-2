package com.metacube.AlgorithmDesignAssn2;
import org.junit.*;

public class QuickSortTest {
	@Test(expected=AssertionError.class)
	public void Emptylist()
	{
		QuickSortLinkedList q=new QuickSortLinkedList();
		q.printList(q.head);
	}
	@Test
	public void Sorttest() {
		QuickSortLinkedList l1 = new QuickSortLinkedList();
		l1.addNode(15000, 26);
		l1.addNode(14500, 24);
		l1.addNode(12000, 20);
		l1.addNode(20000, 24);
		l1.addNode(12000, 22);
		l1.addNode(22000, 22);
		QuickSortLinkedList.Node n=l1.head;
		while(n.next!=null)
			n=n.next;
		l1.sort(l1.head,n);
		l1.head=l1.reverse(l1.head);
		Assert.assertEquals(22000, l1.head.sal);
	}

	@Test
	public void Sortest1() {
		QuickSortLinkedList l1 = new QuickSortLinkedList();
		l1.addNode(15000, 26);
		l1.addNode(14500, 24);
		l1.addNode(12000, 20);
		l1.addNode(20000, 24);
		l1.addNode(12000, 22);
		l1.addNode(22000, 22);
		QuickSortLinkedList.Node n=l1.head;
		while(n.next!=null)
			n=n.next;
		l1.sort(l1.head,n);
		l1.head=l1.reverse(l1.head);
		Assert.assertFalse(15000 == l1.head.sal);
	}
	
	@Test
	public void Sortest2() {
		QuickSortLinkedList l1 = new QuickSortLinkedList();
		l1.addNode(15000, 26);
		l1.addNode(14500, 24);
		l1.addNode(12000, 20);
		l1.addNode(20000, 24);
		l1.addNode(12000, 22);
		l1.addNode(22000, 22);
		QuickSortLinkedList.Node n=l1.head;
		while(n.next!=null)
			n=n.next;
		l1.sort(l1.head,n);
		l1.head=l1.reverse(l1.head);
		Assert.assertTrue(22 == l1.head.age);
	}
	

}
