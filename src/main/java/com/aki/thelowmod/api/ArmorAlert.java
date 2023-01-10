package com.aki.thelowmod.api;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.config.DataStorage;
import com.aki.thelowmod.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.time.LocalDateTime;

public class ArmorAlert {

    public static void checkArmorDamage(){
        ItemStack[] items=Minecraft.getMinecraft().thePlayer.getInventory();
        for(int i=0;i<items.length;i++){
            if(items[i]!=null){
                if(items[i].isItemStackDamageable() && 1.0f-(items[i].getItemDamage()+0.0f)/items[i].getMaxDamage() <= DataStorage.alertArmorDamage){
                    if(!ModCoreData.isArmorPinch[i]){
                        ModCoreData.whenArmorBecamePinch= LocalDateTime.now();
                    }
                    ModCoreData.isArmorPinch[i]=true;
                }else{
                    ModCoreData.isArmorPinch[i]=false;
                }
            }else{
                ModCoreData.isArmorPinch[i]=false;
            }
        }
        if(ModCoreData.whenArmorBecamePinch!=null){
            Long[] time=AKITheLowUtil.calcPreciseTimeDifference(LocalDateTime.now(),ModCoreData.whenArmorBecamePinch);
            for(int i=0;i<5;i++){
                if(time[0]<1 && time[1]>i*100+75 && time[1]<i*100+125){
                    Minecraft.getMinecraft().thePlayer.playSound("random.anvil_land",0.08f,1.25f);
                }
            }
        }
    }



}
