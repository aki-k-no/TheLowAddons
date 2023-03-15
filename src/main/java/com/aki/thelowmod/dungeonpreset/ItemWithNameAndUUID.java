package com.aki.thelowmod.dungeonpreset;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.io.Serializable;

public class ItemWithNameAndUUID implements Serializable,Cloneable {
    public String item_name;
    public Long uuid;

    public ItemWithNameAndUUID(ItemStack item){
        this.item_name=item.getDisplayName();
        this.uuid= AKITheLowUtil.getTheLowSeedValue(item);
    }

    ItemWithNameAndUUID(String name,Long uuid){
        this.item_name=name;
        this.uuid=uuid;
    }

    @Override
    public ItemWithNameAndUUID clone(){
        return new ItemWithNameAndUUID(this.item_name,this.uuid);
    }
}
