package com.aki.thelowmod.holding;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import java.util.*;

public class HoldingItem {

    public static ItemStack holdingItems;
    public static NBTTagList Lore;

    public static List<String> getSlotsInfo(){
        List<String> list=new ArrayList<String>();
        if(Lore==null){
            return list;
        }
        boolean flag=false;
        for(int i=0;i<Lore.tagCount();i++){
            if(Lore.getStringTagAt(i).contains("[SLOT]")){
                flag=true;
            }else if(flag){
                if(Lore.getStringTagAt(i).equals("")) {
                    flag = false;
                }else{
                    list.add(Lore.getStringTagAt(i));
                }

            }
        }
        return list;

    }
    public static Double getWeaponStrength(){
        if(Lore==null){
            return null;
        }
        for(int i=0;i<Lore.tagCount();i++){
            if(Lore.getStringTagAt(i).contains("攻撃力 ： §6+")) {
                 String power=Lore.getStringTagAt(i).split("攻撃力 ： §6+")[1].trim();
                 try{
                     return Double.parseDouble(power);
                 }catch(Exception e){

                 }

            }
        }
        return null;
    }

    public static Map<String,Double> getAdditionalStrength(){
        if(Lore==null){
            return null;
        }
        boolean flag=false;
        Map<String,Double> map=new HashMap<String, Double>();
        for(int i=0;i<Lore.tagCount();i++){
            if(Lore.getStringTagAt(i).contains("[追加ダメージ]")) {
                flag=true;
            }else if(flag){
                if(Lore.getStringTagAt(i).equals("")){
                    flag=false;
                }else{

                    map.put(Lore.getStringTagAt(i).split(" ： §6+")[0],Double.parseDouble(Lore.getStringTagAt(i).split(" ： §6+")[1].trim()));
                }
            }
        }
        return map;
    }

    public static void analyzeItemLore(){
        if(holdingItems==null) return;
        if(holdingItems.getTagCompound()==null) return;
        if(!holdingItems.getTagCompound().hasKey("display")) return;
        if(!holdingItems.getTagCompound().getCompoundTag("display").hasKey("Lore")) return;
        Lore=holdingItems.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);

    }

    public static Double[] getMultiple(){
        List<String> list=getSlotsInfo();
        double zombie = 1;
        double skeleton = 1;
        double creature = 1;
        double caster = 1;
        double posing = 0;
        for(String str:list){
            Double[] result=calculateMultiple(str);
            if(result[0]==0.0){
                zombie*=result[1];
            }else if(result[0]==1.0){
                skeleton*=result[1];
            }else if(result[0]==2.0){
                creature*=result[1];
            }else if(result[0]==3.0){
                caster*=result[1];
            }else if(result[0]==4.0){
                posing+=result[1];
            }
            if(result[2]==1.06){
                zombie*=1.06;
                skeleton*=1.06;
                creature*=1.06;
            }
        }
        Double[] data=new Double[5];
        data[0]=zombie;
        data[1]=skeleton;
        data[2]=creature;
        data[3]=caster;
        data[4]=posing;

        return data;


    }

    private static Double[] calculateMultiple(String data){
        Double[] result=new Double[3];
        result[0]=result[1]=10.0;
        result[2]=1.0;
        if(data.contains("ゾンビ")){
            result[0]=0.0;
            if(data.contains("LEVEL1")){
                result[1]=1.1;
            }else if(data.contains("LEVEL2")){
                result[1]=1.15;
            }else if(data.contains("LEVEL3")){
                result[1]=1.23;
            }else if(data.contains("LEVEL4_5")){
                result[1]=1.4;
            }else if(data.contains("LEVEL4")){
                result[1]=1.35;
            }else if(data.contains("LEVEL5")){
                result[1]=1.55;
            }else if(data.contains("LEGEND")){
                result[1]=1.55;
                result[2]=1.06;
            }
        }else if(data.contains("スケルトン")){
            result[0]=1.0;
            if(data.contains("LEVEL1")){
                result[1]=1.1;
            }else if(data.contains("LEVEL2")){
                result[1]=1.15;
            }else if(data.contains("LEVEL3")){
                result[1]=1.23;
            }else if(data.contains("LEVEL4_5")){
                result[1]=1.4;
            }else if(data.contains("LEVEL4")){
                result[1]=1.35;
            }else if(data.contains("LEVEL5")){
                result[1]=1.55;
            }else if(data.contains("LEGEND")){
                result[1]=1.55;
                result[2]=1.06;
            }

        }else if(data.contains("生物")){
            result[0]=2.0;
            if(data.contains("LEVEL1")){
                result[1]=1.1;
            }else if(data.contains("LEVEL2")){
                result[1]=1.15;
            }else if(data.contains("LEVEL3")){
                result[1]=1.23;
            }else if(data.contains("LEVEL4_5")){
                result[1]=1.4;
            }else if(data.contains("LEVEL4")){
                result[1]=1.35;
            }else if(data.contains("LEVEL5")){
                result[1]=1.55;
            }else if(data.contains("LEGEND")){
                result[1]=1.55;
                result[2]=1.06;
            }

        }else if(data.contains("キャスター")){
            result[0]=3.0;
            if(data.contains("LEVEL1")){
                result[1]=0.95;
            }else if(data.contains("LEVEL2")){
                result[1]=0.9;
            }else if(data.contains("LEVEL3")){
                result[1]=0.84;
            }else if(data.contains("LEVEL4_5")){
                result[1]=0.72;
            }else if(data.contains("LEVEL4")){
                result[1]=0.77;
            }else if(data.contains("LEVEL5")){
                result[1]=0.6;
            }

        }else if(data.contains("ポーシング")){
            result[0]=4.0;
            if(data.contains("LEVEL1")){
                result[1]=0.05;
            }else if(data.contains("LEVEL2")){
                result[1]=0.1;
            }else if(data.contains("LEVEL3")){
                result[1]=0.15;
            }else if(data.contains("LEVEL4_5")){
                result[1]=0.4;
            }else if(data.contains("LEVEL4")){
                result[1]=0.3;
            }else if(data.contains("LEVEL5")){
                result[1]=0.5;
            }

        }
        return result;
    }
}
