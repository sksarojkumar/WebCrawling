package com.WebCrawling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *This class containing main() method.
 *@Input text file which will contain extensions without using dot "." .These extensions will separated by space.
 *@Output text file which will save in your current directory.
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
    	while(true){
	    	
	    	//taking input file path which will contain extensions
	    	boolean InputFilePath = true ;
	    	File fileContainExtensions = null ;
	    	while(InputFilePath){
		    	System.out.println("Enter file name (as- C:\\my_input_file\\input.txt) which contains extensions without dot \".\" and separated by space and/or line:");
		    	String inputFile = "D:\\workspace\\WebCrawling\\input.txt";
		    	System.out.println(inputFile);
		    	fileContainExtensions = new File(inputFile);
		    	if(fileContainExtensions.exists()){
		    		InputFilePath = false;
		    	}else{
		    		System.out.println("Input file path not exist, again try");
		    	}
		    }
	    	
	    	
	    	try{
	    		//Reading extensions from given input file
		    	BufferedReader br = new BufferedReader(new FileReader(fileContainExtensions));
		    	ArrayList<String> listOfExtensions = new ArrayList<String>();
		    	String line ;	    	
		    	while((line = br.readLine()) != null){
		    		String[] arrayOfExtension = line.split("\\s+");
		    		for(int i = 0; i<arrayOfExtension.length; i++){
		    			listOfExtensions.add(arrayOfExtension[i]);
		    		}
		     	}
		        
		    	try {
					br.close();
				} catch (IOException e) {} 
		    	
		    	int lengthOfListOfExtensions =  listOfExtensions.size();
		    	System.out.println("Total extension :"+lengthOfListOfExtensions);
		    	
		    	//taking output file path from user
		    	String outputFile = "output_"+System.currentTimeMillis()+".txt";
		    	File fileToStoreExtensionInformation = new File(outputFile);
		    	fileToStoreExtensionInformation.createNewFile();
		    	
		    	System.out.println("Processing...");
		    	
		    	//calling thread to fetching data from url and save them
		    	ExecutorService executor = Executors.newFixedThreadPool(20);
		    	for(int i = 0; i<listOfExtensions.size(); i++){
		    		String extension = listOfExtensions.get(i);
		    	    Runnable r = new ExtensionInfo(extension,fileToStoreExtensionInformation);
		    	    executor.execute(r);    		
		    	}
		    	executor.shutdown();
		    	while(!executor.isTerminated()){}
		    	
		    	System.out.println("\nData has written in "+fileToStoreExtensionInformation.getAbsolutePath());
		    	
		    	
	    	}catch(FileNotFoundException e){
	    		System.err.println("Your given file input file has not found");
	    	}catch(IOException e){
	    		System.err.println("Some error in input/output");
	    	}
	    	
	    	//Ask user to continue main function
	    	Scanner s = new Scanner(System.in);
	    	System.out.println("\nIf you want exit press y or Y otherwise press any key to continue ");
	    	char isContinue = s.nextLine().trim().charAt(0);
	    	if(isContinue=='y' || isContinue == 'Y'){
	    		System.out.println("You successfully exit.");
	    		break;
	    	}
	    	s.close();
    	}	
    }
}
