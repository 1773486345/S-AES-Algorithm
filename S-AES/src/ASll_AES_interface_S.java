import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ASll_AES_interface_S extends Frame implements ActionListener {
    Label lable1;
    TextField t1;
    Button B_back;
    Image myImage;


    ASll_AES_interface_S(String text,String style){
        super("S_DES");
        Font font=new Font("宋体", Font.BOLD, 30);
        this.setFont(font);
        this.setBackground(Color.yellow);

        lable1=new Label(style+"为：");
        t1 = new TextField(15);
        B_back=new Button("返回");
        myImage = new ImageIcon("./图片2.jpg").getImage();

        setLayout(new FlowLayout());
        add(lable1);
        add(t1);
        add(B_back);

        t1.setText(text);
        B_back.addActionListener(this);

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
        if(e.getSource()==B_back){
            new ASll_AES_interface_1();
        }


    }
    public void paint(Graphics g)//显示图片
    {
        g.drawImage(myImage, 0, 0, this);
    }
}
