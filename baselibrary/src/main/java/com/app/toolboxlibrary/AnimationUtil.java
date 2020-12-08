package com.app.toolboxlibrary;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;

/**
 *
 * @author
 * @说明：
 * @接口：无 @说明：无
 * @接口：无 @说明：无
 * @接口：无 @说明：无
 */
public class AnimationUtil {
	private static MyAnimationListener myclick ;
	private static final int start = 0 ;
	// 翻转动画效果
	public static void applyRotation(float start, float end,  View view) {
		// 计算中心点
		final float centerX = view.getWidth() / 2.0f;
		final float centerY = view.getHeight() / 2.0f;

		final Rotate3D rotation = new Rotate3D(start, end, centerX, centerY,
				0.0f, true);
		rotation.setDuration(1300);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new DecelerateInterpolator());
		// 设置监听
		rotation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				myclick.doEnd();
			}
		});

		view.startAnimation(rotation);
	}
	
	public interface MyAnimationListener {
		/**
		 * 动画结束操作
		 */
		public void doEnd();
	}
	
	public static void setAnimationLis(MyAnimationListener myanimaclick){
		myclick = myanimaclick ;
	}

}
