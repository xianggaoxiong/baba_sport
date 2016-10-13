package com.bestseller.common;

public class PictureResult {
	public String path;
	public String url;
	public PictureResult() {
		super();
	}
	public PictureResult(String path, String url) {
		super();
		this.path = path;
		this.url = url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
