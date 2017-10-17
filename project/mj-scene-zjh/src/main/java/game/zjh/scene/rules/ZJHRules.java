package game.zjh.scene.rules;

import ZJH.data.ZJHConfig;

public class ZJHRules extends Rules{

	public ZJHRules(ZJHConfig config) {
		super(config);
	}

	@Override
	public boolean rest() {
		return false;
	}

	@Override
	public int getUserNum() {
		return config.getInt(ZJHConfig.USERNUM);
	}

	@Override
	public int danzhu() {
		
		return config.getInt(ZJHConfig.DANZHU);
	}

	@Override
	public String moShi() {
		return config.getString(ZJHConfig.MOSHI);
	}

	@Override
	public int chuShiFen() {
		return config.getInt(ZJHConfig.CHUSHIFEN);
	}
}
