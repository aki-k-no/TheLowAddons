package com.aki.thelowmod.timer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;

import java.time.LocalDateTime;

public class DragonShineTimer extends AbstractTimer{
    @Override
    public void setTimer(String s) {
        if(s.equals("龍の一閃")){
            ModCoreData.dragonEndTime= LocalDateTime.now().plusSeconds(25);
        }
    }

    @Override
    public boolean shouldBeShown() {
        if(AKITheLowUtil.calcTimeDifference(ModCoreData.dragonEndTime,LocalDateTime.now())>=0){
            return true;
        }

        return false;

    }

    @Override
    public String getDisplayText() {
        return "龍の一閃 "+AKITheLowUtil.calcTimeDifference(ModCoreData.dragonEndTime,LocalDateTime.now())+"秒";
    }
}
