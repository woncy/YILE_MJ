package mj.data.poker.douniu;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class DouniuPai implements Serializable,Comparable<DouniuPai>{
	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;
	private DouniuPoker[] pais;
	private DouniuPaiType type;
	
	public DouniuPai(DouniuPoker[] pais) throws Throwable {
		super();
		if(pais==null){
			throw new NullPointerException();
		}else{
			if(pais.length!=5){
				throw new Exception("斗牛牌的数量不正确");
			}else{
				this.pais = pais;
			}
		}
		
		type = DouniuPaiType.getDouniuPaiType(this.pais);
	}
	
	
	public DouniuPai(Collection<DouniuPoker> pais) throws Throwable{
		if(pais==null){
			throw new NullPointerException();
		}else{
			if(pais.size()!=5){
				throw new Exception("斗牛牌的数量不正确");
			}else{
				Iterator<DouniuPoker> iterator = pais.iterator();
				int i = 0;
				while(iterator.hasNext()){
					DouniuPoker next = iterator.next();
					this.pais[i++] = next;
				}
			}
		}
		type = DouniuPaiType.getDouniuPaiType(this.pais);
	}
	
	public DouniuPoker[] getPais() {
		return pais;
	}
	public void setPais(DouniuPoker[] pais) {
		this.pais = pais;
	}
	public DouniuPaiType getType() {
		return type;
	}
	public void setType(DouniuPaiType type) {
		this.type = type;
	}
	
	


	@Override
	public String toString() {
		return "DouniuPai [pais=" + Arrays.toString(pais) + ", type=" + type + "]";
	}


	@Override
	public int compareTo(DouniuPai pai) {
		int weight = Integer.compare(this.type.getIndex(), pai.getType().getIndex());
		if(weight!=0){
			return weight;
		}else {
			DouniuPoker [] p1 = this.pais.clone();
			DouniuPoker [] p2 = pai.pais.clone();
			Arrays.sort(p1);
			Arrays.sort(p2);
			return compareMaxPoer(p1,p2,p2.length-1);
		}

	}


	private int compareMaxPoer(DouniuPoker[] p1, DouniuPoker[] p2,int index) {
		if(index == -1){
			return 0;
		}
		int size =p1[index].compareTo(p2[index]);
		return size !=0 ?size:compareMaxPoer(p1, p2, --index);
	}
	
	
	
	
	
	
}
