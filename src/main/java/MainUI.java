/**
 * Created by Benjamin on 2018/12/2.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame implements ActionListener  {
    // 定义组件
    JButton EnterBtn,EmptyBtn; // 定义确认按钮
    JLabel Brand,Model,OSVersion,ScreenSize,MemTotal,TotalMem,CpuName,CpuBit;

    public static void main(String[] args) {
        MainUI mUI=new MainUI();
    }

    public MainUI()
    {
        // 创建组件并设置监听
        EnterBtn = new JButton("获取设备信息");
        EmptyBtn = new JButton("清空");
        EnterBtn.addActionListener(this);
        EmptyBtn.addActionListener(this);

        getContentPane().add(new JLabel("Brand：", SwingConstants.CENTER ));
        Brand = new JLabel("Brand");
        getContentPane().add(Brand);

        getContentPane().add(new JLabel("Model：", SwingConstants.CENTER ));
        Model = new JLabel("Model");
        getContentPane().add(Model);

        getContentPane().add(new JLabel("安卓版本：", SwingConstants.CENTER ));
        OSVersion = new JLabel("OSVersion");
        getContentPane().add(OSVersion);

        getContentPane().add(new JLabel("分辨率：", SwingConstants.CENTER ));
        ScreenSize = new JLabel("ScreenSize");
        getContentPane().add(ScreenSize);

        getContentPane().add(new JLabel("内存：", SwingConstants.CENTER ));
        MemTotal = new JLabel("MemTotal");
        getContentPane().add(MemTotal);

        getContentPane().add(new JLabel("存储：", SwingConstants.CENTER ));
        TotalMem = new JLabel("TotalMem");
        getContentPane().add(TotalMem);

        getContentPane().add(new JLabel("CPU：", SwingConstants.CENTER ));
        CpuName = new JLabel("CpuName", SwingConstants.LEFT );
        getContentPane().add(CpuName);

        getContentPane().add(new JLabel("CPU位数：", SwingConstants.CENTER ));
        CpuBit = new JLabel("CpuBit", SwingConstants.LEFT );
        getContentPane().add(CpuBit);

        getContentPane().add(EnterBtn);
        getContentPane().add(EmptyBtn);

        this.setLayout(new GridLayout(0,2));    //选择GridLayout布局管理器
        this.setTitle("AndroidDevicesInfo");
        this.setSize(500,400);
        this.setLocation(0, 0);
        this.setLocationRelativeTo(null);//窗体居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置当关闭窗口时，保证JVM也退出
        this.setVisible(true);
        this.setResizable(true);
    }


    // 事件判断
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand()=="获取设备信息")
        {

            try {
                Main.GetDevicesStatus();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (DevicesInfo.getAdbDevices() == "Success"){

                try {
                    Main.DoGetDevices();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                Brand.setText(DevicesInfo.getBrand());
                Model.setText(DevicesInfo.getModel());
                OSVersion.setText( DevicesInfo.getOSVersion() + " ( " + DevicesInfo.getSDKVersion() + " ) ");
                ScreenSize.setText(DevicesInfo.getScreenSize());
                MemTotal.setText( DevicesInfo.getMemAvailable() + "/" + DevicesInfo.getMemTotal() );
                TotalMem.setText( DevicesInfo.getSurplusMem() + "/" +DevicesInfo.getTotalMem() );
                CpuName.setText(DevicesInfo.getCpuName());
                CpuBit.setText(DevicesInfo.getCpuBit());
                return;
            }else {
                JOptionPane.showMessageDialog(null,DevicesInfo.getAdbDevices(),"提示消息",JOptionPane.WARNING_MESSAGE);
                return;
            }


        }else if(e.getActionCommand() == "清空" ){

            Brand.setText("NaN");
            Model.setText("NaN");
            OSVersion.setText("NaN");
            ScreenSize.setText("NaN");
            MemTotal.setText("NaN");
            TotalMem.setText("NaN");
            CpuName.setText("NaN");
            CpuBit.setText("Nan");
            return;
        }

    }

}
