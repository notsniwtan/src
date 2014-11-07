package edu.ntu.androidsecure.soot;

import java.util.ArrayList;
import java.util.List;

public class SootArguments {

	/**
	 * Return the argument for running Soot
	 * @return
	 */
	public static String[] makeAndroidTestArguments(){
		List<String> args = new ArrayList<String>();
		args.add("-soot-class-path");
		args.add("bin/:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-7-oracle/jre/lib/jce.jar:libs/android-support-v4.jar:libs/android-platforms/android-19/android.jar");
		args.add("-android-jars");
		args.add("libs/android-platforms");
		args.add("-x");
		args.add("android.support.");
		//args.add("edu.ntu.security.sendinfoout.aidl.IStealInfoService");
		args.add("-w");
		//args.add("–allow-phantom-refs");
		//args.add("-main-class");
		//args.add("edu.ntu.security.collusiveattack.MainActivity");
		//args.add("edu.ntu.security.collusiveattack.MainActivity");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$1");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$2");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$3");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$4");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$4$1");
		//args.add("-v");
		args.add("-process-dir");
		args.add("samples/CollusiveAttack.apk");
		//args.add("samples/AndroidSpecific_DirectLeak1.apk");
		//args.add("samples/wechat-5-3-0-es-en-br-fr-de-it-cn-jp-ar-ru-pl-cz-kr-tr-android.apk");
		//args.add("samples/Facebook_3.9.apk");
		//args.add("samples/2cloud.apk");
		return args.toArray(new String[args.size()]);
	}
	public static String[] makeAndroidTestArguments(String apkFile){
		List<String> args = new ArrayList<String>();
		args.add("-soot-class-path");
		//args.add("bin/:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-7-oracle/jre/lib/jce.jar:libs/android-support-v4.jar:libs/android-platforms/android-19/android.jar");
		args.add("C:/Program Files/Java/jre7/lib/rt.jar;C:/Program Files/Java/jre7/lib/jce.jar;libs/android-support-v4.jar;C:/Program Files (x86)/adt-bundle-windows-x86_64-20140321/sdk/platforms/android-19/android.jar");
		args.add("-android-jars");
		args.add("libs/android-platforms");
		args.add("-x");
		args.add("android.support.");
		//args.add("edu.ntu.security.sendinfoout.aidl.IStealInfoService");
		args.add("-w");
		//args.add("–allow-phantom-refs");
		//args.add("-main-class");
		//args.add("edu.ntu.security.collusiveattack.MainActivity");
		//args.add("edu.ntu.security.collusiveattack.MainActivity");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$1");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$2");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$3");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$4");
		//args.add("edu.ntu.security.collusiveattack.MainActivity$4$1");
		args.add("-v");
		args.add("-process-dir");
		args.add(apkFile);
		//args.add("samples/AndroidSpecific_DirectLeak1.apk");
		//args.add("samples/wechat-5-3-0-es-en-br-fr-de-it-cn-jp-ar-ru-pl-cz-kr-tr-android.apk");
		//args.add("samples/Facebook_3.9.apk");
		//args.add("samples/2cloud.apk");
		return args.toArray(new String[args.size()]);
	}
	
	/**
	 * Return the argument for running Soot
	 * @return
	 */
	public static String[] makeAndroidCloneTestArguments(String apkFile){
		List<String> args = new ArrayList<String>();
		args.add("-soot-class-path");
		args.add("C:/Program Files/Java/jre7/lib/rt.jar;C:/Program Files/Java/jre7/lib/jce.jar;libs/android-support-v4.jar;C:/Program Files (x86)/adt-bundle-windows-x86_64-20140321/sdk/platforms/android-19/android.jar");
		args.add("-android-jars");
		args.add("libs/android-platforms");
		args.add("-x"); //exclude android.support
		args.add("android.support.");
		args.add("-w");
		//args.add("-main-class");
		//args.add("edu.ntu.security.collusiveattack.MainActivity");
		args.add("-allow-phantom-refs");
		args.add("-v");
		args.add("-process-dir");
		args.add(apkFile);
		return args.toArray(new String[args.size()]);
	}
	
	/**
	 * Return the argument for running Soot
	 * @return
	 */
	public static String[] makeAndroidPointerAnalysisArguments(String apkFile){
		List<String> args = new ArrayList<String>();
		args.add("-soot-class-path");
		args.add("bin/:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-7-oracle/jre/lib/jce.jar:libs/android-support-v4.jar:libs/android-platforms/android-19/android.jar");
		args.add("-android-jars");
		args.add("libs/android-platforms");
		args.add("-x");
		args.add("android.support.");
		args.add("-w");
		args.add("–allow-phantom-refs");
		args.add("-v");
		args.add("-process-dir");
		args.add(apkFile);
		return args.toArray(new String[args.size()]);
	}
	/**
	 * Return the argument for generating call graph
	 * @return
	 */
	public static String[] makeCallGraphTestArguments(){
		List<String> args = new ArrayList<String>();
		args.add("-soot-class-path");
		args.add("bin/:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-7-oracle/jre/lib/jce.jar");
		args.add("-x");
		args.add("android.support.");
		args.add("-w");
		args.add("-v");
		args.add("-main-class");
		args.add("testers.CallGraphs");
		args.add("testers.CallGraphs");
		args.add("testers.A");
		args.add("testers.A$1");
		args.add("testers.CallGraphs$1");
		args.add("testers.CallGraphs$2");
		return args.toArray(new String[args.size()]);
	}
	/**
	 * Return the argument for generating call graph
	 * @return
	 */
	public static String[] makePointerAnalysisArguments(){
		List<String> args = new ArrayList<String>();
		args.add("-soot-class-path");
		args.add("bin/:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-7-oracle/jre/lib/jce.jar");
		args.add("-x");
		args.add("android.support.");
		args.add("-w");
		args.add("-main-class");
		args.add("testers.Tester");
		args.add("testers.Tester");
		args.add("testers.Container");
		args.add("testers.Item");
		return args.toArray(new String[args.size()]);
	}
	
	/**
	 * Check if the exclusions 
	 */
	public static String[] exclusions = {
		"java.",
		"javax.",
		"sun.",
		"android."
	};
	
	/**
	 * 
	 * @param method
	 * @return
	 */
	public static boolean exclusive(String sootClass){
		for (int i = 0 ; i < exclusions.length; i++){
			if (sootClass.startsWith(exclusions[i]))
				return true;
		}
		return false;
	}
}