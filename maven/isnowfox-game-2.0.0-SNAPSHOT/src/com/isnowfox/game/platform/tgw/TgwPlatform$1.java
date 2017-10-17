// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TgwPlatform.java

package com.isnowfox.game.platform.tgw;

import com.isnowfox.game.platform.ApiCallback;
import com.isnowfox.util.JsonUtils;
import com.isnowfox.util.ObjectUtils;
import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;
import gnu.trove.map.hash.TIntIntHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;

// Referenced classes of package com.isnowfox.game.platform.tgw:
//			TgwPlatform

class TgwPlatform$1
	implements Runnable {

	final String val$openId;
	final ApiCallback val$callback;
	final int val$rmb;
	final TgwPlatform this$0;

	public void run() {
		Map objectMap = (Map)loginInfo.get(val$openId);
		if (objectMap == null) {
			TgwPlatform.access$000().error("²»´æÔÚÍæ¼ÒÐÅÏ¢!");
			val$callback.callback(false, null);
			return;
		}
		if (!TgwPlatform.access$100().containsKey(val$rmb)) {
			TgwPlatform.access$000().error("´íÎóµÄÌ×²Í!");
			val$callback.callback(false, null);
			return;
		}
		int gold = TgwPlatform.access$100().get(val$rmb);
		OpenApiV3 api = new OpenApiV3(TgwPlatform.access$200(TgwPlatform.this), TgwPlatform.access$300(TgwPlatform.this));
		api.setServerName(TgwPlatform.access$400(TgwPlatform.this));
		String scriptName = "/v3/pay/buy_goods";
		String protocol = "https";
		HashMap params = new HashMap();
		params.put("openid", val$openId);
		params.put("openkey", String.valueOf(objectMap.get("openkey")));
		params.put("pf", String.valueOf(objectMap.get("pf")));
		params.put("pfkey", String.valueOf(objectMap.get("pfkey")));
		params.put("amt", (new StringBuilder()).append(val$rmb * 10).append("*1").toString());
		params.put("ts", String.valueOf(System.currentTimeMillis() / 1000L));
		params.put("payitem", (new StringBuilder()).append("").append(val$rmb).append("*").append(val$rmb * 10).append("*1").toString());
		params.put("appmode", "1");
		params.put("goodsmeta", (new StringBuilder()).append(gold).append("½ð±Ò±¦Ïä*").append(gold).append("½ð±Ò±¦Ïä£¡").toString());
		params.put("goodsurl", (new StringBuilder()).append("http://app1104202590.imgcache.qzoneapp.com/app1104202590/img/pay").append(val$rmb).append(".png").toString());
		params.put("zoneid", TgwPlatform.access$500(TgwPlatform.this));
		try {
			String resp = api.api(scriptName, params, protocol);
			Map map = (Map)JsonUtils.deserialize(resp, java/util/HashMap);
			if (0 == ObjectUtils.toInt(map.get("ret"))) {
				val$callback.callback(true, map);
			} else {
				TgwPlatform.access$000().error("³äÖµ´íÎó´íÎó:{}", resp);
				val$callback.callback(false, null);
			}
		}
		catch (OpensnsException e) {
			TgwPlatform.access$000().error("Request Failed.msg:", e.getMessage(), e);
			val$callback.callback(false, null);
		}
	}

	TgwPlatform$1() {
		this.this$0 = this$0;
		val$openId = s;
		val$callback = apicallback;
		val$rmb = I.this;
		super();
	}
}
