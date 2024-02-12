package com.aki.thelowmod.damageviewer;

public class Pair<E,T> {

    public E key;
    public T value;

    public Pair(E key,T value){
        this.key=key;
        this.value=value;
    }

    public E getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
