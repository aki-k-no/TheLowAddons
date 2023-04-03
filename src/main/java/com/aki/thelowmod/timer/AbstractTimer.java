package com.aki.thelowmod.timer;

//アムルタイマーを除く全てのタイマーの親抽象クラス
public abstract class AbstractTimer {

    public abstract void setTimer(String s);

    public abstract String getDisplayText();

    public abstract boolean shouldBeShown();



}
