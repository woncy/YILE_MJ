package mj.data;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import com.google.common.collect.ArrayListMultimap;

import mj.data.majiang.AgariUtils;
import mj.net.message.game.TingPai;
import mj.net.message.game.UserPlaceMsg;

/**
 * 用户的牌信息
 *
 * @author zuoge85@gmail.com on 16/10/17.
 */
public class UserPlace {
    /**
     * 显示暗杠的手数
     */
    public static final int SHOW_ANGANG_SHOUNUMS = 4;
    /**
     * 手牌
     */
    private final ArrayListMultimap<PaiType, Pai> shouPai = ArrayListMultimap.create();
    private final ArrayListMultimap<Pai, Pai> shouPaiMap = ArrayListMultimap.create();

    private final ArrayList<Pai> shouPaiList = new ArrayList<>();

    /**
     * 暗杠
     * 当前手数大于杠牌手数4，显示
     * 手数 -> 牌
     */
    private final ArrayList<Map.Entry<Integer, Pai>> anGang = new ArrayList<>();

    /**
     * 小明杠(加杠)
     * 自己以前碰了人家一张牌形成三张然后摸到相同的那一张牌后形成的杠称加杠。加杠也可以叫做小明杠。
     */
    private final ArrayList<Map.Entry<Integer, Pai>> xiaoMingGang = new ArrayList<>();

    /**
     * 别人打出一张牌时自己手中有三张相同的牌开的杠称大明杠
     */
    private final ArrayList<Map.Entry<Integer, Pai>> daMingGang = new ArrayList<>();


    /**
     * 碰
     */
    private final ArrayList<Map.Entry<Integer, Pai>> peng = new ArrayList<>();

    /**
     * 吃
     */
    private final ArrayList<Pai[]> chi = new ArrayList<>();
    
    
    private final ArrayList<Integer> daMingGangIndex = new ArrayList<Integer>();
    private final ArrayList<Integer> chiIndex = new ArrayList<Integer>();
    private final ArrayList<Integer> pengIndex = new ArrayList<Integer>();
    private final ArrayList<Integer> xiaoMingGangIndex = new ArrayList<Integer>();

    private ArrayList<Pai> out = new ArrayList<>();
    private int locationIndex;
    private int paoCount;
    
    private ArrayList<Integer> pao = new ArrayList<Integer>();
    
    private boolean isQiDuiHu;
    
    private int state = 0;//1,发牌中，2，出牌，3，吃，4，碰，5，杠，6，胡

    
    /**
     * 全部打出的牌，不管是不是被人 吃碰杠胡了
     */
    private ArrayList<Pai> allOut = new ArrayList<>();

    private int userId;
    private String userName;
    
    private boolean isTingPai = false;
    
    private boolean isOffLine;
    private boolean isWinner = false;
    private double winPro = 0D;
    
    private TingPai tingPais;
    
    
    
    public boolean isOffLine() {
		return isOffLine;
	}

	public void setOffLine(boolean isOffLine) {
		this.isOffLine = isOffLine;
	}


	public void addPao(int paoFen){
    	pao.add(paoFen);
    }
    
    public  ArrayList<Integer> getPao(){
    	return pao;
    }
    

    public boolean isQiDuiHu() {
		return isQiDuiHu;
	}


	public void setQiDuiHu(boolean isQiDuiHu) {
		this.isQiDuiHu = isQiDuiHu;
	}


	public boolean isTingPai() {
		return isTingPai;
	}


	public void setTingPai(boolean isTingPai) {
		this.isTingPai = isTingPai;
	}


	public UserPlace() {

    }


    public void clear() {
        shouPai.clear();
        shouPaiMap.clear();
        shouPaiList.clear();
        anGang.clear();
        xiaoMingGang.clear();
        daMingGang.clear();
        peng.clear();
        allOut.clear();
        chi.clear();
        out.clear();
    }

