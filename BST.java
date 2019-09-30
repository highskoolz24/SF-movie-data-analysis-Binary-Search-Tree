package project6;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * This class enables user to make Binary Search Tree, enables to use iterators,
 * and provides methods for better usage
 * @author Ji Hwan Valentine Choi
 *
 */

public class BST<E extends Comparable<E>> implements Collection<E>, Iterable<E>, Cloneable{

	//Variables consisting the tree
	private BSTNode<E> root=null;
	private int size = 0;
	//Boolean used in remove method
	private boolean found;
	
	/**
	 * Constructor for class BST 
	 */
	public BST(){
	}
	
	/**
	 * This nested private class provides node for the BST class 
	 * with strong encapsulation, and shield the details from the users 
	 */
	private class BSTNode<E>{
		//Variables for Node; element for element of the node, left for left node of the current
		//right for right node of the current
		private E element; 	
		private BSTNode<E> left;
		private BSTNode<E> right;
		
		/**
		 * Constructor for class Node
		 * @param e element of the node 
		 */
		public BSTNode(E e) {
			this.element = e;
		}
		
		/**
		 * Constructor for class Node
		 * @param e element of the node
		 * @param left left of the node
		 * @param right right of the node 
		 */
		public BSTNode(E e, BSTNode<E> left, BSTNode<E> right) {
			this.element = e;
			this.left = left;
			this.right = right;
		}
		
		/**
		 * Enables to get private variable 'element'
		 * @return element element of the node
		 */
		public E getElement() {return element;}
	}

	/**
	 * This nested private class provides inorder iterator for the BST class 
	 * to easily iterate through the BST
	 */
	private class inorderItr implements Iterator<E>{

		//Create a stack to store the elements
		Stack<BSTNode<E>> stack = new Stack<BSTNode<E>>();
		
		/**
		 * Constructor for the iterator
		 * @param root of the BST 
		 */
		private inorderItr (BSTNode<E> root) {
			leftChildStack(root);
		}
		
		/**
		 * Pushes the node in the stack going to the left of the node
		 * @param node current node
		 */
		private void leftChildStack(BSTNode<E> node) {
			//if node is not null, push the node in the stack and go left
			while(node != null) {
				stack.push(node);
				node=node.left;
			}
		}
		
		/**
		 * Overrides the hasNext method
		 * @return true if the current node has next
		 * @return false if not
		 */
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		/**
		 * Overrides the next method
		 * Proceeds to the next node and return the element of the node (detail below) 
		 * @return cur.element element of the current node before using this method
		 * @throws NoSuchElementException when hasNext is false
		 */
		@Override
		public E next() {
			//Check if hasNext is false
			if(!hasNext()) 
				throw new NoSuchElementException();
			//Pop the current node
			BSTNode<E> cur = stack.pop();
			//Check if there is right, and if there is store the node going to the left
			leftChildStack(cur.right);
			return cur.element;
		}
	}
	
	/**
	 * This nested private class provides preorder iterator for the BST class 
	 * to easily iterate through the BST
	 */
	private class preorderItr implements Iterator<E>{
		
		//Create a stack to store the elements
		Stack<BSTNode<E>> stack = new Stack<BSTNode<E>>();
		
		/**
		 * Constructor for the iterator
		 * @param root of the BST 
		 */
		private preorderItr(BSTNode<E> root){
			//store the root first in the stack
			if(root!=null)
				stack.push(root);
		}

		/**
		 * Overrides the hasNext method
		 * @return true if the current node has next
		 * @return false if not
		 */
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		/**
		 * Overrides the next method
		 * Proceeds to the next node and return the element of the node (detail below) 
		 * @return cur.element element of the current node before using this method
		 * @throws NoSuchElementException when hasNext is false
		 */
		@Override
		public E next() {
			//Check if hasNext is false
			if(!hasNext()) 
				throw new NoSuchElementException();
			//Pop the current node
			BSTNode<E> cur = stack.pop();
			//Store right of the node, if exists
			if(cur.right!=null) 
				stack.push(cur.right);
			//Store left of the node, if exists
			if(cur.left!=null) {
				stack.push(cur.left);
			}
			return cur.element;
		}
	}
	
	/**
	 * This nested private class provides postorder iterator for the BST class 
	 * to easily iterate through the BST
	 */
	private class postorderItr implements Iterator<E>{
		
