
package edu.cmu.square.proofofconcept;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.cmu.square.client.exceptions.SquareException;


public class SquareUtility
{

	public static void main(String[] args) 
	{
		//getReqProCSVFormat("c:\\importTest\\outpuImport.xml","c:\\importTest\\output.CSV");
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(new File("c:\\importTest\\requirements.xml")));
			String SQUARE_XML =getContent(reader);
			
			reader = new BufferedReader(new FileReader(new File("c:\\importTest\\UC All Use Cases.CSV")));
			String ReqProCSV = getContent(reader);
			
			
			RequisiteProTransformations tranformer = new RequisiteProTransformations();
			
		    tranformer.transform_SquareXML_To_ReqProCSV(SQUARE_XML);
			tranformer.transform_SquareXML_Terms_To_ReqProCSV(SQUARE_XML);
			tranformer.transform_ReqProCSV_To_SquareXML(ReqProCSV);
			
			
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public static String getContent(BufferedReader reader)
	{
		StringBuilder builder= new StringBuilder();
		    String str;
		    try
			{
				while ((str = reader.readLine()) != null) {
					builder.append(str+"\n");
				}
				 reader.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		    
		    return builder.toString();
		
	}
	


}
