package com.aki.thelowmod.event;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.api.ArmorAlert;
import com.aki.thelowmod.api.TheLowLogin;
import com.aki.thelowmod.chat.ChatReceiver;
import com.aki.thelowmod.config.AKITheLowModConfigCore;
import com.aki.thelowmod.config.DataStorage;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.AmeretatChecker;
import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Events {

    @SubscribeEvent
    public void everyTick(TickEvent.PlayerTickEvent event){
        AKITheLowMod.proxy.everyTick(event);
        ArmorAlert.checkArmorDamage();
        if(event.player.isUser()) {
            TheLowLogin.loginUpdater();
            if(!ModCoreData.isDungeonDataLoaded){
                TheLowLogin.dungeonLoader();
            }
            if(!ModCoreData.isAPISubscribed){
                TheLowLogin.apiSubscriber();
            }
            if(!ModCoreData.isPlayerDataLoaded || TheLowLogin.playerAPITimer()>300){
                TheLowLogin.playerLoader();
            }
            try {
                TheLowLogin.playerNameUpdater();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(ModCoreData.wasInTheLow){
                    for(int i=0;i<ModCoreData.skillPlace.length;i++){
                        if(ModCoreData.skillPlace[i]!=null){
                            if(ModCoreData.cts.containsKey(ModCoreData.skillPlace[i])){
                                if(AKITheLowUtil.calcTimeDifference(ModCoreData.cts.get(ModCoreData.skillPlace[i]),LocalDateTime.now())<-3){
                                    ModCoreData.cts.remove(ModCoreData.skillPlace[i]);
                                    ModCoreData.skillPlace[i]=null;

                                }
                            }
                        }
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            try{
                AmeretatChecker.checkAmere();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }



    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        // コンフィグが変更された時に呼ばれる。
        if (event.modID.equals(AKITheLowMod.MODID))
            AKITheLowModConfigCore.syncConfig();
    }

    @SubscribeEvent
    public void everyWorldTick(TickEvent.ClientTickEvent event){

    }

    @SubscribeEvent
    public void whenJoined(EntityJoinWorldEvent event){
        if(event.entity instanceof EntityPlayer){

        }


    }



    @SubscribeEvent
    public void chatReceived(ClientChatReceivedEvent e){
        if(e.message.getUnformattedText().startsWith("$api")){
            if(e.message.getUnformattedText().split("api ").length != 2){

                return;
            }
            try{
                ChatReceiver.distribute(e.message.getUnformattedText().split("api ")[1]);
            }catch(Exception ex){
                ex.printStackTrace();
            }

            if(DataStorage.shouldDeleteAPIOutput){
                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void amereAttackEvent(AttackEntityEvent e){
        if(e.entityPlayer.isUser()){
            if(HoldingItem.holdingItems==null){
                return;
            }if(HoldingItem.holdingItems.getDisplayName()==null){
                return;
            }
            if(HoldingItem.holdingItems.getDisplayName().startsWith("§4§lAmərətāt")){
                ModCoreData.lastAmeretat=LocalDateTime.now();
            }
        }
    }
}
