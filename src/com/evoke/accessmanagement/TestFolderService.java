package com.evoke.accessmanagement;

import java.util.Set;
import java.util.stream.Collectors;

public class TestFolderService {

	Folder folderA = null;
	Folder folderB = null;
	Folder folderC = null;
	Folder folderD = null;
	Folder folderE = null;
	Folder folderF = null;
	
	User user1 = null;
	User user2 = null;
	
	FolderService folderService = null;
	
	public TestFolderService() {
		
		user1 = new User(1, "user1");
		user2 = new User(2, "user2");
	}
	
	private void createFolderHierarchy() {
		
		
		folderA = new Folder("A", "A");
		folderB = new Folder("A/B", "B");
		folderC = new Folder("A/C", "C");
		folderD = new Folder("A/B/D", "D");
		folderE = new Folder("A/B/E", "E");
		folderF = new Folder("A/C/F", "F");

		folderB.addChildFolder(folderD);
		folderB.addChildFolder(folderE);
		folderC.addChildFolder(folderF);

		folderA.addChildFolder(folderB);
		folderA.addChildFolder(folderC);
		
		folderService = new FolderServiceImpl(folderA);
	}

	private void grantUserAccess(String userName, String folderId) {
		folderService.assignUserAccessToFolder(userName, folderId);
		//folderE.addUserAccess("user2");
	}
	
	public void testUserAccessibleFolders(String userName) {
		Set<Folder> userFolders = folderService.findUserAccessibleFolders(userName);
		Set<String> folderNames = userFolders.stream().map(f->f.getFolderName()).collect(Collectors.toSet());
		String result = String.join(",", folderNames);
		if(result != null && result.length() > 0) {
			System.out.println(userName+" has access to  folders with Name : " +result);
		}else {
			System.out.println(userName+" has No access to  any folders");
		}
		
		
	}
	
	public void testFindUsersByFolderId(String folderId) {
		Set<User> userFolders = folderService.findUsersByFolder(folderId);
		Set<String> userNames = userFolders.stream().map(u->u.getUserName()).collect(Collectors.toSet());
		String result = String.join(",", userNames);
		if(result != null && result.length() > 0) {
			System.out.println(result+" have access to folder with Id "+folderId);
		}else {
			System.out.println("No user have access to folder with Id "+folderId);
		}
		
		
	}
	
	
	public static void main(String[] args) {

		TestFolderService testCase = new TestFolderService();
		
		testCase.createFolderHierarchy();
		
		testCase.grantUserAccess("user1", "A/B/E"); // granting Folder 'E' (A/B/E is folderId for E) access to user1. 
		//testCase.grantUserAccess("user1", "A/B/D");// granting Folder 'D' (A/B/D is folderId for D) access to user1. 
		
		testCase.testUserAccessibleFolders("user1"); // user1 should have now access to A,B,E
		
		
		testCase.grantUserAccess("user2", "A/C/F");
		testCase.testUserAccessibleFolders("user2");
		
		
		testCase.testFindUsersByFolderId("A");
		testCase.testFindUsersByFolderId("A/B/D");
		
		testCase.testFindUsersByFolderId("A/B/E");
		

		
	}
	
}
