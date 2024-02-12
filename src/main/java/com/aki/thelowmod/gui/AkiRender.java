package com.aki.thelowmod.gui;

import com.aki.thelowmod.ClientProxy;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.config.DataStorage;
import com.aki.thelowmod.damageviewer.DamageCalc;
import com.aki.thelowmod.damageviewer.DamageData;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

public class AkiRender extends GuiIngameForge {

    private Minecraft mc;
    private ScaledResolution sclRes;


    public AkiRender(Minecraft mc) {
        super(mc);
        this.mc=mc;
        sclRes = new ScaledResolution(mc);
    }

    @SubscribeEvent
    public void renderWorldLast(RenderWorldLastEvent e){
        try{
            WorldRender.render(this,sclRes);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text event){
        sclRes = new ScaledResolution(mc);
        try {


            //手に持ってるアイテムの描画
            if (HoldingItem.holdingItems != null && DataStorage.isItemInfoVisible) {
                renderHoldingItem();
            }
            renderArmorPinch();
            CTRender.showCT(this, sclRes);
            if(ModCoreData.wasInTheLow) {
                CTRender.showUtilityTimer(this, sclRes);
            }

            //DPS表示機能
            if(DataStorage.shouldRenderDPS){
                DamageCalc.RenderDPS(this,sclRes);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @SubscribeEvent
    public void renderGUI(GuiScreenEvent.DrawScreenEvent.Pre e){

        OverlayRender.renderOverlay(this,sclRes);

    }

    @SubscribeEvent
    public void renderHotBarOverlay(RenderGameOverlayEvent e){
        if(DataStorage.showMagicStoneOverlay && e.type==RenderGameOverlayEvent.ElementType.HOTBAR){
            OverlayRender.MagicalStoneOverlayHotbar(this,sclRes);
        }
    }

    private void renderHoldingItem(){
        int line=2;
        float renderScale=0.6F*DataStorage.renderSize;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.scale(renderScale, renderScale, 1);
        GlStateManager.translate(sclRes.getScaledWidth()* DataStorage.renderHoldingItemX/renderScale, sclRes.getScaledHeight()* DataStorage.renderHoldingItemY/renderScale,0);
        this.getFontRenderer().drawString(HoldingItem.holdingItems.getDisplayName(), 0,0, 16777215, true);


        HoldingItem.analyzeItemLore();

        Double weaponStrength=HoldingItem.getWeaponStrength();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6F*DataStorage.renderSize, 0.6F*DataStorage.renderSize, 0.6F*DataStorage.renderSize);
        GlStateManager.translate((sclRes.getScaledWidth()* DataStorage.renderHoldingItemX+10)/renderScale, (sclRes.getScaledHeight()* DataStorage.renderHoldingItemY)/renderScale,0);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        if(weaponStrength!=null) {
            this.getFontRenderer().drawString("§e§l攻撃力", 0, 5*line*DataStorage.renderSize/renderScale, 16777215, true);
            line++;
            this.getFontRenderer().drawString("§6    +"+weaponStrength.toString(), 0, 5*line*DataStorage.renderSize/renderScale, 16777215, true);
            line++;
        }

        Map<String,Double> additinalStr=HoldingItem.getAdditionalStrength();
        if(additinalStr!=null && additinalStr.size()!=0) {
            this.getFontRenderer().drawString("§e§l追加ダメージ", 0, 5*line*DataStorage.renderSize/renderScale, 16777215, true);
            line++;
            for(String nameKey: additinalStr.keySet()){
                this.getFontRenderer().drawString(nameKey+" ： §6+"+additinalStr.get(nameKey), 0, 5*line*DataStorage.renderSize/renderScale, 16777215, true);
                line++;
            }

        }

        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6F*DataStorage.renderSize, 0.6F*DataStorage.renderSize, 0.6F*DataStorage.renderSize);
        GlStateManager.translate((sclRes.getScaledWidth()* DataStorage.renderHoldingItemX+10)/renderScale,  (sclRes.getScaledHeight() * DataStorage.renderHoldingItemY)/renderScale,0);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        List<String> slot=HoldingItem.getSlotsInfo();
        if(slot!=null) {
            if (slot.size() > 0) {
                slot.add(0, "§2§lSLOT情報");
            }
            for (int i = 0; i < slot.size(); i++) {
                this.getFontRenderer().drawString(slot.get(i), 0, 5*line*DataStorage.renderSize/renderScale,  16777215, true);
                line++;
            }
            try{
                if (slot.size() > 0) {
                    String[] mahouseki=new String[5];
                    mahouseki[0]="§dゾンビ      ×";
                    mahouseki[1]="§7スケルトン ×";
                    mahouseki[2]="§a生物        ×";
                    mahouseki[3]="§3キャスター ×";
                    mahouseki[4]="§dポーシング +";
                    Double[] eneMul=HoldingItem.getMultiple();
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(renderScale, renderScale, 1);
                    GlStateManager.translate((sclRes.getScaledWidth()* DataStorage.renderHoldingItemX)/(renderScale)+130,  (sclRes.getScaledHeight() * DataStorage.renderHoldingItemY)/(renderScale)+6,0);

                    GlStateManager.scale(0.8f, 0.8f, 1);


                    for(int i=0;i<5;i++){
                        this.getFontRenderer().drawString(mahouseki[i]+(Math.round(eneMul[i]*1000.0)/1000.0+"     ").subSequence(0,4).toString(), 0, 4*i*DataStorage.renderSize/(0.8f*renderScale), 16777215, true);

                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }


        }

        GlStateManager.popMatrix();


        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    private void renderArmorPinch(){
        if(ModCoreData.isArmorPinch[0] || ModCoreData.isArmorPinch[1] || ModCoreData.isArmorPinch[2] || ModCoreData.isArmorPinch[3]){

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.8F, 0.8F, 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            String str="§c";
            if(ModCoreData.isArmorPinch[3]){
                str=str+"頭";
            }
            if(ModCoreData.isArmorPinch[2]){
                str=str+"胸";
            }
            if(ModCoreData.isArmorPinch[1]){
                str=str+"脚";
            }
            if(ModCoreData.isArmorPinch[0]){
                str=str+"足";
            }
            str=str+"の耐久がピンチ！";
            GlStateManager.scale(DataStorage.alertArmorSize,DataStorage.alertArmorSize,DataStorage.alertArmorSize);
            this.getFontRenderer().drawString(str,sclRes.getScaledWidth()*DataStorage.alertArmorDamageX/DataStorage.alertArmorSize,sclRes.getScaledHeight()*DataStorage.alertArmorDamageY/DataStorage.alertArmorSize,16711680,true);
            GlStateManager.popMatrix();

            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
        }
    }


}
