import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benjamin on 2019/8/26.
 */
public class getDevices {
    private static String[] devicelist = new String[0];

    // 获取电脑连接的所有设备id的brand、model
    public static void getDevicesInfo(String getDevicesRes){
        getDevices(getDevicesRes);
        Map devicesInfo = new HashMap();
        for(String device: devicelist){
            getBrandModel(device,devicesInfo);
        }

        String[] devicelist = new String[0];
        for (Object device : devicesInfo.keySet()) {
            devicelist = insert(devicelist,device.toString());
        }

        deviceInfo.setDeviceslist(devicelist);
        deviceInfo.setDevicesInfo(devicesInfo);
    }

    // 获取adb devices设备列表中的设备id信息
    private static void getDevices(String getDevicesRes){
        String[] resList = getDevicesRes.split("\n");
        for( String resLine : resList ) {
            String[] devicesList = resLine.split("\\s+");
            if(devicesList.length == 2 && Arrays.asList(devicesList).contains("device")){
                for( String devices : devicesList ) {
                    // 追加设备id
                    if( !devices.equals("device") ){devicelist = insert(devicelist,devices);}
                }
            }
        }
    }

    // 获取设备的Brand、model
    private static void getBrandModel(String devicesMark,Map devices){
        String Brand,Model;
        String getBrandModel = "adb -s " + devicesMark + " shell getprop | grep product";
        String res = DevicesTopAPP.CmdPull(getBrandModel);
        // 正则表达式部分代码
        Matcher getBrand = Pattern.compile("\\[ro.product.brand\\]: \\[(.*?)\\]").matcher(res);
        if ( getBrand.find( )) {
            Brand = getBrand.group(1);
        }else {
            Brand = "null" ;
        }
        Matcher getModel = Pattern.compile("\\[ro.product.model\\]: \\[(.*?)\\]").matcher(res);
        if ( getModel.find( )) {
            Model = getModel.group(1);
        }else {
            Model = "null" ;
        }
        devices.put(Brand+" "+Model,devicesMark);

    }

    // 往字符串数组追加新数据
    private static String[] insert(String[] arr, String str) {
        int size = arr.length;
        String[] tmp = new String[size + 1];
        for (int i = 0; i < size; i++){
            tmp[i] = arr[i];
        }
        tmp[size] = str;
        return tmp;
    }

}

