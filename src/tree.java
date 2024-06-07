import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class tree {
	Node root;

	int getHeight(Node node) {
		if (node == null)
			return -1;
		else
			return node.height;
	}

	public Node insertNode(Node avlNode, dictionary value) {
		if (avlNode == null) {
			Node x = new Node(value);
			return (x);
		}
		if (value.getWords().compareTo(avlNode.key.getWords()) < 0)
			avlNode.left = insertNode(avlNode.left, value);
		else if (value.getWords().compareTo(avlNode.key.getWords()) > 0)
			avlNode.right = insertNode(avlNode.right, value);
		else
			return avlNode;
		avlNode.height = Math.max(getHeight(avlNode.left), getHeight(avlNode.right)) + 1;
		if ((getHeight(avlNode.left) - getHeight(avlNode.right)) > 1) {
			if (value.getWords().compareTo(avlNode.left.key.getWords()) < 0) {
				return rotateToRight(avlNode);
			} else if (value.getWords().compareTo(avlNode.left.key.getWords()) > 0) {
				avlNode.left = rotateToLeft(avlNode.left);
				return rotateToRight(avlNode);
			}
		}
		if ((getHeight(avlNode.left) - getHeight(avlNode.right)) < -1) {
			if (value.getWords().compareTo(avlNode.right.key.getWords()) > 0) {
				return rotateToLeft(avlNode);
			} else if (value.getWords().compareTo(avlNode.right.key.getWords()) < 0) {
				avlNode.right = rotateToRight(avlNode.right);
				return rotateToLeft(avlNode);
			}
		}
		return avlNode;
	}

	public Node rotateToRight(Node d) {
		Node a, b;
		a = d.left;
		b = a.right;
		a.right = d;
		d.left = b;
		d.height = Math.max(getHeight(d.left), getHeight(d.right)) + 1;
		a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
		return a;
	}

	public Node rotateToLeft(Node d) {
		Node a,b;
		a = d.right;
		b = a.left;
		a.left = d;
		d.right = b;
		d.height = Math.max(getHeight(d.left), getHeight(d.right)) + 1;
		a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
		return a;
	}

	Node deleteNode(Node avlNode, String value) {
		if (avlNode == null)
			return avlNode;
		if (value.compareTo(avlNode.key.getWords()) < 0)
			avlNode.left = deleteNode(avlNode.left, value);
		else if (value.compareTo(avlNode.key.getWords()) > 0)
			avlNode.right = deleteNode(avlNode.right, value);
		else {
			if ((avlNode.left == null) || (avlNode.right == null)) {
				Node t = null;
				if (t == avlNode.left)
					t = avlNode.right;
				else
					t = avlNode.left;
				if (t == null) {
					t = avlNode;
					avlNode = null;
				} else
					avlNode = t;
			} else {
				Node temp = null;
				while (temp.left != null)
					temp = temp.left;
				avlNode.key = temp.key;
				avlNode.right = deleteNode(avlNode.right, temp.key.getWords());
			}
		}
		if (avlNode == null)
			return avlNode;
		avlNode.height = Math.max(getHeight(avlNode.left), getHeight(avlNode.right)) + 1;
		if ((getHeight(avlNode.left) - getHeight(avlNode.right) > 1
				&& (getHeight(avlNode.left.left) - getHeight(avlNode.left.right) >= 0)))
			return rotateToRight(avlNode);
		if ((getHeight(avlNode.left) - getHeight(avlNode.right) > 1
				&& (getHeight(avlNode.left.left) - getHeight(avlNode.left.right) < 0))) {
			avlNode.left = rotateToLeft(avlNode.left);
			return rotateToRight(avlNode);
		}
		if ((getHeight(avlNode.left) - getHeight(avlNode.right) < -1
				&& (getHeight(avlNode.right.left) - getHeight(avlNode.right.right) <= 0)))
			return rotateToLeft(avlNode);
		if ((getHeight(avlNode.left) - getHeight(avlNode.right) < -1
				&& (getHeight(avlNode.right.left) - getHeight(avlNode.right.right) > 0))) {
			avlNode.right = rotateToRight(avlNode.right);
			return rotateToLeft(avlNode);
			
		}
		return avlNode;
	}

	void displayInOrder(Node treeNode) {
		if (treeNode == null) {
			return;
		}
		displayInOrder(treeNode.left);
		System.out.println("Word:"+treeNode.key.getWords());
		System.out.println("Meaning:"+treeNode.key.getMeaning());
		displayInOrder(treeNode.right);
	}

	void displayPostOrder(Node treeNode) {
		if (treeNode == null)
			return;
		displayPostOrder(treeNode.left);
		displayPostOrder(treeNode.right);
		System.out.println("Word:"+treeNode.key.getWords());
		System.out.println("Meaning:"+treeNode.key.getMeaning());
	}

	void dispalyPreOrder(Node treeNode) {
		if (treeNode == null)
			return;
		System.out.println("Word:"+treeNode.key.getWords());
		System.out.println("Meaning:"+treeNode.key.getMeaning());
		dispalyPreOrder(treeNode.left);
		dispalyPreOrder(treeNode.right);
	}

	void displayPostOrder() {
		displayPostOrder(root);
	}

	void displayInOrder() {
		displayInOrder(root);
	}

	void dispalyPreOrder() {
		dispalyPreOrder(root);
	}

	static void writetoFile(Node node) throws IOException {
		File file = new File("words.txt");
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bf = new BufferedWriter(fileWriter);
		if (node != null) {
			bf.write(node.key.getWords() + " " + node.key.getMeaning());
			writetoFile(node.left);
			writetoFile(node.right);
			bf.newLine();
			
		}
		bf.close();
	}
	

}
