package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "0f56e3caf2bd40098043d590b51b317e";

	public void process(NewsApi newsApi) {
		System.out.println("Start process");

		//TODO implement Error handling

		NewsReponse newsResponse = newsApi.getNews();

		List<Article> articles = null;

		if (newsResponse != null) {
			articles = newsResponse.getArticles();
			//articles.stream().forEach(article -> System.out.println(article.toString()));
		} else {
			System.out.println("Error in Controller");
		}

		long article_count = articles.stream().count();

		List<String> authors = new ArrayList<String>();

		//articles.stream().forEach(author -> System.out.println(author.toString()));
		articles.stream().forEach(article -> System.out.println(article.getAuthor()));  //gets me all authors
		articles.stream().forEach(article -> authors.add(article.getAuthor()));

		Set<String> unique = new HashSet<String>(authors);

		for (String key : unique) {
			System.out.println(key + ": " + Collections.frequency(authors, key));
		}


		//TODO implement methods for analysis

		System.out.println("Number of Articles: "+ article_count);
		System.out.println("End process");
	}
	

	public Object getData() {
		
		return null;
	}
}
