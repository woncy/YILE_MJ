package game.boss.msg;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
    * @ClassName: WXPayPxMsg
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月14日
    *
    */

public class WXPayPxMsg extends AbstractSessionPxMsg{
		public static final int ID = 10;
		private short result;
		private String trade_no;
		private int money;
	    /**
	     * 创建一个新的实例 WXPayPxMsg.
	     *
	     * @param id
	     */
	    
	public WXPayPxMsg() {
		super(ID);
	}
	public WXPayPxMsg(int id) {
		super(id);
		this.result = 0;
		this.trade_no = "-1";
		this.money = -1;
	}
	public WXPayPxMsg(int id,short result,String trade_no,int money){
		this(id);
		this.result = result;
		this.trade_no = trade_no;
		this.money = money;
	}

		
		    /* (非 Javadoc)
		    * 
		    * 
		    * @param arg0
		    * @throws Exception
		    * @see com.isnowfox.game.proxy.message.PxMsg#encode(io.netty.buffer.ByteBuf)
		    */
		    
		@Override
		public void encode(ByteBuf out) throws Exception {
			out.writeShort(result);
			byte[] bytes = trade_no.getBytes("utf-8");
			out.writeShort(bytes.length);
			out.writeBytes(bytes);
			out.writeInt(money);
		}

		
		    /* (非 Javadoc)
		    * 
		    * 
		    * @param arg0
		    * @param arg1
		    * @throws Exception
		    * @see com.isnowfox.game.proxy.message.PxMsg#decode(io.netty.buffer.ByteBuf, io.netty.channel.ChannelHandlerContext)
		    */
		    
		@Override
		public void decode(ByteBuf in, ChannelHandlerContext arg1) throws Exception {
			result = in.readShort();
			short len = in.readShort();
			byte[] bytes = new byte[len];
			in.readBytes(bytes);
			trade_no=new String(bytes,"utf-8");
			money = in.readInt();
		}


		public String getTrade_no() {
			return trade_no;
		}


		public void setTrade_no(String trade_no) {
			this.trade_no = trade_no;
		}
		public short getResult() {
			return result;
		}
		public void setResult(short result) {
			this.result = result;
		}
		public int getMoney() {
			return money;
		}
		public void setMoney(int money) {
			this.money = money;
		}
		@Override
		public String toString() {
			return "WXPayPxMsg [result=" + result + ", trade_no=" + trade_no + ", money=" + money + "]";
		}
		
		
		
	
}

