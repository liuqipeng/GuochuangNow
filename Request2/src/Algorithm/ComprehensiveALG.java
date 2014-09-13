/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithm;

import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author miss lulu 
 */
public class ComprehensiveALG {
    
    private String[] s1 = {"s1_1","s1_2","s1_3","s1_4","s1_5","s1_6","s1_7","s1_8","s1_9","s1_10",};
	private String[] s2 = {"s2_1","s2_2","s2_3","s2_4","s2_5","s2_6","s2_7","s2_8","s2_9","s2_10",};
	//private String[] s3 = {"s3_1","s3_2","s3_3","s3_4","s3_5","s3_6","s3_7","s3_8","s3_9","s3_10",};
	private int[][] index = new int[2][10];
	private String[] x = new String[10];
   
	//对外接口，输出十个词
	public String[] init(String[] s1,String []s2) throws Exception{
		this.s1 = s1;
		this.s2 = s2;
		int num = s1.length+s2.length;
		//总数必须大于10
		if(num>=10){
			alocate();
			sort();
			return x;
		}else{
			return x = null;
		}
		
	}
	
	//对对应数组赋予权重	
	private void alocate() throws Exception{
		File file = new File("weight.txt");
		Scanner input = new Scanner(file);
		int i1,i2;
		i1 = input.nextInt();
		i2 = input.nextInt();
		for(int i = 0;i<2;i++){
			for(int j = 0;j<10;j++){
				switch(i){
				case 0:index[0][j] = (10-j)*i1;break;
				case 1:index[1][j] = (10-j)*i2;break;
				//case 2:index[2][j] = (10-j)*i3;break;
				}

			}
		}
	}
	
	//从处理好的词汇中选择前十个主题词
	private void sort(){
		int i1 = 0, i2 = 0;
		
		ArrayList xLit = new ArrayList<String>();
		for(int i = 0;i<10;){
			if(i1<s1.length&&i2<s2.length){
			if(index[0][i1]>index[1][i2]){
				
				if(!xLit.contains(s1[i1])){
					xLit.add(s1[i1]);
					i1++;
					i++;
				}
				
			}else{
				if(!xLit.contains(s2[i2])){
					xLit.add(s2[i2]);
					i2++;
					i++;
				}
				}
			}else if(i1>=s1.length&&!xLit.contains(s2[i2])){
				xLit.add(s2[i2]);
				i2++;
				i++;
			}else if(i2>=s2.length&&!xLit.contains(s1[i1])){
				xLit.add(s1[i1]);
				i1++;
				i++;
			}
		/*	if(index[0][i1]>index[1][i2]){
				if(index[0][i1]>index[2][i3]){
					System.out.print(s1[i1]+" ");
					x[i] = s1[i1];
					i1++;
				}else{
					System.out.print(s3[i3]+" ");
					x[i] = s3[i3];
					i3++;
				}
			}else{
				if(index[1][i2]>index[2][i3]){
					System.out.print(s2[i2]+" ");
					x[i] = s2[i2];
					i2++;
				}else{
					System.out.print(s3[i3]+" ");
					x[i] = s3[i3];
					i3++;
				}
			}*/
		}
		x=(String[])xLit.toArray(new String[1]);
      
		
	}

 
    public void feedBack(String [] keyword,String [] method1,String [] method2) throws FileNotFoundException, IOException
    {
       
       
       System.out.println("");
       /*
       for(int i=0;i<method1.length;i++)
       {
           
           System.out.print(method1[i]+" ");
           System.out.print(method2[i]+" ");
           System.out.print(keyword[i]+" ");
       }
        */    
       File file = new File("weight.txt");
       Scanner input = new Scanner(file);
       int methodScore1=input.nextInt();
        int methodScore2=input.nextInt();
	int numMatch1=0;
	int numMatch2=0;
        //跟Method1匹配中的次数，下同
        
        //keyword直接与Method1、2字符串匹配
        for(int i=0;i<keyword.length;i++)
        {
            for(int j=0;j<method1.length;j++)
            {
                if(trim(keyword[i]).equals(trim(method1[j])))
                {
                    numMatch1++;
                }
            }
        }
     
         for(int i=0;i<keyword.length;i++)
        {
            for(int j=0;j<method2.length;j++)
            {
                if(trim(keyword[i]).equals(trim(method2[j])))
                {
                    numMatch2++;
                }
            }
        }
        int Cha =abs(numMatch1-numMatch2);
        if(numMatch1>numMatch2)
        {
            methodScore1=methodScore1+Cha;
            methodScore2=methodScore2-Cha;
        }
        else
        {
             methodScore1=methodScore1-Cha;
             methodScore2=methodScore2+Cha;
        }
        FileWriter filewriter = new FileWriter("weight.txt");
        filewriter.write(String.valueOf(methodScore1));
        filewriter.write(" ");
        filewriter.write(String.valueOf(methodScore2));
        filewriter.flush();
        filewriter.close();
        System.out.print(methodScore1+" "+methodScore2);
    } 
}
