package com.silver;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindTumor {
	private static int width;
	private static int length;
	private static String content;

	private static List<ScanPoint> mriMatrix = new ArrayList<ScanPoint>();
	

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
			int[] dimensions = calculateDimensions(content);

			boolean hasMultipleGroups = hasMultipleGroups(content);

			if (hasMultipleGroups) {
				System.out.println("True");
			} else {
				System.out.println("False");
			}

			System.out.println("Dimensions: " + dimensions[0] + " x " + dimensions[1]);

		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

	protected static String readContent(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		return Files.readString(path);
	}

	//Main Method that calls the other utility methods
	protected static boolean hasMultipleGroups(String content) {
		// String[] rows = content.split("\n");
		Set<Character> currentGroup = new HashSet<>();

		populateMatrix();

		// getUniqueLettersInTheMatrix();

		checkForLetterBlock();

		determineIfBlockIsAutonomous();
		
		checkForCancer();

		return currentGroup.size() > 1;
	}

	protected static int[] calculateDimensions(String content) {
		String[] rows = content.split("\n");
		length = rows.length;
		width = rows.length > 0 ? rows[0].length() : 0;
		return new int[] { length, width };
	}

	// Populate the matrix into a ScanPoint object that equals the Letter name and
	// it's coordinate
	protected static void populateMatrix() {
		ScanPoint scanPoint;

		String[] rows = content.split("\n");

		// Loop through the content and build a matrix
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				String currentLetter = "" + rows[l].charAt(w);
				Point2D coordinate = new Point2D.Double(w, l);

				String groupName = currentLetter;
				scanPoint = new ScanPoint(new String(currentLetter), coordinate, false, groupName);

				mriMatrix.add(scanPoint);

			}
		}
	}


	// Check for Letter Block
	protected static void checkForLetterBlock() {

		for (int i = 1; i < mriMatrix.size(); i++) {
			// Determine if previous letter in the row is the same
			String currentLetter = mriMatrix.get(i).getLetterName();
			String previousLetterInRow = mriMatrix.get(i - 1).getLetterName();

			//int xInt = (int) mriMatrix.get(i).getMriScanPoint().getX();
			//int yInt = (int) mriMatrix.get(i).getMriScanPoint().getY();
			String groupName = currentLetter;

			if (currentLetter.equals(previousLetterInRow)) {
				mriMatrix.get(i).setLetterBlock(true);
				mriMatrix.get(i).setGroupName(groupName);
			}

			// Determine if letter in row above is the same
			int indexForLetterAbove = (i - width) > -1 ? (i - width) : -1;
			String letterInRowAbove = indexForLetterAbove > -1 ? mriMatrix.get(indexForLetterAbove).getLetterName() : "";
			
			// If the letter above is the same then set the one below as part of the same block
			if (currentLetter.equals(letterInRowAbove)) {
				mriMatrix.get(i).setLetterBlock(true);
				mriMatrix.get(i).setGroupName(groupName);
			}

		}

	}

	// Loop through the list of block ids count how many different ids start with
	// the same letter but are different
	protected static boolean checkForCancer() {
		List<String> blockGroups = new ArrayList<String>();

		int blockIdCount = 0;
		for (int i = 0; i < mriMatrix.size(); i++) {
			String groupName = mriMatrix.get(i).getGroupName();
			blockGroups.add(groupName);
			
		}
		
		for(int x = 0;x < blockGroups.size();x++) {
			blockGroups.get(x);
			System.out.println(blockGroups.get(x));
		}
		
		

		if (blockIdCount > 1) {
			System.out.println("Cancer has been found");
		} else {
			System.out.println("NO CANCER!");
		}

		return false;
	}

	//Give the letters that are in the same block and similar id
	protected static boolean determineIfBlockIsAutonomous() {

		for (int i = 0; i < mriMatrix.size(); i++) {
			for (int x = 0; x < mriMatrix.size(); x++) {
				String letterName = mriMatrix.get(x).getLetterName();
				int xPoint = (int)mriMatrix.get(x).getMriScanPoint().getX();	
				int yPoint = (int)mriMatrix.get(x).getMriScanPoint().getY();
				if(mriMatrix.get(x).isLetterBlock()) {
					mriMatrix.get(x).setGroupName(letterName + String.valueOf(xPoint) + String.valueOf(yPoint));
					//System.out.println("Group Name -- "+mriMatrix.get(x).getGroupName());
				}
			}
			
			
//			System.out.println(mriMatrix.get(i).getMriScanPoint());
//			System.out.println(mriMatrix.get(i).getLetterName());
//			System.out.println(mriMatrix.get(i).isLetterBlock());
//			System.out.println(mriMatrix.get(i).getGroupName());
//			
//			System.out.println("**************************************************");
		}

		return false;
	}
}