    public void addShouPai(Pai pai) {
        if (shouPaiList.size() >= 14) {
            throw new RuntimeException("严重错误");
        }
        shouPai.put(pai.getType(), pai);
        shouPaiMap.put(pai, pai);
        
        shouPaiList.add(pai);
    }
    
    public boolean  checkCPGISQUE(PaiType payType){
    	boolean flage = true;
    	if(flage && anGang.size() > 0 ){
    		for(int i = 0 ; i < anGang.size() ; i++){
    			Entry<Integer, Pai> anGangPai = anGang.get(i);
    			if(anGangPai.getValue().getType() == payType){
    				flage = false;
    				break;
    			}
    		}
    	}
    	if(flage && daMingGang.size() > 0 ){
    		for(int i = 0 ; i < daMingGang.size() ; i++){
    		    Entry<Integer, Pai> pai = daMingGang.get(i);
//    			Pai pai = daMingGang.get(i);
    			if(pai.getValue().getType() == payType){
    				flage = false;
    				break;
    			}
    		}
    	}
    	if(flage && xiaoMingGang.size() > 0 ){
    		for(int i = 0 ; i < xiaoMingGang.size() ; i++){
    		    Entry<Integer, Pai> pai = xiaoMingGang.get(i);
//    			Pai pai = xiaoMingGang.get(i);
    			if(pai.getValue().getType() == payType){
    				flage = false;
    				break;
    			}
    		}
    	}
    	if(flage && peng.size() > 0 ){
    		for(int i = 0 ; i < peng.size() ; i++){
//    			Pai pai = peng.get(i);
    		    Entry<Integer, Pai> pai = peng.get(i);
    			if(pai.getValue().getType() == payType){
    				flage = false;
    				break;
    			}
    		}
    	}
    	
    	if(flage && chi.size() > 0 ){
    		for(int i = 0 ; i < chi.size() ; i++){
    			Pai[] paiList = chi.get(i);
	    			if(paiList.length > 0 && paiList[0].getType() == payType){
	    				flage = false;
	    				break;
	    			}
    		}
    	}
    	return flage;
    }
    
    /*
     * 查询赢牌的时候,到底是缺少多少路.
     * 只能判断三种情况， 却一路，缺二路，清一色。
     */
    public int getWinType(){
	int i = 4 ;
	if(!shouPai.keys().contains(PaiType.TONG) && checkCPGISQUE(PaiType.TONG)){
	    i--;
	}
	if(!shouPai.keys().contains(PaiType.WAN) && checkCPGISQUE(PaiType.WAN)){
	    i--;
	}
	if(!shouPai.keys().contains(PaiType.TIAO) && checkCPGISQUE(PaiType.TIAO)){
	    i--;
	}
	/*
	 * 如果少 饼 ， 万 ，条中的两个
	 * 来判断是清一色， 还是缺三路。
	 */
	if(i <= 2){
		//清一色
		if(i == 1 &&
			(shouPai.keys().contains(PaiType.FENG)|| 
					shouPai.keys().contains(PaiType.SANYUAN) ||
					!checkCPGISQUE(PaiType.FENG)||
					!checkCPGISQUE(PaiType.SANYUAN))){
			i = 0;
		}else{
			//缺三路
			if(i == 2 &&
				!(shouPai.keys().contains(PaiType.FENG)|| 
						shouPai.keys().contains(PaiType.SANYUAN) ||
						!checkCPGISQUE(PaiType.FENG)||
						!checkCPGISQUE(PaiType.SANYUAN))){
				i= 1 ;
			}
		}
	}
	int winType  = 4 - i;
	return  winType ;
    }

    public void removeShouPai(Pai pai) {

        if (!shouPai.remove(pai.getType(), pai)) {
            throw new RuntimeException("删除牌失败！" + this + ",PAI:" + pai);
        }
        if (!shouPaiMap.remove(pai, pai)) {
            throw new RuntimeException("删除牌失败！" + this + ",PAI:" + pai);
        }
        if (!shouPaiList.remove(pai)) {
            throw new RuntimeException("删除牌失败！" + this + ",PAI:" + pai);
        }
    }

