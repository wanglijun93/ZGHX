package com.travelsky.airportapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ElectronicSignView extends View {

	private Paint mPaint;

	private static final float MINP = 0.25f;
	private static final float MAXP = 0.75f;

	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;

	private boolean isEmpaty = true;


	public ElectronicSignView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		isEmpaty = true;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF000000);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(15);

	}

	//public Paint getPaint() {
	//	return mPaint;
	//}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
//		mCanvas.drawColor(Color.RED);   //绘制图片背景色必须加上透明度 颜色生效
		isEmpaty = true;

	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		canvas.drawPath(mPath, mPaint);
		
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);   //����ƽ������
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
		// commit the path to our offscreen
		mCanvas.drawPath(mPath, mPaint);
		// kill this so we don't double draw
		mPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		isEmpaty = false;
		return true;
	}
	
	public Bitmap  getPic(){
		if(mBitmap !=null && !isEmpaty ){
			int width = mBitmap.getWidth();
			int height = mBitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidht = 0.85f;
			float scaleHeight = 0.85f;
			matrix.postScale(scaleWidht, scaleHeight);
			Bitmap newbmp = Bitmap.createBitmap(mBitmap, 0, 0, width, height,
					matrix, true);
			if(!mBitmap.isRecycled()){
				mBitmap.recycle();
			}
			clearCanvas();
			return newbmp;
			
		}
		return null;
	}

	
	public void clearCanvas(){
		if( !isEmpaty){
			mBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
					mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
			mCanvas.setBitmap(mBitmap);
			isEmpaty = true;
			invalidate();
		}
		
	}
	

}
