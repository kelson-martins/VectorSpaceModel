/*
+------------------+---------------------------------------------------------+
!Author            ! Kelson Martins     		                             !
+------------------+---------------------------------------------------------+
!Creation Date     ! 28/10/2014                                              !
+------------------+---------------------------------------------------------+
!   Updates		                                                             !
+-------------------------------------------------------------------+--------+
!   Updates Description				                                !Date    !
!                                                                   !        !
+-------------------------------------------------------------------+--------+
!                                                                   !        !
+-------------------------------------------------------------------+--------+
!                                                                   !        !
+-------------------------------------------------------------------+--------+
*/


package br.com.kelsonmartins;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
	
	 // Stopwords from internet to save time dont having to read it from a file and parse it in an array
	public static String[] stopwords ={"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", 
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
	

	public static void main (String args[]) throws IOException {
		
		  final int separator = 3;
		  
		  BufferedReader br = new BufferedReader(new FileReader("corpus.txt"));
		  
		  List<List<String>> data = new ArrayList<List<String>>();
		  
		  List<List<String>> finalData = new ArrayList<List<String>>();
		  
		  ArrayList<String> singleData;
		  
		  int i,t,a,b,w = 0;
		  
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
		        int it = 0;
		        
		        while (i != -1) {
		        	
		        	it++;
			        
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

			        if (i != -1) {
			        	//System.out.println( everything.substring(0, i) );
			        	sW = everything.substring(0 , everything.indexOf(".I"));
			        } else {
			        	sW = everything.substring(0);
			        	//System.out.println( sW );
			        }
		        	
			        singleData = new ArrayList<String>();
			        singleData.add(sI);
			        singleData.add(sT);
			        singleData.add(sA);
			        singleData.add(sB);
			        singleData.add(sW);
			        
			        data.add(singleData);
		        }
		      
		        
		    } finally {
		        br.close();		        
		    }
		    
		      Iterator itr = data.iterator();
		      while(itr.hasNext()) {
		         ArrayList element = (ArrayList) itr.next();

		         String s = element.get(1).toString();
		         s = s.replaceAll("\r", "");
		         s = s.replaceAll("\t", "");
		         s = s.replaceAll("\n", "");
		         
		         String u = element.get(4).toString();
		         u = u.replaceAll("\r", "");
		         u = u.replaceAll("\t", "");
		         u = u.replaceAll("\n", "");
		         
		         for(String stopWord : stopwords){
		        	 s = s.replaceAll(" "+ stopWord + " ", " ");
		        	 u = s.toString().replaceAll(" "+ stopWord + " ", " ");

		         }
		         
	        	 singleData = new ArrayList<String>(); 
	        	 singleData.add(element.get(0).toString().trim());
	        	 singleData.add(s.trim());
	        	 singleData.add(element.get(2).toString().trim());
	        	 singleData.add(element.get(3).toString().trim());
	        	 singleData.add(u.trim());
	        	 
	        	 finalData.add(singleData);
		      }
		      
		      int counter = 0;
		      Iterator itr2 = finalData.iterator();
		      while(itr2.hasNext() && counter < 10) {
			         ArrayList element = (ArrayList) itr2.next();
			         //System.out.print( element.get(1).toString().trim() + "\n");
			         counter++;
		      }
		      
		      //String[] arr = s.split(" "); 
		      
		      //for ( String term : arr) {

		      //    System.out.println(ss);
		     //}
		      

	}
	
}
