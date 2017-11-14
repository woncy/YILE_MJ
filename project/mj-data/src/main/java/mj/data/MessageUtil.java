package mj.data;

import java.io.IOException;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class MessageUtil {
	
	public static <T extends AbstractMessage> void decodeList(Input in,List<T> list,Class<T> clazz) throws IOException, ProtocolException{
		int len = in.readInt();
		if(len < 0){
			list = null;
		}else{
			for (int i = 0; i < len; i++) {
				try {
					T t = clazz.newInstance();
					t.decode(in);
					list.add(t);
				} catch (InstantiationException e) {
					
				} catch (IllegalAccessException e) {
					
				}
			}
		}
	}
	
	
	public static <T extends AbstractMessage> void encodeList(Output out,List<T> list) throws IOException, ProtocolException{
		int len = -1;
		if(list!=null){
			len = list.size();
		}
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			T t = list.get(i);
			t.encode(out);
		}
	}
}
