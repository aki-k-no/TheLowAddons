package com.aki.thelowmod.chat;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.types.DungeonData;
import com.aki.thelowmod.types.Player_Status;
import com.aki.thelowmod.types.Response;
import com.google.common.reflect.TypeToken;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.lang.reflect.Type;

import static com.aki.thelowmod.AKITheLowMod.gson;

public class ChatPlayerAnalyzer {
    public static void analyzePlayer(String text){
        Type type=new TypeToken<Response<Player_Status>>() {}.getType();
        Response<Player_Status> playerData=gson.fromJson(text, type);
        ModCoreData.player_status.put(playerData.response.mcid,playerData.response);
    }

    public static void addPlayerReinc(){

    }
}
