package mj.net.message.game;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * @Package:mj.net.message.game
 * @ClassName: OperationDingPao
 * @Description: TODO
 * @author XuKaituo
 * @date May 17, 2017  10:24:10 AM
 */
public class OperationDingPao extends AbstractMessage{
    public static final int TYPE             = 1;
    public static final int ID               = 28;
    
    private int userId ; 
    private int paoCount;
    
    public OperationDingPao(){
        
    }
    public OperationDingPao(int userId , int paoCount){
    	this.userId = userId ;
        this.paoCount=paoCount;
    }
    
    @Override
    public void decode(Input in)  throws IOException, ProtocolException {
        userId = in.readInt();
    	paoCount = in.readInt();
    }

    @Override
    public void encode(Output out)  throws IOException, ProtocolException {
    	out.writeInt(userId);
        out.writeInt(paoCount);
    }
    
    public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
    public String toString() {
        return "OperationDingPao [userId=" + userId + " , paoCount=" + paoCount + " ]";
    }
    
    public int getPaoCount() {
        return paoCount;
    }
    public void setPaoCount(int paoCount) {
        this.paoCount = paoCount;
    }
    
    @Override
    public final int getMessageType() {
        return TYPE;
    }

    @Override
    public final int getMessageId() {
        return ID;
    }
}
