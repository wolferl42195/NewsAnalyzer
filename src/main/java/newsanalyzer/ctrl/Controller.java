package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsreader.downloader.ParallelDownloader;
import newsreader.downloader.SequentialDownloader;
import newsreader.downloader.UrlException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "c808e079b93d47df95a84133c3102675";

	public void downloadUrlToList(NewsApi news) throws IOException, NewsAnalyserException, BuildURLException, UrlException {
		NewsReponse newsReponse = news.getNews();

		if(newsReponse  != null) {

			List<Article> articles = newsReponse.getArticles();
			articles.forEach(article -> System.out.println(article.toString()));

			var urls = articles.stream()
					.map(Article::getUrl)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());

			SequentialDownloader seqDwnld = new SequentialDownloader();
			ParallelDownloader paraDwnld = new ParallelDownloader();
			seqDwnld.process(urls);
			paraDwnld.process(urls);
		}
	}

	public void process(NewsApi news) throws IOException, NewsAnalyserException, BuildURLException {
		System.out.println("Start process");

		NewsReponse newsResponse = news.getNews();
		if(newsResponse != null) {
			List<Article> articles = newsResponse.getArticles();
			articles.forEach(article -> System.out.println(article.toString()));

			System.out.println("Title Sorted by Length");
			getTitlesSortedByLength(articles).forEach(article -> System.out.println(article.getTitle()));

			System.out.println("Sorted by Author");
			getShortestAuthorName(articles).forEach(article -> System.out.println(article.getAuthor()));

			System.out.println("Count of Article");
			System.out.println(getNumberofArticle(articles));

			System.out.println("Best Provider");
			System.out.println(getBestProvider(articles));
		}

		//TODO implement Error handling

		//TODO load the news based on the parameters

		//TODO implement methods for analysis

		System.out.println("End process");
	}

	public  List<Article> getTitlesSortedByLength(List<Article> data){
		return data
				.stream()
				.sorted(Comparator.comparing(Article::getTitle))
				.collect(Collectors.toList());
	}

	public List<Article> getShortestAuthorName(List <Article> data) {
		return data
				.stream()
				.filter(article -> Objects.nonNull(article.getAuthor()))
				.sorted(Comparator.comparing(Article::getAuthor))
				.collect(Collectors.toList());
	}

	public Long getNumberofArticle(List<Article> data) {
		return (long) data.size();
	}

	public String getBestProvider(List<Article> data) {
		return data
				.stream()
				.collect(Collectors.groupingBy(article->article.getSource().getName(),Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue()).orElseThrow(NoSuchElementException::new).getKey();
	}

	public Object getData(NewsApi newsApi)  {

		return null;
	}
}
