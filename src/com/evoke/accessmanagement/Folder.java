package com.evoke.accessmanagement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.evoke.accessmanagement.User;

public class Folder {

	private Integer id;
	private String folderName;
	private Set<User> users;
	private List<Folder> children;
	private Folder parent;

	public Folder(Integer id, String folderName) {
		this.id = id;
		this.folderName = folderName;
		users = new HashSet<>();
		children = new ArrayList<>();
		parent = null;
	}

	

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public List<Folder> getChildren() {
		return children;
	}

	public void setChildren(List<Folder> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Folder getParent() {
		return parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	public void addChildFolder(Folder childFolder) {
		childFolder.setParent(this);
		this.children.add(childFolder);
	}

	public void addUserAccess(User user) {
		this.users.add(user);
		if (this.parent != null) {
			this.parent.users.add(user);
		}
	}

	public void removeUserAccess(User user) {
		if (this.users.contains(user)) {
			this.users.remove(user);
		}

	}

	public static void printUserAccessibleFolders(User inputUser, Folder rootFolder, Set<String> accessFolderList) {

		if (rootFolder.getParent() == null) {
			if (rootFolder.getUsers().contains(inputUser)) {
				accessFolderList.add(rootFolder.getFolderName());
			}
		}

		rootFolder.getChildren().forEach(folder -> {
			if (folder.getUsers().contains(inputUser)) {
				accessFolderList.add(folder.getFolderName());

			}
			printUserAccessibleFolders(inputUser, folder, accessFolderList);

		});
	}

	public static void printAllFolders(Folder rootFolder) {

		if (rootFolder.getParent() == null) {
			System.out.println(rootFolder.getFolderName());
		}
		rootFolder.getChildren().forEach(folder -> {
			System.out.println(folder.getFolderName());
			printAllFolders(folder);
		});

	}
	
	public static void findFolderAndPrintUsers(Folder rootFolder, Integer childFolderId) {
		
		if(rootFolder.getId() == childFolderId) {
			rootFolder.getUsers().forEach(u -> System.out.println(u.getName()));
			return;
		}
		rootFolder.getChildren().forEach(childFolder -> {
			if(childFolder.getId() == childFolderId) {
				childFolder.getUsers().forEach(u -> System.out.println(u.getName()));
				return;
			}
			findFolderAndPrintUsers(childFolder,childFolderId);
		});
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		return Objects.equals(id, other.id);
	}

}
