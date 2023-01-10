package com.aki.thelowmod.types;

//ここら辺もTheLowExtendを参考にさせて頂きました。
public class Response<T> {
    public String apiType;
    public Integer version;
    public T response;

}
