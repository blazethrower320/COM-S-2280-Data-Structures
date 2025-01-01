package edu.iastate.cs2280.hw4;

import java.util.HashSet;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

/**
 * @author Carson Torrey
 */
public class Main {
	public static void main(String[] args) throws IOException {
	    System.out.println("File Name: ");
	    
	    
	    Scanner Scnr = new Scanner(System.in);
	    String fileString = Scnr.next();
	    
	    File file = new File(fileString);
	    Scanner fileScnr = new Scanner(file);


	    StringBuilder builder = new StringBuilder();
	    while (fileScnr.hasNextLine()) 
	    {
	    	builder.append(fileScnr.nextLine()).append("\n");
	    }
	    String contentBefore = builder.toString();
	    // Remove the sspaces from the content
	    String content = contentBefore.trim();
	    int positionn = content.lastIndexOf('\n');
	    String nodeStyle = content.substring(0, positionn);
	    String decodeStyle = content.substring(positionn).trim(); 

	    
	    HashSet<Character> characterCodes = new HashSet<>();
	    StringBuilder allCharacters = new StringBuilder();
	    for (char loopCharacter : nodeStyle.toCharArray()) 
	    {
	        if (loopCharacter != '^') 
	        {
	        	characterCodes.add(loopCharacter);
	        }
	    }
	    for(char loopCharacter : characterCodes)
	    {
	    	// Be able to call .toString as characterCodes is a Hashset. I dont think theres a way to convernt a Hashset to a string
	    	allCharacters.append(loopCharacter);
	    }
	    
	    
	    MsgTree root = new MsgTree(nodeStyle);
	    MsgTree.printCodes(root, allCharacters.toString());
	    root.decode(root, decodeStyle);
	}

}