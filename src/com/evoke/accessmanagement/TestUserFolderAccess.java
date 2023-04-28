package com.evoke.accessmanagement;

import java.util.HashSet;
import java.util.Set;

public class TestUserFolderAccess {

	Folder rootFolder = null;
	Folder dir11 = null;
	Folder dir12 = null;
	Folder dir121 = null;
	Folder dir1211 = null;
	Folder dir1212 = null;
	User user1 = null;
	User user2 = null;

	public TestUserFolderAccess() {

		user1 = new User(1, "user1");
		user2 = new User(2, "user2");
	}

	private void createFolderHierarchy() {

		rootFolder = new Folder(1, "dir1");
		dir11 = new Folder(2, "dir1.1");
		dir12 = new Folder(3, "dir1.2");
		dir121 = new Folder(4, "dir1.2.1");
		dir1211 = new Folder(5, "dir1.2.1.1");
		dir1212 = new Folder(6, "dir1.2.1.2");

		dir121.addChildFolder(dir1211);
		dir121.addChildFolder(dir1212);
		dir12.addChildFolder(dir121);

		rootFolder.addChildFolder(dir12);
		rootFolder.addChildFolder(dir11);
		
		System.out.println("**** List of folders *****");
		Folder.printAllFolders(rootFolder);
		System.out.println("**********************");

	}

	private void grantUser1Access() {
		//user1 is given access to only Root folder(dir1)
		rootFolder.addUserAccess(user1);
	}

	private void grantUser2Access() {
		// user2 is given access to multiple folders like (dir1,dir1.1,dir1.2,dir1.2.1)
		dir12.addUserAccess(user2);
		dir121.addUserAccess(user2);
		dir11.addUserAccess(user2);
		
	}

	private void testUser2Access() {
		grantUser2Access();
		Set<String> accessFolderList = new HashSet<>();
		Folder.printUserAccessibleFolders(user2, rootFolder, accessFolderList);
		System.out.println("List of access folders for  " + user2.getName());
		accessFolderList.forEach(f -> System.out.println(f));
		System.out.println("**********************************");
	}

	public void testUser1Access() {

		grantUser1Access();

		Set<String> accessFolderList = new HashSet<>();
		Folder.printUserAccessibleFolders(user1, rootFolder, accessFolderList);

		System.out.println("List of access folders for  " + user1.getName());
		accessFolderList.forEach(f -> System.out.println(f));
		System.out.println("**********************************");
	}

	public static void main(String[] args) {

		TestUserFolderAccess fileSystem = new TestUserFolderAccess();
		fileSystem.createFolderHierarchy();
		
		
		fileSystem.testUser1Access(); //user1 is given access to only Root folder(dir1)
		fileSystem.testUser2Access(); // user2 is given access to multiple folders like (dir1,dir1.1,dir1.2,dir1.2.1)
		
		
	}

}
