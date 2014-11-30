package org.zarroboogs.weibo.fragment;

import org.zarroboogs.weibo.Constances;
import org.zarroboogs.weibo.R;
import org.zarroboogs.weibo.bean.UserBean;
import org.zarroboogs.weibo.bean.UserListBean;
import org.zarroboogs.weibo.setting.SettingUtils;
import org.zarroboogs.weibo.support.utils.AppConfig;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.List;

/**
 * User: qii Date: 12-11-10
 */
public abstract class AbstractFriendsFanListFragment extends AbstractUserListFragment {

	public AbstractFriendsFanListFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setInsets(getActivity(), container);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public static void setInsets(Activity context, View view) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
			return;
		SystemBarTintManager tintManager = new SystemBarTintManager(context);
		SystemBarTintManager.SystemBarConfig config = tintManager
				.getConfig();
		view.setPadding(0, config.getPixelInsetTop(true),
				config.getPixelInsetRight(), config.getPixelInsetBottom());
	}
	
	// this api has bug, check cursor before add data
	@Override
	protected void oldUserLoaderSuccessCallback(UserListBean newValue) {
		if (newValue != null && newValue.getUsers().size() > 0 && newValue.getPrevious_cursor() != bean.getPrevious_cursor()) {
			List<UserBean> list = newValue.getUsers();
			getList().getUsers().addAll(list);
			bean.setNext_cursor(newValue.getNext_cursor());
			buildActionBarSubtitle();
		}

	}

	@Override
	protected void newUserLoaderSuccessCallback() {
		buildActionBarSubtitle();
	}

	protected void buildActionBarSubtitle() {
		if (!TextUtils.isEmpty(getCurrentUser().getFriends_count())) {
			int size = Integer.valueOf(getCurrentUser().getFriends_count());
			int newSize = bean.getTotal_number();
			String number = "";
			if (size >= newSize) {
				number = bean.getUsers().size() + "/" + size;
			} else {
				number = bean.getUsers().size() + "/" + newSize;
			}
			getActivity().getActionBar().setSubtitle(number);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		switch (getCurrentState(savedInstanceState)) {
		case FIRST_TIME_START:
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (getActivity() != null) {
						pullToRefreshListView.setRefreshing();
						loadNewMsg();
					}

				}
			}, AppConfig.REFRESH_DELAYED_MILL_SECOND_TIME);

			break;
		case SCREEN_ROTATE:
			// nothing
			refreshLayout(bean);
			break;
		case ACTIVITY_DESTROY_AND_CREATE:
			clearAndReplaceValue((UserListBean) savedInstanceState.getParcelable(Constances.BEAN));
			getAdapter().notifyDataSetChanged();
			break;
		}

		refreshLayout(bean);

		getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

		if (SettingUtils.isFollowingOrFanListFirstShow()) {
			new AlertDialog.Builder(getActivity()).setTitle(R.string.tip).setMessage(R.string.following_and_fan_list_tip)
					.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
		}

	}

	protected abstract UserBean getCurrentUser();

}