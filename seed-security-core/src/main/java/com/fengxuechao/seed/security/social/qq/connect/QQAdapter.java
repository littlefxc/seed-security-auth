package com.fengxuechao.seed.security.social.qq.connect;

import com.fengxuechao.seed.security.social.qq.api.QQ;
import com.fengxuechao.seed.security.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ 用户信息 和 Spring Social 标准用户信息的适配
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * TODO 用于测试当前Api是否可用，QQ服务是否可用。
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
	 * 适配创建 {@link org.springframework.social.connect.Connection} 需要服务提供商QQ需要的信息
	 *
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // qq 没有个人主页
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

	/**
	 * 在某些服务提供商才会有这个功能与 profileUrl 属性有关，例如新浪微博（发送消息更新微博）。
	 * QQ 没有。
	 *
	 * @param api
	 * @param message
	 */
	@Override
    public void updateStatus(QQ api, String message) {
        //do noting
    }

}
