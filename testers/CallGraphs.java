package testers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CallGraphs
{
	public static String printStr = "test";
	public String anotherStr;
	private int a = 10;
	private int b = 20;
	
	public static void main(String[] args) {
		doStuff();
	}
	
	public static void doStuff() {
		
		CallGraphs cg = new CallGraphs();
		
		int c = cg.a + cg.b;
		System.out.println(c);
		/*new Thread(new Runnable(){
			@Override
			public void run(){
				System.out.println("Test");
			}
		}).start();*/
		new Thread(){
			@Override
			public void run(){
				System.out.println("Test");
			}
		}.start();
		
		String t = new A().boo();
		cg.anotherStr = t;
		printStr = t;
		
		
		Timer timer = new Timer();
		TimerTask tt = new TimerTask(){

			@Override
			public void run() {
				int a = new Random().nextInt()%100;
				if (a < 50){
					new A().foo();
				}else{
					new A().boo();
				}
			}
			
		};
		timer.schedule(tt, 1000);
		
		
	}
}

class A
{
	public void foo() {
		new Thread(){
			@Override
			public void run(){
				bar();
			}
		}.start();
	}
	
	public static void unreadable(){
		System.out.println("test");
	}
	
	public String boo(){
		System.out.println(CallGraphs.printStr);
		return new String("hello");
	}
	
	public void bar() {
		int cnt = 99;
		int[] arr = {1,2,3,4,5};
	//	int x=0;
	//	int y=10;
	//	boolean b1 = true;
		int d=0;
		int s=54;
		int a = 5,b,c,k = 5;
		
		
	/*	for(int x = 0; x<5; x++){
			s++;
			System.out.println(s);
			for(int i = 0; i<3; i++){
				i++;
			}
			if(x==7){
				break;
			}*/

	/*	while (cnt < 100){
			if (cnt % 2 == 0){
				cnt = cnt * 2 + 1;
			}else{
				cnt = cnt + 1;
			}
		}*/
		
	/*	for(int i = 0; i<2; i++){
			System.out.println("elloo");
		}
		System.out.println("elloo");
	*/
		while(cnt>1){
			System.out.println("elloo");
			System.out.println("elloo");
			if(cnt>6){
				break;
			}
		}
		System.out.println("elloo");

	
	/*	while(s!=0){
			System.out.println("hello");
			System.out.println("hello");
			while(s>0){
				System.out.println("hello");
			}
		}*/
	//	System.out.println("hello");

		
		
		
	/*	if(k>0){
			a = 0;
			while(k>0){
				int h = 0;
			}
		}
		else{
			b=0;
			return;
		}*/
		
/*		while(a>0){
			System.out.println(k);
			if(k>1){
				break;
			}
			System.out.println(k);
		}*/
		
	//	b = a+k;
/*		switch (k){
			case 0: System.out.println("0");
			break;
			
			case 1: System.out.println("1");
			
			
			case 2: System.out.println("2");
			break;
			
			case 3: System.out.println("3");
			break;
		
			case 4: System.out.println("4");
			break;
			
			default: System.out.println("default");
			break;
		
		}*/
	
/*		for (int i = 0 ; i < 5; i++){
			System.out.println(arr[i]);
			if(a>5){
				System.out.println(k);
			}
			else{
				System.out.println(k);
			}
			for (int j : arr)
			{
				System.out.println(i);
				System.out.println(i);
			}
		}

		
		

		
		if(a>0){
			if(k>0){
				System.out.println(k);
			}
			else{
				System.out.println(k);
			}
		}
		else{
			System.out.println(k);
		}
		*/

		
	}
}