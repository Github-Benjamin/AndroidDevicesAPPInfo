/**
 * Created by Benjamin on 2018/11/29.
 */

import java.text.DecimalFormat;
import java.util.regex.*;


public class Main {

    public static String GetAdbDevices = "adb devices";
    public static String GetScreen = "adb shell wm size";
    public static String GetDevicesBrand = "adb shell getprop ro.product.brand";
    public static String GetDevicesModel = "adb shell getprop ro.product.model";
    public static String GetDevicesVersion = "adb shell getprop ro.build.version.release";
    public static String GetDeviceVersionSdk = "adb shell getprop ro.build.version.sdk";
    public static String GetMeminfo = "adb shell cat /proc/meminfo";
    public static String GetStore = "adb shell df | grep /data";
    public static String GetCpuName = "adb shell cat /proc/cpuinfo |grep Hardware";
    public static String GetCpuBit = "adb shell cat /proc/cpuinfo |grep Processor";

//    public static void main(String[] args) {
//
//        GetBrandThread t1 = new GetBrandThread();
//        GetModelThread t2 = new GetModelThread();
//        GetOSVersionThread t3 = new GetOSVersionThread();
//
//        Thread thread1 = new Thread(t1);
//        Thread thread2 = new Thread(t2);
//        Thread thread3 = new Thread(t3);
//        thread1.start();
//        thread2.start();
//        thread3.start();
//
////        // 获取屏幕分辨率
////        GetScreen = CmdPull(GetScreen).split("Physical size: ")[1];
////
////        // 获取手机 brand model
////        GetDevicesBrand = CmdPull(GetDevicesBrand);
////        GetDevicesModel = CmdPull(GetDevicesModel);
////
////        // 获取系统版本
////        GetDevicesVersion = CmdPull(GetDevicesVersion);
////        GetDeviceVersionSdk = CmdPull(GetDeviceVersionSdk);
////
////        // 获取运行 总内存 空余内存
////        GetMeminfo = CmdPull(GetMeminfo);
////        GetMemTotal = CountMeminfo(GetMeminfo(GetMeminfo,"MemTotal:.*?(\\d+) kB"));
////        GetMemAvailable = CountMeminfo(GetMeminfo(GetMeminfo,"MemAvailable:.*?(\\d+) kB"));
////
////        // 获取存储 总存储
////        GetStore = CmdPull(GetStore);
////        String[] GetStoreList = GetStore.split("\n")[0].split(" ");
////        TotalMem = CountMeminfo(GetStoreList[2]);
////        SurplusMem = CountMeminfo(GetStoreList[3]);
////        UsingMem = CountMeminfo(GetStoreList[5]);
////
////        // 获取cpu型号
////        GetCpuName = CmdPull(GetCpuName).split("Hardware\t: ")[1];
////
////        // 获取cpu位数
////        GetCpuBit = GetCPUBit(CmdPull(GetCpuBit).split("\n")[0]);
////
////        System.out.print(GetDevicesBrand);
////        System.out.print(GetDevicesModel);
////        System.out.print(GetDevicesVersion);
////        System.out.print(GetDeviceVersionSdk);
////        System.out.print(GetScreen);
////        System.out.println(GetMemTotal);
////        System.out.println(GetMemAvailable);
////        System.out.println(TotalMem);
////        System.out.println(SurplusMem);
////        System.out.println(UsingMem);
////        System.out.print(GetCpuName);
////        System.out.println(GetCpuBit);
//
//
//    }


    public static void GetDevicesStatus()  throws InterruptedException {
        GetAdbDevicesThread t10 = new GetAdbDevicesThread();
        Thread thread10 = new Thread(t10);
        thread10.start();
        thread10.join();
    }

    public static void DoGetDevices() throws InterruptedException {

        GetBrandThread t1 = new GetBrandThread();
        GetModelThread t2 = new GetModelThread();
        GetOSVersionThread t3 = new GetOSVersionThread();
        GetSDKVersionThread t4 = new GetSDKVersionThread();
        GetScreenSizeThread t5 = new GetScreenSizeThread();
        GetMemTotalThread t6 = new GetMemTotalThread();
        GetTotalMemThread t7 = new GetTotalMemThread();
        GetCPUNameThread t8 = new GetCPUNameThread();
        GetCPUBitThread t9 = new GetCPUBitThread();

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        Thread thread3 = new Thread(t3);
        Thread thread4 = new Thread(t4);
        Thread thread5 = new Thread(t5);
        Thread thread6 = new Thread(t6);
        Thread thread7 = new Thread(t7);
        Thread thread8 = new Thread(t8);
        Thread thread9 = new Thread(t9);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();
        thread9.join();
    }

    // 执行cmd命令，并回调返回信息
    public static String CmdPull(String command){
        // 执行命令
        // String command = "adb shell getprop ro.product.brand";
        try {
            Process p = Runtime.getRuntime().exec(command);
            StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
            StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
            new Thread(errorStream).start();
            new Thread(outputStream).start();
            p.waitFor();
            String result = outputStream.output.toString() + errorStream.output.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "NaN";
        }

    }

    // 正则匹配运行内存大小信息,
    public static String GetMeminfo(String GetMeminfo,String re) {
        Pattern FindMemTotal = Pattern.compile(re);
        Matcher FindMemTotalMatcher = FindMemTotal.matcher(GetMeminfo);
        String MemTotal;
        if ( FindMemTotalMatcher.find( )) {
            MemTotal = FindMemTotalMatcher.group(1) ;
        }else {
            MemTotal = null ;
        }
        return MemTotal;
    }

