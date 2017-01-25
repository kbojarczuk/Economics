package model;

import java.util.List;

public class Data {
	
	private String name;
	private String country;
	private List<Pair<Number,Double>> values;
	/**
	 * Data constructor which stores: 
	 * @param country - country name
	 * @param name - name of the data
	 * @param values - list of year,value pairs to include in the charts
	 */
	public Data(String country,String name,List<Pair<Number,Double>> values ){
		this.country = country;
		this.name = name;
		this.values = values;
	}
	/**
	 * gets name
	 * @return name
	 */
	public String getName(){
		return name;
	}
	/**
	 * gets country
	 * @return country
	 */
	public String getCountry(){
		return country;
	}
	/**
	 * gets the list of values
	 * @return list of values
	 */
	public List<Pair<Number,Double>> getList(){
		return values;
	}
	/**
	 * tostring for printing
	 * @return String to print
	 */
	public String toString(){
		return "C: " + country + " N: " + name + " V: " + values;
	}
	

}
