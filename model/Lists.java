package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Lists {
	private ArrayList<Data> gdp_const; //GDP const LCU
	private ArrayList<Data> gdp_growth; //GDP growth annual %
	private ArrayList<Data> gdp_pc; //GDP per capita const LCU
	private ArrayList<Data> cpi; // Consumer Price Index
	private ArrayList<Data> unemployment; // Unemployment (% of total labor force)
	private ArrayList<Data> cab; // Current account balance (% of GDP)
	private ArrayList<Data> trade; // Trade (% of GDP)
	private ArrayList<Data> inflation; //Inflation YOY
	private ArrayList<Data> services;
	private ArrayList<Data> agriculture;
	private ArrayList<Data> industry;
	private ArrayList<Data> hdi;
	private String[] codes = {"NY_GDP_MKTP_CD","NY_GDP_MKTP_KD_ZG","NY_GDP_PCAP_CD",
			"FP_CPI_TOTL","SL_UEM_TOTL_ZS","BN_CAB_XOKA_GD_ZS","NE_TRD_GNFS_ZS",
			"NV_SRV_TETC_ZS","NV_AGR_TOTL_ZS","NV_IND_TOTL_ZS","FP_CPI_TOTL_ZG"}; 
	
	/**
	 * constructor that retrieves all data from the API
	 */
	public Lists() throws MalformedURLException, URISyntaxException, JSONException, IOException{
		try{
			getDataFromAPI();
			retrieveDataFromFile();
			} catch(Exception e){
				System.out.println("API is offline, accessing the last saved version");
				retrieveDataFromFile();
			}
			
		
	}
	/**
	 * Creates an array of Data, with a seperate obejct for each country
	 * @param code - code of a dataset from the API
	 */
	private ArrayList<Data> getAllData(String code) throws MalformedURLException, URISyntaxException, JSONException, IOException{
		ArrayList<Data> data = new ArrayList<Data>();
		String[] c = {"GBR","USA","RUS","ITA","FRA","JPN","IND","CAN","BRA","DEU","CHE","SAU","ARG","AUS","TUR","CHN","KOR"};
		for(int i = 0; i < c.length; i++){
			data.add(getCountryData(c[i] + "_" + code,"WWDI/"));
		}
		return data;
	}
	/**
	 * gets data for all countries for HDI
	 * @return ArrayList of Data for all countries for an indicator
	 */
	private ArrayList<Data> getAllDataHDI() throws MalformedURLException, URISyntaxException, JSONException, IOException{
		ArrayList<Data> data = new ArrayList<Data>();
		String[] c = {"GBR","USA","RUS","ITA","FRA","JPN","IND","CAN","BRA","DEU","CHE","SAU","ARG","AUS","CHN","KOR","TUR"};
		for(int i = 0; i < c.length; i++){
			data.add(getCountryDataHDI(c[i]));
		}
		return data;
	}
	/**
	 * gets country data for HDI indicator
	 * @param code - country code
	 * @return Data object for a particular country
	 */
	private Data getCountryDataHDI(String code) throws URISyntaxException, MalformedURLException, JSONException, IOException{
		
		URI uri = new URI("http://ec2-52-1-168-42.compute-1.amazonaws.com/version/1/country_code/" + code + "/indicator_id/137506/year/2011,2012,2013");
	    JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
	    JSONObject root2 = new JSONObject(tokener);
	    String name = (String) root2.getJSONObject("indicator_name").get("137506");
	    String country = (String)root2.getJSONObject("country_name").get(code);
	    if(country.equals("UK")){
	    	country = "United Kingdom";
	    }
	    if(country.equals("USA")){
	    	country = "United States";
	    }
	    if(country.equals("Russia")){
	    	country = "Russian Federation";
	    }
	    if(country.equals("Korea (Republic of)")){
	    	country = "Korea";
	    }
	      
	    JSONArray array = root2.getJSONArray("indicator_value");
	    List<Pair<Number,Double>> list = new ArrayList<Pair<Number,Double>>();
	    for(int i = 0; i < array.length(); i++){
	    	Pair<Number,Double> pair = new Pair<Number,Double>(array.getJSONArray(i).getInt(2), array.getJSONArray(i).getDouble(3));
	    	list.add(pair);
	    }
	    Data data = new Data(country,name.replaceAll(",",""),list);
	    return data;
		
	}
	/**
	 * gets data for one country
	 * @param code - country + dataset code
	 * @param database - database code eg WWDI
	 * @return Data object for a particular country
	 */
	private Data getCountryData(String code,String database) throws URISyntaxException, MalformedURLException, JSONException, IOException{
		
		URI uri = new URI("https://www.quandl.com/api/v3/datasets/" + database + code + ".json?api_key=D7cJ-Vtscyg82BSaaJ7q");
	    JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
	    JSONObject root = new JSONObject(tokener);
	    JSONObject root2 = root.getJSONObject("dataset");
	      
	    String title = (String)root2.get("name");
	    String[] t = title.split(" - ");
	    String name = t[0];
	    String country = t[1];
	    if(country.equals("UK")){
	    	country = "United Kingdom";
	    }
	    if(country.equals("USA")){
	    	country = "United States";
	    }
	    if(country.equals("Russia")){
	    	country = "Russian Federation";
	    }
	    if(country.equals("Korea, Rep.")){
	    	country = "Korea";
	    }
	    JSONArray array = root2.getJSONArray("data");
	    List<Pair<Number,Double>> list = new ArrayList<Pair<Number,Double>>();
	    if(code.substring(4, code.length()).equals("NV_SRV_TETC_ZS") ||
	    		code.substring(4, code.length()).equals("NV_AGR_TOTL_ZS") ||
	    		code.substring(4, code.length()).equals("NV_IND_TOTL_ZS")){
		    	String s = ((String)array.getJSONArray(0).get(0));
		    	String y = s.substring(0, Math.min(s.length(), 4));
		    	int year = Integer.parseInt(y);
		    	Pair<Number,Double> pair = new Pair<Number,Double>(year,((Double)array.getJSONArray(0).get(1)));
		    	list.add(pair);
	    }
	    else{
		    for(int i = 0; i < array.length(); i++){
		    	String s = ((String)array.getJSONArray(i).get(0));
		    	String y = s.substring(0, Math.min(s.length(), 4));
		    	int year = Integer.parseInt(y);
		    	Pair<Number,Double> pair = new Pair<Number,Double>(year,((Double)array.getJSONArray(i).get(1)));
		    	list.add(pair);
		    }
	    }

	    Data data = new Data(country,name.replaceAll(",",""),list);
	    return data;
		
	}
	
	/**
	 * Accesses api and stores required data to API_data.csv
	 */
	private void getDataFromAPI() throws MalformedURLException, JSONException, URISyntaxException, IOException {
		System.out.println("Accessing API");
		ArrayList<Data> names = new ArrayList<Data>();
		for(String code: codes){
			ArrayList<Data> countries = getAllData(code);
			for(Data country:countries){
				names.add(country);
			}
		}
		ArrayList<Data> countries3 = getAllDataHDI();
		for(Data country:countries3){
			names.add(country);
		}
		FileWriter fileWriter;
		try {
			File apiData = new File("API_Data.csv");
			if(apiData.exists()){
				apiData.delete();
			}
			fileWriter = new FileWriter("API_data.csv");
			fileWriter.append("COUNTRY,NAME,VALUES\n");

			for(Data data: names){
				fileWriter.append(data.getCountry()+",");
				fileWriter.append(data.getName()+",");
				List<Pair<Number,Double>> values = data.getList();
				for(Pair<Number,Double> pair: values){
					fileWriter.append(pair.getL()+","+ pair.getR()+",");
				}
				fileWriter.append("\n");
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads API_data.csv and stores in ArrayLists
	 */
	private void retrieveDataFromFile() throws MalformedURLException, JSONException, URISyntaxException, IOException{
		File apiData = new File("API_data.csv");
		if(!apiData.exists()){
			System.out.println("File Does Not Exist");
			getDataFromAPI();
			apiData = new File("API_data.csv");
		}
		gdp_const = new ArrayList<Data>();
		gdp_growth = new ArrayList<Data>();
		gdp_pc = new ArrayList<Data>();
		cpi = new ArrayList<Data>();
		unemployment = new ArrayList<Data>();
		cab = new ArrayList<Data>();
		trade = new ArrayList<Data>();
		inflation = new ArrayList<Data>();
		services = new ArrayList<Data>();
		agriculture = new ArrayList<Data>();
		industry = new ArrayList<Data>();
		hdi = new ArrayList<Data>();
		
		BufferedReader in = new BufferedReader(new FileReader(apiData));
		String ln = in.readLine();
		ln = in.readLine();
		while(ln != null && !ln.equals("COUNTRY,NAME,VALUES")){
			
			String[] values = ln.split(",");
			String country;
			String name;

			country = values[0];
			name = values[1];
			
			List<Pair<Number,Double>> value = new ArrayList<Pair<Number,Double>>();
			if(name.equals("HDI: Human development index (HDIg) value")){
				value.add(new Pair<Number,Double>(Integer.parseInt(values[2]),Double.parseDouble(values[3])));
			}else{
				for(int i = 2; i<values.length-1; i+=2){
					value.add(new Pair<Number,Double>(Integer.parseInt(values[i]),Double.parseDouble(values[i+1])));
				}
			}
			switch(name){
			case "GDP (current US$)":
				gdp_const.add(new Data(country, name, value));
				break;
			case "GDP growth (annual %)":
				gdp_growth.add(new Data(country, name, value));
				break;
			case "GDP per capita (current US$)":
				gdp_pc.add(new Data(country, name, value));
				break;
			case "Consumer price index (2005 = 100)":
				cpi.add(new Data(country, name, value));
				break;
			case "Unemployment total (% of total labor force)":
				unemployment.add(new Data(country, name, value));
				break;
			case "Current account balance (% of GDP)":
				cab.add(new Data(country, name, value));
				break;
			case "Trade (% of GDP)":
				trade.add(new Data(country, name, value));
				break;
			case "Inflation YOY":
				inflation.add(new Data(country, name, value));
				break;
			case "Services etc. value added (% of GDP)":
				services.add(new Data(country, name, value));
				break;
			case "Agriculture value added (% of GDP)":
				agriculture.add(new Data(country, name, value));
				break;
			case "Industry value added (% of GDP)":
				industry.add(new Data(country, name, value));
				break;
			case "HDI: Human development index (HDIg) value":
				hdi.add(new Data(country, name, value));
				break;
			case "Inflation consumer prices (annual %)":
				inflation.add(new Data(country, name, value));
				break;
				
			}
			ln = in.readLine();
		}
		in.close();
	}
	
	
	/**
	 * gets Data object for a country for GDP
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getGDP_const_c(String c){
		for(Data d: gdp_const){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for GDP growth
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getGDP_g_c(String c){
		for(Data d: gdp_growth){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for GDP per capita
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getGDP_pc_c(String c){
		for(Data d: gdp_pc){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for CPI
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getCPI_c(String c){
		for(Data d: cpi){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for Unemployment
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getUnemployment_c(String c){
		for(Data d: unemployment){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for CAB
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getCAB_c(String c){
		for(Data d: cab){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for Trade
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getTrade_c(String c){
		for(Data d: trade){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for Inflation
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getInflation_c(String c){
		for(Data d: inflation){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for Services
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getServices_c(String c){
		for(Data d: services){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for Agriculture
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getAgriculture_c(String c){
		for(Data d: agriculture){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for Industry
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getIndustry_c(String c){
		for(Data d: industry){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	/**
	 * gets Data object for a country for HDI
	 * @param c - country name
	 * @return Data object for a country
	 */
	public Data getHDI_c(String c){
		for(Data d: hdi){
			if((d.getCountry()).equals(c)) {
				return d;
			}	
		}
		return null;
	}
	
}
