/*
 * MainActivity.java
 * 
 * Copyright (C) 2013 6 Wunderkinder GmbH.
 * 
 * @author      Jose L Ugia - @Jl_Ugia
 * @author      Antonio Consuegra - @aconsuegra
 * @author      Cesar Valiente - @CesarValiente
 * @author      Benedikt Lehnert - @blehnert
 * @author      Timothy Achumba - @iam_timm
 * @version     2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ugia.slidinglayersample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.slidinglayer.SlidingLayer;

public class MainActivity extends Activity {

    private SlidingLayer mSlidingLayer;
    private TextView swipeText;

    private String mStickContainerToRightLeftOrMiddle;
    private boolean mShowShadow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPrefs();
        bindViews();
        initState();
    }

    /**
     * View binding
     */
    private void bindViews() {
        mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);
        swipeText = (TextView) findViewById(R.id.swipeText);
    }

    /**
     * Get current value for preferences
     */
    private void getPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mStickContainerToRightLeftOrMiddle = prefs.getString("layer_location", "right");
        mShowShadow = prefs.getBoolean("layer_has_shadow", false);
    }

    /**
     * Initializes the origin state of the layer
     */
    private void initState() {

        // Sticks container to right or left
        LayoutParams rlp = (LayoutParams) mSlidingLayer.getLayoutParams();
        int textResource;

        if (mStickContainerToRightLeftOrMiddle.equals("right")) {
            textResource = R.string.swipe_right_label;
            rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (mStickContainerToRightLeftOrMiddle.equals("left")) {
            textResource = R.string.swipe_left_label;
            rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            textResource = R.string.swipe_label;
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlp.width = LayoutParams.MATCH_PARENT;
        }

        swipeText.setText(getResources().getString(textResource));
        mSlidingLayer.setLayoutParams(rlp);

        // Sets the shadow of the container
        if (mShowShadow) {
            mSlidingLayer.setShadowWidthRes(R.dimen.shadow_width);
            mSlidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        } else {
            mSlidingLayer.setShadowWidth(0);
            mSlidingLayer.setShadowDrawable(null);
        }
    }

    public void buttonClicked(View v) {
        switch (v.getId()) {
        case R.id.buttonOpen:
            if (!mSlidingLayer.isOpened()) {
                mSlidingLayer.openLayer(true);
            }
            break;
        case R.id.buttonClose:
            if (mSlidingLayer.isOpened()) {
                mSlidingLayer.closeLayer(true);
            }
            break;
        }
    }

}
