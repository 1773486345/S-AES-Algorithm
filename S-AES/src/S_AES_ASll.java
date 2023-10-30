import java.util.Scanner;
public class S_AES_ASll {
    public static String W0;
    public static String W1;
    public static String W2;
    public static String W3;
    public static String W4;
    public static String W5;
    public static String[][] S1_box = new String[][] {
            { "1001", "0100", "1010", "1011" }, { "1101", "0001", "1000", "0101" },
            { "0110", "0010", "0000", "0011" }, { "1100", "1110", "1111", "0111" } };//S盒
    public static String[][] S2_box = new String[][] {
            { "1010", "0101", "1001", "1011" }, { "0001", "0111", "1000", "1111" },
            { "0110", "0000", "0010", "0011" }, { "1100", "0100", "1101", "1110" } };//逆S盒

    /*输入主密匙，获取K1,K2*/
    public static void get_Key(){
        System.out.println("请输入主密匙(16bit):");
        Scanner sc =new Scanner(System.in);
        String main_Key=sc.nextLine();
        W0= main_Key.substring(0,8);
        W1=main_Key.substring(8,16);
        W2=xor(W0,g(W1,1));
        W3=xor(W1,W2);
        W4=xor(W2,g(W3,2));
        W5=xor(W4,W3);
        System.out.println("w2："+W2);
        System.out.println("w3："+W3);
        System.out.println("w4："+W4);
        System.out.println("w5："+W5);
    }
    /*加密函数*/
    public static void encrypt(){
        System.out.println("请输入加密信息(16bit):");
        Scanner sc=new Scanner(System.in);
        String  Plainttext=sc.nextLine(); //明文
        String  Plainttext_1=Plainttext.substring(0,1);
        String  Plainttext_2=Plainttext.substring(1,2);
        Plainttext=AsciiToBinary(Plainttext_1)+AsciiToBinary(Plainttext_2);
        System.out.println("转化为二进制数后为："+Plainttext);
        //第一轮
        Plainttext=xor(Plainttext,W0+W1);//轮秘钥加
        Plainttext=replace(Plainttext,1);//半字节替代
        Plainttext=move_hang(Plainttext);//行移位
        Plainttext=multiply_lie(Plainttext,1);//列混淆
        //第二轮
        Plainttext=xor(Plainttext,W2+W3);//轮秘钥加
        Plainttext=replace(Plainttext,1);//半字节替代
        Plainttext=move_hang(Plainttext);//行移位
        String ciphertext=xor(Plainttext,W4+W5);//轮秘钥加
        System.out.println("二进制的密文为: " + ciphertext);
        String ciphertext_1=ciphertext.substring(0,8);
        String ciphertext_2=ciphertext.substring(8,16);
        ciphertext=BinaryToAscii(ciphertext_1)+BinaryToAscii(ciphertext_2);

        System.out.println("密文为: " + ciphertext);
    }
    /*解密函数*/
    public static void decrypt(){
        System.out.println("请输入解密信息(16bit):");
        Scanner sc=new Scanner(System.in);
        String  ciphertext=sc.nextLine(); //秘文
        String ciphertext_1=ciphertext.substring(0,1);
        String ciphertext_2=ciphertext.substring(1,2);
        ciphertext=AsciiToBinary(ciphertext_1)+AsciiToBinary(ciphertext_2);
        //ciphertext=AsciiToBinary(ciphertext);
        System.out.println("转化为二进制数后为："+ciphertext);
        //第一轮
        ciphertext=xor(ciphertext,W4+W5);//轮秘钥加
        ciphertext=move_hang(ciphertext);//行移位
        ciphertext=replace(ciphertext,2);//半字节替代
        ciphertext=xor(ciphertext,W2+W3);//轮秘钥加
        ciphertext=multiply_lie(ciphertext,2);//列混淆
        //第二轮
        ciphertext=move_hang(ciphertext);//行移位
        ciphertext=replace(ciphertext,2);//半字节替代
        String Plainttext=xor(ciphertext,W0+W1);//轮秘钥加
        System.out.println("二进制的明文为: " + Plainttext);
        String  Plainttext_1=Plainttext.substring(0,8);
        String  Plainttext_2=Plainttext.substring(8,16);
        Plainttext=BinaryToAscii(Plainttext_1)+BinaryToAscii(Plainttext_2);

        System.out.println("明文为: " + Plainttext);
    }
    /*n代表的是第几轮*/
    public static String g(String key,int n){
        String N0=key.substring(0,4);
        String N1=key.substring(4,8);
        String N1_=searchSbox(N1,1);
        String N0_=searchSbox(N0,1);
        String N_=N1_+N0_;
        String W_="";
        if(n==1){
            W_=xor(N_,"10000000");
        }
        else if(n==2){
            W_=xor(N_,"00110000");
        }
        return W_;
    }

