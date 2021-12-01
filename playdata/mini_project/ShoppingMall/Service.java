package ShoppingMall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Service {
	
	private CustomerDao cdao;
	private ProductDao pdao;
	private OrderDao odao;
	public static String login_id = null;
	
	public Service() {
		cdao = new CustomerDao();
		pdao = new ProductDao();
		odao = new OrderDao();
		
		// cdao »ý¼º°ú µ¿½Ã¿¡ °ü¸®ÀÚ °èÁ¤À» ³Ö¾îÁØ´Ù.
		Customer admin = new Customer("admin", "admin", "°ü¸®ÀÚ", "admin@naver.com");
		cdao.insert(admin);
		
		Product[] p = { new Product("¿À¸®ÅÐÆÐµù", 99000, 100), 
					    new Product("µ¶ÀÏ±º½º´ÏÄ¿Áî", 36600, 100), 
					    new Product("Ç×°øÁ¡ÆÛ", 72700, 100),
					    new Product("¿¡¾îÆÌÇÁ·Î", 169000, 10),
					    new Product("¾ç¸»¼¼Æ®", 9900, 1000)
		};
		
		for (Product x : p)
			pdao.insert(x);
	}
	
	// È¸¿ø°¡ÀÔ ±â´É
	public void join(Scanner sc) {
		System.out.println("££¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ È¸¿ø°¡ÀÔ ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡££");
		System.out.print("££ ID     : ");
		String id = sc.next();
		
		if (cdao.select(id) != null) {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢                ÀÌ¹Ì Á¸ÀçÇÏ´Â IDÀÔ´Ï´Ù               ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
			return;
		}
		
		System.out.print("££ PWD    : ");
		String pwd = sc.next();
		
		System.out.print("££ NAME   : ");
		String name = sc.next();
		
		System.out.print("££ EMAIL  : ");
		String email = sc.next();
		
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢                È¸¿ø°¡ÀÔÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù              ¦¢");
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
		
		Customer c = new Customer(id, pwd, name, email);
		cdao.insert(c);
	}
	
	// ·Î±×ÀÎ ±â´É
	// 0: ·Î±×ÀÎ ½ÇÆÐ
	// 1: °ü¸®ÀÚ ·Î±×ÀÎ
	// 2: °í°´ ·Î±×ÀÎ
	public int login(Scanner sc) {
		if (login_id != null) {
			System.out.println("ÀÌ¹Ì ·Î±×ÀÎÀÌ µÇ¾îÀÖ½À´Ï´Ù.");
			return 0;
		}
		
		System.out.println("££¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ ·Î±×ÀÎ ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡££");
		System.out.print("££ ID     : ");
		String id = sc.next();

		System.out.print("££ PWD    : ");
		String pwd = sc.next();
		
		if (cdao.select(id) != null) {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢                 ·Î±×ÀÎÀÌ µÇ¾ú½À´Ï´Ù                 ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
			login_id = id;
			if (id.equals("admin")) {
				System.out.println("(admin) °ü¸®ÀÚ °èÁ¤À¸·Î ·Î±×ÀÎ");
				return 1;
			}
			return 2;

		} else {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢            ID¿Í PW¸¦ È®ÀÎÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù           ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
		}
		return 0;
	}
	
	// ·Î±×¾Æ¿ô ±â´É
	public void logout() {
		if (login_id == null) {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢               ·Î±×ÀÎÀÌ µÇ¾îÀÖÁö ¾Ê½À´Ï´Ù              ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
			return;
		}
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢                 ·Î±×¾Æ¿ô µÇ¾ú½À´Ï´Ù                 ¦¢");
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
		login_id = null;
	}
	
	/***
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 *                    °í°´ È­¸é
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 */
	// 1. ³»Á¤º¸È®ÀÎ ±â´É
	public void printMyInfo() {
		Customer c = cdao.select(login_id);
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ My Info ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.printf("¦¢  ID     :  %s\t                          ¦¢\n", c.getId());
		System.out.printf("¦¢  PW     :  %s\t                          ¦¢\n", "*".repeat(c.getPwd().length()));
		System.out.printf("¦¢  NAME   :  %s\t                          ¦¢\n", c.getName());
		System.out.printf("¦¢  EMAIL  :  %s\t\t\t  ¦¢\n", c.getEmail());
		System.out.printf("¦¢  BALANCE:  %,d\t                          ¦¢\n", c.getAsset());
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
		
	}
	
	// »óÇ°¸ñ·Ï Ãâ·Â
	public void printProductList() {
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ SHOPPING MALL ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á ÇöÀç»óÇ° ¸ñ·Ï ¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¡á¦¢");
		System.out.println("¦¢ »óÇ°¹øÈ£\t ¦¢ »óÇ°¸í \t¦¢ °¡°Ý \t\t¦¢ Àç°í·®\t  ¦¢");
		System.out.println("¦¢--------+--------------+---------------+---------¦¢");
		for(Product x : pdao.selectAll()) {
			if (x.getName().getBytes().length <= 5) {
				if (x.getPrice() >= 10000) {
					System.out.printf("¦¢%3d \t ¦¢ %s \t\t¦¢ %,d\t¦¢ %d\t  ¦¢\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());					
				} else {
					System.out.printf("¦¢%3d \t ¦¢ %s \t\t¦¢ %,d\t\t¦¢ %d\t  ¦¢\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());				
				}

			} else {
				if (x.getPrice() >= 10000) {
					System.out.printf("¦¢%3d \t ¦¢ %s \t¦¢ %,d\t¦¢ %d\t  ¦¢\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());					
				} else {
					System.out.printf("¦¢%3d \t ¦¢ %s \t¦¢ %,d\t\t¦¢ %d\t  ¦¢\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());				
				}
			}
		}
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
	}
	
	// Àå¹Ù±¸´Ï¿¡ ³Ö±â
	// Á¦Ç°¹øÈ£·Î ÇØ´ç Á¦Ç° °´Ã¼¸¦ Ã£´Â´Ù
	// ±× °´Ã¼¸¦ ÇöÀç ·Î±×ÀÎµÈ À¯ÀúÀÇ Àå¹Ù±¸´Ï¿¡ ³Ö´Â´Ù.
	public void putCart(int num, int amount) {
		
		Product p = pdao.selectByNum(num);
		Customer c = cdao.select(login_id);
		HashMap<Product, Integer> cart = c.getCart();
		
		// 1. ÀÌ¹Ì Àå¹Ù±¸´Ï¿¡ p°¡ ÀÖ´Ù¸é, ¼ö·®¸¸ Ãß°¡ÇÑ´Ù.
		// 2. Àå¹Ù±¸´Ï¿¡ ¾ø´Â p¶ó¸é »óÇ° p¸¦ amount°³ ¸¸Å­ cart¿¡ ´ã´Â´Ù.
		if (cart.get(p) == null) {
			c.setCart(p, amount);			
		} else {
			int preAmount = cart.get(p);
			c.setCart(p, preAmount + amount);
		}
	}
	
	public boolean buy() {
		Customer c = cdao.select(login_id);
		HashMap<Product, Integer> cart = c.getCart();
		ArrayList<Order> orders = new ArrayList<>();
		
		int[] amounts = new int[cart.size()];
		// Ä«Æ®¿¡ ´ã±ä ¿ä¼Ò
		int total_pay = 0;
		int amount = 0;
		int i = 0;
		for (Product x : cart.keySet()) {
			amount = cart.get(x);
			Order o = new Order(x, cart.get(x), c.getId());
			orders.add(o);
			
			total_pay += o.getTotal_pay();
			
			amounts[i++] = x.getAmount() - amount;	// Á¦Ç° xÀÇ ÇöÀç Àç°í - ÀÔ·Â¹ÞÀº ¼ö·®
			x.setAmount(x.getAmount() - amount);
		}	
		// ¸¸¾à ÁÖ¹®±Ý¾×ÀÌ º¸À¯±Ý¾×º¸´Ù ¸¹´Ù¸é
		if (c.getAsset() < total_pay) {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ WARNINGS ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢\t\t  ÀÜ¾×ÀÌ ºÎÁ·ÇÕ´Ï´Ù \t\t        ¦¢");
			if (c.getAsset() < 10000 && total_pay - c.getAsset() < 10000) {
				System.out.printf("¦¢\t\t ÇöÀç ÀÜ°í: %,d \t\t        ¦¢\n", c.getAsset());
				System.out.printf("¦¢\t\t ºÎÁ· ±Ý¾×: %,d \t\t        ¦¢\n", total_pay - c.getAsset());					
			} else if (total_pay - c.getAsset() < 10000) {
				System.out.printf("¦¢\t\t ÇöÀç ÀÜ°í: %,d \t        ¦¢\n", c.getAsset());
				System.out.printf("¦¢\t\t ºÎÁ· ±Ý¾×: %,d \t\t        ¦¢\n", total_pay - c.getAsset());									
				
			}
			
			else {
				System.out.printf("¦¢\t\t ÇöÀç ÀÜ°í: %,d \t        ¦¢\n", c.getAsset());
				System.out.printf("¦¢\t\t ºÎÁ· ±Ý¾×: %,d \t        ¦¢\n", total_pay - c.getAsset());									
			}
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
			return false;
			
		} else {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢                 ±¸¸ÅÃ³¸® µÇ¾ú½À´Ï´Ù                 ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
			
			// orderDAO¿¡ ÇØ´ç ÁÖ¹® Ãß°¡
			for (Order o : orders)
				odao.insert(o);
			
			// ÇØ´ç Á¦Ç°ÀÇ Àç°í °»½Å
			i = 0;
			for (Product x : cart.keySet()) {
				x.setAmount(amounts[i++]);
			}
			// ÇØ´ç °í°´ÀÇ ÀÜ¾× °»½Å
			c.setAsset(c.getAsset() - total_pay);
			
		}
		return true;
	}
	
	
	// 2. ¼îÇÎ
	// 	2-1. »óÇ°¸ñ·Ï È®ÀÎ ±â´É
	// 	2-2. Àå¹Ù±¸´Ï¿¡ ³Ö±â ±â´É
	// 	2-3. ¼±ÅÃ»óÇ° ¹Ù·Î°áÁ¦
	public void shopping(Scanner sc) {
		
		printProductList();
		// ±¸¸ÅÀÇ»ç ¹°¾îº¸±â, 
		System.out.println("1.»óÇ°±¸¸Å 2.µÚ·Î°¡±â");
		String menu = sc.next();
		// Àå¹Ù±¸´Ï¿¡ ³Ö±â / ¹Ù·Î°áÁ¦ / ±¸¸ÅÃë¼Ò
		switch (menu) {
		case "1":

			int num = 0;
			while (true) {
				System.out.println("££¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ »óÇ°±¸¸Å ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡££");
				System.out.println("££ ±¸¸ÅÇÒ »óÇ°ÀÇ Á¦Ç°¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä. (-1 ÀÔ·Â½Ã ÀÔ·ÂÁ¾·á)    ££");
				System.out.print("££ Á¦Ç°¹øÈ£     : ");
				num = sc.nextInt();
				if (num < 0) break;
				System.out.print("££ ±¸¸Å¼ö·®     : ");
				int amount = sc.nextInt();
				if (amount > pdao.selectByNum(num).getAmount()) {
					System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
					System.out.println("¦¢                  Àç°í·®ÀÌ ºÎÁ·ÇÕ´Ï´Ù                ¦¢");
					System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
					continue;
				}
				System.out.println("££ " + pdao.selectByNum(num).getName() + " " + amount + "°³¸¦ Àå¹Ù±¸´Ï¿¡ ´ã¾Ò½À´Ï´Ù.");
				putCart(num, amount);
			}
			break;
			
		case "2":
			break;
		}
		
//		System.out.println("Àå¹Ù±¸´Ï¿¡ ´ã±ä »óÇ°µéÀ» ¹Ù·Î ±¸¸ÅÇÏ½Ã°Ú½À´Ï±î? (Y/N)");
//		String mode = sc.next();
//		if (mode.equals("Y")) {
//			// °áÁ¦
//			buy();
//		} else {
//			System.out.println("¼îÇÎÀ» Á¾·áÇÕ´Ï´Ù.");
//		}
	}
	
	// 3. Àå¹Ù±¸´Ï È®ÀÎ
	public void myCart(Scanner sc) {
		Customer c = cdao.select(login_id);
		HashMap<Product, Integer> cart = c.getCart();

		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ CART ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		if (cart.isEmpty()) {
			System.out.println("¦¢               Àå¹Ù±¸´Ï°¡ ºñ¾îÀÖ½À´Ï´Ù.              ¡¡  ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");

			return;
		}
		
		System.out.println("¦¢»óÇ°¸í \t\t ¦¢ ÁÖ¹®°³¼ö \t¦¢ ±Ý¾×\t\t  ¦¢");
		System.out.println("¦¢----------------+--------------+-----------------¦¢");
		int total_pay = 0;
		for (Product x : cart.keySet()) {
			int orderAmount = cart.get(x);
			total_pay += x.getPrice() * orderAmount;
//			System.out.println(x.getName() + "\t" + orderAmount + "°³" + "\t" + x.getPrice() * orderAmount + "¿ø");
			System.out.printf("¦¢%s \t ¦¢%3d°³ \t\t¦¢ %,d¿ø\t  ¦¢\n", x.getName(), orderAmount, x.getPrice() * orderAmount);
		
		}
		System.out.println("¦¢-------------------------------+-----------------¦¢");
		System.out.printf("¦¢ÃÑ °áÁ¦±Ý¾×\t\t\t¦¢ %,d¿ø\t  ¦¢\n", total_pay);
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
	
		System.out.println("Àå¹Ù±¸´Ï¿¡ ´ã±ä »óÇ°µéÀ» ¹Ù·Î ±¸¸ÅÇÏ½Ã°Ú½À´Ï±î? (Y/N)");
		String mode = sc.next();
		if (mode.equals("Y")) {
			// °áÁ¦
			boolean result = buy();
			// °áÁ¦¿¡ ¼º°øÇß´Ù¸é, °áÁ¦³»¿ª¿¡ Order °´Ã¼¸¦ Àü¼Û
			if (result) {
				for (Product x : cart.keySet()) {
					int orderAmount = cart.get(x);
					Order o = new Order(x, orderAmount, x.getName());
					c.putPaymentDetail(o);
				}
				
				// Àå¹Ù±¸´Ï¸¦ ºñ¿î´Ù.
				c.clearCart();
			}
			
		} else {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢                   Àå¹Ù±¸´Ï Á¾·á                   ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");

		}
		
	}
	
	// 4. °áÁ¦³»¿ª
	public void printPayments() {
		Customer c = cdao.select(login_id);
		ArrayList<Order> pds = c.getPaymentDetail();
		int total_pay = 0;
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ HISTORY ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢»óÇ°¸í \t\t ¦¢ ÁÖ¹®°³¼ö \t¦¢ ±Ý¾×\t\t  ¦¢");
		System.out.println("¦¢----------------+--------------+-----------------¦¢");
		
		for (Order x : pds) {
			if (x.getTotal_pay() > 10000)
				System.out.printf("¦¢%s \t ¦¢ %d \t\t¦¢ %,d\t  ¦¢\n", x.getP().getName(), x.getAmount(), x.getTotal_pay());
			else
				System.out.printf("¦¢%s \t ¦¢ %d \t\t¦¢ %,d\t\t  ¦¢\n", x.getP().getName(), x.getAmount(), x.getTotal_pay());
			total_pay += x.getTotal_pay();
				
		}
		System.out.println("¦¢-------------------------------------------------¦¢");
		if (total_pay < 10000)
			System.out.printf("¦¢ Total                         ¦¢ %,d\t\t  ¦¢\n", total_pay);
		else
			System.out.printf("¦¢ Total                         ¦¢ %,d\t  ¦¢\n", total_pay);

		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");

		}
	
	
	// 5. ÃæÀü
	public void charging(Scanner sc) {
		
		Customer c = cdao.select(login_id);		

		System.out.println("££¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ ÀÜ¾×ÃæÀü ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡££");
		System.out.printf("££ ÇöÀç ÀÜ°í\t: %,d\n",c.getAsset());
		System.out.print("££ ÃæÀü ±Ý¾×\t: ");
		int charge = sc.nextInt();

		
		System.out.printf("\n££ ÃæÀü ¿Ï·á \n££ ÃæÀü ÈÄ ÀÜ°í\t: %,d\n", c.getAsset() + charge);
		c.setAsset(c.getAsset() + charge);
	}

	/***
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 *                       °ü¸®ÀÚ È­¸é
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 */
	public void productListManage(Scanner sc) {
		
		// 1.µî·Ï 2.¼öÁ¤ 3.»èÁ¦ 4.µÚ·Î
		String st = "1.µî·Ï 2.¼öÁ¤ 3.»èÁ¦ 4.µÚ·Î";
		boolean flag = true;
		String name;
		int price = 0;
		int amount = 0;
		
		while (flag) {
			
			printProductList();
			System.out.println(st);
			String menu = sc.next();
			Product p;
			
			switch (menu) {
			case "1":
				// product µî·Ï
				// µî·ÏÇÒ Á¦Ç°ÀÇ Á¤º¸ ÀÔ·Â
				System.out.println("££¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ »óÇ°µî·Ï ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡££");
				System.out.print("££ »óÇ°¸í\t: ");
				name = sc.next();
				
				if (pdao.select(name) != null) {
					System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
					System.out.println("¦¢               ÀÌ¹Ì Á¸ÀçÇÏ´Â »óÇ°¸íÀÔ´Ï´Ù              ¦¢");
					System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
					flag = false;
					break;
				}
				
				System.out.print("££ °¡°Ý\t: ");
				price = sc.nextInt();
				System.out.print("££ ¼ö·®\t: ");
				amount = sc.nextInt();

				
				p = new Product(name, price, amount);
				pdao.insert(p);
				break;
			case "2":
				// product ¼öÁ¤
				// ¼öÁ¤ÇÒ Á¦Ç°ÀÇ »óÇ°¸í ÀÔ·Â
				System.out.println("££¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ »óÇ°¼öÁ¤ ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡££");
				System.out.print("££ »óÇ°¸í\t: ");
				name = sc.next();
				
				p = pdao.select(name);
				
				// »óÇ°¸íÀÌ Á¸ÀçÇÏÁö ¾ÊÀ¸¸é "¼öÁ¤"¸Þ´º Á¾·á
				if (p == null) {
					System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
					System.out.println("¦¢               Á¸ÀçÇÏÁö ¾Ê´Â »óÇ°¸íÀÔ´Ï´Ù              ¦¢");
					System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
					break;
				} else {
					
					System.out.println(name + "»óÇ°À» ¼öÁ¤ÇÕ´Ï´Ù.");
					System.out.print("££ ¼öÁ¤ °¡°Ý\t: ");
					price = sc.nextInt();
					System.out.print("££ ¼öÁ¤ ¼ö·®\t: ");
					amount = sc.nextInt();
					// ÇÑ¹ø¿¡ ³Ñ°ÜÁÖ±â À§ÇØ Çüº¯È¯À» »ç¿ë
					String[] params = { name, String.valueOf(price), String.valueOf(amount) };
					
					pdao.update(p, params);
				}
				break;
				
			case "3":
				// product »èÁ¦
				// »èÁ¦ÇÒ Á¦Ç°ÀÇ »óÇ°¸í ÀÔ·Â
				System.out.println("££¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ »óÇ°»èÁ¦ ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡££");
				System.out.print("££ »óÇ°¸í\t: ");
				name = sc.next();
				
				p = pdao.select(name);
				
				if (p == null) {
					System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ NOTICE ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
					System.out.println("¦¢               Á¸ÀçÇÏÁö ¾Ê´Â »óÇ°¸íÀÔ´Ï´Ù              ¦¢");
					System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
					break;
				} else {
					System.out.printf("\"%s\" »óÇ°À» »èÁ¦ÇÏ½Ã°Ú½À´Ï±î? (Y/N)", name);
					String isDelete = sc.next();
					
					if (isDelete.equals("Y")) {
						System.out.printf("\"%s\" »óÇ°À» ¼º°øÀûÀ¸·Î »èÁ¦Çß½À´Ï´Ù.\n", name);
						pdao.delete(p);
					} else {
						System.out.println("»èÁ¦¸¦ Ãë¼ÒÇÕ´Ï´Ù.");
					}
				}
				break;
				
			case "4":
				// µÚ·Î°¡±â
				flag = false;
				break;
			}
		}
		
	}
	// ¸ðµç À¯ÀúÀÇ °áÁ¦³»¿ª È®ÀÎ
	public void orderPrintAll() {
		ArrayList<Customer> users = cdao.selectAll();
		
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ HISTORY ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢»óÇ°ÀÌ¸§ \t\t ¦¢ ÁÖ¹®°³¼ö \t¦¢ °áÁ¦±Ý¾×\t\t  ¦¢");
		System.out.println("¦¢----------------+--------------+-----------------¦¢");
		for (Customer c : users) {
			ArrayList<Order> o = c.getPaymentDetail();
			
			if (o.isEmpty()) continue; 
			System.out.printf("¦¢ \"%s\" ´ÔÀÇ °áÁ¦³»¿ª \t\t\t\t  ¦¢\n", c.getId());
			
			int total_pay = 0;
			String tab = "\t";
			for (Order x : o) {
				if (x.getP().getName().getBytes().length <= 5) {
					System.out.printf("¦¢ %s %s ¦¢ %d\t\t¦¢", x.getP().getName(), tab.repeat(2), x.getAmount(), x.getTotal_pay());
				} else {
					System.out.printf("¦¢ %s \t ¦¢ %d\t\t¦¢", x.getP().getName(), x.getAmount(), x.getTotal_pay());
				}
				if (x.getTotal_pay() < 10000)
					System.out.printf(" %,d%s  ¦¢\n", x.getTotal_pay(), tab.repeat(2));
				else
					System.out.printf(" %,d%s  ¦¢\n", x.getTotal_pay(), tab);					
				total_pay += x.getTotal_pay();
			}
			if (total_pay <= 10000)
				System.out.printf("¦¢ Total                         ¦¢ %,d%s  ¦¢\n", total_pay, tab.repeat(2));
			else
				System.out.printf("¦¢ Total                         ¦¢ %,d%s  ¦¢\n", total_pay, tab);
				
			total_pay = 0;
			System.out.println("¦¢-------------------------------------------------¦¢");
		}
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");

	}
	
	// 3. À¯Àú °ü¸® ±â´É
	public void userManage() {
		ArrayList<Customer> allUsers = cdao.selectAll();
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡ User Infomation ¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		for (Customer c: allUsers) {
			if (c.getId().equals("admin")) continue;
			System.out.printf("¦¢  ID     :  %s\t                          ¦¢\n", c.getId());
			System.out.printf("¦¢  PW     :  %s\t                          ¦¢\n", "*".repeat(c.getPwd().length()));
			System.out.printf("¦¢  NAME   :  %s\t                          ¦¢\n", c.getName());
			System.out.printf("¦¢  EMAIL  :  %s\t                          ¦¢\n", c.getEmail());
			System.out.println("¦¢-------------------------------------------------¦¢");
		}
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
		
	}
}
