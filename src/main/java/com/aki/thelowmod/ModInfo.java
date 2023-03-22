package com.aki.thelowmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ModInfo {

    public static void load(ModMetadata meta){
        meta.modId = AKITheLowMod.MODID;
        meta.name = AKITheLowMod.MODNAME;
        meta.description ="TheLowのクライアント用のModです TheLow公式mod,TheLowToopTipModとは競合します";
        meta.version =AKITheLowMod.VERSION ;
        meta.authorList.add("AKI(K_no)");
        meta.credits = "";
        meta.logoFile ="";
        meta.autogenerated = false;

    }

}