    public static String searchSbox(String str, int n) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.charAt(0));
        sb.append(str.charAt(1));
        String ret = new String(sb);
        StringBuilder sb1 = new StringBuilder();
        sb1.append(str.charAt(2));
        sb1.append(str.charAt(3));
        String ret1 = new String(sb1);
        String retu = new String();
        if (n == 1) {
            retu = S1_box[Integer.parseInt(ret, 2)][Integer.parseInt(ret1, 2)];
        } else {
            retu = S2_box[Integer.parseInt(ret, 2)][Integer.parseInt(ret1, 2)];
        }
        return retu;
    }
    /*异或*/
    public static String xor(String str, String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == key.charAt(i)) {
                sb.append("0");
            } else {
                sb.append("1");
            }
        }
        return new String(sb);
    }
    /*半字节替换
    n代表半字节替换或是逆替换*/
    public static String replace(String str,int n){
        String str0=str.substring(0,4);
        String str1=str.substring(4,8);
        String str2=str.substring(8,12);
        String str3=str.substring(12,16);
        str0=searchSbox(str0,n);
        str1=searchSbox(str1,n);
        str2=searchSbox(str2,n);
        str3=searchSbox(str3,n);
        return str0+str1+str2+str3;

    }
    /*行移位*/
    public static String move_hang(String str){
        String str0=str.substring(0,4);
        String str1=str.substring(4,8);
        String str2=str.substring(8,12);
        String str3=str.substring(12,16);
        String str_=str1;
        str1=str3;
        str3=str_;
        return str0+str1+str2+str3;
    }
    /*列混淆*/
    public static String multiply_lie(String str,int n){
        String s0=str.substring(0,4);
        String s1=str.substring(4,8);
        String s2=str.substring(8,12);
        String s3=str.substring(12,16);
        String s0_="0000";
        String s1_="0000";
        String s2_="0000";
        String s3_="0000";
        if(n==1){
            String s1_4=multiply(s1,4);
            s0_=xor(s0,multiply(s1,4));
            s1_=xor(s1,multiply(s0,4));
            s2_=xor(s2,multiply(s3,4));
            s3_=xor(s3,multiply(s2,4));
        }
        else if(n==2){
            s0_=xor(multiply(s0,9),multiply(s1,2));
            s1_=xor(multiply(s0,2),multiply(s1,9));
            s2_=xor(multiply(s2,9),multiply(s3,2));
            s3_=xor(multiply(s2,2),multiply(s3,9));
        }
        return s0_+s1_+s2_+s3_;

    }
    /*乘法*/
    public static String multiply(String str,int n){
        String result="";
        if(n==2){
            if(str.equals("0000"))  result="0000";
            else if(str.equals("0001")) result="0010";
            else if(str.equals("0010")) result="0100";
            else if(str.equals("0011")) result="0110";
            else if(str.equals("0100")) result="1000";
            else if(str.equals("0101")) result="1010";
            else if(str.equals("0110")) result="1100";
            else if(str.equals("0111")) result="1110";
            else if(str.equals("1000")) result="0011";
            else if(str.equals("1001")) result="0001";
            else if(str.equals("1010")) result="0111";
            else if(str.equals("1011")) result="0101";
            else if(str.equals("1100")) result="1011";
            else if(str.equals("1101")) result="1001";
            else if(str.equals("1110")) result="1111";
            else if(str.equals("1111")) result="1101";
        }

        if(n==4){
            if(str.equals("0000"))  result="0000";
            else if(str.equals("0001")) result="0100";
            else if(str.equals("0010")) result="1000";
            else if(str.equals("0011")) result="1100";
            else if(str.equals("0100"))result="0011";
            else if(str.equals("0101")) result="0111";
            else if(str.equals("0110")) result="1011";
            else if(str.equals("0111")) result="1111";
            else if(str.equals("1000")) result="0110";
            else if(str.equals("1001")) result="0010";
            else if(str.equals("1010")) result="1110";
            else if(str.equals("1011")) result="1010";
            else if(str.equals("1100")) result="0101";
            else if(str.equals("1101")) result="0001";
            else if(str.equals("1110")) result="1101";
            else if(str.equals("1111")) result="1001";
        }
        if(n==9){
            if(str.equals("0000"))  result="0000";
            else if(str.equals("0001")) result="1001";
            else if(str.equals("0010")) result="0001";
            else if(str.equals("0011")) result="1000";
            else if(str.equals("0100")) result="0010";
            else if(str.equals("0101")) result="1011";
            else if(str.equals("0110")) result="0011";
            else if(str.equals("0111")) result="1010";
            else if(str.equals("1000")) result="0100";
            else if(str.equals("1001")) result="1101";
            else if(str.equals("1010")) result="0101";
            else if(str.equals("1011")) result="1100";
            else if(str.equals("1100")) result="0110";
            else if(str.equals("1101")) result="1110";
            else if(str.equals("1110")) result="0111";
            else if(str.equals("1111")) result="1111";
        }
        return result;
    }
    /*二进制转化为ASCII码*/
    public static String BinaryToAscii(String str){
        int num=Integer.parseInt(str,2);
        char ch=(char)num;
        String s=Character.toString(ch);
        /*System.out.println("用asall编码表示为："+ch);*/
        /* return ch;*/
        return s;

    }
    /*ASCII码转化为二进制*/
    public static String AsciiToBinary(String str){
        byte[] num=str.getBytes();
        int s=num[0];
        String binary="";
        while (s>0){
            binary=(s%2)+binary;
            s=s/2;
        }
        if(binary.length()<8){
            for(int i=0;binary.length()<8;i++)
                binary="0"+binary;
        }
        return binary;

    }
    public static void main(String[] args){
        get_Key();
        encrypt();
        decrypt();
        //System.out.println("测试半字节替换"+replace("0100011011110110",1));
        //System.out.println("测试加密行移位"+move_hang("1101100001111000"));
        //System.out.println("测试列混淆"+multiply_lie("1101100001111000",1));

    }
}
