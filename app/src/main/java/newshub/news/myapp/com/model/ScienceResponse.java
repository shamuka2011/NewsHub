package newshub.news.myapp.com.model;

import java.util.List;

/**
 * Created by Sweety on 16-02-2019.
 */

public class ScienceResponse {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ScienceModel> getSources() {
        return sources;
    }

    public void setSources(List<ScienceModel> sources) {
        this.sources = sources;
    }

    private String status;
    private List<ScienceModel> sources;
}
