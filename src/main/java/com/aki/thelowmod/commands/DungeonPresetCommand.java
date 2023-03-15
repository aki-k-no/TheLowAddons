package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.dungeonpreset.DungeonPresets;
import com.aki.thelowmod.dungeonpreset.Preset;
import com.aki.thelowmod.dungeonpreset.PresetSerializer;
import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class DungeonPresetCommand extends CommandBase implements ICommand {



    public static final String HELPMSG="§7/preset create [プリセット名]: 新しいプリセットを作成する\n"+
            "§7/preset delete [プリセット名]: 既存のプリセットを削除する\n"+
            "§7/preset help: この説明を表示する\n"+
            "§7/preset list: 現在のプリセットの一覧を表示する\n"+
            "§7/preset add [プリセット名]: 現在の手持ちアイテムを[プリセット名]に追加する\n"+
            "§7/preset remove [プリセット名]: 現在の手持ちアイテムを[プリセット名]から削除する\n"+
            "§7/preset show [プリセット名]: [プリセット名]のプリセットのアイテムの一覧をチャットに表示する\n"+
            "§7/preset missing [プリセット名]: [プリセット名]のプリセットのアイテムのうち、今インベントリに入っていないものの一覧をチャットに表示する\n"+
            "§7/preset hotbar [プリセット名]: 現在のホットバーに入っているアイテム全てを[プリセット名]に登録する。";


    @Override
    public String getCommandName() {
        return "preset";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "set/show dungeon preset";
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if(args.length==1){
            return getListOfStringsMatchingLastWord(args, "help","list","create","delete","add","remove","show","missing","hotbar");
        }else if(args.length==2){
            return getListOfStringsMatchingLastWord(args, DungeonPresets.getListOfPreset());
        }
        return new ArrayList<String>();

    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length==0){
            AKITheLowUtil.showInChat("§cコマンドが正しくありません。使い方\n"+HELPMSG);
            return;
        }else if(args[0].equals("help")){
            AKITheLowUtil.showInChat("§b===Presetコマンドの使い方===\n"+HELPMSG);
            return;
        }else if(args[0].equals("list")){
            if(DungeonPresets.presets.size()==0){
                AKITheLowUtil.showInChat("§b【！】現在一つもプリセットが無いようです。まずは/preset createでプリセットを作成してください。");
                return;
            }
            AKITheLowUtil.showInChat("§6現在のプリセット一覧は以下です");
            for(Preset preset:DungeonPresets.presets){
                AKITheLowUtil.showInChat(preset.presetName);
            }
            return;
        }else if(args.length<2){
            AKITheLowUtil.showInChat("§cコマンドが正しくありません。使い方\n"+HELPMSG);
            return;
        } else if(args[0].equals("create")){
            DungeonPresets.addPreset(args[1]);
        } else if(args[0].equals("delete")){
            DungeonPresets.deletePreset(args[1]);
        }else if(args[0].equals("add")){
            if(DungeonPresets.getPresetByName(args[1])==null){
                AKITheLowUtil.showInChat("§b【！】プリセット "+args[1]+"は存在しません\n"+
                        "§bプリセット一覧を確認する場合は/preset listを実行してください");
                return;
            }
            DungeonPresets.getPresetByName(args[1]).addItem(HoldingItem.holdingItems);
        }else if(args[0].equals("remove")){
            if(DungeonPresets.getPresetByName(args[1])==null){
                AKITheLowUtil.showInChat("§b【！】プリセット "+args[1]+"は存在しません\n"+
                        "§bプリセット一覧を確認する場合は/preset listを実行してください");
                return;

            }
            DungeonPresets.getPresetByName(args[1]).removeItem(HoldingItem.holdingItems);
        } else if(args[0].equals("show")){
            if(DungeonPresets.getPresetByName(args[1])==null){
                AKITheLowUtil.showInChat("§b【！】プリセット "+args[1]+"は存在しません\n"+
                        "§bプリセット一覧を確認する場合は/preset listを実行してください");
                return;
            }
            DungeonPresets.getPresetByName(args[1]).showAllItem();
            return;
        } else if(args[0].equals("missing")){
            if(DungeonPresets.getPresetByName(args[1])==null){
                AKITheLowUtil.showInChat("§b【！】プリセット "+args[1]+"は存在しません\n"+
                        "§bプリセット一覧を確認する場合は/preset listを実行してください");
                return;
            }
            DungeonPresets.getPresetByName(args[1]).showMissingItem();
            return;
        } else if(args[0].equals("hotbar")){
            if(DungeonPresets.getPresetByName(args[1])==null){
                AKITheLowUtil.showInChat("§b【！】プリセット "+args[1]+"は存在しません\n"+
                        "§bプリセット一覧を確認する場合は/preset listを実行してください");
                return;
            }
            DungeonPresets.getPresetByName(args[1]).addHotBarItem();
            return;
        }
        PresetSerializer.outputPresetData();
    }


}