    public UserPlaceMsg toMessage(boolean isMy, int shouIndex,int roomState) {
        UserPlaceMsg m = new UserPlaceMsg();
        m.setAnGang(getAnGangIndex(isMy, shouIndex));
        m.setChi(MajiangUtils.toIndexByDyadicArray(chi));
        m.setDaMingGang(MajiangUtils.toIndex(daMingGang.stream().map(Map.Entry<Integer,Pai> :: getValue).toArray(Pai[] :: new)));
        m.setPeng(MajiangUtils.toIndex(peng.stream().map(Map.Entry<Integer,Pai> :: getValue).toArray(Pai[] :: new)));
        
        if (isMy) {
            m.setShouPai(MajiangUtils.toIndex(shouPai.values()));
            m.setXuanPaoCount(this.getPaoCount());
        } else {
            m.setShouPaiLen(shouPai.size());
            if(roomState==1){
            	m.setXuanPaoCount(-1);
            }else{
            	m.setXuanPaoCount(this.getPaoCount());
            }
        }
        m.setXiaoMingGang(MajiangUtils.toIndex(xiaoMingGang.stream()
                .map(Map.Entry<Integer,Pai> ::getValue).toArray(Pai[]::new)));

        m.setOutPai(MajiangUtils.toIndex(out));
        m.setChiIndex(getValueArrayFromList(chiIndex));
        m.setPengIndex(getValueArrayFromList(pengIndex));
        m.setDaMingGangIndex(getValueArrayFromList(daMingGangIndex));
        m.setXiaoMingGangIndex(getValueArrayFromList(xiaoMingGangIndex));
        return m;
    }
    private int[] getValueArrayFromList(ArrayList<Integer> list){
    	int[] res = null;
    	if(list==null){
    		res = new int[0];
    	}else{
    		int length = list.size();
    		res = new int[length];
    		for (int i = 0; i < length; i++) {
    			Integer val = list.get(i);
    			if(val!=null){
					res[i] = val;
    			}else{
    				res[i] = -1;
    			}
			}
    	}
    	return res;
    	
    }

    private int[] getAnGangIndex(boolean isMy, int shouIndex) {
        int[] arr = new int[anGang.size()];
        for (int i = 0; i < anGang.size(); i++) {
            Map.Entry<Integer, Pai> entry = anGang.get(i);
            if (isMy || shouIndex - entry.getKey() > SHOW_ANGANG_SHOUNUMS) {
                arr[i] = entry.getValue().getIndex();
            } else {
                arr[i] = Pai.HAS_PAI_INDEX;
            }
        }
        return arr;
    }

    public ArrayList<Pai> getShouPai() {
        return shouPaiList;
    }

    public ArrayList<Map.Entry<Integer, Pai>> getAnGang() {
        return anGang;
    }

    public ArrayList<Map.Entry<Integer, Pai>> getXiaoMingGang() {
        return xiaoMingGang;
    }

    public ArrayList<Map.Entry<Integer, Pai>> getDaMingGang() {
        return daMingGang;
    }

    public ArrayList<Map.Entry<Integer, Pai>> getPeng() {
        return peng;
    }

    public ArrayList<Pai[]> getChi() {
        return chi;
    }


    public ArrayList<Pai> getOut() {
        return out;
    }

    public void changeFa(Pai pai) {
    	
        addShouPai(pai);
        state = 1;
    }
    

