package com.awpl.folder.exception;

/**
 * Custom Exception during Json file parse
 * 
 * @author Moulya_P
 *
 */
public class InvalidJSONFileException extends Exception {
	
	private static final long serialVersionUID = 8026615798993801870L;
	
	public InvalidJSONFileException() {
        super("Invalid JSON file - The File content is not a valid JSON format");
    }
    public InvalidJSONFileException(String message) {
        super(message);
    }
}
