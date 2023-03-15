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

public class CreateCITPropertiesCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "cit";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "make the item properties format";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(HoldingItem.holdingItems==null){
            sender.getCommandSenderEntity().addChatMessage(new ChatComponentText("現在アイテムを持っていないようです"));
            return;
        }

        String item_id= AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems);
        if(item_id==null){
            AKITheLowUtil.showInChat("今の手持ちアイテムにはthelow_item_idが指定されていないようです");
        }else{
            String format="type=item\n" +
                    "items="+HoldingItem.holdingItems.getItem().getRegistryName() + "\n" +
                    "texture=\n" +
                    "nbt.thelow_item_id=iregex:" + AKITheLowUtil.convertToUnicode(item_id);
            copyToClipboard(format);
            AKITheLowUtil.showInChat("アイテムに対応するcitの.propertiesファイルをクリップボードにコピーしました");
        }

    }
    public static void copyToClipboard(String select) {
        Clipboard clipboard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        StringSelection selection = new StringSelection(select);
        clipboard.setContents(selection, selection);
    }


}
