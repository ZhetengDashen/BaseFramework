package com.baseeasy.commonlibrary.mytool.animotion;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;

/**
 * Created by Wang on 2016/7/6.
 */
public class ObjectAnimator_Utils {
//    scaleX
    //translationX 'rotationX' 'alpha'

    /**
     * 创建人： Wang   时间： 2016/7/6  19:05
     * 说明： 放大缩小动画
     *
     * @param view
     * @param start_size 起始大小
     * @param end_size   结束大小
     * @param ease_Type  动画类型 28种
     * @param time       持续时间（毫秒）
     */

    public static void Animator_scale(View view, float start_size, float end_size, Ease ease_Type, int time, int delay_time) {
        if(null==ease_Type){
            ease_Type=Ease.EASE_IN_EXPO;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", start_size, end_size);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", start_size, end_size);
        animator.setInterpolator(new EasingInterpolator(ease_Type));
        animator2.setInterpolator(new EasingInterpolator(ease_Type));
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.play(animator2);
        set.setDuration(time);
        set.setStartDelay(delay_time);
        set.start();
    }


    /**
     *    创建人： Wang   时间： 2016/7/7  14:09
     *    说明：  透明度
     */

    public static void Animator_alpha(View view, float start_alpha, float end_alpha, Ease ease_Type, int time, int delay_time) {
        if(null==ease_Type){
            ease_Type=Ease.EASE_IN_EXPO;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", start_alpha, end_alpha);
        animator.setInterpolator(new EasingInterpolator(ease_Type));
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(time);
        set.setStartDelay(delay_time);
        set.start();
    }


    /**
     *    创建人： Wang   时间： 2016/7/7  15:04
     *    说明： 水平移动
     */

    public static void Animator_translation_X(View view, float start_positionX, float end_positionX,Ease ease_Type, int time, int delay_time){
        if(null==ease_Type){
            ease_Type=Ease.EASE_IN_EXPO;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", start_positionX, end_positionX);
        animator.setInterpolator(new EasingInterpolator(ease_Type));
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(time);
        set.setStartDelay(delay_time);
        set.start();
    }

    /**
     *    创建人： Wang   时间： 2016/7/7  15:04
     *    说明：  垂直移动
     */

    public static void Animator_translation_Y(View view, float start_positionY, float end_positionY, Ease ease_Type, int time, int delay_time ){
        if(null==ease_Type){
            ease_Type=Ease.EASE_IN_EXPO;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", start_positionY, end_positionY);
        animator.setInterpolator(new EasingInterpolator(ease_Type));
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(time);
        set.setStartDelay(delay_time);
        set.start();
    }

    /**
     *    创建人： Wang   时间： 2016/7/7  15:35
     *    说明：  旋转动画
     */

    public  static void Animator_rotation(View view, float start_rotation, float end_rotation,Ease ease_Type, int time, int delay_time){

        if(null==ease_Type){
            ease_Type=Ease.EASE_IN_EXPO;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", start_rotation, end_rotation);
        animator.setInterpolator(new EasingInterpolator(ease_Type));
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(time);
        set.setStartDelay(delay_time);
        set.start();
    }

    /**
     *    创建人： Wang   时间： 2016/7/7  15:35
     *    说明：  水平翻转动画
     */

    public  static void Animator_rotation_X(View view, float start_rotationX, float end_rotationX, Ease ease_Type, int time, int delay_time){
        if(null==ease_Type){
            ease_Type=Ease.EASE_IN_EXPO;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationX", start_rotationX, end_rotationX);
        animator.setInterpolator(new EasingInterpolator(ease_Type));

        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(time);
        set.setStartDelay(delay_time);
        set.start();
    }
    /**
     *    创建人： Wang   时间： 2016/7/7  15:35
     *    说明：  垂直翻转动画
     */

    public  static void Animator_rotation_Y(View view, float start_rotationY, float end_rotationY, Ease ease_Type, int time, int delay_time){

        if(null==ease_Type){
            ease_Type=Ease.EASE_IN_EXPO;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationY", start_rotationY, end_rotationY);
        animator.setInterpolator(new EasingInterpolator(ease_Type));
        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(time);
        set.setStartDelay(delay_time);
        set.start();
    }
}