		//Create a stack to store the elements
		Stack<BSTNode<E>> stack = new Stack<BSTNode<E>>();
		
		/**
		 * Constructor for the iterator
		 * @param root of the BST 
		 */
		private postorderItr(BSTNode<E> root){
			nextStack(root);
		}
		
		/**
		 * Pushes the node in the stack
		 * If there is left, go leftward
		 * If not, go rightward
		 * @param node current node
		 */
		public void nextStack(BSTNode<E> node) {
			while(node!=null) {
				stack.push(node);
				if(node.left!=null) 
					node = node.left;
				else
					node = node.right;
			}
		}
		
		/**
		 * Overrides the hasNext method
		 * @return true if the current node has next
		 * @return false if not
		 */
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		/**
		 * Overrides the next method
		 * Proceeds to the next node and return the element of the node (detail below) 
		 * @return cur.element element of the current node before using this method
		 * @throws NoSuchElementException when hasNext is false
		 */
		@Override
		public E next() {
			//Check if hasNext is false
			if(!hasNext()) 
				throw new NoSuchElementException();
			//Pop the current node
			BSTNode<E> cur = stack.pop();
			//Check if the stack is empty
			if (!stack.isEmpty()) {
				BSTNode<E> curTop = stack.peek();
				//Check if the current node is same as the the left of the top of the stack
				if(cur.equals(curTop.left))
					//Do the nextStack with the right of the top of the stack, if so
					nextStack(curTop.right);
			}
			return cur.element;
		}
	}
	
	/**
	 * Gets the node that stores the param and return its element, if exists
	 * 
	 * @param value value being searched
	 * @return current.element element of the searched node
	 * @return null when param or root is null, or the class of param and root.element is not the same
	 * 		        and when there is no match found
	 */
	public E get(E value) {
		//Check if the param or root is null
		if (value == null||this.root==null)
			return null;
		//Check if the class of param and root.element have the same class
		if (value.getClass()!=this.root.element.getClass())
			return null;
		//Calls the helper method to find the node that is storing the same value with param
		BSTNode<E> tmp= recGet(value,this.root);
		//Check if there is no match found
		if (tmp==null)
			return null;
		return tmp.element;
	}
	
	/**
	 * Recursive Helper method for get method 
	 * Check if the node contains the value. If not, by comparing the value, check right or left recursively
	 * 
	 * @param value value being searched
	 * @param node current node
	 * @return node if the node contains the value
	 * @return null if node is null
	 * @return recGet(value, node.left) if the value is smaller than node.element
	 * @return recGet(value, node.right) if the value is bigger than node.element
	 */
	private BSTNode<E> recGet(E value, BSTNode<E> node){
		//Check if the node is null
		if (node == null)
			return null;
		//Check if the node contains the value
		if (node.element.equals(value))
			return node;
		//Check if the value is smaller or bigger than node.element
		else if (value.compareTo(node.element)<0) 
			//Call method recursively with left node if the value is smaller
			return recGet(value, node.left);
		else
			//Call method recursively with right node if the value is bigger
			return recGet(value, node.right);
	}
	
	/**
	 * Simple representation of Tree class in string
	 * @return the element stored in the tree in a formatted version
	 */
	public String toString() {
		String tmp = "[";
		//Make an iterator of the tree
		Iterator<E> inorder = this.iterator();
		//Check if the iterator hasNext
		while(inorder.hasNext()) {
			//Stack each element, if exist
			tmp = tmp + String.valueOf(inorder.next());
			if(inorder.hasNext())
				tmp+=", ";
		}
		return tmp+"]";
		
	}
	
	/* *
	* Produces tree like string representation of this BST .
	* @author method from class
	* @return string containing tree - like representation of this BST .
	*/
	public String toStringTreeFormat() {
		StringBuilder s = new StringBuilder();
		preOrderPrint(root, 0, s);
		return s.toString();
	}
	
