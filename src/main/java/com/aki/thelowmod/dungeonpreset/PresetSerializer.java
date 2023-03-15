package com.aki.thelowmod.dungeonpreset;

import akka.japi.pf.FI;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.types.DungeonData;
import com.aki.thelowmod.types.Response;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.aki.thelowmod.AKITheLowMod.gson;

public class PresetSerializer {

    public static String FILE_POS="dungeon_preset.dat";

//    public static void serializePresetData(){
//        ObjectOutputStream oos =null;
//        try {
//            oos=new ObjectOutputStream(Files.newOutputStream(Paths.get("dungeon_preset.dat")));
//            oos.writeObject(DungeonPresets.presets);
//            System.out.println("データ保存");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            try{
//                oos.close();
//            } catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//    }


    public static void inputPresetData(){

        String text=loadFile();
        try {
            Type type = new TypeToken<Set<Preset>>() {}.getType();
            Set<Preset> dungeonPreset = gson.fromJson(text, type);
            DungeonPresets.presets=dungeonPreset;
        }catch(Exception e){
            e.printStackTrace();
            DungeonPresets.presets=null;
        }

    }

    public static void outputPresetData(){
        Gson gson = new Gson();
        String text = gson.toJson(DungeonPresets.presets);
        saveFile(text);
    }

    public static String loadFile(){
        FileInputStream stream=null;
        String streamToString=null;
        try{
            stream = new FileInputStream(FILE_POS);
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            Stream<String> streamOfString= new BufferedReader(inputStreamReader).lines();
             streamToString= streamOfString.collect(Collectors.joining());

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                stream.close();
            }catch(Exception e){
                e.printStackTrace();

            }
        }
        return streamToString;
    }

    public static void saveFile(String text){
        FileOutputStream stream=null;

        try{
            stream=new FileOutputStream(FILE_POS,false);
            stream.write(text.getBytes());
        }catch(Exception e){

        }finally{
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
//    public static void deserializePresetData(){
//        ObjectInputStream ois =null;
//        try{
//            ois = new ObjectInputStream(new FileInputStream("dungeon_preset.dat"));
//            DungeonPresets.presets = (Set) ois.readObject();
//            System.out.println("データ復元");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally{
//           try {
//               ois.close();
//           }catch(Exception e){
//               e.printStackTrace();
//           }
//        }
//    }
}
