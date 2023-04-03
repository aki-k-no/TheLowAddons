package com.aki.thelowmod.timer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;

import java.time.LocalDateTime;

public class BSKTimer extends AbstractTimer{
    @Override
    public void setTimer(String s) {
        if(s.equals("バーサーク")){
            ModCoreData.BSKEndTime= LocalDateTime.now().plusSeconds(5);
        }
    }

    @Override
    public boolean shouldBeShown() {
        if(HoldingItem.holdingItems==null) return false;
        if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems)==null )return false;
        if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).equals("浮世の狂剣")){
            if(AKITheLowUtil.calcTimeDifference(ModCoreData.BSKEndTime,LocalDateTime.now())>=0){
                return true;
            }
        }
        if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).equals("浮世の冥剣")){
            if(AKITheLowUtil.calcTimeDifference(ModCoreData.BSKEndTime,LocalDateTime.now())>=0){
                return true;
            }
        }
        return false;

    }

    @Override
    public String getDisplayText() {
        return "バーサーク "+AKITheLowUtil.calcTimeDifference(ModCoreData.BSKEndTime,LocalDateTime.now())+"秒";
    }
}
