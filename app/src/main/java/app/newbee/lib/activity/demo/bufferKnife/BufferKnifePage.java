package app.newbee.lib.activity.demo.bufferKnife;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.newbee.lib.activity.base.BaseFragment;
import butterknife.*;
import com.newbee.lib.R;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * http://jakewharton.github.io/butterknife/
 * https://github.com/JakeWharton/butterknife
 * http://www.it165.net/pro/html/201404/12375.html
 */
public class BufferKnifePage extends BaseFragment {

    private static final ButterKnife.Action<View> ALPHA_FADE = new ButterKnife.Action<View>() {
        @Override
        public void apply(@NonNull View view, int index) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setFillBefore(true);
            alphaAnimation.setDuration(500);
            alphaAnimation.setStartOffset(index * 100);
            view.startAnimation(alphaAnimation);
        }
    };

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.subtitle)
    TextView subtitle;
    @Bind(R.id.hello)
    Button hello;
    @Bind(R.id.list_of_things)
    ListView listOfThings;
    @Bind(R.id.footer)
    TextView footer;

    @Bind({R.id.title, R.id.subtitle, R.id.hello})
    List<View> headerViews;
    @Bind(R.id.hello1)
    Button hello1;
    @Bind(R.id.hello2)
    Button hello2;

    private BFSimpleAdapter adapter;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.page_sub_buffer_knife, null);
    }

    @Override
    protected void processLogic() {
        // Contrived code to use the bound fields.
        title.setText("Butter Knife");
        subtitle.setText("Field and method binding for Android views.");
        footer.setText("by Jake Wharton");
        hello.setText("Say Hello");
        hello1.setText("Say Hello1");
        hello2.setText("Say Hello2");

        adapter = new BFSimpleAdapter(mActivity);
        listOfThings.setAdapter(adapter);
    }

    @OnClick(R.id.hello)
    void sayHello() {
        Toast.makeText(mActivity, "Hello, views!", LENGTH_SHORT).show();
        ButterKnife.apply(headerViews, ALPHA_FADE);
    }

    @OnClick({R.id.hello1, R.id.hello2})
    void muchRegClick(View view) {
        switch (view.getId()) {
            case R.id.hello1:
                Toast.makeText(mActivity, "Hello, views1!", LENGTH_SHORT).show();
                break;
            case R.id.hello2:
                Toast.makeText(mActivity, "Hello, views2!", LENGTH_SHORT).show();
                break;
        }
    }

    @OnLongClick(R.id.hello)
    boolean sayGetOffMe() {
        Toast.makeText(mActivity, "Let go of me!", LENGTH_SHORT).show();
        return true;
    }

    @OnItemClick(R.id.list_of_things)
    void onItemClick(int position) {
        Toast.makeText(mActivity, "You clicked: " + adapter.getItem(position), LENGTH_SHORT).show();
    }


}
