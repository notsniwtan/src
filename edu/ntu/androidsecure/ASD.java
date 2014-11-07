package edu.ntu.androidsecure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ASD {
	public static String apk1;
	public static String apk2;
	//threshold for method similarity
	public static double threshold = 0.01;
	//ASD measures the extent to which first method set contributes its methods to the second method set
	public static ArrayList<Method> firstMethodSet = new ArrayList();
	public static ArrayList<Method> secondMethodSet = new ArrayList();
	public static ArrayList<Method> methodSet;
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]){		
		apk1 = "results/2ac72e9b0c4cbf4e3aaaf0f3614e53dbce703bfa.json";
		apk2 = "results/2ac72e9b0c4cbf4e3aaaf0f3614e53dbce703bfa.json";
		JSONParser parser = new JSONParser();
		
		JSONLoader(parser, apk1, firstMethodSet);
		JSONLoader(parser, apk2, secondMethodSet);
		
		methodSet = ProduceASDMethodSet(firstMethodSet, secondMethodSet, threshold);
		System.out.println("Application Similarity Degree: " + methodSet.size()/(firstMethodSet.size()));
		//produce ASD(a1,a2) and ASD(a2,a1), get the max and check against threshold for ASD
		//check if their public key is the same
		//check if the size of the smaller app is at least two-thirds of the bigger app
	}
	
	public static void JSONLoader(JSONParser parser, String filename, ArrayList<Method> methodList){

		try{
			Object obj = parser.parse(new FileReader(filename));
			JSONObject jsonObject = (JSONObject) obj;
			
			String appName = (String) jsonObject.get("app");
			System.out.println("<"+appName+">" + "being loaded into Array of Methods");
			
			JSONArray listOfMethods = (JSONArray) jsonObject.get("methods");  
			Iterator iterator = listOfMethods.iterator();  
			while (iterator.hasNext()) {  
				JSONObject jsonObj = (JSONObject)iterator.next();
				String methodName = (String)jsonObj.get("method");
				
				//centroid one
				String centroid = (String)jsonObj.get("centroid");
				String[] centroidList = centroid.replaceAll("<|>", "").split(",");
				double cX = Double.parseDouble(centroidList[0]);
				double cY = Double.parseDouble(centroidList[1]);
				double cZ = Double.parseDouble(centroidList[2]);
				double weight = (double)jsonObj.get("weight");  
				
				//centroid two
				centroid = (String)jsonObj.get("centroid'");
				centroidList = centroid.replaceAll("<|>", "").split(",");
				double cX2 = Double.parseDouble(centroidList[0]);
				double cY2 = Double.parseDouble(centroidList[1]);
				double cZ2 = Double.parseDouble(centroidList[2]);
				double weight2 = (double)jsonObj.get("weight'"); 
				
				Method m = new Method(methodName, cX, cY, cZ, weight, cX2, cY2, cZ2, weight2);
				methodList.add(m);
			}   
			
			
		}catch(ParseException  e) {
			e.printStackTrace();} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static double calculateCCD(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		double num1 = Math.abs(x1-x2)/(x1+x2);
		double num2 = Math.abs(y1-y2)/(y1+y2);
		double num3 = Math.abs(z1-z2)/(z1+z2);
		double num4 = Math.abs(w1-w2)/(w1+w2);
		if(Double.isNaN(num1))
			num1 = 0;
		if(Double.isNaN(num2))
			num2 = 0;
		if(Double.isNaN(num3))
			num3 = 0;
		if(Double.isNaN(num4))
			num4 = 0;
		return Math.max(Math.max(num3, num4), Math.max(num1, num2));
	}
	
	public static double calculateMDD(double num1, double num2){
		return Math.max(num1, num2);
	}
	
	public static ArrayList<Method> ProduceASDMethodSet(ArrayList<Method> firstMethodSet, ArrayList<Method> secondMethodSet, double threshold){
		double x1, y1, z1, w1, x2, y2, z2, w2;
		double ccd1, ccd2, mdd;
		ArrayList<Method> methodSet = new ArrayList();
		//loop from first method set because for each method m in the method set, m has to be from the first method set 
		for(int i = 0; i < firstMethodSet.size(); i++){
			x1 = firstMethodSet.get(i).getCentroid_X();
			y1 = firstMethodSet.get(i).getCentroid_Y();
			z1 = firstMethodSet.get(i).getCentroid_Z();
			w1 = firstMethodSet.get(i).getCentroid_weight();
			x2 = firstMethodSet.get(i).getExt_centroid_X();
			y2 = firstMethodSet.get(i).getExt_centroid_Y();
			z2 = firstMethodSet.get(i).getExt_centroid_Z();
			w2 = firstMethodSet.get(i).getExt_centroid_weight();
					
			for(int j = 0; j < secondMethodSet.size(); j++){
				ccd1 = calculateCCD(x1,y1,z1,w1,secondMethodSet.get(j).getCentroid_X(),secondMethodSet.get(j).getCentroid_Y(),secondMethodSet.get(j).getCentroid_Z(),secondMethodSet.get(j).getCentroid_weight());
				ccd2 = calculateCCD(x2,y2,z2,w2,secondMethodSet.get(j).getExt_centroid_X(),secondMethodSet.get(j).getExt_centroid_Y(),secondMethodSet.get(j).getExt_centroid_Z(),secondMethodSet.get(j).getExt_centroid_weight());
				mdd = calculateMDD(ccd1,ccd2);
				//if mdd is below or the same as the threshold, add into method set and go to the next method of the first method set
				if(mdd <= threshold){
					methodSet.add(firstMethodSet.get(i));
					break;
				}
			}
		}
		return methodSet;
	}
	
}
