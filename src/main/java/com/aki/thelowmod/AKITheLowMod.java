package com.aki.thelowmod;

//import com.aki.thelowmod.commands.CommandGetArmor;
//import com.aki.thelowmod.commands.CommandGetItem;
import com.aki.thelowmod.commands.GetCurrentItemDataCommand;
import com.aki.thelowmod.config.AKITheLowModConfigCore;
import com.aki.thelowmod.gui.AkiRender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.common.*;
import com.aki.thelowmod.event.*;

@Mod(modid = AKITheLowMod.MODID,name=AKITheLowMod.MODNAME, version = AKITheLowMod.VERSION, clientSideOnly=true,guiFactory = "com.aki.thelowmod.config.AKITheLowModConfigManager", dependencies="before:onim.en.etl")
public class AKITheLowMod
{
    public static final String MODID = "akithelowmod";
    public static final String MODNAME = "AKI's The Low Addons";

    public static final String VERSION = "1.1.3";

    public static Gson gson=new GsonBuilder().create();;
    @Metadata(MODID)
    public static ModMetadata meta;

    @SidedProxy(clientSide = "com.aki.thelowmod.ClientProxy", serverSide = "com.aki.thelowmod.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        ModInfo.load(meta);
        AKITheLowModConfigCore.loadConfig(event);
        System.out.println("perInit Done");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        ClientCommandHandler.instance.registerCommand(new GetCurrentItemDataCommand());



        System.out.println("Init Done");

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
        MinecraftForge.EVENT_BUS.register(new Events());
        MinecraftForge.EVENT_BUS.register(new AkiRender(Minecraft.getMinecraft()));
        System.out.println("postInit Done");
    }



    private void clientOnlyEvents(PlayerTickEvent event){



    }

}
