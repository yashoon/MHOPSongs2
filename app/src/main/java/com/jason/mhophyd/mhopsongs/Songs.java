package com.jason.mhophyd.mhopsongs;

public class Songs {

    private String sNumber;
    private String sName;
    private String sIndex;
    private String sSong;


    public String getsNumber() {
        return sNumber;
    }

    public void setsNumber(String sNumber) {
        this.sNumber = sNumber;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsIndex() {
        return sIndex;
    }

    public void setsIndex(String sIndex) {
        this.sIndex = sIndex;
    }

    @Override
    public String toString() {
        return this.getsName();
    }
//    public String getIndexList() {
//        return this.getsIndex();
//    }

    public String getsSong() {
        return sSong;
    }

    public void setsSong(String sSong) {
        this.sSong = sSong;
    }
}
