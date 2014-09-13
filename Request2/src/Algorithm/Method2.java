/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author zijie 
 */
public class Method2 {
    public String [] MethodTest(String Path)
    {
        StringBuffer keyword1 = new StringBuffer();
        try
        {
        /**
		 * 程序涉及三个excel
		 * src为输入文本，
		 * map为关键词库文本，
		 * result为输出文本
	 */
        
        String srcFilePath = Path;
	String mapFilePath = "关键词词库.xlsx" ;
	String newFilePath = Path.replace("名词.xlsx", "method2.xlsx");

        FileInputStream srcFile = new FileInputStream(new File(srcFilePath));
        FileInputStream mapFile = new FileInputStream(new File(mapFilePath));
          
        XSSFWorkbook srcWorkbook = new XSSFWorkbook(srcFile);
        XSSFWorkbook mapWorkbook = new XSSFWorkbook(mapFile);
        XSSFWorkbook newWorkbook = new XSSFWorkbook();
           
        XSSFSheet srcSheet = srcWorkbook.getSheetAt(0);
        XSSFSheet mapSheet = mapWorkbook.getSheetAt(0);
        XSSFSheet newSheet = newWorkbook.createSheet("test");
            
        Iterator<Row> srcRowIterator = srcSheet.iterator();
        Iterator<Row> mapRowIterator = mapSheet.iterator();
   
        //将map.xlst中的关键词保存到动态数组mapList中
        ArrayList mapList = new ArrayList();
        while(mapRowIterator.hasNext()) {
        	Row row = mapRowIterator.next();
        	Iterator<Cell> cellIterator = row.cellIterator();
        	
        	while(cellIterator.hasNext()) {
        		Cell cell = cellIterator.next();		
                String test = cell.getStringCellValue();
                mapList.add(test);
        	}
        }
        
        //将动态数组转化为静态数组mapArray,方便读取其中元素内容
        int mapListLength = mapList.size();
        String[] mapArray = (String[])mapList.toArray(new String[mapListLength]);
        
        //静态数组mapResult,用于记录mapArray中的对应项是否存在于src文件中
        //如,若mapArray[i]存在于src中,则mapResult[i]则置1，否则置0
        int[] mapResult = new int[mapListLength];
        for(int i=0; i<mapListLength; i++) {
        	mapResult[i]=0;
        }
        
        //读取src.xlsx文件,并将每一项内容与mapArray数组元素对比
       	while(srcRowIterator.hasNext()) {
       		Row row = srcRowIterator.next();
       		Iterator<Cell> cellIterator = row.cellIterator();
       		while(cellIterator.hasNext()) {
       			Cell cell = cellIterator.next();
       			String test = cell.getStringCellValue().trim();
       		
       			for(int i=0; i<mapListLength; i++) {
       				if(test.equals(mapArray[i])) 
           				mapResult[i]=1;
       			}
       		}
        }
        
       	//根据mapResult元素的值,决定将mapArray中的哪一项写入新文件result中
       	int newRowNum=0;
        for(int i=mapListLength-1; i>=0; i--) {
        	if(mapResult[i] == 1) {
        		Row newRow = newSheet.createRow(newRowNum++);
        		Cell newCell =newRow.createCell(0);
        		String test = mapArray[i];
                        keyword1.append(test+" ");
        		newCell.setCellValue(test);
        	}
        }
        
        srcFile.close();
        mapFile.close();
        
        //FileOutputStream fileOut = new FileOutputStream(new File(newFilePath));
       // newWorkbook.write(fileOut);
       // fileOut.close();
       // System.out.print("method2");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    
        
        return keyword1.toString().split(" ");
    }
    
}
