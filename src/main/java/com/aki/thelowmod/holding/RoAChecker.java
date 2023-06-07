package com.aki.thelowmod.holding;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import net.minecraft.item.ItemStack;

import java.time.LocalDateTime;

public class RoAChecker {

    public static boolean wasHoldingRoA=false;

    public static void checkRoA() {

        ItemStack nowHolding = HoldingItem.holdingItems;
        if (nowHolding == null) {
            wasHoldingRoA = false;
        }else if(AKITheLowUtil.getTheLowItemID(nowHolding)==null){
            wasHoldingRoA=false;
        }else if(AKITheLowUtil.getTheLowItemID(nowHolding).equals("RaidLightReward2") || AKITheLowUtil.getTheLowItemID(nowHolding).equals("mainH魔法LvEliteLight1")){
            if(!wasHoldingRoA) ModCoreData.RoAHandTime=LocalDateTime.now();
            wasHoldingRoA=true;
        }else{
            wasHoldingRoA=false;
        }
    }
}
