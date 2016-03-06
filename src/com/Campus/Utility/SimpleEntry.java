package com.Campus.Utility;

public class SimpleEntry<K extends Object, V extends Object> {
	
	private K Key;
	private V Value;
	
	public SimpleEntry(K Key, V Value) {
		this.Key = Key;
		this.Value = Value;
	}
	
	public K getKey(){
		return Key;
	}
	
	public V getValue(){
		return Value;
	}

}
