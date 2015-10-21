package app.newbee.lib.model.gson;

/**
 * Created by cao_ruixiang on 15/8/14.
 */

import java.util.List;

public class Root {
    private int error;

    private String status;

    private String date;

    private List<results> results;

    public void setError(int error) {
        this.error = error;
    }

    public int getError() {
        return this.error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public List<app.newbee.lib.model.gson.results> getResults() {
        return results;
    }

    public void setResults(List<app.newbee.lib.model.gson.results> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Root{" +
                "error=" + error +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", results=" + results +
                '}';
    }
}