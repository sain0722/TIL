package ShoppingMall;

import java.util.ArrayList;

public class OrderDao implements Dao<Order> {

	private ArrayList<Order> orders;

	public OrderDao() {
		orders = new ArrayList<>();
	}
	@Override
	public void insert(Order o) {
		// TODO Auto-generated method stub
		orders.add(o);
	}

	@Override
	public ArrayList<Order> selectAll() {
		return orders;
	}

}
