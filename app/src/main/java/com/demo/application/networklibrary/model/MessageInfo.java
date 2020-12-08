package com.demo.application.networklibrary.model;

import java.io.Serializable;

/**
 * 组装你的请求数据
 */
public class MessageInfo implements Serializable {
	
	public int tag = 0;
	public String key;//数据的key
	public Object value;//对应的值

	public MessageInfo(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public MessageInfo() {
		super();

	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
