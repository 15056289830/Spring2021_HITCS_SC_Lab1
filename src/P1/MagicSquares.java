package P1;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MagicSquares {
    public static boolean isLegalMagicSquares(String name,double[][] matriex,int n)
    {
        double[] num=new double[2*n+2];
        for(int i = 0;i<n;i++)
        {
            for(int j = 0;j<n;j++)
            {
                if(matriex[i][j]<=0)
                {
                    System.out.println(name+"存在非正整数数。");
                    return false;
                }
                num[i]+=matriex[i][j];
                num[i+n]+=matriex[j][i];
            }
        }
        for(int i=0;i<n;i++)
        {
            num[2*n] += matriex[i][i];
            num[2*n+1] += matriex[i][n-1-i];
        }
        for(int i=0;i<2*n+1;i++)
        {
            for(int j=i+1;j<2*n+2;j++)
            {
                if(num[i]!=num[j])
                {
                    return false;
                }
            }
        }
        return true;
    }
    public static int index(String name)
    {
        File  file=new File(name);
        BufferedReader reader=null;
        String temp=null;
        int line=0;
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                line++;
            }
        }
        catch(Exception e){
            System.out.println("文件读取失败。");
        }
        return line;
    }
    public static boolean alter(String name,double[][] matriex)
    {
        boolean t = true;
        int n = index(name);
        File file=new File(name);
        BufferedReader reader=null;
        String temp=null;
        int line=0;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null)
            {
                String[] buf = temp.split("\\s+");
                String[] buf1 = temp.split("\t");
                if(buf1.length!=buf.length)
                {
                    System.out.println(name+"未用制表符分隔。");
                    t=false;
                }
                for (int i = 0; i < n; i++)
                {
                    matriex[line][i] = Double.parseDouble(buf[i]);
                }
                line++;
            }

        } catch(Exception e){
            t=false;
            System.out.println(name + "列数行数不相等，不是一个矩阵。");
        }
        return t;
    }
    public static boolean generateMagicSquare(int n) {
        if(n<=0)
        {
            System.out.println("输入的n是非正数");
            return false;
        }
        int magic[][] = new int[n][n];
        double Magic[][] = new double[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0){
                row++;
                if(row>=n&&i<square)
                {
                  System.out.println("输入的n是一个偶数。");
                    return false;
                }
            }
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        File file = new File("src\\txt\\6.txt");
        try {
            FileWriter out = new FileWriter(file);
            for(i=0;i<n;i++){
                for(j=0;j<n;j++){
                    out.write(magic[i][j]+"\t");
                    Magic[i][j] = (double) magic[i][j];
                }
                out.write("\r\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("src\\txt\\6.txt:"+isLegalMagicSquares("src\\txt\\6.txt",Magic,n));
        return true;
    }
    public static void main(String[] args){
        for(int i=1;i<=5;i++)
        {
            String name=new String("src\\txt\\"+i+".txt");
            int n = index(name);
            double[][] matriex=new double[n][n];
            boolean a=alter(name,matriex);
            if(a)
            {
                System.out.println(name+":"+isLegalMagicSquares(name,matriex,n));
            }
            else
            {
                System.out.println(name+":"+a);
            }
        }
        Scanner sn = new Scanner(System.in);
        System.out.print("请输入一个整数：");
        int intVal;
        try{
            intVal = sn.nextInt();
            System.out.println("你输入了：" + intVal);
            boolean bl = generateMagicSquare(intVal);
            System.out.println("是否存在"+intVal+"*"+intVal+"的幻方："+bl);
        }
        catch(InputMismatchException e){
            System.out.println("必须输入整数！");
        }
    }
}