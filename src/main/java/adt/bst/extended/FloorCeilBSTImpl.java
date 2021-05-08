package adt.bst.extended;

import adt.bst.BST;
import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;

/**
 * Note que esta classe estende sua implementacao de BST (BSTImpl).
 * Dependendo do design que voce use, sua BSTImpl precisa ter apenas funcionando
 * corretamente o metodo insert para que voce consiga testar esta classe.
 */
public class FloorCeilBSTImpl extends BSTImpl<Integer> implements FloorCeilBST {

	@Override
	public Integer floor(Integer[] array, double numero) {
		Integer floor = null;
		BST<Integer> tree = new BSTImpl<Integer>();
		this.insert(tree, array, 0);
		
		if (!tree.isEmpty()) {
			floor = floor((BSTNode<Integer>) tree.getRoot(), numero);
		}
		
		return floor;
	}
	
	private Integer floor(BTNode<Integer> node, double numero) {
		Integer resp = null;
		if (igualNode(node, numero) || isLeaf(node)) {
			resp = resp = node.getData();
		} else if (menorQueNode(numero, node)) {
			resp = floor(node.getLeft(), numero);
		} else if (isNull(node)) {
			resp = null;
		} else if (isLeaf(node) && menorQueNode(numero, node)) {
			resp = null;
		} else {
			resp = max(node, floor(node.getRight(),numero));
		}
		
		return resp;
	}

	@Override
	public Integer ceil(Integer[] array, double numero) {
		//TODO Implemente seu codigo aqui
		throw new UnsupportedOperationException("Not implemented yet!");
	}
	
	private Integer ceil(BSTNode<Integer> node, double numero) {
		Integer ceil = null;
		if (!node.isEmpty()) {
			if (node.getData() == numero) {
				ceil = node.getData();
			} else if (node.getData() < numero) {
				if (!node.getRight().isEmpty()) {
					ceil = ceil((BSTNode<Integer>) node.getRight(), numero);
				} else {
					Integer ceilLeft = null;
					if (!node.getLeft().isEmpty()) {
						ceilLeft = this.ceil((BSTNode<Integer>) node.getLeft(), numero);
					}
					
					if (ceilLeft == null) {
						ceil = node.getData();
					} else {
						ceil = ceilLeft;
					}
				}
			}
		}
		
		return ceil;
	}
	
	private boolean igualNode(BTNode<Integer> node, Double numero) {
		return numero.compareTo(Double.valueOf(node.getData())) == 0;
	}
	
	private boolean menorQueNode(Double numero, BTNode<Integer> node) {
		return numero.compareTo(Double.valueOf(node.getData())) < 0;
	}
	
	private boolean maiorQueNode(Double numero, BTNode<Integer> node) {
		return numero.compareTo(Double.valueOf(node.getData())) > 0;
	}
	
	private boolean isNull(Object element) {
		return element == null;
	}
	
	private boolean isLeaf(BTNode<Integer> node) {
		return isNull(node.getLeft()) && isNull(node.getRight());
	}
	
	private Integer max(BTNode<Integer> node, Integer num) {
		Integer resp = num;
		if (isNull(num) || node.getData().compareTo(num) > 0) {
			resp = node.getData();
		}
		
		return resp;
	}
	
	private Integer min(BTNode<Integer> node, Integer num) {
		Integer resp = num;
		if (isNull(num) || node.getData().compareTo(num) < 0) {
			resp = node.getData();
		}
		
		return resp;
	}
	
	private void insert(BST<Integer> tree, Integer[] array, int index) {
		if(index<array.length){
			tree.insert(array[index]);
			this.insert(tree,array,index+1);
		}
	}

}
