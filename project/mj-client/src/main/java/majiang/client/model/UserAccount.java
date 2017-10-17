package majiang.client.model;

/**
 * @author zuoge85@gmail.com on 2016/12/7.
 */
public class UserAccount {
    private LoginTokenInfo loginTokenInfo;
    private WeixinUserInfo weixinUserInfo;

    public UserAccount(LoginTokenInfo loginTokenInfo, WeixinUserInfo weixinUserInfo) {
        this.loginTokenInfo = loginTokenInfo;
        this.weixinUserInfo = weixinUserInfo;
    }

    public LoginTokenInfo getLoginTokenInfo() {
        return loginTokenInfo;
    }

    public void setLoginTokenInfo(LoginTokenInfo loginTokenInfo) {
        this.loginTokenInfo = loginTokenInfo;
    }

    public WeixinUserInfo getWeixinUserInfo() {
        return weixinUserInfo;
    }

    public void setWeixinUserInfo(WeixinUserInfo weixinUserInfo) {
        this.weixinUserInfo = weixinUserInfo;
    }

    @Override
    public String toString() {
        return "AdminAccount{" +
                "loginTokenInfo=" + loginTokenInfo +
                ", weixinUserInfo=" + weixinUserInfo +
                '}';
    }


}
