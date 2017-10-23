package majiang.client.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.forkjoin.apikit.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import game.boss.msg.WXPayPxMsg;
import majiang.client.boss.BossClient;
import majiang.client.boss.BossClientHandler;
import majiang.client.model.UserAccount;
import majiang.client.model.WeixinUserInfo;
import majiang.client.services.AccountService;
import majiang.client.services.WeiXinService;
import majiang.client.services.WeixinLoginService;

/**
 * @author zuoge85@gmail.com on 2016/12/6.
 */
@Controller
public class IndexController {
    private static final Logger logger = LogManager.getLogger(IndexController.class);
    
    @Value("${site.title}")
    private String title;

    @Value("${site.serverUrl}")
    private String serverUrl;

    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private WeixinLoginService weiXinLoginService;

    @Autowired
    private AccountService accountService;
    

    @GetMapping({"/", "/index"})
    private String index(
            @CookieValue(name = AccountService.ACCOUNT_TOKEN_NAME, required = false) String accountToken,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        UserAccount userAccount = accountService.checkLogin(accountToken);
        String agent = request.getHeader("user-agent");
        model.addAttribute("account", userAccount);
        model.addAttribute("version", "v1");
        model.addAttribute("title", title);
        model.addAttribute("serverUrl", serverUrl);

        boolean isWeixin = agent.contains("MicroMessenger");
//      return "redirect:/hello";
        if (userAccount != null) {
            model.addAttribute("userEncrypt", accountService.checkLoginToEncrypt(userAccount));
        }
        if (isWeixin) {
            ObjectNode weixinConfig;
            if (request.getQueryString() == null) {
                weixinConfig = weiXinService.weixinConifg(request.getRequestURI());
            } else {
                weixinConfig = weiXinService.weixinConifg(request.getRequestURI() + "?" + request.getQueryString());
            }

            model.addAttribute("weixinConfig", weixinConfig);
            return "weixinIndex";
        } else {
            return "index";
        }
    }
    

    @GetMapping(value = "/icon/{uuid}")
    private void icon(
            @PathVariable String uuid, HttpServletResponse res
    ) throws Exception {
        byte[] icon = accountService.getIcon(uuid);
        if (icon != null) {
            res.getOutputStream().write(icon);
            res.flushBuffer();
        }
    }
    
   //http://122.114.150.35:8080/wxpay
    @RequestMapping(value="/wxpay") 
	public String wxPay(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException{
		logger.info("微信支付回调");
    	BossClient bossClient = null;
		InputStream in = request.getInputStream();
		try{
			bossClient = new BossClient("127.0.0.1", 11000, new BossClientHandler());
			bossClient.connect();
			
			Map<Object,Object> map = new HashMap<Object, Object>();
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList){
				map.put(e.getName(), e.getText());
			}
			if(map.get("return_code").equals("SUCCESS")){
				String trade_on = (String) map.get("out_trade_no");
				int tatol_fee = Integer.parseInt((String)(map.get("total_fee")));
				WXPayPxMsg pxmsg = new WXPayPxMsg(WXPayPxMsg.ID,(short)1,trade_on,tatol_fee);
				bossClient.writeAndFlush(pxmsg);
				logger.info("wx huidiao success:to boss WXPayPxMsg:"+pxmsg);
			}else{
				bossClient.writeAndFlush(new WXPayPxMsg(WXPayPxMsg.ID));
				logger.info("pay faild:");
			}
			String ret = "<xml>"
							+ "<return_code><![CDATA["+map.get("return_code")+"]]></return_code>"
						+ "</xml>";
			response.getOutputStream().write(ret.getBytes("utf-8"));
			
		}catch (Exception e) {
			logger.error("wxPay HuiDiao error:{"+e.getMessage()+"}");
			bossClient.writeAndFlush(new WXPayPxMsg(10));
		}finally{
			try {
				if(null!=bossClient)
					bossClient.close();
				if(null!=in){
					in.close();
					in = null;
				}
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
    
   
    @RequestMapping(value="/alipay")
    public String aliPayHuiDiao(HttpServletRequest request,HttpServletResponse response){
    	Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		BossClient bossClient = null;
		logger.info("支付宝支付回调");
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  }
		  //乱码解决，这段代码在出现乱码时使用。
		  //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		  params.put(name, valueStr);
		 }
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		try {
			if(params!=null && params.size()>0 &&params.get("out_trade_no")!=null){
				bossClient = new BossClient("127.0.0.1", 11000, new BossClientHandler());
				bossClient.connect();
				int money = (int) (Double.valueOf(params.get("total_amount"))*100D);
				bossClient.writeAndFlush(new WXPayPxMsg(WXPayPxMsg.ID,(short)1,params.get("out_trade_no"),money));
			}
			logger.info("alipay huidiao  params:"+params);
		} catch (Exception e) {
			logger.error("alipay HuiDiao eroor:{"+e.getMessage()+"}");
		}finally{
			try {
				if(null!=bossClient)
					bossClient.close();
				
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return null;
    }

    @GetMapping("/weixinLogin")
    private String weixinLogin(
            @CookieValue(name = AccountService.ACCOUNT_TOKEN_NAME, required = false) String accountToken,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        UserAccount userAccount = accountService.checkLogin(accountToken);
        String agent = request.getHeader("user-agent");
        if (userAccount == null && agent != null && agent.contains("MicroMessenger")) {

            Optional<WeixinUserInfo> userAccountOpt = weiXinLoginService.checkWeixin(request, response);
            if (null == userAccountOpt) {
                return "weixinLogin";
            }
            WeixinUserInfo weixinUserInfo = userAccountOpt.orElse(null);
            userAccount = accountService.login(weixinUserInfo, response);
        }
        request.setAttribute("userAccount", userAccount);
        return "weixinLogin";
    }

    @PostMapping("/appWeixinLogin")
    @ResponseBody
    private Result<String> appWeixinLogin(
            @RequestParam("data") String data,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        logger.debug("appWeixinLogin:{}", data);
        Map.Entry<WeixinUserInfo, String> resultEntry = weiXinLoginService.checkAppWeixin(
                data
        );
        logger.info("data:{}"+data);

        if (resultEntry.getKey() != null) {
            WeixinUserInfo weixinUserInfo = resultEntry.getKey();
            UserAccount userAccount = accountService.login(weixinUserInfo, response);
            return Result.createSuccess(
                    accountService.checkLoginToEncrypt(userAccount)
            );
        } else {
            return Result.createError(resultEntry.getValue());
        }
    }
    
    @PostMapping("/version")
    @ResponseBody
    private Result<String> version(
    		@RequestParam("version") String version,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException{
    	response.getWriter().write("1.0.4");
		return null;
    }
}