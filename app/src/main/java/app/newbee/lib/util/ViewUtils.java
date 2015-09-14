package app.newbee.lib.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewUtils {

    /**
     * get ListView height according to every children
     *
     * @param view
     * @return
     */
    public static int getListViewHeightBasedOnChildren(ListView view) {
        int height = getAbsListViewHeightBasedOnChildren(view);
        ListAdapter adapter;
        int adapterCount;
        if (view != null && (adapter = view.getAdapter()) != null && (adapterCount = adapter.getCount()) > 0) {
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }

    public static StateListDrawable setbg(Context context, int normalId, int pressId) {
        StateListDrawable bg = new StateListDrawable();
        Drawable idNormal = context.getResources().getDrawable(normalId);
        Drawable idPressed = context.getResources().getDrawable(pressId);
        bg.addState(new int[]{android.R.attr.state_pressed,
                android.R.attr.state_enabled, android.R.attr.selectable,
                android.R.attr.focusable}, idPressed);
        bg.addState(new int[]{android.R.attr.state_enabled}, idNormal);
        bg.addState(new int[]{}, idNormal);
        return bg;
    }

    public static long lastClickTime = 0;
    public static View view;

    public static boolean isFastDoubleClick(View _view) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        if (view != null) {
            if (view.equals(_view)) {
                if (0 < timeD && timeD < 800) {
                    return true;
                }
            } else {
                view = _view;
                return false;
            }
        } else {
            view = _view;
            return false;
        }
        return false;
    }

    private static final String CLASS_NAME_GRID_VIEW = "android.widget.GridView";
    private static final String FIELD_NAME_VERTICAL_SPACING = "mVerticalSpacing";

    /**
     * get GridView vertical spacing
     *
     * @param view
     * @return
     */
    public static int getGridViewVerticalSpacing(GridView view) {
        // get mVerticalSpacing by android.widget.GridView
        Class<?> demo = null;
        int verticalSpacing = 0;
        try {
            demo = Class.forName(CLASS_NAME_GRID_VIEW);
            Field field = demo.getDeclaredField(FIELD_NAME_VERTICAL_SPACING);
            field.setAccessible(true);
            verticalSpacing = (Integer) field.get(view);
            return verticalSpacing;
        } catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException, NoSuchFieldException, InstantiationException,
             * IllegalArgumentException, IllegalAccessException, NullPointException
             */
            e.printStackTrace();
        }
        return verticalSpacing;
    }

    /**
     * get AbsListView height according to every children
     *
     * @param view
     * @return
     */
    public static int getAbsListViewHeightBasedOnChildren(AbsListView view) {
        ListAdapter adapter;
        if (view == null || (adapter = view.getAdapter()) == null) {
            return 0;
        }

        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, view);
            if (item instanceof ViewGroup) {
                item.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }

    /**
     * set view height
     *
     * @param view
     * @param height
     */
    public static void setViewHeight(View view, int height) {
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    // /**
    // * set GistView height which is calculated by {@link # getGridViewHeightBasedOnChildren(GridView)}
    // *
    // * @param view
    // * @return
    // */
    // public static void setGridViewHeightBasedOnChildren(GridView view) {
    // setViewHeight(view, getGridViewHeightBasedOnChildren(view));
    // }

    /**
     * set ListView height which is calculated by {@link # getListViewHeightBasedOnChildren(ListView)}
     *
     * @param view
     * @return
     */
    public static void setListViewHeightBasedOnChildren(ListView view) {
        setViewHeight(view, getListViewHeightBasedOnChildren(view));
    }

    /**
     * set AbsListView height which is calculated by {@link # getAbsListViewHeightBasedOnChildren(AbsListView)}
     *
     * @param view
     * @return
     */
    public static void setAbsListViewHeightBasedOnChildren(AbsListView view) {
        setViewHeight(view, getAbsListViewHeightBasedOnChildren(view));
    }

    /**
     * set SearchView OnClickListener
     *
     * @param v
     * @param listener
     */
    public static void setSearchViewOnClickListener(View v, OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    /**
     * get descended views from parent.
     *
     * @param parent
     * @param filter          Type of views which will be returned.
     * @param includeSubClass Whether returned list will include views which are subclass of filter or not.
     * @return
     */
    public static <T extends View> List<T> getDescendants(ViewGroup parent, Class<T> filter, boolean includeSubClass) {
        List<T> descendedViewList = new ArrayList<T>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Class<? extends View> childsClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childsClass))
                    || (!includeSubClass && childsClass == filter)) {
                descendedViewList.add(filter.cast(child));
            }
            if (child instanceof ViewGroup) {
                descendedViewList.addAll(getDescendants((ViewGroup) child, filter, includeSubClass));
            }
        }
        return descendedViewList;
    }

    public static void big(View animationView, float scaleValue) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation gigAnimation = new ScaleAnimation(scaleValue, 1f, scaleValue, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(gigAnimation);
        animationSet.setDuration(500);
        animationSet.setFillAfter(true);
        animationView.startAnimation(animationSet);
    }

    public static void smallThenBig(View animationView, Animation.AnimationListener animationListener) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation smallAnimation = new ScaleAnimation(1f, (float) 0.8, 1f, (float) 0.8,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        smallAnimation.setDuration(80);
        smallAnimation.setFillAfter(false);
        animationSet.addAnimation(smallAnimation);

        ScaleAnimation bigAnimation = new ScaleAnimation((float) 1.2, 1f, (float) 1.2, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        bigAnimation.setDuration(160);
        bigAnimation.setStartOffset(80);
        bigAnimation.setFillAfter(false);
        animationSet.addAnimation(bigAnimation);
        animationSet.setAnimationListener(animationListener);
        animationView.startAnimation(animationSet);
    }

    //    highlight
    public static void highlightText(TextView textView, String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        SpannableString s = new SpannableString(textView.getText());
        Pattern p = Pattern.compile(key);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(Color.rgb(77, 164, 248)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(s);
    }
}
