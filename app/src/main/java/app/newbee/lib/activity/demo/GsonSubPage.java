package app.newbee.lib.activity.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.newbee.lib.activity.base.BaseFragment;
import app.newbee.lib.model.gson.Root;
import app.newbee.lib.util.LogUtil;
import butterknife.Bind;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.newbee.lib.R;


public class GsonSubPage extends BaseFragment {

    @Bind(R.id.text)
    TextView text;
    private String txt = " * Json是一种类似于XML的通用数据交换格式,具有比XML更高的传输效率;本文将介绍两种方法解析JSON数据，需要的朋友可以参考下\n" +
            " * Json是一种类似于XML的通用数据交换格式,具有比XML更高的传输效率.\n" +
            " * 从结构上看，所有的数据（data）最终都可以分解成三种类型：\n" +
            " * 第一种类型是标量（scalar），也就是一个单独的字符串（string）或数字（numbers），比如\"北京\"这个单独的词。\n" +
            " * 第二种类型是序列（sequence），也就是若干个相关的数据按照一定顺序并列在一起，又叫做数组（array）或列表（List），比如\"北京，上海\"。\n" +
            " * 第三种类型是映射（mapping），也就是一个名/值对（Name/value），即数据有一个名称，还有一个与之相对应的值，这又称作散列（hash）或字典（dictionary），比如\"首都：北京\"。\n" +
            " * Json的规格非常简单，只用一个页面几百个字就能说清楚，而且Douglas Crockford声称这个规格永远不必升级，因为该规定的都规定了。\n" +
            " * 1） 并列的数据之间用逗号（\"，\"）分隔。\n" +
            " * 2） 映射用冒号（\"：\"）表示。\n" +
            " * 3） 并列数据的集合（数组）用方括号(\"[]\")表示。\n" +
            " * 4） 映射的集合（对象）用大括号（\"{}\"）表示。";

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        View messageLayout = inflater.inflate(R.layout.page_gson_template, container, false);
        return messageLayout;
    }


    @Override
    protected void processLogic() {
        text.setText(txt);
        deserializeAndSerialization();
    }
    @OnClick(R.id.title_back_img)
    void onBackPress() {
        mActivity.onBackPressed();
    }

    /**
     * {
     * "error": 0,
     * "status": "success",
     * "date": "2014-05-10",
     * "results": [
     * {
     * "currentCity": "南京",
     * "weather_data": [
     * {
     * "date": "周六(今天, 实时：19℃)",
     * "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/dayu.png",
     * "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/dayu.png",
     * "weather": "大雨",
     * "wind": "东南风5-6级",
     * "temperature": "18℃"
     * },
     * {
     * "date": "周日",
     * "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/zhenyu.png",
     * "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/duoyun.png",
     * "weather": "阵雨转多云",
     * "wind": "西北风4-5级",
     * "temperature": "21 ~ 14℃"
     * }
     * ]
     * }
     * ]
     * }
     */
    private void deserializeAndSerialization() {
        String jsonString = "{\n" +
                "    \"error\": 0,\n" +
                "    \"status\": \"success\",\n" +
                "    \"date\": \"2014-05-10\",\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"currentCity\": \"南京\",\n" +
                "            \"weather_datas\": [\n" +
                "                {\n" +
                "                    \"date\": \"周六(今天, 实时：19℃)\",\n" +
                "                    \"dayPictureUrl\": \"http://api.map.baidu.com/images/weather/day/dayu.png\",\n" +
                "                    \"nightPictureUrl\": \"http://api.map.baidu.com/images/weather/night/dayu.png\",\n" +
                "                    \"weather\": \"大雨\",\n" +
                "                    \"wind\": \"东南风5-6级\",\n" +
                "                    \"temperature\": \"18℃\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"date\": \"周日\",\n" +
                "                    \"dayPictureUrl\": \"http://api.map.baidu.com/images/weather/day/zhenyu.png\",\n" +
                "                    \"nightPictureUrl\": \"http://api.map.baidu.com/images/weather/night/duoyun.png\",\n" +
                "                    \"weather\": \"阵雨转多云\",\n" +
                "                    \"wind\": \"西北风4-5级\",\n" +
                "                    \"temperature\": \"21 ~ 14℃\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Gson gson = new Gson();
        Root root = gson.fromJson(jsonString, Root.class);

        LogUtil.info(GsonSubPage.class, "fromJson-->" + root.toString());
        String toJson = gson.toJson(root);

        LogUtil.info(GsonSubPage.class, root.toString());
        LogUtil.info(GsonSubPage.class, "toJson-->" + toJson);
    }

}
