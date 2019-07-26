import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Benjamin on 2018/12/4.
 */

public class DevicesTopAPP {

    public static String GetAdbDevices = "adb devices";
    public static String GetTopAPP = "adb shell dumpsys activity activities  |grep -i hist |grep visible=true -1";
    public static String PackName,PackPath,PackMainActivity;
    public static String CompatibleOS = "";
    public static String GetTopAPPMainActivity = "adb shell dumpsys package " + PackName + " |grep -B5 android.intent.category.LAUNCHER  |grep filter";
    public static String GetAppInfo = "adb shell dumpsys package " + PackName + "|grep version";
    public static String GetAPPPID = "adb shell ps " + CompatibleOS + " |grep "+ PackName ;
    public static String GetSystemPID = "adb shell ps  " + CompatibleOS + " |grep zygote64|head -1";
    public static String GetPmPath = "adb shell pm path " + PackName;
    public static String GetPullFile = "adb pull " + PackPath + " "+ PackName + ".apk";
    public static String CloseAPP = "adb shell am force-stop" + PackName;
    public static String CleanAPP = "adb shell am clear " + PackName;
    public static String UninstallAPP = "adb uninstall" + PackName;
    public static String StartAPP = "adb shell am start -n" + PackMainActivity;
    private static String SystemResult;
    private static String mCurrentFocus = "adb shell dumpsys window | grep mCurrentFocus";

//    public static void main(String[] args) {
//
//        // 获取顶层APP程序包名和Activity
//        GetTopAPP = Main.CmdPull(GetTopAPP).split(" ")[11];  // 此处有坑，不支持 32位 手机 head -1
//        TopAPPInfo.setPackTopActivity(GetTopAPP);
//        TopAPPInfo.setPackName(GetTopAPP.split("/")[0]);
//
//        // 获取顶层APP的第一启动启动界面Activity
//        PackName = TopAPPInfo.getPackName(); // 获取程序包名
//        GetTopAPPMainActivity = Main.CmdPull(GetTopAPPMainActivity(PackName));
//        TopAPPInfo.setPackMainActivity(SetTopAPPMainActivity(GetTopAPPMainActivity)); // 异常处理部分程序无该界面
//
//        // 获取顶层APP是否是 32位 或者 64位应用
//        GetTopAPPBit = GetTopAPPBit(PackName);
//
//        // 备份apk,存储apk路径
//        GetPmPath = Main.CmdPull(GetPmPath(PackName));
//        String[]    GetPmPaths = GetPmPath.split("\n");
//        TopAPPInfo.setPackPath(GetPmPaths[GetPmPaths.length-1].split(":")[1]);
//
//        // 执行pull备份到本地
//        // GetPullFile(TopAPPInfo.getPackPath(),TopAPPInfo.getPackName());
//
//    }

    // 获取顶层APP的launcher信息
    public static String GetTopAPPMainActivity(String PackName){
        GetTopAPPMainActivity = "adb shell dumpsys package " + PackName + " |grep -B5 android.intent.category.LAUNCHER  |grep filter";
        return GetTopAPPMainActivity;
    }

    // 设置顶层APP的launcher_activity
    public static String SetTopAPPMainActivity(String GetTopAPPMainActivity){
        try {
            GetTopAPPMainActivity = GetTopAPPMainActivity.split(" ")[9];
            return GetTopAPPMainActivity;
        }catch (Exception e){
            return "APP NO LAUNCHER";
        }
    }


    // 获取顶层应用程序包名 ;获取屏幕顶层应用进程名称 adb shell dumpsys activity activities|grep app=ProcessRecord
    public static void GetPIDPackageName(){
        String adbshell = "adb shell dumpsys activity activities|grep app=ProcessRecord";
        adbshell = Main.CmdPull(adbshell);

        // 获取应用程序包名
        String result = adbshell;
        String PackName = result.split(":")[1].split("/")[0];

        // 获取应用程序PID进程名
        adbshell = adbshell.split("\\s+")[2];
        String pidpackagename = adbshell.split("/")[1];
        pidpackagename = pidpackagename.substring(0,pidpackagename.length()-1);
        pidpackagename = pidpackagename.substring(0,pidpackagename.indexOf("a")) + "_" + pidpackagename.substring(pidpackagename.indexOf("a"),pidpackagename.length());
        TopAPPInfo.setPIDPackageName(pidpackagename);

        // 获取到应用程序PID进程名就立即使用执行获取 adb shell ps|grep <pid packagename>
        GetPackNameResult();

    }


