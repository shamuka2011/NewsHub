package newshub.news.myapp.com.model;

import java.util.List;

/**
 * Created by Sweety on 15-02-2019.
 */

public class GeneralResponse {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeneralModel> getSources() {
        return sources;
    }

    public void setSources(List<GeneralModel> sources) {
        this.sources = sources;
    }

    private  String status;

    private List<GeneralModel> sources;
}
