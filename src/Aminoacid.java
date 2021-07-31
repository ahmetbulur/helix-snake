import java.io.*;

public class Aminoacid {

	static MultiLinkedList mll;

	public Aminoacid() {

	}

	// write data which taken from "aminoacids.txt" file to Multi Linked List
	public static void takeCodonsFromTxt() throws NumberFormatException, IOException {

		mll = new MultiLinkedList();

		File file = new File("aminoacids.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;

		String[] temp;
		String[] temp2;
		while ((st = br.readLine()) != null) {

			temp = st.split(",");
			mll.addCategory(temp[0]);
			for (int i = 2; i < temp.length; i++) {
				temp2 = temp[i].split("-");
				mll.addItem(temp[0], temp2[0], Integer.parseInt(temp2[1]));

			}

		}

	}

}
