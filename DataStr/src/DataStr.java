
import java.lang.IndexOutOfBoundsException;

public class DataStr {

	private static class Node<T>{
		private T  	    data;
		private Node<T> next;
		
		Node(T data){
			this.data = data;
			this.next = null;
		}
		
		Node(T data, Node<T> next){
			this.data = data;
			this.next = next;
		}
		
		public void setData(T data){
			this.data = data;
		}
		
		public void setNext(Node<T> next){
			this.next = next;
		}
		
		public T getData(){
			return this.data;
		}
		
		public Node<T> getNext(){
			return this.next;
		}
	}
	
	public static class LinkedList<T>{
		private Node<T> head = null;
		private int length = 0;
		
		public int length(){
			return this.length;
		}
		
		public void add(T data){
			/*
			 * Add at beginning
			 */
			Node<T> newNode = new Node<T>(data, head);
			this.head = newNode;
			this.length++;
		}
		
		public void add(T data, int pos){
			// Add at position pos
		}
		
		public T remove(){
			// Remove at beginning
			if(this.length()==0){
				throw new IndexOutOfBoundsException("Cannot remove from empty list");
			}
			T data = head.getData();
			this.head = head.getNext();
			this.length --;
			return data;
		}
		
		public T remove(int pos){
			// Remove at position pos
			if(this.length()<=pos||pos<0){
				throw new IndexOutOfBoundsException("pos index out of bounds");
			}
			if(pos == 0){
				T data = this.remove();
				return data;
			}
			Node<T> current = getNodeAtPos(pos-1);
			T data = current.getNext().getData();
			current.setNext(current.getNext().getNext());
			this.length --;
			return data;
		}

		private Node<T> getNodeAtPos(int pos){
			// Get node at position pos
			if(pos>=length||pos<0){
				throw new IndexOutOfBoundsException("");
			}
			Node<T> current = head;
			for(int i = 0; i < pos; i++){
				current = current.getNext();
			}
			return current;
		}

		public T getAtPos(int pos){
			// return data at position pos
			return getNodeAtPos(pos).getData();
		}
		
		public void print(){
			// Print elements of list
			Node<T> current = head;
			while(current!=null){
				System.out.print(current.getData());
				System.out.print(" -> ");
				current = current.getNext();
			}
			System.out.println("Null");
		}
		
	}
	
	public static class Stack<T>{
		private final LinkedList<T> list = new LinkedList<T>();
		
		public void append(T data){
			list.add(data);
		}
		
		public T pop(){
			try{
				return list.remove();
			}
			catch(IndexOutOfBoundsException err){
				throw new IndexOutOfBoundsException("The stack is empy");
			}
			
		}
		
		public T peek(){
			try{
				return list.getAtPos(0);
			}
			catch(IndexOutOfBoundsException err){
				throw new IndexOutOfBoundsException("Stack is empty");
			}
		}
		
	}
	
	public static class Queue<T>{
		
	}
	
}
