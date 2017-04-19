package com.example.administrator.huaweiview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MyView extends View {
    private int mWidth;
    private int mHeight;
    private int radius;
    private float startAngle;
    private float sweepAngle;
    private Paint mArcPaint;
    private Paint mLinePaint;
    private TextPaint mTextPaint;
    private Paint mPointPaint;
    private int count;
    private int currentTemp;
    private int maxTemp;
    private int minTemp;
    private Bitmap bitmap;
    private int ocAngle;
    private int fgAngle;
    private int offset;

    public MyView(Context context) {
       this(context,null,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        initPaint();

        startAngle=120;
        sweepAngle=300;
        count=60;//刻度份数

        currentTemp=26;
        maxTemp=27;
        minTemp=20;
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.taq);
        ocAngle=230;
        fgAngle=90;
        offset=22;
    }
    private void initPaint() {
        mArcPaint=new Paint();
        mArcPaint.setColor(Color.WHITE);
        mArcPaint.setStrokeWidth(2);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);

        mLinePaint=new Paint();
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);

        mTextPaint=new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStrokeWidth(4);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(144);

        mPointPaint=new Paint();
        mPointPaint.setColor(Color.WHITE);
        mPointPaint.setStrokeWidth(2);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wrap_Len = 600;
        int width = measureDimension(wrap_Len, widthMeasureSpec);
        int height = measureDimension(wrap_Len, heightMeasureSpec);
        int len=Math.min(width,height);
        //保证是一个正方形
        setMeasuredDimension(len,len);
    }

    public int measureDimension(int defaultSize, int measureSpec){
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{
            result = defaultSize;   //UNSPECIFIED
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth=getWidth();
        mHeight=getHeight();
//       radius=(mWidth-getPaddingLeft()-getPaddingRight())/2;//半径
        //移动画布，是坐标圆心，在圆心

        canvas.translate(mWidth/2,mHeight/2);
        //半径
      radius=mWidth/2;
      //  drawArcView(canvas);
        drawLine(canvas);
        drawTextBitmapView(canvas);
    }

    /**
     * 绘制圆弧
     * @param canvas
     */
    private void drawArcView(Canvas canvas) {
//        RectF mRect=new RectF(-radius,-radius,radius,radius);
        RectF mRect=new RectF(0,0,mWidth,mHeight);
      //canvas.drawRect(mRect,mArcPaint);
       canvas.drawArc(mRect,startAngle,sweepAngle,false,mArcPaint);
    }

    private void drawLine(final Canvas canvas){
        //保存之前的画布状态
        canvas.save();

        //旋转画布，使圆心与起始点的连线，在Y轴上
        canvas.rotate(-270+startAngle);
        //刻度间隔
        final float angle=sweepAngle/count;
        for (int i=0;i<=count;i++){
            if (i==0||i==count){
                mLinePaint.setStrokeWidth(1);
                mLinePaint.setColor(Color.WHITE);
                canvas.drawLine(0,-radius,0,-radius+40,mLinePaint);
            }else if (i>0&&i<count){
                mLinePaint.setStrokeWidth(2);
                mLinePaint.setColor(getRealColor((int) startAngle));
                canvas.drawLine(0,-radius,0,-radius+30,mLinePaint);
            }
            startAngle+=angle;
            canvas.rotate(angle);//顺时针旋转
        }
        //恢复保存的状态
        canvas.restore();
    }
    //根据温度返回颜色值
    public int getRealColor(int startAngle){
        if(startAngle>120 && startAngle<180 ){
            return Color.parseColor("#40E0D0");//宝石绿
        }else if(startAngle>=180 && startAngle<225){
            return Color.parseColor("#00FF00");//酸橙绿
        }else if(startAngle>=225 &&startAngle<315){
            return Color.parseColor("#FFD700");//金色
        }else  if(startAngle>=315){
            return Color.parseColor("#CD5C5C");//印度红
        }
        return Color.parseColor("#00FF00");//酸橙绿;
    }

    private void drawTextBitmapView(Canvas canvas) {

        mTextPaint.setTextSize(16*2);
        canvas.drawText(currentTemp+"℃",0,0,mTextPaint);
        mTextPaint.setTextSize(16*2);
        bitmap=Bitmap.createScaledBitmap(bitmap,50,60,false);
        canvas.drawBitmap(bitmap,0-bitmap.getWidth()/2,radius-bitmap.getHeight()/2-30,null);

    }
    public float getTextPaintOffset(Paint paint){
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        return  -fontMetrics.descent+(fontMetrics.bottom-fontMetrics.top)/2;
    }
}
