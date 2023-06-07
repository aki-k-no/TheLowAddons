package com.aki.thelowmod.timer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;

import java.time.LocalDateTime;

public class RoATimer extends AbstractTimer{
    @Override
    public void setTimer(String s) {
        if(s.equals("ファイヤ・ボルケーノ") || s.equals("メテオストライク") || s.equals("マジックボール") || s.equals("ライトニングボルト")){
            ModCoreData.RoAHandTime= LocalDateTime.now();
        }
    }

    @Override
    public boolean shouldBeShown() {
        if(HoldingItem.holdingItems==null) return false;
        if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems)==null )return false;
        if(AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).equals("RaidLightReward2") || AKITheLowUtil.getTheLowItemID(HoldingItem.holdingItems).equals("mainH魔法LvEliteLight1")){
            if(AKITheLowUtil.calcTimeDifference(LocalDateTime.now(),ModCoreData.RoAHandTime)>=0){
                return true;
            }
        }

        return false;

    }

    @Override
    public String getDisplayText() {
        long timer=0;
        if((timer=AKITheLowUtil.calcTimeDifference(LocalDateTime.now(),ModCoreData.RoAHandTime))>=10){
            return "詠唱 §2OK";
        }else{
            return "詠唱 "+(10-timer)+"秒";

        }
    }
}
