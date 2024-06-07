public class Node {
		dictionary key;
		Node left, right;
		int height;

		public Node(dictionary item) {
			key = item;
			left = right = null;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

	}