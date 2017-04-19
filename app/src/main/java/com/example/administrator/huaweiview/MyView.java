package com.example.administrator.huaweiview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {
	// 画圆弧的画笔
	private Paint paint;
	// 正方形的宽高
	private int len;
	// 圆弧的半径
	private float radius;
	// 矩形
	private RectF oval;
	// 圆弧的起始角度
	private float startAngle = 120;
	// 圆弧的经过角度
	private float sweepAngle = 300;

	// 刻度经过角度范围
	private float targetAngle = 300;

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		change(0);
	}

	/**
	 * 设置动画效果，开启子线程定时绘制
	 *
	 * @param trueAngle
	 */
	// 前进或者后退的状态，1代表前进，2代表后退。初始为后退状态。
	int state = 2;
	// 每次后退时的值，实现越来越快的效果
	private int[] back = { 2, 2, 4, 4, 6, 6, 8, 8, 10 };
	// 每次前进时的值，实现越来越慢的效果
	private int[] go = { 10, 10, 8, 8, 6, 6, 4, 4 };
	// 前进的下标
	private int go_index = 0;
	// 后退的下标
	private int back_index = 0;
	public void change(final float trueAngle) {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				switch (state) {
					case 1:
						// 开始增加
						targetAngle += go[go_index];
						go_index++;
						if (go_index == go.length) {// 到最后的元素时，下标一直为最后的
							go_index--;
						}
						if (targetAngle >= trueAngle) {// 如果画过刻度大于等于真实角度
							// 画过刻度=真实角度
							targetAngle = trueAngle;
							// 状态改为1（前进）
							state = 2;
							timer.cancel();
						}
						break;
					case 2:
						targetAngle -= back[back_index];
						back_index++;
						if (back_index == back.length) {
							back_index--;
						}

						if (targetAngle <= 0) {
							targetAngle = 0;
							state = 1;
						}
						break;
					default:
						break;
				}
				//异步重新绘制
				postInvalidate();
			}
		}, 500, 10);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 通过测量规则获得宽和高
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		// 取出最小值
		len = Math.min(width, height);
		oval = new RectF(0, 0, len, len);
		radius = len / 2;
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制一个圆弧，如果懂得坐标系的旋转，可以不写。
		// canvas.drawArc(oval, startAngle, sweepAngle, false, paint);
		// 画布，圆心左边，半径，起始角度，经过角度,
		drawLine(canvas);
	}

	float a = sweepAngle / 100;
	private Paint linePaint;

	/**
	 *
	 * @param canvas
	 */
	private void drawLine(final Canvas canvas) {
		// 保存之前的画布状态
		canvas.save();
		// 移动画布，实际上是改变坐标系的位置
		canvas.translate(radius, radius);
		// 旋转坐标系,需要确定旋转角度
		canvas.rotate(30);
		// 初始化画笔
		linePaint = new Paint();
		// 设置画笔的宽度（线的粗细）
		linePaint.setStrokeWidth(2);
		// 设置抗锯齿
		linePaint.setAntiAlias(true);
		// 累计叠加的角度
		float c = 0;
		for (int i = 0; i <= 100; i++) {

			if (c <= targetAngle&&targetAngle>0) {// 如果累计画过的角度，小于当前有效刻度
				// 计算累计划过的刻度百分比（画过的刻度比上中共进过的刻度）
				double p = c / (double) sweepAngle;
				int red = (int) (p * 255);
				int green = 255 - (int) (p * 255);
				linePaint.setARGB(255, red, green, 50);
				canvas.drawLine(0, radius, 0, radius - 40, linePaint);
				// 画过的角度进行叠加
				c += a;
			} else {
				linePaint.setColor(Color.WHITE);
				canvas.drawLine(0, radius, 0, radius - 40, linePaint);
			}
			canvas.rotate(a);
		}
		// 恢复画布状态。
		canvas.restore();
	}

}
