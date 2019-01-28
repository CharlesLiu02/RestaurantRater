package com.example.basiclogins;

public class Restaurant {
    //name, cuisine, rating, link to website/yelp, price, address
    private String name;
    private String cuisine;
    private float rating; // .5 - > 1 increments of .5
    private String websitelink;
    private String address;
    private int price; //1 -> 5 increments of one

    //need a default constructor for Backendless
    public Restaurant() {
    }

    public Restaurant(String name, String cuisine, float rating, String websitelink, String address, int price) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.websitelink = websitelink;
        this.address = address;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getWebsitelink() {
        return websitelink;
    }

    public void setWebsitelink(String websitelink) {
        this.websitelink = websitelink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", rating=" + rating +
                ", websitelink='" + websitelink + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }
}
