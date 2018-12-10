/**
 * Created by Benjamin on 2018/12/4.
 */



public class DevicesTopAPP {


    public static String GetAdbDevices = "adb devices";
    public static String GetTopAPP = "adb shell dumpsys activity activities  |grep -i hist |grep visible=true -1";
    public static String PackName,PackPath,GetTopAPPBit;
    public static String CompatibleOS = "";
    public static String GetTopAPPMainActivity = "adb shell dumpsys package " + PackName + " |grep -B5 android.intent.category.LAUNCHER  |grep filter";
    public static String GetAPPPID = "adb shell ps " + CompatibleOS + " |grep "+ PackName ;
    public static String GetSystemPID = "adb shell ps  " + CompatibleOS + " |grep zygote64|head -1";
    public static String GetPmPath = "adb shell pm path " + PackName;
    public static String GetPullFile = "adb pull " + PackPath + " "+ PackName + ".apk";
    private static String SystemResult;


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



    public static String GetTopAPPMainActivity(String PackName){
        GetTopAPPMainActivity = "adb shell dumpsys package " + PackName + " |grep -B5 android.intent.category.LAUNCHER  |grep filter";
        return GetTopAPPMainActivity;
    }

    public static String SetTopAPPMainActivity(String GetTopAPPMainActivity){
        try {
            GetTopAPPMainActivity = GetTopAPPMainActivity.split(" ")[9];
            return GetTopAPPMainActivity;
        }catch (Exception e){
            return "APP NO LAUNCHER";
        }
    }

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

    public static void SetPID(String CompatibleOS,String PackName ){
        String PackNameResult = Main.CmdPull(GetAPPPID(CompatibleOS,PackName));
        GetAPPPID = PackNameResult.split(" ")[9];
        TopAPPInfo.setPIDPackName(GetAPPPID);

        // 获取系统PID进程ID
        SystemResult = Main.CmdPull(GetSystemPID(CompatibleOS));
        try {
            SystemResult = SystemResult.split(" ")[11];
            TopAPPInfo.setPIDzygote(SystemResult);
        }catch (Exception e){
            TopAPPInfo.setPIDzygote("NaN");
        }

        // 通过获取程序PID与系统的64位PID对比判断是否是 64位 或 32位程序
        if( TopAPPInfo.getPIDPackName().equals(TopAPPInfo.getPIDzygote()) ){
            TopAPPInfo.setPackBit("64位 程序");
        }else {
            TopAPPInfo.setPackBit("32位 程序");
        }

    }

    public static String GetAPPPID(String CompatibleOS,String PackName){
        GetAPPPID = "adb shell ps " + CompatibleOS + " |grep "+ PackName ;
        return GetAPPPID;
    }

    public static String GetSystemPID(String CompatibleOS){
        GetSystemPID = "adb shell ps  " + CompatibleOS + " |grep zygote64|head -1";
        return GetSystemPID;
    }

    public static String GetPmPath(String PackName){
        GetPmPath = "adb shell pm path " + PackName;
        return GetPmPath;
    }

    public static String GetPullFile(String PackPath, String PackName){
        GetPullFile = "adb pull " + PackPath + " "+ PackName + ".apk";
        GetPullFile = Main.CmdPull(GetPullFile);
        return GetPullFile;
    }


    public static void DoGetAPP() throws InterruptedException {

        GetPackNameThread t1 = new GetPackNameThread();
        GetTopAPPMainActivityThread t2 = new GetTopAPPMainActivityThread();
        GetTopAPPBitThread t3 = new GetTopAPPBitThread();
        GetPmPathThread t4 = new GetPmPathThread();

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        Thread thread3 = new Thread(t3);
        Thread thread4 = new Thread(t4);

        thread1.start();
        thread1.join();

        thread2.start();
        thread3.start();
        thread4.start();
        thread2.join();
        thread3.join();
        thread4.join();

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
        PackName = TopAPPInfo.getPackName(); // 获取程序包名
        GetTopAPPBit = DevicesTopAPP.GetTopAPPBit(PackName);
    }
}

// 获取顶层APP是否是 32位 或者 64位应用
class GetPmPathThread implements Runnable {
    private static String PackName;
    private static String GetPmPath;
    public void run() {
        // 备份apk,存储apk路径
        PackName = TopAPPInfo.getPackName();
        GetPmPath = Main.CmdPull(DevicesTopAPP.GetPmPath(PackName));
        String[]    GetPmPaths = GetPmPath.split("\n");
        TopAPPInfo.setPackPath(GetPmPaths[GetPmPaths.length-1].split(":")[1]);
    }
}