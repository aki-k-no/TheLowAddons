package com.aki.thelowmod.config;

import com.aki.thelowmod.AKITheLowMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.Set;

public class AKITheLowModConfigManager implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {}



    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        if(AKITheLowModConfigCore.configElements==null){
            AKITheLowModConfigCore.setConfigElements();
        }
        return AKITheLowModConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }


    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }


    public static class AKITheLowModConfigGui extends GuiConfig {
        public AKITheLowModConfigGui(GuiScreen parent) {
            super(parent, AKITheLowModConfigCore.configElements ,AKITheLowMod.MODID,false,false,AKITheLowMod.MODNAME);

        }
    }
}
