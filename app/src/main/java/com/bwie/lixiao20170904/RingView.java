package com.bwie.lixiao20170904;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by lixiao on 2017/9/4 09:13.
 */
public class RingView extends View{

    private Paint mPaint;
    private final float ring_width;
    private int ring_color;
    private final TypedArray array;

    public RingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //通过该方法，可以取出attrs中的RingView属性。RingView就是我们在attrs.xml文件中declare-styleable标签名称。
        array = context.obtainStyledAttributes(attrs, R.styleable.RingView);
        //取出属性
        ring_width = array.getDimension(R.styleable.RingView_ring_width,5);
        ring_color = array.getColor(R.styleable.RingView_ring_color, Color.BLACK);
        //最后需要将TypedArray对象回收
        array.recycle();

        View v = View.inflate(context, R.layout.item, null);
        Button btn_change_color = (Button) v.findViewById(R.id.btn_change_color);
        btn_change_color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor();
            }
        });

    }

    private void setColor() {

        if (ring_color==Color.BLACK){
            int color = array.getColor(R.styleable.RingView_ring_color, Color.RED);
            ring_color=color;
        }else if (ring_color==Color.RED){
            int color = array.getColor(R.styleable.RingView_ring_color, Color.BLACK);
            ring_color=color;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaint.setAntiAlias(true); //设置抗锯齿的效果
        mPaint.setStyle(Paint.Style.STROKE); //设置画笔样式为描边
        mPaint.setStrokeWidth(ring_width);  //设置笔刷的粗细度
        mPaint.setColor(ring_color); //设置画笔的颜色

        int r = getMeasuredWidth()/2;    //使用getMeasuredWidth 可以获得在onMeasure方法中新计算的宽度
        //int cx = getLeft() + r;            //getLeft 表示的是当前View的左边到屏幕左边框的位置距离。这里不建议使用，因为如果view在左边使用其他View时，会影响该值。
        canvas.drawCircle(r, r+100, 100, mPaint); //该方法中的四个参数，前两个表示圆心的x，y的坐标。这两个值表示的是相对于该View中的位置，与其他view的位置无关。

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getSize(100, widthMeasureSpec);
        int height = getSize(100, heightMeasureSpec);

        if(width > height){
            width = height;
        }else{
            height = width;
        }
        setMeasuredDimension(width, height);  //将新计算的宽高测量值进行存储,否则不生效
    }

    private int getSize(int defaultSize, int measureSpec) {

        int mySize = defaultSize;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: // 没有指定大小，设置默认大小.

                mySize = defaultSize;

                break;
            case MeasureSpec.EXACTLY:      // 如果布局中设置的值大于默认值，则使用布局中设置的值，对应match_parent和固定值

                if(size > defaultSize){
                    mySize = size;
                }

                break;
            case MeasureSpec.AT_MOST:     // 如果测量模式中的值大于默认值，取默认值，对应wrap_content

                if(size > defaultSize){
                    mySize = defaultSize;
                }
                break;
        }
        return mySize;

    }
}
