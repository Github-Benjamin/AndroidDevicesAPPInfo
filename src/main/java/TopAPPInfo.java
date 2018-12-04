/**
 * Created by Benjamin on 2018/12/4.
 */


public class TopAPPInfo {

    public static String AdbDevices;
    public static String PackName;
    public static String PackPath;
    public static String PIDzygote;
    public static String PIDPackName;
    public static String PackMainActivity;
    public static String PackTopActivity;
    public static String PackBit;

    public static String getPackBit() {
        return PackBit;
    }

    public static void setPackBit(String packBit) {
        PackBit = packBit;
    }

    public static String getAdbDevices() {
        return AdbDevices;
    }

    public static void setAdbDevices(String adbDevices) {
        AdbDevices = adbDevices;
    }

    public static String getPackName() {
        return PackName;
    }

    public static void setPackName(String packName) {
        PackName = packName;
    }

    public static String getPackPath() {
        return PackPath;
    }

    public static void setPackPath(String packPath) {
        PackPath = packPath;
    }

    public static String getPIDzygote() {
        return PIDzygote;
    }

    public static void setPIDzygote(String PIDzygote) {
        TopAPPInfo.PIDzygote = PIDzygote;
    }

    public static String getPIDPackName() {
        return PIDPackName;
    }

    public static void setPIDPackName(String PIDPackName) {
        TopAPPInfo.PIDPackName = PIDPackName;
    }

    public static String getPackMainActivity() {
        return PackMainActivity;
    }

    public static void setPackMainActivity(String packMainActivity) {
        PackMainActivity = packMainActivity;
    }

    public static String getPackTopActivity() {
        return PackTopActivity;
    }

    public static void setPackTopActivity(String packTopActivity) {
        PackTopActivity = packTopActivity;
    }


}
