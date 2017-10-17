package mj.data.poker;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PokerColorType {
	HEI_TAO("黑桃",4),
	HONG_TAO("红桃",3),
	MEI_HUA("梅花",2),
	FANG_KUAI("方块",1),
	WNAG("王",0);
	
	private String type;
	private int size;

    PokerColorType(String type,int size) {
        this.type = type;
        this.size = size;
    }
	public String getType() {
		return type;
	}
	
	
	
	public String getName(){
		if(this.size==0)
			return "";
		return type;
	}
	public int size() {
		return size;
	}
	public static PokerColorType getColorTypeBySize(int size){
		switch (size) {
		case 1:
			return PokerColorType.FANG_KUAI;
		case 2:
			return PokerColorType.MEI_HUA;
		case 3:
			return PokerColorType.HONG_TAO;
		case 4:
			return PokerColorType.HEI_TAO;
		case 0:
			return PokerColorType.WNAG;
		default:
			return null;
		}
		
	}
	
	public int compare(PokerColorType pokerColorType){
		return Integer.compare(this.size, pokerColorType.size());
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 斗牛引用添加
	 */
	private static final Map<Integer, PokerColorType> indexMap = Arrays.stream(PokerColorType.values()).collect(
            Collectors.toMap(PokerColorType::size,  v -> v));
	
	public static PokerColorType fromIndex(int size) {
        return indexMap.get(size);
    }
}
