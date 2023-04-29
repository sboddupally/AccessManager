package com.evoke.accessmanagement;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Folder {

	private String id;
	private String folderName;
	private Set<User> users;
	private Set<Folder> children;
	private Folder parent;

	public Folder(String id, String folderName) {
		this.id = id;
		this.folderName = folderName;
		users = new HashSet<>();
		children = new HashSet<>();
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

	public Set<Folder> getChildren() {
		return children;
	}

	public void setChildren(Set<Folder> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}



	public void setId(String id) {
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

	public void addUserAccess(String userName) {
		User user = new User(userName);
		this.users.add(user);
		Folder pFolder = this.parent;
		while(pFolder != null) {
			pFolder.users.add(user);
			pFolder = pFolder.parent;
		}		
	}

	public void removeUserAccess(String userName) {
		User user = new User(userName);
		if (this.users.contains(user)) {
			this.users.remove(user);
		}

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
