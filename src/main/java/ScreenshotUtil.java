import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Benjamin on 2019/3/6.
 */

public class ScreenshotUtil {

    public static String RmImage = "adb shell rm -rf /sdcard/screenshot.png";
    public static String Screencap = "adb shell screencap -p /sdcard/screenshot.png";
    public static String PullImage = "adb pull /sdcard/screenshot.png /Users/xielianshi/Desktop";

    // 获取当前时间
    public static String GetNowTime(){
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    // 执行截图命令
    public static String Screencap(){
        TopAPPInfo.setScreencapTime(ScreenshotUtil.GetNowTime());
        Screencap = "adb shell screencap -p /sdcard/screenshot" + TopAPPInfo.getScreencapTime() + ".png";
        Screencap = DevicesTopAPP.CmdPull(Screencap);
        // 判断截图是否成功
        if (Screencap.indexOf("error")!=-1){
            // -1 不包含，其他为包含
            // 包含错误信息时
            TopAPPInfo.setScreencapStatus("Faild");

        }else{
            // 不包含错误信息时
            TopAPPInfo.setScreencapStatus("Success");
        }
        return Screencap;
    }

    // 执行pull同步图片命令
    public static String PullImage(){
        PullImage = "adb pull /sdcard/screenshot" + TopAPPInfo.getScreencapTime() + ".png";
        PullImage = DevicesTopAPP.CmdPull(PullImage);
        return PullImage;
    }

    // 执行删除命令
    public static String RmImage(){
        RmImage = "adb shell rm -rf /sdcard/screenshot" + TopAPPInfo.getScreencapTime() + ".png";
        RmImage = DevicesTopAPP.CmdPull(RmImage);
        return RmImage;
    }

    // 操作方法组合
    public static String ScreencapShot(){
        Screencap();
        PullImage();
        RmImage();
        return "Success";
    }

}
