package newsanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import newsanalyzer.ctrl.Controller;
import newsanalyzer.ctrl.NewsAnalyserException;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.enums.*;

public class UserInterface 
{
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		System.out.println("Choice Headline: Corona-AT");

		NewsApi news = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("corona")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.health)
				.createNewsApi();
		try {
			ctrl.process(news);
		} catch (MalformedURLException e) {
			System.out.println("URL Stimmt nicht");
		} catch (NewsAnalyserException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Kein Eintrag gefunden");
		}
	}

	public void getDataFromCtrl2(){
		System.out.println("Choice Science NEWS since 23.04.2021");

		NewsApi news = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("NEWS")
				.setFrom("2021-04-23")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCategory(Category.science)
				.createNewsApi();
		try {
			ctrl.process(news);
		} catch (MalformedURLException e) {
			System.out.println("URL Stimmt nicht");
		} catch (NewsAnalyserException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Kein Eintrag gefunden");
		}
	}

	public void getDataFromCtrl3(){
		System.out.println("All");

		NewsApi news = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("")
				.createNewsApi();
		try {
			ctrl.process(news);
		} catch (MalformedURLException e) {
			System.out.println("URL Stimmt nicht");
		} catch (NewsAnalyserException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Kein Eintrag gefunden");
		}
	}
	
	public void getDataForCustomInput() {
		String read = readLine();
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Choice Headline: Corona-AT", this::getDataFromCtrl1);
		menu.insert("b", "Choice Science NEWS since 23.04.2021", this::getDataFromCtrl2);
		menu.insert("c", "Choice Error Test", this::getDataFromCtrl3);
		menu.insert("d", "Not impelemted:",this::getDataForCustomInput);
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
