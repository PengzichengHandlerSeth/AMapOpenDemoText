package com.seth.amap;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Tip;
import com.sinfeeloo.openmap.OpenMapUtil;
import com.sinfeeloo.openmapdemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener, AMap.OnMapClickListener, GeoFenceListener, GeocodeSearch.OnGeocodeSearchListener {

    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;


    private TextView address;
    private TextView lan;
    private TextView lon;
    private TextView tv_ren;
    private TextView searchText;
    private TextView name;
    private double mLatitude;
    private double mLongitude;
    private String mName;
    private String mAdress;

    private String myadress;
    private double myLatitude;
    private double myLongitude;

    private LinearLayout root;
    private Marker maker;
    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.tv_search);
        name = findViewById(R.id.tv_name);
        address = findViewById(R.id.tv_address);
        lan = findViewById(R.id.tv_lan);
        lon = findViewById(R.id.tv_lon);
        tv_ren = findViewById(R.id.tv_ren);
        root = findViewById(R.id.ll_root);
        hasPermissionToReadNetworkStats();

        markerOption = new MarkerOptions().draggable(true);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputTipsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMapUtil.openMapPopupWindow(MainActivity.this, root, mName, mLatitude, mLongitude);
            }
        });
//        myLatitude=aMapLocation.getLatitude();
//        myLongitude=aMapLocation.getLongitude();
//        myadress= aMapLocation.getAddress();
        tv_ren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NearByHuman.class);
                intent.putExtra("userid", "Aes89347IudRR");
                intent.putExtra("longt", myLongitude);
                intent.putExtra("lat", myLatitude);
                intent.putExtra("address", myadress);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        if (mapView != null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
//        aMap.setOnMapClickListener(this);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
////        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转3种
////        //跟随：LOCATION_TYPE_MAP_FOLLOW
////        //旋转：LOCATION_TYPE_MAP_ROTATE
////        //定位：LOCATION_TYPE_LOCATE
////        aMap.setMyLocationType(AMap.MAP_TYPE_NORMAL);

        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101 && data != null) {
            Tip tip = data.getParcelableExtra("EXTRA_TIP");
            if (tip.getPoiID() != null && !tip.getPoiID().equals("")) {
                name.setText("名称：" + tip.getName());
                address.setText("地址：" + tip.getAddress());
                lan.setText("纬度：" + tip.getPoint().getLatitude());
                lon.setText("经度：" + tip.getPoint().getLongitude());
                mLatitude = tip.getPoint().getLatitude();
                mLongitude = tip.getPoint().getLongitude();


                mName = tip.getName();
                mAdress = tip.getAddress();
                LatLng latLng = new LatLng(mLatitude, mLongitude);
//                addCenterMarker(latLng);
                makepoint(latLng);
            }

        } else if (resultCode == 102 && data != null) {
            String keywords = data.getStringExtra("KEY_WORDS_NAME");
            if (keywords != null && !keywords.equals("")) {
                Toast.makeText(this, keywords, Toast.LENGTH_SHORT).show();
            }
        }


        TrafficInfo mTrafficInfo2 = new TrafficInfo(getApplicationContext());
        int uid = mTrafficInfo2.getUid();
        TrafficInfo mTrafficInfo = new TrafficInfo(getApplicationContext(), uid);

        Log.e("onActivityResult", "\nuid: " + uid +
                "\n获取总流量:       " + mTrafficInfo.getTrafficInfo() +
                "\n获取下载流量:      " + mTrafficInfo.getRcvTraffic() +
                "\n获取上传流量:      " + mTrafficInfo.getSndTraffic() +
                "\n获取当前下载流量总和: " + mTrafficInfo.getNetworkRxBytes() +
                "\n获取当前上传流量总和: " + mTrafficInfo.getNetworkTxBytes() +
                "\n获取当前网速: " + mTrafficInfo.getNetSpeed());
        NetworkStatsHelper2 helper;
        NetworkStatsHelper helper2;
//初始化这个工具类
        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getSystemService(NETWORK_STATS_SERVICE);
        helper = new NetworkStatsHelper2(getApplicationContext(), networkStatsManager);

        Log.e("onActivityResult2", "\n本机使用的 wifi 总流量: " + helper.getAllBytesWifi() +
                "\n本机使用的 mobile 总流量:       " + helper.getAllBytesMobile() +
                "\n获取指定应用 wifi 发送的当天总流量:      " + helper.getPackageTxDayBytesWifi(uid) +
                "\n获取当天的零点时间:      " + helper.getTimesmorning() +
                "\n根据包名获取uid: " + helper.getUidByPackageName(getApplicationContext(), "com.sinfeeloo.openmapdemo")
        );
        NetworkStatsManager networkStatsManager2 = (NetworkStatsManager) getSystemService(NETWORK_STATS_SERVICE);

        helper2 = new NetworkStatsHelper(networkStatsManager2, uid);

        Log.e("onActivityResult2", "\n本机今天使用的 Mobile 总流量: " + helper2.getAllTodayMobile(getApplicationContext()) +
                "\n本机使用的 mobile 总流量:       " + helper2.getAllMonthMobile(getApplicationContext()) +
                "\n本机使用的 wifi 发送总流量:      " + helper2.getAllRxBytesWifi() +
                "\n本机使用的 wifi 总流量:      " + helper2.getAllTxBytesWifi() +
                "\n获取mobile收到的当天总流量: " + helper2.getPackageRxBytesMobile(getApplicationContext()) +
                "\n获取mobile 发送的当天总流量  " + helper2.getPackageTxBytesMobile(getApplicationContext()) +
                "\n获取wifi 收到的当天总流量  " + helper2.getPackageRxBytesWifi() +
                "\n获取wifi 发送的当天总流量  " + helper2.getPackageTxBytesWifi()
        );

