package com.aki.thelowmod.config;

import com.aki.thelowmod.AKITheLowMod;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.xml.crypto.Data;
import java.util.List;

public class AKITheLowModConfigCore {
    public static Configuration cfg;
    public static final String HOLDING="HoldingItemInfo";
    public static final String CORE="Core";
    public static final String CT="CoolTime";
    public static final String ARMOR="Armor";

    public static final String UTIL="Util";

    public static List<IConfigElement> configElements=null;

    public static void setConfigElements(){
        configElements=(new ConfigElement(AKITheLowModConfigCore.cfg.getCategory(AKITheLowModConfigCore.HOLDING))).getChildElements();
        configElements.addAll((new ConfigElement(AKITheLowModConfigCore.cfg.getCategory(AKITheLowModConfigCore.CORE))).getChildElements());
        configElements.addAll((new ConfigElement(AKITheLowModConfigCore.cfg.getCategory(AKITheLowModConfigCore.CT))).getChildElements());
        configElements.addAll((new ConfigElement(AKITheLowModConfigCore.cfg.getCategory(AKITheLowModConfigCore.ARMOR))).getChildElements());
        configElements.addAll((new ConfigElement(AKITheLowModConfigCore.cfg.getCategory(AKITheLowModConfigCore.UTIL))).getChildElements());
    }
    public static void loadConfig(FMLPreInitializationEvent event) {
        // net.minecraftforge.common.config.Configurationのインスタンスを生成する。
        cfg = new Configuration(event.getSuggestedConfigurationFile(), AKITheLowMod.VERSION, true);
        // 初期化する。
        initConfig();
        // コンフィグファイルの内容を変数と同期させる。
        syncConfig();
    }


    /** コンフィグを初期化する。 */
    private static void initConfig() {
        // カテゴリのコメントなどを設定する。
        cfg.addCustomCategoryComment(HOLDING, "持ってるアイテムの情報についての設定");
        cfg.setCategoryLanguageKey(HOLDING, "config.akithelow.holdingiteminfo");
        cfg.addCustomCategoryComment(CORE,"modの中心設定");
        cfg.setCategoryLanguageKey(CORE,"config.akithelow.core");
        cfg.addCustomCategoryComment(CT,"スキルクールタイム表示についての設定");
        cfg.setCategoryLanguageKey(CT,"config.akithelow.ct");
        cfg.addCustomCategoryComment(ARMOR,"防具の耐久値がピンチな時の設定");
        cfg.setCategoryLanguageKey(ARMOR,"config.akithelow.armor");
        cfg.addCustomCategoryComment(UTIL,"アムルタイマーなど便利機能の設定");
        cfg.setCategoryLanguageKey(UTIL,"config.akithelow.util");


    }


