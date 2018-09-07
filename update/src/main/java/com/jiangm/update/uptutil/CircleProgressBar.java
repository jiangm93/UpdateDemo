package com.jiangm.update.uptutil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.jiangm.update.R;


/**
 * Created by Administrator on 2018/7/20.
 */

public class CircleProgressBar extends View {





    //圆环的背景颜色与宽度属性，颜色默认为黑色，宽度默认为2
    private  int circleColor = Color.BLACK;
    private float circleWidth =2;

    //进度条的背景颜色与宽度属性，为了达到一个渐变色的效果，这里定义三个过渡色，进度条默认为绿色，宽度默认为1
    private int progressColor= Color.GREEN;
    private int progressColor2=progressColor;
    private int progressColor3=progressColor;
    private float progressWidth ;

    //进度条进度显示是否为从高到低，即从100到0，默认为从低到高，从高到底一般是在手机管家类的App中清理内存模块用于显示内存时用到
    private boolean isHighToLow;

    //是否设置顶部标题，中间内容，底部内容
    private boolean isSetToptitle = true;
    private boolean isSetMidContent = true;
    private boolean isSetBottomContent = true;



    private String topTitle;
    private String currentContent;
    private float currentProgress;
    private String bottmoContent;
    private float maxProgress;

    //设置显示的title,midContent,bottomContent的颜色，为了美观和谐,title与bottom的颜色应该相同，midcontent的颜色可以不同，字体大小可以不同
    private int topTitleColor;
    private int midProgressColor;
    private int bottomcontentColor;
    private float toptitleTextSize;
    private float currentProgressTextsize;
    private float bottomcontentTextSize;

    private float center;
    private int radius;

    //需要绘制的总弧度
    private int sweepAngle;
    private float startAngle;
    //因为可能绘画的不是一个完整的圆，所以不能用canvas.drawCircle这个方法，而是采用的 canvas.drawArc方法，因此要
    //定义一个RectF对象，这个矩形不会被画出来，只是用来确定绘画的圆的位置而已
    private RectF hideRect;
    private Paint circleBgPaint,progressPaint,titlePaint,midPaint,bottomPaint;
    private SweepGradient sweepGradient;
    private Matrix rotateMatrix;
    private  int colors[];

    public CircleProgressBar(Context context) {
        super(context,null);
        initView();
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initFlag(context, attrs);
        initView();

    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFlag(context, attrs);
        initView();

        // TODO Auto-generated constructor stub
    }

    private void initFlag(Context context, AttributeSet attrs){
        TypedArray a =context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        circleColor = a.getColor(R.styleable.CircleProgressBar_circle_color, Color.BLACK);
        circleWidth = a.getDimension(R.styleable.CircleProgressBar_circle_width, 2);

        progressColor = a.getColor(R.styleable.CircleProgressBar_progress_color1, Color.GREEN);
        progressColor2 = a.getColor(R.styleable.CircleProgressBar_progress_color2,progressColor);
        progressColor3 = a.getColor(R.styleable.CircleProgressBar_progress_color3,progressColor);
        progressWidth = a.getDimension(R.styleable.CircleProgressBar_progress_width, 1);

        isSetToptitle = a.getBoolean(R.styleable.CircleProgressBar_is_set_top_title, false);
        isSetMidContent = a.getBoolean(R.styleable.CircleProgressBar_is_set_mid_content, false);
        isSetBottomContent = a.getBoolean(R.styleable.CircleProgressBar_is_set_bottom_content, false);

        topTitle=a.getString(R.styleable.CircleProgressBar_top_title);
        bottmoContent=a.getString(R.styleable.CircleProgressBar_bottom_content);
        currentProgress=a.getFloat(R.styleable.CircleProgressBar_current_progress, 0);
        maxProgress=a.getFloat(R.styleable.CircleProgressBar_max_progress, 360);

        topTitleColor=a.getColor(R.styleable.CircleProgressBar_top_title_color, Color.GREEN);
        midProgressColor=a.getColor(R.styleable.CircleProgressBar_mid_progress_color, Color.GREEN);
        bottomcontentColor=a.getColor(R.styleable.CircleProgressBar_bottom_content_color,topTitleColor);
        toptitleTextSize= a.getDimension(R.styleable.CircleProgressBar_top_title_text_size, 15);
        currentProgressTextsize=a.getDimension(R.styleable.CircleProgressBar_current_progress_text_size, 55);
        bottomcontentTextSize=a.getDimension(R.styleable.CircleProgressBar_bottom_content_text_size, 15);

        sweepAngle=a.getInteger(R.styleable.CircleProgressBar_sweep_angle, 360);
        startAngle=a.getFloat(R.styleable.CircleProgressBar_start_arc,135);
        a.recycle();
    }


