package br.com.kelsonmartins;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Main {
    
    public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException
    
    {
    	
    	//Parse the Given Corpus
    	VectorSpaceModel.generateCorpse(VectorSpaceModel.askInput()); 
        
    	// load the matrix only if there is no tfidfmatrix.zip
    	//(This approach was my solution due to the processing time to build the tfidfmatrix)
        if (!VectorSpaceModel.loadData()) {
        	//calculates tfidf
        	VectorSpaceModel.tfidfMatrix();
        }
      
        // process the query manually
        VectorSpaceModel.processQuery(VectorSpaceModel.query);
        
      //calculated cosine similarity   
        VectorSpaceModel.cosine(); 
    }
    
    
    
}    
    