package mj.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import mj.net.message.login.OptionEntry;

public class PdkConfig extends Config{
	
	private ArrayList<OptionEntry> listOpteions;
	
	public PdkConfig(ArrayList<OptionEntry> ops) {
		super(ops);
		this.listOpteions = ops;
	}
	public PdkConfig(Map<String,String> ops) {
		super(ops);
		toList(ops);
	}
	
	private void toList(Map<String,String> ops){
		this.listOpteions = new ArrayList<OptionEntry>();
		Iterator<Map.Entry<String, String>> it = ops.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = it.next();
			OptionEntry opt = new OptionEntry(entry.getKey(),entry.getValue());
			listOpteions.add(opt);
		}
	}
	public ArrayList<OptionEntry> getListOpteions() {
		return listOpteions;
	}
	public void setListOpteions(ArrayList<OptionEntry> listOpteions) {
		this.listOpteions = listOpteions;
	}
	
	
	

}
