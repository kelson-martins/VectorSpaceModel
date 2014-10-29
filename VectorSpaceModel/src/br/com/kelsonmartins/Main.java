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
import java.util.List;

public class Main {

	public static void main (String args[]) throws IOException {
		
		  final int separator = 3;
		  
		  BufferedReader br = new BufferedReader(new FileReader("corpus.txt"));
		  
		  List<List<String>> data = new ArrayList<List<String>>();
		  
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
		        		        
		        
		        System.out.println(everything.indexOf(".I"));
		        
		        i = everything.indexOf(".I");
		        int it = 0;
		        
		        while (i != -1) {
		        	
		        	it++;
			        System.out.println(everything.indexOf(".T"));
			        
			        t = everything.indexOf(".T");
			        
			        System.out.println(everything.indexOf(".A"));
			        
			        a = everything.indexOf(".A");
			        
			        System.out.println(everything.indexOf(".B"));
			        
			        b = everything.indexOf(".B");
			        
			        System.out.println(everything.indexOf(".W"));
			        
			        w = everything.indexOf(".W");
			        
			        System.out.println("Iteration " + it);
			        
			        System.out.println( everything.substring(i + separator, t) );
			        System.out.println( everything.substring(t + separator, a) );
			        System.out.println( everything.substring(a + separator, b) );
			        System.out.println( everything.substring(b + separator, w) );
			        
			        sI = everything.substring(i + separator, t);
			        sT = everything.substring(t + separator, a);
			        sA = everything.substring(a + separator, b);
			        sB = everything.substring(b + separator, w);
			        
			        everything = everything.substring(w+separator);

			        i = everything.indexOf(".I");

			        if (i != -1) {
			        	System.out.println( everything.substring(0, i) );
			        	sW = everything.substring(0 , everything.indexOf(".I"));
			        } else {
			        	sW = everything.substring(0);
			        	System.out.println( sW );
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
		    
		    
		    
	}
	
}
