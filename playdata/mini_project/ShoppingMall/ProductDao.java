package ShoppingMall;
import java.util.ArrayList;

public class ProductDao implements Dao<Product> {

	private ArrayList<Product> prods;
	
	public ProductDao() {
		prods = new ArrayList<>();
	}
	
	@Override
	public void insert(Product p) {
		// TODO Auto-generated method stub
		prods.add(p);
	}

	@Override
	public ArrayList<Product> selectAll() {
		return prods;
	}

	public Product select(String name) {
		// TODO Auto-generated method stub
		for (Product p : prods) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
		
	}

	public void update(Product p, String[] params) {
		// TODO Auto-generated method stub
		// params : name, price, amount
		p.setName(params[0]);
		p.setPrice(Integer.parseInt(params[1]));
		p.setAmount(Integer.parseInt(params[2]));
		
	}

	public void delete(Product p) {
		// TODO Auto-generated method stub
		
		prods.remove(p);

	}
		
	public Product selectByNum(int num) {
		// TODO Auto-generated method stub
		for (Product p : prods) {
			if (p.getNum() == num) {
				return p;
			}
		}
		return null;
		
	}


}
