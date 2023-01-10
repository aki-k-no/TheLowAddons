package com.aki.thelowmod.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatSender {
    public static void sendDungeonAPIChat(){
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/thelow_api dungeon");
    }
    public static void sendPlayerAPIChat(){
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/thelow_api player");
    }

    public static void sendAPISubscribeChat(){
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/thelow_api subscribe SKILL_COOLTIME");
    }

}
