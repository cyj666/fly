package com.fly.pojo;

import java.io.Serializable;

/**
 * 标签类
 * @author DELL
 *
 */
public class Tab implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8253463529533754184L;
	
	private Integer id;
	private String name; //标签名
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Tab [id=" + id + ", name=" + name + "]";
	}
	
	
	

}