	/**
     * 碰了的牌，自己在摸到一张，那么可以杠，叫小明杠
     */
    public int xiaoMingGang(Pai pai) {
        Integer waitCurrentIndex=-1;
        int pengI = -1;
        int otherIndex = -1;
        for(int i = 0 ; i < peng.size() ; i++){
            Entry<Integer, Pai> pengPai = peng.get(i);
            if(pengPai.getValue().getIndex() == pai.getIndex()){
            	pengI = i;
                if (!peng.remove(pengPai)) {
                    throw new RuntimeException("删除牌失败！" + this + ",PAI:" + pai);
                }
                waitCurrentIndex=pengPai.getKey();
            }
        }
        if(pengI!=-1){
        	otherIndex = pengIndex.remove(pengI);
        	xiaoMingGangIndex.add(otherIndex);
        }
//        if (!peng.remove(pai)) {
//            throw new RuntimeException("删除牌失败！" + this + ",PAI:" + pai);
//        }
        removeShouPai(pai);
        xiaoMingGang.add(new AbstractMap.SimpleEntry<>(waitCurrentIndex, pai));
        return otherIndex;
    }

    public void anGang(int shou, Pai pai) {
        anGang.add(new AbstractMap.SimpleEntry<Integer, Pai>(shou, pai));
        removeShouPai(pai);
        removeShouPai(pai);
        removeShouPai(pai);
        removeShouPai(pai);
    }

    public int[] checkAnGang(Pai pai) {
        Stream<Collection<Pai>> stream = shouPaiMap.asMap().values().stream().filter(pais -> pais.size() == 4);
        return stream.mapToInt(r -> r.iterator().next().getIndex()).toArray();
    }