	/*
	* Uses pre - order traversal to produce a tree - like representation of this BST .
	* @author method from class
	* @param tree the root of the current subtree
	* @param level level ( depth ) of the current recursive call in the tree to
	* determine the indentation of each item
	* @param output the string that accumulated the string representation of this
	* BST
	*/
	private void preOrderPrint (BSTNode<E> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.element);
			preOrderPrint(tree.left,level+1, output);
			preOrderPrint(tree.right,level+1, output);
		}
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}
	
	/**
	 * Make a new preorder iterator
	 * 
	 * @return new preorder iterator
	 */
	public Iterator<E> preorderIterator(){
		return new preorderItr(root);
	}
	
	/**
	 * Make a new postorder iterator
	 * 
	 * @return new postorder iterator
	 */
	public Iterator<E> postorderIterator(){
		return new postorderItr(root);
	}
	
	/**
	 * Returns the least element greater than or equal to the given value
	 * 
	 * @param e value being compared
	 * @return tmp.element the least element in the tree greater than or equal to the value 
	 * @return null when root is null or no ceiling found
	 * @throws NullPointerException if param is null
	 * @throws ClassCastException if the class of param and root.element is not the same
	 */
	public E ceiling (E e) {
		//Check if param is null
		if (e == null)
			throw new NullPointerException();
		//Check if root is null
		if(this.root==null)
			return null;
		//Check if the class of param and root.element is not the same
		if (e.getClass()!=this.root.element.getClass())
			throw new ClassCastException();
		BSTNode<E> tmpCeiling=null;
		//Call the recursive helper method to find the value
		BSTNode<E> tmp = recCeiling(e, root, tmpCeiling);
		//Check if there is no ceiling found
		if (tmp==null)
			return null;
		else
			return tmp.element;
	}
	
	/**
	 * Recursive Helper method for ceiling method 
	 * Check if node.element is least value which is greater than or equal to the value. 
	 * If not equal, by comparing the value, check right or left recursively to find adequate ceiling
	 * 
	 * @param value value being searched
	 * @param node current node
	 * @param tmpCeiling node.element that is best qualified as ceiling
	 * @return node if node.element is equal to the value
	 * @return null if node is null and no tmpCeiling
	 * @return tmpCeiling if node is null and has tmpCeiling
	 * @return recCeiling(value, node.left, tmpCeiling) if the value is smaller than node.element
	 * @return recCeiling(value, node.right, tmpCeiling) if the value is bigger or equal to node.element
	 */
	private BSTNode<E> recCeiling(E value, BSTNode<E> node, BSTNode<E> tmpCeiling){
		//Check if node and tmpCeiling is null
		if (node==null && tmpCeiling==null)
			return null;
		//Check if node is null and tmpCeiling is not null
		else if(node==null && tmpCeiling!=null)
			//return most qualified ceiling
			return tmpCeiling;
		//Check if node.element is equal to the value
		if (node.element.equals(value))
			return node;
		//Check if value is smaller than node.element
		if (value.compareTo(node.element)<0) { 
			//Store the node as temporary least value ceiling
			tmpCeiling = node;
			//Call the method recursively with the node with smaller value
			return recCeiling(value, node.left, tmpCeiling);
		}
		else 
			return recCeiling(value, node.right, tmpCeiling);
	}
	
	/**
	 * Overrides the clone method
	 * Make a shallow copy of a BST 
	 * 
	 * @return this with null root when root is null
	 * @return shallowCopy a shallow copied BST
	 */
	@Override
	public Object clone() { 
		//Check if root is null, and if so, return the BST with null root
		if(this.root==null)
			return this;
		//Use iterator to go through all the element and shallow copy to the new tree
		Iterator<E> copyIter = this.iterator();
		BST<E> shallowCopy = new BST<E>();
		while(copyIter.hasNext()) {
			shallowCopy.add(copyIter.next());
		}
		return shallowCopy;
	}		
	
	/**
	 * Returns the lowest element in the BST
	 * 
	 * @return tmp.element the lowest element in the tree  
	 * @throws NoSuchElementException when root is null
	 */
	public E first() {
		//Check if root is null
		if(this.root==null)
			throw new NoSuchElementException();
		//Call the helper method to find the lowest element
		BSTNode<E> tmp = recFirst(root);
		return tmp.element;
	}
	
	/**
	 * Recursive Helper method for first method 
	 * Check if there is left node to find the lowest element 
	 *
	 * @param node current nod
	 * @return node if node is the rightmost node
	 * @return recFirst(node.left) if the left node exists
	 */
	private BSTNode<E> recFirst(BSTNode<E> node){
		//Check if there exists a left node
		if(node.left!=null)
			//Call the method recursively to find the leftmost node
			return recFirst(node.left);
		else
			return node;
	}
	
	/**
	 * Returns the greatest element in the tree less than or equal to the given value
	 * 
	 * @param e value being compared
	 * @return tmp.element the greatest element in the tree less than or equal to the value 
	 * @return null when root is null or no floor found
	 * @throws NullPointerException if param is null
	 * @throws ClassCastException if the class of param and root.element is not the same
	 */
	public E floor (E e) {
		//Check if param is null
		if (e == null)
			throw new NullPointerException();
		//Check if root is null
		if(this.root==null)
			return null;
		//Check if the class of param and root.element is not the same
		if (e.getClass()!=this.root.element.getClass())
			throw new ClassCastException();
		BSTNode<E> tmpFloor=null;
		//Call the helper method to find floor
		BSTNode<E> tmp = recFloor(e, root, tmpFloor);
		//Check if floor exists
		if (tmp==null)
			return null;
		else
			return tmp.element;
	}
	
	/**
	 * Recursive Helper method for floor method 
	 * Check if node.element is Greatest value which is less than or equal to the value. 
	 * If not equal, by comparing the value, check right or left recursively to find adequate floor
	 * 
	 * @param value value being searched
	 * @param node current node
	 * @param tmpFloor node.element that is best qualified as floor
	 * @return node if node.element is equal to the value
	 * @return null if node is null and no tmpFloor
	 * @return tmpFloor if node is null and has tmpFloor
	 * @return recFloor(value, node.left, tmpFloor) if the value is smaller than node.element
	 * @return recFloor(value, node.right, tmpFloor) if the value is bigger or equal to node.element
	 */
	private BSTNode<E> recFloor(E value, BSTNode<E> node, BSTNode<E> tmpFloor){
		//Check if node and tmpFloor is null
		if (node==null && tmpFloor==null)
			return null;
		//Check if node is null and tmpFloor is not null
		else if(node==null && tmpFloor!=null)
			return tmpFloor;
		//Check if node.element is equal to the value
		if (node.element.equals(value))
			return node;
		//Check if value is smaller than node.element
		if (value.compareTo(node.element)<0) 
			//Call the method recursively with the node with smaller value
			return recFloor(value, node.left, tmpFloor);
		else {
			//Store the node as temporary greatest value floor
			tmpFloor = node;
			return recFloor(value, node.right, tmpFloor);
		}
	}
	
	/**
	 * Returns the least element in the tree greater than the given value
	 * 
	 * @param e value being compared
	 * @return tmp.element the least element in the tree greater than the value 
	 * @return null when root is null or no higher found
	 * @throws NullPointerException if param is null
	 * @throws ClassCastException if the class of param and root.element is not the same
	 */
	public E higher(E e) { 
		//Check if param is null
		if (e == null)
			throw new NullPointerException();
		//Check if root is null
		if(this.root==null)
			return null;
		//Check if the class of param and root.element is not the same
		if (e.getClass()!=this.root.element.getClass())
			throw new ClassCastException();
		BSTNode<E> tmpHigher=null;
		//Call the helper method to find higher
		BSTNode<E> tmp = recHigher(e, root, tmpHigher);
		//Check if higher exists
		if (tmp==null)
			return null;
		else
			return tmp.element;
	}
	
	/**
	 * Recursive Helper method for higher method 
	 * Check if node.element is least value which is greater than the value. 
	 * If not equal, by comparing the value, check right or left recursively to find adequate higher
	 * 
	 * @param value value being searched
	 * @param node current node
	 * @param tmpHigher node.element that is best qualified as higher
	 * @return null if node is null and no tmpHigher
	 * @return tmpFloor if node is null and has tmpHigher
	 * @return recHigher(value, node.left, tmpHigher) if the value is smaller than node.element
	 * @return recHigher(value, node.right, tmpHigher) if the value is bigger or equal to node.element
	 */
	private BSTNode<E> recHigher(E value, BSTNode<E> node, BSTNode<E> tmpHigher){
		//Check if node and tmpHigher is null
		if (node==null && tmpHigher==null)
			return null;
		//Check if node is null and tmpHigher.element and value is the same
		else if(node==null && tmpHigher.element.equals(value))
			return null;
		//Check if node is null and tmpHigher exists
		else if(node==null && tmpHigher!=null)
			return tmpHigher;
		//Check if value is smaller than node.element
		if (value.compareTo(node.element)<0) {
			//Store the node as temporary least value higher
			tmpHigher = node;
			return recHigher(value, node.left, tmpHigher);
		}
		else 
			return recHigher(value, node.right, tmpHigher);
	}
	
	/**
	 * Returns the highest element in the BST
	 * 
	 * @return tmp.element the highest element in the tree  
	 * @throws NoSuchElementException when root is null
	 */
	public E last() {
		//Check if root is null
		if(this.root==null)
			throw new NoSuchElementException();
		//Call the helper method to find the highest element
		BSTNode<E> tmp = recLast(root);
		return tmp.element;
	}
	
	/**
	 * Recursive Helper method for last method 
	 * Check if there is right node to find the highest element 
	 *
	 * @param node current node
	 * @return node if node is the rightmost node
	 * @return recLast(node.right) if the right node exists
	 */
	private BSTNode<E> recLast(BSTNode<E> node){
		//Check if there exists a right node
		if(node.right!=null)
			//Call the method recursively to find the rightmost node
			return recLast(node.right);
		else
			return node;
	}
	
	/**
	 * Returns the greatest element in the tree less than the given value
	 * 
	 * @param e value being compared
	 * @return tmp.element the greatest element in the tree less than the value 
	 * @return null when root is null or no lower found
	 * @throws NullPointerException if param is null
	 * @throws ClassCastException if the class of param and root.element is not the same
	 */
	public E lower(E e) {
		//Check if param is null
		if (e == null)
			throw new NullPointerException();
		//Check if root is null
		if(this.root==null)
			return null;
		//Check if the class of param and root.element is not the same
		if (e.getClass()!=this.root.element.getClass())
			throw new ClassCastException();
		BSTNode<E> tmpLower=null;
		//Call the helper method to find lower
		BSTNode<E> tmp = recLower(e, root, tmpLower);
		//Check if lower exists
		if (tmp==null)
			return null;
		else
			return tmp.element;
	}
	
	/**
	 * Recursive Helper method for lower method 
	 * Check if node.element is greatest value which is less than the value. 
	 * If not equal, by comparing the value, check right or left recursively to find adequate higher
	 * 
	 * @param value value being searched
	 * @param node current node
	 * @param tmpHigher node.element that is best qualified as lower
	 * @return null if node is null and no tmpHLower
	 * @return tmpFloor if node is null and has tmpLower
	 * @return recLower(value, node.right, tmpLower) if the value is smaller than node.element
	 * @return recLower(value, node.left, tmpLower) if the value is bigger or equal to node.element
	 */
	private BSTNode<E> recLower(E value, BSTNode<E> node, BSTNode<E> tmpLower){
		//Check if node and tmpLower is null
		if (node==null && tmpLower==null)
			return null;
		//Check if node is null and tmpLower.element and value is the same
		else if(node==null && tmpLower.element.equals(value))
			return null;
		//Check if node is null and tmpLower exists
		else if(node==null && tmpLower!=null)
			return tmpLower;
		//Check if value is smaller than node.element
		if (value.compareTo(node.element)>0) {
			//Store the node as temporary greatest value lower
			tmpLower = node;
			return recLower(value, node.right, tmpLower);
		}
		else 
			return recLower(value, node.left, tmpLower);
	}
	
	/**
	 * Overrides add method
	 * Add a new node in the BST at a right position
	 * 
	 * @param e element of the node which is added
	 * @return true if successfully added 
	 * @return false if parameter is null
	 * @throws NullPointerException if param is null
	 * @throws ClassCastException if the class of param and root.element is not the same
	 */
	@Override
	public boolean add(E e) {
		//Check if param and the set is usable
		if (e==null)
			throw new NullPointerException();
		if(this.root!=null) {
			if (!e.getClass().isInstance(this.root.element))
				throw new ClassCastException();
			//Check if there already exists a node has param as element
			if (this.contains(e))
				return false;
		}
		//Set a node with param value as root if root is null
		if (this.root==null) {
			this.root=new BSTNode<E> (e);
			size++;
			return true;
		}
		//Call the helper method to add node in a right position
		recAdd(e, this.root);
		return true;
	}
	
	/**
	 * Recursive Helper method for add method 
	 * Add node with param e in a right position by comparing to the current node
	 * Set the position of the node in the tree by checking right or left recursively
	 * 
	 * @param e value being searched
	 * @param node current node
	 * @return node when node added successfully
	 */
	private BSTNode<E> recAdd(E e, BSTNode<E> node) {
		//Add the node with value e, if node is null
		if (node==null) {
			size++;
			node=new BSTNode<E>(e);
		}
		//Check if value is smaller than node.element
		else if (e.compareTo(node.element)<0) 
			//Recursively finds the right position and set it as left or right node if adequate  
			node.left=recAdd(e, node.left);
		else
			node.right=recAdd(e, node.right);
		return node;
	}
	
	/**
	 * Overrides addAll method
	 * @throws UnsupportedOperationException if not supported in this class
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Overrides clear method
	 * Clearing the BST making it empty
	 * Set root as null, and make size zero
	 */
	@Override
	public void clear() {
		if (this.root==null)
			return;
		else {
			this.root=null;
			size=0;
		}
	}

	/**
	 * Overrides contains method
	 * Check if the BST contains an object
	 * 
	 * @param o element being searched
	 * @return true if the list contains the parameter 
	 * @return false if not
	 * @throws NullPointerException if param is null
	 * @throws ClassCastException if the class of param and root.element is not the same
	 */
	@Override
	public boolean contains(Object o) {
		//Check is param is adequate for the method
		if(o==null)
			throw new NullPointerException();
		if(!this.root.element.getClass().equals(o.getClass()))
			throw new ClassCastException();
		//Call the helper method to see if the tree contains the param
		BSTNode<E> tmp = recContains(o, this.root);
		if(tmp==null)
			return false;
		return true;
	}
	
	/**
	 * Recursive Helper method for contains method 
	 * Find a node that contains 'o' as element
	 * Checking right or left recursively to find the node that may contain the value
	 * 
	 * @param o value being searched
	 * @param node current node
	 * @return node when node contains param 'o'
	 * @return null when not found
	 */
	private BSTNode<E> recContains(Object o, BSTNode<E> node){
		//Check node is end of the tree
		if (node==null)
			return null;
		//Check if node contains value 'o'
		if (node.element.equals(o))
			return node;
		//Compare value and node.element to choose to search to left or right
		else if(((Comparable<E>) o).compareTo(node.element)<0) 
			return recContains(o,node.left);
		else
			return recContains(o,node.right);
	}

	/**
	 * Overrides containsAll method
	 * Check if the tree contains all the element of collectin c
	 * 
	 * @param c the collection being tested
	 * @return true if the tree contains all the elements
	 * @return false if not
	 * @throws NullPointerException if param is null
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		if(c==null)
			throw new NullPointerException();
		if(this.root==null)
			return false;
		//Make a iterator for param 'c'
		Iterator<E> param = (Iterator<E>) c.iterator();
		
		//See if tree contains all the element of c
		try {
			while(param.hasNext()) {
				if(!this.contains(param.next()))
					return false;
			}
		}catch(NoSuchElementException e) {}
		return true;
	}
	
	/**
	 * Overrides equals method
	 * Check if two trees are identical
	 * 
	 * @param o object being tested
	 * @return true if the trees are identical
	 * @return false if not
	 */
	public boolean equals (Object o) {
		//Check if this and o are pointing the same object
		if(this==o) 
			return true;
		
		//Check if the parameter is null
		if(o==null)
			return false;
		
		//Check if the classes are same
		if (getClass() != o.getClass()) 
			return false;
		BST obj = (BST)o;
		
		//check if the sizes are same
		if (obj.size()!=this.size())
			return false;
		
		Iterator<E> itr1 = this.iterator();
		Iterator<E> itr2 = obj.iterator();
		
		//check each element using iterator if they are same
		while(itr1.hasNext()&&itr2.hasNext()) {
			if(!itr1.next().equals(itr2.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Overrides isEmpty method
	 * Check if the list is empty
	 * 
	 * @return true if the list is empty 
	 * @return false if not
	 */
	@Override
	public boolean isEmpty() {
		if(this.root==null && size==0)
			return true;
		else 
			return false;
	}
	/**
	 * Overrides iterator method
	 * Make a new inorder iterator
	 * 
	 * @return new inorder iterator
	 */
	@Override
	public Iterator<E> iterator() {
		return new inorderItr(root);
	}

	/**
	 * Overrides remove method
	 * Removes a node that contains the parameter object
	 * 
	 * @param o element being searched
	 * @return found which has boolean if the node is removed successfully
	 * @throws NullPointerException if param is null
	 * @throws ClassCastException if the class of param and root.element is not the same
	 */
	@Override
	public boolean remove(Object o) {
		//Check if param and BST is adequate for the method
		if(o==null)
			throw new NullPointerException();
		if(this.root==null)
			return false;
		if(!this.root.element.getClass().equals(o.getClass()))
			throw new ClassCastException();
		//Call the helper method to remove the node with element 'o'
		root = recRemove(o, this.root);
		return found;
	}
	
	/**
	 * Recursive Helper method for remove method 
	 * Find a node that contains 'o' as element
	 * Checking right or left recursively to find the node that may contain the value
	 * 
	 * @param o value being searched
	 * @param node current node
	 * @return node when node has param 'o'
	 * @return null when not found
	 */
	private BSTNode<E> recRemove(Object o, BSTNode<E> node){
		//Check if node is the end of the tree
		if (node==null) {
			found=false;
			return null;
		}
		//Remove node when found
		if (node.element.equals(o)) {
			found=true;
			node=removeNode(node);
			size--;
		}
		//Recursively search for node that has 'o' as element
		else if(((Comparable<E>) o).compareTo(node.element)<0) 
			node.left=recRemove(o,node.left);
		else
			node.right=recRemove(o,node.right);
		return node;
	}
	
	/**
	 * Helper method for remove method 
	 * Remove node and find a replaceable node to replace the node
	 * 
	 * @param node to be removed
	 * @return node.left if there is only left child or no child
	 * @return node.right if there is only right child 
	 * @return node when there is two children
	 */
	private BSTNode<E> removeNode(BSTNode<E> node){
		E tmpElement;
		//Check if the node has only right child (deals with case with no child too)
		if (node.right==null)
			return node.left;
		//Check if the node has only left child
		else if (node.left==null)
			return node.right;
		//If there are two children, find a successor to replace
		else {
			tmpElement=getSuccessor(node.right);
			node.element=tmpElement;
			//Call the recursive helper method to remove the replaced node from its original position
			node.right=recRemove(tmpElement, node.right);
			return node;
		}
	}
	
	/**
	 * Recursive Helper method for remove method 
	 * Find the successor of the node
	 * 
	 * @param right a node that becomes the root for searching the leftmost node of subtree
	 * @return right.element if the param is the leftmost node
	 * @return getSuccessor(right.left) to find the leftmost node 
	 */
	private E getSuccessor(BSTNode<E> right) {
		if (right.left==null)
			return right.element;
		else
			return getSuccessor(right.left);
	}

	/**
	 * Overrides removeAll method
	 * @throws UnsupportedOperationException if not supported in this class
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Overrides retainAll method
	 * @throws UnsupportedOperationException if not supported in this class
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Overrides size method
	 * @return size size of the linked list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Overrides toArray method
	 * Transform a tree in to an array
	 *
	 * @return copyArray Array which is transformed from the tree
	 * @return null if the tree is empty
	 */
	@Override
	public Object[] toArray() {
		//Check if tree is empty
		if (this==null || this.size==0)
			return null;
		Iterator<E> inorder = this.iterator();
		int counter=0;
		//Make an array to store the data
		E[] copyArray = (E[]) Array.newInstance(root.element.getClass(), this.size);
		try {
			//Use the iterator to store all the data
			while (inorder.hasNext()) {
				copyArray[counter]=inorder.next();
				counter++;
			}
		}catch(NoSuchElementException e) {}
		return copyArray;
	}

	/**
	 * Overrides toArray method
	 * Transform a tree in to an array with a type of the specified array
	 *
	 * @return arrayTmp array which is transform from the tree
	 * @throws NullPointerException if the parameter is null
	 * @throws ArrayStoreException if the types doesn't match
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		T[] arrayTmp;
		int counter = 0;
		//Check if param and the tree is adequate for the method
		if (a==null)
			throw new NullPointerException();
		//Return empty array if root is null
		if(this.root==null) {
			arrayTmp = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
			return arrayTmp;
		}
		if(!a.getClass().getComponentType().isInstance(this.root.element))
			throw new ArrayStoreException();
		Iterator<E> inorder = this.iterator();
		
		//if the size is smaller than array, use array 'a' to store data
		if(a.length>this.size)
			arrayTmp=a;
		else
			arrayTmp = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
		try {
			//Use iterator to store all data
			while(inorder.hasNext()) {
				arrayTmp[counter]=(T) inorder.next();
				counter++;
			}
		}catch(NoSuchElementException e) {}
		return arrayTmp;
	}
	

}
