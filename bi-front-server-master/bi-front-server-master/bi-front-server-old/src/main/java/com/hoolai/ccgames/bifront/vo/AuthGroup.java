package com.hoolai.ccgames.bifront.vo;

import java.util.Map;
import java.util.TreeMap;

public class AuthGroup {
	
	public String name;
	public Map< String, String > content;
	
	public AuthGroup() {
		name = new String();
		content = new TreeMap< String, String >();
	}
	
	public AuthGroup( String name ) {
		this.name = name;
		content = new TreeMap< String, String >();
	}
	
	public AuthGroup( String name, Map< String, String > content ) {
		super();
		this.name = name;
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName( String name ) {
		this.name = name;
	}
	public Map< String, String > getContent() {
		return content;
	}
	public void setContent( Map< String, String > content ) {
		this.content = content;
	}
	
	public AuthGroup addItem( String auth, String desc ) {
		this.content.put( auth, desc );
		return this;
	}
}
