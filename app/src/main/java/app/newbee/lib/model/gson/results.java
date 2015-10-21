package app.newbee.lib.model.gson;

/**
 * Created with Android Studio.
 * User: cao_ruixiang
 * Date: 15/10/20
 * Time: 10:05
 */
import java.util.List;
public class results {
    private String currentCity;

    private List<weather_data> weather_datas ;

    public void setCurrentCity(String currentCity){
        this.currentCity = currentCity;
    }
    public String getCurrentCity(){
        return this.currentCity;
    }
    public void setWeather_data(List<weather_data> weather_data){
        this.weather_datas = weather_data;
    }
    public List<weather_data> getWeather_data(){
        return this.weather_datas;
    }


    @Override
    public String toString() {
        return "results{" +
                "currentCity='" + currentCity + '\'' +
                ", weather_datas=" + weather_datas +
                '}';
    }
}