package com.aki.thelowmod.holding;

import com.aki.thelowmod.data.ModCoreData;
import net.minecraft.item.ItemStack;

import java.time.LocalDateTime;

public class AmeretatChecker {

    public static boolean wasHoldingAmeretat=false;

    public static void checkAmere() {

        ItemStack nowHolding = HoldingItem.holdingItems;
        if (nowHolding == null) {
            wasHoldingAmeretat = false;
        } else {
            if (nowHolding.getDisplayName() == null) {
                wasHoldingAmeretat = false;
                return;
            }
            if (nowHolding.getDisplayName().startsWith("§4§lAmərətāt")) {
                if (!wasHoldingAmeretat) {
                    ModCoreData.lastAmeretat = LocalDateTime.now();
                }
                wasHoldingAmeretat = true;
            } else {
                wasHoldingAmeretat = false;
            }
        }
    }
}
