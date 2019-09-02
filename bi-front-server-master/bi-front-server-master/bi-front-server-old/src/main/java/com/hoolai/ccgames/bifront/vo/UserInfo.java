package com.hoolai.ccgames.bifront.vo;

public class UserInfo {

	public long uid;
	public String name;
	public String pass;
	
	public UserInfo( long uid, String name, String pass ) {
		super();
		this.uid = uid;
		this.name = name;
		this.pass = pass;

	}
	
	public long getUid() {
		return uid;
	}

	public void setUid( long uid ) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass( String pass ) {
		this.pass = pass;
	}
}
