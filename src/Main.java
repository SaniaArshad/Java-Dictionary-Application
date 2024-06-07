import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static tree diction = new tree();
	static Node root = null;

	public static void main(String[] args) throws IOException {
		Scanner scn = new Scanner(System.in);
		int option;
		do {
			System.out.println("-------SANIAS DICTIONARY-------");
			System.out.println("1.Add a Word To The Dictionary");
			System.out.println("2.Delete a Word From The Dictionary");
			System.out.println("3.Search a Word From The Dictionary");
			System.out.println("4.Show Full Dictionary");
			System.out.println("5.Exit The Program");
			option = scn.nextInt();
			switch (option) {
			case 1:
				start();
				System.out.println("Enter A Word: ");
				String word = scn.next();
				System.out.println("Enter Meaning: ");
				String meaning = scn.next();
				diction.root = diction.insertNode(diction.root, new dictionary(word, meaning));
				diction.writetoFile(diction.root);
				System.out.println("Your Word Has Been Successfully Added");
				break;
			case 2:
				start();
				System.out.println("Enter The Word To Delete: ");
				String wordDelete = scn.next();
				diction.root = diction.deleteNode(diction.root, wordDelete);
				String meaningDel = search(wordDelete);
				removeFromFile("words.txt", wordDelete + " " + meaningDel);
				System.out.println("Your Word Has Been Successfully Deleted");
				break;
			case 3:
				start();
				System.out.println("Enter Word To Search: ");
				String searchWord = scn.next();
				String Meaning = search(searchWord);
				System.out.println("The Word You Searched: " + searchWord);
				System.out.println("Meaning: " + Meaning);
				break;
			case 4:
				System.out.println("Select The Way You Want To View The Dictionary: ");
				System.out.println("1.Pre Order");
				System.out.println("2.Post Order");
				System.out.println("3.In Order");
				int opt = scn.nextInt();
				switch (opt) {
				case 1:
					start();
					diction.dispalyPreOrder(root);
					break;
				case 2:
					start();
					diction.displayPostOrder(root);
					break;
				case 3:
					start();
					diction.displayInOrder(root);
					break;
				default:
					System.out.println("Enter Valid Option");
				}

				break;
			case 5:
				System.out.println("Exiting Program..");
				break;
			default:
				System.out.println("Select A Valid Option");
			}
		} while (option != 5);

	}

	public static void start() throws FileNotFoundException {

		Scanner file = new Scanner(new File("words.txt"));
		String[] array = null;
		String data = null;
		while (file.hasNextLine()) {
			data = file.nextLine();
			for (int i = 0; i < data.split(" ").length; i++) {
				array = data.split(" ");
			}
			String word = array[0];
			String meaning = array[1];
			root = diction.insertNode(root, new dictionary(word, meaning));

		}
		file.close();

	}

	static String searchForTheWord(Node node, String key) {
		if (node == null) {
			return null;
		} else if (key.compareTo(node.key.getWords()) < 0) {
			return searchForTheWord(node.left, key);
		} else if (key.compareTo(node.key.getWords()) > 0) {
			return searchForTheWord(node.right, key);
		} else {
			return node.key.getMeaning();
		}
	}

	public static String search(String key) {
		return searchForTheWord(root, key);
	}

	public static void removeFromFile(String fileName, String removingLine) {
		try {
			File theFile = new File(fileName);
			if (!theFile.isFile()) {
				System.out.println("no such file exists");
				return;
			}
			File theFile2 = new File(theFile.getAbsolutePath() + ".tmp");
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			PrintWriter printt = new PrintWriter(new FileWriter(theFile2));
			String line = null;
			while ((line = buff.readLine()) != null) {
				if (!line.trim().equals(removingLine)) {
					printt.println(line);
					printt.flush();
				}
			}
			printt.close();
			buff.close();
			if (!theFile.delete()) {
				System.out.println("file cannot be deleted");
				return;
			}
			if (!theFile2.renameTo(theFile))
				System.out.println("file cannot be renamed");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

}
