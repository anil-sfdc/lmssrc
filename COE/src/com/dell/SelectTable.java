package com.dell;




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SelectTable {
	private JdbcTemplate jdbcTemplate;
	String sqlTable=null;
	
	
	
	 public void readXML(String sql){// throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JSONException, ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder=null;
	        System.out.println("Inside readXML ");
			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        Document doc = builder.newDocument();
	        Element results = doc.createElement("Results");
	        doc.appendChild(results);

	        
	        Connection conn;
			try {
				if(jdbcTemplate==null)
				{
					System.out.println("DB config err--- ");
					return;
			    	
				}
				PreparedStatement preStatement=null;
				
				try {
					conn = jdbcTemplate.getDataSource().getConnection();

					 preStatement = conn.prepareStatement(sql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
	        
	        
	        //String sql = "select * from EMP_EMAIL WHERE to_char(dob,'MM-DD')<>to_char(sysdate,'MM-DD') or DOB is NULL";
	        //PreparedStatement preStatement = connect.prepareStatement(sql);
	        try (ResultSet result = preStatement.executeQuery()) {
	            ResultSetMetaData metaData = result.getMetaData();
	            int columnCount = metaData.getColumnCount();

	            while (result.next()) {
	                Element row = doc.createElement("Row");
	                results.appendChild(row);
	                for (int i = 1; i <= columnCount; i++) {
	                    String xyz;
	                    try {
	                        xyz = result.getObject(i).toString();
	                    } catch (NullPointerException e) {
	                        xyz = "";
	                    }
	                    Element node = doc.createElement(metaData.getColumnLabel(i));
	                    node.appendChild(doc.createTextNode(xyz));
	                    row.appendChild(node);
	                }

	            }
	            DOMSource domSource = new DOMSource(doc);
	            TransformerFactory tf = TransformerFactory.newInstance();
	            Transformer transformer;
				try {
					transformer = tf.newTransformer();
				
	            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	            //  StringWriter sw = new StringWriter();
	            StreamResult sr = new StreamResult("C:\\Users\\Anil\\Desktop\\sql\\t.xml");
	            try {
					transformer.transform(domSource, sr);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            // QueryFileReader.setQueries(path, sw.toString());
	            //System.out.println(sw.toString());
	            //connect.close();
	            result.close();
			}
	            // System.out.println(jArray);
 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        
	    
			}
			finally{
		        //finally block used to close resources
			}
				 
	 }
	public void  SelectTableSQL1(String sql)
	{
		
		
      String input;
    	
    	System.out.println("Enter Query File Path : ");
    	
    	/*Scanner keybord=new Scanner(System.in);
    	String path=keybord.nextLine();
	    */JSONArray jArray = new JSONArray();
		
    	String path=new String("C:\\Users\\Anil\\Desktop\\sql\\t.json");
    	
    	readXML(path);
		Connection conn;
		try {
			if(jdbcTemplate==null)
			{
				System.out.println("DB config err--- ");
				return;
		    	
			}
			
			conn = jdbcTemplate.getDataSource().getConnection();
		
		
		 PreparedStatement preStatement = conn.prepareStatement(sql);
	        ResultSet result = preStatement.executeQuery();
	        ResultSetMetaData metaData = result.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
	        System.out.println("  Before Data "+columnCount);
		if(result==null)
		{
			 System.out.println("Table No Data");
				
		}
	        while (result.next()) {
	        	 System.out.println("Inside loop");
	            JSONObject jobj = new JSONObject();
	            for (int i = 1; i <= columnCount; i++) {
	                try {
	                	 System.out.println("Inside  for loop");
						jobj.put(metaData.getColumnLabel(i), result.getObject(i));
						
					      //System.out.println(" Data "+result.getObject(i).toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            jArray.put(jobj);
	            System.out.println(" Data "+jArray.toString());
	            bw.write(jArray.toString(),0, jArray.toString().length());
	        }
	        // QueryFileReader.setQueries(path, jArray.toString());
	        //System.out.println(jArray);
	        result.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//sqlTable=new String(sql);
		//String query=
		//jdbcTemplate.execute(sql);
	}
	 //String tableSelect(){
		//Connection conn = null;
		
		//conn = dataSource.getConnection();
	//}
	

}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

