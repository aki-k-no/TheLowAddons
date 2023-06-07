package com.aki.thelowmod.nametag;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.dungeonpreset.PresetSerializer;

public class SpecialNameTag {

    public static void init(){
        String specialTag= AKITheLowUtil.loadFile("./specialtag.dat");
        if(specialTag==null || specialTag.equalsIgnoreCase("")){
            ModCoreData.specialTag=null;
        }else{
            ModCoreData.specialTag=specialTag;
        }
    }
}
