package com.aki.thelowmod.gui;

import com.aki.thelowmod.api.AKITheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;

import java.lang.reflect.Field;
import java.util.List;

public class OverlayRender {

    private static final ResourceLocation NOTHROW = new ResourceLocation("akithelowmod", "textures/nothrow.png");

    //nothrowマーカーなどの描画
    public static void renderOverlay(GuiIngameForge gif, ScaledResolution sclRes){
        nothrowOverlay(gif,sclRes);
    }

    static void nothrowOverlay(GuiIngameForge gif, ScaledResolution sclRes){
        GuiScreen screen= Minecraft.getMinecraft().currentScreen;
        if(screen==null) return;
        if(screen instanceof GuiContainer){
            GuiContainer gui=(GuiContainer)screen;
            Container c=gui.inventorySlots;
            List<Slot> slots=c.inventorySlots;
            Minecraft.getMinecraft().getTextureManager().bindTexture(NOTHROW);


            int leftX=0;
            int topY=0;
            Class<? extends GuiContainer> guiclazz=gui.getClass();
            try {
                Field fleftX=AKITheLowUtil.getFieldFromClass(guiclazz,"field_147003_i");
                Field ftopY=AKITheLowUtil.getFieldFromClass(guiclazz,"field_147009_r");
                fleftX.setAccessible(true);
                ftopY.setAccessible(true);
                leftX=fleftX.getInt(gui);
                topY=ftopY.getInt(gui);
            } catch (Exception e) {}

            for(int i=0;i<slots.size();i++){
                ItemStack item=slots.get(i).getStack();
                if(item==null) continue;

                if(item.getTagCompound()==null) continue;

                if(item.getTagCompound().hasKey("no_throw_item")){
                    if(item.getTagCompound().getString("no_throw_item").equals("01")){
                        drawNoThrowIcon(gif,sclRes,slots.get(i),gui,leftX,topY);
                    }

                }
            }
        }
    }

    static void drawNoThrowIcon(GuiIngameForge gif,ScaledResolution sclRes,Slot slot,GuiContainer gui,int leftX,int TopY){
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 1000);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 1);
        int x=slot.xDisplayPosition;
        int y=slot.yDisplayPosition;
        GlStateManager.scale(1.0f,1.0f,1.0f);



        Gui.drawModalRectWithCustomSizedTexture(leftX+x+10,TopY+y+10, 0, 0, 6, 6,6,6);
        GlStateManager.popMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }


}
