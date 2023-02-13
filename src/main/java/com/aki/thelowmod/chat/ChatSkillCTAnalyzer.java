package com.aki.thelowmod.chat;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.types.Response;
import com.aki.thelowmod.types.SkillCoolTime;
import com.google.common.reflect.TypeToken;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static com.aki.thelowmod.AKITheLowMod.gson;

public class ChatSkillCTAnalyzer {

    public static void uniqueSkills(SkillCoolTime sc){
        if(sc.name.equals("予兆")){
            sc.name="予兆タイマー";
            sc.cooltime=30.0;
            ModCoreData.isAlreadyYochou=true;
        }else if(sc.name.equals("開放")){
            sc.name="開放タイマー";
            sc.cooltime=30.0;
            ModCoreData.isAlreadyKaihou=true;
        }else if(sc.name.equals("覚醒")){
            ModCoreData.isAlreadyKaihou=false;
        }else if(sc.name.equals("ステッドショック") && sc.cooltime>60){
            sc.cooltime=60.0;
        }
    }

    public static void analyzeCT(String text){
        Type type=new TypeToken<Response<SkillCoolTime>>() {}.getType();
        Response<SkillCoolTime> ct=gson.fromJson(text, type);

        uniqueSkills(ct.response);
        if(!ModCoreData.cts.containsKey(ct.response.name)){
            for(int i=0;i<ModCoreData.skillPlace.length;i++){
                if(ModCoreData.skillPlace[i]==null){
                    ModCoreData.skillPlace[i]=ct.response.name;
                    break;
                }
            }
        }



        ModCoreData.cts.put(ct.response.name, LocalDateTime.now().plusSeconds(ct.response.cooltime.longValue()).plusNanos(Math.round((ct.response.cooltime-ct.response.cooltime.longValue())*1000000000)));


    }


}
