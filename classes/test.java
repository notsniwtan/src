package classes;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList l1 = new ArrayList();
		l1.add(1);
		l1.add(3);
		l1.add(2);
		ArrayList l2 = new ArrayList();
		l2.add(3);
		l2.add(6);
		l2.add(5);
		LinkedHashSet h1 = new LinkedHashSet(l1);
		LinkedHashSet h2 = new LinkedHashSet(l2);
		h1.addAll(h2);
		System.out.println(h1);

	}

}
