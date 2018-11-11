package com.dist.entity;

import java.io.Serializable;

import lombok.Data;

@Data //神注解https://www.cnblogs.com/daimajun/p/7136078.html
public class UserEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String pas;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPas() {
		return pas;
	}

	public void setPas(String pas) {
		this.pas = pas;
	}

	@Override
	public String toString() {
		return "UserEntity [name=" + name + ", pas=" + pas + "]";
	} 
	
	
}
