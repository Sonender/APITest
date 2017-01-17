package com.main;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class ThreadApp implements Runnable{
	private Map<String, Map<String,String>> appParams;
	private Map<Object, Object> headers;
	private Map<String, String> appHeaders;
	private Methods method = new Methods();
	private Map<String, Map<String, String>> webParams;
    private  Map<String, Map<String, Map<String, String>>> mapper = new HashMap<String, Map<String, Map<String, String>>>();
	 List<String> resourceList;
	 public Map<String, Map<String, Map<String, String>>> returnVal()
	 {
		 Map<String, Map<String, Map<String, String>>> value= this.mapper;
		 System.out.println(value);
		 return value;
	 }
	 
public void run()
{
	
	 try{
//			Methods method = new Methods();
			Map<Object, Object> sheet= method.getValues("DataConfig");
			Map<Object, Map<Object, Object>> sheetVal= method.getSheetData(sheet);
		    this.headers = method.setHeaders(sheetVal);
		    this.appHeaders=method.setAppHeadersers(headers);
		    this.resourceList= method.setResource(sheetVal);
		    this.appParams =method.setAppParameters(sheetVal);
		    //this.webParams=method.setWebParameters(sheetVal);
		     }
		  catch(Exception e)
		  {
			  System.out.println("Exception occured"+e);
		  }
			  
		  	try{
				
			  //  Map<String, Map<String, Map<String, String>>> mapper = new HashMap<String, Map<String, Map<String, String>>>();
				for(Map.Entry<String,Map<String, String>> appParam: appParams.entrySet()) 	
				   {     
				Response resp=given().urlEncodingEnabled(false).log().all().contentType("application/json\r\n").when().parameters(appParam.getValue()).headers(appHeaders).get(method.setUrl()+resourceList.get(0));
				    
			  
				String Resp = resp.getBody().asString();
				System.out.println(Resp);
				List<String>id = resp.getBody().jsonPath().getList("hotels.id");
				List<String> hotels =resp.getBody().jsonPath().getList("hotels");
				
			//	List<String> priort = resp.getBody().jsonPath().getList("hotels.prioritization_scores_breakup");
				Map<String, String> priort_score = new HashMap<String, String>();
				Map<String,Map<String,String>> idPriotMap = new HashMap<String, Map<String, String>>();
				int hotel_size= hotels.size();
				int id_size= id.size();
				//int priort_size=priort.size();
				String m_g="";
				String total="";
				String inventory= "";
				String location="";
				String cust_ex="";
				String hotel_listing="";
				String c3="";
				String manual_param="";
				String pricing="";
				String slashed_percent="";
				String flagship_score="";
				String oyo_share="";
				for(int i=0; i<2;i++)
				{m_g = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.m_g");
				if(m_g==null)
					m_g="0";
				priort_score.put("m_g", m_g);
				total = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.total");
				if(total==null)
				{	total="0";}
				priort_score.put("total", total);
				
				inventory = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.inventory");
				if(inventory==null)
				{	inventory="0";}
				priort_score.put("inventory", inventory);
				location = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.location");
				if(location==null)
				{	location="0";}
				priort_score.put("location", location);
				cust_ex = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.cust_ex");
				if(cust_ex==null)
				{	cust_ex="0";}
				priort_score.put("cust_ex", cust_ex);
				hotel_listing = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.hotel_listing");
				if(hotel_listing==null)
				{hotel_listing="0";}
				priort_score.put("hotel_listing", hotel_listing);
				c3 = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.c3");
				if(c3==null)
				{	c3="0";}
				priort_score.put("c3", c3);
				manual_param = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.manual_param");
				if(manual_param==null)
				{	manual_param="0";}
				priort_score.put("manual_param", manual_param);
				pricing = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.pricing");
				if(pricing==null)
					{pricing="0";}
				priort_score.put("pricing",pricing);
				slashed_percent = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.slashed_percent");
				if(slashed_percent==null)
					{slashed_percent="0";}
				priort_score.put("slashed_percent", slashed_percent);
				flagship_score = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.flagship_score");
				if(flagship_score==null)
					{flagship_score="0";}
				priort_score.put("flagship_score", flagship_score);
				oyo_share = resp.getBody().jsonPath().getString("hotels["+i+"].prioritization_scores_breakup.oyo_share");
				if(oyo_share==null)
						{oyo_share="0";}
				priort_score.put("oyo_share", oyo_share);
				String  ids = resp.getBody().jsonPath().getString("hotels["+i+"].id");
				idPriotMap.put(ids, priort_score);
				}
				//System.out.println(idPriotMap);
				mapper.put(appParam.getKey(), idPriotMap);
				  } //System.out.println(mapper);
			}  catch(Exception e){
				System.out.println(e);
			}
	
}

}
