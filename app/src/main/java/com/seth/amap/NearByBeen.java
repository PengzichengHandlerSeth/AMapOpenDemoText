package com.seth.amap;

//map.put("userid", userid);
//        map.put("longt", 39.9212);
//        map.put("lat", 116.4612);
//        map.put("gender", 2);
//        map.put("condition_gender", 1);
public class NearByBeen {
    private String userid;
    private double longt;
    private double lat;
    private int gender;
    private int condition_gender;

    public NearByBeen(String userid, double longt, double lat, int gender, int condition_gender) {
        this.userid = userid;
        this.longt = longt;
        this.lat = lat;
        this.gender = gender;
        this.condition_gender = condition_gender;
    }
    public NearByBeen(String userid, double longt, double lat, int gender) {
        this.userid = userid;
        this.longt = longt;
        this.lat = lat;
        this.gender = gender;
    }
    public NearByBeen(String userid, double longt, double lat) {
        this.userid = userid;
        this.longt = longt;
        this.lat = lat;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public void setCondition_gender(int condition_gender) {
        this.condition_gender = condition_gender;
    }

    public double getLongt() {
        return longt;
    }

    public double getLat() {
        return lat;
    }
    public int getCondition_gender() {
        return condition_gender;
    }
    public int getGender() {
        return gender;
    }

}
