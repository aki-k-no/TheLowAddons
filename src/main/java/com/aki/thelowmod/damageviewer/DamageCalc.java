package com.aki.thelowmod.damageviewer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.config.DataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.GuiIngameForge;

import java.util.HashMap;
import java.util.Map;

public class DamageCalc {

    public static String nowBossName=null;
    public static EntityLivingBase currentBoss=null;

    public static Map<String,DamageData> HPmap=new HashMap<String, DamageData>();

    public static String getBossName(String subtitle){
        if(subtitle.split("§c 【").length!=2){
            return null;
        }
        return subtitle.split("§c 【")[0];
    }
    public static float getBossHP(String bossName){
        EntityLivingBase e=AKITheLowUtil.getEntityFromBossName(bossName);
        currentBoss=e;
        if(e==null)return -1;
        return e.getHealth();

    }
    public static void calcBossDamage(){



        String s=AKITheLowUtil.getCurrentActionBar();

        nowBossName=getBossName(s);
        if(nowBossName==null || nowBossName.equals("")){
            nowBossName="";
            return;
        }

        float nowBossHealth= (float) Math.ceil(getBossHP(nowBossName));
        if(nowBossHealth<-0.5f)return;

        if(HPmap.containsKey(nowBossName)){
            DamageData dd=HPmap.get(nowBossName);
            dd.calcDamage(nowBossHealth);
        }else{
            HPmap.put(nowBossName,new DamageData(nowBossHealth));
        }

    }

    public static void RenderDPS(GuiIngameForge gif, ScaledResolution sclRes){


        float nowBossHealth= (float) Math.ceil(getBossHP(nowBossName));
        if(nowBossHealth<-0.5f)return;

        DamageData dd;
        if(HPmap.containsKey(nowBossName)){
            dd=HPmap.get(nowBossName);
        }else{
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.scale(DataStorage.renderDPSSize,DataStorage.renderDPSSize,DataStorage.renderDPSSize);
        GlStateManager.translate((int) (sclRes.getScaledWidth()*DataStorage.renderDPSX/DataStorage.renderDPSSize),(int) (sclRes.getScaledHeight()*DataStorage.renderDPSY/DataStorage.renderDPSSize),0);
        Gui.drawRect( -1, -1,120,41,0x3Fb1ebff);
        gif.getFontRenderer().drawString(nowBossName, 0, 0, 16777215, true);
        gif.getFontRenderer().drawString("DPS: "+AKITheLowUtil.round_off(dd.getDPS(),3), 0, 8, 16777215, true);
        gif.getFontRenderer().drawString("DPS(5s): "+AKITheLowUtil.round_off(dd.getDP5S()/5.0f,3), 0, 16, 16777215, true);
        gif.getFontRenderer().drawString("DPS(60s): "+AKITheLowUtil.round_off(dd.getDPM()/60.0f,3), 0, 24, 16777215, true);
        gif.getFontRenderer().drawString("DPM: "+AKITheLowUtil.round_off(dd.getDPM(),3), 0, 32, 16777215, true);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.disableAlpha();
    }



}
