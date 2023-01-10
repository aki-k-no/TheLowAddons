package com.aki.thelowmod.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

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
}
