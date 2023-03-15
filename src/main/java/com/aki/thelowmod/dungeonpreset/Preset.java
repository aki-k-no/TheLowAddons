package com.aki.thelowmod.dungeonpreset;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Preset  implements Serializable {
    public String presetName;
    public Set<ItemWithNameAndUUID> items;

    public Preset(String presetName){
        this.presetName=presetName;
        this.items=new HashSet<ItemWithNameAndUUID>();
    }

    public void addItem(ItemStack item){
        if(item==null || AKITheLowUtil.getTheLowSeedValue(item)==null){
            AKITheLowUtil.showInChat("§b【！】手にアイテムを持っていない、若しくは追加できませんでした");
            return;
        }
        for(ItemWithNameAndUUID testItem:items){
            if(testItem.uuid.equals(AKITheLowUtil.getTheLowSeedValue(item))){
                AKITheLowUtil.showInChat("§b【！】このアイテムはすでに追加済みです");
                return;
            }
        }

        this.items.add(new ItemWithNameAndUUID(item));
        AKITheLowUtil.showInChat(item.getDisplayName()+"§6をプリセット "+this.presetName+"に追加しました");

    }

    public boolean removeItem(ItemStack item){
        if(item==null){
            AKITheLowUtil.showInChat("§b【！】手にアイテムを持っていない、若しくはTheLowの固有アイテムではないので削除できませんでした");
            return false;
        }
        for(ItemWithNameAndUUID testItem:items){
            if(testItem.uuid.equals(AKITheLowUtil.getTheLowSeedValue(item))){
                this.items.remove(testItem);
                AKITheLowUtil.showInChat(HoldingItem.holdingItems.getDisplayName()+"§6をプリセット "+this.presetName+"から削除しました");
                return true;
            }
        }
        AKITheLowUtil.showInChat("§b【！】このアイテムはそもそも追加されていないか、すでに削除済みです");
        return false;


    }

    public void showAllItem(){
        AKITheLowUtil.showInChat("§6プリセット "+this.presetName+"に含まれるアイテム一覧");
        for(ItemWithNameAndUUID item:items){
            AKITheLowUtil.showInChat(item.item_name);
        }

    }

    public void showMissingItem(){
        AKITheLowUtil.showInChat("§6プリセット "+this.presetName+"に比べて足りないアイテム");
        InventoryPlayer inventory= Minecraft.getMinecraft().thePlayer.inventory;
        ItemStack[] mainInv=inventory.mainInventory;
        ItemStack[] armorInv=inventory.armorInventory;

        Set<ItemWithNameAndUUID> copied=new HashSet<ItemWithNameAndUUID>();
        for(ItemWithNameAndUUID item:items){
            copied.add(item.clone());
        }

        boolean flag=false;
        for(ItemWithNameAndUUID item:items){
            flag=false;
            for(ItemStack testItem:mainInv){
                if(testItem==null)continue;
                if(AKITheLowUtil.getTheLowSeedValue(testItem)==null)continue;
                if(AKITheLowUtil.getTheLowSeedValue(testItem).equals(item.uuid)){
                    flag=true;
                }
            }for(ItemStack testItem2:armorInv){
                if(testItem2==null)continue;
                if(AKITheLowUtil.getTheLowSeedValue(testItem2)==null)continue;
                if(AKITheLowUtil.getTheLowSeedValue(testItem2).equals(item.uuid)){
                    flag=true;
                }
            }
            if(!flag){
                AKITheLowUtil.showInChat(item.item_name);

            }
        }

    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Preset)) {
            return false;
        }else{
            Preset compareObj=(Preset)obj;
            if(this.presetName.equals(compareObj.presetName))return true;
            else return false;
        }
    }

    @Override
    public int hashCode() {
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e){}

        byte[] res = sha256.digest(this.presetName.getBytes());
        return (int)(res[0]*Math.pow(2,7)+res[1]*Math.pow(2,6)+res[2]*Math.pow(2,5)+res[3]*Math.pow(2,4)+res[4]*Math.pow(2,3)
                +res[5]*Math.pow(2,2) +res[6]*Math.pow(2,1) +res[7]);
    }

    public void addHotBarItem(){
        ItemStack[] items=Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
        for(int i=0;i<9;i++){
            if(items[i]==null){
                AKITheLowUtil.showInChat("§b【！】このスロットにはアイテムが入ってません");
                continue;
            }
            addItem(items[i]);
        }

    }
}

