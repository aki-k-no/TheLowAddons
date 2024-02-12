package com.aki.thelowmod.timer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;

import java.time.LocalDateTime;

public class HyakkaTimer extends AbstractTimer{
    @Override
    public void setTimer(String s) {
        if(s.equals("百火繚乱")){
            ModCoreData.HyakkaEndTime= LocalDateTime.now().plusSeconds(12);
        }
    }

    @Override
    public boolean shouldBeShown() {
        if(AKITheLowUtil.calcTimeDifference(ModCoreData.HyakkaEndTime,LocalDateTime.now())>0){
                return true;
        }

        return false;

    }

    @Override
    public String getDisplayText() {
        return "百花 "+AKITheLowUtil.calcTimeDifference(ModCoreData.HyakkaEndTime,LocalDateTime.now())+"秒";
    }
}
