package com.me.keepme.keepme.model;

/**
 * Created by JiginVp on 6/29/2017.
 */

public class GroupModel {
    private int id;

    public GroupModel(){

    }

    public GroupModel(int icon,String name){
        this.name = name;
        this.icon = icon;
    }
    public GroupModel(int icon,String name,int grpId){
        this.name = name;
        this.icon = icon;
        this.id = grpId;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private int icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
