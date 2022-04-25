package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;

import java.util.List;

public class Controller {

	public static final String APIKEY = "0f56e3caf2bd40098043d590b51b317e";

	public void process(NewsApi newsApi) {
		System.out.println("Start process");

		//TODO implement Error handling

		NewsReponse newsResponse = newsApi.getNews();

		if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		}

		//TODO implement methods for analysis

		System.out.println("End process");
	}
	

	public Object getData() {
		
		return null;
	}
}
