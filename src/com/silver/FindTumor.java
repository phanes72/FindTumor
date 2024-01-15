package com.silver;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FindTumor {
	private static int width;
	private static int length;
	private static String content;

	private static List<ScanPoint> mriMatrix = new ArrayList<>();

	public static void main(String[] args) {

		try {
			// To run in an IDE uncomment the two lines below and run internal files from the project's resources folder
			//args = new String[1]; 
			//args[0] = "resources/test.in";

			String returnMessage;

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
					returnMessage = "True,";
				} else {
					returnMessage = "False,";
				}

				// System.out.println("Dimensions: " + dimensions[0] + " x " + dimensions[1]);
				returnMessage += dimensions[0] + "," + dimensions[1];

				System.out.println(returnMessage);

			} catch (IOException e) {
				System.err.println("Error reading file: " + e.getMessage());
				System.exit(1);
			}

		} catch (Exception e) {
			System.err.println("NA");
			System.err.println("Process finished with exit code -1");
			System.exit(1);
		}
	}

	protected static String readContent(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		return Files.readString(path);
	}

	// Main Method that calls the other utility methods
	protected static boolean hasMultipleGroups(String content) {
		//Set<Character> currentGroup = new HashSet<>();

		populateMatrix();

		checkForLetterBlock();

		determineIfBlockIsAutonomous();

		checkForCancer();

		return checkForCancer();
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
			String currentLetter = mriMatrix.get(i).getLetterName().toUpperCase();
			String previousLetterInRow = mriMatrix.get(i - 1).getLetterName().toUpperCase();

			String groupName = currentLetter;

			if (currentLetter.equals(previousLetterInRow)) {
				mriMatrix.get(i).setLetterBlock(true);
				mriMatrix.get(i).setGroupName(groupName);
			}

			// Determine if letter in row above is the same
			int indexForLetterAbove = (i - width) > -1 ? (i - width) : -1;
			String letterInRowAbove = indexForLetterAbove > -1 ? mriMatrix.get(indexForLetterAbove).getLetterName()
					: "";

			// If the letter above is the same then set the one below as part of the same
			// block
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

		int blockIdCount = 1;
		for (int i = 0; i < mriMatrix.size(); i++) {
			String groupName = mriMatrix.get(i).getGroupName();

			if (groupName.length() > 1) {
				groupName = groupName.substring(1);
				blockGroups.add(groupName);
			}

		}

		for (int x = 0; x < blockGroups.size(); x++) {
			int currentBlock = Integer.parseInt(blockGroups.get(x));

			if (blockGroups.size() > (x + 1)) {
				int nextBlock = Integer.parseInt(blockGroups.get(x + 1));

				if ((nextBlock - currentBlock) > 1) {
					blockIdCount++;
					break;
				}
			}

		}

		if (blockIdCount > 1) {
			// Cancer has been found
			return true;
		} else {
			// "NO CANCER!
			return false;
		}

	}

	// Give the letters that are in the same block and similar id
	protected static boolean determineIfBlockIsAutonomous() {

		for (int i = 0; i < mriMatrix.size(); i++) {
			for (int x = 0; x < mriMatrix.size(); x++) {
				String letterName = mriMatrix.get(x).getLetterName().toUpperCase();
				int xPoint = (int) mriMatrix.get(x).getMriScanPoint().getX();
				int yPoint = (int) mriMatrix.get(x).getMriScanPoint().getY();
				if (mriMatrix.get(x).isLetterBlock()) {
					mriMatrix.get(x).setGroupName(letterName + String.valueOf(xPoint) + String.valueOf(yPoint));
					
				}
			}

		}

		return false;
	}
}
