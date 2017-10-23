package game.scene.room.majiang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

import mj.data.Pai;

public class FaPaiUtil {
	private static ArrayList<Control> controls;
	static int anGangNums[] = new int[100];
	static int shunziNums[] = new int[100];
	static int duiziNums[] = new int[100];
	static int keziNums[] = new int[100];
	static int huierNums[] = new int[100];
	
	static{
		double[] anGangGl = new double[]{0.9,0.07,0.02,0.01};
		double[] duiziGl = new double[]{0.05,0.8,0.05,0.02,0.02,0.02,0.02,0.02};
		double[] shuziGl = new double[]{0.25,0.4,0.2,0.1,0.00};
		double[] keziGl = new double[]{0.8,0.1,0.05,0.05,0.00};
		double[] huierGl = new double[]{0.6,0.28,0.12,0.00,0.00};
		int end = 0;
		for (int i = 0; i < anGangGl.length; i++) {
			int num = (int) (anGangGl[i]*100);
			for (int j = end; j < end+num; j++) {
				anGangNums[j] = i;
			}
			end = end+num;
		}
		end = 0;
		for (int i = 0; i < duiziGl.length; i++) {
			int num = (int) (duiziGl[i]*100);
			for (int j = end; j < end+num; j++) {
				duiziNums[j] = i;
			}
			end = end+num;
		}
		end = 0;
		for (int i = 0; i < shuziGl.length; i++) {
			int num = (int) (shuziGl[i]*100);
			for (int j = end; j < end+num; j++) {
				shunziNums[j] = i;
			}
			end = end+num;
		}
		end = 0;
		for (int i = keziGl.length-1; i >=0; i--) {
			int num = (int) (keziGl[i]*100);
			for (int j = end; j < end+num; j++) {
				keziNums[j] = i;
			}
			end = end+num;
		}
		end = 0;
		for (int i = 0; i <huierGl.length; i++) {
			int num = (int) (huierGl[i]*100);
			for (int j = end; j < end+num; j++) {
				huierNums[j] = i;
			}
			end = end+num;
		}
		randomGl(anGangNums);
		randomGl(keziNums);
		randomGl(duiziNums);
		randomGl(shunziNums);
		randomGl(huierNums);
		
		
	}
	
	static{
		Random random = new Random();
		Set<Control> temp = new HashSet<Control>();
		int anGang = 4,duizi=2,kezi=3,shuzi=3,huier=1;
		int count=0;
		while(count<10000){
			int anGangNum = anGangNums[random.nextInt(100)];
			int duiziNum = duiziNums[random.nextInt(100)];
			int shunziNum = shunziNums[random.nextInt(100)];
			int keziNum = keziNums[random.nextInt(100)];
			int huierNum = huierNums[random.nextInt(100)];
			boolean res = (anGangNum*anGang+duiziNum*duizi+shunziNum*shuzi+keziNum*kezi+huierNum*huier)<=13;
			if(res){
				Control c = null;
				try{
					c=new Control(keziNum, huierNum, shunziNum, anGangNum, duiziNum);
				}catch (Exception e) {
					continue;
				}
				temp.add(c);
			}
			count++;
		}
		controls = new ArrayList<FaPaiUtil.Control>(temp);
	}
	
	public static ArrayList<Pai>[] faPaiByControl(int index[], int usernum, ArrayList<Pai> paiPool, Pai huier) {
		Random random = new Random();

		java.util.Map<Pai, Integer> paiMap = new HashMap<Pai, Integer>();
		initPaiMap(paiMap, paiPool);

		ArrayList<Pai>[] userPais = new ArrayList[usernum];
		for (int i = 0; i < userPais.length; i++) {
			ArrayList<Pai> pais = new ArrayList<Pai>();
			if(isContins(i, index)){
				int r = random.nextInt(controls.size());
				Control control = controls.get(r);
				for (int j = 0; j < control.getAngangNum(); j++) {
					addAnGang(pais, paiPool, paiMap, random);
				}
				for (int j = 0; j < control.getKeziNum(); j++) {
					addKezi(pais, paiPool, paiMap, random);
				}
				for (int j = 0; j < control.getShunziNum(); j++) {
					addShunzi(pais, paiPool, paiMap, random);
				}
				for (int j = 0; j < control.getDuiziNum(); j++) {
					addDuizi(pais, paiPool, paiMap, random);
				}
				if(huier!=null){
					for (int j = 0; j < control.getHuierNum(); j++) {
						addHuier(pais, paiPool, paiMap, huier);
					}
				}
				
				int len = pais.size();
				if(len<13){
					for (int j = len; j < 13; j++) {
						Pai remove = paiPool.remove(j);
						pais.add(remove);
						paiMap.put(remove, paiMap.get(remove)-1);
					}
				}
				
			}else{
				xiPai(paiPool);
				int len = pais.size();
				if(len<13){
					for (int j = len; j < 13; j++) {
						Pai remove = paiPool.remove(paiPool.size()-1);
						pais.add(remove);
						paiMap.put(remove, paiMap.get(remove)-1);
					}
				}
			}
			userPais[i] = pais;
		}
		return userPais;
		 
	}
	
	
	
	
	private static void initPaiMap(java.util.Map<Pai, Integer> paiMap, ArrayList<Pai> paiPool) {
		for (int i = 0; i < paiPool.size(); i++) {
			Integer count = paiMap.get(paiPool.get(i));
			if(count==null){
				count = new Integer(0);
			}
			paiMap.put(paiPool.get(i), ++count);
		}
	}
	
