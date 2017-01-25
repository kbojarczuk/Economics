package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Data;
import model.Lists;
import model.Pair;
import view.Editor;

public class GUIController implements Initializable, ControllerInterface{

	@FXML ComboBox<String> indicatorList; @FXML Button search;

	@FXML LineChart<Number,Double> LineChart; @FXML NumberAxis year; @FXML NumberAxis value;
	@FXML LineChart<Number,Double> LineChart1; @FXML NumberAxis year1; @FXML NumberAxis value1;
	@FXML LineChart<Number,Double> LineChart2; @FXML NumberAxis year2; @FXML NumberAxis value2;
	@FXML StackedAreaChart<Number, Double>  sac3; @FXML NumberAxis year3; @FXML NumberAxis value3;
	@FXML LineChart<Number,Double> LineChart4; @FXML NumberAxis year4; @FXML NumberAxis value4;
	@FXML LineChart<Number,Double> LineChart5; @FXML NumberAxis year5; @FXML NumberAxis value5;
	@FXML LineChart<Number,Double> LineChart6; @FXML NumberAxis year6; @FXML NumberAxis value6;
	@FXML BarChart<Double,String> bc; @FXML NumberAxis xAxis; @FXML CategoryAxis yAxis;
	@FXML StackedBarChart<String,Double> sbc; @FXML CategoryAxis xAxis1; @FXML NumberAxis yAxis1;
	@FXML PieChart pc;

	@FXML CheckBox argentina; @FXML CheckBox australia; @FXML CheckBox brazil; @FXML CheckBox canada;
	@FXML CheckBox china; @FXML CheckBox france; @FXML CheckBox germany; @FXML CheckBox india; @FXML CheckBox italy;
	@FXML CheckBox japan; @FXML CheckBox korea; @FXML CheckBox russia; @FXML CheckBox saudi;
    @FXML CheckBox switzerland; @FXML CheckBox turkey; @FXML CheckBox uk; @FXML CheckBox usa;

    @FXML ImageView logo;  @FXML ImageView icon; @FXML ImageView notes; @FXML TextArea ta;
    @FXML TabPane tp; @FXML Tab t; @FXML Tab t1; @FXML Tab t2; @FXML Tab t3; @FXML Tab t4;
    @FXML Tab t5; @FXML Tab t6; @FXML Tab t7; @FXML Tab t8; @FXML Tab t9;

	ArrayList<CheckBox> countryList;
	Lists lists;
	private Editor editor;
	private Stage editorStage;
	private String currentEditorFile;
	private FileChooser fileChooser;

	String gdpText = "Gross domestic product (GDP) is the monetary value of all the finished goods and services produced within a country's borders in a specific time period.";
	String gdpGrowthText = "The GDP growth rate measures how fast the economy is growing. It does this by comparing one quarter of the country's economic output (Gross Domestic Product) to the last.";
	String gdpPerCapitaText = "Per Capita GDP is a measure of the total output of a country that takes the GDP and divides it by its population, GDP per capita is a measure of average income per person in a country.";
	String cpiText = "CPI is a measure of changes in the purchasing power of  a currency and the rate of inflation. The CPI index examines the weighted average of prices of a basket of consumer goods and services." ;
	String unemploymentText = "The unemployment rate is calculated as a percentage by dividing the number of unemployed individuals by all individuals currently in the labor force.";
	String cabText = "The current account is defined as the sum of the balance of trade (goods and services exports less imports), net income from abroad and net current transfers.";
	String inflationText = "Inflation as measured by the consumer price index reflects the annual percentage change in the cost to the average consumer of acquiring a basket of goods and services.";
	String hdiText = "The Human Development Index (HDI) is a composite statistic of life expectancy, education, and per capita income indicators, which are used to rank countries into four tiers of human development.";
	String shareOfGdpText = "The distribution gives the percentage contribution of agriculture, industry, and services to total GDP, and will total 100 percent of GDP if the data are complete.";
	String tradeText = "Trade is the sum of exports and imports of goods and services measured as a share of GDP. The trade-to-GDP ratio is an indicator of the relative importance of international trade in the economy of a country. ";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    
		setList();
		setIndicatorList();
		formatGraphs();
		listActionListener();
		tabActionListener();
		notesIconActionListener();
		updateButtonActionListener();

