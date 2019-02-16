package newshub.news.myapp.com.newshub;

import java.util.List;

/**
 * Created by Sweety on 13-02-2019.
 */

public class TechResponse {
    @Override
    public String toString() {
        return "TechResponse{" +
                "status='" + status + '\'' +
                ", sourcesList=" + sources +
                '}';
    }

    private String status;
    private List<Sources> sources ;

    public TechResponse() {
    }

    public List<Sources> getSourcesList() {
        return sources;
    }

    public void setSourcesList(List<Sources> sourcesList) {
        this.sources = sourcesList;
    }


   
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
