package com.awpl.folder.main;

import java.util.Scanner;

import com.awpl.folder.manager.FolderManager;

/**
 * Generate Directory Structure based on input
 * 
 * @author Moulya_P
 *
 */
public class GenerateDirectoryStructureMain {
	
	/**
	 * main method where execution starts
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		String inputFleName = null;
		if(args.length != 1) {
			System.out.println("Enter JSON File name with full relative path: ");
			//Read input from input stream
			Scanner scanner = new Scanner(System.in);
			inputFleName = scanner.nextLine();
			System.out.println("Given file path - " + inputFleName);
			scanner.close();
		} else {
			inputFleName = args[0];
		}
		FolderManager folderManager = FolderManager.instantiate();
		//generate directory structure taking input file
		folderManager.generateDirectoryStructure(inputFleName);
	}
}
