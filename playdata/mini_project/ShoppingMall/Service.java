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
		
		// cdao ������ ���ÿ� ������ ������ �־��ش�.
		Customer admin = new Customer("admin", "admin", "������", "admin@naver.com");
		cdao.insert(admin);
		
		Product[] p = { new Product("�������е�", 99000, 100), 
					    new Product("���ϱ�����Ŀ��", 36600, 100), 
					    new Product("�װ�����", 72700, 100),
					    new Product("����������", 169000, 10),
					    new Product("�縻��Ʈ", 9900, 1000)
		};
		
		for (Product x : p)
			pdao.insert(x);
	}
	
	// ȸ������ ���
	public void join(Scanner sc) {
		System.out.println("������������������������������������������ ȸ������ ������������������������������������������");
		System.out.print("�� ID     : ");
		String id = sc.next();
		
		if (cdao.select(id) != null) {
			System.out.println("���������������������������������������� NOTICE ������������������������������������������");
			System.out.println("��                �̹� �����ϴ� ID�Դϴ�               ��");
			System.out.println("��������������������������������������������������������������������������������������������������");
			return;
		}
		
		System.out.print("�� PWD    : ");
		String pwd = sc.next();
		
		System.out.print("�� NAME   : ");
		String name = sc.next();
		
		System.out.print("�� EMAIL  : ");
		String email = sc.next();
		
		System.out.println("���������������������������������������� NOTICE ������������������������������������������");
		System.out.println("��                ȸ�������� �Ϸ�Ǿ����ϴ�              ��");
		System.out.println("��������������������������������������������������������������������������������������������������");
		
		Customer c = new Customer(id, pwd, name, email);
		cdao.insert(c);
	}
	
	// �α��� ���
	// 0: �α��� ����
	// 1: ������ �α���
	// 2: �� �α���
	public int login(Scanner sc) {
		if (login_id != null) {
			System.out.println("�̹� �α����� �Ǿ��ֽ��ϴ�.");
			return 0;
		}
		
		System.out.println("������������������������������������������ �α��� ������������������������������������������");
		System.out.print("�� ID     : ");
		String id = sc.next();

		System.out.print("�� PWD    : ");
		String pwd = sc.next();
		
		if (cdao.select(id) != null) {
			System.out.println("���������������������������������������� NOTICE ������������������������������������������");
			System.out.println("��                 �α����� �Ǿ����ϴ�                 ��");
			System.out.println("��������������������������������������������������������������������������������������������������");
			login_id = id;
			if (id.equals("admin")) {
				System.out.println("(admin) ������ �������� �α���");
				return 1;
			}
			return 2;

		} else {
			System.out.println("���������������������������������������� NOTICE ������������������������������������������");
			System.out.println("��            ID�� PW�� Ȯ���� �ֽñ� �ٶ��ϴ�           ��");
			System.out.println("��������������������������������������������������������������������������������������������������");
		}
		return 0;
	}
	
	// �α׾ƿ� ���
	public void logout() {
		if (login_id == null) {
			System.out.println("���������������������������������������� NOTICE ������������������������������������������");
			System.out.println("��               �α����� �Ǿ����� �ʽ��ϴ�              ��");
			System.out.println("��������������������������������������������������������������������������������������������������");
			return;
		}
		System.out.println("���������������������������������������� NOTICE ������������������������������������������");
		System.out.println("��                 �α׾ƿ� �Ǿ����ϴ�                 ��");
		System.out.println("��������������������������������������������������������������������������������������������������");
		login_id = null;
	}
	
	/***
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 *                    �� ȭ��
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 */
	// 1. ������Ȯ�� ���
	public void printMyInfo() {
		Customer c = cdao.select(login_id);
		System.out.println("������������������������������������������ My Info ������������������������������������������");
		System.out.printf("��  ID     :  %s\t                          ��\n", c.getId());
		System.out.printf("��  PW     :  %s\t                          ��\n", "*".repeat(c.getPwd().length()));
		System.out.printf("��  NAME   :  %s\t                          ��\n", c.getName());
		System.out.printf("��  EMAIL  :  %s\t\t\t  ��\n", c.getEmail());
		System.out.printf("��  BALANCE:  %,d\t                          ��\n", c.getAsset());
		System.out.println("������������������������������������������������������������������������������������������������������");
		
	}
	
	// ��ǰ��� ���
	public void printProductList() {
		System.out.println("������������������������������������ SHOPPING MALL ������������������������������������");
		System.out.println("���������������������� �����ǰ ��� �������������������ᦢ");
		System.out.println("�� ��ǰ��ȣ\t �� ��ǰ�� \t�� ���� \t\t�� ���\t  ��");
		System.out.println("��--------+--------------+---------------+---------��");
		for(Product x : pdao.selectAll()) {
			if (x.getName().getBytes().length <= 5) {
				if (x.getPrice() >= 10000) {
					System.out.printf("��%3d \t �� %s \t\t�� %,d\t�� %d\t  ��\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());					
				} else {
					System.out.printf("��%3d \t �� %s \t\t�� %,d\t\t�� %d\t  ��\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());				
				}

			} else {
				if (x.getPrice() >= 10000) {
					System.out.printf("��%3d \t �� %s \t�� %,d\t�� %d\t  ��\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());					
				} else {
					System.out.printf("��%3d \t �� %s \t�� %,d\t\t�� %d\t  ��\n", x.getNum(), x.getName(), x.getPrice(), x.getAmount());				
				}
			}
		}
		System.out.println("������������������������������������������������������������������������������������������������������");
	}
	
	// ��ٱ��Ͽ� �ֱ�
	// ��ǰ��ȣ�� �ش� ��ǰ ��ü�� ã�´�
	// �� ��ü�� ���� �α��ε� ������ ��ٱ��Ͽ� �ִ´�.
	public void putCart(int num, int amount) {
		
		Product p = pdao.selectByNum(num);
		Customer c = cdao.select(login_id);
		HashMap<Product, Integer> cart = c.getCart();
		
		// 1. �̹� ��ٱ��Ͽ� p�� �ִٸ�, ������ �߰��Ѵ�.
		// 2. ��ٱ��Ͽ� ���� p��� ��ǰ p�� amount�� ��ŭ cart�� ��´�.
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
		// īƮ�� ��� ���
		int total_pay = 0;
		int amount = 0;
		int i = 0;
		for (Product x : cart.keySet()) {
			amount = cart.get(x);
			Order o = new Order(x, cart.get(x), c.getId());
			orders.add(o);
			
			total_pay += o.getTotal_pay();
			
			amounts[i++] = x.getAmount() - amount;	// ��ǰ x�� ���� ��� - �Է¹��� ����
			x.setAmount(x.getAmount() - amount);
		}	
		// ���� �ֹ��ݾ��� �����ݾ׺��� ���ٸ�
		if (c.getAsset() < total_pay) {
			System.out.println("���������������������������������������� WARNINGS ��������������������������������������");
			System.out.println("��\t\t  �ܾ��� �����մϴ� \t\t        ��");
			if (c.getAsset() < 10000 && total_pay - c.getAsset() < 10000) {
				System.out.printf("��\t\t ���� �ܰ�: %,d \t\t        ��\n", c.getAsset());
				System.out.printf("��\t\t ���� �ݾ�: %,d \t\t        ��\n", total_pay - c.getAsset());					
			} else if (total_pay - c.getAsset() < 10000) {
				System.out.printf("��\t\t ���� �ܰ�: %,d \t        ��\n", c.getAsset());
				System.out.printf("��\t\t ���� �ݾ�: %,d \t\t        ��\n", total_pay - c.getAsset());									
				
			}
			
			else {
				System.out.printf("��\t\t ���� �ܰ�: %,d \t        ��\n", c.getAsset());
				System.out.printf("��\t\t ���� �ݾ�: %,d \t        ��\n", total_pay - c.getAsset());									
			}
			System.out.println("��������������������������������������������������������������������������������������������������");
			return false;
			
		} else {
			System.out.println("���������������������������������������� NOTICE ������������������������������������������");
			System.out.println("��                 ����ó�� �Ǿ����ϴ�                 ��");
			System.out.println("��������������������������������������������������������������������������������������������������");
			
			// orderDAO�� �ش� �ֹ� �߰�
			for (Order o : orders)
				odao.insert(o);
			
			// �ش� ��ǰ�� ��� ����
			i = 0;
			for (Product x : cart.keySet()) {
				x.setAmount(amounts[i++]);
			}
			// �ش� ���� �ܾ� ����
			c.setAsset(c.getAsset() - total_pay);
			
		}
		return true;
	}
	
	
	// 2. ����
	// 	2-1. ��ǰ��� Ȯ�� ���
	// 	2-2. ��ٱ��Ͽ� �ֱ� ���
	// 	2-3. ���û�ǰ �ٷΰ���
	public void shopping(Scanner sc) {
		
		printProductList();
		// �����ǻ� �����, 
		System.out.println("1.��ǰ���� 2.�ڷΰ���");
		String menu = sc.next();
		// ��ٱ��Ͽ� �ֱ� / �ٷΰ��� / �������
		switch (menu) {
		case "1":

			int num = 0;
			while (true) {
				System.out.println("������������������������������������������ ��ǰ���� ������������������������������������������");
				System.out.println("�� ������ ��ǰ�� ��ǰ��ȣ�� �Է��ϼ���. (-1 �Է½� �Է�����)    ��");
				System.out.print("�� ��ǰ��ȣ     : ");
				num = sc.nextInt();
				if (num < 0) break;
				System.out.print("�� ���ż���     : ");
				int amount = sc.nextInt();
				if (amount > pdao.selectByNum(num).getAmount()) {
					System.out.println("���������������������������������������� NOTICE ������������������������������������������");
					System.out.println("��                  ����� �����մϴ�                ��");
					System.out.println("��������������������������������������������������������������������������������������������������");
					continue;
				}
				System.out.println("�� " + pdao.selectByNum(num).getName() + " " + amount + "���� ��ٱ��Ͽ� ��ҽ��ϴ�.");
				putCart(num, amount);
			}
			break;
			
		case "2":
			break;
		}
		
//		System.out.println("��ٱ��Ͽ� ��� ��ǰ���� �ٷ� �����Ͻðڽ��ϱ�? (Y/N)");
//		String mode = sc.next();
//		if (mode.equals("Y")) {
//			// ����
//			buy();
//		} else {
//			System.out.println("������ �����մϴ�.");
//		}
	}
	
	// 3. ��ٱ��� Ȯ��
	public void myCart(Scanner sc) {
		Customer c = cdao.select(login_id);
		HashMap<Product, Integer> cart = c.getCart();

		System.out.println("���������������������������������������� CART ��������������������������������������������������");
		if (cart.isEmpty()) {
			System.out.println("��               ��ٱ��ϰ� ����ֽ��ϴ�.              ��  ��");
			System.out.println("������������������������������������������������������������������������������������������������������");

			return;
		}
		
		System.out.println("����ǰ�� \t\t �� �ֹ����� \t�� �ݾ�\t\t  ��");
		System.out.println("��----------------+--------------+-----------------��");
		int total_pay = 0;
		for (Product x : cart.keySet()) {
			int orderAmount = cart.get(x);
			total_pay += x.getPrice() * orderAmount;
//			System.out.println(x.getName() + "\t" + orderAmount + "��" + "\t" + x.getPrice() * orderAmount + "��");
			System.out.printf("��%s \t ��%3d�� \t\t�� %,d��\t  ��\n", x.getName(), orderAmount, x.getPrice() * orderAmount);
		
		}
		System.out.println("��-------------------------------+-----------------��");
		System.out.printf("���� �����ݾ�\t\t\t�� %,d��\t  ��\n", total_pay);
		System.out.println("������������������������������������������������������������������������������������������������������");
	
		System.out.println("��ٱ��Ͽ� ��� ��ǰ���� �ٷ� �����Ͻðڽ��ϱ�? (Y/N)");
		String mode = sc.next();
		if (mode.equals("Y")) {
			// ����
			boolean result = buy();
			// ������ �����ߴٸ�, ���������� Order ��ü�� ����
			if (result) {
				for (Product x : cart.keySet()) {
					int orderAmount = cart.get(x);
					Order o = new Order(x, orderAmount, x.getName());
					c.putPaymentDetail(o);
				}
				
				// ��ٱ��ϸ� ����.
				c.clearCart();
			}
			
		} else {
			System.out.println("���������������������������������������� NOTICE ������������������������������������������");
			System.out.println("��                   ��ٱ��� ����                   ��");
			System.out.println("��������������������������������������������������������������������������������������������������");

		}
		
	}
	
	// 4. ��������
	public void printPayments() {
		Customer c = cdao.select(login_id);
		ArrayList<Order> pds = c.getPaymentDetail();
		int total_pay = 0;
		System.out.println("������������������������������������������ HISTORY ������������������������������������������");
		System.out.println("����ǰ�� \t\t �� �ֹ����� \t�� �ݾ�\t\t  ��");
		System.out.println("��----------------+--------------+-----------------��");
		
		for (Order x : pds) {
			if (x.getTotal_pay() > 10000)
				System.out.printf("��%s \t �� %d \t\t�� %,d\t  ��\n", x.getP().getName(), x.getAmount(), x.getTotal_pay());
			else
				System.out.printf("��%s \t �� %d \t\t�� %,d\t\t  ��\n", x.getP().getName(), x.getAmount(), x.getTotal_pay());
			total_pay += x.getTotal_pay();
				
		}
		System.out.println("��-------------------------------------------------��");
		if (total_pay < 10000)
			System.out.printf("�� Total                         �� %,d\t\t  ��\n", total_pay);
		else
			System.out.printf("�� Total                         �� %,d\t  ��\n", total_pay);

		System.out.println("������������������������������������������������������������������������������������������������������");

		}
	
	
	// 5. ����
	public void charging(Scanner sc) {
		
		Customer c = cdao.select(login_id);		

		System.out.println("������������������������������������������ �ܾ����� ������������������������������������������");
		System.out.printf("�� ���� �ܰ�\t: %,d\n",c.getAsset());
		System.out.print("�� ���� �ݾ�\t: ");
		int charge = sc.nextInt();

		
		System.out.printf("\n�� ���� �Ϸ� \n�� ���� �� �ܰ�\t: %,d\n", c.getAsset() + charge);
		c.setAsset(c.getAsset() + charge);
	}

	/***
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 *                       ������ ȭ��
	 * -------------------------------------------------------
	 * -------------------------------------------------------
	 */
	public void productListManage(Scanner sc) {
		
		// 1.��� 2.���� 3.���� 4.�ڷ�
		String st = "1.��� 2.���� 3.���� 4.�ڷ�";
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
				// product ���
				// ����� ��ǰ�� ���� �Է�
				System.out.println("������������������������������������������ ��ǰ��� ������������������������������������������");
				System.out.print("�� ��ǰ��\t: ");
				name = sc.next();
				
				if (pdao.select(name) != null) {
					System.out.println("���������������������������������������� NOTICE ������������������������������������������");
					System.out.println("��               �̹� �����ϴ� ��ǰ���Դϴ�              ��");
					System.out.println("��������������������������������������������������������������������������������������������������");
					flag = false;
					break;
				}
				
				System.out.print("�� ����\t: ");
				price = sc.nextInt();
				System.out.print("�� ����\t: ");
				amount = sc.nextInt();

				
				p = new Product(name, price, amount);
				pdao.insert(p);
				break;
			case "2":
				// product ����
				// ������ ��ǰ�� ��ǰ�� �Է�
				System.out.println("������������������������������������������ ��ǰ���� ������������������������������������������");
				System.out.print("�� ��ǰ��\t: ");
				name = sc.next();
				
				p = pdao.select(name);
				
				// ��ǰ���� �������� ������ "����"�޴� ����
				if (p == null) {
					System.out.println("���������������������������������������� NOTICE ������������������������������������������");
					System.out.println("��               �������� �ʴ� ��ǰ���Դϴ�              ��");
					System.out.println("��������������������������������������������������������������������������������������������������");
					break;
				} else {
					
					System.out.println(name + "��ǰ�� �����մϴ�.");
					System.out.print("�� ���� ����\t: ");
					price = sc.nextInt();
					System.out.print("�� ���� ����\t: ");
					amount = sc.nextInt();
					// �ѹ��� �Ѱ��ֱ� ���� ����ȯ�� ���
					String[] params = { name, String.valueOf(price), String.valueOf(amount) };
					
					pdao.update(p, params);
				}
				break;
				
			case "3":
				// product ����
				// ������ ��ǰ�� ��ǰ�� �Է�
				System.out.println("������������������������������������������ ��ǰ���� ������������������������������������������");
				System.out.print("�� ��ǰ��\t: ");
				name = sc.next();
				
				p = pdao.select(name);
				
				if (p == null) {
					System.out.println("���������������������������������������� NOTICE ������������������������������������������");
					System.out.println("��               �������� �ʴ� ��ǰ���Դϴ�              ��");
					System.out.println("��������������������������������������������������������������������������������������������������");
					break;
				} else {
					System.out.printf("\"%s\" ��ǰ�� �����Ͻðڽ��ϱ�? (Y/N)", name);
					String isDelete = sc.next();
					
					if (isDelete.equals("Y")) {
						System.out.printf("\"%s\" ��ǰ�� ���������� �����߽��ϴ�.\n", name);
						pdao.delete(p);
					} else {
						System.out.println("������ ����մϴ�.");
					}
				}
				break;
				
			case "4":
				// �ڷΰ���
				flag = false;
				break;
			}
		}
		
	}
	// ��� ������ �������� Ȯ��
	public void orderPrintAll() {
		ArrayList<Customer> users = cdao.selectAll();
		
		System.out.println("���������������������������������������� HISTORY ��������������������������������������������");
		System.out.println("����ǰ�̸� \t\t �� �ֹ����� \t�� �����ݾ�\t\t  ��");
		System.out.println("��----------------+--------------+-----------------��");
		for (Customer c : users) {
			ArrayList<Order> o = c.getPaymentDetail();
			
			if (o.isEmpty()) continue; 
			System.out.printf("�� \"%s\" ���� �������� \t\t\t\t  ��\n", c.getId());
			
			int total_pay = 0;
			String tab = "\t";
			for (Order x : o) {
				if (x.getP().getName().getBytes().length <= 5) {
					System.out.printf("�� %s %s �� %d\t\t��", x.getP().getName(), tab.repeat(2), x.getAmount(), x.getTotal_pay());
				} else {
					System.out.printf("�� %s \t �� %d\t\t��", x.getP().getName(), x.getAmount(), x.getTotal_pay());
				}
				if (x.getTotal_pay() < 10000)
					System.out.printf(" %,d%s  ��\n", x.getTotal_pay(), tab.repeat(2));
				else
					System.out.printf(" %,d%s  ��\n", x.getTotal_pay(), tab);					
				total_pay += x.getTotal_pay();
			}
			if (total_pay <= 10000)
				System.out.printf("�� Total                         �� %,d%s  ��\n", total_pay, tab.repeat(2));
			else
				System.out.printf("�� Total                         �� %,d%s  ��\n", total_pay, tab);
				
			total_pay = 0;
			System.out.println("��-------------------------------------------------��");
		}
		System.out.println("������������������������������������������������������������������������������������������������������");

	}
	
	// 3. ���� ���� ���
	public void userManage() {
		ArrayList<Customer> allUsers = cdao.selectAll();
		System.out.println("������������������������������������ User Infomation ��������������������������������");
		for (Customer c: allUsers) {
			if (c.getId().equals("admin")) continue;
			System.out.printf("��  ID     :  %s\t                          ��\n", c.getId());
			System.out.printf("��  PW     :  %s\t                          ��\n", "*".repeat(c.getPwd().length()));
			System.out.printf("��  NAME   :  %s\t                          ��\n", c.getName());
			System.out.printf("��  EMAIL  :  %s\t                          ��\n", c.getEmail());
			System.out.println("��-------------------------------------------------��");
		}
		System.out.println("������������������������������������������������������������������������������������������������������");
		
	}
}
