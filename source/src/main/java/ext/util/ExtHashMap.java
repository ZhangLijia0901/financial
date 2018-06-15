package ext.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ExtHashMap<K, V> implements Map<K, V> {
	public ExtHashMap() {
	}

	@SuppressWarnings("unchecked")
	public ExtHashMap(int initialCapacity) {
		if (initialCapacity > 0)
			tables = new Node[initialCapacity];
	}

	Node<K, V>[] tables;
	int size;

	int DEFAULT_INITIAL_CAPACITY = 1 << 4;

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public V get(Object key) {
		Node<K, V> node = getNode(key, tables[hash(key, tables.length)]);
		return node == null ? null : node.value;
	}

	private Node<K, V> getNode(Object key, Node<K, V> node) {
		while (node != null)
			if (Objects.equals(node.key, key))
				return node;
			else
				node = node.next;
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		if (tables == null)
			tables = new Node[DEFAULT_INITIAL_CAPACITY];
		if (size > (tables.length * 0.75))
			resise();

		int hash = hash(key, tables.length);

		Node<K, V> oldNode = tables[hash];
		Node<K, V> newNode = oldNode;
		while (newNode != null)
			if (Objects.equals(newNode.key, key))
				return newNode.setValue(value);
			else
				newNode = newNode.next;

		tables[hash] = new Node<>(key, value, oldNode);
		size++;
		return value;
	}

	int hash(Object key, int length) {
		return key == null ? 0 : key.hashCode() % length;
	}

	@Override
	public V remove(Object key) {
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {

	}

	@Override
	public void clear() {

	}

	@Override
	public Set<K> keySet() {
		return null;
	}

	@Override
	public Collection<V> values() {
		return null;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return null;
	}

	@SuppressWarnings("unchecked")
	void resise() {
		Node<K, V>[] newTables = new Node[tables.length << 1];

		for (int i = 0; i < tables.length; i++) {
			Node<K, V> node = tables[i];
			tables[i] = null;
			while (node != null) {
				int hash = hash(node.key, newTables.length);
				Node<K, V> nodeNext = node.next;
				node.next = newTables[hash];
				newTables[hash] = node;
				node = nodeNext;
			}
		}
		this.tables = newTables;
	}

	static class Node<K, V> implements Entry<K, V> {
		int hash;
		K key;
		V value;
		Node<K, V> next;

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Node(K key, V val, Node<K, V> next) {
			this.key = key;
			this.value = val;
			this.next = next;

		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			Node<K, V> node = this;
			builder.append("[");
			while (node != null) {
				builder.append("{").append(node.key).append(":").append(node.value).append("}");
				node = node.next;
				if (node != null)
					builder.append(" -> ");
			}
			builder.append("]");
			return builder.toString();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tables.length; i++)
			builder.append("talbes[").append(i).append("]: ").append(tables[i]).append("\r\n");

		return builder.toString();
	}

}
