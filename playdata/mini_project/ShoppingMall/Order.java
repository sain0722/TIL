package ShoppingMall;

public class Order {
	
	private int num;		// 주문번호
	private String id;	// 주문자 아이디
	private Product p;		// 주문제품
	private int amount;		// 주문수량
	private int total_pay;	// 결제금액
	private static int cnt;
	
	public Order() {}
	
	public Order(Product p, int amount, String id) {
		super();
		this.num = ++cnt;
		this.p = p;
		this.amount = amount;
		this.id = id;
		this.total_pay = amount * p.getPrice();
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getTotal_pay() {
		return total_pay;
	}
	public void setTotal_pay(int total_pay) {
		this.total_pay = total_pay;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Order [num=" + num + ", id=" + id + ", p=" + p + ", amount=" + amount + ", total_pay=" + total_pay
				+ "]";
	}


	
	

	
}
