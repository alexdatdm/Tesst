package bk.robotmotionaction.npl;

import android.util.Log;

import com.fpt.api.nlp.response.TrueCasingResponse;
import com.fpt.api.nlp.service.GeneralListener;

public class TrueCasing implements GeneralListener<TrueCasingResponse> {
	public static String result;
	public static boolean lock = false;

	public TrueCasing() {
		result = new String("-1");
	}

	@Override
	public void onError(Exception e) {
		Log.d("On error:", e.getMessage());
	}

	@Override
	public void onSuccess(TrueCasingResponse response) {
		result = response.truecased_text.toString();
		lock = false;

	}

}
