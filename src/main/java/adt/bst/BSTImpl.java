package adt.bst;

import java.util.ArrayList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}
	
	private int height(BSTNode<T> node) {
		int height = -1;
		if (!node.isEmpty()) {
			height = 1+ Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));
		}
		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> resp = new BSTNode<T>();
		
		if (element != null) {
			if (!isEmpty()) {
				resp = search(this.root, element);
			}
		}
		
		return resp;
	}
	
	private BSTNode<T> search(BSTNode<T> node, T element) {
		BSTNode<T> aux = new BSTNode<T>();
		
		if (!node.isEmpty()) {
			if (node.getData().equals(element)) {
				aux = node;
			} else if (node.getData().compareTo(element) < 0) {
				aux = search((BSTNode<T>) node.getRight(), element);
			} else {
				aux = search((BSTNode<T>) node.getLeft(), element);
			}
		}
		
		return aux;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			insert(this.root, element);
		}
	}
	
	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setRight(new BSTNode.Builder<T>().data(null).right(null).left(null).parent(node).build());
			node.setLeft(new BSTNode.Builder<T>().data(null).right(null).left(null).parent(node).build());
		} else {
			if (node.getData().compareTo(element) > 0) {
				insert((BSTNode<T>) node.getLeft(), element);
			} else {
				insert((BSTNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> resp = null;
		if (!isEmpty()) {
			resp = maximum(this.root);
		}
		
		return resp;
	}
	
	private BSTNode<T> maximum(BSTNode<T> node) {
		BSTNode<T> resp = node;
		if (!node.getRight().isEmpty()) {
			resp = maximum((BSTNode<T>) node.getRight());
		}
		return resp;
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> resp = null;
		if (!isEmpty()) {
			resp = minimum(this.root);
		}
		
		return resp;
	}
	
	private BSTNode<T> minimum(BSTNode<T> node) {
		BSTNode<T> resp = node;
		if (!node.getLeft().isEmpty()) {
			resp = minimum((BSTNode<T>) node.getLeft());
		}
		return resp;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> resp = search(element);
		
		if (element != null && !this.root.isEmpty() && !resp.isEmpty()) {
			if (resp.getRight().isEmpty()) {
				resp = sucessor(resp);
			} else {
				resp = minimum((BSTNode<T>) resp.getRight());
			}
		} else {
			resp = null;
		}
		
		return resp;
	}
	
	private BSTNode<T> sucessor(BSTNode<T> node) {
		BSTNode<T> sucessor = (BSTNode<T>) node.getParent();
		
		if (node.getParent() != null) {
			if (!sucessor.isEmpty() && sucessor.getRight().equals(node)) {
				sucessor = sucessor((BSTNode<T>) node.getParent());
			}
		}
		
		return sucessor;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> resp = search(element);
		
		if (element != null && this.root.isEmpty() && !resp.isEmpty()) {
			if (resp.getLeft().isEmpty()) {
				resp = predecessor(resp);
			} else {
				resp = maximum((BSTNode<T>) resp.getLeft());
			}
		} else {
			resp = null;
		}
		
		return resp;
	}
	
	private BSTNode<T> predecessor(BSTNode<T> node) {
		BSTNode<T> resp = (BSTNode<T>) node.getParent();
		
		if (node.getParent() != null) {
			if (!resp.isEmpty() && resp.getLeft().equals(node)) {
				resp = predecessor((BSTNode<T>) node.getParent());
			}
		}
		
		return resp;
	}

	@Override
	public void remove(T element) {
		if (element != null && !this.isEmpty() && !this.search(element).isEmpty()) {
			BSTNode<T> node = this.search(element);
			this.remove(node, element);
		}
	}
	
	private void remove(BSTNode<T> node, T element) {
		if (!node.getRight().isEmpty() && !node.getLeft().isEmpty()) {
			BSTNode<T> sucessor = sucessor(element);
			node.setData(sucessor.getData());
			sucessor.setData(element);
			remove(sucessor, sucessor.getData());
		} else if (!node.getRight().isEmpty() && node.getLeft().isEmpty()) {
			if (node.equals(this.root)) {
				this.root = (BSTNode<T>) node.getRight();
				this.root.setParent(new BSTNode<T>());
			} else {
				node.getRight().setParent(node.getParent());
				if (node.getParent().getData().compareTo(node.getRight().getData()) > 0) {
					node.getParent().setLeft(node.getRight());
				} else {
					node.getParent().setRight(node.getRight());
				}
			}
		} else if (!node.getLeft().isEmpty() && node.getRight().isEmpty()){
			if(!node.equals(this.root)){
				node.getLeft().setParent(node.getParent());
				if(node.getParent().getData().compareTo(node.getLeft().getData()) > 0) {
					node.getParent().setLeft(node.getLeft());
				}
				else {
					node.getParent().setRight(node.getLeft());
				}
				
			} else {
				this.root = (BSTNode<T>) node.getLeft();
				this.root.setParent(new BSTNode<T>());
			}
		} else if (node.isLeaf()) {
			if(node.equals(this.root)) {
				this.root = new BSTNode<T>();
			} else if (node.getParent().getLeft().equals(node)) {
				node.getParent().setLeft(new BSTNode<T>());
			}
			else if (node.getParent().getRight().equals(node)) {
				node.getParent().setRight(new BSTNode<T>());
			}
	}
	}

	@Override
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[size()];
		if(!isEmpty()) {
			ArrayList<T> list = new ArrayList<T>();
			list = preOrder(this.root,list);
			list.toArray(array);	
		}
		return array;
	}
	
	private ArrayList<T> preOrder(BSTNode<T> node, ArrayList<T> list){
		if(!node.isEmpty()) {
			list.add(node.getData());
			preOrder((BSTNode<T>)node.getLeft(),list);
			preOrder((BSTNode<T>)node.getRight(),list);
		}
		return list;
	}

	@Override
	public T[] order() {
		T[] array = (T[]) new Comparable[size()];
		
		if(!isEmpty()) {
			ArrayList<T> list = new ArrayList<T>();
			list = order(this.root,list);
			list.toArray(array);
		}
		
		return array;
	}
	
	private ArrayList<T> order(BSTNode<T> node, ArrayList<T> list){
		if(!node.isEmpty()) {
			order((BSTNode<T>) node.getLeft(),list);
			list.add(node.getData());
			order((BSTNode<T>)node.getRight(),list);
		}
		return list;
	}

	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[size()];
		
		if(!isEmpty()) {
			ArrayList<T> list = new ArrayList<T>();
			list = postOrder(this.root,list);
			list.toArray(array);
		}
		
		return array;
	}
	
	private ArrayList<T> postOrder(BSTNode<T> node, ArrayList<T> list){
		if(!node.isEmpty()) {
			list.add(node.getData());
			postOrder((BSTNode<T>) node.getLeft(),list);
			postOrder((BSTNode<T>)node.getRight(),list);
		}
		
		return list;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
