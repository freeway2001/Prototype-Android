package com.beyondsoft.util;

import android.util.Log;

public class Logs {
	public final static String LOGTAG = "debug";
	public static final boolean IS_DEBUG = true;
	private static final int DEBUG_LEVEL = Log.DEBUG;
	
	public static void v(String msg) {
		if (DEBUG_LEVEL <= Log.VERBOSE)
			Log.v(LOGTAG, msg);
	}
	
	public static void e(String msg) {
		if (DEBUG_LEVEL <= Log.ERROR)
			Log.e(LOGTAG, msg);
	}
	
	public static void i(String msg) {
		if (DEBUG_LEVEL <= Log.INFO)
			Log.i(LOGTAG, msg);
	}
	
	public static void d(String msg) {
		if (DEBUG_LEVEL <= Log.DEBUG)
			Log.d(LOGTAG, msg);
	}
	
	public static void v(String tag, String msg) {
		if (DEBUG_LEVEL <= Log.VERBOSE)
			Log.v(tag, msg);
	}
	
	public static void e(String tag, String msg) {
		if (DEBUG_LEVEL <= Log.ERROR)
			Log.e(tag, msg);
	}
	
	public static void i(String tag, String msg) {
		if (DEBUG_LEVEL <= Log.INFO)
			Log.i(tag, msg);
	}
	
	public static void d(String tag, String msg) {
		if (DEBUG_LEVEL <= Log.DEBUG)
			Log.d(tag, msg);
	}
}
