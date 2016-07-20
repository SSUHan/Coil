package com.miniandroid.myzzung.coli.model;

/**
 * Created by myZZUNG on 2016. 7. 21..
 */
public class AvatarInfo extends Info {

    public static final String TYPE_CHICKEN = "chicken";

    private String type;
    private int level;

    public AvatarInfo(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
