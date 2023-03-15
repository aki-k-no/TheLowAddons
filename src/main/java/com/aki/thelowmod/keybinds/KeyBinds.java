package com.aki.thelowmod.keybinds;

import com.aki.thelowmod.api.AKITheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBinds {
    public static KeyBinding key_nothrow;

    public static void init(){
        key_nothrow=new KeyBinding("key.shortcut.nothrow", Keyboard.KEY_N, "com.aki.thelowmod");
        ClientRegistry.registerKeyBinding(key_nothrow);
    }

    public static void sendNoThrow(){
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/nothrow");
    }
}
