package newshub.news.myapp.com.model;

import java.util.List;

/**
 * Created by Sweety on 16-02-2019.
 */

public class EntertainmentResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EntertainmentModel> getSources() {
        return sources;
    }

    public void setSources(List<EntertainmentModel> sources) {
        this.sources = sources;
    }

    private List<EntertainmentModel> sources;
}
