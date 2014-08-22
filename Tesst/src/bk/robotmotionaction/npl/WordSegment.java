package bk.robotmotionaction.npl;

import android.util.Log;

import com.fpt.api.nlp.response.WordSegmentResponse;
import com.fpt.api.nlp.service.GeneralListener;

public class WordSegment implements GeneralListener<WordSegmentResponse> {
	public static String result;
	public static boolean lock = false;

	public WordSegment() {
		result = new String("-1");
	}

	@Override
	public void onError(Exception e) {
		Log.d("On error:", e.getMessage());
	}

	@Override
	public void onSuccess(WordSegmentResponse response) {

		result = response.word_segmented_text.toString();
		lock = false;
	}

}
