/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author lulu
 */
public class Method1 {
    public void pre() throws Exception{
		File file = new File("idf.txt");
		File fileout = new File("tfidf.txt");
		
		Scanner input = new Scanner(file);
		PrintWriter out = new PrintWriter(fileout);
		
		int i1;
		String s;
		double i2;
		int i3;
		double i4;
		
		while(input.hasNext()){
			i1 = input.nextInt();
			s = input.next();
			i2 = input.nextDouble();
			i3 = input.nextInt();
			i4 = input.nextDouble();
			
			out.println(s+" "+i2);
		}
		
		input.close();
		out.close();
	}

    public String [] MethodTest(String Path)
    {
        StringBuffer keyword1 = new StringBuffer();
        try {
             Hashtable hash=new Hashtable();
            
             FileInputStream file = new FileInputStream(new File(Path));
             XSSFWorkbook workbook = new XSSFWorkbook(file);
             XSSFSheet sheet1 = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet1.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                //Row rowNew =sheetNew.createRow(rowNumNew++);
                //For each row, iterate through all the columns
                Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) 
                {
                    org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                    // Cell cellNew =rowNew.createCell(cellNumNew++);
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) 
                    {
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
                            break;
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                           String result = cell.getStringCellValue();
            	            if(hash.containsKey(result)){
            		    int f = Integer.parseInt(hash.get(result).toString());
            		    f++;
            		     hash.put(result, f);
            	             }else{
            		     hash.put(result, 1);
            	              }
                              
}
                }
                
                //System.out.println("");
            }
            
            
            XSSFWorkbook workbookNew = new XSSFWorkbook();
            XSSFSheet sheetNew = workbookNew.createSheet("test");
            int rowNum = 0;
            
            Set s=hash.keySet();
            String key = new String();
            int t = 0;
            for(Iterator<String> i=s.iterator();i.hasNext();){
            	key = i.next();
                Row rowNew =sheetNew.createRow(rowNum);
                org.apache.poi.ss.usermodel.Cell cellNew =rowNew.createCell(0);
                cellNew.setCellValue(key);
                keyword1.append(key+" ");
                org.apache.poi.ss.usermodel.Cell cellNew2 =rowNew.createCell(1);
                cellNew2.setCellValue(hash.get(key).toString());
                rowNum++;
            	//sheet2.addCell(new Label(0,t , key));
                //System.out.println(hash.get(key));
                //sheet2.addCell(new Label(1,t , hash.get(key).toString()));
                t++;
            }
            
            FileOutputStream fileOut = new FileOutputStream(new File(Path.replace("名词.xlsx", "method1.xlsx")));//new file
            workbookNew.write(fileOut);
            fileOut.close();
            
            file.close();
             
             
           // Workbook book = Workbook.getWorkbook(new File("n.xls"));
           
           
            //WritableWorkbook book2 = Workbook.createWorkbook(new File("method1.xls"));
            //在原文件中新建第二个工作表
           // WritableSheet sheet2 = book2.createSheet("num1", 0);
            
            // 获得第一个工作表对象
            //Sheet sheet = book.getSheet(0);         
            //int rownum = sheet.getRows();// 得到行数
            /**
            Cell cell;
            for(int i = 0;i<rownum;i++){
            	cell = sheet.getCell(0,i);
            	String result = cell.getContents();
            	if(hash.containsKey(result)){
            		int f = Integer.parseInt(hash.get(result).toString());
            		f++;
            		hash.put(result, f);
            	}else{
            		hash.put(result, 1);
            	}
            }
            */
            //遍历哈希表
            /*
            Set s=hash.keySet();
            String key = new String();
            int t = 0;
            for(Iterator<String> i=s.iterator();i.hasNext();){
            	key = i.next();
            	sheet2.addCell(new Label(0,t , key));
                //System.out.println(hash.get(key));
                sheet2.addCell(new Label(1,t , hash.get(key).toString()));
                t++;
            }
            
            book2.write();
            book2.close();
            */
           // book.close();
            System.out.print("method1");
        } catch (Exception e) {
            System.out.println(e);
        }
        return keyword1.toString().split(" ");
    }
    	public String[] method1Out(String filepath) throws Exception{
		
		
		File fileout = new File("pp.txt");
		PrintWriter out1 = new PrintWriter(fileout);
		
    
		String[] out = null;
		ArrayList outList = new ArrayList<String>();
		String result;
		Hashtable hash=new Hashtable();
        FileInputStream file = new FileInputStream(new File(filepath));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet1 = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet1.iterator();
        //从名词excel文件中提取数据并计算词频
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                result = cell.getStringCellValue().trim();
            	if(hash.containsKey(result)){
            		int f = Integer.parseInt(hash.get(result).toString());
            		f++;
            		hash.put(result, f);
            		
            	}else{
            		hash.put(result, 1);
            		
            		
            	}
            	}//end while
        }//end while
        
        //提取tf-idf文件中的数值
        Hashtable hash_2 = new Hashtable();
        File filein = new File("tfidf.txt");
        Scanner input = new Scanner(filein);
        String s;
        double d;
        
		while(input.hasNext()){
			
			s = input.next().trim();
			d = input.nextDouble();

			hash_2.put(s, d);
		}    
        
        //遍历哈希表
        Set ss=hash.keySet();
        String key = new String();
       //计算tf-idf值
        for(Iterator<String> i=ss.iterator();i.hasNext();){
        	key = i.next().trim();
        	
        	if(hash_2.containsKey(key)){
        		int tf = Integer.parseInt(hash.get(key).toString());
        		double idf = Double.parseDouble(hash_2.get(key).toString());
        		hash.put(key, tf*idf);
        		//out1.println(key+" "+tf*idf);
        	}else{
        		hash.put(key,0.1);
        		//out1.println(key+" "+0.1);
        	}
          
        }
        
        key = null;
        
        //对所有字符串按照tf-idf排序并输出
        Set ss2=hash.keySet();
        
        String tmp = "";
   
        int size = hash.size();
        for(int j = 0;j<size;j++){
        	ss2=hash.keySet();
        	double max = 0;
        	for(Iterator<String> i=ss2.iterator();i.hasNext();){
        		key = i.next().trim();
        		double xxoo = Double.parseDouble(hash.get(key).toString());
        		if(xxoo>max){
        		//一遍循环找最大值
        			max = xxoo;
        			tmp = key;
        			
        		}
          
        	}
        
        	hash.remove(tmp);
        
        	
        	ss=hash.keySet();
        	
    	    if(!outList.contains(tmp))
        	  outList.add(tmp);
        	
        }
        
        //arraylist转换为数组输出
        out=(String[])outList.toArray(new String[1]);
   
         //out1.close();
        /*
        for(int i=0;i<out.length;i++)
        {
            System.out.print(out[i]);
        }
                */
        return out;
	}
            
        
    
    
}
