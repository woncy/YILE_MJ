package mj.net.message.login;


	import java.io.IOException;
	import com.isnowfox.core.io.Input;
	import com.isnowfox.core.io.Output;
	import com.isnowfox.core.io.ProtocolException;
	import com.isnowfox.core.net.message.AbstractMessage;
	/**
	    * @ClassName: WXPaySuccess
	    * @Description: 这里用一句话描述这个类的作用
	    * @author 13323659953@163.com
	    * @date 2017年6月12日
	    *
	    */

	public class WXPaySuccess extends AbstractMessage{
		public static final int TYPE			 = 7;
		public static final int ID				 = 29;
		//支付结果状态
		private boolean result;
		//支付金额
		private int money;
		private int goldCount;
		
		public WXPaySuccess() {
			super();
			
		}
		public WXPaySuccess(boolean result, int money,int goldCount) {
			super();
			this.result = result;
			this.money = money;
			this.goldCount = goldCount;
		}


		@Override
		public void decode(Input in) throws IOException, ProtocolException {
			result=in.readBoolean();
			money=in.readInt();
			goldCount = in.readInt();
		}

		
		    /* (非 Javadoc)
		    * 
		    * 
		    * @param arg0
		    * @throws IOException
		    * @throws ProtocolException
		    * @see com.isnowfox.core.net.message.Message#encode(com.isnowfox.core.io.Output)
		    */
		    
		@Override
		public void encode(Output out) throws IOException, ProtocolException {
			out.writeBoolean(result);
			out.writeInt(money);
			out.writeInt(goldCount);
		}

		
		    /* (非 Javadoc)
		    * 
		    * 
		    * @return
		    * @see com.isnowfox.core.net.message.Message#getMessageType()
		    */
		    
		@Override
		public int getMessageType() {
			return TYPE;
		}

		    /* (非 Javadoc)
		    * 
		    * 
		    * @return
		    * @see com.isnowfox.core.net.message.Message#getMessageId()
		    */
		    
		@Override
		public int getMessageId() {
			return ID;
		}
		
		@Override
		public String toString() {
			return "WXPaySuccess [result=" + result + ", money=" + money + ", goldCount=" + goldCount + "]";
		}

		
}


