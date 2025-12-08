package org.com.DaoCategory;

public class CategoryModel {
	private int id;
    private String name;
    private String description; 
    private String imageLink; 

    public CategoryModel(int id, String name, String description, String imageLink) {
        this.id = id;
        this.name = name;
        this.description = description; 
        this.imageLink = imageLink;
    }

    public int getCategoryId() { return id; }
    public String getCategoryName() { return name; }
    public void setCategoryName(String name) { this.name = name; }
    public String getCategoryDescription() {return this.description; }
    public String getImageLink() { return this.imageLink; }
}
