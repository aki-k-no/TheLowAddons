package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.chat.ChatSender;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class RefreshCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "refresh";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Refresh All API";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        try {
            ChatSender.sendAPISubscribeChat();
            ;
            ChatSender.sendDungeonAPIChat();
            ChatSender.sendPlayerAPIChat();
            AKITheLowUtil.showInChat("全てのapiコマンドを再送信しました。");
        }catch(Exception e){

            AKITheLowUtil.showInChat("何らかの予想外のエラーが発生しました。");
        }


    }





}
