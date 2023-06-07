package com.aki.thelowmod.api;

public class Coordinates implements Cloneable{
    public double x,y,z;

    public Coordinates(double x ,double y ,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    @Override
    public Coordinates clone(){
        return new Coordinates(this.x,this.y,this.z);
    }

    @Override
    public String toString(){
        return " x:"+this.x+" y:"+this.y+" z:"+this.z;
    }
}
