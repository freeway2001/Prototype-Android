package com.beyondsoft.prototype.model;

public class ListViewItem {
	private String imageurl;
	private String title;
	private String brief;
	
	/**
	 * @return the imageurl
	 */
	public String getImageurl() {
		return imageurl;
	}
	/**
	 * @param imageurl the imageurl to set
	 */
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	public ListViewItem()
	{
		this.brief = "";
		this.imageurl = "";
		this.title = "";
	}
}
