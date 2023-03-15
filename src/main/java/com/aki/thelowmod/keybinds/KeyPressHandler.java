package com.aki.thelowmod.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyPressHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e){
        KeyBinding bind = FMLClientHandler.instance().getClient().gameSettings.keyBindForward;
        if(KeyBinds.key_nothrow.isKeyDown()){
            KeyBinds.sendNoThrow();
        }
    }
}
