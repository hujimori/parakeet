package com.example.parakeet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * @author Yoshimori
 * 
 */
public class ClipImageView extends ImageView {

	// ---------------------------------------------------------------------------------------------
	// instance field
	// ---------------------------------------------------------------------------------------------
	private Path path = new Path();

	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ClipImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		// setWillNotDraw(false);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public ClipImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自動生成されたコンストラクター・スタブ
		// setWillNotDraw(false);
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);


	}

	/**
	 * 
	 * @param context
	 */
	public ClipImageView(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
		// setWillNotDraw(false);
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);

	}

	/**
	 * 
	 */
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		path.addCircle(w / 2, h / 2, w/2, Path.Direction.CCW);

	}

	protected void onDraw(Canvas canvas) {
		canvas.clipPath(path);

		// setWillNotDraw(false);
		// invalidate();

		super.onDraw(canvas);

	}

}
