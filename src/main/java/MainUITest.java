/**
 * Created by Benjamin on 2018/12/4.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainUITest extends JFrame implements ActionListener  {

    // 定义组件
    JButton EnterBtn,GetFile; // 定义确认按钮
    JMenuItem MenuAbout;
    JLabel PackBit,versionCode, versionName, minSdk, targetSdk;
    JTextField PackName,PackPath,Launchable_Activity,TopActivity;
    public static void main(String[] args) {
        MainUITest mUI=new MainUITest();
    }

    public MainUITest() {

        // 创建组件并设置监听
        EnterBtn = new JButton("获取APK信息");
        GetFile = new JButton("备份APK");
        EnterBtn.addActionListener(this);
        GetFile.addActionListener(this);

        //初始化一个菜单栏
        JMenuBar menuBar = new JMenuBar();
        MenuAbout = new JMenuItem("About");
        menuBar.add(MenuAbout);
        myEvent();  // 加载菜单栏监听事件处理

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

        getContentPane().add(EnterBtn);
        getContentPane().add(GetFile);

        this.setJMenuBar(menuBar);	//设置菜单栏
        this.setLayout(new GridLayout(0,2));    //选择GridLayout布局管理器
        this.setTitle("AndroidAPP");
        this.setSize(350,300);
        this.setLocation(0, 0);
        this.setLocationRelativeTo(null);//窗体居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置当关闭窗口时，保证JVM也退出
        this.setVisible(true);
        this.setResizable(true);

    }

    // 菜单栏监听
    private void myEvent()
    {
        // About 菜单栏监听
        MenuAbout.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
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
                JOptionPane.showMessageDialog(null,"备份成功，请查看本地目录！","提示消息",JOptionPane.WARNING_MESSAGE);
                return;
            }

        }

    }

}
