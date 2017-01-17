package TestCases;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.main.Methods;
import com.main.ThreadApp;
import com.main.ThreadWeb;
import com.utils.Comparator;

public class Test1  {
	
	Map<String, Map<String, String>> values= new HashMap<String, Map<String,String>>();
	Comparator comp = new Comparator();
	Map<String, Map<String, Map<String, String>>> mapApp;
	Map<String, Map<String, Map<String, String>>> mapWeb;

//public void initialTest()

@BeforeClass
  public void runTest() throws InterruptedException
  {
	ThreadApp obj1= new ThreadApp();
 	ThreadWeb obj2 = new ThreadWeb();
 	Thread thread = new Thread(obj1);
 	Thread thread1= new Thread(obj2);
	List<Thread> threads= new ArrayList<Thread>();
	 threads.add(thread);
	 threads.add(thread1);
	 
 	thread.start();
 	thread1.start(); 
    
    for(Thread threade:threads)
    {	threade.join();
    }
    
   // Thread.sleep(10000);
    mapApp=obj1.returnVal();
    mapWeb=obj2.returnVal();
    this.values=comp.comparator(mapApp, mapWeb);
    System.out.println("values"+values);
}
  //System.out.println("mapApp"+mapWeb);	
  
  @DataProvider
	public Object[][] getData()
	{
	
	 Map<String,String> map= new HashMap<String,String>();
	 String value=null;
	 String key= null;
     Object[][] obj =new Object[values.size()][];
     int i=0;
			 for(Map.Entry<String, Map<String,String>> object : this.values.entrySet())
		     {
		 obj[i++]= new Object[] {object.getKey(), object.getValue()};
			 
		 }
	 
     
     return obj;
	}
     
     @Test(dataProvider="getData")
     public void runTest(String City, Map<String, String> idPair)
     {
    	 boolean condition=false;
    	 if(idPair.containsValue("Correct"))
    	 {
    		 condition=true;
    	 }
    	 else
    		 if(idPair.containsValue("Incorrect"))
    		 {
        		 condition=false;
        	 }
    	 System.out.println("City: "+City+"  idPair: "+idPair);	 
    	 Assert.assertTrue(condition);
    	
    	 
     }
	  
  }





	
		   
		
	

	


