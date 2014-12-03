package br.com.kelsonmartins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class VectorSpaceModel {


	// Variable to hold all parsed documents
	public static List<String> items = new ArrayList<String>();

	//This variable will hold all terms of each document in an array.
	public static List<String[]> termsDocsArray = new ArrayList<String[]>();

	// All terms of all documents
	public static List<String> allTerms = new ArrayList<String>();

	// Variabe to hold the tfidfmatrix ( The processing time of this variable was huge. Had to serialize it in an object for future use )
	public static List<float[]> tfidfDocsVector = new ArrayList<float[]>();

	public static String query = "";
	
	// asking the user query input
	public static String askInput() {

		Scanner input = new Scanner( System.in );

		System.out.print("Enter Query: ");
		
		// nextLine instead of next since next read only one word. nextLine can read entire strings containing spaces
		query = input.nextLine();
		
		System.out.println("Processing.............................................................");
		input.close();

		return query;
	}


	public static void generateCorpse(String query) throws IOException {

		final byte separator = 3;

		BufferedReader br = new BufferedReader(new FileReader("corpus.txt"),15000);
		
		String[] stopwords ={"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", 
				"alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", 
				"another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", 
				"because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", 
				"between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt",
				"cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else",
				"elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", 
				"fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", 
				"front", "full", "further", "get", "give", "go", "had", "has", "hasnt",
				"have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", 
				"him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", 
				"is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", 
				"may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", 
				"my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", 
				"noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", 
				"or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps",
				"please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she",
				"should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", 
				"sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", 
				"them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", 
				"these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", 
				"thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", 
				"up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
				"where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", 
				"whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet",
				"you", "your", "yours", "yourself", "yourselves","1","2","3","4","5","6","7","8","9","10","1.","2.","3.","4.","5.","6.","11",
				"7.","8.","9.","12","13","14","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
				"terms","CONDITIONS","conditions","values","interested.","care","sure",".","!","@","#","$","%","^","&","*","\\(","\\)","\\{","\\}","\\[","\\]",":",";",",","<",".",">","/","?","_","-","+","=",
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
				"contact","grounds","buyers","tried","said,","plan","value","principle.","forces","sent:","is,","was","like",
				"discussion","tmus","diffrent.","layout","area.","thanks","thankyou","hello","bye","rise","fell","fall","psqft.","http://","km","miles"};

		List<String> data = new ArrayList<String>();

		// i = Number
		// t = Title
		// a = Author
		// b = Publication
		// w = Content
		
		int i,t,a,b,w = 0;

		@SuppressWarnings("unused")
		String sI, sT, sA, sB, sW = "";

		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}

			String everything = sb.toString();

			i = everything.indexOf(".I");

			// logic for parsing the given corpus - I am taking in account only the content of each document (.W)
			while (i != -1) {

				w = everything.indexOf(".W");
				t = everything.indexOf(".T");
		        a = everything.indexOf(".A");
		        b = everything.indexOf(".B");
		        w = everything.indexOf(".W");
		        
		        sI = everything.substring(i + separator, t);
		        sT = everything.substring(t + separator, a);
		        sA = everything.substring(a + separator, b);
		        sB = everything.substring(b + separator, w);
		        
				everything = everything.substring(w+separator);

				i = everything.indexOf(".I");

				// if i != -1, there are other documents to parse. If is -1, it was the last one
				if (i != -1) {
					sW = everything.substring(0 , everything.indexOf(".I"));
				} else {
					sW = everything.substring(0);
				}

				// adding all the parts from corpus
				data.add(sT + " " + sA + " " + sB + " " + sW);
			}


		} finally {
			br.close();		        
		}

		Iterator<String> itr = data.iterator();
		while(itr.hasNext()) {

			String element = (String) itr.next();

			// Parsing the content of the .W (Content of the document. Title is no being used for this assignment)
			String u = element;
			u = u.replaceAll("\r", "");
			u = u.replaceAll("\t", "");
			u = u.replaceAll("\n", " ");

			// removing stopwords
			for(String stopWord : stopwords){
				u = u.toString().replaceAll(" "+ stopWord + " ", " ");
			}

			items.add(u);


		}

		for (int k = 0; k<items.size();k++) {
			String[] tokenizedTerms = items.get(k).replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
			for (String term : tokenizedTerms) {

				// only adding distinct terms
				if (!allTerms.contains(term)) {  
					allTerms.add(term);
				}
			}

			// terms by each documents (entire documents)
			termsDocsArray.add(tokenizedTerms);
		}



	}

	@SuppressWarnings("unchecked")
	public static boolean loadData() throws IOException, ClassNotFoundException {

		boolean returnValue = false;

		if (new File("tfidfmatrix.zip").isFile()) {

			unZipIt("tfidfmatrix.zip",System.getProperty("user.dir"));

			FileInputStream f_in = new FileInputStream("tfidfmatrix.data");

			ObjectInputStream obj_in = new ObjectInputStream (f_in);

			Object obj = obj_in.readObject();
			obj_in.close();

			if (obj instanceof ArrayList)
			{
				tfidfDocsVector = (ArrayList<float[]>) obj;
			}        	

			returnValue = true;

		}

		return returnValue;
	}


	public static void saveData() throws IOException {

		FileOutputStream f_out = new FileOutputStream("tfidfmatrix.data");

		ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

		obj_out.writeObject ( tfidfDocsVector );
		obj_out.close();

	}


	public static void unZipIt(String zipFile, String outputFolder){

		byte[] buffer = new byte[1024];

		try{

			File folder = new File(outputFolder);
			if(!folder.exists()){
				folder.mkdir();
			}

			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));

			ZipEntry ze = zis.getNextEntry();

			while(ze!=null){

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);

				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);             

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();   
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		}catch(IOException ex){
			ex.printStackTrace(); 
		}
	}    

	
	public static void tfidfMatrix() throws IOException {

		for (String[] docTermsArray : termsDocsArray) {
			// creating the double vector with the size of the terms for the index table
			float[] tfidfvectors = new float[allTerms.size()];
			int count = 0;
			for (String terms : allTerms) {
				tfidfvectors[count] = tfCalculator(docTermsArray, terms) * idfCalculator(termsDocsArray, terms);
				count++;
			}

			//storing document vectors; 
			tfidfDocsVector.add(tfidfvectors);             
		}

		// save the State of the tfidfvector 
		//( not using anymore since I am storing the data in a zip file)
		//saveData();
	}

	// get and print the similarity
	public static void cosine() {

		HashMap<String, Float> result = new HashMap<String, Float>();
		ValueComparator comp =  new ValueComparator(result);

		// using tree to sort the results by default
		TreeMap<String,Float> sorted_map = new TreeMap<String,Float>(comp);
		float cosine = 0;

		for (int i = 0; i < tfidfDocsVector.size(); i++) {
			cosine = getSimilarity(tfidfDocsVector.get(tfidfDocsVector.size()-1), tfidfDocsVector.get(i));
			
			result.put(String.valueOf(i+1),cosine);
		}

		sorted_map.putAll(result);

		System.out.println();
		System.out.println("Showing the 100 Best Results according to the given query");
		System.out.println();

		// Starting with index 1 because the index 0 is the query itself so the cosine is going to be 1.0
		for (byte i = 1; i < 101; i++) {

			float v = (float) sorted_map.values().toArray()[i];
			if (Float.isNaN(v)) {
				v = 00;
			}
			System.out.println("Result " + i + ": " + "Document " + sorted_map.keySet().toArray()[i] + " -> " + v);

		}

		File file = new File("tfidfmatrix.data");

		// Delete tfidfmatrix
		// only use this when I have the ziped file so I dont need the .data since every time that the program runs, it is going to unzip 
		//
		file.delete();

	}    

	// Processing the query manually in the matrix since I have the tfidf matrix stored, need to add the query to the table
	public static void processQuery(String query) {

		List<String[]> termsDocsQuery = new ArrayList<String[]>();  

		String[] queryTerms = query.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");;///get individual query term

		termsDocsQuery.add(queryTerms);

		// if the query has different terms, add it to the allTerms list
		for(String term : queryTerms){
			if(!allTerms.contains(term)){
				allTerms.add(term);
			}
		}

		//add query document
		termsDocsArray.add(queryTerms);       

		float[] tfidfvectors = new float[allTerms.size()];
		//double[] tfcall = new double[allTerms.size()];
		int count =0;

		for (String[] docTermsArray : termsDocsQuery) { 
			for (String terms : allTerms) {


				tfidfvectors[count] = tfCalculator(docTermsArray, terms) * idfCalculator(termsDocsArray, terms);
				count++;

			}
		}

		// Store query vectors
		tfidfDocsVector.add(tfidfvectors);       
	}
	
	// Calculate the tf
	public static float tfCalculator(String[] totalterms, String termToCheck) {
		
		float occurrences = 0;  // to count the number of occurrence of each term in each document
		for (String separatedTerm : totalterms) {
			if (separatedTerm.equalsIgnoreCase(termToCheck)) {
				occurrences++;
			}
		}
		// return the term frequency
		return occurrences / totalterms.length;
	}

	// Calculate the idf
	public static float idfCalculator(List<String[]> allTerms, String check) {
		float count = 0;
		for (String[] arrayTerms : allTerms) {
			for (String separatedTerms : arrayTerms) {
				// check if the document contains the term
				if (separatedTerms.equalsIgnoreCase(check)) {
					count++;
					// break because it does not matter if the document has more occurrences.
					break;
				}
			}
		}
		// return the size of terms divided by the number of documents that contain the term.
		// powering by 2 to reduce the scale
		return (float)Math.log(allTerms.size() / count);
	}


	// getting the similarity between each document
	public static float getSimilarity(float[] docVector1, float[] docVector2) {
		float qd = 0;
		float queyP = 0;
		float docP = 0;
		float cosineResult = 0;

		for (int i = 0; i < docVector2.length; i++) // getting the product of query and document
		{
			// adding all multiplication of array in the same index
			qd += docVector1[i] * docVector2[i]; 
			// powering query vector by 2
			queyP += Math.pow(docVector1[i], 2);
			// powering document vector by 2
			docP += Math.pow(docVector2[i], 2);
		}

		// getting the lenght vector
		queyP = (float)Math.sqrt(queyP);
		// getting the document  vector lenght
		docP = (float)Math.sqrt(docP);

		// if both values are not 0, there were no occurrences at all
		// if != 0, it is noa NaN so there is a smilarity to show
		if (queyP != 0.0 | docP != 0.0) {
			cosineResult = qd / (queyP * docP);
		} else {
			// Set to 0 manually since it is NaN by default
			return 0;
		}
		
		// returning the similarity
		return cosineResult;
	}

	
}
