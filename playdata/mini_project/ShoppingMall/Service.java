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
		
		// cdao 儅撩婁 翕衛縑 婦葬濠 啗薑擊 厥橫遽棻.
		Customer admin = new Customer("admin", "admin", "婦葬濠", "admin@naver.com");
		cdao.insert(admin);
		
		Product[] p = { new Product("螃葬瓚ぬ註", 99000, 100), 
					    new Product("絮橾捱蝶棲醴鍔", 36600, 100), 
					    new Product("о奢薄ぷ", 72700, 100),
					    new Product("縑橫とЩ煎", 169000, 10),
					    new Product("曄蜓撮お", 9900, 1000)
		};
		
		for (Product x : p)
			pdao.insert(x);
	}
	
	// �蛾灠㊣� 晦棟
	public void join(Scanner sc) {
		System.out.println("ㄒ式式式式式式式式式式式式式式式式式式式式 �蛾灠㊣� 式式式式式式式式式式式式式式式式式式式式ㄒ");
		System.out.print("ㄒ ID     : ");
		String id = sc.next();
		
		if (cdao.select(id) != null) {
			System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
			System.out.println("弛                檜嘐 襄營ж朝 ID殮棲棻               弛");
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
			return;
		}
		
		System.out.print("ㄒ PWD    : ");
		String pwd = sc.next();
		
		System.out.print("ㄒ NAME   : ");
		String name = sc.next();
		
		System.out.print("ㄒ EMAIL  : ");
		String email = sc.next();
		
		System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
		System.out.println("弛                �蛾灠㊣埬� 諫猿腎歷蝗棲棻              弛");
		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
		
		Customer c = new Customer(id, pwd, name, email);
		cdao.insert(c);
	}
	
	// 煎斜檣 晦棟
	// 0: 煎斜檣 褒ぬ
	// 1: 婦葬濠 煎斜檣
	// 2: 堅偌 煎斜檣
	public int login(Scanner sc) {
		if (login_id != null) {
			System.out.println("檜嘐 煎斜檣檜 腎橫氈蝗棲棻.");
			return 0;
		}
		
		System.out.println("ㄒ式式式式式式式式式式式式式式式式式式式式 煎斜檣 式式式式式式式式式式式式式式式式式式式式ㄒ");
		System.out.print("ㄒ ID     : ");
		String id = sc.next();

		System.out.print("ㄒ PWD    : ");
		String pwd = sc.next();
		
		if (cdao.select(id) != null) {
			System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
			System.out.println("弛                 煎斜檣檜 腎歷蝗棲棻                 弛");
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
			login_id = id;
			if (id.equals("admin")) {
				System.out.println("(admin) 婦葬濠 啗薑戲煎 煎斜檣");
				return 1;
			}
			return 2;

		} else {
			System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
			System.out.println("弛            ID諦 PW蒂 �挫恉� 輿衛晦 夥奧棲棻           弛");
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
		}
		return 0;
	}
	
	// 煎斜嬴醒 晦棟
	public void logout() {
		if (login_id == null) {
			System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
			System.out.println("弛               煎斜檣檜 腎橫氈雖 彊蝗棲棻              弛");
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
			return;
		}
		System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
		System.out.println("弛                 煎斜嬴醒 腎歷蝗棲棻                 弛");
		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
		login_id = null;
	}
	
	/***
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 *                    堅偌 �飛�
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 */
	// 1. 頂薑爾�挫� 晦棟
	public void printMyInfo() {
		Customer c = cdao.select(login_id);
		System.out.println("忙式式式式式式式式式式式式式式式式式式式式 My Info 式式式式式式式式式式式式式式式式式式式式忖");
		System.out.printf("弛  ID     :  %s\t                          弛\n", c.getId());
		System.out.printf("弛  PW     :  %s\t                          弛\n", "*".repeat(c.getPwd().length()));
		System.out.printf("弛  NAME   :  %s\t                          弛\n", c.getName());
		System.out.printf("弛  EMAIL  :  %s\t\t\t  弛\n", c.getEmail());
		System.out.printf("弛  BALANCE:  %,d\t                          弛\n", c.getAsset());
		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
		
	}
	
	// 鼻ヶ跡煙 轎溘
	public void printProductList() {
		System.out.println("忙式式式式式式式式式式式式式式式式式 SHOPPING MALL 式式式式式式式式式式式式式式式式式忖");
		System.out.println("弛﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥ ⑷營鼻ヶ 跡煙 ﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥﹥弛");
		System.out.println("弛 鼻ヶ廓�αt 弛 鼻ヶ貲 \t弛 陛問 \t\t弛 營堅榆\t  弛");
		System.out.println("弛--------+--------------+---------------+---------弛");
		for(Product x : pdao.selectAll()) {
			if (x.getName().getBytes().length <= 5) {
				if (x.getPrice() >= 10000) {
					System.out.printf("弛%3d \t 弛 %s \t\t弛 %,d\t弛 %d\t  弛\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());					
				} else {
					System.out.printf("弛%3d \t 弛 %s \t\t弛 %,d\t\t弛 %d\t  弛\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());				
				}

			} else {
				if (x.getPrice() >= 10000) {
					System.out.printf("弛%3d \t 弛 %s \t弛 %,d\t弛 %d\t  弛\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());					
				} else {
					System.out.printf("弛%3d \t 弛 %s \t弛 %,d\t\t弛 %d\t  弛\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());				
				}
			}
		}
		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
	}
	
	// 濰夥掘棲縑 厥晦
	// 薯ヶ廓�ㄦ� п渡 薯ヶ 偌羹蒂 瓊朝棻
	// 斜 偌羹蒂 ⑷營 煎斜檣脹 嶸盪曖 濰夥掘棲縑 厥朝棻.
	public void putCart(int num, int amount) {
		
		Product p = pdao.selectByNum(num);
		Customer c = cdao.select(login_id);
		HashMap<Product, Integer> cart = c.getCart();
		
		// 1. 檜嘐 濰夥掘棲縑 p陛 氈棻賊, 熱榆虜 蹺陛и棻.
		// 2. 濰夥掘棲縑 橈朝 p塭賊 鼻ヶ p蒂 amount偃 虜躑 cart縑 氬朝棻.
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
		// 蘋お縑 氬曹 蹂模
		int total_pay = 0;
		int amount = 0;
		int i = 0;
		for (Product x : cart.keySet()) {
			amount = cart.get(x);
			Order o = new Order(x, cart.get(x), c.getId());
			orders.add(o);
			
			total_pay += o.getTotal_pay();
			
			amounts[i++] = x.getAmount() - amount;	// 薯ヶ x曖 ⑷營 營堅 - 殮溘嫡擎 熱榆
			x.setAmount(x.getAmount() - amount);
		}	
		// 虜擒 輿僥旎擋檜 爾嶸旎擋爾棻 號棻賊
		if (c.getAsset() < total_pay) {
			System.out.println("忙式式式式式式式式式式式式式式式式式式式 WARNINGS 式式式式式式式式式式式式式式式式式式忖");
			System.out.println("弛\t\t  濤擋檜 睡褶м棲棻 \t\t        弛");
			if (c.getAsset() < 10000 && total_pay - c.getAsset() < 10000) {
				System.out.printf("弛\t\t ⑷營 濤堅: %,d \t\t        弛\n", c.getAsset());
				System.out.printf("弛\t\t 睡褶 旎擋: %,d \t\t        弛\n", total_pay - c.getAsset());					
			} else if (total_pay - c.getAsset() < 10000) {
				System.out.printf("弛\t\t ⑷營 濤堅: %,d \t        弛\n", c.getAsset());
				System.out.printf("弛\t\t 睡褶 旎擋: %,d \t\t        弛\n", total_pay - c.getAsset());									
				
			}
			
			else {
				System.out.printf("弛\t\t ⑷營 濤堅: %,d \t        弛\n", c.getAsset());
				System.out.printf("弛\t\t 睡褶 旎擋: %,d \t        弛\n", total_pay - c.getAsset());									
			}
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
			return false;
			
		} else {
			System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
			System.out.println("弛                 掘衙籀葬 腎歷蝗棲棻                 弛");
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
			
			// orderDAO縑 п渡 輿僥 蹺陛
			for (Order o : orders)
				odao.insert(o);
			
			// п渡 薯ヶ曖 營堅 偵褐
			i = 0;
			for (Product x : cart.keySet()) {
				x.setAmount(amounts[i++]);
			}
			// п渡 堅偌曖 濤擋 偵褐
			c.setAsset(c.getAsset() - total_pay);
			
		}
		return true;
	}
	
	
	// 2. 澗ё
	// 	2-1. 鼻ヶ跡煙 �挫� 晦棟
	// 	2-2. 濰夥掘棲縑 厥晦 晦棟
	// 	2-3. 摹鷗鼻ヶ 夥煎唸薯
	public void shopping(Scanner sc) {
		
		printProductList();
		// 掘衙曖餌 僭橫爾晦, 
		System.out.println("1.鼻ヶ掘衙 2.菴煎陛晦");
		String menu = sc.next();
		// 濰夥掘棲縑 厥晦 / 夥煎唸薯 / 掘衙鏃模
		switch (menu) {
		case "1":

			int num = 0;
			while (true) {
				System.out.println("ㄒ式式式式式式式式式式式式式式式式式式式式 鼻ヶ掘衙 式式式式式式式式式式式式式式式式式式式式ㄒ");
				System.out.println("ㄒ 掘衙й 鼻ヶ曖 薯ヶ廓�ㄧ� 殮溘ж撮蹂. (-1 殮溘衛 殮溘謙猿)    ㄒ");
				System.out.print("ㄒ 薯ヶ廓��     : ");
				num = sc.nextInt();
				if (num < 0) break;
				System.out.print("ㄒ 掘衙熱榆     : ");
				int amount = sc.nextInt();
				if (amount > pdao.selectByNum(num).getAmount()) {
					System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
					System.out.println("弛                  營堅榆檜 睡褶м棲棻                弛");
					System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
					continue;
				}
				System.out.println("ㄒ " + pdao.selectByNum(num).getName() + " " + amount + "偃蒂 濰夥掘棲縑 氬懊蝗棲棻.");
				putCart(num, amount);
			}
			break;
			
		case "2":
			break;
		}
		
//		System.out.println("濰夥掘棲縑 氬曹 鼻ヶ菟擊 夥煎 掘衙ж衛啊蝗棲梱? (Y/N)");
//		String mode = sc.next();
//		if (mode.equals("Y")) {
//			// 唸薯
//			buy();
//		} else {
//			System.out.println("澗ё擊 謙猿м棲棻.");
//		}
	}
	
	// 3. 濰夥掘棲 �挫�
	public void myCart(Scanner sc) {
		Customer c = cdao.select(login_id);
		HashMap<Product, Integer> cart = c.getCart();

		System.out.println("忙式式式式式式式式式式式式式式式式式式式 CART 式式式式式式式式式式式式式式式式式式式式式式式式忖");
		if (cart.isEmpty()) {
			System.out.println("弛               濰夥掘棲陛 綠橫氈蝗棲棻.              ﹛  弛");
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");

			return;
		}
		
		System.out.println("弛鼻ヶ貲 \t\t 弛 輿僥偃熱 \t弛 旎擋\t\t  弛");
		System.out.println("弛----------------+--------------+-----------------弛");
		int total_pay = 0;
		for (Product x : cart.keySet()) {
			int orderAmount = cart.get(x);
			total_pay += x.getPrice() * orderAmount;
//			System.out.println(x.getName() + "\t" + orderAmount + "偃" + "\t" + x.getPrice() * orderAmount + "錳");
			System.out.printf("弛%s \t 弛%3d偃 \t\t弛 %,d錳\t  弛\n", x.getName(), orderAmount, x.getPrice() * orderAmount);
		
		}
		System.out.println("弛-------------------------------+-----------------弛");
		System.out.printf("弛識 唸薯旎擋\t\t\t弛 %,d錳\t  弛\n", total_pay);
		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
	
		System.out.println("濰夥掘棲縑 氬曹 鼻ヶ菟擊 夥煎 掘衙ж衛啊蝗棲梱? (Y/N)");
		String mode = sc.next();
		if (mode.equals("Y")) {
			// 唸薯
			boolean result = buy();
			// 唸薯縑 撩奢ц棻賊, 唸薯頂羲縑 Order 偌羹蒂 瞪歎
			if (result) {
				for (Product x : cart.keySet()) {
					int orderAmount = cart.get(x);
					Order o = new Order(x, orderAmount, x.getName());
					c.putPaymentDetail(o);
				}
				
				// 濰夥掘棲蒂 綠遴棻.
				c.clearCart();
			}
			
		} else {
			System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
			System.out.println("弛                   濰夥掘棲 謙猿                   弛");
			System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");

		}
		
	}
	
	// 4. 唸薯頂羲
	public void printPayments() {
		Customer c = cdao.select(login_id);
		ArrayList<Order> pds = c.getPaymentDetail();
		int total_pay = 0;
		System.out.println("忙式式式式式式式式式式式式式式式式式式式式 HISTORY 式式式式式式式式式式式式式式式式式式式式忖");
		System.out.println("弛鼻ヶ貲 \t\t 弛 輿僥偃熱 \t弛 旎擋\t\t  弛");
		System.out.println("弛----------------+--------------+-----------------弛");
		
		for (Order x : pds) {
			if (x.getTotal_pay() > 10000)
				System.out.printf("弛%s \t 弛 %d \t\t弛 %,d\t  弛\n", x.getP().getName(), x.getAmount(), x.getTotal_pay());
			else
				System.out.printf("弛%s \t 弛 %d \t\t弛 %,d\t\t  弛\n", x.getP().getName(), x.getAmount(), x.getTotal_pay());
			total_pay += x.getTotal_pay();
				
		}
		System.out.println("弛-------------------------------------------------弛");
		if (total_pay < 10000)
			System.out.printf("弛 Total                         弛 %,d\t\t  弛\n", total_pay);
		else
			System.out.printf("弛 Total                         弛 %,d\t  弛\n", total_pay);

		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");

		}
	
	
	// 5. 醱瞪
	public void charging(Scanner sc) {
		
		Customer c = cdao.select(login_id);		

		System.out.println("ㄒ式式式式式式式式式式式式式式式式式式式式 濤擋醱瞪 式式式式式式式式式式式式式式式式式式式式ㄒ");
		System.out.printf("ㄒ ⑷營 濤堅\t: %,d\n",c.getAsset());
		System.out.print("ㄒ 醱瞪 旎擋\t: ");
		int charge = sc.nextInt();

		
		System.out.printf("\nㄒ 醱瞪 諫猿 \nㄒ 醱瞪 �� 濤堅\t: %,d\n", c.getAsset() + charge);
		c.setAsset(c.getAsset() + charge);
	}

	/***
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 *                       婦葬濠 �飛�
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 */
	public void productListManage(Scanner sc) {
		
		// 1.蛔煙 2.熱薑 3.餉薯 4.菴煎
		String st = "1.蛔煙 2.熱薑 3.餉薯 4.菴煎";
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
				// product 蛔煙
				// 蛔煙й 薯ヶ曖 薑爾 殮溘
				System.out.println("ㄒ式式式式式式式式式式式式式式式式式式式式 鼻ヶ蛔煙 式式式式式式式式式式式式式式式式式式式式ㄒ");
				System.out.print("ㄒ 鼻ヶ貲\t: ");
				name = sc.next();
				
				if (pdao.select(name) != null) {
					System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
					System.out.println("弛               檜嘐 襄營ж朝 鼻ヶ貲殮棲棻              弛");
					System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
					flag = false;
					break;
				}
				
				System.out.print("ㄒ 陛問\t: ");
				price = sc.nextInt();
				System.out.print("ㄒ 熱榆\t: ");
				amount = sc.nextInt();

				
				p = new Product(name, price, amount);
				pdao.insert(p);
				break;
			case "2":
				// product 熱薑
				// 熱薑й 薯ヶ曖 鼻ヶ貲 殮溘
				System.out.println("ㄒ式式式式式式式式式式式式式式式式式式式式 鼻ヶ熱薑 式式式式式式式式式式式式式式式式式式式式ㄒ");
				System.out.print("ㄒ 鼻ヶ貲\t: ");
				name = sc.next();
				
				p = pdao.select(name);
				
				// 鼻ヶ貲檜 襄營ж雖 彊戲賊 "熱薑"詭景 謙猿
				if (p == null) {
					System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
					System.out.println("弛               襄營ж雖 彊朝 鼻ヶ貲殮棲棻              弛");
					System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
					break;
				} else {
					
					System.out.println(name + "鼻ヶ擊 熱薑м棲棻.");
					System.out.print("ㄒ 熱薑 陛問\t: ");
					price = sc.nextInt();
					System.out.print("ㄒ 熱薑 熱榆\t: ");
					amount = sc.nextInt();
					// и廓縑 剩啖輿晦 嬪п ⑽滲�素� 餌辨
					String[] params = { name, String.valueOf(price), String.valueOf(amount) };
					
					pdao.update(p, params);
				}
				break;
				
			case "3":
				// product 餉薯
				// 餉薯й 薯ヶ曖 鼻ヶ貲 殮溘
				System.out.println("ㄒ式式式式式式式式式式式式式式式式式式式式 鼻ヶ餉薯 式式式式式式式式式式式式式式式式式式式式ㄒ");
				System.out.print("ㄒ 鼻ヶ貲\t: ");
				name = sc.next();
				
				p = pdao.select(name);
				
				if (p == null) {
					System.out.println("忙式式式式式式式式式式式式式式式式式式式 NOTICE 式式式式式式式式式式式式式式式式式式式式忖");
					System.out.println("弛               襄營ж雖 彊朝 鼻ヶ貲殮棲棻              弛");
					System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
					break;
				} else {
					System.out.printf("\"%s\" 鼻ヶ擊 餉薯ж衛啊蝗棲梱? (Y/N)", name);
					String isDelete = sc.next();
					
					if (isDelete.equals("Y")) {
						System.out.printf("\"%s\" 鼻ヶ擊 撩奢瞳戲煎 餉薯ц蝗棲棻.\n", name);
						pdao.delete(p);
					} else {
						System.out.println("餉薯蒂 鏃模м棲棻.");
					}
				}
				break;
				
			case "4":
				// 菴煎陛晦
				flag = false;
				break;
			}
		}
		
	}
	// 賅萇 嶸盪曖 唸薯頂羲 �挫�
	public void orderPrintAll() {
		ArrayList<Customer> users = cdao.selectAll();
		
		System.out.println("忙式式式式式式式式式式式式式式式式式式式 HISTORY 式式式式式式式式式式式式式式式式式式式式式忖");
		System.out.println("弛鼻ヶ檜葷 \t\t 弛 輿僥偃熱 \t弛 唸薯旎擋\t\t  弛");
		System.out.println("弛----------------+--------------+-----------------弛");
		for (Customer c : users) {
			ArrayList<Order> o = c.getPaymentDetail();
			
			if (o.isEmpty()) continue; 
			System.out.printf("弛 \"%s\" 椒曖 唸薯頂羲 \t\t\t\t  弛\n", c.getId());
			
			int total_pay = 0;
			String tab = "\t";
			for (Order x : o) {
				if (x.getP().getName().getBytes().length <= 5) {
					System.out.printf("弛 %s %s 弛 %d\t\t弛", x.getP().getName(), tab.repeat(2), x.getAmount(), x.getTotal_pay());
				} else {
					System.out.printf("弛 %s \t 弛 %d\t\t弛", x.getP().getName(), x.getAmount(), x.getTotal_pay());
				}
				if (x.getTotal_pay() < 10000)
					System.out.printf(" %,d%s  弛\n", x.getTotal_pay(), tab.repeat(2));
				else
					System.out.printf(" %,d%s  弛\n", x.getTotal_pay(), tab);					
				total_pay += x.getTotal_pay();
			}
			if (total_pay <= 10000)
				System.out.printf("弛 Total                         弛 %,d%s  弛\n", total_pay, tab.repeat(2));
			else
				System.out.printf("弛 Total                         弛 %,d%s  弛\n", total_pay, tab);
				
			total_pay = 0;
			System.out.println("弛-------------------------------------------------弛");
		}
		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");

	}
	
	// 3. 嶸盪 婦葬 晦棟
	public void userManage() {
		ArrayList<Customer> allUsers = cdao.selectAll();
		System.out.println("忙式式式式式式式式式式式式式式式式式 User Infomation 式式式式式式式式式式式式式式式忖");
		for (Customer c: allUsers) {
			if (c.getId().equals("admin")) continue;
			System.out.printf("弛  ID     :  %s\t                          弛\n", c.getId());
			System.out.printf("弛  PW     :  %s\t                          弛\n", "*".repeat(c.getPwd().length()));
			System.out.printf("弛  NAME   :  %s\t                          弛\n", c.getName());
			System.out.printf("弛  EMAIL  :  %s\t                          弛\n", c.getEmail());
			System.out.println("弛-------------------------------------------------弛");
		}
		System.out.println("戌式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式式戎");
		
	}
}
