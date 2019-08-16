

# AndroidDevicesInfo


#### Java GUI工具 - 获取Android设备最顶层应用程序相关信息，并提供备份功能；



简介：

	该程序通过Java执行ADB CMD命名获取回调信息判断设备信息，获取信息时通过多线程操作节约读取时间；
	
	1、获取Android屏幕顶层焦点应用名称，可用于辨别Activity和悬浮窗页面
	
	2、获取Android屏幕顶层应用，不能识别悬浮窗
	
	3、获取应用程序位数 PackBit

	4、获取屏幕顶层应用程序常用信息：PackName、Launchable_Activity、TopActivity、versionName、versionCode等
	
	5、提供常用APP功能操作：备份、清除缓存、关闭、截图、卸载功能


#### 项目详细描述请转移至：https://testerhome.com/opensource_projects/android-app-testtools



--------------------------------------------------



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



## 清除缓存并重新启动
adb shell pm clean <packname>

adb shell am start -n  <packname/packname.Activity>



## 关闭程序
adb shell am force-stop <packname>

