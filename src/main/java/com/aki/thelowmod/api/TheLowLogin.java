package com.aki.thelowmod.api;

import com.aki.thelowmod.chat.ChatSender;
import com.aki.thelowmod.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.Mod;

import java.time.LocalDateTime;
import java.util.Iterator;

public class TheLowLogin {
    public static long loginTimer(){

        if(ModCoreData.loggedTime != null && AKITheLowUtil.isInTheLow()){
            LocalDateTime nowTime=LocalDateTime.now();
            return (AKITheLowUtil.calcTimeDifference(nowTime,ModCoreData.loggedTime));
        }
        return 0;
    }

    public static long playerAPITimer(){

        if(ModCoreData.lastPlayerRequest != null && AKITheLowUtil.isInTheLow()){
            LocalDateTime nowTime=LocalDateTime.now();
            return (AKITheLowUtil.calcTimeDifference(nowTime,ModCoreData.lastPlayerRequest));
        }else if(!ModCoreData.isPlayerDataLoaded && AKITheLowUtil.isInTheLow()){

            return 10000;
        }
        return 0;
    }

    public static void loginUpdater(){
        boolean isInTheLow=AKITheLowUtil.isInTheLow();
        if(!ModCoreData.hasBeenInTheLow[0] && isInTheLow){
            ModCoreData.loggedTime= LocalDateTime.now();
            if(ModCoreData.isFirstLogin)AKITheLowUtil.description();
        }else if(!isInTheLow && !ModCoreData.hasBeenInTheLow[0]){
            ModCoreData.isDungeonDataLoaded=false;
            ModCoreData.isPlayerDataLoaded=false;
            ModCoreData.isAPISubscribed=false;
            ModCoreData.isAlreadyKaihou=false;
            ModCoreData.isAlreadyYochou=false;
            for(int i=0;i<ModCoreData.skillPlace.length;i++){
                ModCoreData.skillPlace[i]=null;
            }
            ModCoreData.dungeonData=null;
        }
        for(int i=0;i<9;i++){
            ModCoreData.hasBeenInTheLow[i]=ModCoreData.hasBeenInTheLow[i+1];
        }
        ModCoreData.hasBeenInTheLow[9]=isInTheLow;
        ModCoreData.wasInTheLow=ModCoreData.hasBeenInTheLow[0];


    }

    public static void dungeonLoader(){
        if(loginTimer()>30){
            ModCoreData.isDungeonDataLoaded=true;
            ChatSender.sendDungeonAPIChat();
        }
    }
    public static void playerLoader(){
        if(playerAPITimer()>300 && loginTimer()>30){
            ModCoreData.isPlayerDataLoaded=true;
            ChatSender.sendPlayerAPIChat();
            ModCoreData.lastPlayerRequest=LocalDateTime.now();

            ModCoreData.playerInfos=Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        }
    }

    public static void apiSubscriber(){
        if(loginTimer()>5){
            ModCoreData.isAPISubscribed=true;
            ChatSender.sendAPISubscribeChat();
        }
    }
    public static void playerNameUpdater(){
        Iterator<NetworkPlayerInfo> iterator=null;
        if(ModCoreData.playerInfos !=null){
            iterator=ModCoreData.playerInfos.iterator();
        }

        if(iterator != null){
            while(iterator.hasNext()) {
                NetworkPlayerInfo ni = iterator.next();
                try {
                    String mcid = "";
                    if (ni == null) return;
                    if (ni.getDisplayName() == null) return;
                    if (ni.getDisplayName().getUnformattedText() == null) return;
                    if (ni.getDisplayName().getUnformattedText().split(" ").length != 2) {
                        mcid = ni.getDisplayName().getUnformattedText();
                    } else {
                        mcid = ni.getDisplayName().getUnformattedText().split(" ")[1];

                    }
                    int swords = -1;
                    int bows = 0;
                    int magics = 0;
                    if (ModCoreData.player_status.get(mcid) != null) {

                        swords = ModCoreData.player_status.get(mcid).swordStatus.reincCount;
                        bows = ModCoreData.player_status.get(mcid).bowStatus.reincCount;
                        magics = ModCoreData.player_status.get(mcid).magicStatus.reincCount;
                    }
                    String displayName = "";
                    if (ni.getDisplayName().getUnformattedText().split(" ").length != 2) {
                        displayName = ni.getDisplayName().getUnformattedText();
                    } else {
                        displayName = ni.getDisplayName().getUnformattedText().split(" ")[1];
                    }
                    ni.setDisplayName(new ChatComponentText(AKITheLowUtil.addReincTag(swords + bows + magics) + displayName));
                    if (AKITheLowUtil.isSpecialName(displayName)) {
                        ni.setDisplayName(new ChatComponentText(AKITheLowUtil.addSpecialReincTag(displayName, swords + bows + magics) + displayName));
                    }
                }catch(Exception e){
                    //人数が多いときに例外吐いてるのでは説に従って意味もなくcatchしてみる
                }
            }
        }
    }


}
