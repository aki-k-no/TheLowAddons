package com.aki.thelowmod.api;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.config.DataStorage;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.types.DungeonData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.lwjgl.Sys;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.List;

public class AKITheLowUtil {


    private static final String THELOW_SCOREBOARD_TITLE =  EnumChatFormatting.AQUA + "===== The Low =====";

    //このメソッドはTheLowExtendの実装を参考にしました　感謝です
    public static boolean isInTheLow(){
        Minecraft minecraft = Minecraft.getMinecraft();
        ServerData server=minecraft.getCurrentServerData();

        if(server == null){
            //ぬるぽ防止
            return false;
        }else if(minecraft.theWorld == null){
            return false;
        }
        if(!(server.serverIP.startsWith("mc.exim"))){
            return false;
        }
        World world= minecraft.theWorld;
        if(world == null){
            return false;
        }
        Scoreboard sc=world.getScoreboard();
        if(sc==null){
            return false;
        }

        ScoreObjective so=sc.getObjectiveInDisplaySlot(1);
        if(so ==null){
            return false;
        }
        return THELOW_SCOREBOARD_TITLE.equalsIgnoreCase(so.getDisplayName());

    }

    public static long calcTimeDifference(LocalDateTime ldt1,LocalDateTime ldt2){
        return ldt1.toInstant(ZoneOffset.UTC).getLong(ChronoField.INSTANT_SECONDS)-ldt2.toInstant(ZoneOffset.UTC).getLong(ChronoField.INSTANT_SECONDS);
    }
    public static void showInChat(Object obj){
        if(obj==null){
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("null"));
        }else{
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(obj.toString()));
        }

    }

    public static Long[] calcPreciseTimeDifference(LocalDateTime ldt1,LocalDateTime ldt2){
        long sec=ldt1.toInstant(ZoneOffset.UTC).getLong(ChronoField.INSTANT_SECONDS)-ldt2.toInstant(ZoneOffset.UTC).getLong(ChronoField.INSTANT_SECONDS);
        long nano=ldt1.getNano()-ldt2.getNano();
        if(nano>=0){
            Long[] ans=new Long[2];
            ans[0]=sec;
            ans[1]=nano/1000000L;
            return ans;
        }else{
            Long[] ans=new Long[2];
            ans[0]=sec-1;
            ans[1]=1000+nano/1000000L;
            return ans;
        }
    }

    public static String addReincTag(int reinc){
        String s1="§f[";
        String ss1="§8[";
        String s2="§f]§r ";
        String ss2="§8]§r ";
        if (reinc >= 0) {
            if(reinc==0){
                return ss1+"§8"+reinc+ss2;
            }else if(reinc<50){
                return ss1+"§7"+reinc+ss2;
            }else if(reinc<100){
                return ss1+"§f"+reinc+ss2;
            }else if(reinc<200){
                return s1+"§f"+reinc+s2;
            }else if(reinc<300){
                return s1+"§e"+reinc+s2;
            }else if(reinc<400){
                return s1+"§b"+reinc+s2;
            }else if(reinc<500){
                return s1+"§2"+reinc+s2;
            }else if(reinc<600){
                return s1+"§4"+reinc+s2;
            }else if(reinc<700){
                return s1+"§d"+reinc+s2;
            }else if(reinc<800){
                return s1+"§9"+reinc+s2;
            }else if(reinc<900){
                return s1+"§5"+reinc+s2;
            }else if(reinc<1000){
                return s1+"§1"+reinc+s2;
            }else if(reinc<1500){
                return "§l"+s1+"§4"+(reinc/1000)+"§e"+((reinc%1000)/100)+"§a"+((reinc%100)/10)+"§1"+(reinc%10)+s2;
            }else if(reinc<20000){
                return "§l§f§k[§r§l§4"+(reinc/1000)+"§e"+((reinc%1000)/100)+"§a"+((reinc%100)/10)+"§1"+(reinc%10)+"§f§k]§r§f ";
            }else{
                return "§8[-] ";
            }
        }else{
            return "§8[-] ";
        }
    }

    //GUIの幅高さを推測
    public static Integer[] getGuiWidthHeight(GuiContainer gui){
        int max_y=0;
        int max_x=0;
        List<Slot> slots=gui.inventorySlots.inventorySlots;
        for(Slot slot : slots){
            max_x=Math.max(max_x,slot.xDisplayPosition);
            max_y=Math.max(max_y,slot.yDisplayPosition);

        }

        Integer[] data=new Integer[2];
        data[0]=((max_x+25)/2)*2;
        if(Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size()!=0 && gui instanceof GuiInventory && DataStorage.calculatePotionEffectGUI){
            data[0]=data[0]-120;
        }
        data[1]=((max_y+25)/2)*2;
        return data;
    }

    public static Field getFieldFromClass(Class clazz, String fieldName)
            throws NoSuchFieldException {
        Field field = null;
        while (clazz != null) {
            try {
                Field[] fields=clazz.getDeclaredFields();
                if(clazz.getSimpleName().equals("GuiContainer")){
                    for(int i=0;i<fields.length;i++){
                    }
                }

                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }

        if (field == null) {
            throw new NoSuchFieldException();
        }
        return field;
    }

    public static Field getFieldFromClassSP(Class clazz, String fieldName,Object obj)
            throws NoSuchFieldException {
        Field field = null;
        while (clazz != null) {
            try {
                Field[] fields=clazz.getDeclaredFields();
                if(clazz.getSimpleName().equals("GuiContainer")){
                    for(int i=0;i<fields.length;i++){
                        fields[i].setAccessible(true);
                        if(true){
                        }

                    }
                }

                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }

        if (field == null) {
            throw new NoSuchFieldException();
        }
        return field;
    }

    public static String getTheLowItemID(ItemStack item){
        if(item==null) return null;
        if(!item.hasTagCompound()) return null;
        if(!item.getTagCompound().hasKey("thelow_item_id")) return null;
        return item.getTagCompound().getString("thelow_item_id");

    }

    public static Long getTheLowSeedValue(ItemStack item){
        if(item==null) return null;
        if(!item.hasTagCompound()) return null;
        if(!item.getTagCompound().hasKey("thelow_item_seed_value") && !item.getTagCompound().hasKey("thelow_item_id")) return null;
        if(!item.getTagCompound().hasKey("thelow_item_seed_value")){
            return Long.valueOf(item.getTagCompound().getString("thelow_item_id").hashCode());
        }
        return item.getTagCompound().getLong("thelow_item_seed_value");

    }

    public static double getAdditionalHealth(ItemStack item){
        if(item==null) return 0;
        if(!item.hasTagCompound()) return 0;
        if(!item.getTagCompound().hasKey("thelow_item_add_max_health")){
            return 0;
        }
        return item.getTagCompound().getDouble("thelow_item_add_max_health");
    }

    public static double getMobArmor(ItemStack item){
        if(item==null) return 0;
        if(!item.hasTagCompound()) return 0;
        if(!item.getTagCompound().hasKey("thelow_item_normal_armor_point")){
            return 0;
        }
        return item.getTagCompound().getDouble("thelow_item_normal_armor_point");
    }

    public static double getBossArmor(ItemStack item){
        if(item==null) return 0;
        if(!item.hasTagCompound()) return 0;
        if(!item.getTagCompound().hasKey("thelow_item_boss_armor_point")){
            return 0;
        }
        return item.getTagCompound().getDouble("thelow_item_boss_armor_point");
    }

    // JavaのUnicode文字列の変換用メソッド("あ" <-> "\u3042") https://qiita.com/sifue/items/039846cf8415efdc5c92 参照
    public static String convertToUnicode(String original){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            sb.append(String.format("\\u%04X", Character.codePointAt(original, i)));
        }
        String unicode = sb.toString();
        return unicode;
    }

    public static void description(){
        showInChat("§b=================================================");
        showInChat("§b現在のバージョン: AKI's TheLow Addons v1.4");
        showInChat("§a==使用可能コマンド==");
        showInChat("§7/ghi 現在のアイテムの詳しい情報を取得");
        showInChat("§7/cit 現在のアイテムに対応するCITテクスチャのpropertiesファイルの内容をコピー");
        showInChat("§7/preset ダンジョンプリセット機能(ダンジョンごとに持っていくアイテムを保存していつでも確認可能)");
        showInChat("§a==主機能==");
        showInChat("§7CTタイマー、アムルタイマー、開放予兆マーカー");
        showInChat("§7手に持ってる武器の情報表示機能");
        showInChat("§7タブに転生数を表示");
        showInChat("§7防具の耐久アラート");
        showInChat("§7NoThrowマーカー、NoThrowショートカット(デフォルトはNキー)");
        showInChat("§cこのModはTheLow公式modやTheLowToopTipModと競合します。同時に使うと一部の機能が使えない可能性があります");
        showInChat("§b=================================================");
        ModCoreData.isFirstLogin=false;
    }

    public static DungeonData getSpecificDungeonData(String name){
        if(ModCoreData.dungeonData==null){
            return null;
        }
        for(int i=0;i<ModCoreData.dungeonData.length;i++){
            if(ModCoreData.dungeonData[i].name.equals(name)){
                return ModCoreData.dungeonData[i];
            }
        }
        return null;

    }
}
