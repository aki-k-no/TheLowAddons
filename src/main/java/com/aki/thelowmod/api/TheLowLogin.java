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
        if(!ModCoreData.wasInTheLow && isInTheLow){
            ModCoreData.loggedTime= LocalDateTime.now();
        }else if(!isInTheLow){
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
        ModCoreData.wasInTheLow=isInTheLow;


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
                NetworkPlayerInfo ni=iterator.next();
                String mcid="";
                if(ni.getDisplayName().getUnformattedText().split(" ").length!=2) {
                    mcid=ni.getDisplayName().getUnformattedText();
                }else{
                    mcid=ni.getDisplayName().getUnformattedText().split(" ")[1];

                }
                int swords=-1;
                int bows=0;
                int magics=0;
                if(ModCoreData.player_status.get(mcid) != null){

                    swords=ModCoreData.player_status.get(mcid).swordStatus.reincCount;
                    bows=ModCoreData.player_status.get(mcid).bowStatus.reincCount;
                    magics=ModCoreData.player_status.get(mcid).magicStatus.reincCount;
                }
                if(ni.getDisplayName().getUnformattedText().split(" ").length!=2){
                    ni.setDisplayName(new ChatComponentText(AKITheLowUtil.addReincTag(swords+bows+magics)+ni.getDisplayName().getUnformattedText()));
                }else{

                    ni.setDisplayName(new ChatComponentText(AKITheLowUtil.addReincTag(swords+bows+magics)+ni.getDisplayName().getUnformattedText().split(" ")[1]));
                }
            }
        }
    }


}
