package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.jayway.restassured.RestAssured.given;


import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.utils.GoogleSheetReader;



public class Methods  {

	private GoogleSheetReader sheet = new GoogleSheetReader();
	private String GoogleString="1bFbhoykXVzTIxxbwAzMJ34xYa2R5PD8Rz6Y4VtUVRtI";

	public Map<Object, Object> getValues(String data) throws IOException
	{
		Map<Object, Object> allSheets;
		allSheets=sheet.getSheetMap(GoogleString, data); 
		return allSheets;
	}

	public Map<Object, Map<Object, Object>> getSheetData(Map<Object,Object> sheetVals) 
	{ 
		int count =0;		
		Map<Object, Map<Object, Object>> wholeSheet= new HashMap<Object,Map<Object, Object>>();
		String mapVal;
		Map<Object, Object> oneSheet;
		// oneSheet;
		try
		{
			for(Map.Entry<Object, Object> mapVals: sheetVals.entrySet())
			{   

				mapVal= mapVals.getValue().toString();
				System.out.println("mapVal= "+mapVal);
				if(!(mapVal.contains("Production")||(mapVal.contains("Local "))))
				{
					oneSheet=this.getValues(mapVal);
					System.out.println("oneSheet"+oneSheet);
					wholeSheet.put(mapVal,oneSheet);
					//oneSheet.clear();
				}
			}
		}

		catch(Exception e)
		{
			System.out.println("Exception Occured there"+e);
		}

		try
		{
			{
				if(sheetVals.containsValue("Local "))
				{
					wholeSheet.remove("Headers_Prod");
				}
				else
					if(sheetVals.containsValue("Production"))
					{
						wholeSheet.remove("Headers_Local");
					}
			}
		}	catch(Exception e)
		{
			System.out.println("Exception Occured here"+e);
		}
		return wholeSheet;

	}

	public Map<String, Map<String, String>> setAppParameters(Map<Object,Map<Object, Object>> paramValues){
		Map<Object, Object> allValues=new HashMap<Object, Object>();
		Map<String, String> changedMap = new HashMap<String, String>();
		Map<String, Map<String,String>> mapWithCities = new HashMap<String, Map<String, String>>();



		if(paramValues.containsKey("Params_App"))
		{
			try

			{ 
				Map<Object, Object> cities=this.getValues("Cities");
				allValues=paramValues.get("Params_App");
				Map<String, String> valMap;
				for (Map.Entry<Object, Object> entry : allValues.entrySet()) 
				{
					if(entry.getValue() instanceof String){
						changedMap.put((String)entry.getKey(), (String) entry.getValue());
						for(Map.Entry<Object, Object> cityvals:cities.entrySet())
						{   
						valMap= new HashMap<String, String>(changedMap);
						valMap.put((String)"filters[coordinates][city]", (String)cityvals.getValue());
						mapWithCities.put((String)cityvals.getValue(),valMap);

						}
					}
				}

			}  catch(Exception e)
			{
				System.out.println("Error occured"+e);

			}
		}
		else
			System.out.println("No parameters are available");
		return mapWithCities;
	}

	public Map<String, Map<String, String>> setWebParameters(Map<Object,Map<Object, Object>> paramValues){
		Map<Object, Object> allValues=new HashMap<Object, Object>();
		Map<String, String> changedMap = new HashMap<String, String>();
		Map<String, Map<String,String>> mapWithCities = new HashMap<String, Map<String, String>>();


		if(paramValues.containsKey("Params_Web"))
		{
			try

			{ 
				Map<Object, Object> cities=this.getValues("Cities");
				allValues=paramValues.get("Params_Web");
				Map<String, String> valMap;
				for (Map.Entry<Object, Object> entry : allValues.entrySet()) 
				{
					if(entry.getValue() instanceof String){
						changedMap.put((String)entry.getKey(), (String) entry.getValue());
						for(Map.Entry<Object, Object> cityvals:cities.entrySet())
						{   
						valMap= new HashMap<String, String>(changedMap);
						valMap.put((String)"filters[coordinates][city]", (String)cityvals.getValue());
						mapWithCities.put((String)cityvals.getValue(),valMap);

						}
					}
				}

			}  catch(Exception e)
			{
				System.out.println("Error occured"+e);

			}
		}
		else
			System.out.println("No parameters are available");
		
				return mapWithCities;
	}

	public Map<String, String> setLLParameters(Map<Object,Map<Object, Object>> paramValues){
		Map<Object, Object> allValues=new HashMap<Object, Object>();
		Map<String, String> changedMap = new HashMap<String, String>();


		if(paramValues.containsKey("Params_LL"))
		{
			try

			{ 
				allValues=paramValues.get("Params_LL");
				for (Map.Entry<Object, Object> entry : allValues.entrySet()) 
				{
					if(entry.getValue() instanceof String){
						changedMap.put((String)entry.getKey(), (String) entry.getValue());
					}

					return changedMap;
				}

			}  catch(Exception e)
			{
				System.out.println("Error occured"+e);

			}
		}
		else
			System.out.println("No parameters are available");
		return changedMap;
	}

