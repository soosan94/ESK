package com.example.owner.esk;

public class TravelItem {
    private int id;
    private String nation;
    public int getId(){
        return id;
    }

    public String getName(){
        return nation;
    }

    public TravelItem(int id,String nation){
        this.id=id;
        this.nation=nation;
    }
}

