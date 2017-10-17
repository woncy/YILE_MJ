package mj.net.message.game;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * @Package:mj.net.message.game
 * @ClassName: OperationDingPaoRet
 * @Description: TODO
 * @author XuKaituo
 * @date May 17, 2017  10:24:24 AM
 */
public class OperationDingPaoRet extends AbstractMessage{
    public static final int TYPE             = 1;
    public static final int ID               = 29;
    
    private int locationIndex ; 
    
    private int paoCount ; 
    
    public OperationDingPaoRet(int locationIndex , int paoCount){
    	this.locationIndex = locationIndex;
    	this.paoCount = paoCount;
    }

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}



	public int getPaoCount() {
		return paoCount;
	}

	public void setPaoCount(int paoCount) {
		this.paoCount = paoCount;
	}

	public OperationDingPaoRet(){
        
    }
    
    @Override
    public void decode(Input in)  throws IOException, ProtocolException {
    	locationIndex = in.readInt();
    	paoCount = in.readInt();
    }

    @Override
    public void encode(Output out)  throws IOException, ProtocolException {
    	out.writeInt(locationIndex);
    	out.writeInt(paoCount);
    }
    
    @Override
    public String toString() {
        return "OperationDingPaoRet [ locationIndex = "+locationIndex +" [ paoCount= " + paoCount + "]";
    }
    
    public final int getMessageType() {
        return TYPE;
    }

    @Override
    public final int getMessageId() {
        return ID;
    }
}
