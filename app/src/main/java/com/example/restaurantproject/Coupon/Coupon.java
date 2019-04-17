package com.example.restaurantproject.Coupon;

public class Coupon {
    private int id;
    private String uID;
    private String name;
    private String desc;
    private String enddate;
    private String code;
    private int numOfUses;

    public Coupon(int id, String uID, String name, String desc, String enddate, String code, int numOfUses){
        this.id = id;
        this.uID = uID;
        this.name = name;
        this.desc = desc;
        this.enddate = enddate;
        this.code = code;
        this.numOfUses = numOfUses;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    public String getEnddate() { return enddate; }
    public void setEnddate(String enddate) { this.enddate = enddate; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getuID() { return uID; }
    public void setuID(String uID) { this.uID = uID; }
    public int getNumOfUses() { return numOfUses; }
    public void setNumOfUses(int numOfUses) { this.numOfUses = numOfUses; }
}
