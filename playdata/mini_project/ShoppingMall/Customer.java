package ShoppingMall;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private int asset = 10000;
	private ArrayList<Order> paymentDetail;
	private HashMap<Product, Integer> cart;
	
	public Customer() {}
	
	public Customer(String id, String pwd, String name, String email) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.cart = new HashMap<>();
		this.paymentDetail = new ArrayList<>();
//		this.asset = asset;
//		this.paymentDetail = paymentDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAsset() {
		return asset;
	}

	public void setAsset(int asset) {
		this.asset = asset;
	}
	
	public ArrayList<Order> getPaymentDetail() {
		return paymentDetail;
	}

	public void putPaymentDetail(Order pd) {
		this.paymentDetail.add(pd);
	}

	public HashMap<Product, Integer> getCart() {
		return cart;
	}

	public void setCart(Product p, int amount) {
		this.cart.put(p, amount);
	}
	
	public void removeCart(Product p) {
		this.cart.remove(p);
	}
	
	public void clearCart() {
		this.cart.clear();
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", asset=" + asset
				+ ", paymentDetail=" + paymentDetail + "]";
	}
	
	
}
