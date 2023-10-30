import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class AES_interface_D extends Frame implements ActionListener {
    //页面设计
    Label lable1, lable2;
    TextField t1, t2;
    Button B_login;
    Connection con;
    Statement sql;
    ResultSet rs;
    Image myImage;

    AES_interface_D() {
        //初始化页面
        super("S_AES算法");//设置标题
        this.setFont(new Font("黑体", Font.BOLD, 20));
        this.setBackground(Color.white);

        lable1 = new Label("主秘钥（16bit）");
        lable2 = new Label("密文(16bit)");
        t1 = new TextField(20);
        t2 = new TextField(20);
        B_login = new Button("确定");
        B_login.setBackground(Color.WHITE);
        myImage = new ImageIcon("./图片2.jpg").getImage();

        //设置布局
        setLayout(new FlowLayout());
        add(lable1);
        add(t1);
        add(lable2);
        add(t2);
        add(B_login);
        t1.addActionListener(this);
        t2.addActionListener(this);
        B_login.addActionListener(this);


        //界面布局设置
        setSize(400, 300);
        //设置窗口居中显示
        Toolkit tool;
        int w, h;
        tool = Toolkit.getDefaultToolkit();
        w = (tool.getScreenSize().width - this.getWidth()) / 2;
        h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getWidth()) / 2;
        setLocation(w, h);
        setVisible(true);

        //单击窗口右上角的“X”按钮，即关闭窗口退出
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }
    public void actionPerformed (ActionEvent e){

        if (e.getSource() == B_login)
        {   String main_Key=t1.getText();
            String ciphertext=t2.getText();
            AES_interface.get_Key(main_Key);
            new AES_interface_S(AES_interface.decrypt(ciphertext),"明文");
            //int yes=0;
        }

        else
            JOptionPane.showMessageDialog(null, "WRONG!!!");


        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    public void paint(Graphics g)//显示图片
    {
        g.drawImage(myImage, 0, 0, this);
    }
}
