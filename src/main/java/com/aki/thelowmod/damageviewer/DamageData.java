package com.aki.thelowmod.damageviewer;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class DamageData {

    DamageList dps;
    DamageList dpm;
    DamageList dp5s;

    float prevHP;

    public DamageData(float firstHP){
        this.dps=new DamageList(1);
        this.dp5s=new DamageList(5);
        this.dpm=new DamageList(60);
        this.prevHP=firstHP;
    }

    public void calcDamage(float nowHP){
        if(prevHP<nowHP){ //回復した場合
            this.dps.addDamage(0);
            this.dp5s.addDamage(0);
            this.dpm.addDamage(0);
        }else{
            float damaged=prevHP-nowHP;
            this.dps.addDamage(damaged);
            this.dp5s.addDamage(damaged);
            this.dpm.addDamage(damaged);
        }
        prevHP=nowHP; //データ更新
    }

    public float getDPS(){
        return this.dps.totalDamage;
    }
    public float getDP5S(){
        return this.dp5s.totalDamage;
    }
    public float getDPM(){
        return this.dpm.totalDamage;
    }

    class DamageList{

        int limit;
        LinkedList<Pair<LocalDateTime,Float>> damagedlist;
        float totalDamage;

        //limit: 区切り時間(s)
        public DamageList(int limit){
            this.limit=limit;
            this.damagedlist=new LinkedList<Pair<LocalDateTime, Float>>();
            this.totalDamage=0;

        }
        public void addDamage(float damage){
            try {
                LocalDateTime ldt = LocalDateTime.now();
                this.damagedlist.add(new Pair<LocalDateTime, Float>(ldt, damage));
                this.totalDamage += damage;
                while (!damagedlist.isEmpty() && ldt.minusSeconds(limit%60).minusMinutes(limit/60).isAfter(damagedlist.peek().getKey())) {
                    this.totalDamage -= damagedlist.pollFirst().getValue();
                }
            }catch(Exception e){
                 e.printStackTrace();
            }

        }
        public float getTotalDamage(){
            return this.totalDamage;
        }
    }

}
