package edu.iastate.cs2280.hw4;

import java.util.Stack;

/**
 * 
 * @author Carson Torrey
 *
 */
public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	// Need static char idx to the tree string for recursive solution
	private static int staticCharIdx = 0;
	private static String codeSaved;

	/**
	 * Constructor building the tree from a string
	 * 
	 * @param encodingString string pulled from data file
	 */
	public MsgTree(String encodingString) {
	    if (encodingString == null) 
	    {
	        return;
	    }

	    Stack<MsgTree> stack = new Stack<>();
	    this.payloadChar = encodingString.charAt(staticCharIdx);
	    staticCharIdx++;
	    
	    stack.push(this);
	    MsgTree currentNode = this;
	    boolean isLeftChildNode = true;

	    while (staticCharIdx < encodingString.length()) 
	    {
	        MsgTree node = new MsgTree(encodingString.charAt(staticCharIdx));
	        staticCharIdx++;
	        
	        if (isLeftChildNode) 
	        {
	        	// go left of the node
	            currentNode.left = node;
	        } else 
	        {
	        	// go right of node
	            currentNode.right = node;
	        }

	        if (node.payloadChar == '^') 
	        {
	        	// There is more to the left
	            stack.push(currentNode = node);
	            isLeftChildNode = true;
	        } 
	        else 
	        {
	            if (!stack.empty()) 
	            {
	                currentNode = stack.pop();
	            }
	            // There is no more left nodes, need to go to the right now
	            isLeftChildNode = false;
	        }
	    }
	}

	/**
	 * Constructor for a single node with null children
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.left = null;
		this.right = null;
		this.payloadChar = payloadChar;
	}
	/**
	 * Gets the code of the root
	 * @param root
	 * @param codePath
	 * @param lookingCharacter
	 * 
	 */
	private static boolean recursiveCode(MsgTree root, String codePath, char lookingCharacter) {
		
		if (root == null) 
		{
	        return false;
	    }
	    if (root.payloadChar == lookingCharacter) 
	    {
	    	codeSaved = codePath;
	        return true;
	    }
	    return recursiveCode(root.left, codePath + "0", lookingCharacter) 
	    		|| 
	    		recursiveCode(root.right, codePath + "1",lookingCharacter);
	}
	/**
	 * Method to print characters and their binary codes
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
	    System.out.println("Character Codes\n-------------------------");
	    
	    for (char characterArray : code.toCharArray()) 
	    {
	        code = "";
	        recursiveCode(root, code, characterArray);

	        if (characterArray == '\n') 
	        {
	        	// Its a new Line
	            System.out.println("    \\n    " + codeSaved);
	        } 
	        else 
	        {
	            System.out.println("    " + characterArray + "    " + codeSaved);
	        }
	    }
	}

	/**
	 * Decodes message using the pulled code alphabet
	 * 
	 * @param codes
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {
		System.out.println("MESSAGE:");
		MsgTree currentNode = codes;
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < msg.length(); i++) 
		{
			char characterLook = msg.charAt(i);
			
			if (characterLook == '0') 
			{
			    currentNode = currentNode.left;
			} 
			else 
			{
			    currentNode = currentNode.right;
			}
			if (currentNode.payloadChar != '^') 
			{
				codeSaved = "";
				recursiveCode(codes, codeSaved, currentNode.payloadChar);
				builder.append(currentNode.payloadChar);
				currentNode = codes;
			}
		}
		System.out.println(builder.toString());
		// Statistic 5% extra part
		System.out.println("Statistics:");
		System.out.println("Avg Bits / Char: " +  msg.length() / (double) builder.length());
		System.out.println("Total Characters: " + builder.length());
		System.out.println("Savings: " + (1 - builder.length() / (double)msg.length()) * 100);
	}
}