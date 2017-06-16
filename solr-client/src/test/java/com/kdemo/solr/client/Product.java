package com.kdemo.solr.client;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class Product {
	
	@Field
	private String id;
	
	@Field
	private String title;
	
	@Field
	private double price;
	
	@Field
	private List<String> tags;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + ", tags=" + tags + "]";
	}
}
