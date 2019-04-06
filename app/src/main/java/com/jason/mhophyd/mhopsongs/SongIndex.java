package com.jason.mhophyd.mhopsongs;

public class SongIndex {



        private String iNumber;
        private String iName;


        @Override
        public String toString() { return this.getiName();    }

    public String getiNumber() {
        return iNumber;
    }

    public void setiNumber(String iNumber) {
        this.iNumber = iNumber;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}
