package model;

public class Product {
	private int id;
	private String name;
	private String category;
	private String description;
	private int quality;
	private int price;
	private String color;
	private String type;
	private String size;
	private String imageURL;

	public Product() {

	}

	public Product(int id, String name, String category, String description, int quality, int price, String color,
			String type, String size, String imageURL) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.quality = quality;
		this.price = price;
		this.color = color;
		this.type = type;
		this.size = size;
		this.imageURL = imageURL;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}
