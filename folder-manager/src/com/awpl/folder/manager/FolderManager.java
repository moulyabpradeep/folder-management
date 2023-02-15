package com.awpl.folder.manager;

import java.util.Iterator;
import java.util.Map.Entry;

import com.awpl.folder.exception.CantDecideOnDirectoryException;
import com.awpl.folder.exception.InvalidInputException;
import com.awpl.folder.exception.UnAuthorizedFolderException;
import com.awpl.folder.util.FolderUtil;
import com.awpl.folder.util.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Folder Manager DO to hold folder parse
 * 
 * @author Moulya_P
 *
 */
public final class FolderManager {
	
	/**
	 * Json file provided
	 */
	private static final String JSON_FILE_NAME = "folder.json";
	
	/**
	 * file content
	 */
	private static final String FILE_CONTENT = "Hello World";
	
	/**
	 * folder separator
	 */
	private static final String FOLDER_SEPARATOR = "/";
	
	/**
	 * file name inputted by user
	 */
	private String fileName = null;
	
	/**
	 * boolean value to check whether file exists in json file
	 */
	private boolean isGivenFileAvailable = false;
	
	/**
	 * folder of inputted file
	 */
	private String givenFilePathDirectory = "";
	
	//Keep Subsequent folder structure
	/**
	 * maintain latest found folder based on condition
	 */
	private String immediateFolder = null;
	
	/**
	 * corresponding child count of files/folders
	 */
	private int givenDirectoryChildCount = 0;
	
	/**
	 * maintain child count of files/folders latest found folder based on condition
	 */
	private int immediateFolderChildCount = 0;
	
	/**
	 * singleton object creation
	 */
	private static FolderManager folderManager = new FolderManager();
	
	private FolderManager() {
	}
	
	public synchronized static FolderManager instantiate() {
		return folderManager;
	}
	
	/**
	 * generate directory folder structure 
	 * 
	 * @param fileName
	 */
	public void generateDirectoryStructure(String fileName) {
		try {
			this.fileName = fileName;
			//parse Json file
			JsonNode jsonNode = JSONUtil.readJSONFromFile(JSON_FILE_NAME);
			// json node as object
			if(jsonNode.isObject()) {
				traverseObject(jsonNode);
			} else if(jsonNode.isArray()) {
				// json node as array
				traverseObject(jsonNode);
			} else if(jsonNode.isTextual()) {
				// json node as text
				checkGivenFileExists(jsonNode);
			} else {
				System.out.println("Unknown - Out of scope");
			}
			
			if(isGivenFileAvailable) {
				
				String finalFolderPath =  immediateFolder;
				
				System.out.println("Given File <"+fileName+"> is availble under Folder <"+finalFolderPath+">");
				
//				finalFolderPath=finalFolderPath.substring(finalFolderPath.indexOf("/"),finalFolderPath.length());
				
				// create root directory if file is available
				boolean isRootFolderCreated = FolderUtil.createDirectory(finalFolderPath);
				
				if(isRootFolderCreated) {
					// create file under root directory
					FolderUtil.createFile(finalFolderPath+fileName, FILE_CONTENT);
					System.out.println("File has been created under folder <"+immediateFolder+">");
				} else {
					throw new UnAuthorizedFolderException();
				}
			} else {
				throw new InvalidInputException("Cannot create the file");
			}
		} catch (InvalidInputException e) {
			e.printStackTrace();
		} catch (UnAuthorizedFolderException e) {
			e.printStackTrace();
		} catch (CantDecideOnDirectoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("System Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * traverse over json object
	 * 
	 * @param jsonNode
	 * @throws CantDecideOnDirectoryException
	 */
	private void traverseObject(JsonNode jsonNode) throws CantDecideOnDirectoryException {
		Iterator<Entry<String, JsonNode>> jsonArrayNodeIterator =  jsonNode.fields();
		while(jsonArrayNodeIterator.hasNext()) {
			//node key and value
			Entry<String, JsonNode> keyEntry = jsonArrayNodeIterator.next();
			JsonNode eachJsonNode = keyEntry.getValue();
			//iterate over nodes of array
			if(eachJsonNode.isArray()) {
				//maintain folder structure and its count of children
				givenFilePathDirectory += keyEntry.getKey() + FOLDER_SEPARATOR;
				givenDirectoryChildCount = eachJsonNode.size();
				
				for (JsonNode jsonArrayNode : eachJsonNode) {
					if(jsonArrayNode.isTextual()) {
						// if node is of text type, check whether it contains inputted file
						checkGivenFileExists(jsonArrayNode);
					} else if(jsonArrayNode.isObject()) {
						// traverse over object
						traverseObject(jsonArrayNode);
					}
				}
				//for subsequent files, start traversing on root parent folder
				if(givenFilePathDirectory.contains("/")) {
					givenFilePathDirectory = givenFilePathDirectory.substring(0, givenFilePathDirectory.indexOf("/")+1);
				}
			}
		}
	}
	
	/**
	 * check whether given file exists with additional conditions
	 * 
	 * @param jsonNode
	 * @throws CantDecideOnDirectoryException
	 */
	private void checkGivenFileExists(JsonNode jsonNode) throws CantDecideOnDirectoryException {
		//compare inputted file with file found from json
		if(jsonNode.asText().equals(fileName)) {
			if(immediateFolder == null) {
				immediateFolder = givenFilePathDirectory;
				immediateFolderChildCount = givenDirectoryChildCount;
			} else {
				//Checking the depth of file existence
				if(immediateFolder.split("/").length == givenFilePathDirectory.split("/").length) {
					//check for other file/folder count in case of same depth
					if(immediateFolderChildCount > givenDirectoryChildCount) 
						immediateFolder = givenFilePathDirectory;
					//if same children count then throw exception
					else if(immediateFolderChildCount == givenDirectoryChildCount)
						throw new CantDecideOnDirectoryException();
					//check on least depth folder
				} else if(immediateFolder.split("/").length > givenFilePathDirectory.split("/").length) {
					immediateFolder = givenFilePathDirectory;
				}				
			}
			isGivenFileAvailable = true;
		}
		
	}
}
