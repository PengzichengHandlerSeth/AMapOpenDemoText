package com.seth.amap;

import android.annotation.TargetApi;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

import java.util.Calendar;

@TargetApi(Build.VERSION_CODES.M)
public class NetworkStatsHelper2 {
    Context context;
    NetworkStatsManager networkStatsManager;
    int packageUid;

    public NetworkStatsHelper2(NetworkStatsManager networkStatsManager) {
        this.networkStatsManager = networkStatsManager;
    }

    public NetworkStatsHelper2(Context context, NetworkStatsManager networkStatsManager) {
        this.context = context;
        this.networkStatsManager = networkStatsManager;
    }

    /**
     * 本机使用的 wifi 总流量
     */
    public long getAllBytesWifi() {
        NetworkStats.Bucket bucket;
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI,
                    "",
                    0,
                    System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;
        }
        //这里可以区分发送和接收
        return bucket.getTxBytes() + bucket.getRxBytes();
    }

    /**
     * 本机使用的 mobile 总流量
     */
    public long getAllBytesMobile() {
        NetworkStats.Bucket bucket;
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
                    getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                    0,
                    System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;
        }
        //这里可以区分发送和接收
        return bucket.getTxBytes() + bucket.getRxBytes();
    }

    private String getSubscriberId(Context context, int networkType) {
        if (ConnectivityManager.TYPE_MOBILE == networkType) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSubscriberId();
        }
        return "";
    }

    /**
     * 获取指定应用 wifi 发送的当天总流量
     *
     * @param packageUid 应用的uid
     * @return
     */
    public long getPackageTxDayBytesWifi(int packageUid) {
        NetworkStats networkStats = null;
        try {
            networkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_WIFI,
                    "",
                    getTimesmorning(),
                    System.currentTimeMillis(),
                    packageUid);
        } catch (RemoteException e) {
            return -1;
        }
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getTxBytes();
    }

    /**
     * 获取当天的零点时间
     *
     * @return
     */
    public static long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis());
    }

    //获得本月第一天0点时间
    public static int getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return (int) (cal.getTimeInMillis());
    }

    /**
     * 根据包名获取uid
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static int getUidByPackageName(Context context, String packageName) {
        int uid = -1;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            uid = packageInfo.applicationInfo.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return uid;
    }

}
