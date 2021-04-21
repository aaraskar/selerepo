package com.qa.utils;

public class AddPlace {
	
	public int accuracy;
	public String name;
	public String phone_number;
	public String address;
	public String[] types;
	public String website;
	public String language;
	public Location location;
	
	public AddPlace(int accuracy,String name,String phone_number,String adress,String[] types,
			String website,String language,Location location)
	{
		this.accuracy=accuracy;
		this.name=name;
		this.phone_number=phone_number;
		this.address=address;
		this.types=types;
		this.website=website;
		this.language=language;
		this.location=location;
	}

}
