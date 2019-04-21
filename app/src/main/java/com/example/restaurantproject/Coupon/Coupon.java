package com.example.restaurantproject.Coupon;

/*Class to contain a user's coupons*/
public class Coupon {
    private String uID;
    private String name;
    private String desc;
    private String enddate;
    private String code;
    private int numOfUses;

    public Coupon(String uID, String name, String desc, String enddate, String code, int numOfUses){
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
    public String getuID() { return uID; }
    public void setuID(String uID) { this.uID = uID; }
    public int getNumOfUses() { return numOfUses; }
    public void setNumOfUses(int numOfUses) { this.numOfUses = numOfUses; }
}
