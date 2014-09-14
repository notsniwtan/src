
public class test {

	public static void main(String[] args) {
		//String s1 = "$z0 = 0;"
		//String s2 = "$r2 := @caughtexception; staticinvoke <com.energysource.szj.android.Log: void e(java.lang.String,java.lang.String,java.lang.Throwable)>("SZJClassLoad.java", "classLoadException", $r2); $z0 = 0; goto [?= (branch)];"
		
		String s3 = "$r0.<net.youmi.android.d: boolean i> = 1; goto [?= $z1 = $r0.<net.youmi.android.d: boolean i>];";
		String s4 = "$r0.<net.youmi.android.d: boolean i> = 1; goto [?= $z1 = $r0.<net.youmi.android.d: boolean i>];";
				System.out.println(s3.compareTo(s4));
	}

}
