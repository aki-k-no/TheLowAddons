package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class SpringPlaceCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "sp";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "debug";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length==0){
            for(String s: ModCoreData.springLocation.keySet()){
                AKITheLowUtil.showInChat(ModCoreData.springLocation.get(s));
            }
        }else{
            String mcid=args[0];
            EntityPlayer ep=Minecraft.getMinecraft().theWorld.getPlayerEntityByName(mcid);
            AKITheLowUtil.showInChat("x:"+ep.posX+" y:"+ep.posY+" z:"+ep.posZ);
        }


    }





}
