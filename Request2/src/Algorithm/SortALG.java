/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithm;

import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.floor;
import java.util.Scanner;

/**
 *
 * @author miss
 */
public class SortALG {
    
     public void keywordSort(String []  keyword ,String [] method1,String [] method2 ) throws FileNotFoundException, IOException
    {
        int orderFactor = 5 ;
        int numKeyword = keyword.length;
        File file = new File("weight.txt");
        Scanner input = new Scanner(file);
        int methodScore1=input.nextInt();
        int methodScore2=input.nextInt();
	boolean numMatch1=false;
        int placeMatch1 = 0 ;
	boolean numMatch2=false;
        int placeMatch2 = 0 ;
     //   boolean [] inputMatch = new boolean[inputKeyword.length]; 
        /*
         FileInputStream workFile = new FileInputStream(new File("关键词词库.xlsx"));
         XSSFWorkbook srcWorkbook = new XSSFWorkbook(workFile);
         XSSFSheet srcSheet = srcWorkbook.getSheetAt(0);
         Iterator<Row> srcRowIterator = srcSheet.iterator();        
        for(int i=0;i<inputKeyword.length;i++)
        {
            for(int j=0;j<method1.length;j++)
            {
                if(inputKeyword[i].equals(method1[j]))
                    inputMatch[i]=true;
            }
        }
        for(int i=0;i<inputKeyword.length;i++)
        {
            for(int j=0;j<method2.length;j++)
            {
                
                if(inputKeyword[i].equals(method2[j]))
                    inputMatch[i]=true;
            }
        }

        for(int i=0;i<inputMatch.length;i++)
        {
            if(inputMatch[i]==false)
            {
                Row row=srcSheet.createRow(srcSheet.getLastRowNum()+1);
                Cell cell =row.createCell(0);
                cell.setCellValue(inputKeyword[i]);
            }
        }
        */
        for(int i=0;i<keyword.length;i++)//匹配方法1
        {
            for(int j=0;j<method1.length;j++)
            {
                if(trim(keyword[i]).equals(trim(method1[j])))
                {
                    numMatch1 = true ;
                    placeMatch1=i;
                    methodScore1=(int)floor(methodScore1+orderFactor*(i/keyword.length));
                }
            }
        }
     
         for(int i=0;i<keyword.length;i++)//匹配方法2
        {
            for(int j=0;j<method2.length;j++)
            {
                if(trim(keyword[i]).equals(trim(method2[j])))
                {
                    numMatch2 = true;
                    placeMatch2=i;
                    methodScore2=(int)floor(methodScore2+orderFactor*(i/keyword.length));
           
                }
            }
        }      
  
        System.out.print(methodScore2);
        FileWriter filewriter = new FileWriter("weight.txt");
        filewriter.write(String.valueOf(methodScore1));
        filewriter.write(" ");
        filewriter.write(String.valueOf(methodScore2));
        filewriter.flush();
        filewriter.close();
    }

  
    
}
