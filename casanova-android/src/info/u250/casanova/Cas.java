package info.u250.casanova;

import info.u250.c2d.engine.Engine;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Cas extends AndroidApplication {
	private static final String XID = "ca-app-pub-0429520335266267/9030641132";
	protected AdView adView;
	private LoadingProgressAndroid loadingView;
	RelativeLayout layout;
	public LoadingProgressAndroid getLoadingView() {
		return loadingView;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = new RelativeLayout(this);
		loadingView = new LoadingProgressAndroid(this);
		
		adView = new AdView(this); // Put in your secret key here
        adView.setAdUnitId(XID);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder().build());
        
		Casanova game = new Casanova(new LoadingFeedbackAndroid(this));
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.resolutionStrategy = new RatioResolutionStrategy(Engine.getWidth(), Engine.getHeight());
		
		View main = initializeForView(game, config);
		layout.addView(main);
		RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(adView, adParams);
		setContentView(layout);
		loadingView.showLoading();
		
		InterstitialAd mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(XID);
        mInterstitial.loadAd(new AdRequest.Builder().build());
        mInterstitial.show();

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				Gdx.input.getInputProcessor().keyDown(Keys.BACK);
			} catch (Exception ex) {
			}
			return true;
		}
		return false;
	}
}
