package com.scuwangjun.datas;

import com.brtbeacon.sdk.BRTRegion;

/**
 * Created by 骏 on 2016/4/30.
 */
public class StaticDatas {

    //用于检测进出门店的Bright Beacon【BrightBeacon Plus】
    public static final BRTRegion INDOOR_REGION = new BRTRegion(
            "BrtBeacon Plus", "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0", null, 58585, 40216);

    //B-Tag
    public static final BRTRegion BREGION1 = new BRTRegion(
            "B-Tag", "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0", null, 36821, 35512);

    //BrtBeacon 307
    public static final BRTRegion BREGION2 = new BRTRegion(
            "BrtBeacon 307", "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0", null, 10046, 11062);

    //BrtBeacon 定位使用的三个
    public static final BRTRegion BREGIONLOC = new BRTRegion(
            "BrtBeacon 307", "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0", null, null, null);

    public static String basicUrl = "http://120.25.245.241/ibook/";
    public static String urlBookRecommend = basicUrl + "book_recommend.php";
    public static String urlCoffeeServe = basicUrl + "coffee_serve.php";
}
