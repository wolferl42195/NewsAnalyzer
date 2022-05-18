package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsdownloader.ParallelDownloader;
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

	public void htmlDownloader(NewsApi newsApi) throws NewsApiException {		//get URLS form newsAPI and call the downloader
		NewsReponse newsResponse = newsApi.getNews();
		List<Article> articles = null;
		List<String> finalUrls = new ArrayList<>();

		if (newsResponse != null) {				//get article URLs
			articles = newsResponse.getArticles();
			articles.stream().forEach(article -> finalUrls.add(article.getUrl()));

		} else {
			throw new NewsApiException("Error in Controller");
		}


		SequentialDownloader sequentialDownloader = new SequentialDownloader();
		ParallelDownloader parallelDownloader = new ParallelDownloader();

		long startSeqDownload = System.currentTimeMillis();
		sequentialDownloader.process(finalUrls);
		long finishSeqDownload = System.currentTimeMillis();
		long timeSeqDownload = finishSeqDownload - startSeqDownload;


		long startParDownload = System.currentTimeMillis();
		int urlsDownloaded = sequentialDownloader.process(finalUrls);
		long finishParDownload = System.currentTimeMillis();
		long timeParDownload = finishParDownload - startParDownload;


		System.out.println("\nNumber of Websites downloaded: " + urlsDownloaded);
		System.out.println("\nSequential Download time needed: "+timeSeqDownload+"ms");
		System.out.println("\nParallel Download time needed: "+timeParDownload+"ms");

	}
	

	public Object getData() {
		
		return null;
	}
}
