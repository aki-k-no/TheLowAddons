package com.aki.thelowmod.timer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;
import com.aki.thelowmod.holding.HoldingItem;

import java.time.LocalDateTime;

public class TenkaTimer extends AbstractTimer{
    @Override
    public void setTimer(String s) {
        if(s.equals("天下無双")){
            ModCoreData.TenkaEndTime= LocalDateTime.now().plusSeconds(15);
        }
    }

    @Override
    public boolean shouldBeShown() {

        if(AKITheLowUtil.calcTimeDifference(ModCoreData.TenkaEndTime,LocalDateTime.now())>=0){
            return true;
        }
        return false;

    }

    @Override
    public String getDisplayText() {
        return "天下無双 "+AKITheLowUtil.calcTimeDifference(ModCoreData.TenkaEndTime,LocalDateTime.now())+"秒";
    }
}
