package ShoppingMall;

import java.util.ArrayList;

public interface Dao<T> {
	// �߰�
	// 1. Customer �߰� ���
	// 2. Product �߰� ���
	// 3. Order �߰� ���
	public void insert(T t);
	public ArrayList<T> selectAll();
	
}

//
//package ShoppingMall;
//
//import java.util.ArrayList;
//
//public class Dao {
//	private ArrayList<Customer> users;
//	private ArrayList<Product> prods;
//	private ArrayList<Order> orders;
//	
//	public Dao() {
//		users = new ArrayList<>();
//	}
//	
//	// �߰�
//	// 1. Customer �߰� ���
//	// 2. Product �߰� ���
//	// 3. Order �߰� ���
//	
//	public void insert(Customer c) {
//		users.add(c);
//	}
//	
//	public void insert(Product p) {
//		prods.add(p);
//	}
//	
//	public void insert(Order o) {
//		orders.add(o);
//	}
//	
//	
//	
//}
