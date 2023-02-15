package com.awpl.folder.exception;

/**
 * Custom Exception thrown when ambiguous to create file in same directory depth based on provided validation
 * 
 * @author Moulya_P
 *
 */
public class CantDecideOnDirectoryException extends Exception {
	
	private static final long serialVersionUID = -6731956292171768177L;
	
	public CantDecideOnDirectoryException() {
        super("Duplicate file name - Can't decide on the directory");
    }
    public CantDecideOnDirectoryException(String message) {
        super(message);
    }
}
