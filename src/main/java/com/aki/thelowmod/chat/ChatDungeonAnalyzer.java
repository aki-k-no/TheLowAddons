package com.aki.thelowmod.chat;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.types.DungeonData;
import com.aki.thelowmod.types.Response;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import static com.aki.thelowmod.AKITheLowMod.gson;

public class ChatDungeonAnalyzer {

    public static void analyzeDungeon(String text){
        Type type=new TypeToken<Response<DungeonData[]>>() {}.getType();
        Response<DungeonData[]> dungeonData=gson.fromJson(text, type);
        ModCoreData.dungeonData=dungeonData.response;


    }
}
