package com.awpl.folder.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import com.awpl.folder.exception.UnAuthorizedFolderException;
import com.awpl.folder.manager.FolderManager;

/**
 * Util for Directory and file creation
 * 
 * @author Moulya_P
 *
 */
public final class FolderUtil {

	/**
	 * create Directory
	 * 
	 * @param directoryName
	 * @return
	 * @throws UnAuthorizedFolderException
	 */
	public static boolean createDirectory(String directoryName) throws UnAuthorizedFolderException {
		boolean isDirectoryCreated = false;
		try {
			
			File fileDir = new File(directoryName);
			if(!fileDir.exists()) {
				isDirectoryCreated = new File(directoryName).mkdirs();
				if(isDirectoryCreated) {
					System.out.println(directoryName+ " created..");
					return isDirectoryCreated;
				} else {
					throw new UnAuthorizedFolderException();
				}
			} else {
				System.out.println("Folder already exists..");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnAuthorizedFolderException();
		}
	}
	
	/**
	 * create File
	 * 
	 * @param fileName
	 * @param content
	 * @return
	 * @throws UnAuthorizedFolderException
	 */
	public static boolean createFile(String fileName, String content) throws UnAuthorizedFolderException {
		try {
			File file = new File(fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnAuthorizedFolderException();
		}
	}
}