    private void initView()
    {




        circleBgPaint=new Paint();
        circleBgPaint.setColor(circleColor);
        circleBgPaint.setStyle(Paint.Style.STROKE);
        circleBgPaint.setStrokeWidth(circleWidth);
        circleBgPaint.setAntiAlias(true);

        progressPaint=new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setAntiAlias(true);


        titlePaint=new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(toptitleTextSize);
        titlePaint.setColor(topTitleColor);
        titlePaint.setTextAlign(Paint.Align.CENTER);


        midPaint=new Paint();
        midPaint.setAntiAlias(true);
        midPaint.setTextSize(currentProgressTextsize);
        midPaint.setColor(midProgressColor);
        midPaint.setTextAlign(Paint.Align.CENTER);

        bottomPaint=new Paint();
        bottomPaint.setAntiAlias(true);
        bottomPaint.setTextSize(bottomcontentTextSize);
        bottomPaint.setColor(bottomcontentColor);
        bottomPaint.setTextAlign(Paint.Align.CENTER);


        colors=new int[]{ progressColor, progressColor2, progressColor3};
        rotateMatrix = new Matrix();


    }



    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        center = getWidth()/2; //该方法必须在onDraw或者onMeasure中调用，否则不起作用d

        //圆环的半径 ，此处必须是progressWidth与circleWidth中较大的一个
        //radius = (int) (center - progressWidth/2);
        if(progressWidth>circleWidth)
            radius=(int)(center-progressWidth/2);
        else
        {radius=(int)(center-circleWidth/2);}


        sweepGradient = new SweepGradient(0, 0, colors, null);
        hideRect=new RectF(center - radius, center - radius, center
                + radius, center + radius);


        canvas.drawArc(hideRect, startAngle,sweepAngle, false, circleBgPaint);

        rotateMatrix.setRotate(140, center, center);
        sweepGradient.setLocalMatrix(rotateMatrix);
        //   progressPaint.setShader(linearGradient);
        //使用sweepGradient更加美观
        progressPaint.setShader(sweepGradient);
        canvas.drawArc(hideRect, startAngle, currentProgress/maxProgress*360, false, progressPaint);

        if (isSetToptitle) {
            canvas.drawText(topTitle, center, center- currentProgressTextsize, titlePaint);
        }
        if (isSetMidContent) {
            canvas.drawText(String.format("%.0f", currentProgress).isEmpty()? String.format("%.0f", currentProgress): String.format("%.0f", currentProgress)+"%", center, center+currentProgressTextsize/2, midPaint);
        }

        if (isSetBottomContent) {
            canvas.drawText( bottmoContent, center, center+currentProgressTextsize,bottomPaint);
        }


    }

    private int measuredDimension(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 800;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    public void setCurrentProgress(float currentProgress) {
        if (currentProgress > maxProgress) {
            currentProgress = maxProgress;
        }
        if (currentProgress < 0) {
            currentProgress = 0;
        }
        if(currentProgress <= maxProgress){
            this.currentProgress = currentProgress;
            postInvalidate();
        }
    }



    //必须重写该方法，否则在xml文件中定义warp_content与match_parent效果相同
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(measuredDimension(widthMeasureSpec), measuredDimension(heightMeasureSpec));

    }


    /**
     * 用来设置sweepAngle参数，该参数在绘制部分圆弧的时候用到，默认为360
     * @param sweepAngle
     */
    public void setSweepAngle(int sweepAngle)
    {
        this.sweepAngle=sweepAngle;
    }


    /**
     * 设置进度条的最大值
     * @param maxProgress 用来控制进度条的最大值，范围在0-360之间
     */
    public void setMaxProgress(int maxProgress)
    {
        this.maxProgress=maxProgress;
    }

    /**
     * 设置标题
     * @param topTitle
     */
    public void setTopTitle(String topTitle)
    {
        this.topTitle=topTitle;
        postInvalidate();
    }
    /**
     * 设置进度
     * @param currentContent
     */
    public void setCurrentProgressText(String currentContent)
    {
        this.currentContent=currentContent;
        postInvalidate();
    }


}
