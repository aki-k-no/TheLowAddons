package com.aki.thelowmod.dungeonpreset;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class PresetSerializer {

    public static void serializePresetData(){
        ObjectOutputStream oos =null;
        try {
            oos=new ObjectOutputStream(Files.newOutputStream(Paths.get("dungeon_preset.dat")));
            oos.writeObject(DungeonPresets.presets);
            System.out.println("データ保存");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                oos.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void deserializePresetData(){
        ObjectInputStream ois =null;
        try{
            ois = new ObjectInputStream(new FileInputStream("dungeon_preset.dat"));
            DungeonPresets.presets = (Set) ois.readObject();
            System.out.println("データ復元");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
           try {
               ois.close();
           }catch(Exception e){
               e.printStackTrace();
           }
        }
    }
}
