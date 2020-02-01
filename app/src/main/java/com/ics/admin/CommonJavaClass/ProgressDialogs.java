package com.ics.admin.CommonJavaClass;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ics.admin.R;

import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class ProgressDialogs  {
//    public static final float SCALE=1.0f;
//
//    //scale x ,y
//    private float[] scaleFloats=new float[]{SCALE,
//            SCALE,
//            SCALE,
//            SCALE,
//            SCALE};
//
//
//
//    @Override
//    public void draw(Canvas canvas, Paint paint) {
//        float circleSpacing=4;
//        float radius=(Math.min(getWidth(),getHeight())-circleSpacing*2)/12;
//        float x = getWidth()/ 2-(radius*2+circleSpacing);
//        float y=getHeight() / 2;
//        for (int i = 0; i < 4; i++) {
//            canvas.save();
//            float translateX=x+(radius*2)*i+circleSpacing*i;
//            canvas.translate(translateX, y);
//            canvas.scale(scaleFloats[i], scaleFloats[i]);
//            canvas.drawCircle(0, 0, radius, paint);
//            canvas.restore();
//        }
//    }
//
//    @Override
//    public ArrayList<ValueAnimator> onCreateAnimators() {
//        ArrayList<ValueAnimator> animators=new ArrayList<>();
//        int[] delays=new int[]{120,240,360,480};
//        for (int i = 0; i < 4; i++) {
//            final int index=i;
//
//            ValueAnimator scaleAnim= ValueAnimator.ofFloat(1,0.3f,1);
//
//            scaleAnim.setDuration(750);
//            scaleAnim.setRepeatCount(-1);
//            scaleAnim.setStartDelay(delays[i]);
//
//            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    scaleFloats[index] = (float) animation.getAnimatedValue();
//                    postInvalidate();
//
//                }
//            });
//            animators.add(scaleAnim);
//        }
//        return animators;
//    }
//    // Set imageList
//    List<Integer> imageList = new ArrayList<Integer>();
//
//
////    public FlipProgressDialog SetProgress(){
////        imageList.add(R.drawable.asr_logo);
////        imageList.add(R.drawable.asr_logo);
////
////        FlipProgressDialog fpd = new FlipProgressDialog();
////
////        fpd.setImageList(imageList);                              // *Set a imageList* [Have to. Transparent background png recommended]
////        fpd.setCanceledOnTouchOutside(true);                      // If true, the dialog will be dismissed when user touch outside of the dialog. If false, the dialog won't be dismissed.
////        fpd.setDimAmount(0.0f);                                   // Set a dim (How much dark outside of dialog)
////
////// About dialog shape, color
////        fpd.setBackgroundColor(Color.parseColor("#FF4081"));      // Set a background color of dialog
////        fpd.setBackgroundAlpha(0.2f);                             // Set a alpha color of dialog
////        fpd.setBorderStroke(0);                                   // Set a width of border stroke of dialog
////        fpd.setBorderColor(-1);                                   // Set a border stroke color of dialog
////        fpd.setCornerRadius(16);                                  // Set a corner radius
////
////// About image
////        fpd.setImageSize(200);                                    // Set an image size
////        fpd.setImageMargin(10);                                   // Set a margin of image
////
////// About rotation
////        fpd.setOrientation("rotationY");                          // Set a flipping rotation
////        fpd.setDuration(600);                                     // Set a duration time of flipping ratation
////        fpd.setStartAngle(0.0f);                                  // Set an angle when flipping ratation start
////        fpd.setEndAngle(180.0f);                                  // Set an angle when flipping ratation end
////        fpd.setMinAlpha(0.0f);                                    // Set an alpha when flipping ratation start and end
////        fpd.setMaxAlpha(1.0f);                                    // Set an alpha while image is flipping
////
////        return fpd;
////
////    }
}