		try {
			lists = new Lists();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		currentEditorFile = "notes.html";
		File fileCreator = new File(currentEditorFile);
		try
		{
			fileCreator.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		editor = new Editor(this);
		editorStage = new Stage();
		editorStage.setScene(new Scene(editor));
		fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files (*.txt, *.html)", "*.txt", "*.html"));
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(currentEditorFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sBuilder = new StringBuilder();
        String line = null;
        try {
            line = reader.readLine();
            while (line != null) {
                sBuilder.append(line);

                line = reader.readLine();

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        editor.setText(sBuilder.toString());
	}
	
	/**
	 * Adds each Check box representing a country to an ArrayList.
	 */
	public void setList(){
		countryList = new ArrayList<CheckBox>();
		countryList.add(argentina); countryList.add(australia); countryList.add(brazil); countryList.add(canada);
		countryList.add(china); countryList.add(france); countryList.add(germany); countryList.add(india);
		countryList.add(italy); countryList.add(japan); countryList.add(korea); countryList.add(russia);
		countryList.add(saudi); countryList.add(switzerland); countryList.add(turkey);
		countryList.add(uk); countryList.add(usa);
	}
	
	/**
	 * Sets the initial displayed text of the ComboBox and adds to it the collection of economic indicators.
	 */
	public void setIndicatorList(){
		indicatorList.setPromptText("Economic Indicators");
		indicatorList.getItems().addAll("GDP", "GDP Growth", "GDP per capita", "Consumer price index (CPI)", "Unemployment Rate", "Current account balance (% of GDP)", "Inflation", "Human Development Index (HDI)", "Share of GDP by Sector", "Trade % of GDP");
	}

	/**
	 * Makes the text area visible and sets its text to input.
	 * @param input - the definition of the Economic Indicator.
	 */
	private void setText (String input) {
		ta.setDisable(false);
    	ta.setOpacity(1.0);
    	ta.setWrapText(true);

    	if(indicatorList.getValue() == null){ }

    	else if(input.equals("GDP"))
			ta.setText(gdpText);
		else if(input.equals("GDP Growth"))
			ta.setText(gdpGrowthText);
		else if(input.equals("GDP per capita"))
			ta.setText(gdpPerCapitaText);
		else if(input.equals("Consumer price index (CPI)"))
			ta.setText(cpiText);
		else if(input.equals("Unemployment Rate"))
			ta.setText(unemploymentText);
		else if(input.equals("Current account balance (% of GDP)"))
			ta.setText(cabText);
		else if(input.equals("Inflation"))
			ta.setText(inflationText);
		else if(input.equals("Human Development Index (HDI)"))
			ta.setText(hdiText);
		else if(indicatorList.getValue().equals("Share of GDP by Sector"))
			ta.setText(shareOfGdpText);
		else if(indicatorList.getValue().equals("Trade % of GDP"))
			ta.setText(tradeText);
	}

	/**
	 * @return an ArrayList with only those countries that the user has selected.
	 */
	public ArrayList<CheckBox> getSelectedList(){
 
		ArrayList<CheckBox> selectedCountries = new ArrayList<CheckBox>();
		for(int i = 0; i < countryList.size(); i++)
			if(countryList.get(i).isSelected() == true)
				selectedCountries.add(countryList.get(i));
		return selectedCountries;
	}
	
    /**
     * Formats LineCharts that have a year value to display the number on the X-axis without a comma.
     */
    public void formatGraphs(){
		((ValueAxis<Number>) LineChart.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
	        @Override
	        public String toString(Number object) {
	            return (object.intValue()) + "";
	        }

	        @Override
	        public Number fromString(String string) {
	            return 0;
	        }
	    });
		((ValueAxis<Number>) LineChart1.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
	        @Override
	        public String toString(Number object) {
	            return (object.intValue()) + "";
	        }

	        @Override
	        public Number fromString(String string) {
	            return 0;
	        }
	    });
		((ValueAxis<Number>) LineChart2.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
	        @Override
	        public String toString(Number object) {
	            return (object.intValue()) + "";
	        }

	        @Override
	        public Number fromString(String string) {
	            return 0;
	        }
	    });
		((ValueAxis<Number>) sac3.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
	        @Override
	        public String toString(Number object) {
	            return (object.intValue()) + "";
	        }

	        @Override
	        public Number fromString(String string) {
	            return 0;
	        }
	    });
		((ValueAxis<Number>) LineChart4.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
	        @Override
	        public String toString(Number object) {
	            return (object.intValue()) + "";
	        }

	        @Override
	        public Number fromString(String string) {
	            return 0;
	        }
	    });
		((ValueAxis<Number>) LineChart5.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
	        @Override
	        public String toString(Number object) {
	            return (object.intValue()) + "";
	        }

	        @Override
	        public Number fromString(String string) {
	            return 0;
	        }
	    });
		((ValueAxis<Number>) LineChart6.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
	        @Override
	        public String toString(Number object) {
	            return (object.intValue()) + "";
	        }

	        @Override
	        public Number fromString(String string) {
	            return 0;
	        }
	    });
    }
	
	/**
	 * Underlines the text on the update button when you hover over it.
	 */
	public void updateButtonActionListener(){
		
        search.setOnMouseEntered(event -> {
        	search.setUnderline(true);
        });
        search.setOnMouseExited(event -> {
        	search.setUnderline(false);
        });
	}
	
	/**
	 * Changes the opacity of the notes image when you hover over it and opens the HTML editor when clicked.
	 */
	public void notesIconActionListener(){
		
        notes.setOnMouseEntered(event -> {
        	notes.setOpacity(1.0);
        });
        notes.setOnMouseExited(event -> {
        	notes.setOpacity(0.5);
        });      
        notes.setOnMouseClicked(event -> {
        	showEditor();
        });
	}

	/**
	 * Defines a function to be called when a selection changed has occurred on the tab.
	 * Updates the info text according to the tab currently selected.
	 */
	public void tabActionListener(){

        t.setOnSelectionChanged(event -> {
        	ta.setText(gdpText);
        });
        t1.setOnSelectionChanged(event -> {
        	ta.setText(gdpGrowthText);
        });
        t2.setOnSelectionChanged(event -> {
        	ta.setText(gdpPerCapitaText);
        });
        t3.setOnSelectionChanged(event -> {
        	ta.setText(cpiText);
        });
        t4.setOnSelectionChanged(event -> {
        	ta.setText(unemploymentText);
        });
        t5.setOnSelectionChanged(event -> {
        	ta.setText(cabText);
        });
        t6.setOnSelectionChanged(event -> {
        	ta.setText(inflationText);
        });
        t7.setOnSelectionChanged(event -> {
        	ta.setText(hdiText);
        });
        t8.setOnSelectionChanged(event -> {
        	ta.setText(shareOfGdpText);
        });
        t9.setOnSelectionChanged(event -> {
        	ta.setText(tradeText);
        });
	}

	/**
	 * This method is invoked whenever the ComboBox value property is changed.
	 * Gets the string value of the ComboBox and changes the tab currently displayed accordingly.
	 */
	public void listActionListener(){

		indicatorList.setOnAction(event -> {
	    String textInput = indicatorList.getValue();

    	if(textInput == null){ }

    	else if(textInput.equals("GDP"))
			tp.getSelectionModel().select(t);
		else if(textInput.equals("GDP Growth"))
			tp.getSelectionModel().select(t1);
		else if(textInput.equals("GDP per capita"))
			tp.getSelectionModel().select(t2);
		else if(textInput.equals("Consumer price index (CPI)"))
			tp.getSelectionModel().select(t3);
		else if(textInput.equals("Unemployment Rate"))
			tp.getSelectionModel().select(t4);
		else if(textInput.equals("Current account balance (% of GDP)"))
			tp.getSelectionModel().select(t5);
		else if(textInput.equals("Inflation"))
			tp.getSelectionModel().select(t6);
		else if(textInput.equals("Human Development Index (HDI)"))
			tp.getSelectionModel().select(t7);
		else if(textInput.equals("Share of GDP by Sector"))
			tp.getSelectionModel().select(t8);
		else if(textInput.equals("Trade % of GDP"))
			tp.getSelectionModel().select(t9);
	    });
		
	}

	/**
	 * @param ae
	 * Updates the data visualisation every time the update button is clicked.
	 * Sets the X-axis and Y-axis labels and creates a series for each country selected.
	 * The series list is then added to the graph to display the data.
	 */
	@FXML
	public void searchActionListener(ActionEvent ae) {
    
	List<Integer> scale = new ArrayList<Integer>();

	if(indicatorList.getValue() == null){ }

	else if(indicatorList.getValue().equals("GDP")){

		setText("GDP");
		tp.getSelectionModel().select(t);
		year.setAutoRanging(false);
		year.setLabel("Year");
		value.setLabel("GDP");
		LineChart.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Number, Double>> seriesList = new ArrayList<Series<Number, Double>>();

		if(selectedCountries.size() == 0){ }

		else{
		for(int j = 0; j < selectedCountries.size(); j++){

		    Data d = lists.getGDP_const_c(selectedCountries.get(j).getText());
			List<Pair<Number,Double>> list = d.getList();
			Series<Number, Double> series = new XYChart.Series<Number, Double>();
			series.setName(d.getCountry());
			LineChart.setTitle(d.getName());

			for(int i = list.size() - 1; i >= 0; i--){
			   series.getData().add(new XYChart.Data<Number, Double>(list.get(i).getL(), list.get(i).getR()));
			   scale.add((Integer)list.get(i).getL());
			}
			seriesList.add(series);
		}
	    Integer min = Collections.min(scale);
	    Integer max = Collections.max(scale);
	    year.setLowerBound(Math.ceil(min/5) * 5);
	    year.setUpperBound(max + 1);
		LineChart.getData().addAll(seriesList);
		}
	}

	else if(indicatorList.getValue().equals("GDP Growth")){

		setText("GDP Growth");
		tp.getSelectionModel().select(t1);
		year1.setAutoRanging(false);
		year1.setLabel("Year");
		value1.setLabel("GDP Growth %");
		LineChart1.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Number, Double>> seriesList = new ArrayList<Series<Number, Double>>();

		if(selectedCountries.size() == 0){ }

		else{
	    for(int j = 0; j < selectedCountries.size(); j++){

		    Data d = lists.getGDP_g_c(selectedCountries.get(j).getText());
			List<Pair<Number,Double>> list = d.getList();
			Series<Number, Double> series = new XYChart.Series<Number, Double>();
			series.setName(d.getCountry());
			LineChart1.setTitle(d.getName());

			for(int i = list.size() - 1; i >= 0; i--){
			   series.getData().add(new XYChart.Data<Number, Double>(list.get(i).getL(), list.get(i).getR()));
			   scale.add((Integer)list.get(i).getL());
			}
			seriesList.add(series);
		}
	    Integer min = Collections.min(scale);
	    Integer max = Collections.max(scale);
	    year1.setLowerBound(Math.ceil(min/5) * 5);
	    year1.setUpperBound(max + 1);
		LineChart1.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();
		}
	}

	else if(indicatorList.getValue().equals("GDP per capita")){

		setText("GDP per capita");
		tp.getSelectionModel().select(t2);
		year2.setAutoRanging(false);
		value2.setLabel("GDP per capita");
		LineChart2.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Number, Double>> seriesList = new ArrayList<Series<Number, Double>>();

		if(selectedCountries.size() == 0){ }

		else{
	    for(int j = 0; j < selectedCountries.size(); j++){

		    Data d = lists.getGDP_pc_c(selectedCountries.get(j).getText());
			List<Pair<Number,Double>> list = d.getList();
			Series<Number, Double> series = new XYChart.Series<Number, Double>();
			series.setName(d.getCountry());
			LineChart2.setTitle(d.getName());

			for(int i = list.size() - 1; i >= 0; i--){
			   series.getData().add(new XYChart.Data<Number, Double>(list.get(i).getL(), list.get(i).getR()));
			   scale.add((Integer)list.get(i).getL());
			}
			seriesList.add(series);
		}
	    Integer min = Collections.min(scale);
	    Integer max = Collections.max(scale);
	    year2.setLowerBound(Math.ceil(min/5) * 5);
	    year2.setUpperBound(max + 1);
		LineChart2.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();
		}
	}

	else if(indicatorList.getValue().equals("Consumer price index (CPI)")){

		setText("Consumer price index (CPI)");
		tp.getSelectionModel().select(t3);
		year3.setAutoRanging(false);
		value3.setLabel("CPI");
		sac3.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Number, Double>> seriesList = new ArrayList<Series<Number, Double>>();

		if(selectedCountries.size() == 0){ }

		else{
	    for(int j = 0; j < selectedCountries.size(); j++){

		    Data d = lists.getCPI_c(selectedCountries.get(j).getText());
			List<Pair<Number,Double>> list = d.getList();
			Series<Number, Double> series = new XYChart.Series<Number, Double>();
			series.setName(d.getCountry());
			sac3.setTitle(d.getName());

			for(int i = list.size() - 1; i >= 0; i--){
			   series.getData().add(new XYChart.Data<Number, Double>(list.get(i).getL(), list.get(i).getR()));
			   scale.add((Integer)list.get(i).getL());
			}
			seriesList.add(series);
		}
	    Integer min = Collections.min(scale);
	    Integer max = Collections.max(scale);
	    year3.setLowerBound(Math.ceil(min/5) * 5);
	    year3.setUpperBound(max + 1);
		sac3.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();
		}
	}

	else if(indicatorList.getValue().equals("Unemployment Rate")){

		setText("Unemployment Rate");
		tp.getSelectionModel().select(t4);
		year4.setAutoRanging(false);
		value4.setLabel("Unemployment rate %");
		LineChart4.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Number, Double>> seriesList = new ArrayList<Series<Number, Double>>();

		if(selectedCountries.size() == 0){ }

		else{
	    for(int j = 0; j < selectedCountries.size(); j++){

		    Data d = lists.getUnemployment_c(selectedCountries.get(j).getText());
			List<Pair<Number,Double>> list = d.getList();
			Series<Number, Double> series = new XYChart.Series<Number, Double>();
			series.setName(d.getCountry());
			LineChart4.setTitle(d.getName());

			for(int i = list.size() - 1; i >= 0; i--){
			   series.getData().add(new XYChart.Data<Number, Double>(list.get(i).getL(), list.get(i).getR()));
			   scale.add((Integer)list.get(i).getL());
			}
			seriesList.add(series);
		}
	    Integer min = Collections.min(scale);
	    Integer max = Collections.max(scale);
	    year4.setLowerBound(Math.ceil(min/5) * 5);
	    year4.setUpperBound(max + 1);
		LineChart4.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();
		}
	}

	else if(indicatorList.getValue().equals("Current account balance (% of GDP)")){

		setText("Current account balance (% of GDP)");
		tp.getSelectionModel().select(t5);
		year5.setAutoRanging(false);
		value5.setLabel("Current account balance %");
		LineChart5.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Number, Double>> seriesList = new ArrayList<Series<Number, Double>>();

		if(selectedCountries.size() == 0){ }

		else{
	    for(int j = 0; j < selectedCountries.size(); j++){

		    Data d = lists.getCAB_c(selectedCountries.get(j).getText());
			List<Pair<Number,Double>> list = d.getList();
			Series<Number, Double> series = new XYChart.Series<Number, Double>();
			series.setName(d.getCountry());
			LineChart5.setTitle(d.getName());

			for(int i = list.size() - 1; i >= 0; i--){
			   series.getData().add(new XYChart.Data<Number, Double>(list.get(i).getL(), list.get(i).getR()));
			   scale.add((Integer)list.get(i).getL());
			}
			seriesList.add(series);
		}
	    Integer min = Collections.min(scale);
	    Integer max = Collections.max(scale);
	    year5.setLowerBound(Math.ceil(min/5) * 5);
	    year5.setUpperBound(max + 1);
		LineChart5.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();
		}
	}

	else if(indicatorList.getValue().equals("Inflation")){

		setText("Inflation");
		tp.getSelectionModel().select(t6);
		year6.setAutoRanging(false);
		value6.setLabel("Inflation");
		LineChart6.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Number, Double>> seriesList = new ArrayList<Series<Number, Double>>();

		if(selectedCountries.size() == 0){ }

		else{
	    for(int j = 0; j < selectedCountries.size(); j++){

		    Data d = lists.getInflation_c(selectedCountries.get(j).getText());
			List<Pair<Number,Double>> list = d.getList();
			Series<Number, Double> series = new XYChart.Series<Number, Double>();
			series.setName(d.getCountry());
			LineChart6.setTitle(d.getName());
			for(int i = list.size() - 1; i >= 0; i--){
			   series.getData().add(new XYChart.Data<Number, Double>(list.get(i).getL(), list.get(i).getR()));
			   scale.add((Integer)list.get(i).getL());
			}
			seriesList.add(series);
		}
	    Integer min = Collections.min(scale);
	    Integer max = Collections.max(scale);
	    year6.setLowerBound(Math.ceil(min/5) * 5);
	    year6.setUpperBound(max + 1);
		LineChart6.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();
		}
	}

	else if(indicatorList.getValue().equals("Human Development Index (HDI)")){

		setText("Human Development Index (HDI)");
	    tp.getSelectionModel().select(t7);
	    xAxis.setLabel("HDI");
	    xAxis.setTickLabelRotation(90);
	    yAxis.setLabel("Country");
	    yAxis.setAnimated(false);
		bc.getData().clear();
		ArrayList<CheckBox> selectedCountries = getSelectedList();
		ArrayList<Series<Double, String>> seriesList = new ArrayList<Series<Double, String>>();

		if(selectedCountries.size() == 0){ }

		else{
		for(int i = 0; i < 3; i++){
			Series<Double, String> series = new XYChart.Series<Double, String>();
			if(i == 0){
				series.setName("2011");
				for(int j = 0; j < selectedCountries.size(); j++){
					Data d = lists.getHDI_c(selectedCountries.get(j).getText());
					List<Pair<Number,Double>> list = d.getList();
					bc.setTitle(d.getName());
					for(int k = 0; k < list.size(); k++)
						series.getData().add(new XYChart.Data<Double, String>(list.get(k).getR(), d.getCountry()));
				}

			}
			if(i == 1){
				series.setName("2012");
				for(int j = 0; j < selectedCountries.size(); j++){
					Data d = lists.getHDI_c(selectedCountries.get(j).getText());
					List<Pair<Number,Double>> list = d.getList();
					for(int k = 0; k < list.size(); k++)
						series.getData().add(new XYChart.Data<Double, String>(list.get(k).getR(), d.getCountry()));
				}
			}
			if(i == 2){
				series.setName("2013");
				for(int j = 0; j < selectedCountries.size(); j++){
					Data d = lists.getHDI_c(selectedCountries.get(j).getText());
					List<Pair<Number,Double>> list = d.getList();
					for(int k = 0; k < list.size(); k++)
						series.getData().add(new XYChart.Data<Double, String>(list.get(k).getR(), d.getCountry()));
				}
			}
			seriesList.add(series);
		}

		bc.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();
		}
     }

	 else if(indicatorList.getValue().equals("Share of GDP by Sector")){

	    setText("Share of GDP by Sector");
        tp.getSelectionModel().select(t8);
 	    xAxis1.setLabel("Country");
 	    yAxis1.setLabel("Share of GDP%");
 	    yAxis1.setAutoRanging(false);
 	    yAxis1.setUpperBound(100.0);
 	    xAxis1.setAnimated(false);
 		sbc.getData().clear();
 		ArrayList<CheckBox> selectedCountries = getSelectedList();
 		ArrayList<Series<String, Double>> seriesList = new ArrayList<Series<String, Double>>();
 		Series<String, Double> series = new XYChart.Series<String, Double>();
 		Series<String, Double> series1 = new XYChart.Series<String, Double>();
 		Series<String, Double> series2 = new XYChart.Series<String, Double>();
 		series.setName("Services");
 		series1.setName("Agriculture");
 		series2.setName("Industry");

        if(selectedCountries.size() == 0){ }

		else{
			for(int j = 0; j < selectedCountries.size(); j++){
				Data d = lists.getServices_c(selectedCountries.get(j).getText());
				List<Pair<Number,Double>> list = d.getList();
				series.getData().add(new XYChart.Data<String, Double>(d.getCountry(), list.get(0).getR()));
			}

			for(int j = 0; j < selectedCountries.size(); j++){
			    Data d1 = lists.getAgriculture_c(selectedCountries.get(j).getText());
			    List<Pair<Number,Double>> list1 = d1.getList();
				series1.getData().add(new XYChart.Data<String, Double>(d1.getCountry(), list1.get(0).getR()));
			}

			for(int j = 0; j < selectedCountries.size(); j++){
			    Data d2 = lists.getIndustry_c(selectedCountries.get(j).getText());
			    List<Pair<Number,Double>> list2 = d2.getList();
				series2.getData().add(new XYChart.Data<String, Double>(d2.getCountry(), list2.get(0).getR()));
			}
			seriesList.add(series);
			seriesList.add(series1);
			seriesList.add(series2);
		}

        sbc.getData().addAll(seriesList);
		seriesList.clear();
		selectedCountries.clear();

     }
	
	 else if(indicatorList.getValue().equals("Trade % of GDP")){
		 
		    setText("Trade % of GDP");
	        tp.getSelectionModel().select(t9);
	        pc.getData().clear();
			ArrayList<CheckBox> selectedCountries = getSelectedList();
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	        
	        for(int j = 0; j < selectedCountries.size(); j++){

			    Data d = lists.getTrade_c(selectedCountries.get(j).getText());
				List<Pair<Number,Double>> list = d.getList();
				pc.setTitle(d.getName());			
			    PieChart.Data ds = new PieChart.Data(d.getCountry(), list.get(0).getR());
			    pieChartData.add(ds);	
			}
	        
	        pc.setData(pieChartData);
			selectedCountries.clear();

	 }
	
   }

	   @Override
	   public void saveButton(String editorString) {

	        if (currentEditorFile != null)
				try {
	                FileWriter writer = new FileWriter(new File(currentEditorFile));
	                writer.write(editor.getText());
	                writer.close();

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	    }

	    @Override
	    public void openButton() {
	        File file = fileChooser.showOpenDialog(editorStage);
	        if (file != null)
	        {
	            currentEditorFile = file.getAbsolutePath();
	            BufferedReader reader = null;
	            try {
	                reader = new BufferedReader(new FileReader(file));
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            }
	            StringBuilder sBuilder = new StringBuilder();
	            String line = null;
	            try {
	                line = reader.readLine();
	                while (line != null) {
	                    sBuilder.append(line);

	                    line = reader.readLine();

	                }
	            }catch (IOException e) {
	                e.printStackTrace();
	            }
	            editor.setText(sBuilder.toString());
	        }

	    }

	    @Override
	    public void saveAsButton(String editorString) {
	        File file = fileChooser.showSaveDialog(editorStage);

	        if (file != null)
				try {
	                FileWriter writer = new FileWriter(file);
	                writer.write(editor.getText());
	                writer.close();

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	    }

	    @Override
	    public void showEditor(){
		    editorStage.show();
	    }

}