    // 执行获取进程PID的信息 > adb shell ps|grep u0_a1005
    public static String GetAPPPID(String CompatibleOS,String PackName){
        GetAPPPID = "adb shell ps " + CompatibleOS + " |grep "+ PackName;
        return GetAPPPID;
    }

    // 获取APP的进程PID
    public static String GetTopAPPBit(String PackName){
        String PackNameResult = Main.CmdPull(GetAPPPID(CompatibleOS,PackName));
        if( PackNameResult.length() < 10 ){
            CompatibleOS = "-A";
            SetPID(CompatibleOS,PackName);
            return TopAPPInfo.getPackBit();
        }else {
            SetPID(CompatibleOS,PackName);
            return TopAPPInfo.getPackBit();
        }
    }


    // 获取adb shell ps|grep u0_a1005过滤信息
    public static void GetPackNameResult(){
        String PackNameResult = Main.CmdPull(GetAPPPID(CompatibleOS,TopAPPInfo.getPIDPackageName()));
        TopAPPInfo.setPackNameResult(PackNameResult);
    }


    // 获取adb shell ps|grep zygote64过滤信息
    public static void GetSystemResult(){
        String SystemResult = Main.CmdPull(GetSystemPID(CompatibleOS));
        TopAPPInfo.setSystemResult(SystemResult);
    }



    // 根据获取到的 系统进程PID 与 应用程序PID 判断运行程序的位数
    public static void SetPID(String CompatibleOS,String PackName ){

        // 获取APP PackName PID进程ID
        GetAPPPID = GetAPPPID(CompatibleOS,PackName);

        String PackNameResult = TopAPPInfo.getPackNameResult();
        GetAPPPID = PackNameResult.split("\\s+")[2];
        TopAPPInfo.setPIDPackName(GetAPPPID);

        // 获取系统 64位zygote PID进程ID
        SystemResult = TopAPPInfo.getSystemResult();

        try {
            // 解决获取多个64位zygote的情况的bug，导致判断程序位数不准确
               String[] testss = SystemResult.split("\n");
               if(testss.length == 1){
                   SystemResult = SystemResult.split("\\s+")[1];
                   TopAPPInfo.setPIDzygote(SystemResult);
               }else if( testss.length ==2 ){
                   SystemResult  = testss[0].split("\\s+")[1];
                   TopAPPInfo.setPIDzygote(SystemResult);

                   SystemResult  = testss[1].split("\\s+")[1];
                   TopAPPInfo.setPIDzygotetwo(SystemResult);
                }
        }catch (Exception e){
            TopAPPInfo.setPIDzygote("NaN");
        }

        // 通过获取程序PID与系统的64位PID对比判断是否是 64位 或 32位程序

        // 未做为空判断
        if( TopAPPInfo.getPIDzygote().equals("") && TopAPPInfo.getPIDPackName().equals("") ){
            TopAPPInfo.setPackBit("32位 程序");
        }
        else if( TopAPPInfo.getPIDPackName().equals(TopAPPInfo.getPIDzygote()) ){
            TopAPPInfo.setPackBit("64位 程序");
        }else if( TopAPPInfo.getPIDPackName().equals(TopAPPInfo.getPIDzygotetwo()) ){
            TopAPPInfo.setPackBit("64位 程序");
        } else {
            TopAPPInfo.setPackBit("32位 程序");
        }

    }



    // 解决三星S9上获取多个64位进程pid的情况的bug
    public static String GetSystemPID(String CompatibleOS){
        GetSystemPID = "adb shell ps  " + CompatibleOS + " |grep zygote64";
        return GetSystemPID;
    }

    public static String GetPmPath(String PackName){
        GetPmPath = "adb shell pm path " + PackName;
        return GetPmPath;
    }

    // 执行备份文件命令
    public static String GetPullFile(String PackPath, String PackName){
        GetPullFile = "adb pull " + PackPath + " "+ PackName + ".apk";
        GetPullFile = Main.CmdPull(GetPullFile);
        // 判断备份是否成功
        if (GetPullFile.indexOf("error")!=-1){
            // -1 不包含，其他为包含
            // 包含错误信息时
            TopAPPInfo.setPullStatus(GetPullFile);

        }else{
            // 不包含错误信息时
            TopAPPInfo.setPullStatus("Success");
        }
        return GetPullFile;
    }

