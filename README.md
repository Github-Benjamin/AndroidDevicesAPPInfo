

# AndroidDevicesInfo

## Java GUI程序 - 获取Android设备系统信息工具



简介：该程序通知Java执行ADB CMD命名获取回调信息判断设备信息，获取信息时通过多线程操作节约读取时间；
	




分析需要信息如下：

1、手机品牌
2、手机型号 
3、系统版本 SDK版本 
4、屏幕分辨率 
5、运行内存 
6、存储内存 
7、CPU型号 
8、CPU位数


--------------------------------------------------

## 获取系统版本
$ adb shell getprop ro.build.version.release
8.0.0


## 获取手机 brand model
$ adb shell getprop | grep product
[ro.build.product]: [aries]
[ro.product.board]: [MSM8960]
[ro.product.brand]: [Xiaomi]
[ro.product.cpu.abi2]: [armeabi]
[ro.product.cpu.abi]: [armeabi-v7a]
[ro.product.device]: [aries]
[ro.product.locale.language]: [zh]
[ro.product.locale.region]: [CN]
[ro.product.manufacturer]: [Xiaomi]
[ro.product.model]: [MI 2]
[ro.product.name]: [aries]
[ro.product.real_model]: [MI 2S]

$ adb shell getprop ro.product.model
OS105


## 获取屏幕分辨率
$ adb shell wm size
Physical size: 1080x1920


## 获取手机cpu型号名称
$ adb shell cat /proc/cpuinfo |grep Hardware
Hardware        : mt6799


## 获取手机内存信息， 
$ adb shell cat /proc/meminfo
MemTotal:        3711344 kB
MemFree:          156944 kB
MemAvailable:    1682840 kB


## 获取存储空间大小
$ adb shell df | grep /data
/dev/block/sda17  54126016 14795872  39330144  28% /data
/data/media       54126016 14795872  39330144  28% /mnt/runtime/default/emulated


