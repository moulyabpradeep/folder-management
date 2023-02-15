package com.awpl.folder.exception;

/**
 * Custom Exception while creation of any folder/file 
 * 
 * @author Moulya_P
 *
 */
public class UnAuthorizedFolderException extends Exception {
	
	private static final long serialVersionUID = -3455893828439024846L;
	
	public UnAuthorizedFolderException() {
        super("Invalid Access - Un authorized folder or file creation");
    }
    public UnAuthorizedFolderException(String message) {
        super(message);
    }
}
