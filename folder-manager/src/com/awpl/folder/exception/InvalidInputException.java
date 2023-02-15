package com.awpl.folder.exception;

/**
 * Custom Exception thrown in case of invalid json file path
 * 
 * @author Moulya_P
 *
 */
public class InvalidInputException extends Exception {
	
	private static final long serialVersionUID = -2750249305596187921L;
	
	public InvalidInputException() {
        super("Invalid Input - Please make sure the given file is exists in the folder path");
    }
    public InvalidInputException(String message) {
        super(message);
    }
}
