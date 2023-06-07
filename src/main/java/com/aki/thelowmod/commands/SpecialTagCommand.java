package com.aki.thelowmod.commands;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class SpecialTagCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "specialtag";
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "add special reinc tag to your name";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length==0){
            AKITheLowUtil.showInChat("§cコマンドが正しくありません。使い方");
            AKITheLowUtil.showInChat("§b/specialtag <自分の名前の横に表示したいタグ>");
            AKITheLowUtil.showInChat("§bなお、これはタブを自分から見た時だけの表示です。また、nickhiderとは競合すると思います。");
            AKITheLowUtil.showInChat("§b装飾コードの関係でセクション記号を使いたいときは、\\sectionと書くと自動で変換されます");
            AKITheLowUtil.showInChat("§b例: /specialtag me と打てば §rme "+ Minecraft.getMinecraft().thePlayer.getName());
            AKITheLowUtil.showInChat("§b例: /specialtag [\\section6\\sectionka\\section9転生したい\\section6\\sectionka\\sectionr] と打てば §r[§6§ka§9転生したい§6§ka§r] "+ Minecraft.getMinecraft().thePlayer.getName());
            AKITheLowUtil.showInChat("§bという風に表示を変えることが出来ます");
            AKITheLowUtil.showInChat("§bまた、表示を最初に戻したいときは /specialtag reset true");
            AKITheLowUtil.showInChat("§bとすれば元の転生数表示に戻せます");

            return;
        }else if(args.length==2 && args[0].equalsIgnoreCase("reset") && args[1].equalsIgnoreCase("true")){
            ModCoreData.specialTag=null;
            AKITheLowUtil.saveFile("","./specialtag.dat");
            AKITheLowUtil.showInChat("§bタブのタグを初期化しました");

        }else{
            String tag=args[0];
            tag=tag.replaceAll("\\\\section","§");
            ModCoreData.specialTag=tag;
            AKITheLowUtil.saveFile(tag,"./specialtag.dat");
            AKITheLowUtil.showInChat("§bタブのタグを"+tag+"§bに変更しました");
        }

    }





}
