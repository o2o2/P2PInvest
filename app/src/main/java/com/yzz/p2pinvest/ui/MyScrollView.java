package com.yzz.p2pinvest.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.yzz.p2pinvest.utils.UIUtils;


/**
 * Created by Wookeibun on 2017/7/17.
 * 自定义viewgroup
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//    }
    View childView;

    @Override//获取子视图
    protected void onFinishInflate() {
        super.onFinishInflate();
        childView = getChildAt(0);
    }

    private int lastY;//上一次y轴方向操作的坐标
    private Rect normal = new Rect();//用于记录临界状态的上下左右
private boolean isFinishAnimation=true;

    private int lastX,downX,downY;
//    拦截:实现父视图对子视图的拦截
    //true,拦截成功反之,失败

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept=false;
        int eventX= (int) ev.getX();
        int eventY= (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX=downX=eventX;
                lastY=downY=eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                int absX = Math.abs(eventX - downX);
                int absY = Math.abs(eventY - downY);
                if (absY > absX && absY >= UIUtils.dp2px(10)) {
                    isIntercept=true;//执行拦截
                }
                lastX = eventX;
                lastY=eventY;
                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView == null||!isFinishAnimation) {
            return super.onTouchEvent(ev);
        }
        int evY = (int) ev.getY();//获取当前的y轴坐标
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = evY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = evY - lastY;
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        //记录临界值
                        normal.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                    }
                    //重新布局
                    childView.layout(childView.getLeft(), childView.getTop() + dy / 2, childView.getRight(), childView.getBottom() + dy / 2);
                }
                lastY = evY;//重新赋值
                break;
            case MotionEvent.ACTION_UP:
                if(isNeedAnimation()){
//                使用平移动画
                int translateY = childView.getBottom() - normal.bottom;
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -translateY);
                translateAnimation.setDuration(200);
                translateAnimation.setFillAfter(true);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isFinishAnimation=false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isFinishAnimation=true;
                        childView.clearAnimation();
//                        重新布局
                        childView.layout(normal.left, normal.top, normal.right, normal.bottom);
                        normal.setEmpty();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                childView.startAnimation(translateAnimation);
                }
                break;
        }
        return super.onTouchEvent(ev);

    }
//判断是否需要执行动画
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    private boolean isNeedMove() {
        int childViewMeasuredHeight = childView.getMeasuredHeight();
        int scrollmeasuredHeight = this.getMeasuredHeight();
        int dy = childViewMeasuredHeight - scrollmeasuredHeight;
        int scrollY = this.getScrollY();//获取用户在y轴上的偏移量(上+,下-)


        if (scrollY <= 0 || scrollY >= dy) {
            return true;//按照自定义的myscrollview方式处理
        }
        return false;//其他处在临界范围内的,返回false,即表示仍按照scrollview的方式处理
    }

}
