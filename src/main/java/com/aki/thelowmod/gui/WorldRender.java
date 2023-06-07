package com.aki.thelowmod.gui;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.api.Coordinates;
import com.aki.thelowmod.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.opengl.GL11;

import java.time.LocalDateTime;
import java.util.Collection;

public class WorldRender {

    //ここの実装はTheLow公式modを参考にしました ここで謝辞を述べさせていただきます

    public static void render(GuiIngameForge gif, ScaledResolution sclRes){
        if (!AKITheLowUtil.isInTheLow())return;

        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();

        for(String mcid: ModCoreData.springLocation.keySet()){
            if(ModCoreData.springTimer.containsKey(mcid)){
                renderSpringTimer(mcid);

            }
        }
        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.color(1F, 1F, 1F, 1F);

    }
    public static void renderSpringTimer(String mcid){

        Minecraft mc = Minecraft.getMinecraft();
        RenderManager renderManager = mc.getRenderManager();

        Collection<PotionEffect> effects=mc.thePlayer.getActivePotionEffects();
        boolean isBlinded=false;
        for(PotionEffect p:effects){
            if(p.getPotionID()==15){
                isBlinded=true;
            }
        }

        Coordinates coordinates=ModCoreData.springLocation.get(mcid).clone();
        double distance = Math.sqrt(renderManager.getDistanceToCamera(coordinates.x, coordinates.y, coordinates.z));
        double x = coordinates.x - renderManager.viewerPosX;
        double y = (coordinates.y - renderManager.viewerPosY) + 1;
        double z = coordinates.z - renderManager.viewerPosZ;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GL11.glNormal3f(0F, 1F, 0F);

        GlStateManager.rotate(-renderManager.playerViewY, 0F, 1F, 0F);
        if (mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-renderManager.playerViewX, 1F, 0F, 0F);
        } else {
            GlStateManager.rotate(renderManager.playerViewX, 1F, 0F, 0F);
        }

        double scaleByFOV = 0.3 + renderManager.options.fovSetting / 100F;
        double scale = (distance * 0.1F + 1.0F) * 0.03 * scaleByFOV * distance;
        if(scale<0.1) scale=0.1;

        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        //GlStateManager.disableAlpha();

        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(770, 771);

        FontRenderer font = renderManager.getFontRenderer();

        GlStateManager.scale(0.05f/scale, 0.05f/scale, 0.05f/scale);
        if((AKITheLowUtil.calcTimeDifference(ModCoreData.springTimer.get(mcid),LocalDateTime.now())>30)){
            if(isBlinded && distance>4 && distance<=8){
                drawCenteredString("泉:" + mcid + " " + (AKITheLowUtil.calcTimeDifference(ModCoreData.springTimer.get(mcid), LocalDateTime.now()) - 31) + "s", 0, -26,(8.0f-(float)distance)/4.0f, font);
            }else if(isBlinded && distance<=4 && distance>=0){
                drawCenteredString("泉:" + mcid + " " + (AKITheLowUtil.calcTimeDifference(ModCoreData.springTimer.get(mcid), LocalDateTime.now()) - 31) + "s", 0, -26, 1f, font);
            }else if(!isBlinded){
                drawCenteredString("泉:" + mcid + " " + (AKITheLowUtil.calcTimeDifference(ModCoreData.springTimer.get(mcid), LocalDateTime.now()) - 31) + "s", 0, -26, 1f, font);

            }
        }

        GlStateManager.enableDepth();
        GlStateManager.popMatrix();

    }
    public static void drawCenteredString(String name,int x,int y,float alpha,FontRenderer render){
        int i = render.getStringWidth(name);
        if(alpha>0.99f)alpha=0.99f;
        if(alpha<0.015f)return;
        render.drawString(name, x - i / 2, y, 0xFFFF00 + (((int)(alpha*255)) << 24) );

    }
}
