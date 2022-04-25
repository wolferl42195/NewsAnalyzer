package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;

import java.util.*;

public class Analyser {
    private List<Article> articles;
    private NewsReponse newsResponse;
    NewsApi newsApi;



    public Analyser(NewsApi newsApi) throws NewsApiException {
        this.newsApi = newsApi;
        newsResponse = newsApi.getNews();
        if (newsResponse != null) {
			articles = newsResponse.getArticles();
		} else {
			throw new NewsApiException("Error in Controller");
		}
    }

    public long getArticleCnt(){
        long article_count = articles.stream().count();
        return article_count;
    }

    public String getTopPublisher(){
        List<String> publisher = new ArrayList<String>();
        articles.stream().forEach(article -> publisher.add(article.getSource().getName()));

        Set<String> unique = new HashSet<String>(publisher);
        String topPublisher = null;
        int topPublishernum = 0;

        for (String key : unique) {

            if(topPublishernum < Collections.frequency(publisher, key)){        //checks how often a publisher is featured
                topPublisher = key;
                topPublishernum = Collections.frequency(publisher, key);
            }
        }
        return topPublisher;
    }


    public String getShortestAuthorName() throws NewsApiException {
        List<Article> articles = newsResponse.getArticles();
            Article shortestAuthor = articles.stream()
                    .filter(au -> au.getAuthor() != null)
                    .sorted(Comparator.comparingInt(value -> value.getAuthor().length()))
                    .findFirst()
                    .orElse(new Article());
        return shortestAuthor.toString();
    }

}
