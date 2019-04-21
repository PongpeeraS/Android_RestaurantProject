package com.example.restaurantproject.Order;

/*Class to contain deliver order objects*/
public class Order {
    private String food;
    private int amount;
    private int price;
    private String address;
    private String uid;
    private boolean receive = false;

    public Order(String uid, String food, int amount, int price, String address, boolean receive){
        this.food=food;
        this.price=price;
        this.amount=amount;
        this.address=address;
        this.receive=receive;
        this.uid=uid;

    }
    public boolean isReceived(){return this.receive;}
    public  String getUID(){return this.uid;}
    public String getFood(){
        return this.food;
    }
    public int getPrice(){
        return this.price;
    }
    public String getAddress(){
        return this.address;
    }
    public int getAmount(){
        return this.amount;
    }
    public  void setUID(String uid){
        this.uid=uid;
    }
    public void setFood   (String food){
        this.food=food;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public void setAmount(int amount){
        this.amount=amount;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setRecieve(boolean recieve){this.receive=recieve;}
}

