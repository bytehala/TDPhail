package com.noobgrammer.game;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

public class myGame extends BaseGameActivity {
	
	
	private final static int LAYER_CANVASS = 0;
	private final static int LAYER_UI = LAYER_CANVASS + 1;
	private final static int NUM_LAYERS = LAYER_UI + 1;

	final int mCameraWidth = 800;   
	final int mCameraHeight = 480;
	public Scene mScene;
	private Scene[] sceneLayers;
	
	Point prevPoint, currPoint;


	@Override
	public EngineOptions onCreateEngineOptions() {
		Camera mCamera = new Camera(0, 0, mCameraWidth, mCameraHeight);
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(mCameraWidth, mCameraHeight), mCamera);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)	throws Exception {

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)	throws Exception {
		//TODO: add a clear all button
		mScene = new Scene();
//		mScene.setBackground(new Background(0, 0, 1));
		mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
				TouchEvent p = pSceneTouchEvent;
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
				{
					prevPoint = new Point();
					prevPoint.x = (int) p.getX();
					prevPoint.y = (int) p.getY();
					prevPoint.isReady = true;
				}
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
					
					currPoint = new Point();
					currPoint.x = (int) p.getX();
					currPoint.y = (int) p.getY();
					currPoint.isReady = true;
					
					if(prevPoint!=null && prevPoint.isReady)
					{
						Line l = new Line(currPoint.x, currPoint.y, prevPoint.x, prevPoint.y, myGame.this.getVertexBufferObjectManager());
						l.setColor(0.5f, 1f, 0.3f);
//						mScene.getChild(LAYER_CANVASS).attachChild(l);
						mScene.attachChild(l);
					}
					prevPoint = currPoint;
				}
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					prevPoint.isReady = false;
				}
				return true;
			}
		});

		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}


	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback)	throws Exception {

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	class Point
	{
		boolean isReady;
		int x;
		int y;
	}

}
