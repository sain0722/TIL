package ShoppingMall;

import java.util.ArrayList;

public interface Dao<T> {
	// 추가
	// 1. Customer 추가 기능
	// 2. Product 추가 기능
	// 3. Order 추가 기능
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
//	// 추가
//	// 1. Customer 추가 기능
//	// 2. Product 추가 기능
//	// 3. Order 추가 기능
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