	public Map<Object, Object> setHeaders(Map<Object,Map<Object, Object>> paramValues){
		Map<Object, Object> allValues=new HashMap<Object, Object>();
		Map<String, List<String>> changedMap = new HashMap<String,List< String>>();
		Map<String, String> value;

		if(paramValues.containsKey("Headers_Prod"))
		{
			try

			{ 
				allValues=paramValues.get("Headers_Prod");

				for (Map.Entry<Object, Object> entry : allValues.entrySet()) 
				{ 	
					List <String> mapper= (List< String>)(entry.getValue());
					if(mapper instanceof List<?>)
					{
						changedMap.put((String) entry.getKey(), (List<String>)entry.getValue());

					}

					return allValues;
				}

			}  catch(Exception e)
			{
				System.out.println("Error occured"+e);
			}
		}
		else
			if(paramValues.containsKey("Headers_Local"))

			{
				try
				{ 
					allValues=paramValues.get("Headers_Local");
					for (Map.Entry<Object, Object> entry : allValues.entrySet()) 
					{ 	
						List <String> mapper= (List< String>)(entry.getValue());
						if(mapper instanceof List<?>)
						{
							changedMap.put((String) entry.getKey(), (List<String>)entry.getValue());

						}

						return allValues;
					}

				}  catch(Exception e)
				{
					System.out.println("Error occured"+e);

				}
			}
			else	   
				System.out.println("No Headers are available");
		return allValues;

	}

	public List<String> setResource(Map<Object,Map<Object, Object>> paramValues)
	{

		Map<Object, Object> allValues=new HashMap<Object, Object>();
		Map<String, String> changedMap = new HashMap<String, String>();
		String value="";
		List<String> allResources = new ArrayList<String>();
		if(paramValues.containsKey("Resources"))
		{
			try

			{ 
				allValues=paramValues.get("Resources");
				for (Map.Entry<Object, Object> entry : allValues.entrySet()) 
				{
					if(entry.getValue() instanceof String){
						changedMap.put((String)entry.getKey(), (String) entry.getValue());
					}
					for(Map.Entry<String,String> resources:changedMap.entrySet())
					{  
						// data=resources.getKey();
						value=resources.getValue();
						allResources.add(value);
					}

				}

			}  catch(Exception e)
			{
				System.out.println("Error occured"+e);

			}
		}
		else
			System.out.println("No resources available");
		return allResources;
	}

	public String getAndSetResource( List<String> list,int index)
	{
		String value="";
		try
		{
			value= list.get(index);	   
		}
		catch(Exception e){
			System.out.println("Exception"+e);
		}
		return value;

	}

	public String setUrl()
	{

		String uRlVal="";
		Map<Object, Object> urlMap;
		try{
			Map<Object, Object> conFigMap= this.getValues("DataConfig");
			urlMap=this.getValues("Urls");
			//System.out.println(urlMap);
			if(conFigMap.containsKey("Environment"))
			{
				uRlVal= (String) conFigMap.get("Environment");

				if(uRlVal.equalsIgnoreCase("Local "))
				{

					uRlVal=(String) urlMap.get("URL_Local");
				}
				else
				{
					uRlVal=(String) urlMap.get("URL_Prod");
				}
			}
			else 
				System.out.println("No URl present");

		}  catch(Exception e)
		{
			System.out.println("Error occured"+e);

		}

		return uRlVal;
	}

	public Map<String, String> setAppHeadersers(Map<Object, Object> map)
	{
		Map<String,String> appHeaders=new HashMap<String, String>();
		if(map.containsKey("Token_App"))
			try
		{
				{
					List<String> appTokens= (List<String>) map.get("Token_App");	
					appHeaders.put(appTokens.get(0),appTokens.get(1));

				}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return appHeaders;	
	}


	public Map<String, String> setWebHeadersers(Map<Object, Object> map)
	{
		Map<String,String> webHeaders=new HashMap<String, String>();
		if(map.containsKey("Token_Web"))
			try
		{
				{
					List<String> appTokens= (List<String>) map.get("Token_App");	
					webHeaders.put(appTokens.get(0),appTokens.get(1));

				}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return webHeaders;	
	}




	public Map<String, String> setLLHeadersers(Map<Object, Object> map)
	{
		Map<String,String> llHeaders=new HashMap<String, String>();
		if(map.containsKey("Token_Web"))
			try
		{
				{
					List<String> appTokens= (List<String>) map.get("Token_App");	
					llHeaders.put(appTokens.get(0),appTokens.get(1));

				}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return llHeaders;	
	}


	public static void main(String[] args) throws IOException
	{



		Methods method = new Methods();
		//System.out.println("sheet"+sheet);
		Map<Object, Object> sheet = method.getValues("DataConfig");
		//Map<Object, Map<Object, Object>> sheetVal= method.getSheetData(sheet);

		Map<Object, Map<Object, Object>> sheetVal= method.getSheetData(sheet);
		Map<Object, Object> headers= method.setHeaders(sheetVal);
		Map<String, String> appHeader=method.setAppHeadersers(headers);
		System.out.println("AllList"+appHeader);

		//List<String> resources = method.setResource(sheetVal);
		//System.out.println("resources"+resources);
		//String actualResource = method.getAndSetResource(resources, 1);
	}

}







