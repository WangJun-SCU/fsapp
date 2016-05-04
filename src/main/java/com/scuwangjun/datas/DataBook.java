package com.scuwangjun.datas;

public class DataBook {

	private int id;  //数据库id
	private String name;  //书名
	private String intro;  //书的简介
	private String url;  //图片地址

	public DataBook(int id, String name, String intro, String url) {
		super();
		this.id = id;
		this.name = name;
		this.intro = intro;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
