package newsanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import newsanalyzer.ctrl.Controller;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.NewsApiException;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;
import newsapi.enums.Language;
import newsdownloader.NewsDownloaderException;

public class UserInterface 
{

	private Controller ctrl = new Controller();
	private NewsApi latestNewsApi;

	public void getDataFromCtrl1(){
		System.out.println("Aktuelle Tech-News weltweit");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				//.setSourceCountry()
				.setPageSize("30")
				.setSourceCategory(Category.technology)
				.setLanguage(Language.en)
				.createNewsApi();
		latestNewsApi = newsApi;
		try {
			ctrl.process(newsApi);
		}catch (NewsApiException e){
			System.out.println("An error occurred! " + e.getMessage());
		}
	}

	public void getDataFromCtrl2(){
		System.out.println("Aktuelle Sport-News aus Österreich");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey("0f56e3caf2bd40098043d590b51b317e")
				.setQ("")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setLanguage(Language.de)
				.setPageSize("30")
				.setSourceCategory(Category.sports)
				.createNewsApi();
		latestNewsApi = newsApi;
		try {
			ctrl.process(newsApi);
		}catch (NewsApiException e){
			System.out.println("An error occurred! " + e.getMessage());
		}
	}

	public void getDataFromCtrl3(){
		System.out.println("Aktuelle Apple-News weltweit");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey("0f56e3caf2bd40098043d590b51b317e")
				.setQ("apple")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				//.setSourceCountry()
				.setLanguage(Language.en)
				.setPageSize("30")
				.setSourceCategory(Category.technology)
				.createNewsApi();
		latestNewsApi = newsApi;
		try {
			ctrl.process(newsApi);
		}catch (NewsApiException e){
			System.out.println("An error occurred! " + e.getMessage());
		}

	}

	public void downloadLastSearch(){
		if(latestNewsApi != null) {
			try {
				ctrl.htmlDownloader(latestNewsApi);
			} catch (NewsApiException | NewsDownloaderException e) {
				System.out.println("An error occurred! " + e.getMessage());
			}
		}else{
			System.out.println("First search for a Topic!");
		}
	}
	
	public void getDataForCustomInput() {
		
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Aktuelle Tech-News weltweit", this::getDataFromCtrl1);
		menu.insert("b", "Aktuelle Sport-News aus Österreich", this::getDataFromCtrl2);
		menu.insert("c", "Aktuelle Apple-News weltweit", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input: currently unavailable",this::getDataForCustomInput);
		menu.insert("e", "Download last search", this::downloadLastSearch);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}
