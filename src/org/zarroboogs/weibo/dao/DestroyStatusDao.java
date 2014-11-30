package org.zarroboogs.weibo.dao;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.zarroboogs.util.net.HttpUtility;
import org.zarroboogs.util.net.WeiboException;
import org.zarroboogs.util.net.HttpUtility.HttpMethod;
import org.zarroboogs.utils.AppLoggerUtils;
import org.zarroboogs.utils.WeiBoURLs;
import org.zarroboogs.weibo.bean.MessageBean;

import java.util.HashMap;
import java.util.Map;

/**
 * User: qii Date: 12-9-10
 */
public class DestroyStatusDao {

	private String access_token;
	private String id;

	public DestroyStatusDao(String access_token, String id) {
		this.access_token = access_token;
		this.id = id;
	}

	public boolean destroy() throws WeiboException {
		String url = WeiBoURLs.STATUSES_DESTROY;
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", access_token);
		map.put("id", id);

		String jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Post, url, map);
		Gson gson = new Gson();

		try {
			MessageBean value = gson.fromJson(jsonData, MessageBean.class);
		} catch (JsonSyntaxException e) {
			AppLoggerUtils.e(e.getMessage());
			return false;
		}

		return true;

	}
}
