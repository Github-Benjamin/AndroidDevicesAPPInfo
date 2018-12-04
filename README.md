

# AndroidDevicesInfo

## Java GUI工具 - 获取Android设备系统信息程序；
## Java GUI工具 - 获取Android设备最顶层应用程序相关信息，并提供备份功能；



简介：该程序通知Java执行ADB CMD命名获取回调信息判断设备信息，获取信息时通过多线程操作节约读取时间；



备注：代码未做兼容性测试，部分功能模块在不同CPU设备上有读取设备兼容性问题，同时不同系统版本可能有问题，请自测。



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




## 查看当前APP第一界面程序报名和Activity,有坑32位CPU设备Java调用时不支持head命令
$ adb shell dumpsys activity activities  |grep -i hist |head -1

      * Hist #0: ActivityRecord{c08fc2a u0 com.test.benjamin/.ui.activity.MainTabActivity t2179}

	  
## 通过包名检查应用程序的启动Activity
$ adb shell dumpsys package com.test.benjamin |grep -B5 android.intent.category.LAUNCHER  |grep filter

        9cd481a com.test.benjamin/.SplashActivity filter f3f0a77
		

		
## 查看系统进程pid，判断运行程序位数，有坑部分机型 adb shell ps 可能显示显示不出进程信息
$ adb shell ps -A |grep com.test.benjamin

u0_a862       8771   632 2065724 158884 0                   0 S com.test.benjamin

u0_a862       8873   632 1888260  86208 0                   0 S com.test.benjamin:pushcore

u0_a862       9236   632 1852636  85000 0                   0 S com.test.benjamin:monitorService

u0_a862       9433   632 1769844  73896 0                   0 S com.test.benjamin:channel


## 查看系统32位 64位进程pid用户对比判断被检查app运行位数
$ adb shell ps -A |grep zygote

root           631     1 2183544  28908 0                   0 S zygote64

root           632     1 1621752  23992 0                   0 S zygote

webview_zygote 2217    1 1429812  13896 0                   0 S webview_zygote32



## 备份apk
adb shell pm path <packname>

adb pull <path>




