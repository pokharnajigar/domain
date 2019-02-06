import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Domains {
	static List<StringBuffer> listOfChanges = new ArrayList<>();
	static Set<String> domainSet;
	static int matrix[][];
	static ArrayList<ArrayList<String>> listOfList = new ArrayList<ArrayList<String>>();

	boolean load(Set<String> mainDomainSet) {
		Domains.domainSet = mainDomainSet;
		System.out.println("Loading...\n" + domainSet);
		return true;
	}

	String[] editPath(String domainName1, String domainName2) {
		printAllChanges(domainName1, domainName2, new ArrayList<String>());
		String[] strArray = new String[listOfChanges.size()];
		for (int i = 0; i < listOfChanges.size(); i++) {
			strArray[i]=listOfChanges.get(i).toString();
		}
		return strArray;
	}

	// Function to find the minimum of three
	static int min(int a, int b, int c) {
		int z = Math.min(a, b);
		return Math.min(z, c);
	}

	int editDistance(String domainName1, String domainName2) {
		// Create a table to store results of subproblems
		int m = domainName1.length();
		int n = domainName2.length();
		int dp[][] = new int[m + 1][n + 1];

		for (int i = 0; i <= m; i++) { // Fill d[][] in bottom up manner
			for (int j = 0; j <= n; j++) {
				if (i == 0)
					dp[i][j] = j; // Min. operations = j
				else if (j == 0)
					dp[i][j] = i; // Min. operations = i
				else if (domainName1.charAt(i - 1) == domainName2.charAt(j - 1))
					dp[i][j] = dp[i - 1][j - 1];
				else
					dp[i][j] = 1 + min(dp[i][j - 1], // Insert
							dp[i - 1][j], // Remove
							dp[i - 1][j - 1]); // Replace
			}
		}
		matrix = dp; // initialize to global array
		return dp[m][n];
	}

	static void printAllChanges(String domainName1, String domainName2, ArrayList<String> changes) {
		int i = domainName1.length();
		int j = domainName2.length();
		StringBuffer str = new StringBuffer(domainName1);
		while (true) {
			if (i == 0 || j == 0) {
				listOfList.add(changes);
				break;
			}
			if (domainName1.charAt(i - 1) == domainName2.charAt(j - 1)) { // If same
				i--;
				j--;
			} else {
				boolean flag1 = false, flag2 = false;
				if (matrix[i][j] == matrix[i - 1][j - 1] + 1) { // Replace
					changes.add("Change " + domainName1.charAt(i - 1) + " to " + domainName2.charAt(j - 1)); // Add this
					// step
					str.replace(i - 1, i, String.valueOf(domainName2.charAt(j - 1)));
					System.err.println("changed str==" + str);
					if (domainSet.contains(str.toString())) {
						listOfChanges.add(str);
					}
					i--;
					j--;
					flag1 = true; // note whether this 'if' was true.
				}
				if (matrix[i][j] == matrix[i - 1][j] + 1) { // Delete
					if (flag1 == false) {
						changes.add("Delete " + domainName1.charAt(i - 1));
						i--;
					} else {
						// If the previous method was true,
						// create a new list as a copy of previous.
						ArrayList<String> changes2 = new ArrayList<String>();
						changes2.addAll(changes);
						changes2.remove(changes.size() - 1);
						changes2.add("Delete " + domainName1.charAt(i));
						printAllChanges(domainName1.substring(0, i), domainName2.substring(0, j + 1), changes2);
					}
					flag2 = true;
				}

				// Add character step
				if (matrix[i][j] == matrix[i][j - 1] + 1) {
					if (flag1 == false && flag2 == false) {
						changes.add("Add " + domainName2.charAt(j - 1));
						j--;
					} else {
						ArrayList<String> changes2 = new ArrayList<String>();
						changes2.addAll(changes);
						changes2.remove(changes.size() - 1);
						changes2.add("Add " + domainName2.charAt(j));
						printAllChanges(domainName1.substring(0, i + 1), domainName2.substring(0, j), changes2);
					}
				}
			}
		}
	}
}
