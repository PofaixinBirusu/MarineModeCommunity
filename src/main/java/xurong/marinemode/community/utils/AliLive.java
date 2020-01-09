package xurong.marinemode.community.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AliLive {
    private static final String pushDomain = "live.marinemode.top";
    private static final String pullDomain = "play.marinemode.top";
    private static final String appName = "MarineModeLive";
    private static final String liveKey = "5uDYB4ge8P";
    private static final String playKey = "V1jFHXIicv";
    /* 默认有效时间 30分钟 */
    private static int validTime = 30 * 60;

    public static Map<String, String> createPushUrl(String streamName) {
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + validTime);
        String strpush = "/" + appName + "/" + streamName + "-" + timestamp + "-0-0-" + liveKey;
        String pushUrl = "rtmp://" + pushDomain + "/" + appName + "/" + streamName + "?auth_key=" + timestamp + "-0-0-" + MD5Utils.getMD5(strpush);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>推流:   " + pushUrl);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("服务器", "rtmp://" + pushDomain + "/" + appName + "/");
        resultMap.put("串流密钥", streamName + "?auth_key=" + timestamp + "-0-0-" + MD5Utils.getMD5(strpush));
        return resultMap;
    }
    public static String getPlayUrl(String streamName) {
        String strviewrtmp1 = null;
        String strviewflv1 = null;
        String strviewm3u81 = null;

        String rtmpurl1 = null;
        String flvurl1 = null;
        String m3u8url1 = null;

        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + validTime);
        strviewrtmp1 = "/" + appName + "/" + streamName + "-" + timestamp + "-0-0-" + playKey;
        strviewflv1 = "/" + appName + "/" + streamName + ".flv-" + timestamp + "-0-0-" + playKey;
        strviewm3u81 = "/" + appName + "/" + streamName + ".m3u8-" + timestamp + "-0-0-" + playKey;
        rtmpurl1 = "rtmp://" + pullDomain + "/" + appName + "/" + streamName + "?auth_key=" + timestamp + "-0-0-" + MD5Utils.getMD5(strviewrtmp1);
        flvurl1 = "http://" + pullDomain + "/" + appName + "/" + streamName + ".flv?auth_key=" + timestamp + "-0-0-" + MD5Utils.getMD5(strviewflv1);
        m3u8url1 = "http://" + pullDomain + "/" + appName + "/" + streamName + ".m3u8?auth_key=" + timestamp + "-0-0-" + MD5Utils.getMD5(strviewm3u81);

        Map<String, String> resultMap = new HashMap<>(5);
        resultMap.put("rtm", rtmpurl1);
        resultMap.put("flv", flvurl1);
        resultMap.put("m3u8", m3u8url1);
        System.out.println(">>>>>>>>>>>>>>>>>>拉流:  " + rtmpurl1);
        System.out.println(">>>>>>>>>>>>>>>>>>拉流:    " + flvurl1);
        System.out.println(">>>>>>>>>>>>>>>>>>拉流:    " + m3u8url1);
        return rtmpurl1;
    }
    public static void main(String[]args) {
        String streamName = "marinemodeStream";
        createPushUrl(streamName);
        getPlayUrl(streamName);
    }
}
