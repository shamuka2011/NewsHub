package newshub.news.myapp.com.model;

import java.util.List;

/**
 * Created by Sweety on 14-02-2019.
 */

public class SportsResponse {
    private  String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SportsModel> getSources() {
        return sources;
    }

    public void setSources(List<SportsModel> sources) {
        this.sources = sources;
    }

    private List<SportsModel> sources;
}
