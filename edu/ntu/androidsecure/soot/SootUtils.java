package edu.ntu.androidsecure.soot;

import java.util.ArrayList;
import java.util.List;

public class SootUtils {

	/**
	 * Return the argument for running Soot
	 * @return
	 */
	public static String[] makeAndroidTestArguments(){
		List<String> args = new ArrayList<String>();
		args.add("-android-jars");
		args.add("C:/Program Files/Java/jre7/lib/rt.jar;C:/Program Files/Java/jre7/lib/jce.jar;libs/android-support-v4.jar;C:/Program Files (x86)/adt-bundle-windows-x86_64-20140321/sdk/platforms/android-19/android.jar");
		args.add("-x");
		args.add("android.support.");
		args.add("-w");
		//args.add("-main-class");
		//args.add("edu.ntu.security.collusiveattack.MainActivity");
		args.add("-v");
		args.add("-process-dir");
		args.add("samples/AndroidSpecific_DirectLeak1.apk");
		return args.toArray(new String[args.size()]);
	}
	/**
	 * Return the argument for generating call graph
	 * @return
	 */
	public static String[] makeCallGraphTestArguments(){
		List<String> args = new ArrayList<String>();
	//	args.add("-cp");
	//	args.add("java -jar");
		args.add("-soot-class-path");
		args.add("C:/Users/Tan Family/workspace/FYP/bin;C:/Program Files/Java/jre7/lib/rt.jar;C:/Program Files/Java/jre7/lib/jce.jar");
//		args.add(".");
//		System.out.println(System.getenv("CLASSPATH"));
//		args.add("bin/:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-7-oracle/jre/lib/jce.jar");
//		args.add("-x");
//		args.add("C:/Users/Tan Family/workspace/FYP/src");
		//args.add("-w");
		args.add("-main-class");
		//args.add("-process-dir");
	//	args.add("testers/Anserverbot.apk");
		args.add("testers.CallGraphs");
		args.add("testers.CallGraphs");
		args.add("testers.A");
		args.add("testers.A$1");
		args.add("testers.CallGraphs$1");
		args.add("testers.CallGraphs$2");
		//args.add("testers.ModuleEntity");
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