    // 执行关闭APP命令
    public static String CloseAPP(String PackName){
        CloseAPP = "adb shell am force-stop " + PackName;
        CloseAPP = Main.CmdPull(CloseAPP);
        return CloseAPP;
    }

    // 执行清理APP缓存命令
    public static String CleanAPP(String PackName){
        CleanAPP = "adb shell pm clear " + PackName;
        CleanAPP = Main.CmdPull(CleanAPP);
        // 判断清理是否成功
        if (CleanAPP.indexOf("Error")!=-1){
            // -1 不包含，其他为包含
            // 包含错误信息时
            TopAPPInfo.setClearStatus(CleanAPP);

        }else{
            // 不包含错误信息时
            TopAPPInfo.setClearStatus("Success");
        }
        return CleanAPP;
    }

    // 执行启动APP命令
    public static String StartAPPLaunchableActivity(String LaunchableActivity){
        StartAPP = "adb shell am start -n" + LaunchableActivity;
        StartAPP = Main.CmdPull(StartAPP);
        return StartAPP;
    }

    // 执行卸载APP命令
    public static String UninstallAPP(String PackName){
        UninstallAPP = "adb uninstall " + PackName;
        UninstallAPP = Main.CmdPull(UninstallAPP);

        // 判断卸载是否成功
        if (UninstallAPP.indexOf("Failure")!=-1 || UninstallAPP.indexOf("Exception") != -1 || UninstallAPP.indexOf("error") != -1 ){
            // -1 不包含，其他为包含
            // 包含错误信息时
            TopAPPInfo.setUninstallStatus(UninstallAPP);
        }else{
            // 不包含错误信息时
            TopAPPInfo.setUninstallStatus("Success");
        }
        return UninstallAPP;
    }

    // 获取 app versionName, versionCode, minSdk, targetSdk
    public static String GetAppInfo(String PackName){
        GetAppInfo = "adb shell dumpsys package " + PackName + "|grep version";
        GetAppInfo = Main.CmdPull(GetAppInfo);
        return GetAppInfo;
    }

    // 正则匹配 AppInfo,
    public static String GetAppInfo(String GetAppInfo,String re) {
        Pattern FindString = Pattern.compile(re);
        Matcher FindStringInfo = FindString.matcher(GetAppInfo);
        String FindSring;
        if ( FindStringInfo.find( )) {
            FindSring = FindStringInfo.group(1) ;
        }else {
            FindSring = "null" ;
        }
        return FindSring;
    }

    // adb命令执行后，设置实例对象的值
    public static void SetAppInfo(String PackName){
        GetAppInfo = GetAppInfo(PackName);
        TopAPPInfo.setVersionCode(GetAppInfo(GetAppInfo,"versionCode=(\\d+)"));
        TopAPPInfo.setVersionName(GetAppInfo(GetAppInfo,"versionName=(\\d+(\\.\\d+)*)"));
        TopAPPInfo.setMinSdk(GetAppInfo(GetAppInfo,"minSdk=(\\d+)"));
        TopAPPInfo.setTargetSdk(GetAppInfo(GetAppInfo,"targetSdk=(\\d+)"));
    }

    // 获取屏幕焦点应用信息
    public static  String GetmCurrentFocus(){
        mCurrentFocus = Main.CmdPull("adb shell dumpsys window | grep mCurrentFocus");


        String FindSring = null;
        try {
            FindSring = mCurrentFocus.split("\\s+")[3];
            FindSring = FindSring.substring(0,FindSring.length()-1);
        }catch (Exception e){
            FindSring = null;
        }


        // 正则表达式部分代码
        // Pattern FindString = Pattern.compile("u.*? (.*?)}");
        // Matcher FindStringInfo = FindString.matcher(mCurrentFocus);
        // String FindSring;
        // if ( FindStringInfo.find( )) {
        //     FindSring = FindStringInfo.group(1) ;
        // }else {
        //     FindSring = "null" ;
        // }
        TopAPPInfo.setmCurrentFocus(FindSring);
        System.out.println("\nAndroid Screen mCurrentFocus: " + FindSring);
        System.out.println("Android Devices TopActivity: " + TopAPPInfo.getPackTopActivity());
        return FindSring;
    }