    // 计算存储内存大小
    public static String CountMeminfo(String MemTotal) {
        // 计算内存大小，以G为内存单位
        DecimalFormat df = new DecimalFormat("0.00"); //格式化小数
        String num = df.format((float)Integer.parseInt(MemTotal)/1024/1000); //返回的是String类型
        MemTotal =  num + " G";
        return MemTotal;
    }

    // 判断cpu位数
    public static String GetCPUBit(String Processor){
        if( Processor.indexOf("aarch64" )!= -1 ){
            return "64位";
        }else {
            return "32位";
        }
    }

}

// 获取手机 brand
class GetBrandThread implements Runnable {
    private static String GetDevicesBrand;
    public void run() {
        GetDevicesBrand = Main.CmdPull(Main.GetDevicesBrand);
        // System.out.println("GetDevicesBrand:" + GetDevicesBrand);
        DevicesInfo.setBrand(GetDevicesBrand);
    }
}

// 获取手机 model
class GetModelThread implements Runnable {
    private static String GetDevicesModel;
    public void run() {
        GetDevicesModel = Main.CmdPull(Main.GetDevicesModel);
        DevicesInfo.setModel(GetDevicesModel);
    }
}

// 获取手机安卓系统版本
class GetOSVersionThread implements Runnable {
    private static String GetDevicesVersion;
    public void run() {
        GetDevicesVersion = Main.CmdPull(Main.GetDevicesVersion);
        DevicesInfo.setOSVersion(GetDevicesVersion);
    }
}

// 获取手机 SDKVersion
class GetSDKVersionThread implements Runnable {
    private static String GetSDKVersion;
    public void run() {
        GetSDKVersion = Main.CmdPull(Main.GetDeviceVersionSdk);
        DevicesInfo.setSDKVersion(GetSDKVersion);
    }
}

// 获取手机屏幕分辨率大小
class GetScreenSizeThread implements Runnable {
    private static String GetGetScreen;
    public void run() {
        // 获取手机 model
        GetGetScreen = Main.CmdPull(Main.GetScreen).split("Physical size: ")[1];
        DevicesInfo.setScreenSize(GetGetScreen);
    }
}

// 获取手机运行内存 总内存 使用内存
class GetMemTotalThread implements Runnable {
    private static String GetMeminfo;
    private static String MemTotal;
    private static String MemAvailable;

    public void run() {
        // 获取手机 model
        GetMeminfo = Main.CmdPull(Main.GetMeminfo);
        MemTotal = Main.CountMeminfo(Main.GetMeminfo(GetMeminfo,"MemTotal:.*?(\\d+) kB"));

        try {
            MemAvailable = Main.CountMeminfo(Main.GetMeminfo(GetMeminfo,"MemAvailable:.*?(\\d+) kB"));
        }catch (Exception e){
            MemAvailable = Main.CountMeminfo(Main.GetMeminfo(GetMeminfo,"Cached:.*?(\\d+) kB"));
        }

        DevicesInfo.setMemTotal(MemTotal);
        DevicesInfo.setMemAvailable(MemAvailable);
    }
}


// 获取手机存储 总存储 使用存储 剩余存储
class GetTotalMemThread implements Runnable {
    private static String GetStore;
    private static String TotalMem;
    private static String SurplusMem;
    private static String UsingMem;
    public void run() {
        GetStore = Main.CmdPull(Main.GetStore);
        String[] GetStoreList = GetStore.split("\n")[0].split(" ");
        TotalMem = Main.CountMeminfo(GetStoreList[2]);
        SurplusMem = Main.CountMeminfo(GetStoreList[3]);
        UsingMem = Main.CountMeminfo(GetStoreList[5]);
        DevicesInfo.setTotalMem(TotalMem);
        DevicesInfo.setSurplusMem(SurplusMem);
        DevicesInfo.setUsingMem(UsingMem);
    }
}

// 获取手机cpu型号
class GetCPUNameThread implements Runnable {
    private static String CPUName;
    public void run() {
       CPUName = Main.CmdPull(Main.GetCpuName).split("Hardware\t: ")[1];
        DevicesInfo.setCpuName(CPUName);
    }
}

// 获取手机cpu位数
class GetCPUBitThread implements Runnable {
    private static String CPUBit;
    public void run() {
        CPUBit = Main.GetCPUBit(Main.CmdPull(Main.GetCpuBit).split("\n")[0]);
        DevicesInfo.setCpuBit(CPUBit);
    }
}

// 获取手机状态
class GetAdbDevicesThread implements Runnable {
    private static String[] GetConsole;
    private static String AdbDevices;
    public void run() {
        AdbDevices = Main.CmdPull(Main.GetAdbDevices);
        GetConsole = AdbDevices.split("\n");
        int GetConsoleLenth = GetConsole.length;
        if( GetConsoleLenth == 2 && GetConsole[1].indexOf("device") != -1) {
            DevicesInfo.setAdbDevices("Success");
        }else if (GetConsoleLenth >= 3){
            DevicesInfo.setAdbDevices("请检查电脑是否连接多余设备?");
        }else{
            DevicesInfo.setAdbDevices("请检查设备是否正常连接!");
        }
    }
}