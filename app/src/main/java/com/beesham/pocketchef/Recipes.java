package com.beesham.pocketchef;
public class Recipes {
	String title = null;
	String description = null;
	String link = null;
	String img = null;
	
	public Recipes (String t, String d, String l,String i){
		title = t;
		description = d;
		link = l;
		img = i;
	}
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Recipes(){
		
	}
	public  String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
