

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test class
		System.out.println("Ciao");
		DataStr.LinkedList<Integer> list = new DataStr.LinkedList<Integer>();
		list.add(5);
		list.add(42);
		list.add(12);
		list.print();
		for(int x: list){
			System.out.println(x);
		}
		/*
		list.remove(2);
		list.print();
		list.remove(0);
		list.print();
		DataStr.Stack<String> stack = new DataStr.Stack<String>();
		stack.append("bye");
		System.out.println(stack.peek());
		stack.append("ciao");
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		stack.peek();*/
	}

}
