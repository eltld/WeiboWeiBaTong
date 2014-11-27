package org.zarroboogs.weibo.login.javabean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class LoginResultHelper {

	private boolean mIsLogin = false;
	private String mErrorReason = "";
	private String mResponseString;
	private String mUserPageUrl;

	public LoginResultHelper(HttpEntity entity) {
		try {
			String entityString = EntityUtils.toString(entity, "GBK");
			
			String result = URLDecoder.decode(entityString, "GBK");
			result = URLDecoder.decode(entityString, "GBK");
			
			Log.d("LoginResultHelper ", "" + result);
			//http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack&sudaref=widget.weibo.com&
			//retcode=4038&reason=%B5%C7%C2%BC%B4%CE%CA%FD%B9%FD%B6%E0%A1%A3%3Ca%20href%3D%22http%3A%2F%2Fhelp.weibo.com%2Ffaq%2Fq%2F85%2F12699%2312699%22%20target%3D%22_blank%22%3E%B2%E9%BF%B4%B0%EF%D6%FA%3C%2Fa%3E&#39;"/>
			if (result.contains("\"retcode\":0")) {
				mIsLogin = true;
			} else if (result
					.contains("reason=为了您的帐号安全，请输入验证码")) {
				// 为了您的帐号安全，请输入验证码
				mIsLogin = false;
				mErrorReason = "为了您的帐号安全，请输入验证码";
			} else if (result.contains("登录名或密码错误")) {
				// 用户名或密码错误
				mIsLogin = false;
				mErrorReason = "登录名或密码错误";
			} else if (result.contains("输入的验证码不正确")) {
				mIsLogin = false;
				mErrorReason = "输入的验证码不正确";
			} else if (result.contains("请输入正确的密码")) {
			    mIsLogin = false;
                mErrorReason = "请输入正确的密码";
            }
			this.mResponseString = result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isLogin() {
		return mIsLogin;
	}

	public String getErrorReason() {
		return mErrorReason;
	}

	public String getResponseString() {
		return this.mResponseString;
	}

	public String getUserPageUrl() {
		if (mIsLogin) {
			String regx = "url=";
			String tmp = getResponseString().split(regx)[1];
			String url = tmp.split("retcode=0")[0] + "retcode=0";
			try {
				url = URLDecoder.decode(url, "GBK");
				url = URLDecoder.decode(url, "GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return url;
		}
		return null;
	}



}