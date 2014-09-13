package Algorithm;
/*@auther yixiu
 * 2014/9/6
 * 主题词综合评价算法*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Mega {

	private String[] s1 = {"s1_1","s1_2","s1_3","s1_4","s1_5","s1_6","s1_7","s1_8","s1_9","s1_10",};
	private String[] s2 = {"s2_1","s2_2","s2_3","s2_4","s2_5","s2_6","s2_7","s2_8","s2_9","s2_10",};
	//private String[] s3 = {"s3_1","s3_2","s3_3","s3_4","s3_5","s3_6","s3_7","s3_8","s3_9","s3_10",};
	private int[][] index = new int[2][10];//用来标记分数
	private double[] poit = new double[10];//用以记录要输出的词汇的分数
	private String[] x = new String[10];//用来输出十个词汇
	private ArrayList xLit = new ArrayList<String>();//内部存储这要输出的十个词汇，便于运算
	private ArrayList recordAssociateWord = new ArrayList<String>();//在其他主题词中匹配到的词汇
	private ArrayList recordAssociatePoit = new ArrayList();//匹配到的词汇的分数
	int recordAssociateNum = 0;//总共匹配到的个数
	
	//对外接口，输出十个词
	public String[] init(String[] s1,String[]s2) throws Exception{
		this.s1 = s1;
		this.s2 = s2;
		int num = s1.length+s2.length;
		
		xLit.clear();
		recordAssociateWord.clear();
		recordAssociatePoit.clear();
		recordAssociateNum = 0;
		
		//总数必须大于10
		if(num>=10){
			alocate();
			sort();
			if(findAssociate()){
				reSort();
			}
			return x;
		}else{
			x = null;
			return x;
		}
		
	}
	
	//对外输出对应分数,必须放在init函数之后，不能单独使用！
	public double[] outPoint(){
		return poit;
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
				//System.out.print(index[i][j]);
				//System.out.print(" ");
			}
		}
	}
	
	//从处理好的词汇中选择前十个主题词
	private String[] sort(){
		int i1 = 0, i2 = 0;
		
		xLit.clear();
		for(int i = 0;i<10;){
			if(i1<s1.length&&i2<s2.length){
			if(index[0][i1]>index[1][i2]){
				
				if(!xLit.contains(s1[i1])){
					xLit.add(s1[i1]);
					poit[i] = index[0][i1];//将分数也记录下来
					i1++;
					i++;
				}
				
			}else{
				if(!xLit.contains(s2[i2])){
					xLit.add(s2[i2]);
					poit[i] = index[1][i2];
					i2++;
					i++;
				}
				}
			}else if(i1>=s1.length&&!xLit.contains(s2[i2])){//s1已完
				xLit.add(s2[i2]);
				poit[i] = index[1][i2];
				i2++;
				i++;
			}else if(i2>=s2.length&&!xLit.contains(s1[i1])){//s2已完
				xLit.add(s1[i1]);
				poit[i] = index[0][i1];
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
      
		return x;
	}
	
	private boolean findAssociate() throws Exception{
		  FileInputStream fs=new FileInputStream("其他主题词.xlsx");
			
	      XSSFWorkbook wb=new XSSFWorkbook(fs);    
	      XSSFSheet sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表  
	      XSSFRow row=sheet.getRow(0);
	      int rowNum = sheet.getLastRowNum();
	      int cellNum = row.getLastCellNum();
		 
		  
		  for(int i=0;i<=rowNum;i++){
			  row = sheet.getRow(i);
			  cellNum  = row.getLastCellNum();
			  int count = 0;//用来计数一行有多少匹配
			  for(int j=2;j<cellNum;j++){
				
				 if(xLit.contains(row.getCell(j).toString())){
					 count++;
				 }else
					 break;//有一个单元格不匹配，直接退出，避免浪费计算资源
				 
				 if(count == (cellNum-2)){//如果这一条匹配，存入数组中
					 recordAssociateWord.add(row.getCell(1).toString());
					 recordAssociatePoit.add(row.getCell(0).toString());
					
					 recordAssociateNum++;
				 }
			  }
			 
		  }//end for
		  if(recordAssociateNum>0)
			  return true;
		  else
			  return false;
		  
	}
	
	private void reSort(){
		double[] recordeAssociatePoitArray = new double[recordAssociateNum];
		for(int i = 0;i<recordAssociateNum;i++){//将关联词的分数转化为数组
			recordeAssociatePoitArray[i] = Double.parseDouble(recordAssociatePoit.get(i).toString());
			//System.out.print(recordeAssociatePoitArray[i]+" ");
		}
		ArrayList tmp = new ArrayList<String>(10);
		double[] tmpPoint = new double[10];
		int i1 = 0, i2 = 0;
		//将原数组与关联数组进行排序并选择前十个
		for(int i = 0;i<10;){
			if(i1<recordAssociateNum&&i2<10){
			if(recordeAssociatePoitArray[i1]>poit[i2]){
				
				if(!tmp.contains(recordAssociateWord.get(i1))){
					tmp.add(recordAssociateWord.get(i1));
					tmpPoint[i] = recordeAssociatePoitArray[i1];//将分数也记录下来
					i1++;
					i++;
				}
				
			}else{
				if(!tmp.contains(xLit.get(i2))){
					tmp.add(xLit.get(i2));
					tmpPoint[i] = poit[i2];//将分数也记录下来
					i2++;
					i++;
				}
				}
			}else if(i1>=recordAssociateNum&&!!tmp.contains(xLit.get(i2))){//s1已完
				tmp.add(xLit.get(i2));
				tmpPoint[i] = poit[i2];//将分数也记录下来
				i2++;
				i++;
			}
	
		}//end for
		
		x=(String[])tmp.toArray(new String[1]);
		poit = tmpPoint;
		
	
	}

}
