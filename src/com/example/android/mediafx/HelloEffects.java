/*
 * Copyright (C) 2012 The Android Open Source Project
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

package com.example.android.mediafx;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;



public class HelloEffects extends Activity implements GLSurfaceView.Renderer {

    private GLSurfaceView mEffectView;
    private int[] mTextures = new int[2];
    private EffectContext mEffectContext;
    private Effect mEffect;
    private TextureRenderer mTexRenderer = new TextureRenderer();
    private int mImageWidth;
    private int mImageHeight;
    private boolean mInitialized = false;
    int mCurrentEffect;
    
    Integer[] pics = {
    		R.drawable.antartica1,
    		R.drawable.antartica2,
    		R.drawable.antartica3,
    		R.drawable.antartica4,
    		R.drawable.antartica5,
    		R.drawable.antartica6,
    		R.drawable.antartica7,
    		R.drawable.antartica8,
    		R.drawable.antartica9,
    		R.drawable.antartica10,
    		R.drawable.antartica1,
    		R.drawable.antartica2,
    		R.drawable.antartica3,
    		R.drawable.antartica4,
    		R.drawable.antartica5,
    		R.drawable.antartica6,
    		R.drawable.antartica7,
    		R.drawable.antartica8,
    		R.drawable.antartica9,
    		R.drawable.antartica10,
    		R.drawable.antartica1,
    		R.drawable.antartica2,
    		R.drawable.antartica3,
    		R.drawable.antartica4,
    };

    public void setCurrentEffect(int effect) {
        mCurrentEffect = effect+1;
        //Toast.makeText(getApplicationContext(), "" + mCurrentEffect, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery ga = (Gallery)findViewById(R.id.gallery);
        ga.setAdapter(new ImageAdapter(this));
        
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        MarginLayoutParams mlp = (MarginLayoutParams) ga.getLayoutParams();
        mlp.setMargins(-(metrics.widthPixels / 2 + (200)), mlp.topMargin,
        mlp.rightMargin, mlp.bottomMargin);
        
        ga.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//Toast.makeText(getBaseContext(), 
					//	"You have selected picture " + (arg2+1) + " of Antartica", 
						//Toast.LENGTH_SHORT).show();
				//imageView.setImageResource(pics[arg2])
					
				setCurrentEffect(arg2);
			    mEffectView.requestRender();
				
			}
        	
        });
        
        /**
         * Initialize the renderer and tell it to only render when
         * explicity requested with the RENDERMODE_WHEN_DIRTY option
         */
        mEffectView = (GLSurfaceView) findViewById(R.id.effectsview);
        mEffectView.setEGLContextClientVersion(2);
        mEffectView.setRenderer(this);
        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mCurrentEffect = 1;
    }

    private void loadTextures() {
        // Generate textures
        GLES20.glGenTextures(2, mTextures, 0);

        // Load input bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.puppy);
        mImageWidth = bitmap.getWidth();
        mImageHeight = bitmap.getHeight();
        mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);

        // Upload to texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // Set texture parameters
        GLToolbox.initTexParams();
    }

    private void initEffect() {
        EffectFactory effectFactory = mEffectContext.getFactory();
        if (mEffect != null) {
            mEffect.release();
        }
        /**
         * Initialize the correct effect based on the selected menu/action item
         */
        switch (mCurrentEffect) {

            case 1:
                break;

            case 2:
                mEffect = effectFactory.createEffect(
                   
                		EffectFactory.EFFECT_AUTOFIX);
                mEffect.setParameter("scale", 0.5f);
                break;

            case 3:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_BLACKWHITE);
                mEffect.setParameter("black", .1f);
                mEffect.setParameter("white", .7f);
                break;

            case 4:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_BRIGHTNESS);
                mEffect.setParameter("brightness", 2.0f);
                break;

            case 5:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_CONTRAST);
                mEffect.setParameter("contrast", 1.4f);
                break;

            case 6:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_CROSSPROCESS);
                break;

            case 7:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_DOCUMENTARY);
                break;

            case 8:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_DUOTONE);
                mEffect.setParameter("first_color", Color.YELLOW);
                mEffect.setParameter("second_color", Color.DKGRAY);
                break;

            case 9:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_FILLLIGHT);
                mEffect.setParameter("strength", .8f);
                break;

            case 10:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_FISHEYE);
                mEffect.setParameter("scale", .5f);
                break;

            case 11:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_FLIP);
                mEffect.setParameter("vertical", true);
                break;

            case 12:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_FLIP);
                mEffect.setParameter("horizontal", true);
                break;

            case 13:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_GRAIN);
                mEffect.setParameter("strength", 1.0f);
                break;

            case 14:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_GRAYSCALE);
                break;

            case 15:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_LOMOISH);
                break;

            case 16:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_NEGATIVE);
                break;

            case 17:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_POSTERIZE);
                break;

            case 18:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_ROTATE);
                mEffect.setParameter("angle", 180);
                break;

            case 19:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_SATURATE);
                mEffect.setParameter("scale", .5f);
                break;

            case 20:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_SEPIA);
                break;

            case 21:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_SHARPEN);
                break;

            case 22:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_TEMPERATURE);
                mEffect.setParameter("scale", .9f);
                break;

            case 23:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_TINT);
                mEffect.setParameter("tint", Color.MAGENTA);
                break;

            case 24:
                mEffect = effectFactory.createEffect(
                        EffectFactory.EFFECT_VIGNETTE);
                mEffect.setParameter("scale", .5f);
                break;

            default:
                break;

        }
    }

    private void applyEffect() {
        mEffect.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1]);
    }

    private void renderResult() {
        if (mCurrentEffect != 1) {
            // if no effect is chosen, just render the original bitmap
            mTexRenderer.renderTexture(mTextures[1]);
        }
        else {
            // render the result of applyEffect()
            mTexRenderer.renderTexture(mTextures[0]);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (!mInitialized) {
            //Only need to do this once
            mEffectContext = EffectContext.createWithCurrentGlContext();
            mTexRenderer.init();
            loadTextures();
            mInitialized = true;
        }
        
        if (mCurrentEffect != 1) { //normal
            //if an effect is chosen initialize it and apply it to the texture
            initEffect();
            applyEffect();
        }
        renderResult();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (mTexRenderer != null) {
            mTexRenderer.updateViewSize(width, height);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//Toast.makeText(this, "" + item.getItemId(), Toast.LENGTH_SHORT).show();
        //setCurrentEffect(item.getItemId());
        //mEffectView.requestRender();
        return true;
    }
    
    public class ImageAdapter extends BaseAdapter {

    	private Context ctx;
    	int imageBackground;
    	
    	public ImageAdapter(Context c) {
			ctx = c;
			TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
			imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
			ta.recycle();
		}

		@Override
    	public int getCount() {
    		
    		return pics.length;
    	}

    	@Override
    	public Object getItem(int arg0) {
    		
    		return arg0;
    	}

    	@Override
    	public long getItemId(int arg0) {
    		
    		return arg0;
    	}

    	@SuppressWarnings("deprecation")
		@Override
    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		ImageView iv = new ImageView(ctx);
    		iv.setImageResource(pics[arg0]);
    		iv.setScaleType(ImageView.ScaleType.FIT_XY);
    		iv.setLayoutParams(new Gallery.LayoutParams(150,150));
    		iv.setBackgroundResource(R.drawable.background);
    		return iv;
    	}

    }
}
