// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TgwPlatform.java

package com.isnowfox.game.platform.tgw;

import com.isnowfox.game.platform.ApiCallback;
import com.isnowfox.game.platform.GamePayResult;
import com.isnowfox.util.JsonUtils;
import com.isnowfox.util.ObjectUtils;
import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

// Referenced classes of package com.isnowfox.game.platform.tgw:
//			TgwPlatform

class TgwPlatform$2
	implements ApiCallback {

	final Map val$allParams;
	final String val$openId;
	final Map val$objectMap;
	final TgwPlatform this$0;

	public void callback(boolean result, Map map) {
		if (!result) {
			return;
		} else {
			Runnable runnable = new Runnable() {

				private volatile int nums;
				final TgwPlatform._cls2 this$1;

				public void run() {
					TgwPlatform.access$000().info("开始确认回调不存在玩家信息!{}", allParams);
					OpenApiV3 api = new OpenApiV3(TgwPlatform.access$200(this$0), TgwPlatform.access$300(this$0));
					api.setServerName(TgwPlatform.access$400(this$0));
					String scriptName = "/v3/pay/confirm_delivery";
					String protocol = "https";
					HashMap params = new HashMap();
					params.put("openid", openId);
					params.put("openkey", String.valueOf(objectMap.get("openkey")));
					params.put("pf", String.valueOf(objectMap.get("pf")));
					params.put("ts", String.valueOf(System.currentTimeMillis() / 1000L));
					params.put("payitem", allParams.get("payitem"));
					params.put("token_id", allParams.get("token"));
					String billno = (String)allParams.get("billno");
					params.put("billno", billno);
					params.put("zoneid", allParams.get("zoneid"));
					params.put("providetype", allParams.get("providetype"));
					params.put("provide_errno", allParams.get("provide_errno"));
					params.put("amt", allParams.get("amt"));
					params.put("payamt_coins", allParams.get("payamt_coins"));
					params.put("pubacct_payamt_coins", allParams.get("pubacct_payamt_coins"));
					try {
						String resp = api.api(scriptName, params, protocol);
						Map map = (Map)JsonUtils.deserialize(resp, java/util/HashMap);
						TgwPlatform.access$000().info("参数:{}", map);
						if (0 == ObjectUtils.toInt(map.get("ret"))) {
							TgwPlatform.access$000().error("充值确认成功：{}", resp);
							TgwPlatform.access$600(this$0).paySuccess(openId, billno);
						} else
						if (nums > 0) {
							nums--;
							TgwPlatform.access$000().error("充值错误错误:{}，准备重试", resp);
							TgwPlatform.access$700(this$0).schedule(this, 5L, TimeUnit.SECONDS);
						} else {
							TgwPlatform.access$000().error(" 充值失败10次:{}，不在重试", resp);
						}
					}
					catch (OpensnsException e) {
						TgwPlatform.access$000().error("Request Failed.msg:", e.getMessage(), e);
					}
				}

			 {
				this.this$1 = TgwPlatform._cls2.this;
				super();
				nums = 10;
			}
			};
			TgwPlatform.access$700(TgwPlatform.this).schedule(runnable, 2L, TimeUnit.SECONDS);
			return;
		}
	}

	TgwPlatform$2() {
		this.this$0 = this$0;
		val$allParams = map;
		val$openId = s;
		val$objectMap = Map.this;
		super();
	}
}
