package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsdownloader.NewsDownloaderException;
import newsdownloader.ParallelDownloader;
import newsdownloader.SequentialDownloader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;

public class Controller {

	public static final String APIKEY = "0f56e3caf2bd40098043d590b51b317e";

	public void process(NewsApi newsApi) throws NewsApiException {
		System.out.println("Start process");


		ShowNews showNews = new ShowNews(newsApi);
		showNews.show();


		Analyser analyser = new Analyser(newsApi);


		System.out.println("Most featured publisher: "+analyser.getTopPublisher());
		System.out.println("Number of Articles: "+analyser.getArticleCnt());
		System.out.println("Shortest Author is: "+analyser.getShortestAuthorName());
		for(Article article : analyser.sortedTitles()){
			System.out.println("Article-Titles sorted alphabetically: "+article.getTitle());
		}

		System.out.println("End process");
	}

	public void htmlDownloader(@NotNull NewsApi newsApi) throws NewsApiException, NewsDownloaderException {		//get URLS form newsAPI and call the downloader
		NewsReponse newsResponse = newsApi.getNews();
		List<Article> articles = null;
		List<String> Urls = new ArrayList<>();

		if (newsResponse != null) {				//get article URLs and put them into a list;
			articles = newsResponse.getArticles();
			articles.stream().forEach(article -> Urls.add(article.getUrl()));

		} else {
			throw new NewsApiException("Error in Controller");
		}


		SequentialDownloader sequentialDownloader = new SequentialDownloader();
		ParallelDownloader parallelDownloader = new ParallelDownloader();


		//call parallel downloader and measure download time needed
		System.out.println("Parallel Download started:");
		long startParDownload = System.currentTimeMillis();
		int urlsDownloaded = parallelDownloader.process(Urls);
		long finishParDownload = System.currentTimeMillis();
		long timeParDownload = finishParDownload - startParDownload;

		//call sequential downloader and measure download time needed
		System.out.println("\nSequential Download started:");
		long startSeqDownload = System.currentTimeMillis();
		sequentialDownloader.process(Urls);
		long finishSeqDownload = System.currentTimeMillis();
		long timeSeqDownload = finishSeqDownload - startSeqDownload;





		System.out.println("\nSequential Download time needed: "+timeSeqDownload+"ms");
		System.out.println("\nParallel Download time needed: "+timeParDownload+"ms");
		System.out.println("\nNumber of Websites downloaded: " + urlsDownloaded);



	}



	public Object getData() {
		
		return null;
	}
}
