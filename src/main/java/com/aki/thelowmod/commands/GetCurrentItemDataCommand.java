package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.TheLowLogin;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GetCurrentItemDataCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "ghi";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "you can get NBT data of your holding item";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(HoldingItem.holdingItems==null){
            sender.getCommandSenderEntity().addChatMessage(new ChatComponentText("現在アイテムを持っていないようです"));
        }else if(HoldingItem.holdingItems.getTagCompound()==null){
            sender.getCommandSenderEntity().addChatMessage(new ChatComponentText("持っているアイテムの情報をクリップボードにコピーしました"));
            copyToClipboard(HoldingItem.holdingItems.getDisplayName());
        }else {
            sender.getCommandSenderEntity().addChatMessage(new ChatComponentText("持っているアイテムの情報[+NBT]をクリップボードにコピーしました"));
            copyToClipboard(HoldingItem.holdingItems.getDisplayName()+ " " + HoldingItem.holdingItems.getTagCompound().toString());
        }
        ModCoreData.playerInfos= Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        TheLowLogin.playerNameUpdater();

    }
    public static void copyToClipboard(String select) {
        Clipboard clipboard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        StringSelection selection = new StringSelection(select);
        clipboard.setContents(selection, selection);
    }


}
