package com.example.foodie.HolderData;

public class EditHolder {

    private String Foodname, Discription, Price, Image, Category, Pid;

    public EditHolder() {
    }

    public EditHolder(String foodname, String discription, String price, String image, String category, String pid) {
        Foodname = foodname;
        Discription = discription;
        Price = price;
        Image = image;
        Category = category;
        Pid = pid;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }
}
