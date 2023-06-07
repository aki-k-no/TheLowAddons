package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GetGUIItemNBTDataCommand extends CommandBase implements ICommand {

    public static boolean toggled=false;

    @Override
    public String getCommandName() {
        return "nbt";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "toggle whether you get the nbt data from gui or not when clicked";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        toggled= !toggled;
        if(toggled){

            AKITheLowUtil.showInChat("現在GUI内アイテムのnbtデータ取得機能はONです");
        }else{

            AKITheLowUtil.showInChat("現在GUI内アイテムのnbtデータ取得機能はOFFです");
        }
    }
    public static void copyToClipboard(String select) {
        Clipboard clipboard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        StringSelection selection = new StringSelection(select);
        clipboard.setContents(selection, selection);
    }


}
