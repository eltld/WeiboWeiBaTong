package org.zarroboogs.weibo.activity;

import com.umeng.analytics.MobclickAgent;

/**
 * User: qii Date: 12-8-15
 */
public class MyInfoActivity extends UserInfoActivity {
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
}
