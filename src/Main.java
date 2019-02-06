import java.util.HashSet;
import java.util.Set;

public class Main {
	// Driver Code
	public static void main(String[] args) throws Exception {
		Set<String> inputDomainSet = new HashSet<>();
		inputDomainSet.add("google.ca");
		inputDomainSet.add("g00gle.ca");
		inputDomainSet.add("gogle.ca");
		inputDomainSet.add("g0gle.ca");
		inputDomainSet.add("gooogle.ca");
		inputDomainSet.add("goog1e.ca");
		inputDomainSet.add("g00g1e.ca");

		Domains domainObj = new Domains();
		boolean result = domainObj.load(inputDomainSet);
		if (result) {
			System.out.println("Domain Set Loaded successfully !");
		}
		
		String domainName1 = "google.ca";
		String domainName2 = "g00gle.ca";

		int editDist = domainObj.editDistance(domainName1, domainName2);
		System.err.println("Edit Distance : " + editDist);
		
		String[] resultEditPath = domainObj.editPath(domainName1, domainName2);
		for (int i = 0; i < resultEditPath.length; i++) {
			System.err.println("Edit Path : " + resultEditPath[i]);
		}

	}
}
