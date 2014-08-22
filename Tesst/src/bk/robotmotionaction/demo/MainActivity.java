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

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import bk.robotmotionaction.demo.SendActivity.HttpAsyncTask;

import com.fpt.robot.RobotException;
import com.fpt.robot.app.RobotActivity;
import com.fpt.robot.motion.RobotGesture;
import com.fpt.robot.tts.RobotTextToSpeech;

public class MainActivity extends RobotActivity implements OnClickListener {
	EditText edt;
	Button rutTien, guiTien, chuyenKhoan;
	static String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// setFont

		String fontPath = "fonts/TIMES.TTF";
		TextView nameBank = (TextView) findViewById(R.id.nameBank);
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		nameBank.setTypeface(tf);

		rutTien = (Button) findViewById(R.id.rutTien);
		rutTien.setOnClickListener(this);

		guiTien = (Button) findViewById(R.id.guiTien);
		guiTien.setOnClickListener(this);

		chuyenKhoan = (Button) findViewById(R.id.chuyenKhoan);
		chuyenKhoan.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guiTien:
			Intent guiTienIntent = new Intent(this, SendActivity.class);
			startActivity(guiTienIntent);
			break;
		case R.id.rutTien:
			Intent rutTienIntent = new Intent(this, SendActivity.class);
			startActivity(rutTienIntent);
			break;
		case R.id.chuyenKhoan:
			Intent chuyenKhoanIntent = new Intent(this, SendActivity.class);
			startActivity(chuyenKhoanIntent);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
