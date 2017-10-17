package mj.data.poker.pdk;

public class PdkUserOutPai {
	private int index;
	private PdkPai outPai;
	
	public PdkUserOutPai() {
		super();
	}
	public PdkUserOutPai(int index, PdkPai outPai) {
		super();
		this.index = index;
		this.outPai = outPai;
	}
	@Override
	public String toString() {
		return "PdkUserOutPai [index=" + index + ", outPai=" + outPai + "]";
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public PdkPai getOutPai() {
		return outPai;
	}
	public void setOutPai(PdkPai outPai) {
		this.outPai = outPai;
	}
	
	
	
}
