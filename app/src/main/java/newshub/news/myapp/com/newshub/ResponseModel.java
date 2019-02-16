package newshub.news.myapp.com.newshub;

import java.util.List;

/**
 * Created by Sweety on 07-02-2019.
 */

public class ResponseModel {

    private String status;
    private int totalResults;
    private List<Article> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


}
