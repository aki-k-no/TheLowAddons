package com.aki.thelowmod.gui;

import com.aki.thelowmod.AKITheLowMod;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.config.DataStorage;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;
import com.aki.thelowmod.types.SkillCoolTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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
            GlStateManager.scale(DataStorage.renderCTSize,DataStorage.renderCTSize,DataStorage.renderCTSize);
            gif.getFontRenderer().drawString(key+" "+timer(key), sclRes.getScaledWidth()*DataStorage.renderCTX/DataStorage.renderCTSize, sclRes.getScaledHeight()*(DataStorage.renderCTY+determineY(key)*0.03f)/DataStorage.renderCTSize, 16777215, true);
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
        if(!DataStorage.showAmereTimer){
            if(diff[1]<=25L && diff[0]==6 && DataStorage.playAmereSound){
                Minecraft.getMinecraft().thePlayer.playSound("akithelowmod:amere",1f,1.00f);
            }
            return "";
        }
        if(HoldingItem.holdingItems==null){
            return "";
        }
        if(! HoldingItem.holdingItems.getDisplayName().startsWith("§4§lAmərətāt")){
            return "";
        }
        if(diff[0]>=6){
            if(diff[1]<=25L && diff[0]==6 && DataStorage.playAmereSound){
                Minecraft.getMinecraft().thePlayer.playSound("akithelowmod:amere",DataStorage.amereSoundVolume,1.00f);
            }
            return "アムル§2使用可能";
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
        if(HoldingItem.holdingItems!=null && HoldingItem.holdingItems.getDisplayName()!=null && HoldingItem.holdingItems.getDisplayName().startsWith("§4§lAmərətāt") && DataStorage.showAmereTimer){
            Gui.drawRect((int) (sclRes.getScaledWidth()*(DataStorage.utilityCTX-0.002f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize), (int) (sclRes.getScaledHeight()*(DataStorage.utilityCTY-0.002f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize), (int) (sclRes.getScaledWidth()*(DataStorage.utilityCTX+0.152f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize), (int) (sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.15f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize),0x3Fb1ebff);
        }else{
            Gui.drawRect((int) (sclRes.getScaledWidth()*(DataStorage.utilityCTX-0.002f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize), (int) (sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.048f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize), (int) (sclRes.getScaledWidth()*(DataStorage.utilityCTX+0.152f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize), (int) (sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.15f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize),0x3Fb1ebff);
        }

        gif.getFontRenderer().drawString(amereTimer(), sclRes.getScaledWidth()*DataStorage.utilityCTX/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY)/DataStorage.utilityCTSize, 16777215, true);

        showYK(gif,sclRes);

        GlStateManager.popMatrix();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.disableAlpha();
    }
    public static void showYK(GuiIngameForge gif, ScaledResolution sclRes){

        if(ModCoreData.isAlreadyYochou){
            gif.getFontRenderer().drawString("予兆§2済", sclRes.getScaledWidth()*(DataStorage.utilityCTX)/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.05f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize, 16777215, true);
        }else{
            gif.getFontRenderer().drawString("予兆§4未", sclRes.getScaledWidth()*(DataStorage.utilityCTX)/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.05f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize, 16777215, true);
        }
        if(ModCoreData.isAlreadyKaihou){
            gif.getFontRenderer().drawString("開放§2済", sclRes.getScaledWidth()*DataStorage.utilityCTX/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.1f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize, 16777215, true);
        }else{
            gif.getFontRenderer().drawString("開放§4未", sclRes.getScaledWidth()*DataStorage.utilityCTX/DataStorage.utilityCTSize, sclRes.getScaledHeight()*(DataStorage.utilityCTY+0.1f*DataStorage.utilityCTSize)/DataStorage.utilityCTSize, 16777215, true);
        }

    }
}
