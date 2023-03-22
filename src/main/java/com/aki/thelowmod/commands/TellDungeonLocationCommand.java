package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.holding.HoldingItem;
import com.aki.thelowmod.types.DungeonData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class TellDungeonLocationCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "dungeonlocation";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "give xyz coordinates of specific dungeon";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {

            AKITheLowUtil.showInChat("§b【！】ダンジョン名を指定してください Usage:/dungeonlocation [ダンジョン名]");
            return;

        }

        DungeonData dd=AKITheLowUtil.getSpecificDungeonData(args[0]);
        if(dd==null){

            AKITheLowUtil.showInChat("§b【！】その名前のダンジョンは存在しないか、まだ読み込まれていません。時間をおいて試すか、タイプミスが無いか確認してください Usage:/dungeonlocation [ダンジョン名]");
            return;

        }

        AKITheLowUtil.showInChat("§6 ダンジョン"+dd.name+"の座標：x"+dd.x+" y"+dd.y+" z"+dd.z);


    }



}
