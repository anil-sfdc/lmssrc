package com.dell;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.javatpoint.Test;

public class Main {

	/**
	 * @param args
	 */
	public static String[] getQueries() {
        //  List<String> queries = new ArrayList<>();
    	 String input;
    	
    	System.out.println("Enter Query File Path : ");
    	
    	/*Scanner keybord=new Scanner(System.in);
    	String path=keybord.nextLine();
    	
 */
    	String path=new String("C:\\Users\\Anil\\Desktop\\sql\\testsql.txt");
    	
        String queries[]=null;
        BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        String str=null;
		try {
			str = br.readLine();
		
        StringBuilder sb = new StringBuilder();
        while (str != null) {
            sb.append(str);
            str = br.readLine();
            queries = sb.toString().split(";");
        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println(sb.toString());
        
        return queries;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println(" Database connected");
		
	
           String sql[]= new Main().getQueries();
           for(String s:sql )
           {
        	   System.out.println(" Select SQL:"+s);
        	   
        	   SelectTable SelectTableObj=(SelectTable)ctx.getBean("stable");
        	   
        	   //SelectTable SelectTableObj=new SelectTable();
        	  
        	   SelectTableObj.readXML(s);
        	   
           }

	}

}
