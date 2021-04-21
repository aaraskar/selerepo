package com.qa.pojo;

public class Book {
	
	String name;
	String isbn;
	String aisle;
	String author;
	
	public Book(String name,String isbn,String aisle,String author)
	{
		this.name=name;
		this.isbn=isbn;
		this.aisle=aisle;
		this.author=author;
		
		
	}
	public Book()
	{
		
	}

	public String getName() {
		return name;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getAisle() {
		return aisle;
	}

	public String getAuthor() {
		return author;
	}
	
	
	

}
