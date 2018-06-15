package ext.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ExtArrayList<E> implements List<E> {

	private Object[] elementData;

	private static Object[] DEFAULT_ARRAY = {};
	private static Object[] EMPTY_ARRAY = {};
	private static int DEFAULT_SIZE = 10;

	private int size;

	public ExtArrayList() {
		elementData = DEFAULT_ARRAY;
	}

	public ExtArrayList(int size) {
		if (size > 0)
			elementData = new Object[size];
		else if (size == 0)
			elementData = EMPTY_ARRAY;
		else
			throw new IndexOutOfBoundsException("默认大小错误: " + size);
	}

	public ExtArrayList(List<E> datas) {
		Object[] o = datas.toArray();
		if (o != null)
			elementData = o;
		else
			elementData = EMPTY_ARRAY;
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

		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(E e) {
		if (elementData.length < size + 1)
			grow();
		elementData[size++] = e;
		return true;
	}

	void grow() {
		int minCapacity = size + 1;
		if (elementData == DEFAULT_ARRAY)
			minCapacity = Math.max(DEFAULT_SIZE, minCapacity);
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);

		if (newCapacity < minCapacity)
			newCapacity = minCapacity;

		elementData = Arrays.copyOf(elementData, newCapacity);

	}

	@Override
	public boolean remove(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (elementData[i] == null) {
					int length = size - i - 1;
					System.arraycopy(elementData, i + 1, elementData, i, length);
					elementData[--size] = null;
					return true;
				}
		} else {
			for (int i = 0; i < size; i++)
				if (o.equals(elementData[i])) {
					int length = size - i - 1;
					System.arraycopy(elementData, i + 1, elementData, i, length);
					elementData[--size] = null;
					return true;
				}
		}
		return false;
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

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		return (E) elementData[index];
	}

	@SuppressWarnings("unchecked")
	@Override
	public E set(int index, E element) {
		E oldData = (E) (elementData[index]);
		elementData[index] = element;
		return oldData;
	}

	@Override
	public void add(int index, E element) {
		if (index > size)
			throw new IndexOutOfBoundsException("下标越界: " + index);
		grow();
		int length = size - index - 1;
		System.arraycopy(elementData, index, elementData, index + 1, length);
		elementData[index] = element;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(int index) {
		Object oldData = elementData[index];
		int length = size - index - 1;
		System.arraycopy(elementData, index + 1, elementData, index, length);
		elementData[--size] = null;
		return (E) oldData;
	}

	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (elementData[i] == null)
					return i;

		} else {
			for (int i = 0; i < size; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;

	}

	@Override
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size - 1; i >= 0; i--)
				if (elementData[i] == null)
					return i;

		} else {
			for (int i = size - 1; i >= 0; i--)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
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
		for (int i = 0; i < elementData.length; i++)
			builder.append("elementData[").append(i).append("]: ").append(elementData[i]).append("\r\n");

		return builder.toString();
	}
}
