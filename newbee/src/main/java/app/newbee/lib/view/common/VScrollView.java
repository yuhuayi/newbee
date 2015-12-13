package app.newbee.lib.view.common;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * ScrollView反弹效果的实现
 */
public class VScrollView extends ScrollView {
    private View inner;// 孩子View
    private float y;// 点击时y坐标
    private int mLastMotionX;
    private int mLastMotionY;
    // 默认支持反弹效果
    private boolean isAllowRebound = true;
    private Rect normal = new Rect();// 矩形(这里只是个形式，只是用于判断是否需要动画.)

    private boolean isCount = false;// 是否开始计算

    public VScrollView(Context context) {
        super(context);
    }

    public VScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 根据 XML 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以执行.
     */
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    public void setInnerView(View view) {
        this.inner = view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getRawX();
        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastMotionX;
                int deltaY = y - mLastMotionY;
                if (Math.abs(deltaX) > 10 && Math.abs(deltaY) < 45) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    /**
     * 监听touch
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null && getAllowRebound()) {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 触摸事件
     *
     * @param ev
     */
    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                // 手指松开.
                if (isNeedAnimation()) {
                    animation();
                    isCount = false;
                }
                break;
            /***
             * 排除出第一次移动计算，因为第一次无法得知y坐标， 在MotionEvent.ACTION_DOWN中获取不到，
             * 因为此时是MyScrollView的touch事件传递到到了LIstView的孩子item上面.所以从第二次计算开始.
             * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归0. 之后记录准确了就正常执行.
             */
            case MotionEvent.ACTION_MOVE:
                final float preY = y;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int) (preY - nowY);// 滑动距离
                if (!isCount) {
                    deltaY = 0; // 在这里要归0.
                }

                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    // 初始化头部矩形
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                    }

                    // 移动布局
                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2, inner.getRight(), inner.getBottom() - deltaY / 2);
                }
                isCount = true;
                break;

            default:
                break;
        }
    }

    /**
     * 回缩动画
     */
    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
        ta.setDuration(250);
        inner.startAnimation(ta);
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);

        normal.setEmpty();

    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的总高度
     * <p/>
     * getHeight()：获取的是屏幕的高度
     *
     * @return
     */
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        // 0是顶部，后面那个是底部
        return scrollY == 0 || scrollY == offset;
    }

    public void scrollToTop() {
        // this.setScrollY(0);
        this.scrollTo(this.getLeft(), 0);
    }

    public void scrollToBottom() {
        // this.setScrollY(this.getHeight());
        this.scrollTo(this.getLeft(), this.getHeight());
    }

    public void setAllowRebound(boolean isAllowRebound) {
        this.isAllowRebound = isAllowRebound;
    }

    public boolean getAllowRebound() {
        return this.isAllowRebound;
    }

    /**
     * 解决嵌套滑动问题
     *
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        //到底部，给listview放松消息，让其获取触摸事件
        if (getChildCount() >= 1 && getHeight() + getScrollY() == getChildAt(getChildCount() - 1).getBottom()) {
            if (onGetBottomListener != null)
                onGetBottomListener.onBottom();
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(VScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    public void setBottomListener(OnGetBottomListener listener) {
        onGetBottomListener = listener;
    }

    public interface OnGetBottomListener {
        void onBottom();
    }

    private OnGetBottomListener onGetBottomListener;

}