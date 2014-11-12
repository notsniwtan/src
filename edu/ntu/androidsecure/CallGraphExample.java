
package edu.ntu.androidsecure;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import soot.Body;
import soot.Hierarchy;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.BlockGraphConverter;
import soot.toolkits.graph.ExceptionalBlockGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.MHGPostDominatorsFinder;
import soot.toolkits.graph.SimpleDominatorsFinder;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;
import soot.util.cfgcmd.CFGToDotGraph;
import soot.util.dot.DotGraph;
import edu.ntu.androidsecure.soot.SootArguments;
import edu.ntu.cltk.file.FileUtil;

public class CallGraphExample {

	public static String apkFile;
	public static String resultFile;
	
	public static void main(String[] args) {
		List<String> argsList = new ArrayList<String>(Arrays.asList(args));
		argsList.addAll(Arrays.asList(new String[] { "-w", "-main-class",
				"testers.CallGraphs",// main-class
				"testers.CallGraphs",// argument classes
				"testers.A" //
		}));
		
		FileUtil.clearFile("results/3d.txt");

		
		//PackManager.v().getPack("jtp").add(new Transform("jtp.myTrans", new BodyTransformer() {
		PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

			@Override
			protected void internalTransform(String phaseName, Map options) {

				// Get all the application classes
				Chain<SootClass> css = Scene.v().getApplicationClasses();
				
				HashSet<SootClass> lookup = new HashSet<SootClass>();
				HashSet<SootMethod>lookupMethod = new HashSet<SootMethod>();
				
				for (Iterator<SootClass> iter = css.iterator(); iter.hasNext();) {
					lookup.add(iter.next());
				}

				css = Scene.v().getClasses();
				for (Iterator<SootClass> iter = css.iterator(); iter.hasNext();) {
					SootClass sc = iter.next();
					if (isInnerClass(lookup, sc.getName())) {
						lookup.add(sc);
					}
				}

				for (SootClass sc : lookup) {
					
					for (SootMethod sm : sc.getMethods()) {
						lookupMethod.add(sm);
					}
				}

				Hierarchy hie = Scene.v().getActiveHierarchy();

				CHATransformer.v().transform();
				CallGraph cg = Scene.v().getCallGraph();
				//DotGraph dg = new DotGraph("Call Graph");

				//DotGraph for the whole call graph
				for (SootClass sc : lookup) {
					// System.out.println("Css : " +
					// iter.next().toString());
					for (SootMethod sm : sc.getMethods()) {
						if (!sm.hasActiveBody())	continue;
						FileUtil.writeFile("results","/////////////////////////////////////////////////","a");
						System.out.println("This is the method name: " + sm.getName());
						FileUtil.writeFile("results", "The method name is: " + sm.getName(), "a");
						CFGCodeClone.myInternalTransform(sm.retrieveActiveBody(), null, null);
						FileUtil.writeFile("results","/////////////////////////////////////////////////","a");
					}

				}
				
				for (SootClass sc : lookup) {
					// System.out.println("Css : " +
					// iter.next().toString());
					//System.out.println("This is the method:" + sc.getme);
					for (SootMethod sm : sc.getMethods()) {
						if (!sm.hasActiveBody())	continue;
						DotGraph cfg = new DotGraph(sm.toString());
						UnitGraph ug = new ExceptionalUnitGraph(sm.retrieveActiveBody());
						
						BlockGraph bg = new ExceptionalBlockGraph(sm.retrieveActiveBody());
						
					//	System.out.println("This is the body: " + ug.getBody());
					//	System.out.println("This is the size: "s + ug.size());
						
						// final HashMutablePDG pdg = new
						// HashMutablePDG(ug);
						final CFGToDotGraph cfgToDot = new CFGToDotGraph();
						final DotGraph pdgGraph = cfgToDot.drawCFG(bg, bg.getBody());
						pdgGraph.plot("tmp/"+replace(sm.toString() + ".dot"));
					}
		
				}
				
				//dg.plot("callgraph.dot");

				// CHATransformer.v().transform();
				// CallGraph cg = Scene.v().getCallGraph();

				/*
				 * DotGraph dg = new DotGraph("Call Graph"); for
				 * (Iterator<MethodOrMethodContext> iter =
				 * cg.sourceMethods(); iter.hasNext();){ SootMethod m =
				 * iter.next().method(); if
				 * (SootUtils.exclusive(m.getDeclaringClass
				 * ().getName())) continue;
				 * 
				 * dg.drawNode(m.toString());
				 * Iterator<MethodOrMethodContext> targets = new
				 * Targets(cg.edgesOutOf(m)); while (targets.hasNext())
				 * { SootMethod tgt = (SootMethod) targets.next();
				 * dg.drawEdge(m.toString(), tgt.toString());
				 * 
				 * //System.out.println(m + " may call " + tgt); } }
				 * 
				 * dg.plot("callgraph.dot");
				 */
			}

		

			

		}));
				
		//Options.v().set_output_format(Options.v().output_format_dava);
	
		//Options.v().set_output_dir("sootOutput");
		// args = argsList.toArray(new String[0]);
		Options.v().set_src_prec(Options.src_prec_apk);
		//soot.Main.main(SootArguments.makeAndroidTestArguments());
		apkFile = "samples/8fc445ba6e8ef561607a41fc83008f92890a026f.apk";
		
		resultFile = "results/"+FileUtil.changeFileExtension(FileUtil.getFileName(apkFile), "json");
		FileUtil.clearFile(resultFile);
		FileUtil.writeFile(resultFile, "{","a");
		FileUtil.writeFile(resultFile, "\t\"app\": " + apkFile, "a");
		FileUtil.writeFile(resultFile, "\t\"methods\": [", "a");
		
		
		soot.Main.main(SootArguments.makeAndroidCloneTestArguments("samples/8fc445ba6e8ef561607a41fc83008f92890a026f.apk"));


		FileUtil.writeFile(resultFile, "\t]", "a");
		FileUtil.writeFile(resultFile, "}","a");
		//soot.Main.main(SootUtils.makeCallGraphTestArguments());
	}
	public static int count = 1;

	/**
	 * Check if the current class is an inner class The inner class is like A$1
	 * which cannot be categorized into application classes, <br/>
	 * Therefore, we need to check if A is an application class
	 * 
	 * @param sc
	 * @param className
	 * @return
	 */
	public static boolean isInnerClass(Set<SootClass> sc, String className) {
		if (className.indexOf('$') == -1) {
			return false;
		}
		for (SootClass clazz : sc){
			if (className.startsWith(clazz.getName())){
				return true;
			}
		}
		return false;
	}

	protected static void myInternalTransform(Body arg0, String arg1,
			Map<String, String> arg2) {		
		//Convert the method body into BlockGraph
				BlockGraph bg = new ExceptionalBlockGraph(arg0);
				
				//Add dummy start and stop node
				BlockGraphConverter.addStartStopNodesTo(bg);
		
		final CFGToDotGraph cfgToDot = new CFGToDotGraph();
		final DotGraph pdgGraph = cfgToDot.drawCFG(bg, bg.getBody());
		pdgGraph.plot("tmp/"+replace(arg0.getMethod().getName()+".dot"));
		count++;
		

		//Convert BlockGraph into Post Dominator Tree
		MHGPostDominatorsFinder cfgToDomtree = new MHGPostDominatorsFinder(bg);

		//List of visited nodes
		List<Block> visitedNodes = new ArrayList();
		
		//skip the starting dummy node
		Block b = bg.getBlocks().get(1);
		
		//List for 3D CFG
		List<CFGNode> cfgList = new ArrayList();
		while(cfgList.size()<bg.size()-1){
			cfgList.add(new CFGNode());
		}
		
		//stack for branches
		Stack<Block> branchStack = new Stack();
		
		//stack for IPD
		Stack<Block> ipdStack = new Stack();
		
		System.out.println("Size OF BG: " + bg.getBlocks().size());
		//FileUtil.writeFile("results/3d.txt","This is the size of the bg: "+bg.getBlocks().size(),"a");
		for(int i = 0; i< bg.size(); i++){
			//FileUtil.writeFile("results/3d.txt","-------------"+arg0.getMethod().toString()+"----------","a");
			//FileUtil.writeFile("results/3d.txt","This is the Block: "+bg.getBlocks().get(i),"a");
			//FileUtil.writeFile("results/3d.txt","This is the IPD of Block: "+cfgToDomtree.getImmediateDominator(bg.getBlocks().get(i)),"a");
			//FileUtil.writeFile("results/3d.txt","This is the Block's Chilren Size: "+bg.getBlocks().get(i).getSuccs().size(),"a");
			//FileUtil.writeFile("results/3d.txt","This is the Statement Count: "+StmtsCount(bg.getBlocks().get(i),(Block)cfgToDomtree.getImmediateDominator(bg.getBlocks().get(i))),"a");
			System.out.println("This is the Block: "+bg.getBlocks().get(i));
			System.out.println("This is the IPD of Block: "+cfgToDomtree.getImmediateDominator(bg.getBlocks().get(i)));
			System.out.println("This is the Block's Chilren Size: "+bg.getBlocks().get(i).getSuccs().size());
			System.out.println("This is the Statement Count: "+StmtsCount(bg.getBlocks().get(i),(Block)cfgToDomtree.getImmediateDominator(bg.getBlocks().get(i))));
			//FileUtil.writeFile("results/3d.txt","----------------------------------------------------","a");
		}
		
		System.out.println("==========================================");
		
		//////////////////////////////////////////////////////////////////
		//to locate loops and assign
		//form a dominator tree
		SimpleDominatorsFinder dff = new SimpleDominatorsFinder(bg);

			List<List> loopList = new ArrayList();
			
			for(int i=1; i<bg.getBlocks().size(); i++){
				List tempList = dff.getDominators(bg.getBlocks().get(i));
				for(int k = 0; k<tempList.size(); k++){
					if(bg.getBlocks().get(i).getSuccs().contains(tempList.get(k))){
						Block head = (Block) tempList.get(k);
						Block tail = bg.getBlocks().get(i);
						
						List loop = new ArrayList();
						Stack s = new Stack();
						loop.add(head);
						s.add(tail);
						
						while(!s.isEmpty()){
							Block tempB = (Block) s.pop();
							if(!loop.contains(tempB)){
								loop.add(tempB);
								s.addAll(tempB.getPreds());
							}
						}
						loopList.add(loop);
					}
				}
			}
			
			List clearHeadLoopsList = new ArrayList();
			
			//same heads
			while(loopList.size()>0){
				List compareList = (List) loopList.get(0);
				List tempList = new ArrayList();
				List deleteList = new ArrayList();
				deleteList.add(compareList);
				for(int k = 0; k < loopList.size(); k++){
					//if its a nested loop or contains a nested loop
					if(compareList.get(0).equals(loopList.get(k).get(0))){
						//merge the two loops
						LinkedHashSet h1 = new LinkedHashSet(compareList);
						LinkedHashSet h2 = new LinkedHashSet(loopList.get(k));
						h1.addAll(h2);
						
						deleteList.add(loopList.get(k));
						List convertList = new ArrayList(h1);
						compareList = convertList;
					}
				}
				loopList.removeAll(deleteList);
				clearHeadLoopsList.add(compareList);
			}
			
			
			List <List>finalLoopsList = new ArrayList();
			
			//adding nested loops into outer loops
			while(clearHeadLoopsList.size()>0){
				List compareList = (List) clearHeadLoopsList.get(0);
				List tempList = new ArrayList();
				for(int k = 0; k < clearHeadLoopsList.size(); k++){
					//if its a nested loop or contains a nested loop
					if(compareList.containsAll((Collection) clearHeadLoopsList.get(k))){
						tempList.add(clearHeadLoopsList.get(k));
					}
					else if(((AbstractCollection<Loop>) clearHeadLoopsList.get(k)).containsAll(compareList)){
						tempList.add(compareList);
						compareList = (List) clearHeadLoopsList.get(k);
					}
				}
				clearHeadLoopsList.removeAll(tempList);
				clearHeadLoopsList.remove(compareList);
				finalLoopsList.add(compareList);
			}

			//assigning sequence numbers to the loops
			Set<Block> blocksInLoops = new LinkedHashSet();
			
			while(finalLoopsList.size() > 0){
				List <Block>currentList = finalLoopsList.get(0);
				List <Block>visitedQBlocks = new ArrayList();
				int count = 0;
				
				List <Block>tempList = new ArrayList();
				tempList.add((Block) currentList.get(0));
				
				while(tempList.size()>0){
					List <Block> successList = new ArrayList();
					for(int i = 0; i<tempList.size(); i++){
						if(currentList.contains(tempList.get(i))){
							if(!visitedQBlocks.contains(tempList.get(i))){
								cfgList.get(tempList.get(i).getIndexInMethod()-1).setZ(count);
								visitedQBlocks.add(tempList.get(i));
								blocksInLoops.add(tempList.get(i));
								successList.addAll(tempList.get(i).getSuccs());
							}
						}
					}
					
					for(int k = 0; k<tempList.size(); k++){
						tempList.remove(tempList.get(k));
					}
					tempList.addAll(successList);
					count++;
				}
				finalLoopsList.remove(0);
			}
			//////////////////////////////////////////////////////////////////
			
		//System.out.println("This is the size: " + (bg.size()-1));
		
//////////////////////////Assigning seq no//////////////////////////////////
			/*if (arg0.getMethod().toString().equals("<net.youmi.android.aK: void run()>")){
				int a = 0;
				System.out.println(a+"");
			}*/
		assignSequence(b, bg, cfgList);
		/*	
		int seq = 0;
		//ignore dummy nodes
		loop:
		while(bg.size()-2 != visitedNodes.size()){
		System.out.println(b);
			if(!ipdStack.isEmpty() && b.equals(ipdStack.peek())){
				//check if node is dummy last node
				if(!b.equals(bg.getBlocks().get(bg.getBlocks().size()-1))){
					//has it been visited
					if(!visitedNodes.contains(b)){
						//assign seq no
						seq++;
						visitedNodes.add(b);
						cfgList.get(b.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(b));
						cfgList.get(b.getIndexInMethod()-1).setX(seq);
						cfgList.get(b.getIndexInMethod()-1).setY(b.getSuccs().size());
						cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size());
						cfgList.get(b.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(b));
						cfgList.get(b.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(b));
					}
				}
				//go to the other branch
				ipdStack.pop();
				b = branchStack.pop();
				continue loop;
			}
			
			//check if node has one successor
			if(b.getSuccs().size() == 1){
				//check if its been visited
				if(!visitedNodes.contains(b)){
					//assign seq no
					seq++;
					visitedNodes.add(b);
					cfgList.get(b.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(b));
					cfgList.get(b.getIndexInMethod()-1).setX(seq);
					cfgList.get(b.getIndexInMethod()-1).setY(b.getSuccs().size());
					cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size());
					cfgList.get(b.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(b));
					cfgList.get(b.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(b));
				}
				//assign successor as next block
				b = b.getSuccs().get(0);
				continue loop;
			}
			
			//check if node has more than one successor
			if(b.getSuccs().size()>1){
				List<Block> temp = new ArrayList();
				for (int i=0; i<b.getSuccs().size(); i++){
					if(!visitedNodes.contains(b.getSuccs().get(i))){
						temp.add(b.getSuccs().get(i));
					}
				}
				
				List<Block> orderedSuccessorList = orderedSuccessorList(temp, (Block)cfgToDomtree.getImmediateDominator(b), bg.getBlocks().get(bg.getBlocks().size()-1));
				//check if its been visited
				if(visitedNodes.contains(b)){
					//check if all the branches have been visited
					if(orderedSuccessorList.size()==0){
						ipdStack.pop();
						b = branchStack.pop();
						continue loop;
					}
					//check if branch nodes of branch have been visited
					if(orderedSuccessorList.size() != b.getSuccs().size()){
						//get the larger node
						b = orderedSuccessorList.get(orderedSuccessorList.size()-1);
						continue loop;
					}	
				}
				else{
					//assign seq no
					seq++;
					visitedNodes.add(b);
					cfgList.get(b.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(b));
					cfgList.get(b.getIndexInMethod()-1).setX(seq);
					cfgList.get(b.getIndexInMethod()-1).setY(b.getSuccs().size());
					cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size());
					cfgList.get(b.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(b));
					cfgList.get(b.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(b));
					
					//check if all successors have been visited
					if(temp.size() == 0){
						ipdStack.pop();
						b = branchStack.pop();
						continue loop;
					}
				}
				//add into both stacks
				int length = orderedSuccessorList.size();
				while(length != 0){
					branchStack.push(orderedSuccessorList.get(length-1));
					ipdStack.push((Block) cfgToDomtree.getImmediateDominator(b));
					length--;
				}
				
				ipdStack.pop();
				b = branchStack.pop();
			}
		}
		
//////////////////////////Assigning seq no//////////////////////////////////	
		
		
		//assign <x,y,z> to dummy last node
		cfgList.get(b.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(b));
		cfgList.get(b.getIndexInMethod()-1).setX(seq);
		cfgList.get(b.getIndexInMethod()-1).setY(b.getSuccs().size());
		cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size());
		cfgList.get(b.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(b));
		cfgList.get(b.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(b));
		*/
		//wInvoke is w'
				double x=0,y=0,z=0, w=0, xInvoke=0, yInvoke=0, zInvoke=0, wInvoke=0;
				
				//calculate w and w'
				for(int i = 0; i<cfgList.size(); i++){
					w = w + cfgList.get(i).getNumOfAppear() * cfgList.get(i).getNumOfStmts();
					wInvoke = wInvoke + cfgList.get(i).getNumOfAppear() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts());
				}
				FileUtil.writeFile("results/3d.txt", "--------------"+arg0.getMethod().toString()+"-------------", "a");
				
				for(int i = 0; i<cfgList.size(); i++){
					
					System.out.println("\n");
					System.out.println("---------"+arg0.getMethod().toString()+"------------");
					System.out.println("This is "+bg.getBlocks().get(cfgList.get(i).getIndex()));
					System.out.println("This is the sequence number: "+cfgList.get(i).getX());
					System.out.println("This is the number of outgoing edges: "+cfgList.get(i).getY());
					System.out.println("This is the depth of the block in the loop: "+cfgList.get(i).getZ());
					System.out.println("This is the number of statements in the block: "+cfgList.get(i).getNumOfStmts());
					System.out.println("This is the number of invoke statements in the block: "+cfgList.get(i).getNumOfInvokeStmts());
					System.out.println("This is the number of times it appears: "+cfgList.get(i).getNumOfAppear());
					System.out.println("-----------------------------------------");
					
					FileUtil.writeFile("results/3d.txt", "This is "+bg.getBlocks().get(cfgList.get(i).getIndex()), "a");
					FileUtil.writeFile("results/3d.txt", "This is the sequence number: "+cfgList.get(i).getX(), "a");
					FileUtil.writeFile("results/3d.txt", "This is the number of outgoing edges: "+cfgList.get(i).getY(), "a");
					FileUtil.writeFile("results/3d.txt", "This is the depth of the block in the loop: "+cfgList.get(i).getZ(), "a");
					FileUtil.writeFile("results/3d.txt", "This is the number of statements in the block: "+cfgList.get(i).getNumOfStmts(), "a");
					FileUtil.writeFile("results/3d.txt", "This is the number of invoke statements in the block: "+cfgList.get(i).getNumOfInvokeStmts(), "a");
					FileUtil.writeFile("results/3d.txt", "This is the number of times it appears: "+cfgList.get(i).getNumOfAppear(), "a");
					FileUtil.writeFile("results/3d.txt", "-----------------------------------------", "a");
					
					x = x + cfgList.get(i).getX() * cfgList.get(i).getNumOfStmts() * cfgList.get(i).getNumOfAppear();
					y = y + cfgList.get(i).getY() * cfgList.get(i).getNumOfStmts() * cfgList.get(i).getNumOfAppear();
					z = z + cfgList.get(i).getZ() * cfgList.get(i).getNumOfStmts() * cfgList.get(i).getNumOfAppear();
					
					xInvoke = xInvoke + cfgList.get(i).getX() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts()) * cfgList.get(i).getNumOfAppear();
					yInvoke = yInvoke + cfgList.get(i).getY() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts()) * cfgList.get(i).getNumOfAppear();
					zInvoke = zInvoke + cfgList.get(i).getZ() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts()) * cfgList.get(i).getNumOfAppear();
					
				}
				FileUtil.writeFile("results/3d.txt", "Results for " + arg0.getMethod().toString(), "a");
				FileUtil.writeFile("results/3d.txt", "The bg size is " + (bg.getBlocks().size()-1), "a");
				FileUtil.writeFile("results/3d.txt", "The visited size is " + visitedNodes.size(), "a");
				FileUtil.writeFile("results/3d.txt", "The w is " + w, "a");
				FileUtil.writeFile("results/3d.txt", "The c is <"+x/w+", "+y/w+", "+z/w+">", "a");
				FileUtil.writeFile("results/3d.txt", "The w' is " + wInvoke, "a");
				FileUtil.writeFile("results/3d.txt", "The c' is <"+xInvoke/wInvoke+", "+yInvoke/wInvoke+", "+zInvoke/wInvoke+">", "a");
				System.out.println("The w is " + w);
				System.out.println("The c is <"+x/w+", "+y/w+", "+z/w+">");
				System.out.println("The w' is " + wInvoke);
				System.out.println("The c' is <"+xInvoke/wInvoke+", "+yInvoke/wInvoke+", "+zInvoke/wInvoke+">");
				System.out.println("==========================================");
				
				FileUtil.writeFile(resultFile, "\t\t{\n\t\t\t\"method\": \""+arg0.getMethod().toString()+"\"","a");
				FileUtil.writeFile(resultFile, "\t\t\t\"centroid\": <"+x/w+", "+y/w+", "+z/w+">", "a");
				FileUtil.writeFile(resultFile, "\t\t\t\"weight\": "+w, "a");
				FileUtil.writeFile(resultFile, "\t\t\t\"centroid'\": <"+xInvoke/wInvoke+", "+yInvoke/wInvoke+", "+zInvoke/wInvoke+">", "a");
				FileUtil.writeFile(resultFile, "\t\t\t\"weight'\": "+wInvoke+"\n\t\t},", "a");
		
		/*
		int seq = 0;
		loop1:
		while(bg.size()-1 != visitedNodes.size()){
			System.out.println("This is the current block being processed: "+b);
			if(ipdStack.isEmpty() && branchStack.size()==1){
				b = branchStack.pop();
				continue loop1;
			}
			//if stack is not empty and block is immediate post dominator
			if(!ipdStack.isEmpty() && b.equals(ipdStack.peek())){
				System.out.println("The block has met its IPD");
					ipdStack.pop();
					b = branchStack.pop();
					continue loop1;
			}
			else			
				if(visitedNodes.contains(b)){
					ipdStack.pop();
					b = branchStack.pop();
					System.out.println("The block has been visited before");
					System.out.println("the branch is: "+branchStack.size());
					System.out.println("The stack is: "+ipdStack.size());
					System.out.println("Size of visited Nodes: "+visitedNodes.size());
					System.out.println("Size of BG: "+ bg.getBlocks().size());
					//if only 1 successor
					if(b.getSuccs().size() == 1){
						b = b.getSuccs().get(0);
					}
					//if more than one successor
					else{
						for (int i=0; i<b.getSuccs().size(); i++){
							System.out.println("This is the current node: "+b.getSuccs().get(i));
							if(!visitedNodes.contains(b.getSuccs().get(i))){
								b=b.getSuccs().get(i);
								break;
							}
							else{
								
								ipdStack.pop();
								b = branchStack.pop();
							}
						}
					}
				}
			
				else 
					if(b.getSuccs().size() == 0){
						if(!blocksInLoops.contains(b)){
							cfgList.get(b.getIndexInMethod()-1).setZ(0);
						}
					seq++;
					visitedNodes.add(b);
					System.out.println("ADD TO VISITEDNODES");
					cfgList.get(b.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(b));
					cfgList.get(b.getIndexInMethod()-1).setX(seq);
					cfgList.get(b.getIndexInMethod()-1).setY(b.getSuccs().size());
					cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size());
					cfgList.get(b.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(b));
					cfgList.get(b.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(b));
				}
			
				else
				if(b.getSuccs().size() == 1){
					System.out.println("This block has one child");
					//assign sequence number
					if(!blocksInLoops.contains(b)){
						cfgList.get(b.getIndexInMethod()-1).setZ(0);
					}
						seq++;
						visitedNodes.add(b);
						System.out.println("ADD TO VISITEDNODES");
						cfgList.get(b.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(b));
						cfgList.get(b.getIndexInMethod()-1).setX(seq);
						cfgList.get(b.getIndexInMethod()-1).setY(b.getSuccs().size());
						if(b.getIndexInMethod() == 1){
							cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size()-1);
						}
						else{
							cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size());
						}
						cfgList.get(b.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(b));
						cfgList.get(b.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(b));
						b=b.getSuccs().get(0);
				}
				else
				if(b.getSuccs().size() > 1){
					System.out.println("This block has more than one child");
					List<Block> temp = new ArrayList();
					for (int i=0; i<b.getSuccs().size(); i++){
						if(!visitedNodes.contains(b.getSuccs().get(i))){
							temp.add(b.getSuccs().get(i));
						}
					}
					
					//determine the order
					List<Block> orderedSuccessorList = orderedSuccessorList(temp, (Block)cfgToDomtree.getImmediateDominator(b));
					//add into both stacks
					int length = orderedSuccessorList.size();
					while(length != 0){
						branchStack.push(orderedSuccessorList.get(length-1));
						ipdStack.push((Block) cfgToDomtree.getImmediateDominator(b));
						length--;
					}
					System.out.println("This is the pushed branch stack: "+branchStack);
					System.out.println("This is the pushed ipd stack: "+ipdStack);
					/////////////////////add x,y,z/////////////////
					if(!blocksInLoops.contains(b)){
						cfgList.get(b.getIndexInMethod()-1).setZ(0);
					}
					visitedNodes.add(b);
					System.out.println("ADD TO VISITEDNODES");
					seq++;
					cfgList.get(b.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(b));
					cfgList.get(b.getIndexInMethod()-1).setX(seq);
					cfgList.get(b.getIndexInMethod()-1).setY(b.getSuccs().size());
					if(b.getIndexInMethod() == 1){
						cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size()-1);
					}
					else{
						cfgList.get(b.getIndexInMethod()-1).setNumOfAppear(b.getPreds().size()+b.getSuccs().size());
					}
					cfgList.get(b.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(b));
					cfgList.get(b.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(b));
					////////////////////////////////////////////////
					ipdStack.pop();
					b = branchStack.pop();
				}
			}
		
		//wInvoke is w'
		double x=0,y=0,z=0, w=0, xInvoke=0, yInvoke=0, zInvoke=0, wInvoke=0;
		
		//calculate w and w'
		for(int i = 0; i<cfgList.size(); i++){
			w = w + cfgList.get(i).getNumOfAppear() * cfgList.get(i).getNumOfStmts();
			wInvoke = wInvoke + cfgList.get(i).getNumOfAppear() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts());
		}
		for(int i = 0; i<cfgList.size(); i++){
			
			System.out.println("\n");
			System.out.println("-----------------------------------------");
			System.out.println("This is "+bg.getBlocks().get(cfgList.get(i).getIndex()));
			System.out.println("This is the sequence number: "+cfgList.get(i).getX());
			System.out.println("This is the number of outgoing edges: "+cfgList.get(i).getY());
			System.out.println("This is the depth of the block in the loop: "+cfgList.get(i).getZ());
			System.out.println("This is the number of statements in the block: "+cfgList.get(i).getNumOfStmts());
			System.out.println("This is the number of invoke statements in the block: "+cfgList.get(i).getNumOfInvokeStmts());
			System.out.println("This is the number of times it appears: "+cfgList.get(i).getNumOfAppear());
			System.out.println("-----------------------------------------");
			
			x = x + cfgList.get(i).getX() * cfgList.get(i).getNumOfStmts() * cfgList.get(i).getNumOfAppear();
			y = y + cfgList.get(i).getY() * cfgList.get(i).getNumOfStmts() * cfgList.get(i).getNumOfAppear();
			z = z + cfgList.get(i).getZ() * cfgList.get(i).getNumOfStmts() * cfgList.get(i).getNumOfAppear();
			
			xInvoke = xInvoke + cfgList.get(i).getX() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts()) * cfgList.get(i).getNumOfAppear();
			yInvoke = yInvoke + cfgList.get(i).getY() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts()) * cfgList.get(i).getNumOfAppear();
			zInvoke = zInvoke + cfgList.get(i).getZ() * (cfgList.get(i).getNumOfInvokeStmts() + cfgList.get(i).getNumOfStmts()) * cfgList.get(i).getNumOfAppear();
			
		}
		FileUtil.writeFile("results", "The bg size is " + (bg.getBlocks().size()-1), "a");
		FileUtil.writeFile("results", "The visited size is " + visitedNodes.size(), "a");
		FileUtil.writeFile("results", "The w is " + w, "a");
		FileUtil.writeFile("results", "The c is <"+x/w+", "+y/w+", "+z/w+">", "a");
		System.out.println("The w is " + w);
		System.out.println("The c is <"+x/w+", "+y/w+", "+z/w+">");
		System.out.println("The w' is " + wInvoke);
		System.out.println("The c' is <"+xInvoke/wInvoke+", "+yInvoke/wInvoke+", "+zInvoke/wInvoke+">");
		System.out.println("==========================================");
		
	*/	
		
	}
	public static SootClass getSootClass(List<SootClass> sc, String className) {
		for (int i = 0; i < sc.size(); i++) {
			SootClass s = sc.get(i);
			if (sc.get(i).getName().equals(className)) {
				return sc.get(i);
			}
		}
		return null;
	}
	
	public static int seqNo = 0;
	public static MHGPostDominatorsFinder<Block> cfgToDomtree;
	public static List<Block> visitedNodes;
	
	private static void assign(Block s, BlockGraph bg, List<CFGNode> cfgList){
		cfgList.get(s.getIndexInMethod()-1).setIndex(bg.getBlocks().indexOf(s));
		cfgList.get(s.getIndexInMethod()-1).setX(seqNo++);
		cfgList.get(s.getIndexInMethod()-1).setY(s.getSuccs().size());
		cfgList.get(s.getIndexInMethod()-1).setNumOfAppear(s.getPreds().size()+s.getSuccs().size());
		cfgList.get(s.getIndexInMethod()-1).setNumOfStmts(blockStmtsCount(s));
		cfgList.get(s.getIndexInMethod()-1).setNumOfInvokeStmts(blockInvokeStmtsCount(s));
	}
	public static void DFS(List<CFGNode> cfgList, Block e, Block now, BlockGraph bg){
				
		if (now.equals(e))	return;
		
		assign(now, bg, cfgList);
		visitedNodes.add(now);
		
		List<Block> temp = new ArrayList<Block>();
		for (int i=0; i<now.getSuccs().size(); i++){
			if(!visitedNodes.contains(now.getSuccs().get(i)) && !now.getSuccs().get(i).equals(e)){
				temp.add(now.getSuccs().get(i));
			}
		}
		if (temp.size() > 0){
			List<Block> orderedSuccessorList = orderedSuccessorList(temp, cfgToDomtree.getImmediateDominator(now), bg.getBlocks().get(bg.getBlocks().size()-1));
			
			for (Block succ : orderedSuccessorList){
				if (visitedNodes.contains(succ))	continue;
				Block ipd = cfgToDomtree.getImmediateDominator(succ);
				while (!ipd.equals(e)){
					DFS(cfgList, ipd, succ, bg);
					succ = ipd;
					ipd = cfgToDomtree.getImmediateDominator(succ);
				}
				DFS(cfgList, e, succ, bg);
			}
		}
	}
	
	public static void assignSequence(Block b, BlockGraph bg, List<CFGNode> cfgList){
		//List of visited nodes
		visitedNodes = new ArrayList<Block>();
		
		//Convert BlockGraph into Post Dominator Tree
		cfgToDomtree = new MHGPostDominatorsFinder<Block>(bg);
		
		Block ipd = cfgToDomtree.getImmediateDominator(b);
		
		seqNo = 0;
		
		DFS(cfgList, ipd, b, bg);
		
		assign(ipd, bg, cfgList);
	}
	
	public static String replace(final String name){
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < name.length(); i++){
			if (name.charAt(i) == '<' || name.charAt(i) == '>' || name.charAt(i) == ' ' || name.charAt(i) == '$'){
				if (i == 0) sb.append("s_");
				else	sb.append('_');
			}
			else	
				sb.append(name.charAt(i));
		}
		return sb.toString();
	}
	
	/*BlockGraph bg, Block startBlock, Block immPostDomBlock*/
	/*public static int NodesCount(Block startBlock, Block immPostDomBlock){
		Stack s = new Stack();
		HashSet nodesSet = new HashSet();
		
		s.addAll(startBlock.getSuccs());

		while (s.size() != 0){
			
			Block currentBlock = (Block) s.pop();
			if(!nodesSet.contains(currentBlock)){
				s.addAll(currentBlock.getSuccs());	
				nodesSet.add(currentBlock);
		//	if (currentBlock.equals(immPostDomBlock) || currentBlock.equals(startBlock)){ // || currentBlock.equals(bg.getTails())
		//		break loop;
			}
		
		}
		return nodesSet.size();
	}*/
	
	public static int blockStmtsCount(Block b){
		int total = 0;
		Unit u = b.getHead();
		
		if(u != null){
				
			if(u.equals(b.getTail())){
				total += 1;
			}
			else{
				while(!u.equals(b.getTail())){
					u = b.getSuccOf(u);
					total ++;
				}
				//add count for the last statement
				total += 1;
			}
		}
		return total;
	}
	
	public static int blockInvokeStmtsCount(Block b){
		int total = 0;
		Unit u = b.getHead();
		String s;
		
		if(u != null){
				
			if(u.equals(b.getTail())){
				s = u.toString();
				if(s.contains("invoke")){
					total += 1;
				}
			}
			else{
				while(!u.equals(b.getTail())){
					s = u.toString();
					if(s.contains("invoke")){
						total ++;
					}
					u = b.getSuccOf(u);
				}
				
				//add count for the last statements
				s = u.toString();
				if(s.contains("invoke")){
					total += 1;
				}
			}
		}
		return total;
	}
	
	public static int StmtsCount(Block startBlock, Block immPostDomBlock){
		Stack s = new Stack();
		Set<Block> nodesSet = new HashSet();
		int total = 0;
		
		s.addAll(startBlock.getSuccs());
	
		while (s.size() != 0){

			Block currentBlock = (Block) s.pop();
			if(!nodesSet.contains(currentBlock)){
				s.addAll(currentBlock.getSuccs());	
				nodesSet.add(currentBlock);
		}
		}
		for(Block b : nodesSet){
			Unit u = b.getHead();
			if(u == null){
			}
			else if(u.equals(b.getTail())){
				total += 1;
			}
			else{
				total += 1;
				while(!u.equals(b.getTail())){
					u = b.getSuccOf(u);
					total ++;
				}
			}
		}
		return total;
		
	}
	
	
	public static List<Block> orderedSuccessorList(List<Block> successorList, Block ipd, Block dummyLastBlk){
		List<Block> tempList = new ArrayList<Block>(successorList);
		int maxNodes = 0;
		Block maxBlock;
		List<Block> newSuccessorList = new ArrayList();

		while(tempList.size()!= 0){
			int size = 0;
			maxBlock = tempList.get(0);
			//ignore the dummy last node
			for(int i = 0; i < tempList.get(0).getSuccs().size(); i++){
				if(!tempList.get(0).getSuccs().get(i).equals(dummyLastBlk)){
					size++;
				}
			}
			maxNodes = size;
			for(int i = 1; i < tempList.size(); i++){
				int currentNodes = 0;
				//ignore the dummy last node
				for(int j = 0; j < tempList.get(i).getSuccs().size(); j++){
					if(!tempList.get(i).getSuccs().get(j).equals(dummyLastBlk)){
						currentNodes++;
					}
				}
				//compare nodes
				if(maxNodes < currentNodes){
					maxNodes = currentNodes;
					maxBlock = tempList.get(i);
				}
				else if(maxNodes == currentNodes){
					//compare statements
					int maxStmts = StmtsCount(maxBlock, (Block) ipd);
					int currentStmts = StmtsCount(tempList.get(i), (Block) ipd);
					if(maxStmts < currentStmts){
						maxBlock = tempList.get(i);
					}
					else if(maxStmts == currentStmts){
						//compare strings
						if(maxBlock.getHead().toString().compareTo(tempList.get(i).getHead().toString()) < 0){
							maxBlock = tempList.get(i);
						}
						if(maxBlock.getHead().toString().compareTo(tempList.get(i).getHead().toString()) == 0){
							maxBlock = maxBlock;
						}
					}
				}
			}
			tempList.remove(maxBlock);
			newSuccessorList.add(maxBlock);
		}
		
		return newSuccessorList;
		
		
	}
	
	public static double calculateCCD(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		double num1 = Math.abs(x1-x2)/(x1+x2);
		double num2 = Math.abs(y1-y2)/(y1+y2);
		double num3 = Math.abs(z1-z2)/(z1+z2);
		double num4 = Math.abs(w1-w2)/(w1+w2);
		return Math.max(Math.max(num3, num4), Math.max(num1, num2));
	}
	
	public static double calculateMDD(double num1, double num2){
		return Math.max(num1, num2);
	}

}