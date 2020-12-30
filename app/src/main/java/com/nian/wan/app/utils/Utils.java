package com.nian.wan.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mc.developmentkit.utils.ToastUtil;
import com.nian.wan.app.R;
import com.nian.wan.app.activity.BaseApplication;
import com.nian.wan.app.bean.AboutUsData;
import com.nian.wan.app.bean.UserInfo;
import com.nian.wan.app.bean.UserLoginBean;
import com.nian.wan.app.db.UserInfoDB;
import com.nian.wan.app.http.HttpConstant;

import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static long b = 0;
    private static long c;
    private static long d;
    private static long l;
    private int versionCode;

    /**
     * 截断超出指定长度的字符串并用指定的字符代替
     *
     * @param length             超出多少长度
     * @param appendCharacter    替换的字符
     * @param operationCharacter 要进行操作的字符串
     * @return
     */
    public static String truncationCharacter(int length, String appendCharacter, String operationCharacter) {
        String result = null;
        if (!TextUtils.isEmpty(operationCharacter)) {
            if (operationCharacter.length() > length) {
                result = operationCharacter.substring(0, length) + appendCharacter;
            } else {
                return operationCharacter;
            }
        }
        return result;
    }


    public static Context getContext() {
        return BaseApplication.getApplication();
    }


    /**
     * 根据路径获得应用包名
     *
     * @param context
     * @param path
     * @return
     */
    public static String getPackageName(Context context, String path) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pm = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (pm != null) {
            ApplicationInfo Info = pm.applicationInfo;
            return Info.packageName;
        } else {
            return "";
        }
    }


    /**
     * 获取下载链接
     *
     * @param handler
     * @param id
     */
    public static String record_id;  //删除下载用的id标记

    @SuppressLint("HandlerLeak")
    public static void getDownLoadUrl(final Handler handler, final int id) {
        if (Utils.getLoginUser() != null) {
            //告知后端记录一下当前下载的游戏
            Map<String, String> TabMap = new HashMap<>();
            TabMap.put("game_id", id + "");
            TabMap.put("token", Utils.getLoginUser().token);
            HttpConstant.POST(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            try {
                                Log.e("告诉后台记录当前下载的游戏返回数据", msg.obj.toString());
                                JSONObject jsonObject = new JSONObject(msg.obj.toString());
                                int code = jsonObject.getInt("code");
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                if (code == 200) {
                                    record_id = jsonData.getString("record_id");
                                    if (!TextUtils.isEmpty(record_id)) {
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("game_id", "" + id);
                                        map.put("promote_id", new PromoteUtils().getPromoteId());
                                        HttpConstant.POST(handler, HttpConstant.GetdownURL, map, false);
                                    } else {
                                        ToastUtil.showToast("下载数据有误,下载失败!");  //删除下载用的id标记为空
                                    }

                                } else {
                                    ToastUtil.showToast("下载失败:" + jsonObject.getString("msg"));
                                }
                            } catch (Exception e) {
                                Log.e("告诉后台记录当前下载的游戏返回数据异常", e.toString());
                                ToastUtil.showToast("数据异常,下载失败!");
                            }
                            break;
                        case 2:
                            ToastUtil.showToast("网络异常,下载失败!");
                            break;
                    }
                }
            }, HttpConstant.TabDown, TabMap, false);
        }


    }


    /**
     * 判断包是否安装
     *
     * @param context
     * @return
     */
    public static boolean isInstall(Context context, String path) {
        try {
            if (path == null || path.equals("")) {
                Log.e("包名为空", "包名为空");
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo(path, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 打开应用程序
     *
     * @param context
     */
    public static void openApp(Context context, String path) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(path);
        context.startActivity(intent);
    }


    /**
     * 获取下载的Apk安装包
     *
     * @param packageName
     * @return
     */
    public static File getApkFile(String packageName) {
        String dir = FileUtils.getDownloadDir().getAbsolutePath();
        return new File(dir, packageName + ".apk");
    }


    /**
     * 安装应用程序
     *
     * @param context
     * @param apkFile
     */
    public static void installApp(Context context, File apkFile) {
        // 核心是下面几句代码
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String pakName = context.getPackageName();
            //兼容7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, pakName + ".fileProvider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                //兼容8.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                    if (!hasInstallPermission) {
                        //注意这个是8.0新API
                        Intent intentO = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                        intentO.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        x.app().startActivity(intentO);
                        return;
                    }
                }
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("安装应用程序异常", e.toString());
        }
    }


    public static String getSpent(long a) {
        c = a - b;
        d = c / 1024;
        b = a;
        if (d > 1024) {
            return d / 1024 + "M/s";
        }
        return d + "kb/s";
    }

    /**
     * 获取文件大小
     *
     * @param yixia
     * @param size
     * @return
     */
    public static String getSize(long yixia, long size) {
        String yixiazai = CleanMessageUtil.getFormatSize(yixia);
        String zsize = CleanMessageUtil.getFormatSize(size);
        return yixiazai + "/" + zsize;
    }

    /**
     * dip 转 px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {
        //
        // 公式： dp = px / (dpi / 160) px = dp * (dpi / 160)
        // dp = px / denisity
        // px = dp * denisity;
        DisplayMetrics metrics = x.app().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }


    public static int dpToPixel(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp < 0 ? dp : Math.round(dp * displayMetrics.density);
    }


    private static Toast toast;

    public static void TS(String content) {
        if (toast == null) {
            toast = Toast.makeText(x.app(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][345678]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }


    /**
     * 判断字符串是否全是汉字
     *
     * @param name
     * @return
     */
    public static boolean isChinese(String name) {
        int n = 0;
        for (int i = 0; i < name.length(); i++) {
            n = (int) name.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取当前本地apk的版本
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("version", "--------------------------------versionCode = " + versionCode);
        return versionCode;
    }



    /**
     * 获取本地软件版本名
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            versionName = packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        Log.i("Utils", "--------------------------------versionName = " + versionName);
        return versionName;
    }


    /**
     * 隐藏手机号中间4位
     *
     * @param hao
     * @return
     */
    public static String YinCang(String hao) {
        if (hao == null || hao.equals("")) {
            return "";
        }
        String maskNumber = hao.substring(0, 3) + "*****" + hao.substring(8, hao.length());
        return maskNumber;
    }

    /**
     * 隐藏身份证
     */
    public static String hiddenIdCard(String idCard) {
        String opIdCard = null;
        if (!TextUtils.isEmpty(idCard)) {
            opIdCard = idCard.substring(0, 1) + "**************" + idCard
                    .substring(idCard.length() - 1);
        }
        return opIdCard;
    }


    /**
     * 填充图片
     *
     * @param view
     * @param url
     */
    public static void Fill(ImageView view, String url) {
        x.image().bind(view, url, BitmapHelper.getBitmapUtils());
    }

    /**
     * 填充图片
     *
     * @param view
     * @param url
     */
    public static void Fill1(ImageView view, String url) {
        x.image().bind(view, url, BitmapHelper.getBitmapUtils());
    }

    /**
     * 判断用户是否登录    返回登录ID
     *
     * @return
     */
    public static String LoginUser() {
        DbManager db = DatabaseOpenHelper.getInstance();
        try {
            UserInfo first = db.findFirst(UserInfo.class);
            if (first != null) {
                return first.id + "";
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("获取用户ID报错", e.toString());
            return null;
        }
    }


    /**
     * 显示圆形头像，第三个参数为true
     *
     * @param imageView  图像控件
     * @param iconUrl    图片地址
     * @param isCircluar 是否显示圆形
     */
    public static void display(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(isCircluar)
                .setCrop(true)
                //设置支持gif
                .setIgnoreGif(false)
                .setLoadingDrawableId(R.mipmap.txrw)
                .setFailureDrawableId(R.mipmap.txrw)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    /**
     * 判断RecyclerView是否滑动到了底部
     */
    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state ==
                recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算GridView高度
     */
    public static void setListViewHeightBasedOnChildren(GridView listView, int shu) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = shu;
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    /**
     * 计算listview高度
     */
    public static void setListView(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public static int dipToPx(Context context, float paramFloat) {
        return (int) (0.5f + (context.getResources().getDisplayMetrics().density * paramFloat));
    }


    /**
     * 压缩头像
     */
    public static String getSmallBitmap(String filePath) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
        int degree = readPictureDegree(filePath);
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);

        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;

    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 获取系统当前时间时间戳
     */
    public static Long getSystemTime() {
        return Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10));//获取十位数时间戳
    }

    /**
     * 时间戳转换成日期格式
     */
    public static String getData(String time) {
        if (!time.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sd = sdf.format(new Date(Long.parseLong(time) * 1000));
            return sd;
        }
        return "";
    }

    /**
     * 时间戳转换成日期格式
     */
    public static String getData1(String time) {
        if (!time.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            String sd = sdf.format(new Date(Long.parseLong(time) * 1000));
            return sd;
        }
        return "";
    }

    /**
     * 时间戳转换成日期格式
     */
    public static String getData2(String time) {
        if (!time.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String sd = sdf.format(new Date(Long.parseLong(time) * 1000));
            return sd;
        }
        return "";
    }

    /**
     * 初始化状态栏位置
     */
    public static void initActionBarPosition(Activity activity, ImageView view) {
        boolean setTranslucent = Utils.setTranslucent(activity);
        if (setTranslucent == true) {
            int height = getStatusHeight(activity);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        } else {
            return;
        }
    }

    /**
     * 设置状态栏透明, 适用于图片作为背景的界面, 此时需要图片填充到状态栏
     */
    public static boolean setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }




    /**
     * 截取安卓版
     *
     * @param s
     * @return
     */
    public static String getJieQu(String s) {
        if (s == null) {
            return "";
        }
        int i = s.indexOf("安卓版");
        if (i != -1) {
            String substring = s.substring(0, i - 1);
            return substring;
        } else {
            return s;
        }
    }




    /**
     * 获取正在登录的User
     *
     * @return
     */
    public static UserInfo getLoginUser() {
        DbManager db = DatabaseOpenHelper.getInstance();
        try {
            UserInfo byId = db.findById(UserInfo.class, 1);
            if (byId != null) {
                return byId;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     * 保存用户信息
     */
    public static void persistentUserInfo(UserLoginBean userLoginBean){
        try {
            UserInfoDB userInfoDB = new UserInfoDB();
            userInfoDB.id = 1;
            userInfoDB.uid = userLoginBean.getUser_id();
            userInfoDB.account = userLoginBean.getAccount();
            userInfoDB.token = userLoginBean.getToken();
            userInfoDB.portrait = userLoginBean.getHead_icon();
            DbManager db = DatabaseOpenHelper.getInstance();
            db.saveOrUpdate(userInfoDB);
            LogUtils.e("存进去了", "存进去了");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取用户信息
     */
    public static UserInfoDB getPersistentUserInfo() {
        DbManager db = DatabaseOpenHelper.getInstance();
        try {
            UserInfoDB user = db.findById(UserInfoDB.class, 1);
            if (user != null) {
                return user;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 删除持久化用户
     */
    public static boolean deletePersistentUserInfo() {
        DbManager db = DatabaseOpenHelper.getInstance();
        try {
            db.deleteById(UserInfoDB.class, "1");
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取持久化关于我们信息
     */
    public static AboutUsData getPersistentAboutUsData() {
        DbManager db = DatabaseOpenHelper.getInstance();
        try {
            AboutUsData aboutUsData = db.findById(AboutUsData.class, 2);
            if (aboutUsData != null) {
                return aboutUsData;
            } else {
                return null;
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 加群
     */

    /****************
     * 发起添加群流程。群号：牛擦协会(125638122) 的 key 为： HD4wt8XxGOIXKUHrvs0wK-d_PPMywLHI
     * 调用 joinQQGroup(HD4wt8XxGOIXKUHrvs0wK-d_PPMywLHI) 即可发起手Q客户端申请加群 牛擦协会(125638122)
     *
     * @param
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Context context) {
        Log.e("一键加群", "" + HttpConstant.qunkey);
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" +
                HttpConstant.qunkey));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            ToastUtil.showToast("未安装手Q或安装的版本不支持");
            return false;
        }
    }


    /**
     * 与QQ客服对话
     *
     * @param context
     */
    public static void talkWithQQCustom(Context context) {
        if (checkApkExist(context, "com.tencent.mobileqq")) {
            AboutUsData aboutUsData = getPersistentAboutUsData();
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + aboutUsData.getPC_SET_SERVER_QQ() + "&version=1")));
        } else {
            ToastUtil.showToast("本机未安装QQ应用");
        }
    }

    /**
     * 与QQ客服对话
     *
     * @param context
     */
    public static void talkWithQQCustom(Context context, String qq) {
        if (checkApkExist(context, "com.tencent.mobileqq")) {
            AboutUsData aboutUsData = getPersistentAboutUsData();
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1")));
        } else {
            ToastUtil.showToast("本机未安装QQ应用");
        }
    }


    /**
     * 一键唤起QQ聊天界面
     *
     * @param context
     * @param packageName
     * @return
     */
    private static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
