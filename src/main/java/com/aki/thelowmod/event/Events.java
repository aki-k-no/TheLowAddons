package com.aki.thelowmod.event;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.api.ArmorAlert;
import com.aki.thelowmod.api.Coordinates;
import com.aki.thelowmod.api.TheLowLogin;
import com.aki.thelowmod.chat.ChatReceiver;
import com.aki.thelowmod.commands.GetGUIItemNBTDataCommand;
import com.aki.thelowmod.config.AKITheLowModConfigCore;
import com.aki.thelowmod.config.DataStorage;
import com.aki.thelowmod.damageviewer.DamageCalc;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.gui.OverlayRender;
import com.aki.thelowmod.holding.AmeretatChecker;
import com.aki.thelowmod.holding.HoldingItem;
import com.aki.thelowmod.holding.RoAChecker;
import javafx.scene.input.MouseDragEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Mouse;

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
                RoAChecker.checkRoA();
                AmeretatChecker.checkAmere();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            //泉のタイマーをチェックする
            LocalDateTime now=LocalDateTime.now();
            for(String key:ModCoreData.springTimer.keySet()){
                LocalDateTime limit=ModCoreData.springTimer.get(key);
                if(AKITheLowUtil.calcTimeDifference(now,limit)>0){
                    AKITheLowUtil.showInChat("§6[AKI TheLow Addons] §a"+key+"の泉のクールタイムが終了しました");
                    ModCoreData.springTimer.remove(key);
                }else if(AKITheLowUtil.calcTimeDifference(now,limit)>-31){
                    ModCoreData.springLocation.remove(key);
                }
            }
        }

        DamageCalc.calcBossDamage();
//        if(nowHealth!=Minecraft.getMinecraft().thePlayer.getHealth()){
//            AKITheLowUtil.showInChat(nowHealth-Minecraft.getMinecraft().thePlayer.getHealth());
//        }
//
//        nowHealth=Minecraft.getMinecraft().thePlayer.getHealth();


    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        // コンフィグが変更された時に呼ばれる。
        if (event.modID.equals(AKITheLowMod.MODID))
            AKITheLowModConfigCore.syncConfig();
    }





    @SubscribeEvent(receiveCanceled = true)
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
        if(e.message.getUnformattedText().contains("手に持っているアイテムを捨てられるアイテムとして設定しました。")){
            e.setCanceled(true);
            AKITheLowUtil.showInChat("手に持っているアイテムは現在§c捨てられます");
        }else if(e.message.getUnformattedText().contains("手に持っているアイテムを捨てられないアイテムとして設定しました。")){
            e.setCanceled(true);
            AKITheLowUtil.showInChat("手に持っているアイテムは現在§a捨てられません");
        }else if(AKITheLowUtil.checkRegix(e.message.getUnformattedText(),"\\[武器スキル\\].[0-9A-Za-z_]*が恵みの泉を発動")){
            if(e.message.getUnformattedText().split(" ").length==2){
                String mcid=e.message.getUnformattedText().split(" ")[1].split("が")[0];

                try {
                    if (ModCoreData.springTimer.containsKey(mcid)) {
                        ModCoreData.springTimer.remove(mcid);
                    }
                    if (ModCoreData.springLocation.containsKey(mcid)) {
                        ModCoreData.springLocation.remove(mcid);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                ModCoreData.springTimer.put(mcid,LocalDateTime.now().plusSeconds(51).plusNanos(300000000));
                try {
                    EntityPlayer ep=AKITheLowUtil.getEntityPlayerByName(mcid);
                    if(ep!=null){
                        ep.onUpdate();
                        ModCoreData.springLocation.put(mcid,new Coordinates(ep.posX,ep.posY,ep.posZ));

                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

        }

    }

    @SubscribeEvent
    public void textureReloadEvent(TextureStitchEvent.Post e){
        OverlayRender.readOverlayData();;
    }

    private static void NoThrowOK(){

    }

    @SubscribeEvent
    public void amereAttackEvent(AttackEntityEvent e){
        if(e.entityPlayer.isUser()){
            if(HoldingItem.holdingItems==null){
                return;
            }if(HoldingItem.holdingItems.getDisplayName()==null){
                return;
            }
            if((HoldingItem.holdingItems.getDisplayName().startsWith("§4§lAmərətāt") || (AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems)!=null && AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).equals("craft50weapon"))) && !(e.target instanceof EntityPlayer)){
                ModCoreData.lastAmeretat=LocalDateTime.now();
            }
        }
    }

    public static float nowHealth=0;

    @SubscribeEvent
    public void onGUIClicked(GuiScreenEvent.MouseInputEvent.Pre e){

        if(!GetGUIItemNBTDataCommand.toggled){
            return;
        }
        if(!Mouse.isButtonDown(0)){
            return;
        }
        if(Minecraft.getMinecraft().currentScreen==null){
            AKITheLowUtil.showInChat("現在GUIを開いていません");
            return;
        }
        if(Minecraft.getMinecraft().currentScreen instanceof GuiContainer){
            GuiContainer container=(GuiContainer) Minecraft.getMinecraft().currentScreen;
            if(container!=null){
                if(container.getSlotUnderMouse()!=null){
                    ItemStack item=container.getSlotUnderMouse().getStack();
                    if(item==null) {
                        AKITheLowUtil.showInChat("ここのスロットにはアイテムが存在しません");
                    }else{
                        AKITheLowUtil.showInChat("該当スロットのアイテムのデータをクリップボードにコピーしました");

                        AKITheLowUtil.copyToClipboard(item.getDisplayName()+" "+item.getTagCompound());
                    }
                }else{

                    AKITheLowUtil.showInChat("ここにはスロットが存在しません");
                }

            }


        }
    }

    @SubscribeEvent
    public void ClickEvent(MouseEvent e){
        if(e.button==1) return;
        if(e.dx!=0 || e.dy!=0) return;
        if(HoldingItem.holdingItems==null){
            return;
        }else if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems)==null){
            return;
        }else if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).equals("RaidLightReward2") || AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).equals("mainH魔法LvEliteLight1")){
            ModCoreData.RoAHandTime=LocalDateTime.now();
        }
    }



    public static boolean isBoss(String mobName){
        if(mobName==null)return false;
        if(mobName.contains("【"))return true;
        return false;
    }
}
