/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithm;

import ICTCLAS.I3S.AC.ICTCLAS50;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author miss lulu
 */
public class SegmentationAndNounFilter {
    public String [] SegmentationNounFilter(String filepath,String filename)
    { try{
            
            String mingciPath = filepath.replace(filename,"名词.xlsx");
            ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
            String argu = ".";
            if(testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false){
                System.out.println("Init Fail");
            }else{
                System.out.println("Init Succeed!");
            }
 
    
            StringBuffer input = new StringBuffer();
             FileInputStream file = new FileInputStream(new File(filepath));
             XSSFWorkbook workbook = new XSSFWorkbook(file);
             XSSFSheet sheet1 = workbook.getSheetAt(0);
             Iterator<Row> rowIterator = sheet1.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                //Row rowNew =sheetNew.createRow(rowNumNew++);
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    // Cell cellNew =rowNew.createCell(cellNumNew++);
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            break;
                        case Cell.CELL_TYPE_STRING:
                        	 input.append(cell.getStringCellValue());
                              
                    }
                }
                
                //System.out.println("");
            }
            //未导入用户词典
            byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(input.toString().getBytes("GB2312"), 0, 1);
                
            String nativeStr = new String(nativeBytes,0,nativeBytes.length,"GB2312");
        
            //创建xlsx文件
            XSSFWorkbook workbookNew = new XSSFWorkbook();
            XSSFSheet sheetNew = workbookNew.createSheet("test");
             int rowNum = 0;
            
          // WritableWorkbook book = Workbook.createWorkbook(new File("n.xls"));
           // WritableSheet sheet = book.createSheet("num1", 0);
            Scanner in = new Scanner(nativeStr);
            int i = 0;//行数
            while(in.hasNext()){           	
            	String ss = in.next();
                
            	Pattern pattern = Pattern.compile("(.+?)/n.*");
            	Matcher matcher = pattern.matcher(ss);
            	if(matcher.find() && matcher.group(1).length()>1 &&!isDigit(matcher.group(1))){
            		
            		//label = new jxl.write.Label(0, i, matcher.group(1));//设置单元格
            		//sheet.addCell(label);
                        Row rowNew =sheetNew.createRow(rowNum++);
                        Cell cellNew =rowNew.createCell(0);
                        cellNew.setCellValue(matcher.group(1));
            		//i++;

            	}
            }
            
        //    book.write();
         //   book.close();
            FileOutputStream fileOut = new FileOutputStream(new File(mingciPath));//new file
            workbookNew.write(fileOut);
            fileOut.close();
            
            //释放分词组件资源            
          
  
            
            
         

            file.close();
            
            testICTCLAS50.ICTCLAS_Exit();
            
        }catch(Exception ex){
            
        }
        
        
        return null;
        
    }
    private boolean isDigit(String strNum) {
             return strNum.matches("[0-9]{1,}");
}

    
}
