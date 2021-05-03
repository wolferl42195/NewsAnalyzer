package newsapi;

import newsanalyzer.ctrl.BuildURLException;
import newsanalyzer.ctrl.NewsAnalyserException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class NewsAPIExample {

    public static final String APIKEY = "c808e079b93d47df95a84133c3102675";

    public static void main(String[] args) throws IOException, NewsAnalyserException, BuildURLException {

        NewsApi newsApi = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("corona")
                .setEndPoint(Endpoint.TOP_HEADLINES)
                .setSourceCountry(Country.at)
                .setFrom("2021-04-20")
                .setSourceCategory(Category.health)
                .createNewsApi();

            NewsReponse newsResponse = newsApi.getNews();
            if(newsResponse != null){
                List<Article> articles = newsResponse.getArticles();
                articles.forEach(article -> System.out.println(article.toString()));
            }

        newsApi = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("corona")
                .setEndPoint(Endpoint.EVERYTHING)
                .setFrom("2020-03-20")
                .setExcludeDomains("Lifehacker.com")
                .createNewsApi();

            newsResponse = newsApi.getNews();
        if(newsResponse != null){
            List<Article> articles = newsResponse.getArticles();
            articles.stream().forEach(article -> System.out.println(article.toString()));
        }


    }
}
