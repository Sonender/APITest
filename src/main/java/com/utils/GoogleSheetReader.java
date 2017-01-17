



package com.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class  GoogleSheetReader {
	/** Application name. */
	private  final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */
	//private static final java.io.File DATA_STORE_DIR = new java.io.File(
	//  System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart.json");
	private  final File DATA_STORE_DIR = new File(System.getProperty("user.dir"), "/src/main/resources");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private  FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private  final JsonFactory JSON_FACTORY =
			JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private  HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials
	 * at ~/.credentials/sheets.googleapis.com-java-quickstart.json
	 */
	private  final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

	public GoogleSheetReader() 
	{
		try
		{
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) 
		{
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	private Credential authorize() throws IOException 
	{
		// Load client secrets.
		String filePathScreenShot = System.getProperty("user.dir")+"/src/main/resources/client_secret.json";
		InputStream in = new FileInputStream(filePathScreenShot);
		//System.out.println("i have done it");

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		//System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Sheets API client service.
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	private Sheets getSheetsService() throws IOException
	{
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
	}
	/*
	 * Sample code for converting into JSON Object 
	 * 
	 *
    public static int i = 0;
	public static Object obj = null;
	public static String[][] json_KeyValuePair = new String[1000][2];
	public static String filePathScreenShot = System.getProperty("user.dir")+"/src/main/resources/client_secret.json";
	public static String parseJSONToString(String keyVal, String filePath) {
		JSONParser parser = new JSONParser();

		try {
			obj = parser.parse(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject jsonObj = (JSONObject) obj;


		//JSONArray companyList = (JSONArray) jsonObj.get(keyVal);
		//System.out.println(companyList.get(0));
		json_KeyValuePair[i][0] = keyVal.toString();
		//json_KeyValuePair[i][1] = (String) jsonObj.get(keyVal);
		JSONObject jsonObj1 =   (JSONObject) jsonObj.get(keyVal);

		System.out.println(jsonObj1);
		i++;
		return (String) jsonObj.get(keyVal);
	}
	 */	

	/*
	 * Excelread : this function has all the logic of converting data from spread sheet data into Map
	 * Note : If spreadsheet has more than two columns, then it will make A as key and will convert rest data in row to a list of Object
	 * spreadsheet has two columns then, key value pair map will be returned
	 */

	private Map<Object,Object> Excelread(String spreadsheetId,String range)
	{
		Map<Object,Object> map = new HashMap<Object,Object>();

		try
		{
			Sheets service = getSheetsService();
			ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			//			OutputStream out = new FileOutputStream(System.getProperty("user.dir")+"/file");
			//			System.out.println("File saved at" + System.getProperty("user.dir"));
			//			service.spreadsheets().values().get(spreadsheetId, range).executeAndDownloadTo(out);
			List<List<Object>> values = response.getValues();
			if (values == null || values.size() == 0) 
			{
				System.out.println("No data found.");
			} 
			else
			{
				//System.out.println("No of columns in sheet"+ values.get(0).size());
				if(values.get(0).size()>2)
				{
					//System.out.println(values.size());
					//System.out.println("No of colums greater than 2, will return Map with key and list of objects");
					for(List row : values)
					{   
						Object key = row.get(0);
						int sizeOfList = row.size();
						List <Object> newlist = new ArrayList<Object>();
						for(int i = 1; i<sizeOfList; i++)
						{
							Object x = row.get(i);
							newlist.add(x.toString());
							x = null;
						}
						map.put(key, newlist);
					}
				}
				else
				{
					//System.out.println(values.size());
					//System.out.println("No of colums greater than 2, will return Map with key and object");
					for (List row : values)
					{
						map.put(row.get(0), row.get(1));    
					}
				}
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex);
		}
		return map;
	}

	/* 
	 * 
	 * to access this class, 
	 * create object of this class and call getSheetMap function with params : SpreadSheetId and range (it could be both range and sheet name)
	 * function getSheetMap return a map of object, object
	 * 
	 * */

	public Map<Object,Object> getSheetMap(String spreadSheetId, String range) throws IOException
	{
		Map<Object, Object> newMap=Excelread(spreadSheetId, range);
		return newMap;
	}

	public void writeToSheet(String spreadsheetId, String SheetName, Map<Object,Object> map) throws IOException{
		Sheets service = getSheetsService();
		// String range = RowStart+":"+RowEnd;
		String range = SheetName;
		List<List<Object>> arrData = getData(map);
		ValueRange oRange = new ValueRange();
		oRange.setRange(range);
		oRange.setValues(arrData);
		List<ValueRange> oList = new ArrayList<ValueRange>();
		oList.add(oRange);
		BatchUpdateValuesRequest oRequest = new BatchUpdateValuesRequest();
		oRequest.setValueInputOption("RAW");
		oRequest.setData(oList);
		service.spreadsheets().values().batchUpdate(spreadsheetId, oRequest).execute();
		System.out.println("successfully write to google sheet");
	}
	public  List<List<Object>> getData (Map<Object,Object> map){
		List<List<Object>> List = new ArrayList<List<Object>>();
		for(Map.Entry<Object,Object> ExcelVal: map.entrySet()){
			//System.out.println(ExcelVal.getKey() + "  " + ExcelVal.getValue());
			List<Object> subList = new ArrayList<Object>();
			subList.add(ExcelVal.getKey().toString());
			//subList.addAll((List<Object>) ExcelVal.getValue());
			try{
				subList.addAll((List<Object>) ExcelVal.getValue());
			}
			catch(Exception e){
				subList.add(ExcelVal.getValue());
			}
			List.add(subList);
		}
		return List;
	}

	public static void main(String[] args) throws IOException
	{
		GoogleSheetReader sheet = new GoogleSheetReader();
		Map<Object, Object> mapper ;
		mapper = sheet.getSheetMap("1bFbhoykXVzTIxxbwAzMJ34xYa2R5PD8Rz6Y4VtUVRtI", "");
		for(Map.Entry<Object, Object> obj: mapper.entrySet())
		{			System.out.println(obj.getKey()+" "+obj.getValue());
		}
	}
}

