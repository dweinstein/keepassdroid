package com.keepassdroid.hid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgrassi, dweinstein
 */

public class KeyboardAction {
	
	private long duration; //in millis
	private boolean ispause;
	private String key;
	private List<String> modifiers;
	
	private static Map<String,String> mKeyMap = new HashMap<String, String>();
	
	static {
		mKeyMap.put("return", "--return");
		mKeyMap.put("enter", "--return");
		mKeyMap.put("\n", "--return");
		
		mKeyMap.put("space","--spacebar");
		mKeyMap.put(" ", "--spacebar");
		mKeyMap.put("backspace", "--bckspc");
		
		mKeyMap.put("hyphen", "--minus");
		mKeyMap.put("minus", "--minus");
		mKeyMap.put("-", "--minus");
		
		mKeyMap.put("plus", "--plus");
		mKeyMap.put("+", "--plus");
		
		mKeyMap.put("comma", "--comma");
		mKeyMap.put(",", "--comma");
		
		mKeyMap.put("semicolon", "--semicolon");
		mKeyMap.put(";", "--semicolon");
		
		mKeyMap.put("period", "--period");
		mKeyMap.put(".", "--period");
		
		mKeyMap.put("slash", "--slash");
		mKeyMap.put("/", "--slash");
		
		mKeyMap.put("backslash", "--backslash");
		mKeyMap.put("\\", "--backslash");
		
		mKeyMap.put("tilde", "--tilde");
		mKeyMap.put("`", "--tilde");
		
		mKeyMap.put("esc", "--esc");
		
		mKeyMap.put("tab", "--tab");
		mKeyMap.put("\t", "--tab");
		
		mKeyMap.put("equal", "--equal");
		mKeyMap.put("=", "--equal");
		
		mKeyMap.put("asterisk", "--asterisk");
		mKeyMap.put("*", "--asterisk");
		
		mKeyMap.put("tick", "--tick");
		mKeyMap.put("'", "--tick");
		
		mKeyMap.put("left-brace", "--left-brace");
		mKeyMap.put("right-brace", "--right-brace");
		mKeyMap.put("[", "--left-brace");
		mKeyMap.put("]", "--right-brace");
		
		mKeyMap.put("up","--up");
		mKeyMap.put("down","--down");
		mKeyMap.put("right","--right");
		mKeyMap.put("left","--left");
		
		mKeyMap.put("f1", "--f1");
		mKeyMap.put("f2", "--f2");
		mKeyMap.put("f3", "--f3");
		mKeyMap.put("f4", "--f4");
		mKeyMap.put("f5", "--f5");
		mKeyMap.put("f6", "--f6");
		mKeyMap.put("f7", "--f7");
		mKeyMap.put("f8", "--f8");
		mKeyMap.put("f9", "--f9");
		mKeyMap.put("f10", "--f10");
		mKeyMap.put("f11", "--f11");
		mKeyMap.put("f12", "--f12");
		
		
		mKeyMap.put("ctrl", "--left-ctrl");
		mKeyMap.put("alt", "--left-alt");
		mKeyMap.put("maiusc", "--left-shift");
		mKeyMap.put("shift", "--left-shift");
		mKeyMap.put("meta", "--left-meta");
	}
	
	public static Map<String,String> getMapping() {
		return mKeyMap;
	}
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public boolean isPause() {
		return ispause;
	}
	public void setIsPause(boolean ispause) {
		this.ispause = ispause;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<String> getModifiers() {
		return modifiers;
	}
	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}
	
	@Override
	public String toString() {
		if (isPause()) {
			return "PAUSE duration: " + getDuration();
		} else {
			return "Key: " + getKey() +  " modifiers: " + getModifiers();
		}
	}

}
