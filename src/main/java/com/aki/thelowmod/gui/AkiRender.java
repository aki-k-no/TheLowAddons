package com.aki.thelowmod.gui;

import com.aki.thelowmod.ClientProxy;
import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.config.DataStorage;
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
    public void render(RenderGameOverlayEvent.Text event){
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

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void renderGUI(GuiScreenEvent.DrawScreenEvent.Pre e){

        if(DataStorage.showNoThrow){
            OverlayRender.renderOverlay(this,sclRes);
        }

    }

    private void renderHoldingItem(){
        int line=2;

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.8F, 0.8F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6F*DataStorage.renderSize, 0.6F*DataStorage.renderSize, 0.6F*DataStorage.renderSize);
                                                                        this.getFontRenderer().drawString(HoldingItem.holdingItems.getDisplayName(), sclRes.getScaledWidth()* DataStorage.renderHoldingItemX*1.66F/DataStorage.renderSize, sclRes.getScaledHeight()* DataStorage.renderHoldingItemY*1.66F/DataStorage.renderSize, 16777215, true);
        GlStateManager.popMatrix();

        GlStateManager.scale(0.5F*DataStorage.renderSize, 0.5F*DataStorage.renderSize, 0.5F*DataStorage.renderSize);

        HoldingItem.analyzeItemLore();

        Double weaponStrength=HoldingItem.getWeaponStrength();
        GlStateManager.pushMatrix();
        if(weaponStrength!=null) {
            this.getFontRenderer().drawString("§e§l攻撃力", sclRes.getScaledWidth() * DataStorage.renderHoldingItemX * 2/DataStorage.renderSize, (float) (sclRes.getScaledHeight() * (DataStorage.renderHoldingItemY + DataStorage.renderDistance * line*DataStorage.renderSize) * 2)/DataStorage.renderSize, 16777215, true);
            line++;
            this.getFontRenderer().drawString("§6    +"+weaponStrength.toString(), sclRes.getScaledWidth() * DataStorage.renderHoldingItemX * 2/DataStorage.renderSize, (float) (sclRes.getScaledHeight() * (DataStorage.renderHoldingItemY + DataStorage.renderDistance * line*DataStorage.renderSize) * 2)/DataStorage.renderSize, 16777215, true);
            line++;
        }
        GlStateManager.popMatrix();

        Map<String,Double> additinalStr=HoldingItem.getAdditionalStrength();
        GlStateManager.pushMatrix();
        if(additinalStr!=null && additinalStr.size()!=0) {
            this.getFontRenderer().drawString("§e§l追加ダメージ", sclRes.getScaledWidth() * DataStorage.renderHoldingItemX * 2/DataStorage.renderSize, (float) (sclRes.getScaledHeight() * (DataStorage.renderHoldingItemY + DataStorage.renderDistance * line*DataStorage.renderSize) * 2)/DataStorage.renderSize, 16777215, true);
            line++;
            for(String nameKey: additinalStr.keySet()){
                GlStateManager.pushMatrix();
                this.getFontRenderer().drawString(nameKey+" ： §6+"+additinalStr.get(nameKey), sclRes.getScaledWidth()* DataStorage.renderHoldingItemX*2/DataStorage.renderSize, (float) (sclRes.getScaledHeight() * (DataStorage.renderHoldingItemY+DataStorage.renderDistance*line*DataStorage.renderSize)*2)/DataStorage.renderSize, 16777215, true);
                line++;
                GlStateManager.popMatrix();
            }

        }
        GlStateManager.popMatrix();

        List<String> slot=HoldingItem.getSlotsInfo();
        if(slot!=null) {
            if (slot.size() > 0) {
                slot.add(0, "§2§lSLOT情報");
            }
            for (int i = 0; i < slot.size(); i++) {
                GlStateManager.pushMatrix();
                this.getFontRenderer().drawString(slot.get(i), sclRes.getScaledWidth() * DataStorage.renderHoldingItemX * 2/DataStorage.renderSize, (float) (sclRes.getScaledHeight() * (DataStorage.renderHoldingItemY + DataStorage.renderDistance * line*DataStorage.renderSize) * 2)/DataStorage.renderSize, 16777215, true);
                line++;
                GlStateManager.popMatrix();
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
                    for(int i=0;i<5;i++){
                        GlStateManager.pushMatrix();
                        this.getFontRenderer().drawString(mahouseki[i]+(Math.round(eneMul[i]*1000.0)/1000.0+"     ").subSequence(0,4).toString(), ((float) sclRes.getScaledWidth()* (DataStorage.renderHoldingItemX+0.15F*DataStorage.renderSize)*2)/DataStorage.renderSize, (float) (sclRes.getScaledHeight() * (DataStorage.renderHoldingItemY+DataStorage.renderDistance*DataStorage.renderSize*(i+2))*2)/DataStorage.renderSize, 16777215, true);

                        GlStateManager.popMatrix();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }


        }



        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
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
            GlStateManager.pushMatrix();
            GlStateManager.scale(DataStorage.alertArmorSize,DataStorage.alertArmorSize,DataStorage.alertArmorSize);
            this.getFontRenderer().drawString(str,sclRes.getScaledWidth()*DataStorage.alertArmorDamageX/DataStorage.alertArmorSize,sclRes.getScaledHeight()*DataStorage.alertArmorDamageY/DataStorage.alertArmorSize,16711680,true);
            GlStateManager.popMatrix();

            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.disableAlpha();
        }
    }


}
