package com.yzz.p2pinvest.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yzz.p2pinvest.R;
import com.yzz.p2pinvest.utils.UIUtils;

/**
 * Created by Wookeibun on 2017/7/17.
 * 自定义视图
 */

public class RoundProgress extends View {
//    使用自定义属性初始化
        private int roundColor;
    private int roundProgressColor ;
    private int textColor ;
    private float roundwidth ;
    private float textSize ;
    private float max ;
    private float progress ;
    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);//去除毛边
        //获取自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
         roundColor=typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.GRAY);
        roundProgressColor=typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.GRAY);
        textColor=typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GRAY);
        textSize=  typedArray.getDimension(R.styleable.RoundProgress_textsize, UIUtils.dp2px(20));
        roundwidth=  typedArray.getDimension(R.styleable.RoundProgress_roundwith, UIUtils.dp2px(10));
        max=typedArray.getFloat(R.styleable.RoundProgress_max, 100);
        progress=typedArray.getFloat(R.styleable.RoundProgress_progress, 70);
        typedArray.recycle();//回收处理

    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    private int width;
//    设置绘制圆环及文本的属性....使用自定义属性替换
//    private int roundColor = Color.GRAY;
//    private int roundProgressColor = Color.RED;
//    private int textColor = Color.BLUE;
//    private int roundwidth = UIUtils.dp2px(10);
//    private int textSize = UIUtils.dp2px(20);
//    private float max = 100;
//    private float progress = 50;
    private Paint paint;//画笔
//    测量:获取当前视图的宽高

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();
    }

//canvas:画布,对应着视图在布局中的范围区间,范围的左上顶点即为坐标原点

    @Override
    protected void onDraw(Canvas canvas) {
        //1.绘制圆环
        //圆心
        int cx = width / 2;
        int cy = width / 2;
        int radius = (int) (width / 2 - roundwidth / 2);
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);//圆环样式
        paint.setStrokeWidth(roundwidth);//圆环宽度
        canvas.drawCircle(cx, cy, radius, paint);

        //2.绘制圆弧
        RectF rectF = new RectF(roundwidth / 2, roundwidth / 2, width - roundwidth / 2, width - roundwidth / 2);
        paint.setColor(roundProgressColor);
        canvas.drawArc(rectF, 0, progress / max * 360, false, paint);
        //3.绘制文本
        String text = progress / max * 100 + "%";
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        Rect rect = new Rect();//创建了一个矩形,此时矩形没有具体的宽度和高度
        paint.getTextBounds(text, 0, text.length(), rect);//此时矩形的宽度和高度即为正好包裹text
        //获取左下顶点
        int x = width / 2 - rect.width() / 2;
        int y = width / 2 + rect.height() / 2;

        canvas.drawText(text, x, y, paint);
    }
}
