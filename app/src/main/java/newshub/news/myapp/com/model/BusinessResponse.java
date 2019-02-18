package newshub.news.myapp.com.model;

import java.util.List;

import newshub.news.myapp.com.newshub.Business;

/**
 * Created by Sweety on 16-02-2019.
 */

public class BusinessResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<BusinessModel> getSources() {
        return sources;
    }

    public void setSources(List<BusinessModel> sources) {
        this.sources = sources;
    }

    private List<BusinessModel> sources;
}
