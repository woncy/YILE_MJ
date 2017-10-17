package mj.data.poker.douniu;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum DouniuPaiType {
	WuNiu("无牛",1, 0 ),
	NiuDing("牛丁",1, 1),
	NiuTwo("牛二",1,2),
	NiuThree("牛三",1,3),
	NiuFour("牛四",1,4),
	NiuFive("牛五",1,5),
	NiuSix("牛六",1,6),
	NiuSeven("牛七",2,7),
	NiuEight("牛八",2,8),
	NiuNine("牛九",2,9),
	NiuNiu("牛牛",3,10),
	ZhaDanNiu("炸牛",4,11),
	WuHuaNiu("五花牛",4,12),
	NiuFiveSmallNiu("五小牛",4,13);
	
	private static final Map<Integer, DouniuPaiType> indexMap = Arrays.stream(DouniuPaiType.values()).collect(
            Collectors.toMap(DouniuPaiType::getIndex, v -> v)
    );
	
	private String name;
	//倍数
	private int doubleCount;
	//大小序号
	private   int  index;
	
	DouniuPaiType() {
		
	}

	DouniuPaiType(String name, int doubleCount,int index) {
		this.name = name;
		this.doubleCount = doubleCount;
		this.index = index;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public int getDoubleCount() {
		return doubleCount;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	 public static DouniuPaiType fromIndex(int index) {
	        return indexMap.get(index);
	 }
	
	
}
