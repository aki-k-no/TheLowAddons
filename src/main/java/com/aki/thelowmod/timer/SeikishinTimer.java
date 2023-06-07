package com.aki.thelowmod.timer;

import com.aki.thelowmod.api.AKITheLowUtil;
import com.aki.thelowmod.data.ModCoreData;

import java.time.LocalDateTime;

public class SeikishinTimer extends AbstractTimer{
    @Override
    public void setTimer(String s) {
        if(s.equals("星輝神の歌声")){
            ModCoreData.SeikishinEndTime= LocalDateTime.now().plusSeconds(20);
        }
    }

    @Override
    public boolean shouldBeShown() {
        if(AKITheLowUtil.calcTimeDifference(ModCoreData.SeikishinEndTime,LocalDateTime.now())>=0){
            return true;
        }

        return false;

    }

    @Override
    public String getDisplayText() {
        return "星輝神の歌声 "+AKITheLowUtil.calcTimeDifference(ModCoreData.SeikishinEndTime,LocalDateTime.now())+"秒";
    }
}
