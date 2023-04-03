package com.aki.thelowmod.timer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;

import java.time.LocalDateTime;

public class ImpactionTimer extends AbstractTimer{
    @Override
    public void setTimer(String s) {
        if(s.equals("Impaction")){
            ModCoreData.ImpactionEndTime= LocalDateTime.now().plusSeconds(45);
        }
    }

    @Override
    public boolean shouldBeShown() {
        if(HoldingItem.holdingItems==null) return false;
        if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems)==null )return false;
        if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).startsWith("jok-sword")){
            if(AKITheLowUtil.calcTimeDifference(ModCoreData.ImpactionEndTime,LocalDateTime.now())>=0){
                return true;
            }
        }
        return false;

    }

    @Override
    public String getDisplayText() {
        return "Impaction "+AKITheLowUtil.calcTimeDifference(ModCoreData.ImpactionEndTime,LocalDateTime.now())+"秒";
    }
}
