package com.example.basiclogins;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {
    //name, cuisine, rating, link to website/yelp, price, address
    private String name;
    private String cuisine;
    private float rating; // .5 - > 1 increments of .5
    private String websiteLink;
    private String address;
    private int price; //1 -> 5 increments of one
    private String ownerId; //backendless field
    private String objectId; //backendless field

    //need a default constructor for Backendless
    public Restaurant() {
    }

    public Restaurant(String name, String cuisine, float rating, String websiteLink, String address, int price) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.websiteLink = websiteLink;
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

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", rating=" + rating +
                ", websiteLink='" + websiteLink + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        cuisine = in.readString();
        rating = in.readFloat();
        websiteLink = in.readString();
        address = in.readString();
        price = in.readInt();
        ownerId = in.readString();
        objectId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cuisine);
        dest.writeFloat(rating);
        dest.writeString(websiteLink);
        dest.writeString(address);
        dest.writeInt(price);
        dest.writeString(ownerId);
        dest.writeString(objectId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
