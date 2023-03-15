package com.aki.thelowmod.dungeonpreset;

import com.aki.thelowmod.api.AKITheLowUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DungeonPresets implements Serializable {

    public static Set<Preset> presets=null;


    public static void init(){
        loadPresetDat();
    }

    public static List<String> getListOfPreset(){
        List<String> result=new ArrayList<String>();
        for(Preset preset:presets){
            result.add(preset.presetName);
        }
        return result;
    }

    private static void loadPresetDat(){
        PresetSerializer.inputPresetData();
        if(presets==null){
            presets=new HashSet<Preset>();
        }
    }

    public static void addPreset(String presetName){
        if(getPresetByName(presetName)!=null){
            AKITheLowUtil.showInChat("§b【！】すでに同名のプリセット "+presetName+"が存在しています");
        }else{
            AKITheLowUtil.showInChat("§6プリセット "+presetName+"を追加しました");
            presets.add(new Preset(presetName));
        }
    }

    public static void deletePreset(String presetName){
        if(getPresetByName(presetName)!=null){
            AKITheLowUtil.showInChat("§6プリセット "+presetName+"を削除しました");
            for(Preset preset:presets){
                if(preset.presetName.equals(presetName)){
                    presets.remove(preset);
                }
            }

        }else{
            AKITheLowUtil.showInChat("§b【！】プリセット "+presetName+"は存在しないか、すでに削除されています");
        }
    }

    public static Preset getPresetByName(String presetName){
        for(Preset preset:presets){
            if(preset.presetName.equals(presetName)){
                return preset;
            }
        }
        return null;
    }

}