    /** コンフィグを同期する。 */
    public static void syncConfig() {
        // 各項目の設定値を反映させる。
        // General
        DataStorage.isItemInfoVisible = cfg.getBoolean("HoldingItemVisible", HOLDING , DataStorage.isItemInfoVisible, "アイテム情報を表示するか決定します", "config.akithelow.holdingiteminfo.isvisible");
        DataStorage.renderHoldingItemX = cfg.getFloat("HoldingItemRenderX", HOLDING, DataStorage.renderHoldingItemX, 0F, 10F, "アイテム情報の表示場所X座標を決定します", "config.akithelow.holdingiteminfo.renderX");
        DataStorage.renderHoldingItemY = cfg.getFloat("HoldingItemRenderY", HOLDING, DataStorage.renderHoldingItemY, 0F, 10F, "アイテム情報の表示場所Y座標を決定します", "config.akithelow.holdingiteminfo.renderY");
        DataStorage.renderDistance = cfg.getFloat("HoldingItemRenderDistance", HOLDING, DataStorage.renderDistance, 0F, 1F, "アイテム情報の詳細項目の表間隔を決定します", "config.akithelow.holdingiteminfo.renderDistance");
        DataStorage.renderSize = cfg.getFloat("HoldingItemRenderSize", HOLDING, DataStorage.renderSize, 0F, 10F, "アイテム情報の詳細項目の表示サイズを決定します", "config.akithelow.holdingiteminfo.renderSize");
        DataStorage.shouldDeleteAPIOutput = cfg.getBoolean("shouldDeleteAPIOutput", CORE, DataStorage.shouldDeleteAPIOutput, "TheLowAPIの出力を消すかどうか決められます 他modを同時に使う場合はこれをFalseにしてください(他mod側が対応してない場合アリ)", "config.akithelow.core.shouldDeleteAPIOutput");
        DataStorage.renderCTX=cfg.getFloat("CTRenderX",CT, DataStorage.renderCTX,0f,10f,"CTの表示場所X座標を決定します","config.akithelow.ct.renderX");
        DataStorage.renderCTY=cfg.getFloat("CTRenderY",CT, DataStorage.renderCTY,0f,10f,"CTの表示場所Y座標を決定します","config.akithelow.ct.renderY");
        DataStorage.renderCTSize=cfg.getFloat("CTRenderSize",CT, DataStorage.renderCTSize,0f,10f,"CTの表示サイズを決定します","config.akithelow.ct.renderSize");
        DataStorage.isCTVisible=cfg.getBoolean("isCTVisible",CT, DataStorage.isCTVisible,"CTの表示するかを決定します","config.akithelow.ct.isCTVisible");
        DataStorage.alertArmorDamage=cfg.getFloat("ArmorAlertPercent",ARMOR, DataStorage.alertArmorDamage,0f,10f,"防具耐久値のアラートの境界値を設定します","config.akithelow.armor.alertArmorDamage");
        DataStorage.alertArmorDamageX=cfg.getFloat("ArmorAlertX",ARMOR, DataStorage.alertArmorDamageX,0f,10f,"防具耐久値のアラートの表示座標X","config.akithelow.armor.alertArmorX");
        DataStorage.alertArmorDamageY=cfg.getFloat("ArmorAlertY",ARMOR, DataStorage.alertArmorDamageY,0f,10f,"防具耐久値のアラートの表示座標Y","config.akithelow.armor.alertArmorY");
        DataStorage.alertArmorSize=cfg.getFloat("ArmorAlertSize",ARMOR, DataStorage.alertArmorSize,0f,10f,"防具耐久値のアラートの表示サイズ","config.akithelow.armor.alertArmorSize");
        DataStorage.utilityCTX=cfg.getFloat("UtilityCTX",UTIL,DataStorage.utilityCTX,0f,10f,"アムルタイマーなどの表示座標X","config.akithelow.util.utilityX");
        DataStorage.utilityCTY=cfg.getFloat("UtilityCTY",UTIL,DataStorage.utilityCTY,0f,10f,"アムルタイマーなどの表示座標Y","config.akithelow.util.utilityY");
        DataStorage.utilityCTSize=cfg.getFloat("UtilityCTSize",UTIL,DataStorage.utilityCTSize,0f,10f,"アムルタイマーなどの表示サイズ","config.akithelow.util.utilitySize");
        DataStorage.showNoThrow=cfg.getBoolean("ToggleNoThrow",UTIL,DataStorage.showNoThrow,"NoThrowアイコン表示非表示切り替え","config.akithelow.util.toggleNothrow");
        DataStorage.playAmereSound=cfg.getBoolean("ToggleAmereSound",UTIL,DataStorage.playAmereSound,"アムルタイマーの音のオンオフ","config.akithelow.util.toggleAmereSound");
        DataStorage.showAmereTimer=cfg.getBoolean("ShowAmereTimer",UTIL,DataStorage.showAmereTimer,"アムルタイマー表示オンオフ(これ以外にUtilityTimerを表示する設定をONにしてください)","config.akithelow.util.showAmereTimer");


        // 設定内容をコンフィグファイルに保存する。
        cfg.save();
    }
}
