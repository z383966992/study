package bin_tree;

import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的构建和遍历
 * @author zhouliangliang1
 *
 */
public class BinTree {
	
	private List<Node> treeNodeList = new LinkedList<Node>();
	
	private static class Node {
		private Node leftChild;
		private Node rightChild;
		private int data;
		
		public Node(int data) {
			this.leftChild = null;
			this.rightChild = null;
			this.data = data;
		}
	}
	
	//把一个数组转化成二叉树
	private List<Node> createBinTree(int[] array) {
		List<Node> nodeList = new LinkedList<BinTree.Node>();
		
		//首先把数组转化成node list
		for(int i=0; i<array.length; i++) {
			nodeList.add(new Node(array[i]));
		}
		
		//按照父节点和子节点的关系建造二叉树
		//父节点的左孩子的下标是2*父节点下标+1
		//父节点的右孩子的下标是2*父节点下标+2
		//因为最后一个节点可能只有左孩子，没有右孩子，所以要减一，最后一个特殊处理
		for(int i=0; i<array.length/2-1; i++) {
			nodeList.get(i).leftChild = nodeList.get(i*2+1);
			nodeList.get(i).rightChild = nodeList.get(i*2+2);
		}
		
		int a = array.length/2-1;
		nodeList.get(a).leftChild = nodeList.get(a*2+1);
		if (a %2 != 0) {
			nodeList.get(a).rightChild = nodeList.get(a*2+2);			
		}
		
		return nodeList;
	}
	
	//先根遍历
	public void preRootTraverse(Node node) {
		if (node == null) {
			return;
		}
		System.out.print(node.data + " ");
		preRootTraverse(node.leftChild);
		preRootTraverse(node.rightChild);
	}
	
	//中根遍历
	public void middleRootTraverse(Node node) {
		if (node == null) {
			return;
		}
		middleRootTraverse(node.leftChild);
		System.out.print(node.data + " ");
		middleRootTraverse(node.rightChild);
	}
	
	//后跟遍历
	public void postRootTraverse(Node node) {
		if (node == null) {
			return;
		}
		postRootTraverse(node.leftChild);
		postRootTraverse(node.rightChild);
		System.out.print(node.data + " ");
	}
	
	public static void main(String[] args) {
		BinTree binTree = new BinTree();
		int[] array = new int[]{1,2,3,4,5,6,7,8,9};
		List<Node> nodeList = binTree.createBinTree(array);
//		binTree.preRootTraverse(nodeList.get(0));
//		binTree.middleRootTraverse(nodeList.get(0));
		binTree.postRootTraverse(nodeList.get(0));
	}
}

































