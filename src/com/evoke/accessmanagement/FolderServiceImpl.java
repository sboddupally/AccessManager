package com.evoke.accessmanagement;

import java.util.HashSet;
import java.util.Set;

public class FolderServiceImpl implements FolderService {

	private Folder rootFolder = null;

	public FolderServiceImpl(Folder rootFolder) {
		this.rootFolder = rootFolder;
	}

	@Override
	public Set<Folder> findUserAccessibleFolders(String userName) {

		Set<Folder> accessibleFolders = new HashSet<>();
		
		User user = new User(userName);
		if (rootFolder.getUsers().contains(user)) {
			accessibleFolders.add(rootFolder);
		}
		recursive(user,rootFolder, accessibleFolders);
		return accessibleFolders;
	}

	private void recursive(User user, Folder parentFolder, Set<Folder> accessibleFolders) {
		parentFolder.getChildren().forEach(childFolder -> {
			if (childFolder.getUsers().contains(user)) {
				accessibleFolders.add(childFolder);
			}
			recursive(user,childFolder,accessibleFolders);
		});
	}
	

	@Override
	public void assignUserAccessToFolder(String userName, String folderId) {
		
		if(rootFolder.getId().equals(folderId)) {
			rootFolder.addUserAccess(userName);
			return;
		}
		
		rootFolder.getChildren().forEach(childFolder -> {
			if(childFolder.getId().equals(folderId)) {
				childFolder.addUserAccess(userName);
				return;
			}
			recursiveLookUpAccessibleFolders(userName,childFolder,folderId);
		});
		
	}
	
	private void recursiveLookUpAccessibleFolders(String userName, Folder parentFolder, String folderId) {
		parentFolder.getChildren().forEach(childFolder -> {
			if(childFolder.getId().equals(folderId)) {
				childFolder.addUserAccess(userName);
				return;
			}
			recursiveLookUpAccessibleFolders(userName,childFolder,folderId);
		});
	}

	@Override
	public Set<User> findUsersByFolder(String folderId) {
        Set<User> users = new HashSet<>();
        
		if(rootFolder.getId().equals(folderId)) {
			users.addAll(rootFolder.getUsers());
			return users;
		}
		
		rootFolder.getChildren().forEach(childFolder -> {
			if(childFolder.getId().equals(folderId)) {
				users.addAll(childFolder.getUsers());
				return;
			}
			recursiveLookUpForFoldersByFolderId(childFolder,folderId, users);
		});
		return users;
	}
	
	private void recursiveLookUpForFoldersByFolderId(Folder parentFolder, String folderId, Set<User> users) {
		parentFolder.getChildren().forEach(childFolder -> {
			if(childFolder.getId().equals(folderId)) {
				users.addAll(childFolder.getUsers());
				return;
			}
			recursiveLookUpForFoldersByFolderId(childFolder,folderId, users);
		});
	}

}
