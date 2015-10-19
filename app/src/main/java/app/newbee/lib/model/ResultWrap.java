package app.newbee.lib.model;

import java.io.Serializable;

/**
 * Created by cao_ruixiang on 15/8/14.
 */
public class ResultWrap<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int result;
    private long timestamp;
    private T data;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getResult() {
        return result;
    }


    public void setResult(int result) {
        this.result = result;
    }


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ResultWrap{" +
                "result=" + result +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
