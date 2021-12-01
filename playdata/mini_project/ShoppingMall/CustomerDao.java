package ShoppingMall;

import java.util.ArrayList;

public class CustomerDao implements Dao<Customer> {

	private ArrayList<Customer> users;
	
	public CustomerDao() {
		users = new ArrayList<>();
	}

	@Override
	public void insert(Customer c) {
		// TODO Auto-generated method stub
		users.add(c);
	}

	@Override
	public ArrayList<Customer> selectAll() {
		return users;
	}

	public Customer select(String name) {
		// TODO Auto-generated method stub
		for (Customer c : users) {
			if (c.getId().equals(name)) {
				return c;
			}
		}
		return null;

	}


}
