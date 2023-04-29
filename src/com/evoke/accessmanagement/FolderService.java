package com.evoke.accessmanagement;

import java.util.Set;

public interface FolderService {

	public Set<Folder> findUserAccessibleFolders(String userName);
	
	public void assignUserAccessToFolder(String userName, String folderId);
	
	public Set<User> findUsersByFolder(String folderId);
}
