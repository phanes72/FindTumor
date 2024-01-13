package com.silver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindTumor {
	private static String content;
	private static Set<String> listOfLetters = new HashSet<String>();
	private static ArrayList<String> groupList = new ArrayList<String>();

	public static void main(String[] args) {
		args = new String[1];

		// Get file
		args[0] = "resources/test.in";

		if (args.length != 1) {
			System.err.println("Usage: java FindTumor <file-path>");
			System.exit(1);
		}

		String filePath = args[0];
		try {
			content = readContent(filePath);
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

	protected static String readContent(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		return Files.readString(path);
	}

	protected static boolean hasMultipleGroups(String content) {
		//String[] rows = content.split("\n");
		Set<Character> currentGroup = new HashSet<>();

		populateUniqueLetterArray(content);

		searchForLetterGroups();

		// Loop through the rows
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

	protected static int[] calculateDimensions(String content) {
		String[] rows = content.split("\n");
		int length = rows.length;
		int width = rows.length > 0 ? rows[0].length() : 0;
		return new int[] { length, width };
	}

	// Populate a static content variable to carry the matrix data

	// find all the unique letters in the matrix
	protected static void populateUniqueLetterArray(String content) {

		String[] rows = content.split("\n");

		for (int i = 0; i < rows.length; i++) {

			for (int j = 0; j < rows[i].length(); j++) {

				String currentLetter = "" + rows[i].charAt(j);
				if (!currentLetter.isBlank()) {
					listOfLetters.add(currentLetter);
				}

			}
		}
	}

	// search for each letter structure of a side-by-side occurrence to establish a
	// single block
	protected static void searchForLetterGroups() {
		Iterator<String> listOfUniqueLetterIterator = listOfLetters.iterator();

		// Loop thru the Unique List of letters
		while (listOfUniqueLetterIterator.hasNext()) {
			String uniqueLetter = listOfUniqueLetterIterator.next();
			Pattern uniqueLetterSideBySidePattern = Pattern.compile(uniqueLetter + uniqueLetter);
			Matcher uniqueLetterSideBySideGroups = uniqueLetterSideBySidePattern.matcher(content);
			
			while(uniqueLetterSideBySideGroups.find()) {
				System.out.println("Side by Side found for " + uniqueLetter);
				String groupFoundString = "Side by Side found for " + uniqueLetter;
				groupList.add(groupFoundString);
			}

		}

	}

	// when a new letter is found in the row drop down and see if that letter is
	// found

	//
}