//        看看部分函数（非静态）：
//
////查询指定网络类型在某时间间隔内的总的流量统计信息
//        NetworkStats.Bucket querySummaryForDevice(int networkType, String subscriberId, long startTime, long endTime)
//
////查询总的网络使用统计信息
//        NetworkStats querySummary(int networkType, String subcriberId, long startTime, long endTime)
//
// //查询某uid在指定网络类型和时间间隔内的流量统计信息
//        NetworkStats queryDetailsForUid(int networkType, String subscriberId, long startTime, long endTime, int uid) 
//
////查询指定网络类型在某时间间隔内的详细的流量统计信息（包括每个uid）
//        NetworkStats queryDetails(int networkType, String subscriberId, long startTime, long endTime) 
        NetworkStatsManager networkStatsManager3 = (NetworkStatsManager) getSystemService(NETWORK_STATS_SERVICE);

        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String subId = tm.getSubscriberId();
        NetworkStats summaryStats = null;
        long summaryRx = 0;
        long summaryTx = 0;
        NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
        long summaryTotal = 0;
        try {
            summaryStats = networkStatsManager3.querySummary(ConnectivityManager.TYPE_MOBILE, subId, getMillsTime(), System.currentTimeMillis());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        do {
            summaryStats.getNextBucket(summaryBucket);
            int summaryUid = summaryBucket.getUid();
            int uid2 = getUidByPackageName(this, "com.sinfeeloo.openmapdemo");
            if (uid2 == summaryUid) {
                Log.e("onActivityResult3", "\n"+"uid:" + summaryBucket.getUid() +
                        " \n rx:" + summaryBucket.getRxBytes() +
                        " \n tx:" + summaryBucket.getTxBytes());
                summaryRx += summaryBucket.getRxBytes();
                summaryTx += summaryBucket.getTxBytes();
            }
        } while (summaryStats.hasNextBucket());
        Log.e("onActivityResult3", "爱奇艺当前时间段内移动流量使用情况：\n：" +
                "uid:" + getUidByPackageName(this, "com.sinfeeloo.openmapdemo") +
                " \n rx:" + summaryRx + " \n tx:" + summaryTx);
    }



    //从1970-01-01 00:00:00.000到具体日期的毫秒数
    public long getMillsTime() throws ParseException {
        //将字符串转为日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dstr = "2017-09-20 00:00:00 ";
        java.util.Date date = sdf.parse(dstr);
        long s1 = date.getTime();//将时间转为毫秒
        return s1;
    }

    public static int getUidByPackageName(Context context, String packageName) {
        int uid = -1;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            uid = packageInfo.applicationInfo.uid;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return uid;
    }


    CameraUpdate cameraUpdate;

    //根据地址绘制需要显示的点
    public void makepoint(LatLng latLng) {
        Log.e("Shunxu", "开始绘图");
//        使用默认点标记
//         maker = aMap.addMarker(new MarkerOptions().position(latLng).title("地点:"));


        //自定义点标记
//        if (markerOptions == null) {
        markerOptions = new MarkerOptions();

//        }else{
//
//        }
        markerOptions.position(latLng).title(mName).snippet(mAdress);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.ic_launcher)));//设置图标
//这行关键，标记Marker的类型xxx
        aMap.addMarker(markerOptions);
//        Marker.remove();
        //改变可视区域为指定位置
        //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
        cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 14, 0, 0));
        aMap.moveCamera(cameraUpdate);//地图移向指定区域

        //位置坐标的点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(MainActivity.this,"点击指定位置",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //位置上面信息窗口的点击事件
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MainActivity.this, "点击了我的地点", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private MarkerOptions markerOption = null;

    private List<Marker> markerList = new ArrayList<Marker>();
    private Marker centerMarker;
    private BitmapDescriptor ICON_YELLOW = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                Log.d("===经度：", "" + aMapLocation.getLongitude());
                Log.d("===纬度：", "" + aMapLocation.getLatitude());
                myLatitude = aMapLocation.getLatitude();
                myLongitude = aMapLocation.getLongitude();
                Log.i("onActivityResult", "onActivityResult:myLatitude " + myLatitude + "myLongitude" + myLongitude);
                myadress = aMapLocation.getAddress();
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            mLocationOption.setOnceLocation(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }


    List<GeoFence> fenceList = new ArrayList<GeoFence>();

    @Override
    public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
        Message msg = Message.obtain();
        if (i == GeoFence.ADDGEOFENCE_SUCCESS) {
            fenceList = list;
            msg.obj = s;
            msg.what = 0;
        } else {
            msg.arg1 = i;
            msg.what = 1;
        }

    }


    // 中心点坐标
    private LatLng centerLatLng = null;

    @Override
    public void onMapClick(LatLng latLng) {
//        markerOption.icon(ICON_YELLOW);
        centerLatLng = latLng;
        addCenterMarker(centerLatLng);
        lan.setText("纬度：" + centerLatLng.latitude);
        lon.setText("经度：" + centerLatLng.longitude);
    }

    private void addCenterMarker(LatLng latlng) {
        markerOption.icon(ICON_YELLOW);
        if (null == centerMarker) {
            centerMarker = aMap.addMarker(markerOption);
        }
        centerMarker.setPosition(latlng);
        markerList.add(centerMarker);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    //查看权限是否完善
    private boolean hasPermissionToReadNetworkStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }

        requestReadNetworkStats();
        return false;
    }

    // 打开“有权查看使用情况的应用”页面
    private void requestReadNetworkStats() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }
}
