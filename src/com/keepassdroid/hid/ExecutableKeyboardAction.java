package com.keepassdroid.hid;

import java.util.ArrayList;
import java.util.List;

public class ExecutableKeyboardAction {
	
	private KeyboardAction mAction;
	
	public ExecutableKeyboardAction(KeyboardAction action) {
		this.mAction = action;
	}
	
	public void execute() {
		if (mAction.isPause()) {
			if (mAction.getDuration()>0) {
				try {
					Thread.sleep(mAction.getDuration());
				} catch (InterruptedException e) {
					return;
				}
			}
		} else {
			String sKey = mAction.getKey();
			if (sKey != null) {
				if (KeyboardAction.getMapping().containsKey(sKey)) {
					sKey = KeyboardAction.getMapping().get(sKey);
				}
				List<String> sModifiers = mAction.getModifiers();
				List<String> remappedModifiers = new ArrayList<String>();
				if (sModifiers != null && sModifiers.size()>0) {
					for (String s : sModifiers) {
						if (KeyboardAction.getMapping().containsKey(s)) {
							remappedModifiers.add(KeyboardAction.getMapping().get(s));
						}
					}
				}
				
				String command = sKey;
				if (remappedModifiers.size()>0) {
					for (String s : remappedModifiers) {
						command = command + " " + s;
					}
				}
				Hid.sendCommandToKeyboard(command);
			}
		}
	}
}
