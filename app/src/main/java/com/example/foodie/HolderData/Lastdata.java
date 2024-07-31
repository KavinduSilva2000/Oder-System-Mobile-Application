package com.example.foodie.HolderData;

public class Lastdata {
    public Lastdata() {
    }

    String pid,pname, quantity;

    public Lastdata(String pid, String pname, String quantity) {
        this.pid = pid;
        this.pname = pname;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
