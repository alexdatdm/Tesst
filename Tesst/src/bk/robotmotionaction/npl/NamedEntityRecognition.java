package bk.robotmotionaction.npl;

import android.util.Log;

import com.fpt.api.nlp.response.NamedEntityRecognitionResponse;
import com.fpt.api.nlp.service.GeneralListener;

public class NamedEntityRecognition implements
		GeneralListener<NamedEntityRecognitionResponse> {
	public static String result;
	public static boolean lock = false;

	public NamedEntityRecognition() {
		result = new String("-1");
	}

	@Override
	public void onError(Exception e) {
		Log.d("On error:", e.getMessage());
	}

	@Override
	public void onSuccess(NamedEntityRecognitionResponse response) {
		result = response.name_entity_recognize_text.toString();
		lock = false;
	}

}