	public static void xiPai(ArrayList<Pai> paiPool){
		for (int i = 0; i < paiPool.size(); i++) {
            int randomIndex = RandomUtils.nextInt(paiPool.size());

            Pai temp = paiPool.get(i);
            paiPool.set(i, paiPool.get(randomIndex));
            paiPool.set(randomIndex, temp);
        }
	}

	public static boolean isContins(int num,int [] nums){
		if(nums!=null){
			for (int i = 0; i < nums.length; i++) {
				if(num==nums[i]){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static void randomGl(int [] nums){
		Random random = new Random();
		for (int i = 0; i < nums.length; i++) {
			int randInt = random.nextInt(nums.length);
			int num = nums[randInt];
			nums[randInt] = nums[i];
			nums[i] = num;
		}
	}
	
	public static void addHuier(ArrayList<Pai> pais,ArrayList<Pai> paiPool,java.util.Map<Pai,Integer> paiMap,Pai huier){
		
		int count=paiMap.get(huier);
		if(count>0){
			pais.add(huier);
			paiPool.remove(huier);
			paiMap.put(huier,paiMap.get(huier)-1);
		}
	}
	
	public static void addDuizi(ArrayList<Pai> pais,ArrayList<Pai> paiPool,java.util.Map<Pai,Integer> paiMap,Random random){
		int paiIndex = random.nextInt(paiMap.size());
		Pai pai = Pai.fromIndex(paiIndex);
		while(paiMap.get(pai)<2){
			 paiIndex = random.nextInt(paiMap.size());
			 pai = Pai.fromIndex(paiIndex);
		}
		int count=paiMap.get(pai);
		int duiziCount = 0;
		for (int j = 0; j < paiPool.size(); j++) {
			if(paiPool.get(j).getIndex()==paiIndex){
				pais.add(paiPool.remove(j));
				paiMap.put(pai, --count);
				duiziCount++;
				if(duiziCount==2){
					break;
				}
				
			}
		}
		assert(duiziCount==2);
		
	}
	
	public static void addShunzi(ArrayList<Pai> pais,ArrayList<Pai> paiPool,java.util.Map<Pai,Integer> paiMap,Random random){
		int paiIndex = random.nextInt(paiMap.size());
		boolean canShunzi = (paiIndex>= Pai.TONG_1.getIndex() && paiIndex<=Pai.TONG_7.getIndex())
								||(paiIndex>= Pai.TIAO_1.getIndex() && paiIndex<=Pai.TIAO_7.getIndex())
								||(paiIndex>= Pai.WAN_1.getIndex() && paiIndex<=Pai.WAN_7.getIndex());
		while(!canShunzi){
			paiIndex = random.nextInt(paiMap.size());
			canShunzi = (paiIndex>= Pai.TONG_1.getIndex() && paiIndex<=Pai.TONG_7.getIndex())
									||(paiIndex>= Pai.TIAO_1.getIndex() && paiIndex<=Pai.TIAO_7.getIndex())
									||(paiIndex>= Pai.WAN_1.getIndex() && paiIndex<=Pai.WAN_7.getIndex());
		}
		
		Pai s1 = Pai.fromIndex(paiIndex);
		Pai s2 = Pai.fromIndex(paiIndex+1);
		Pai s3 = Pai.fromIndex(paiIndex+2);
		boolean hasPai = paiMap.get(s1)>=1 && paiMap.get(s2)>=1 && paiMap.get(s3)>=1;
		while(!hasPai){
			paiIndex = random.nextInt(paiMap.size());
			canShunzi = (paiIndex>= Pai.TONG_1.getIndex() && paiIndex<=Pai.TONG_7.getIndex())
									||(paiIndex>= Pai.TIAO_1.getIndex() && paiIndex<=Pai.TIAO_7.getIndex())
									||(paiIndex>= Pai.WAN_1.getIndex() && paiIndex<=Pai.WAN_7.getIndex());
			while(!canShunzi){
				paiIndex = random.nextInt(paiMap.size());
				canShunzi = (paiIndex>= Pai.TONG_1.getIndex() && paiIndex<=Pai.TONG_7.getIndex())
										||(paiIndex>= Pai.TIAO_1.getIndex() && paiIndex<=Pai.TIAO_7.getIndex())
										||(paiIndex>= Pai.WAN_1.getIndex() && paiIndex<=Pai.WAN_7.getIndex());
			}
			
			s1 = Pai.fromIndex(paiIndex);
			s2 = Pai.fromIndex(paiIndex+1);
			s3 = Pai.fromIndex(paiIndex+2);
			hasPai = paiMap.get(s1)>=1 && paiMap.get(s2)>=1 && paiMap.get(s3)>=1;
		}
		pais.add(s1);
		pais.add(s2);
		pais.add(s3);
		paiMap.put(s1, paiMap.get(s1)-1);
		paiMap.put(s2, paiMap.get(s2)-1);
		paiMap.put(s3, paiMap.get(s3)-1);
		paiPool.remove(s1);
		paiPool.remove(s2);
		paiPool.remove(s3);
		
	}
	
	public static void addKezi(ArrayList<Pai> pais,ArrayList<Pai> paiPool,java.util.Map<Pai,Integer> paiMap,Random random){
		int paiIndex = random.nextInt(paiMap.size());
		Pai pai = Pai.fromIndex(paiIndex);
		while(paiMap.get(pai)<3){
			 paiIndex = random.nextInt(paiMap.size());
			 pai = Pai.fromIndex(paiIndex);
		}
		int count=paiMap.get(pai);
		int keziCount = 0;
		for (int j = 0; j < paiPool.size(); j++) {
			if(paiPool.get(j).getIndex()==paiIndex){
				pais.add(paiPool.remove(j));
				paiMap.put(pai, --count);
				keziCount++;
				if(keziCount==3){
					break;
				}
				
			}
		}
		assert(keziCount==3);
	}
	
	public static void addAnGang(ArrayList<Pai> pais,ArrayList<Pai> paiPool,java.util.Map<Pai,Integer> paiMap,Random random){
		int paiIndex = random.nextInt(paiMap.size());
		Pai pai = Pai.fromIndex(paiIndex);
		while(paiMap.get(pai)!=4){
			 paiIndex = random.nextInt(paiMap.size());
			 pai = Pai.fromIndex(paiIndex);
		}
		int count=4;
		for (int j = 0; j < paiPool.size(); j++) {
			if(paiPool.get(j).getIndex()==paiIndex){
				pais.add(paiPool.remove(j));
				paiMap.put(pai, --count);
			}
		}
		assert(count==0);
		
	}
	
	
	static class Control{
		private int keziNum;
		private int huierNum;
		private int shunziNum;
		private int angangNum;
		private int duiziNum;

		
		
		public Control(int keziNum, int huierNum, int shunziNum, int angangNum, int duiziNum)throws RuntimeException {
			super();
			if ((angangNum * 4 + duiziNum * 2 + shunziNum * 3 + keziNum * 3 + huierNum * 1) > 13) {
				throw new RuntimeException("超出数量限制");
			}
			if(keziNum+huierNum+shunziNum>4){
				throw new RuntimeException("有可能造成天胡");
			}
			if(shunziNum+keziNum>=4){
				throw new RuntimeException("有可能造成天胡");
			}
			
			this.keziNum = keziNum;
			this.huierNum = huierNum;
			this.shunziNum = shunziNum;
			this.angangNum = angangNum;
			this.duiziNum = duiziNum;
			
			
		}
		
		
		public Control() {
		}


		public int getKeziNum() {
			return keziNum;
		}
		public void setKeziNum(int keziNum) {
			this.keziNum = keziNum;
		}
		public int getHuierNum() {
			return huierNum;
		}
		public void setHuierNum(int huierNum) {
			this.huierNum = huierNum;
		}
		public int getShunziNum() {
			return shunziNum;
		}
		public void setShunziNum(int shunziNum) {
			this.shunziNum = shunziNum;
		}
		public int getAngangNum() {
			return angangNum;
		}
		public void setAngangNum(int angangNum) {
			this.angangNum = angangNum;
		}
		public int getDuiziNum() {
			return duiziNum;
		}
		public void setDuiziNum(int duiziNum) {
			this.duiziNum = duiziNum;
		}


		@Override
		public String toString() {
			return "Control [keziNum=" + keziNum + ", huierNum=" + huierNum + ", shunziNum=" + shunziNum
					+ ", angangNum=" + angangNum + ", duiziNum=" + duiziNum + "]";
		}


		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Control)){
				return false;
			}
			Control o = (Control) obj;
			return this.getAngangNum() == o.getAngangNum()
					&& this.getDuiziNum() == o.getDuiziNum()
					&& this.getHuierNum() == o.getHuierNum()
					&& this.getKeziNum() == o.getHuierNum()
					&& this.getShunziNum() == o.getShunziNum();
		}


		@Override
		public int hashCode() {
			return (""+this.angangNum+this.duiziNum+this.huierNum+this.keziNum+this.keziNum).hashCode();
		}
	
		
	}
}
