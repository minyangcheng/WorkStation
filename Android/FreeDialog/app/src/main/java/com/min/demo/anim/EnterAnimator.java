package com.min.demo.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.min.demo.R;
import com.min.dialog.anim.BaseAnimatorSet;
import com.min.dialog.util.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EnterAnimator extends BaseAnimatorSet {

	@Bind(R.id.tv_a)
	View mView_1;
	@Bind(R.id.tv_b)
	View mView_2;

	@Override
	public Animator getAnimator(View contentView) {
		ButterKnife.bind(this,contentView);

		int dy= ScreenUtils.dpToPxInt(contentView.getContext(), 150);
		AnimatorSet animatorSet=new AnimatorSet();
		ObjectAnimator animator_1=ObjectAnimator.ofFloat(mView_1,"translationY",dy,0);
		ObjectAnimator animator_2=ObjectAnimator.ofFloat(mView_2,"translationY",dy,0);
		animatorSet.play(animator_1).with(animator_2);
		animatorSet.setDuration(600);
		return animatorSet;
	}

}
