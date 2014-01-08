package com.keepassdroid.hid;

import android.util.Log;

public class Hid {

	static {
		try {
			System.loadLibrary("hid");
			Log.i("JNI", "Loaded libhid.so");
		} catch (UnsatisfiedLinkError ule) {
			Log.e("JNI", "WARNING: Could not load libhid.so");
		}
	}

	private static native void nativeSendCommandToKeyboard(String text);

	private static native void nativeSendCommandToMouse(String text);

	public static void sendCommandToKeyboard(String text) {
		nativeSendCommandToKeyboard(text);
	}

	public static void sendCommandToMouse(String text) {
		nativeSendCommandToMouse(text);
	}

}
