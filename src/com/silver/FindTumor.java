package com.silver;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FindTumor {
	private static Set<String> listOfLetters = new HashSet<String>();

    public static void main(String[] args) {
        args = new String[1];
    	
        //Get file
        args[0] = "test.in";
    	
    	if (args.length != 1) {
            System.err.println("Usage: java FindTumor <file-path>");
            System.exit(1);
        }

        String filePath = args[0];
        try {
            String content = readContent(filePath);
            boolean hasMultipleGroups = hasMultipleGroups(content);

            if (hasMultipleGroups) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }

            int[] dimensions = calculateDimensions(content);
            System.out.println("Dimensions: " + dimensions[0] + " x " + dimensions[1]);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static String readContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }

    private static boolean hasMultipleGroups(String content) {
        String[] rows = content.split("\n");
        Set<Character> currentGroup = new HashSet<>();
        
        populateLetterArray(content);
        
        searchForLetterGroups();

        //Loop through the rows
//        for (int i = 0; i < rows.length; i++) {
//            
//        	//Loop through the columns
//        	for (int j = 0; j < rows[i].length(); j++) {
//                char currentChar = rows[i].charAt(j);
//
//                if (currentChar != ' ') {
//                    currentGroup.add(currentChar);
//
//                    if (j > 0 && rows[i].charAt(j - 1) != ' ' && rows[i].charAt(j - 1) != currentChar) {
//                        return true;
//                    }
//
//                    if (i > 0 && rows[i - 1].charAt(j) != ' ' && rows[i - 1].charAt(j) != currentChar) {
//                        return true;
//                    }
//                }
//            }
//        }

        return currentGroup.size() > 1;
    }

    private static int[] calculateDimensions(String content) {
        String[] rows = content.split("\n");
        int length = rows.length;
        int width = rows.length > 0 ? rows[0].length() : 0;
        return new int[]{length, width};
    }
    
    //find all the unique letters and create an Set array
    private static void populateLetterArray(String content) {
    	
    	String[] rows = content.split("\n");
    	
    	for (int i = 0; i < rows.length; i++) {
    		
    		for (int j = 0; j < rows[i].length(); j++) {
    			
    			String currentLetter = "" + rows[i].charAt(j);
    			if(!currentLetter.isBlank()) {    				
    				listOfLetters.add(currentLetter);
    			}
    			
    		}
    	}
    }
    
    //search for each letter structure of side by side to establish a single block
    private static void searchForLetterGroups() {
    	if(listOfLetters.contains("x")) {
    		System.out.println("A single block has been identified");
    	} else {
    		System.out.println("A single block was not found");
    	}
    }
    
    //when a new letter is found in the row drop down and see if that letter is found
    
    //
}
