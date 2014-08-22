package bk.robotmotionaction.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import bk.robotmotionaction.demo.SendActivity.HttpAsyncTask;

import com.fpt.lib.asr.Languages;
import com.fpt.lib.asr.Result;
import com.fpt.lib.asr.SpeakToText;
import com.fpt.lib.asr.SpeakToTextListener;
import com.fpt.robot.RobotException;
import com.fpt.robot.app.RobotActivity;
import com.fpt.robot.motion.RobotGesture;
import com.fpt.robot.motion.RobotMotionAction;
import com.fpt.robot.motion.RobotMotionStiffnessController;
import com.fpt.robot.motion.RobotPosture;
import com.fpt.robot.tts.RobotTextToSpeech;

public class MoneyActivity extends RobotActivity implements OnClickListener,
		SpeakToTextListener {
	Button btRecord, btStop, crouch, standup;
	TextView tvLog;
	SpeakToText stt;
	String textResponse;
	private static final String TAG = "MotionActions";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);

		new HttpAsyncTask()
				.execute("http://tech.fpt.com.vn/AIML/api/bots/53e0fa87e4b04a9d4459b172/chat?request="
						+ "CHAO%5FQUY%5FKHACH"
						+ "&token=eb086f7b-3b11-4ab9-984f-7f3f1f36147a");
		btRecord = (Button) findViewById(R.id.btRecord);
		crouch = (Button) findViewById(R.id.button1);
		standup = (Button) findViewById(R.id.button2);
		btRecord.setOnClickListener(this);
		crouch.setOnClickListener(this);
		standup.setOnClickListener(this);
		stt = new SpeakToText(Languages.VIETNAMESE, this);
//		 initNPL();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btRecord:
			new Thread(new Runnable() {
				@Override
				public void run() {
					stt.recognize(1000, 5000);
				}
			}).start();
			break;
		case R.id.button1:
			new Thread(new Runnable() {
				@Override
				public void run() {
					standUp();
				}
			}).start();
			break;
		case R.id.button2:
			new Thread(new Runnable() {
				@Override
				public void run() {
					crouch();
				}
			}).start();
			break;
		default:
			break;
		}
	}

	private ProgressDialog progressDialog = null;

	protected void showProgress(final String message) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (progressDialog == null) {
					progressDialog = new ProgressDialog(MoneyActivity.this);
				}
				// no title
				if (message != null) {
					progressDialog.setMessage(message);
				}
				progressDialog.setIndeterminate(true);
				progressDialog.setCancelable(true);
				progressDialog.show();
			}
		});
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void standUp() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (getRobot() == null) {
					scan();
				} else {
					boolean result = false;
					try {
						showProgress("Standing up...");
						result = RobotMotionAction.standUp(getRobot(), 0.5f);
					} catch (RobotException e) {
						cancelProgress();

						e.printStackTrace();
						return;
					}
					cancelProgress();

				}
			}
		}).start();
	}

	private void crouch() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (getRobot() == null) {
					scan();
				} else {
					boolean result = false;
					try {
						showProgress("Crouch...");
						result = RobotPosture.goToPosture(getRobot(), "Crouch",
								0.5f);
						// set motor off
						RobotMotionStiffnessController.rest(getRobot());
					} catch (RobotException e) {
						cancelProgress();

						e.printStackTrace();
						return;
					}
					cancelProgress();

				}
			}
		}).start();
	}

	protected void cancelProgress() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
			}
		});
	}

	@Override
	public void onWaiting() {
		showProgress("Waiting sound...");
	}

	@Override
	public void onRecording() {
		showProgress("Recording...");
	}

	@Override
	public void onError(Exception ex) {
		setText("on error:" + ex.getMessage());
		cancelProgress();
	}

	@Override
	public void onTimeout() {
		setText("on timeout \n");
		cancelProgress();
	}

	private void setText(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Log.d("Text", text);
			}
		});
	}

	@Override
	public void onProcessing() {
		showProgress("Processing...");

	}

	@Override
	public void onResult(Result result) {
		String request = result.result[0].alternative[0].transcript.toString()
				.replace(" ", "%20");
		Log.d("Http Get Response:", request);
		new HttpAsyncTask()
				.execute("http://tech.fpt.com.vn/AIML/api/bots/53e0fa87e4b04a9d4459b172/chat?request="
						+ request
						+ "&token=eb086f7b-3b11-4ab9-984f-7f3f1f36147a");
		setText("Result: " + result.result[0].alternative[0].transcript + "\n");
		cancelProgress();
	}

	@Override
	public void onStopped() {
		setText("On stopped");
		cancelProgress();
	}

	public class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		@Override
		protected void onPostExecute(String result) {

			try {
				JSONObject jsonObj = new JSONObject(result);
				textResponse = jsonObj.getString("response");
				Toast.makeText(getApplicationContext(),
						textResponse.toString(), Toast.LENGTH_LONG).show();
//				if (getRobot() == null) {
//					scan();
//				} else {
//
//					new Thread(new Runnable() {
//
//						@Override
//						public void run() {
//							try {
//								String[] gt;
//
//								gt = RobotGesture.getGestureList(getRobot());
//								Random rn = new Random();
//								int position = rn.nextInt(gt.length) + 1;
//								RobotGesture.runGesture(getRobot(),
//										gt[position]);
//							} catch (RobotException e) {
//								e.printStackTrace();
//							}
//						}
//					}).start();
//					new Thread(new Runnable() {
//
//						@Override
//						public void run() {
//							try {
//								RobotTextToSpeech.say(getRobot(),
//										textResponse.toString(),
//										RobotTextToSpeech.ROBOT_TTS_LANG_VI);
//							} catch (RobotException e) {
//								e.printStackTrace();
//							}
//						}
//					}).start();
//				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	public static String GET(String url) {
		InputStream inputStream = null;
		String result = "";
		try {

			HttpClient httpclient = new DefaultHttpClient();

			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

}
