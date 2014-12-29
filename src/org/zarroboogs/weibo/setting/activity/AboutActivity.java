package org.zarroboogs.weibo.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.zarroboogs.weibo.R;
import org.zarroboogs.weibo.activity.AbstractAppActivity;
import org.zarroboogs.weibo.setting.fragment.AboutFragment;

import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends AbstractAppActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity_layout);
//		getActionBar().setDisplayShowHomeEnabled(false);
//		getActionBar().setDisplayShowTitleEnabled(true);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setTitle(getString(R.string.pref_about_title));

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart(this.getClass().getName());
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName());
		MobclickAgent.onPause(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(this, SettingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		}
		return false;
	}

}
