package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import model.Lists;
import model.Data;
import model.Pair;

public class ListsTest {
	static Lists lists;
	/**
	 * Initialises lists before all tests
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lists = new Lists();
	}
	/**
	 * Deconstructs lists after all tests
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		lists = null;
	}
	/**
	 * Tests constructor without pre-existing API_Data.csv file
	 */
	@Test
	public void testLists() {
		File apiData = new File("API_Data.csv");
		if(apiData.exists()){
			apiData.delete();
		}
		try {
			lists = new Lists();
		} catch (JSONException | URISyntaxException | IOException e) {
			assertFalse(true);
		}
		assertTrue(true);
	}
	/**
	 * Tests getGDP_g_c for two random values & countries
	 */
	@Test
	public void testGetGDP_g_c() {
		Data uk = lists.getGDP_g_c("United Kingdom");
		boolean ukTrue = false;
		for(Pair<Number,Double> p: uk.getList()){
			if(p.getL().equals(2015) && p.getR().equals(2.3291833209384)){
				ukTrue = true;
				break;
			}
		}
		assertTrue(ukTrue);
		
		Data germany = lists.getGDP_g_c("Germany");
		boolean germanyTrue = false;
		for(Pair<Number,Double> p: germany.getList()){
			if(p.getL().equals(2012) && p.getR().equals(0.40517067514401)){
				germanyTrue = true;
				break;
			}
		}
		assertTrue(germanyTrue);
	}
	
	/**
	 * Tests getGDP_g_c for two random values & countries
	 */
	@Test
	public void testGetGDP_const_c() {
		Data italy = lists.getGDP_const_c("Italy");
		boolean italyTrue = false;
		for(Pair<Number,Double> p: italy.getList()){
			if(p.getL().equals(2014) && p.getR().equals(2.1385409092111E12)){
				italyTrue = true;
				break;
			}
		}
		assertTrue(italyTrue);
		
		Data switzerland = lists.getGDP_const_c("Switzerland");
		boolean switzerlandTrue = false;
		for(Pair<Number,Double> p: switzerland.getList()){
			if(p.getL().equals(2008) && p.getR().equals(5.5154696269966E11)){
				switzerlandTrue = true;
				break;
			}
		}
		assertTrue(switzerlandTrue);
	}
	
	/**
	 * Tests getGDP_pc_c for two random values & countries
	 */
	@Test
	public void testGetGDP_pc_c() {
		Data france = lists.getGDP_pc_c("France");
		boolean franceTrue = false;
		for(Pair<Number,Double> p: france.getList()){
			if(p.getL().equals(2011) && p.getR().equals(43807.475903241)){
				franceTrue = true;
				break;
			}
		}
		assertTrue(franceTrue);
		
		Data saudi = lists.getGDP_pc_c("Saudi Arabia");
		boolean saudiTrue = false;
		for(Pair<Number,Double> p: saudi.getList()){
			if(p.getL().equals(2015) && p.getR().equals(20481.745322048)){
				saudiTrue = true;
				break;
			}
		}
		assertTrue(saudiTrue);
	}
	
	/**
	 * Tests getCPI_c for random existing value & non existent country
	 */
	@Test
	public void testGetCPI_c() {
		Data india = lists.getCPI_c("India");
		boolean indiaTrue = false;
		for(Pair<Number,Double> p: india.getList()){
			if(p.getL().equals(2006) && p.getR().equals(69.873662370145)){
				indiaTrue = true;
				break;
			}
		}
		assertTrue(indiaTrue);
		
		Data random = lists.getCPI_c("Random Country");
		assertNull(random);
	}
	
	/**
	 * Tests getUnemployment_c for year and value which should not exist
	 */
	@Test
	public void testGetUnemployment_c() {
		Data turkey = lists.getUnemployment_c("Turkey");
		boolean turkeyTrue = false;
		for(Pair<Number,Double> p: turkey.getList()){
			if(p.getL().equals(2013) && p.getR().equals(8.6999998092651)){
				turkeyTrue = true;
				break;
			}
		}
		assertTrue(turkeyTrue);
		
		turkeyTrue = true;
		for(Pair<Number,Double> p: turkey.getList()){
			if(p.getL().equals(2020)){
				turkeyTrue = false;
				break;
			}
		}
		assertTrue(turkeyTrue);
	}
	
	/**
	 * Tests the rest of the getter methods
	 */
	@Test
	public void testTheRest() {
		Data cab = lists.getCAB_c("United Kingdom");
		Data trade = lists.getTrade_c("United Kingdom");
		Data inflation = lists.getInflation_c("United Kingdom");
		Data services = lists.getServices_c("United Kingdom");
		Data agriculture = lists.getAgriculture_c("United Kingdom");
		Data industry = lists.getIndustry_c("United Kingdom");

		Data hdi = lists.getHDI_c("United Kingdom");
		
		assertNotNull(cab);
		assertNotNull(trade);
		assertNotNull(services);
		assertNotNull(agriculture);
		assertNotNull(inflation);
		assertNotNull(industry);
		assertNotNull(hdi);
	}

}
