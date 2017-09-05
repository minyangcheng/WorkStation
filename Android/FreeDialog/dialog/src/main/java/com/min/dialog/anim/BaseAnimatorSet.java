package com.min.dialog.anim;

import android.animation.Animator;
import android.view.View;

public abstract class BaseAnimatorSet {

	protected Animator.AnimatorListener mListener;

	public abstract Animator getAnimator(View contentView);

	public BaseAnimatorSet listener(Animator.AnimatorListener listener) {
		this.mListener = listener;
		return this;
	}

	public void playOn(View view) {
		Animator animator=getAnimator(view);
		if(animator!=null){
			if(mListener!=null){
				animator.addListener(mListener);
			}
			animator.start();
		}
	}

}
