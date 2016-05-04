package com.scuwangjun.datas;

public class DataCoffee {

	private int id;
	private String name;
	private String intro;
	private String url;
	private double priceBefore;
	private double priceNow;

	public DataCoffee(int id, String name, String intro, String url,
			double priceBefore, double priceNow) {
		super();
		this.id = id;
		this.name = name;
		this.intro = intro;
		this.url = url;
		this.priceBefore = priceBefore;
		this.priceNow = priceNow;
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

	public double getPriceBefore() {
		return priceBefore;
	}

	public void setPriceBefore(double priceBefore) {
		this.priceBefore = priceBefore;
	}

	public double getPriceNow() {
		return priceNow;
	}

	public void setPriceNow(double priceNow) {
		this.priceNow = priceNow;
	}

}
