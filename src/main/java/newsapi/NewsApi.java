package newsapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import newsapi.beans.NewsReponse;
import newsapi.enums.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.IllegalFormatException;

public class NewsApi {


    public static final String DELIMITER = "&";

    public static final String NEWS_API_URL = "http://newsapi.org/v2/%s?q=%s&apiKey=%s";

    private Endpoint endpoint;
    private String q;
    private String qInTitle;
    private Country sourceCountry;
    private Category sourceCategory;
    private String domains;
    private String excludeDomains;
    private String from;
    private String to;
    private Language language;
    private SortBy sortBy;
    private String pageSize;
    private String page;
    private String apiKey;

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public String getQ() {
        return q;
    }

    public String getqInTitle() {
        return qInTitle;
    }

    public Country getSourceCountry() {
        return sourceCountry;
    }

    public Category getSourceCategory() {
        return sourceCategory;
    }

    public String getDomains() {
        return domains;
    }

    public String getExcludeDomains() {
        return excludeDomains;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Language getLanguage() {
        return language;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
    }

    public String getApiKey() {
        return apiKey;
    }

    public NewsApi(String q, String qInTitle, Country sourceCountry, Category sourceCategory, String domains, String excludeDomains, String from, String to, Language language, SortBy sortBy, String pageSize, String page, String apiKey, Endpoint endpoint) {
        this.q = q;
        this.qInTitle = qInTitle;
        this.sourceCountry = sourceCountry;
        this.sourceCategory = sourceCategory;
        this.domains = domains;
        this.excludeDomains = excludeDomains;
        this.from = from;
        this.to = to;
        this.language = language;
        this.sortBy = sortBy;
        this.pageSize = pageSize;
        this.page = page;
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    protected String requestData() throws NewsApiException {
        String url = buildURL();
        System.out.println("URL: "+url);
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {

            throw new NewsApiException("A Problem with the URL \""+url+"\" has occurred - "+ e.getMessage());
        }
        HttpURLConnection con;
        StringBuilder response = new StringBuilder();
        BufferedReader in = null;

        try {
            con = (HttpURLConnection) obj.openConnection();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch(UnknownServiceException e){
            throw new NewsApiException("Error UnknownServiceException - There is something wrong with the connection to the URL \""+url+"\" "+e.getMessage());
        } catch (IOException e) {

            throw new NewsApiException("An Error has occurred trying to connect to the URL \""+url+"\" "+e.getMessage());
        }finally {
            try{
                if(in != null){}
                in.close();
            }catch(Exception e){
                throw new NewsApiException("Unable to close the connection to the URL \""+url+"\" "+e.getMessage());
            }
        }
        return response.toString();
    }

    protected String buildURL() throws NewsApiException {

        String urlbase;
        StringBuilder sb = null;

        try {
            urlbase = String.format(NEWS_API_URL, getEndpoint().getValue(), getQ(), getApiKey());
            sb = new StringBuilder(urlbase);
        } catch (IllegalFormatException e) {
            throw new NewsApiException("The given URL does not meet the requirements - " + e.getMessage());
        }


        if (sb != null) {
            if (getFrom() != null) {
                sb.append(DELIMITER).append("from=").append(getFrom());
            }
            if (getTo() != null) {
                sb.append(DELIMITER).append("to=").append(getTo());
            }
            if (getPage() != null) {
                sb.append(DELIMITER).append("page=").append(getPage());
            }
            if (getPageSize() != null) {
                sb.append(DELIMITER).append("pageSize=").append(getPageSize());
            }
            if (getLanguage() != null) {
                sb.append(DELIMITER).append("language=").append(getLanguage());
            }
            if (getSourceCountry() != null) {
                sb.append(DELIMITER).append("country=").append(getSourceCountry());
            }
            if (getSourceCategory() != null) {
                sb.append(DELIMITER).append("category=").append(getSourceCategory());
            }
            if (getDomains() != null) {
                sb.append(DELIMITER).append("domains=").append(getDomains());
            }
            if (getExcludeDomains() != null) {
                sb.append(DELIMITER).append("excludeDomains=").append(getExcludeDomains());
            }
            if (getqInTitle() != null) {
                sb.append(DELIMITER).append("qInTitle=").append(getqInTitle());
            }
            if (getSortBy() != null) {
                sb.append(DELIMITER).append("sortBy=").append(getSortBy());
            }
        }else{
            throw new NewsApiException("The URL could not be built correctly");
        }
        return sb.toString();
        }


    public NewsReponse getNews() throws NewsApiException {
        NewsReponse newsReponse = null;
        String jsonResponse = requestData();
        if(jsonResponse != null && !jsonResponse.isEmpty()){

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                newsReponse = objectMapper.readValue(jsonResponse, NewsReponse.class);
                if(!"ok".equals(newsReponse.getStatus())){
                    System.out.println("The News Status is false"+newsReponse.getStatus());
                }
            } catch (JsonProcessingException e) {
                //System.out.println("Error: "+e.getMessage());
                throw new NewsApiException("Unable to process the Json-File "+e.getMessage());
            }
        }


        if(newsReponse == null){
            throw new NewsApiException("Something went wrong making the newsResponse-File");
        }
        return newsReponse;
    }
}

