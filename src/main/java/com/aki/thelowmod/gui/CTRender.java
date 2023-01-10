package com.aki.thelowmod.gui;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.config.DataStorage;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;
import com.aki.thelowmod.types.SkillCoolTime;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.GuiIngameForge;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Set;

public class CTRender {

    public static void showCT(GuiIngameForge gif, ScaledResolution sclRes){
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.8F, 0.8F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        renderStrings(gif,sclRes);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.disableAlpha();
    }

    public static void renderStrings(GuiIngameForge gif, ScaledResolution sclRes){
        Set<String> keys= ModCoreData.cts.keySet();
        int i=0;
        for(String key:keys){
            GlStateManager.pushMatrix();
            GlStateManager.scale(1f,1f,1f);
            gif.getFontRenderer().drawString(key+" "+timer(key), sclRes.getScaledWidth()*DataStorage.renderCTX, sclRes.getScaledHeight()*(DataStorage.renderCTY+determineY(key)*0.03f), 16777215, true);
            GlStateManager.popMatrix();
            i++;
        }
    }
    public static int determineY(String key){
        for(int i=0;i<ModCoreData.skillPlace.length;i++){
            if(key.equals(ModCoreData.skillPlace[i])){
                return i;
            }
        }
        ModCoreData.cts.remove(key);
        return -1;
    }
    public static String timer(String key){
        Long[] diff=AKITheLowUtil.calcPreciseTimeDifference(ModCoreData.cts.get(key), LocalDateTime.now());
        if(diff[0]>=60){
            return diff[0]/60+":"+String.format("%02d",diff[0]%60);
        }else if(diff[0]>=0){
            return "0:"+String.format("%02d",diff[0]);
        }else{
            return "使用可能";
        }
    }
    public static String amereTimer(){
        Long[] diff=AKITheLowUtil.calcPreciseTimeDifference( LocalDateTime.now(),ModCoreData.lastAmeretat);
        if(HoldingItem.holdingItems==null){
            return "";
        }
        if(! HoldingItem.holdingItems.getDisplayName().startsWith("§4§lAmərətāt")){
            return "";
        }
        if(diff[0]>=6){
            return "アムル使用可能";
        }else{
            return "アムル: 0:0"+(6-diff[0]);
        }
    }
    public static void showUtilityTimer(GuiIngameForge gif, ScaledResolution sclRes){
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.8F, 0.8F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        GlStateManager.pushMatrix();
        GlStateManager.scale(DataStorage.utilityCTSize,DataStorage.utilityCTSize,DataStorage.utilityCTSize);
        gif.getFontRenderer().drawString(amereTimer(), sclRes.getScaledWidth()*DataStorage.utilityCTX/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY)/DataStorage.utilityCTSize, 16777215, true);

        showYK(gif,sclRes);

        GlStateManager.popMatrix();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.disableAlpha();
    }
    public static void showYK(GuiIngameForge gif, ScaledResolution sclRes){
        if(ModCoreData.isAlreadyYochou){
            gif.getFontRenderer().drawString("予兆済", sclRes.getScaledWidth()*(DataStorage.utilityCTX)/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.04f)/DataStorage.utilityCTSize, 16777215, true);
        }else{
            gif.getFontRenderer().drawString("予兆未", sclRes.getScaledWidth()*(DataStorage.utilityCTX)/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.04f)/DataStorage.utilityCTSize, 16777215, true);
        }
        if(ModCoreData.isAlreadyKaihou){
            gif.getFontRenderer().drawString("開放済", sclRes.getScaledWidth()*DataStorage.utilityCTX/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.08f)/DataStorage.utilityCTSize, 16777215, true);
        }else{
            gif.getFontRenderer().drawString("開放未", sclRes.getScaledWidth()*DataStorage.utilityCTX/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.08f)/DataStorage.utilityCTSize, 16777215, true);
        }

    }
}