    // 获取信息主线程组
    public static void DoGetAPP() throws InterruptedException {
        GetPackNameThread t1 = new GetPackNameThread();
        GetTopAPPMainActivityThread t2 = new GetTopAPPMainActivityThread();
        GetTopAPPBitThread t3 = new GetTopAPPBitThread();
        GetPmPathThread t4 = new GetPmPathThread();
        GetAppInfoThread t5 = new GetAppInfoThread();
        GetmCurrentFocusThread t6 = new GetmCurrentFocusThread();
        GetPIDPackageNameThread t7 = new GetPIDPackageNameThread();
        GetSystemResultThread t8 = new GetSystemResultThread();


        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        Thread thread3 = new Thread(t3);
        Thread thread4 = new Thread(t4);
        Thread thread5 = new Thread(t5);
        Thread thread6 = new Thread(t6);
        Thread thread7 = new Thread(t7);
        Thread thread8 = new Thread(t8);



        thread7.start();
        thread8.start();
        thread1.start();
        thread6.start();
        thread7.join();
        thread8.join();
        thread6.join();
        thread1.join();

        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();




        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();




        System.out.println( "> adb shell ps|grep zygote64 && adb shell ps|grep " + TopAPPInfo.getPIDPackageName() + "\n" + TopAPPInfo.getSystemResult() + TopAPPInfo.getPackNameResult());
    }


}


// 获取手机 第一界面APP包名
class GetPackNameThread implements Runnable {
    private static String GetTopAPP;
    public void run() {
        // 获取顶层APP程序包名和Activity
        GetTopAPP = Main.CmdPull(DevicesTopAPP.GetTopAPP).split(" ")[11];  // 此处有坑，不支持 32位 手机 head -1
        TopAPPInfo.setPackTopActivity(GetTopAPP);
        TopAPPInfo.setPackName(GetTopAPP.split("/")[0]);
    }
}

// 获取手机 第一界面PS应用进程名
class GetPIDPackageNameThread implements Runnable {
    public void run() {
        DevicesTopAPP.GetPIDPackageName();
    }
}

// 获取手机 第一界面PS应用进程名
class GetPackNameResultThread implements Runnable {
    public void run() {
        DevicesTopAPP.GetPackNameResult();
    }
}

// 获取手机 第一界面PS应用进程名
class GetSystemResultThread implements Runnable {
    public void run() {
        DevicesTopAPP.GetSystemResult();
    }
}

// 获取 mCurrentFocus
class GetmCurrentFocusThread implements Runnable{
    public void run(){
        DevicesTopAPP.GetmCurrentFocus();
    }
}

// 获取手机 第一界面APP包名
class GetTopAPPMainActivityThread implements Runnable {
    private static String GetTopAPPMainActivity;
    private static String PackName;
    public void run() {
        // 获取顶层APP的第一启动启动界面Activity
        PackName = TopAPPInfo.getPackName(); // 获取程序包名
        GetTopAPPMainActivity = Main.CmdPull(DevicesTopAPP.GetTopAPPMainActivity(PackName));
        TopAPPInfo.setPackMainActivity(DevicesTopAPP.SetTopAPPMainActivity(GetTopAPPMainActivity)); // 异常处理部分程序无该界面
    }
}

// 获取顶层APP是否是 32位 或者 64位应用
class GetTopAPPBitThread implements Runnable {
    private static String GetTopAPPBit;
    private static String PackName;
    public void run() {
        // 获取顶层APP的第一启动启动界面Activity
        PackName = TopAPPInfo.getPIDPackageName(); // 获取程序包名
        GetTopAPPBit = DevicesTopAPP.GetTopAPPBit(PackName);
    }
}

// 备份apk,存储apk路径
class GetPmPathThread implements Runnable {
    private static String PackName;
    private static String GetPmPath;
    public void run() {
        // 备份apk,存储apk路径
        PackName = TopAPPInfo.getPackName();
        GetPmPath = Main.CmdPull(DevicesTopAPP.GetPmPath(PackName));
        String[]    GetPmPaths = GetPmPath.split("\n");
        TopAPPInfo.setPackPath(GetPmPaths[0].split(":")[1]);
    }
}


// 获取APP versionName, versionCode, minSdk, targetSdk
class GetAppInfoThread implements Runnable {
    private static String PackName;
    public void run() {
        PackName = TopAPPInfo.getPackName();
        DevicesTopAPP.SetAppInfo(PackName);
    }
}