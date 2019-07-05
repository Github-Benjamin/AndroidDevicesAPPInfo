/**
 * Created by Benjamin on 2018/12/4.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainUITest extends JFrame implements ActionListener  {

    // 定义组件
    JButton EnterBtn,GetFile,StartBtn,CloseBtn,CleanAndStartBtn,ScreenshotBtn; // 定义确认按钮
    JMenuItem MenuUninstall,MenuScreenshot,MenuGetLog,MenuAbout;
    JLabel PackBit,versionCode, versionName, minSdk, targetSdk;
    JTextField PackName,PackPath,Launchable_Activity,TopActivity;
    public static void main(String[] args) {
        MainUITest mUI=new MainUITest();
    }

    public MainUITest() {

        // 创建组件并设置监听
        EnterBtn = new JButton("获取APK信息");
        GetFile = new JButton("备份APK");
//        StartBtn = new JButton("启动");
        CloseBtn = new JButton("关闭当前程序");
        CleanAndStartBtn = new JButton("清除数据并启动");
//        ScreenshotBtn = new JButton("截图");

        EnterBtn.addActionListener(this);
        GetFile.addActionListener(this);
//        StartBtn.addActionListener(this);
        CloseBtn.addActionListener(this);
        CleanAndStartBtn.addActionListener(this);
//        ScreenshotBtn.addActionListener(this);

        //初始化一个菜单栏
        JMenuBar menuAbout = new JMenuBar();
        MenuAbout = new JMenuItem("About");
        JMenuBar menuScreenshot = new JMenuBar();
        MenuScreenshot = new JMenuItem("截图");
//        JMenuBar menuGetLog = new JMenuBar();
//        MenuGetLog = new JMenuItem("GetLog");
        JMenuBar menuUninstall = new JMenuBar();
        MenuUninstall = new JMenuItem("卸载");
        menuAbout.add(MenuScreenshot);
//        menuAbout.add(MenuGetLog);
        menuAbout.add(MenuUninstall);
        menuAbout.add(MenuAbout);
        myEvent();  // 加载菜单栏监听事件处理

        // 信息栏选项
        getContentPane().add(new JLabel("PackName：", SwingConstants.CENTER ));
        PackName = new JTextField("PackName",10);
        getContentPane().add(PackName);

        getContentPane().add(new JLabel("PackBit：", SwingConstants.CENTER ));
        PackBit = new JLabel("PackBit");
        getContentPane().add(PackBit);

        getContentPane().add(new JLabel("Launchable_Activity：", SwingConstants.CENTER ));
        Launchable_Activity = new JTextField("Launchable_Activity",10);
        getContentPane().add(Launchable_Activity);

        getContentPane().add(new JLabel("TopActivity：", SwingConstants.CENTER ));
        TopActivity = new JTextField("TopActivity",10);
        getContentPane().add(TopActivity);

        getContentPane().add(new JLabel("PackPath：", SwingConstants.CENTER ));
        PackPath = new JTextField("PackPath",10);
        getContentPane().add(PackPath);

        // 新增 app versionCode, versionName, minSdk, targetSdk
        getContentPane().add(new JLabel("versionName：", SwingConstants.CENTER ));
        versionName = new JLabel("versionName");
        getContentPane().add(versionName);

        getContentPane().add(new JLabel("versionCode：", SwingConstants.CENTER ));
        versionCode = new JLabel("versionCode");
        getContentPane().add(versionCode);

        getContentPane().add(new JLabel("minSdk：", SwingConstants.CENTER ));
        minSdk = new JLabel("minSdk");
        getContentPane().add(minSdk);

        getContentPane().add(new JLabel("targetSdk：", SwingConstants.CENTER ));
        targetSdk = new JLabel("targetSdk");
        getContentPane().add(targetSdk);

        // 添加按钮
        getContentPane().add(EnterBtn);
        getContentPane().add(GetFile);
        getContentPane().add(CloseBtn);
        getContentPane().add(CleanAndStartBtn);

        this.setJMenuBar(menuScreenshot);	//设置菜单栏 截图
//        this.setJMenuBar(menuGetLog);
        this.setJMenuBar(menuUninstall);
        this.setJMenuBar(menuAbout);	//设置菜单栏 关于

        this.setLayout(new GridLayout(0,2));    //选择GridLayout布局管理器
        this.setTitle("APP-TestTools");
        this.setSize(400,320);
        this.setLocation(0, 0);
        this.setLocationRelativeTo(null);//窗体居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置当关闭窗口时，保证JVM也退出
        this.setVisible(true);
        this.setResizable(true);

    }

    // 菜单栏监听
    private void myEvent()
    {

        // 截图
        MenuScreenshot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScreenshotUtil.ScreencapShot();
                // 截图判断是否成功
                if( TopAPPInfo.getScreencapStatus().equals("Success") == false ){
                    JOptionPane.showMessageDialog(null, "Screenshot Error!\nPlease Check You Phone USB Connect." , "Faild",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null, "Please Check Jar Current Path File: \nFileName:    screenshot" + TopAPPInfo.getScreencapTime()+".png" , "Success",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        });

//        // 获取日志
//        MenuGetLog.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    GetLogUtil.GetLog();
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });


        // 卸载
        MenuUninstall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // 获取设备信息；fix uninstall APP bug
                try {
                    Main.GetDevicesStatus();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                if( DevicesInfo.getAdbDevices().equals("Success") == false ) {
                    JOptionPane.showMessageDialog(null,DevicesInfo.getAdbDevices(),"提示消息",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(TopAPPInfo.getPackPath() == null | TopAPPInfo.getPackPath() == ""){
                    JOptionPane.showMessageDialog(null,"请先获取APK信息！","提示消息",JOptionPane.WARNING_MESSAGE);
                    return;
                }else {
                    // 执行卸载APP
                    DevicesTopAPP.UninstallAPP(TopAPPInfo.getPackName());
                    if( TopAPPInfo.getUninstallStatus().equals("Success") == false ){
                        JOptionPane.showMessageDialog(null, TopAPPInfo.getUninstallStatus() , "Faild",JOptionPane.WARNING_MESSAGE);
                        return;
                    }else {
                        JOptionPane.showMessageDialog(null, "Uninstall Sucess PackName: " + TopAPPInfo.getPackName() , "Sucess",JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }
        });


        // About
        MenuAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Author: Benjamin\nWeChat: WeChat_Benjamin\nEmail: Benjamin_v@qq.com", "AboutInfo",JOptionPane.INFORMATION_MESSAGE);
            return;
            }
        });


    }

    // 界面按钮监听
    @Override
    public void actionPerformed(ActionEvent e) {

        // 获取设备信息
        try {
            Main.GetDevicesStatus();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        if( DevicesInfo.getAdbDevices().equals("Success") == false ) {
            JOptionPane.showMessageDialog(null,DevicesInfo.getAdbDevices(),"提示消息",JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(e.getActionCommand()=="获取APK信息") {

            try {
                DevicesTopAPP.DoGetAPP();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            PackName.setText(TopAPPInfo.getPackName());
            PackBit.setText(TopAPPInfo.getPackBit());
            Launchable_Activity.setText(TopAPPInfo.getPackMainActivity());
            TopActivity.setText(TopAPPInfo.getPackTopActivity());
            PackPath.setText(TopAPPInfo.getPackPath());

            versionCode.setText(TopAPPInfo.getVersionCode());
            versionName.setText(TopAPPInfo.getVersionName());
            minSdk.setText(TopAPPInfo.getMinSdk());
            targetSdk.setText(TopAPPInfo.getTargetSdk());

            return;

        }else if(e.getActionCommand()=="备份APK"){

            if(TopAPPInfo.getPackPath() == null | TopAPPInfo.getPackPath() == ""){
                JOptionPane.showMessageDialog(null,"请先获取APK信息！","提示消息",JOptionPane.WARNING_MESSAGE);
            }else {
                //执行pull备份到本地
                DevicesTopAPP.GetPullFile(TopAPPInfo.getPackPath(),TopAPPInfo.getPackName());

                if( TopAPPInfo.getPullStatus().equals("Success") == false ){
                    JOptionPane.showMessageDialog(null,TopAPPInfo.getPullStatus(),"Error!",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null,"备份成功，请查看本地目录！","提示消息",JOptionPane.WARNING_MESSAGE);
                return;
            }

        }else if(e.getActionCommand()=="清除数据并启动"){

            if(TopAPPInfo.getPackPath() == null | TopAPPInfo.getPackPath() == ""){
                JOptionPane.showMessageDialog(null,"请先获取APK信息！","提示消息",JOptionPane.WARNING_MESSAGE);
            }else {
                //执行cmd命令, 清除、启动
                DevicesTopAPP.CleanAPP(TopAPPInfo.getPackName());
                if( TopAPPInfo.getClearStatus().equals("Success") == false ) {
                    JOptionPane.showMessageDialog(null,TopAPPInfo.getClearStatus(),"Error!",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                DevicesTopAPP.StartAPPLaunchableActivity(TopAPPInfo.getPackMainActivity());
                return;
            }

        }else if(e.getActionCommand()=="关闭当前程序"){

            if(TopAPPInfo.getPackPath() == null | TopAPPInfo.getPackPath() == ""){
                JOptionPane.showMessageDialog(null,"请先获取APK信息！","提示消息",JOptionPane.WARNING_MESSAGE);
            }else {
                //执行cmd命令,关闭当前程序
                DevicesTopAPP.CloseAPP(TopAPPInfo.getPackName());
                return;
            }

        }



    }

}
