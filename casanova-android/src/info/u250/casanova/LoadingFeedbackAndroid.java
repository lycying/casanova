package info.u250.casanova;


public class LoadingFeedbackAndroid implements LoadingFeedback {
	Cas main;
	public LoadingFeedbackAndroid(Cas main){
		this.main = main;
	}
	@Override
	public void feedback(final float percent) {
	
	}

	@Override
	public void finish() {
		main.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				main.getLoadingView().hideLoading();
			}
		});
		
	}

}
