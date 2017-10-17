package game.scene.douniu.net;

/**
 * 
    * @ClassName: ErrorMsgType
    * @Description: 不需修改
    * @author 13323659953@163.com
    * @date 2017年6月28日
    *
 */
public enum ErrorMsgType {
	NULL,
	ERROR_MSG_TYPE_LOGIN,
	ERROR_MSG_TYPE_EQUIP,
	ERROR_MSG_TYPE_MAP,
	ERROR_MSG_TYPE_PET,
	ERROR_MSG_TYPE_RDOWN,
	ERROR_MSG_TYPE_MARK,
	ERROR_MSG_TYPE_MAX;
	public static final ErrorMsgType valueOf(int i) {
		if (i < 1 || i >= values().length) {
			return NULL;
		}
		return values()[i];
	}
}
