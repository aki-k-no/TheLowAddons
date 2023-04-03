package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ArmorScoreCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "armor";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "calculate your Armor Score";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length==1 && args[0].equals("help")){
            AKITheLowUtil.showInChat("§b/armorコマンドの使い方");
            AKITheLowUtil.showInChat("§7/armor : 現在の防具のスコアを計算します");
            AKITheLowUtil.showInChat("§7/armor help : このヘルプを表示します");

            return;
        }

        InventoryPlayer inventory = Minecraft.getMinecraft().thePlayer.inventory;
        ItemStack[] armors=inventory.armorInventory;

        AKITheLowUtil.showInChat("§b==計算された防具スコア==");
        AKITheLowUtil.showInChat("----------------------------------");
        for(int i=0;i<4;i++){
            if(armors[3-i]==null){
                AKITheLowUtil.showInChat("この部位には防具がないようです");
                continue;
            }
            AKITheLowUtil.showInChat("§a防具:"+armors[3-i].getDisplayName()+"のスコア");
            AKITheLowUtil.showInChat("  "+(AKITheLowUtil.getMobArmor(armors[3-i])+AKITheLowUtil.getAdditionalHealth(armors[3-i])*3));
            AKITheLowUtil.showInChat("----------------------------------");

        }

        AKITheLowUtil.showInChat("§b===================");

    }





}
