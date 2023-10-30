import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class ASll_AES_interface_1 extends Frame implements ActionListener {
    Label lable1;
    Button B_encrypt,B_decrypt;


    ASll_AES_interface_1(){
        super("S_AES");
        Font font=new Font("宋体", Font.BOLD, 20);
        Font font1=new Font("宋体", Font.BOLD, 30);
        this.setFont(font);
        this.setBackground(Color.yellow);

        lable1=new Label("               S-AES算法        ");
        lable1.setFont(font1);
        B_encrypt=new Button("加密");
        B_decrypt=new Button("解密");
        B_decrypt.setBackground(Color.yellow);
        B_encrypt.setBackground(Color.yellow);

        setLayout(new GridLayout(3,1));
        add(lable1);
        add(B_encrypt);
        add(B_decrypt);

        B_encrypt.addActionListener(this);
        B_decrypt.addActionListener(this);

        setSize(400, 300);
        Toolkit tool;
        int w, h;
        tool = Toolkit.getDefaultToolkit();
        w = (tool.getScreenSize().width - this.getWidth()) / 2;
        h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getWidth()) / 2;
        setLocation(w, h);
        setVisible(true);
        //关闭
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });

    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==B_encrypt){
            new ASll_AES_interface_E();
        }
        if(e.getSource()==B_decrypt){
            new ASll_AES_interface_D();
        }


    }
}
