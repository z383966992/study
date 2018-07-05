package stack;

import java.util.concurrent.atomic.AtomicReference;
/**
 * 多线程并发stack 非阻塞栈
 * @author Administrator
 * @param <E>
 */
public class ConcurrentStack<E> {
	AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

	/**
	 * compareAndSet(V expect, V update) 
	 * 如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值
	 * @param item
	 */
	public void push(E item) {
		Node<E> newHead = new Node<E>(item);
		Node<E> oldHead;
		do {
			oldHead = top.get();
			newHead.next = oldHead;
		} while (!top.compareAndSet(oldHead, newHead));
	}
	
	public E pop() {
		Node<E> oldHead;
		Node<E> newHead;
		do {
			oldHead = top.get();
			if(oldHead == null)
				return null;
			newHead = oldHead.next;
		} while (!top.compareAndSet(oldHead, newHead));
		return oldHead.item;
	}
	
	private static class Node<E> {
		public final E item;
		public Node<E> next;
		public Node(E item) {
			this.item = item;
		}
	}
	
	public static void main(String[] args) {
		ConcurrentStack<Integer> cs = new ConcurrentStack<Integer>();
		cs.push(1);
		cs.push(2);
		cs.push(3);
		cs.push(4);
		cs.push(5);
		cs.push(6);
		cs.push(7);
		cs.push(8);
		cs.push(9);
		
		System.out.println(cs.pop());
		System.out.println(cs.pop());
	}
}
