package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "0f56e3caf2bd40098043d590b51b317e";

	public void process(NewsApi newsApi) throws NewsApiException {
		System.out.println("Start process");

		//TODO implement Error handling

		ShowNews showNews = new ShowNews(newsApi);
		showNews.show();

		//TODO implement methods for analysis

		Analyser analyser = new Analyser(newsApi);



		//articles.stream().forEach(author -> System.out.println(author.toString()));
		//articles.stream().forEach(article -> System.out.println(article.getAuthor()));  //gets me all authors




		//Analysing the most featured Publisher



		System.out.println("Most featured publisher: "+analyser.getTopPublisher());
		System.out.println("Number of Articles: "+analyser.getArticleCnt());
		//System.out.println("Shortest Author is: "+analyser.getShortestAuthorName());
		System.out.println("End process");
	}
	

	public Object getData() {
		
		return null;
	}
}
