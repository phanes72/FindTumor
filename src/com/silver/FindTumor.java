package com.silver;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FindTumor {

    public static void main(String[] args) {
        args = new String[1];
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

        //Loop through the rows
        for (int i = 0; i < rows.length; i++) {
            
        	//Loop through the columns
        	for (int j = 0; j < rows[i].length(); j++) {
                char currentChar = rows[i].charAt(j);

                if (currentChar != ' ' && currentGroup.size() == 0) {
                    currentGroup.add(currentChar);

                    if (j > 0 && rows[i].charAt(j - 1) != ' ' && rows[i].charAt(j - 1) != currentChar) {
                        return true;
                    }

                    if (i > 0 && rows[i - 1].charAt(j) != ' ' && rows[i - 1].charAt(j) != currentChar) {
                        return true;
                    }
                }
            }
        }

        return currentGroup.size() > 1;
    }

    private static int[] calculateDimensions(String content) {
        String[] rows = content.split("\n");
        int length = rows.length;
        int width = rows.length > 0 ? rows[0].length() : 0;
        return new int[]{length, width};
    }
}
