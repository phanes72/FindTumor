package com.silver;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
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
				// System.out.println("CurrentLetter" + currentLetter);
				Point2D coordinate = new Point2D.Double(w, l);
				scanPoint = new ScanPoint(new String(currentLetter), coordinate); // System.out.println(scanPoint.getLetterName());
																					// System.out.println(scanPoint.getMriScanPoint());

				mriMatrix.add(scanPoint);

			}
		}
	}

	protected static void getUniqueLettersInTheMatrix() {
		for(int i = 0; i < mriMatrix.size();i++) {
			listOfUniqueLetters.add(mriMatrix.get(i).getLetterName());
		}
		System.out.println("List of Unique Letters " + listOfUniqueLetters);
	}
	
	// Check for Letter Block
	protected static void checkForLetterBlock() {
		

		for (int l = 0; l < length; l++) {	
			
			for (int w = 0; w < width; w++) {				
				
				if (mriMatrix.get(l).getLetterName().contains(mriMatrix.get(w).getLetterName())) {
					//Get the data about the current coordinate
					Point2D cursorLocation = mriMatrix.get(w).getMriScanPoint();
					
					if (cursorLocation.distanceSq(l, w) >= 1) {
						mriMatrix.get(w).setLetterBlock(true);

						System.out.println("mriMatrix Letter -- " + mriMatrix.get(w).getLetterName());
						System.out.println("mriMatrix Coordinates -- " + mriMatrix.get(w).getMriScanPoint());
						System.out.println("Is Letter part of a block - " + mriMatrix.get(w).isLetterBlock());
						System.out.println("****************************************");
						System.out.println();
					}
				}
			}
		}
	}

	protected static boolean checkForCancer() {
		int numberOfBlocks = 0;
		for (int i = 0; i < mriMatrix.size(); i++) {
			if (mriMatrix.get(i).isLetterBlock()) {
				numberOfBlocks++;

				if (numberOfBlocks > 0) {
					System.out.println("The patient has cancer.");
				} else {
					System.out.println("All clear!  No Cancer!!");
				}
			}
		}

		return false;
	}
}
