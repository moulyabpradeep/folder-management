package com.awpl.folder.util;

import java.io.File;
import java.io.IOException;

import com.awpl.folder.exception.InvalidInputException;
import com.awpl.folder.exception.InvalidJSONFileException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Util to read and parse Json file
 * 
 * @author Moulya_P
 *
 */
public final class JSONUtil {
	
	/**
	 * read JSON From File
	 * 
	 * @param filePath
	 * @return
	 * @throws InvalidInputException
	 * @throws InvalidJSONFileException
	 */
	public static JsonNode readJSONFromFile(String filePath) throws InvalidInputException, InvalidJSONFileException {
		ObjectMapper mapper = new ObjectMapper();
		File fileObj = new File(filePath);
		if(fileObj != null && fileObj.exists()) {
			try {
				//read json file
				return mapper.readTree(fileObj);
			} catch (IOException e) {
				throw new InvalidJSONFileException();
			}
		} else {
			throw new InvalidInputException();
		}
	}
	
	
}
