package mj.data;

import java.sql.Date;

	/**
    * @ClassName: WXPayRetInfo
    * @Descriptio	微信支付回调成功返回信息
    * @author 13323659953@163.com
    * @date 2017年6月13日
    *
    */
public class OrderInfo {
	private int userid;
	private String order_id;
	private String plat_order_id;
	private int count;
	private int gold;
	private Date time;
	private int status;
	private int type;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPlat_order_id() {
		return plat_order_id;
	}
	public void setPlat_order_id(String plat_order_id) {
		this.plat_order_id = plat_order_id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "OrderInfo [userid=" + userid + ", order_id=" + order_id + ", plat_order_id=" + plat_order_id
				+ ", count=" + count + ", gold=" + gold + ", time=" + time + ", status=" + status + ", type=" + type
				+ "]";
	}
	
	
	
	
}