    /**
     * 碰了的牌，自己在摸到一张，那么可以杠，叫小明杠
     */
    public int[] checkXiaoMingGang(Pai pai) {
//        return peng.stream().filter(shouPaiMap::containsKey).mapToInt(Pai::getIndex).toArray();
        return peng.stream().map(Map.Entry::getValue).filter(shouPaiMap::containsKey).mapToInt(r -> r.getIndex()).toArray();
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


    public void peng(Integer waitCurrentIndex,Pai pai,int otherIdnex) {
        peng.add(new AbstractMap.SimpleEntry<Integer,Pai>(waitCurrentIndex, pai));
        pengIndex.add(otherIdnex);
        removeShouPai(pai);
        removeShouPai(pai);
    }

    public boolean isPeng(Pai pai) {
        return getShouPaiCount(pai) > 1;
    }

    public void daMingGang(Integer waitCurrentIndex,Pai pai,int otherIndex) {
        daMingGang.add(new AbstractMap.SimpleEntry<Integer, Pai>(waitCurrentIndex, pai));
        daMingGangIndex.add(otherIndex);
        removeShouPai(pai);
        removeShouPai(pai);
        removeShouPai(pai);
    }

    /**
     * 手上的牌可以杠
     */
    public boolean isDaMingGang(Pai pai) {
        return getShouPaiCount(pai) > 2;
    }

    public void chi(Pai pai, int[] chi) {
        Pai[] chiPais = Arrays.stream(chi).mapToObj(Pai::fromIndex).toArray(Pai[]::new);
        this.chi.add(chiPais);
        for (Pai chiItem : chiPais) {
            if (!chiItem.equals(pai)) {
                removeShouPai(chiItem);
            }
        }
    }

    public List<Pai[]> isChi(Pai pai) {
//        List<Pai> pais = shouPai.get(pai.getType());
        Pai next0 = pai.nextPaiType();
        Pai next1 = next0 != null ? next0.nextPaiType() : null;
        Pai prev0 = pai.prevPaiType();
        Pai prev1 = prev0 != null ? prev0.prevPaiType() : null;

        next0 = shouPaiMap.containsKey(next0) ? next0 : null;
        next1 = shouPaiMap.containsKey(next1) ? next1 : null;
        prev0 = shouPaiMap.containsKey(prev0) ? prev0 : null;
        prev1 = shouPaiMap.containsKey(prev1) ? prev1 : null;

        List<Pai[]> result = new ArrayList<>();
        if (next0 != null && prev0 != null && 
        	next0.getType() == pai.getType() && prev0.getType() == pai.getType()) {
            result.add(new Pai[]{prev0, pai, next0});
        }
        if (next0 != null && next1 != null &&
        	next0.getType() == pai.getType() && next1.getType() == pai.getType()) {
            result.add(new Pai[]{pai, next0, next1});
        }
        if (prev0 != null && prev1 != null &&
        	prev0.getType() == pai.getType() && prev1.getType() == pai.getType()) {
            result.add(new Pai[]{prev1, prev0, pai});
        }
        return result;
    }

    public boolean isQiDui() {
        if (shouPaiMap.size() == 14) {
            return shouPaiMap.asMap().values().stream().filter(v -> v.size() == 2).count() == 7;
        }
        return false;
    }

    public boolean isQiDui(Pai pai) {
    	
    	ArrayListMultimap<Pai, Pai> res = ArrayListMultimap.create();
    	res.putAll(shouPaiMap);
    	res.put(pai, pai);
    	if (res.size() == 14) {
            return res.asMap().values().stream().filter(v -> v.size() == 2).count() == 7;
        }
    	
    	
//        if (shouPaiMap.size() == 14) {
//            Stream<Pai> concat = Stream.concat(shouPai.values().stream(), Stream.of(pai));
//            return concat.collect(Collectors.groupingBy(
//                    r -> r
//            )).values().stream().filter(v -> v.size() == 2).count() == 7;
//        }
        return false;
    }


    public boolean isHuPai(boolean isHuihuiGang, ArrayList<Pai> all, Pai huiEr[]) {
        return isHuiErGang(isHuihuiGang, huiEr) || isQiDui() || AgariUtils.isHuiErHuPai(all, shouPai.values(), huiEr);
    }

    public ArrayList<Pai> checkTingPai(boolean isHuihuiGang, ArrayList<Pai> all, Pai huiEr[]) {
        ArrayList<Pai> tingPais = new ArrayList<>();
        if (isHuiErGang(isHuihuiGang, huiEr)) {
            Collections.addAll(tingPais, huiEr);
        }

        ArrayList<Pai> testPais = new ArrayList<>();
        
        for (int i = 0; i < shouPaiList.size(); i++) {
			Pai pai = Pai.fromIndex(shouPaiList.get(i).getIndex());
			testPais.add(pai);
		}
        
        testPais.add(Pai.FENG_BEI);
        int shuPaiLen = shouPai.values().size();

        for (int i = 0; i < all.size(); i++) {
            Pai pai = all.get(i);
            testPais.set(shuPaiLen, pai);
            if (isQiDui(pai)) {
                tingPais.add(pai);
            } else if (AgariUtils.isHuiErHuPai(all, testPais, huiEr)) {
                tingPais.add(pai);
            }
        }
        return tingPais;
    }

    public boolean isHuiErGang(boolean isHuihuiGang, Pai huiEr[]) {
        if (!isHuihuiGang || huiEr == null || huiEr.length != 1) {
            return false;
        }
        return getShouPaiCount(huiEr[0]) == 4;
    }

    /**
     * 检查是否能胡别人打出的牌
     */
    public boolean isHuPaiBy(Pai pai) {
        return isQiDui(pai) || AgariUtils.isHuPai(shouPai.values(), pai);
    }

    private int getShouPaiCount(Pai pai) {
        return shouPaiMap.get(pai).size();
    }

    public boolean checkShouPai(Pai pai) {
        return shouPaiMap.containsKey(pai);
    }

    public void addOut(Pai pai) {
        out.add(pai);
    }

    public void addAllOut(Pai pai) {
        allOut.add(pai);
    }

    public boolean hasAllOut() {
        return !allOut.isEmpty();
    }

    public boolean existShouPai(Pai[] pai) {
        if (pai == null) {
            return false;
        }
        for (Pai p : pai) {
            if (shouPaiMap.containsKey(p)) {
                return true;
            }
        }
        return false;
    }

    public PaiType isYiTiaoLong() {
        PaiType type = isYiTiaoLong(Pai.TONG_1);
        if (type != null) {
            return type;
        }
        type = isYiTiaoLong(Pai.TIAO_1);
        if (type != null) {
            return type;
        }
        type = isYiTiaoLong(Pai.WAN_1);
        return type;
    }

    private PaiType isYiTiaoLong(Pai first) {
        if (shouPai.containsKey(first.getType())) {
            Pai next = first;
            while (shouPaiMap.containsKey(next)) {
                next = next.nextPaiType();
                if (next == null) {
                    return first.getType();
                }
            }
        }
        return null;
    }

    public boolean isQingYiSe() {
        if (shouPai.keySet().size() == 1) {
            PaiType type = shouPai.keySet().iterator().next();
            return !type.isZhi() && testAllPaiType(type);
        }
        return false;
    }

    public boolean isZhiYiSe() {
        if (shouPai.keySet().size() == 1) {
            PaiType type = shouPai.keySet().iterator().next();
            return type.isZhi() && testAllPaiType(type);
        }
        return false;
    }

    public boolean testAllPaiType(PaiType type) {
        return peng.stream().allMatch(pai -> type.equals(pai.getValue().getType())) &&
                anGang.stream().allMatch(e -> type.equals(e.getValue().getType())) &&
                daMingGang.stream().allMatch(pai -> type.equals(pai.getValue().getType())) &&
                xiaoMingGang.stream().allMatch(pai -> type.equals(pai.getValue().getType())) &&
                chi.stream().allMatch(pais -> type.equals(pais[0].getType()));
    }

    public boolean isHunYiSe() {
        if (shouPai.keySet().size() == 2) {
            Optional<PaiType> optional = shouPai.keySet().stream().filter(pai -> !pai.isZhi()).findAny();

            optional.map(type -> {
//                Stream<Pai> paiStream = Stream.concat(
//                        Stream.concat(shouPaiList.stream(), peng.stream()),
//                        Stream.concat(anGang.stream().map(Map.Entry::getValue),
//                                Stream.concat(
//                                        daMingGang.stream(),
//                                        Stream.concat(xiaoMingGang.stream(), chi.stream().map(pais -> pais[0]))
//                                )
//                        )
//                );
                Stream<Pai> paiStream = Stream.concat(
                        Stream.concat(shouPaiList.stream(), peng.stream().map(Map.Entry::getValue)),
                        Stream.concat(anGang.stream().map(Map.Entry::getValue),
                                Stream.concat(
                                        daMingGang.stream().map(Map.Entry::getValue),
                                        Stream.concat(xiaoMingGang.stream().map(Map.Entry::getValue), chi.stream().map(pais -> pais[0]))
                                )
                        )
                );
                return paiStream.allMatch(pai -> type.equals(pai.getType()) || pai.getType().isZhi());
            });
        }
        return false;
    }

    public int getGengCount() {
        return (int) shouPai.asMap().values().stream().filter(coll -> coll.size() == 4).count();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    @Override
    public String toString() {
        return "UserPlace{" +
                "shouPai=" + shouPai +
                ", shouPaiMap=" + shouPaiMap +
                ", shouPaiList=" + shouPaiList +
                ", anGang=" + anGang +
                ", xiaoMingGang=" + xiaoMingGang +
                ", daMingGang=" + daMingGang +
                ", peng=" + peng +
                ", chi=" + chi +
                ", out=" + out +
                ", locationIndex=" + locationIndex +
                ", paoCount=" + paoCount +
                ", allOut=" + allOut +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
    
    

	public int getXiaoMingGangIndex(Pai pai) {
		for (int i = 0; i < peng.size(); i++) {
			if(pai.getIndex() == peng.get(i).getValue().getIndex()){
				return pengIndex.get(i);
			}
			
		}
		return -1;
		
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public double getWinPro() {
		return winPro;
	}

	public void setWinPro(double winPro) {
		this.winPro = winPro;
	}
	


	public TingPai getTingPais() {
		return tingPais;
	}
	
	private boolean isIntArrEqauls(int[] arr1,int arr2[]){
		if(arr1==null || arr2 ==null){
			return false;
		}else{
			if(arr1.length!=arr2.length){
				return false;
			}else{
				for (int i = 0; i < arr2.length; i++) {
					int j = arr2[i];
					if(j!=arr1[i]){
						return false;
					}
					
				}
			}
			return true;
		}
	}
	private int tingPaiCircle = -1;
	private int randomHu = -1;
	private Random random = new Random();
	
	public void addTingPaiCircle(){
		tingPaiCircle++;
	}
	
	
	
	public int getRandomHu() {
		return randomHu;
	}

	public boolean canChangeHu(){
		if(isWinner()){
			if(randomHu!=-1 && tingPaiCircle!=-1){
				return tingPaiCircle == randomHu;
			}
		}
		return false;
	}
	
	
	
	public int getTingPaiCircle() {
		return tingPaiCircle;
	}

	public void  clearChangHu(){
		this.tingPais = null;
		tingPaiCircle = -1;
		randomHu = -1;
	}
	

	public void setTingPais(TingPai tingPais) {
		if(isWinner()){
			if(this.tingPais!=null){
				if(tingPais!=null){
					boolean isEquals = isIntArrEqauls(this.tingPais.getPais(), tingPais.getPais());
					if(!isEquals){
						tingPaiCircle = 0;
						randomHu = random.nextInt(5)+5;
					}
				}
			}else{
				tingPaiCircle = 0;
				randomHu = random.nextInt(5)+5;
			}
		}
		this.tingPais = tingPais;
		
		
	}

	public int[] checkKaZhang() {
		ArrayList<Integer> pais = new ArrayList<Integer>();
		int bing[] = new int[9];
		int tiao[] = new int[9];
		int wan[] = new int[9];
		
		for (int i = 0; i < shouPaiList.size(); i++) {
			Pai pai = shouPaiList.get(i);
			int index = pai.getIndex();
			if(index<=Pai.WAN_9.getIndex()){
				int type = index/9;
				if(type==0){
					bing[index%9]++;
				}else if(type==1){
					tiao[index%9]++;
				}else if(type==2){
					wan[index%9]++;
				}
			}
		}
		
		for (int i = 2; i < wan.length; i++) {
			if(wan[i]>0 && wan[i-2]>0 && wan[i-1]==0){
				pais.add((2*9+i-1));
			}
			if(tiao[i]>0 && tiao[i-2]>0 && tiao[i-1]==0){
				pais.add((1*9+i-1));
			}
			if(bing[i]>0 && bing[i-2]>0 && bing[i-1]==0){
				pais.add((0*9+i-1));
			}
		}
		for (int i = 1; i < wan.length-2; i++) {
			if(wan[i]>0 && wan[i+1]>0 && wan[i-1]==0 && wan[i+2]==0){
				pais.add((2*9+i-1));
				pais.add((2*9+i+2));
			}
			if(tiao[i]>0 && tiao[i+1]>0 && tiao[i-1]==0&&tiao[i+2]==0){
				pais.add((1*9+i-1));
				pais.add((1*9+i+2));
			}
			if(bing[i]>0 && bing[i+1]>0 && bing[i-1]==0&&bing[i+2]==0){
				pais.add((0*9+i-1));
				pais.add((0*9+i+2));
			}
		}
		
		
		
		int [] res = new int[pais.size()];
		for (int i = 0; i < res.length; i++) {
			if(pais.get(i)!=null)
				res[i] = pais.get(i);
			else{
				res[i] = 0;
			}
		}
		
		return res;
	}
	
	
    
    

}
