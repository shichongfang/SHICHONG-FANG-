package com.example.D16124907.utils;

import java.util.Random;

import com.example.D16124907.common.D16124907Application;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 *  use to create code
 */
public class CodeUtils {
	private static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };

	private static CodeUtils mCodeUtils;
	private int mPaddingLeft, mPaddingTop;
	private StringBuilder mBuilder = new StringBuilder();
	private Random mRandom = new Random();
	private String mCurCode;

	// Default Settings
	private static final int DEFAULT_CODE_LENGTH = 4;
	private static final int DEFAULT_FONT_SIZE = 60;
	private static final int DEFAULT_LINE_NUMBER = 4;
	private static final int BASE_PADDING_LEFT = DensityUtil.dp2px(D16124907Application.sContext, 5);
	private static final int RANGE_PADDING_LEFT = DensityUtil.dp2px(D16124907Application.sContext, 20);
	private static final int BASE_PADDING_TOP = DensityUtil.dp2px(D16124907Application.sContext, 25);
	private static final int RANGE_PADDING_TOP = DensityUtil.dp2px(D16124907Application.sContext, 10);
	private static final int DEFAULT_WIDTH = DensityUtil.dp2px(D16124907Application.sContext, 100);
	private static final int DEFAULT_HEIGHT = DensityUtil.dp2px(D16124907Application.sContext, 40);
	private static final int DEFAULT_COLOR = 0xDF;

	public static CodeUtils getInstance() {
		if (mCodeUtils == null) {
			mCodeUtils = new CodeUtils();
		}
		return mCodeUtils;
	}

	// 生成验证码图片
	public Bitmap createBitmap() {
		mPaddingLeft = 0;
		mPaddingTop = 0;

		Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		// create code
		mCurCode = createCode();

		canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
		Paint paint = new Paint();
		paint.setTextSize(DEFAULT_FONT_SIZE);

		for (int i = 0; i < mCurCode.length(); i++) {
			randomTextStyle(paint);
			randomPadding();
			canvas.drawText(mCurCode.charAt(i) + "", mPaddingLeft, mPaddingTop, paint);
		}


		for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
			drawLine(canvas, paint);
		}

		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return bitmap;
	}

	// create code
	public String createCode() {
		mBuilder.delete(0, mBuilder.length());

		for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
			mBuilder.append(CHARS[mRandom.nextInt(CHARS.length)]);
		}

		return mBuilder.toString();
	}

	public String getCurCode() {
		return mCurCode;
	}

	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = mRandom.nextInt(DEFAULT_WIDTH);
		int startY = mRandom.nextInt(DEFAULT_HEIGHT);
		int stopX = mRandom.nextInt(DEFAULT_WIDTH);
		int stopY = mRandom.nextInt(DEFAULT_HEIGHT);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	// 随机颜色
	private int randomColor() {
		mBuilder.delete(0, mBuilder.length());

		String haxString;
		for (int i = 0; i < 3; i++) {
			haxString = Integer.toHexString(mRandom.nextInt(0xFF));
			if (haxString.length() == 1) {
				haxString = "0" + haxString;
			}

			mBuilder.append(haxString);
		}

		return Color.parseColor("#" + mBuilder.toString());
	}

	// random text style
	private void randomTextStyle(Paint paint) {
		int color = randomColor();
		paint.setColor(color);
		paint.setFakeBoldText(mRandom.nextBoolean());
		float skewX = mRandom.nextInt(11) / 10;
		skewX = mRandom.nextBoolean() ? skewX : -skewX;
		paint.setTextSkewX(skewX);

	}

	// random padding
	private void randomPadding() {
		mPaddingLeft += BASE_PADDING_LEFT + mRandom.nextInt(RANGE_PADDING_LEFT);
		mPaddingTop = BASE_PADDING_TOP + mRandom.nextInt(RANGE_PADDING_TOP);
	}
}