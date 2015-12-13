package app.newbee.lib.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 三个构造方法完全不用动，只要重写onMeasure方法，需要改动的地方比起方法3少了不是一点半点…
 * 在xml布局中和Activty中使用的ListView改成这个自定义ListView就行了。代码就省了吧…
 * 这个方法和方法1有一个同样的毛病，就是默认显示的首项是ListView，需要手动把ScrollView滚动至最顶端。
 * sv = (ScrollView) findViewById(R.id.act_solution_4_sv);
 * sv.smoothScrollTo(0, 0);
 */
public class ListViewForScrollView extends ListView {
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    int mLastMotionY ;

    boolean bottomFlag;
    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        //阻止父类拦截事件
        if(bottomFlag){
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;

                if(deltaY>0){
                    View child = getChildAt(0);
                    if (child != null) {


                        if (getFirstVisiblePosition() == 0
                                && child.getTop() == 0) {
                            bottomFlag = false;
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        int top = child.getTop();
                        int padding = getPaddingTop();
                        if (getFirstVisiblePosition() == 0
                                && Math.abs(top - padding) <= 8) {//这里之前用3可以判断,但现在不行,还没找到原因
                            bottomFlag = false;
                            getParent().requestDisallowInterceptTouchEvent(false);

                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }



        return super.onTouchEvent(ev);
    }

    public void setBottomFlag(boolean flag){
        bottomFlag = flag;
    }
}
