import java.util.Map;

/**
 * Created by Benjamin on 2019/8/26.
 */
public class deviceInfo {

    public static String selectDevice;
    public static String devicesID;
    public static String devicesRes;
    public static String[] deviceslist;
    public static Map devicesInfo;

    public static String getSelectDevice() {
        return selectDevice;
    }

    public static void setSelectDevice(String selectDevice) {
        deviceInfo.selectDevice = selectDevice;
    }


    public static String[] getDeviceslist() {
        return deviceslist;
    }

    public static String getDevicesID() {
        return devicesID;
    }

    public static String getDevicesRes() {
        return devicesRes;
    }

    public static void setDevicesRes(String devicesRes) {
        deviceInfo.devicesRes = devicesRes;
    }

    public static void setDevicesID(String devicesID) {
        deviceInfo.devicesID = devicesID;
    }

    public static void setDeviceslist(String[] deviceslist) {
        deviceInfo.deviceslist = deviceslist;
    }

    public static Map getDevicesInfo() {
        return devicesInfo;
    }

    public static void setDevicesInfo(Map devicesInfo) {
        deviceInfo.devicesInfo = devicesInfo;
    }
}

