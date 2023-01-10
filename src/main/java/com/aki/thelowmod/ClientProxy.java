package com.aki.thelowmod;

import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;

import static com.aki.thelowmod.holding.HoldingItem.*;

public class ClientProxy extends CommonProxy{

    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    private int his1=0;

    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    public void everyTick(TickEvent.PlayerTickEvent event){
        super.everyTick(event);

        if(event.player.isUser()){
            hisChanger(event);
            //event.player.addChatMessage(new ChatComponentText(event.player.inventory.currentItem+" "+event.player.getDisplayName().getFormattedText()));

            itemSetter(event);
        }

    }

    private void hisChanger(TickEvent.PlayerTickEvent event){
        his1=event.player.inventory.currentItem;

    }

    private void itemSetter(TickEvent.PlayerTickEvent event){
         HoldingItem.holdingItems=event.player.inventory.mainInventory[his1];

    }

}
