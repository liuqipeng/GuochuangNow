package Algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NetofSubjt {
	
	public void inport(String[] s,double[] x)throws Exception{
		if(s.length == (x.length+1)){
			
			  int n = x.length;//关联主题词的总长度
			  double sn = 1;
			
			  for(int i = 0;i<n;i++)
				 sn = sn*x[i];//计算总分
			
			  double point = Math.sqrt(n)*StrictMath.pow(sn,1.0/n); //得出该词的分数
			
			  FileInputStream fs=new FileInputStream("其他主题词.xlsx");
			
		      XSSFWorkbook wb=new XSSFWorkbook(fs);    
		      XSSFSheet sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表  
		      XSSFRow row=sheet.getRow(0);
			  System.out.println(sheet.getLastRowNum()+"  "+row.getLastCellNum());
			  FileOutputStream out=new FileOutputStream("其他主题词.xlsx");
			
			  row=sheet.createRow((short)(sheet.getLastRowNum()+1));
			  row.createCell(0).setCellValue(point); //设置第一个（从0开始）单元格的数据  
		      row.createCell(1).setCellValue(s[0]); //设置第二个（从0开始）单元格的数据  
		      for(int i=0;i<n;i++)
		        row.createCell(2+i).setCellValue(s[1+i]);
		      
			  out.flush();  
		      wb.write(out);    
		      out.close(); 
		      fs.close();
                      System.out.println("插入数据完成");
		}else{
			System.out.println("the length of input data error");
		}
	}

}
