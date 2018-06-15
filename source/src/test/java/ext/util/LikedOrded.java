package ext.util;

public class LikedOrded {

	public static void main(String[] args) {
//		List<String> list = new List<>();
//		list.add("a");
//		list.add("b");
//		list.add("c");
//		list.add("d");
//		System.err.println(list.first());
//		System.err.println(likedOrded(list.first()));

//		testExtArrayList();

//		testExtLinkedList();

		testExtHashMap();

//		System.err.println(-1 >>> 1);
//		System.err.println(Integer.toBinaryString(-1));
//		System.err.println(Integer.toBinaryString(-1 >>> 1));
//		System.err.println(Integer.toBinaryString(1));
	}

	public static void testExtHashMap() {
		ExtHashMap<String, Object> map = new ExtHashMap<>(64);
		map.put(null, null);
		map.put("0", null);
		map.put("a", "x");
		map.put("b", "x");
		map.put("c", "x");
		map.put("d", "x");
		map.put("1", "x");
		System.err.println(map);
		System.err.println();

		map.put("12", "x");

		System.err.println(map);
		System.err.println();
		map.put("23", "x");
		map.put("34", "x");
		map.put("45", "x");
		map.put("56", "x");
		map.put("67", "x");
		map.put("78", "x");
		map.put(null, 1);
		System.err.println(map);
	}

	public static void testExtLinkedList() {
		ExtLikedList<String> list = new ExtLikedList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		System.err.println(list);
		System.err.println();

		list.remove("b");
		System.err.println(list);
		System.err.println();

		list.add(2, "b");
		System.err.println(list);

	}

	public static void testExtArrayList() {
		ExtArrayList<String> list = new ExtArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		list.add("g");
		list.add("h");
		list.add("i");
		list.add("j");
		System.err.println(list);
		list.add("k");
		System.err.println(list);
		list.remove("b");
		System.err.println(list);
		list.remove(2);
		System.err.println(list);
		list.add(1, "b");
		System.err.println(list);
	}

	public static <T> Node<T> likedOrded(Node<T> node) {

		Node<T> head = null;
		Node<T> temp = null;
		while (node != null) {
			temp = node.next();
			node.setNext(head);
			head = node;
			node = temp;
		}
		return head;

	}

	public static class List<T> {
		private Node<T> first;
		private Node<T> last;
		private int size;

		public void add(T data) {
			if (data == null)
				return;
			Node<T> node = new Node<>();
			node.setData(data);
			if (first == null)
				first = node;
			if (last != null)
				last.setNext(node);
			last = node;
			size++;
		}

		public Node<T> first() {
			return first;
		}

		public Node<T> last() {
			return last;
		}

	}

	public static class Node<T> {

		private T data;
		private Node<T> next;

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Node<T> next() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(data);
			Node<T> temp = next;
			while (temp != null) {
				builder.append(" -> ").append(temp.data);
				temp = temp.next;
			}
			return builder.toString();
		}

	}
}
