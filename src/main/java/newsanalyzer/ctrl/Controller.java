package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsdownloader.SequentialDownloader;

import java.nio.file.Path;
import java.util.*;

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
		System.out.println("Shortest Author is: "+analyser.getShortestAuthorName());
		//System.out.println("Article-Titles sorted alphabetically: "+analyser.sortedTitles());
		System.out.println("End process");
	}

	public void htmlDownloader(NewsApi newsApi) throws NewsApiException {
		NewsReponse newsResponse = newsApi.getNews();
		List<Article> articles = null;
		List<String> finalUrls = new ArrayList<>();

		if (newsResponse != null) {				//get article URLs
			articles = newsResponse.getArticles();
			articles.stream().forEach(article -> finalUrls.add(article.getUrl()));

		} else {
			throw new NewsApiException("Error in Controller");
		}

		List<String> filteredUrls = new ArrayList<>();
		for(String s : finalUrls)
		{
			 filteredUrls.add(s.replaceAll("[^A-Za-z\\d()\\[\\]\\\\\\.]", ""));

		}

		SequentialDownloader sequentialDownloader = new SequentialDownloader();
		int urlsdownloaded = sequentialDownloader.process(finalUrls);

		System.out.println("\nNumber of Websites downloaded: " + urlsdownloaded);

	}
	

	public Object getData() {
		
		return null;
	}
}
