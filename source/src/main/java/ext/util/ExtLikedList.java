package ext.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ExtLikedList<E> implements List<E> {

	Node<E> first;
	Node<E> last;
	int size;

	void likeFirst(E element) {
//		Node<E> f = first;
		Node<E> node = new Node<E>(null, element, first);
		if (first != null)
			first.prev = node;
		else
			last = node;
		first = node;
		size++;
	}

	E unlikeFirst() {
		if (first == null)
			throw new NoSuchElementException("first 不存在");
		if (last == first)
			last = null;
		E oldValue = first.item;
		first = first.next;
		size--;
		return oldValue;
	}

	E unlikeLast() {
		if (last == null)
			throw new NoSuchElementException("last 不存在");
		if (last == first)
			first = null;
		E oldValue = last.item;
		last = last.prev;
		size--;
		return oldValue;
	}

	void likeLast(E element) {
		Node<E> node = new Node<E>(last, element, null);
		if (last != null)
			last.next = node;
		else
			first = node;
		last = node;
		size++;
	}

	void likeBefore(E element, Node<E> node) {
		Node<E> newNode = new Node<>(node.prev, element, node);
		if (node.prev == null)
			first = newNode;
		else
			node.prev.next = newNode;
		node.prev = newNode;
		size++;
	}

	E unlikeBefore(Node<E> node) {
		Node<E> next = node.next;
		node.next.prev = node.prev;
		if (node.prev == null)
			first = next;
		else
			node.prev.next = next;
		size--;
		E value = node.item;
		node.item = null;
		return value;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(E e) {
		likeFirst(e);
		return true;
	}

	@Override
	public boolean remove(Object o) {

		Node<E> temp = null;
		if (o == null) {
			for (Node<E> node = first; node != null; node = node.next)
				if (node.item == null) {
					temp = node;
					break;
				}
		} else {
			for (Node<E> node = first; node != null; node = node.next)
				if (o.equals(node.item)) {
					temp = node;
					break;
				}
		}
		return removeNode(temp);
	}

	boolean removeNode(Node<E> node) {
		if (node == null)
			return false;
		if (node.next == null)
			return unlikeLast() != null;
		if (node.prev == null)
			return unlikeFirst() != null;

		node.prev.next = node.next;
		node.next.prev = node.prev;
		node.item = null;
		size--;
		return true;

	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public E get(int index) {
		checkIndex(index);
		return node(index).item;
	}

	void checkIndex(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("下标越界: " + index);
	}

	Node<E> node(int index) {
		Node<E> node;
		if (index > (size >> 1)) {
			node = last;
			for (int i = size - 1; i > index; i--)
				node = node.prev;
		} else {
			node = first;
			for (int i = 0; i < index; i++)
				node = node.next;
		}
		return node;

	}

	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E oldValue = node.item;
		node.item = element;
		return oldValue;
	}

	@Override
	public void add(int index, E element) {
		checkIndex(index);
		if (index == size)
			likeLast(element);
		else
			likeBefore(element, node(index));
	}

	@Override
	public E remove(int index) {
		checkIndex(index);
		if (index == 0)
			return unlikeFirst();
		else
			return unlikeBefore(node(index));
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Node<E> n = first; n != null; n = n.next, index++)
				if (n.item == o)
					return index;
		} else {
			for (Node<E> n = first; n != null; n = n.next, index++)
				if (o.equals(n.item))
					return index;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = size - 1;
		if (o == null) {
			for (Node<E> n = last; n != null; n = n.prev, index--)
				if (n.item == null)
					return index;
		} else {
			for (Node<E> n = last; n != null; n = n.prev, index--)
				if (o.equals(n.item))
					return index;
		}

		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("first：");
		for (Node<E> node = first; node != null; node = node.next)
			builder.append(node.item).append(node != last ? " -> " : "");

		builder.append("\r\n").append(" last：");
		for (Node<E> node = last; node != null; node = node.prev)
			builder.append(node.item).append(node != first ? " -> " : "");

		return builder.toString();
	}

	private static class Node<E> {
		private E item;
		private Node<E> prev;
		private Node<E> next;

		Node(Node<E> prev, E item, Node<E> next) {
			this.prev = prev;
			this.item = item;
			this.next = next;
		}
	}

}
