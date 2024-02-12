package com.aki.thelowmod.gui;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.config.DataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.*;

public class OverlayRender{

    private static final ResourceLocation NOTHROW = new ResourceLocation("akithelowmod", "textures/nothrow.png");

    private static Map<String,String> customOverlayData= new HashMap<String,String>();

    //nothrowマーカーなどの描画
    public static void renderOverlay(GuiIngameForge gif, ScaledResolution sclRes){

        if(DataStorage.showNoThrow){
            nothrowOverlay(gif,sclRes);
        }

        if(DataStorage.showMagicStoneOverlay){
            MagicalStoneOverlayGUI(gif,sclRes);
        }
    }

    // 魔法石Overlayに関する情報を読む
    public static void readOverlayData(){
        customOverlayData.clear();
        try {
            InputStream is=Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("akithelowmod", "overlay/settings.dat")).getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"UTF-8");
            BufferedReader br=new BufferedReader(isr);

            String text="";
            while((text=br.readLine())!=null){
                String[] data=text.split(",");
                if(data.length<3)continue;
                customOverlayData.put(data[0]+":"+data[1],data[2]);
                System.out.println(text);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
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
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 1000);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 1);

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
            GlStateManager.popMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }
    }

    static void MagicalStoneOverlayGUI(GuiIngameForge gif, ScaledResolution sclRes){
        GuiScreen screen= Minecraft.getMinecraft().currentScreen;
        if(screen==null) return;
        if(screen instanceof GuiContainer){
            GuiContainer gui=(GuiContainer)screen;
            Container c=gui.inventorySlots;
            List<Slot> slots=c.inventorySlots;


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

            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 1000);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 1);

            for(int i=0;i<slots.size();i++){
                ItemStack item=slots.get(i).getStack();
                if(item==null) continue;

                if(item.getTagCompound()==null) continue;

                boolean zom=false;
                boolean ske=false;
                boolean living=false;
                boolean posi=false;
                boolean caster=false;
                if(item.getTagCompound().hasKey("thelow_item_slot_list")){
                    String[] slotData=item.getTagCompound().getString("thelow_item_slot_list").split(",");
                    for(String stone:slotData){
                        if(stone.startsWith("zombie"))zom=true;
                        if(stone.startsWith("skeleton"))ske=true;
                        if(stone.startsWith("living"))living=true;
                        if(stone.startsWith("add_mp_magicsto"))posi=true;
                        if(stone.startsWith("reduce_cooltime_mag"))caster=true;
                    }
                }
                if(!zom && !ske && !living && !posi && !caster)continue;
                String itemId=AKITheLowUtil.getTheLowItemID(item);
                String texture="";

                if(itemId!=null){
                    texture=getTextureLoc(zom,ske,living,posi,caster,itemId);
                    drawMagicalStoneOverlay(gif,sclRes,slots.get(i),gui,leftX,topY,texture);

                }
            }
            GlStateManager.popMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();

        }
    }

    static void MagicalStoneOverlayHotbar(GuiIngameForge gif, ScaledResolution sclRes){

        int leftX=(int) (sclRes.getScaledWidth_double() / 2.0 - 88);
        int topY=(int)(sclRes.getScaledHeight_double() - 18);

        ItemStack[] slots=Minecraft.getMinecraft().thePlayer.inventory.mainInventory;

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 1000);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 1);

        for(int i=0;i<9;i++){
            ItemStack item=slots[i];
            if(item==null) continue;

            if(item.getTagCompound()==null) continue;

            boolean zom=false;
            boolean ske=false;
            boolean living=false;
            boolean posi=false;
            boolean caster=false;
            if(item.getTagCompound().hasKey("thelow_item_slot_list")){
                String[] slotData=item.getTagCompound().getString("thelow_item_slot_list").split(",");
                for(String stone:slotData){
                    if(stone.startsWith("zombie"))zom=true;
                    if(stone.startsWith("skeleton"))ske=true;
                    if(stone.startsWith("living"))living=true;
                    if(stone.startsWith("add"))posi=true;
                    if(stone.startsWith("reduce"))caster=true;
                }
            }
            if(!zom && !ske && !living && !posi && !caster)continue;
            String itemId=AKITheLowUtil.getTheLowItemID(item);
            String texture="";

            if(itemId!=null){
                texture=getTextureLoc(zom,ske,living,posi,caster,itemId);
                if(texture!=null){
                    drawMagicalStoneOverlayHotbar(gif,sclRes,i,leftX,topY,texture);
                }


            }
        }

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }


    private static String getTextureLoc(boolean zom,boolean ske,boolean living,boolean posi,boolean caster,String itemId){
        String texture=null;
        if(zom && ske && living){
            if(customOverlayData.containsKey(itemId +":all")){
                texture=customOverlayData.get(itemId +":all");
            }else{
                texture="all";
            }
        }else if(zom && ske){
            if(customOverlayData.containsKey(itemId +":zomske")){
                texture=customOverlayData.get(itemId +":zomske");
            }else{
                texture="zomske";
            }
        }else if(zom && living){
            if(customOverlayData.containsKey(itemId +":zomliv")){
                texture=customOverlayData.get(itemId +":zomliv");
            }else{
                texture="zomliv";
            }
        }else if(ske && living){
            if(customOverlayData.containsKey(itemId +":skeliv")){
                texture=customOverlayData.get(itemId +":skeliv");
            }else{
                texture="skeliv";
            }
        }else if(zom){
            if(customOverlayData.containsKey(itemId +":zom")){
                texture=customOverlayData.get(itemId +":zom");
            }else{
                texture="zom";
            }
        }else if(ske){
            if(customOverlayData.containsKey(itemId +":ske")){
                texture=customOverlayData.get(itemId +":ske");
            }else{
                texture="ske";
            }
        }else if(living){
            if(customOverlayData.containsKey(itemId +":living")){
                texture=customOverlayData.get(itemId +":living");
            }else{
                texture="living";
            }
        }else if(caster){
            if(customOverlayData.containsKey(itemId +":caster")){
                texture=customOverlayData.get(itemId +":caster");
            }else{
                texture="caster";
            }
        }else if(posi){
            if(customOverlayData.containsKey(itemId +":posing")){
                texture=customOverlayData.get(itemId +":posing");
            }else{
                texture="posing";
            }
        }
        return texture;
    }

    static void drawNoThrowIcon(GuiIngameForge gif,ScaledResolution sclRes,Slot slot,GuiContainer gui,int leftX,int TopY){
        int x=slot.xDisplayPosition;
        int y=slot.yDisplayPosition;
        GlStateManager.scale(1.0f,1.0f,1.0f);



        Gui.drawModalRectWithCustomSizedTexture(leftX+x+10,TopY+y+10, 0, 0, 6, 6,6,6);
    }

    static void drawMagicalStoneOverlay(GuiIngameForge gif,ScaledResolution sclRes,Slot slot,GuiContainer gui,int leftX,int TopY,String textureLoc){

        int x=slot.xDisplayPosition;
        int y=slot.yDisplayPosition;

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("akithelowmod", "textures/"+textureLoc+".png"));

        Gui.drawModalRectWithCustomSizedTexture(leftX+x,TopY+y, 0, 0, 16, 16,16,16);
    }

    static void drawMagicalStoneOverlayHotbar(GuiIngameForge gif,ScaledResolution sclRes,int number,int leftX,int TopY,String textureLoc){



        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("akithelowmod", "textures/"+textureLoc+".png"));

        Gui.drawModalRectWithCustomSizedTexture(leftX+number*20, TopY, 0, 0, 16, 16,16,16);

    }
}
