package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.holding.HoldingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class EHPCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "ehp";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "calculate your Effective HP";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length==1 && args[0].equals("help")){
            AKITheLowUtil.showInChat("§b/ehpコマンドの使い方");
            AKITheLowUtil.showInChat("§7/ehp : 現在の防具と体力から有効体力を計算します。防具perkはmaxと仮定します。");
            AKITheLowUtil.showInChat("§7/ehp help : このヘルプを表示します");
            AKITheLowUtil.showInChat("§7/ehp custom [数字] : 防具perkのレベルを指定します(0~10)");

            return;
        }

        InventoryPlayer inventory = Minecraft.getMinecraft().thePlayer.inventory;
        ItemStack[] armors=inventory.armorInventory;
        double mobArmor=40;
        double bossArmor=40;

        if(args.length==2 && args[0].equals("custom")){
            try{
                mobArmor=Integer.parseInt(args[1])*4;
                bossArmor=Integer.parseInt(args[1])*4;
            }catch(Exception e){
                AKITheLowUtil.showInChat("§b数字では無い値が入力されました。perkを0として計算します。");
                mobArmor=bossArmor=0;
            }

        }
        for(int i=0;i<4;i++){
            mobArmor+=AKITheLowUtil.getMobArmor(armors[i]);
            bossArmor+=AKITheLowUtil.getBossArmor(armors[i]);
        }
        AKITheLowUtil.showInChat("§b==防具ポイントを元に計算した有効体力==");
        AKITheLowUtil.showInChat("§amobに対する有効体力: "+Math.round(Minecraft.getMinecraft().thePlayer.getMaxHealth()*(50.0+mobArmor)/50.0*1000)/1000.0);
        AKITheLowUtil.showInChat("§abossに対する有効体力: "+Math.round(Minecraft.getMinecraft().thePlayer.getMaxHealth()*(50.0+bossArmor)/50.0*1000)/1000.0);;
        AKITheLowUtil.showInChat("§a合計のmob防御: "+Math.round(mobArmor*1000)/1000.0);
        AKITheLowUtil.showInChat("§a合計のboss防御: "+Math.round(bossArmor*1000)/1000.0);


    }





}
