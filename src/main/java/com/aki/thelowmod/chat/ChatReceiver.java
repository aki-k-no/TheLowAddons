package com.aki.thelowmod.chat;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.types.DungeonData;
import com.aki.thelowmod.types.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

import static com.aki.thelowmod.AKITheLowMod.gson;

public class ChatReceiver {

    //TheLowExtend参照のコーディング 大丈夫だとは思うが名前空間の関係で少しclass名をずらしてある
    public static void distribute(String text){
        Response<?> response = gson.fromJson(text, Response.class);
        if (response.version != 1) {
            return;
        }
        if(response.apiType.equals("dungeon")){
            ChatDungeonAnalyzer.analyzeDungeon(text);
        }else if(response.apiType.equals("player_status")){
            ChatPlayerAnalyzer.analyzePlayer(text);
        }else if(response.apiType.equals("skill_cooltime")){
            ChatSkillCTAnalyzer.analyzeCT(text);
        }
    }

}
