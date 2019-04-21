package com.example.restaurantproject.FoodMenu;

/*Class used to contain the details of each food item in the menu*/
public class Food {
    private String name;
    private int price;
    private String type;
    private String des;
    private int img;
    private int id;

    public Food(int id,String name, int price, String des){
        this.id=id;
        this.name=name;
        this.price=price;
        this.des=des;

    }
    public String getName(){
        return this.name;
    }
    public int getImg(){
        return this.img;
    }
    public int getPrice(){
        return this.price;
    }
    public String getType(){
        return this.type;
    }
    public String getDes(){
        return this.des;
    }
    public int getID(){return this.id;}
    public void setImg(int img){
        this.img=img;
    }
    public void setName(String name){ this.name=name; }
    public void setPrice(int price){
        this.price=price;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setDes(String des){ this.des= des; }
    public void  setId(int id){this.id=id;}
}
