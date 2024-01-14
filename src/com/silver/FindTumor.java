package com.silver;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FindTumor {
	private static int width;
	private static int length;
	private static String content;
	private static Set<String> listOfUniqueLetters = new HashSet<String>();

	private static ArrayList<String> groupList = new ArrayList<String>();
	private static ArrayList<ScanPoint> mriMatrix = new ArrayList<>();

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

	protected static boolean hasMultipleGroups(String content) {
		// String[] rows = content.split("\n");
		Set<Character> currentGroup = new HashSet<>();

		populateMatrix();

		getUniqueLettersInTheMatrix();

		checkForLetterBlock();

		checkForCancer();

		return currentGroup.size() > 1;
	}

	protected static int[] calculateDimensions(String content) {
		String[] rows = content.split("\n");
		length = rows.length;
		width = rows.length > 0 ? rows[0].length() : 0;
		return new int[] { length, width };
	}

	// Populate the matrix into a ScanPoint object that contains the Letter name and
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

	protected static void getUniqueLettersInTheMatrix() {
		for (int i = 0; i < mriMatrix.size(); i++) {
			listOfUniqueLetters.add(mriMatrix.get(i).getLetterName());
		}
		System.out.println("List of Unique Letters " + listOfUniqueLetters);
	}

	// Check for Letter Block
	protected static void checkForLetterBlock() {
		Iterator<String> uniqueLetterIter = listOfUniqueLetters.iterator();

		// while(uniqueLetterIter.hasNext()) {
		// String uniqueLetter = uniqueLetterIter.next();
		// System.out.println("UNIQUE LETTER -- " + uniqueLetter);
		// System.out.println();

		for (int i = 1; i < mriMatrix.size(); i++) {
			// Determine if previous letter in the row is the same
			String currentLetter = mriMatrix.get(i).getLetterName();
			String previousLetterInRow = mriMatrix.get(i - 1).getLetterName();

			int xInt = (int) mriMatrix.get(i).getMriScanPoint().getX();
			int yInt = (int) mriMatrix.get(i).getMriScanPoint().getY();
			String groupName = currentLetter + xInt + yInt;

			if (currentLetter.contains(previousLetterInRow)) {
				mriMatrix.get(i).setLetterBlock(true);
				mriMatrix.get(i).setGroupName(groupName);
			}

			// Determine if letter in row above is the same
			int indexForLetterAbove = (i - width) > -1 ? (i - width) : -1;
			String letterInRowAbove = indexForLetterAbove > -1 ? mriMatrix.get(indexForLetterAbove).getLetterName()
					: "";
			if (currentLetter.equals(letterInRowAbove)) {
				mriMatrix.get(i).setLetterBlock(true);
				mriMatrix.get(i).setGroupName(groupName);
			}

		}

	}

	protected static boolean checkForCancer() {
		determineIfBlockIsAutonomous(mriMatrix);
		
		int numberOfBlocks = 0;
		for (int i = 0; i < mriMatrix.size(); i++) {


			if (mriMatrix.get(i).isLetterBlock()) {
				numberOfBlocks++;

				if (numberOfBlocks > 1) {
					// System.out.println("The patient has cancer.");
				} else {
					// System.out.println("All clear! No Cancer!!");
				}
			}
		}

		return false;
	}

	protected static boolean determineIfBlockIsAutonomous(ArrayList<ScanPoint> mriMatrix) {

		for (int i = 0; i < mriMatrix.size(); i++) {
			String group = mriMatrix.get(i).getGroupName();

			if (group.length() > 1) {

				char xPoint = group.charAt(1);
				char yPoint = group.charAt(2);

				//Look for related Blocks and assign a mutual identifier				
				for (int x = 0; x < mriMatrix.size(); x++) {
					String groupName = mriMatrix.get(x).getGroupName();
					
					System.out.println("###");
					System.out.println("groupName--" + groupName);
					if (groupName.length() > 1) {
						
						char xGroup = groupName.charAt(1);
						char yGroup = groupName.charAt(2);

						
						//if xPoint is within 1 space of xGroup and yPoint is equal to yGroup then same block 
						int xDiff = xPoint - xGroup;
						xDiff = xDiff < 1 ? xDiff * -1 : xDiff;
												
						//if xPoint is within 1 space of xGroup and yPoint is equal to yGroup then same block
						int yDiff = yPoint - yGroup;
						yDiff = yDiff < 1 ? yDiff * -1 : yDiff;
						
						
						if(xDiff <= 1 && yDiff <= 1) {
							mriMatrix.get(x).setGroupName(group);
						}
							
						
						
						System.out.println("groupName--" + mriMatrix.get(x).getGroupName());
						System.out.println("***********************************************");
					}

				}
			}

		}

		return false;
	}
}
