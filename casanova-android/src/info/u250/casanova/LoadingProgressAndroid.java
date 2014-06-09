package info.u250.casanova;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class LoadingProgressAndroid {
	ProgressDialog pDialog;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pDialog.dismiss();
		}
	};

	public LoadingProgressAndroid(Cas main) {
		pDialog = new ProgressDialog(main);
		pDialog.setCancelable(false);
		
	}

	public void showLoading() {
		pDialog.show();
		Window window = pDialog.getWindow();
		window.setContentView(R.layout.loading);
	}

	public void hideLoading() {
		handler.sendEmptyMessage(0);
	}

}
