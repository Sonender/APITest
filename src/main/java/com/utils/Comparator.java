package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comparator {

	Map<String, Map<String, Map<String, String>>> mapApp;
	Map<String, Map<String, Map<String, String>>> webApp;
	
	public Map<String, Map<String,String>> comparator(Map<String, Map<String, Map<String, String>>> firstMap, Map<String, Map<String, Map<String, String>>> secondMap) {
		Map<String, Map<String, Map<String, String>>> interimMap=new HashMap<String, Map<String, Map<String, String>>>();
		Map<String,Map<String, String>> variMap1 = new HashMap<String,Map<String, String>>();
		Map<String,Map<String, String>> variMap2 = new HashMap<String,Map<String, String>>();
		Map<String, String> innerValues=new HashMap<String, String>();
		Map<String, String> innerinMap=new HashMap<String, String>();
	  //	List<String> compValues = new ArrayList<String>();
	  	Map<String, Map<String, String>> finalVals= new HashMap<String,Map<String, String>>();
	  
	  	String vals="";
	  	String keyVal="";
	  	String keys="";
	  	String KeyVal1="";
	  	int count=0;
	  	for(Map.Entry<String, Map<String,Map<String, String>>> compVals:firstMap.entrySet())
	  	{    
	  		Map<String, String> cityPairing;
	  		variMap1=compVals.getValue();
	  	    keyVal=compVals.getKey();
	  		 int counter=0;
	  		interimMap=secondMap;
	  		variMap2=interimMap.get(keyVal);
	  		cityPairing=new HashMap<String, String>();
	  		for(Map.Entry<String, Map<String,String>> mapper:variMap1.entrySet())
	  		{ 
	  			
	  			innerValues= mapper.getValue();
	  			KeyVal1=mapper.getKey();
	  			innerinMap =variMap2.get(KeyVal1);
	  		    if(innerValues.equals(innerinMap) )	
	  		    {
	  		    	
	  		     cityPairing.put(KeyVal1, "Correct");
	  	     		
	  		    }
	  			
  			else
  			{ 				      
	  					cityPairing.put(KeyVal1, "Incorrect");
						
	  				 }
	  		  finalVals.put(keyVal, cityPairing);
	  		}
	  		 	
	}
	  	 
		return finalVals;	
	}
	
}
	
