package com.aki.thelowmod.data;

import com.aki.thelowmod.api.Coordinates;
import com.aki.thelowmod.types.DungeonData;
import com.aki.thelowmod.types.Player_Status;
import com.aki.thelowmod.types.SkillCoolTime;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ModCoreData {

    public static boolean wasInTheLow=false;

    public static boolean[] hasBeenInTheLow=new boolean[10];

    public static boolean isDungeonDataLoaded=false;

    public static boolean isPlayerDataLoaded=false;

    public static boolean isAPISubscribed=false;

    public static LocalDateTime loggedTime;

    public static LocalDateTime lastPlayerRequest;

    public static DungeonData[] dungeonData=null;

    public static Map<String,Player_Status> player_status=new ConcurrentHashMap<String, Player_Status>();

    public static Map<String, LocalDateTime> cts=new ConcurrentHashMap<String, LocalDateTime>();

    public static String[] skillPlace=new String[6];

    public static Collection<NetworkPlayerInfo> playerInfos;


    public static LocalDateTime whenArmorBecamePinch;
    public static boolean[] isArmorPinch={false,false,false,false};

    public static LocalDateTime lastAmeretat=LocalDateTime.now();
    public static boolean isAlreadyKaihou=false;
    public static boolean isAlreadyYochou=false;

    public static boolean isFirstLogin=true;

    //以下タイマー関連
    public static LocalDateTime dragonEndTime=LocalDateTime.now();
    public static LocalDateTime ImpactionEndTime=LocalDateTime.now();
    public static LocalDateTime TenkaEndTime=LocalDateTime.now();
    public static LocalDateTime BSKEndTime=LocalDateTime.now();
    public static LocalDateTime HyakkaEndTime=LocalDateTime.now();
    public static LocalDateTime SeikishinEndTime=LocalDateTime.now();

    public static LocalDateTime RoAHandTime=LocalDateTime.now();

    public static ConcurrentHashMap<String,LocalDateTime> springTimer=new ConcurrentHashMap<String,LocalDateTime>();

    public static ConcurrentHashMap<String, Coordinates> springLocation=new ConcurrentHashMap<String,Coordinates>();

    public static String specialTag=null;

}

