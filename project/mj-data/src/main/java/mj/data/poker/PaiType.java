package mj.data.poker;

public enum PaiType {
	
	BaoZi("豹子",6),
	TongHuaShun("同花顺",5),
	TongHua("同花",4),
	ShunZi("顺子",3),
	DuiZi("对子",2),
	SanPai("散牌",1);
	
	
	private String name;
	private int weight;

	PaiType(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return name;
	}

	
	
	
	
	
	
}
