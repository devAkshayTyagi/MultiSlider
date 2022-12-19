/*
 * Copyright (C) 2014 Kalin Maldzhanski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apptik.widget.example.multislider;


import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

import io.apptik.widget.MultiSlider;


public class MultiSliderFragmentMany extends Fragment {

    int firstThumb =20;
    int secondThumb =20;
    int thirdThumb =20;
    int fourthThumb =20;

    public MultiSliderFragmentMany() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_multi_slider_many, container, false);
        final ArrayList<TextView> vals = new ArrayList<>();
        vals.add((TextView) v.findViewById(R.id.value1));
        vals.add((TextView) v.findViewById(R.id.value2));
        vals.add((TextView) v.findViewById(R.id.value3));
        vals.add((TextView) v.findViewById(R.id.value4));
        vals.add((TextView) v.findViewById(R.id.value5));

        MultiSlider multiSlider = v.findViewById(R.id.multiSlider);
        final ArrayList<TextView> tv = new ArrayList<>();
        tv.add((TextView) v.findViewById(R.id.tv_one));
        tv.add((TextView) v.findViewById(R.id.tv_two));
        tv.add((TextView) v.findViewById(R.id.tv_three));
        tv.add((TextView) v.findViewById(R.id.tv_four));
        tv.add((TextView) v.findViewById(R.id.tv_five));

        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(tv.get(0).getContext(), R.layout.fragment_multi_slider_many);

        for (int i = 0; i < 5; i++) {
            vals.get(i).setText(String.valueOf(multiSlider.getThumb(i).getValue()));
            tv.get(i).setText(String.valueOf(20));
        }

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(getResources().getColor(R.color.range_one));
        gd1.setCornerRadii(new float[]{20f, 20f, 0f, 0f, 0f, 0f, 20f, 20f});

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setColor(getResources().getColor(R.color.range_two));

        GradientDrawable gd3 = new GradientDrawable();
        gd3.setColor(getResources().getColor(R.color.range_three));

        GradientDrawable gd4 = new GradientDrawable();
        gd4.setColor(getResources().getColor(R.color.range_four));

        GradientDrawable gd5 = new GradientDrawable();
        gd5.setColor(getResources().getColor(R.color.range_five));
        gd5.setCornerRadii(new float[]{0f, 0f, 20f, 20f, 20f, 20f, 0f, 0f});

        //different ranges example
        multiSlider.getThumb(0).setRange(gd1);
        multiSlider.getThumb(1).setRange(gd2);
        multiSlider.getThumb(2).setRange(gd3);
        multiSlider.getThumb(3).setRange(gd4);
        multiSlider.getThumb(4).setRange(gd5);

        multiSlider.getThumb(4).setThumb(new ColorDrawable(ResourcesCompat.getColor(getActivity().getResources(), R.color.transparent, null))).setValue(100).setEnabled(false);

        multiSlider.getThumb(0).setValue(20);
        multiSlider.getThumb(1).setValue(40);
        multiSlider.getThumb(2).setValue(60);
        multiSlider.getThumb(3).setValue(80);

        // set min max
        multiSlider.getThumb(0).setMin(5);
        multiSlider.getThumb(3).setMax(95);
        multiSlider.getThumb(4).setMax(100);

        multiSlider.setOnTrackingChangeListener(new MultiSlider.OnTrackingChangeListener() {
            @Override
            public void onStartTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value, int thumbIndex) {

            }

            @Override
            public void onStopTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value, int thumbIndex) {
                if (thumbIndex == 0) {
                    firstThumb = value;
                }
            }
        });

        multiSlider.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                vals.get(thumbIndex).setText(String.valueOf(value));

                if (thumbIndex == 0) {
                   /* // tvOne.((multiSlider.getThumb(0).getValue()));
                    tv.get(thumbIndex).setText(String.valueOf(multiSlider.getThumb(0).getValue()));
                    tv.get(thumbIndex + 1).setText(String.valueOf(40 - value));*/
                    if (firstThumb>value){ // thumb moving left
                        tv.get(thumbIndex).setText(String.valueOf(value));
                        tv.get(thumbIndex + 1).setText(String.valueOf((firstThumb-value)+secondThumb));
                    }else if (firstThumb<value){ // thumb moving right
                        tv.get(thumbIndex).setText(String.valueOf(value));
                        tv.get(thumbIndex + 1).setText(String.valueOf(secondThumb -(value-firstThumb)));
                    }/*else {
                        tv.get(thumbIndex).setText(String.valueOf(value));
                        tv.get(thumbIndex + 1).setText(String.valueOf(value));
                    }*/
                    constraintSet.setHorizontalBias(R.id.view_one, multiSlider.getThumb(thumbIndex).getValue() / 100f);
                    constraintSet.applyTo((ConstraintLayout) v.findViewById(R.id.cl_root));
                } else if (thumbIndex == 1) {
                  /*  if (value > 40) {
                        tv.get(thumbIndex).setText(String.valueOf(Integer.parseInt(tv.get(1).getText().toString())+ (value - 40)));
                        tv.get(thumbIndex + 1).setText(String.valueOf(Integer.parseInt(tv.get(2).getText().toString()) - (value - 40)));
                    } else if (value < 40) {
                        tv.get(thumbIndex).setText(String.valueOf(Integer.parseInt(tv.get(1).getText().toString()) - (40 - value)));
                        tv.get(thumbIndex + 1).setText(String.valueOf(Integer.parseInt(tv.get(2).getText().toString()) + (40 - value)));
                    } else {
                        tv.get(thumbIndex).setText(String.valueOf(40 - Integer.parseInt(tv.get(0).getText().toString())));
                        tv.get(thumbIndex + 1).setText(String.valueOf(40 - Integer.parseInt(tv.get(thumbIndex).getText().toString())));
                    }*/

                    constraintSet.setHorizontalBias(R.id.view_two, multiSlider.getThumb(thumbIndex).getValue() / 100f);
                    constraintSet.applyTo((ConstraintLayout) v.findViewById(R.id.cl_root));
                } else if (thumbIndex == 2) {
                    // tvThree.setText(String.valueOf(value));
                    // tvFour.setText(String.valueOf(40 - value));

                    constraintSet.setHorizontalBias(R.id.view_three, multiSlider.getThumb(thumbIndex).getValue() / 100f);
                    constraintSet.applyTo((ConstraintLayout) v.findViewById(R.id.cl_root));
                } else if (thumbIndex == 3) {
                    // tvFour.setText(String.valueOf(value));
                    //  tvFive.setText(String.valueOf(40 - value));

                    constraintSet.setHorizontalBias(R.id.view_four, multiSlider.getThumb(thumbIndex).getValue() / 100f);
                    constraintSet.applyTo((ConstraintLayout) v.findViewById(R.id.cl_root));
                }
            }

        });

        return v;
    }
}
