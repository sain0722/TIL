package ShoppingMall;

import java.util.Scanner;

public class Menu {

	private Service service;

	public Menu() {
		service = new Service();
	}
	
	public void run(Scanner sc) throws InterruptedException {
		
		System.out.println("���������������������������������������� ���θ� �ʱ�ȭ�� ��������������������������������������������\n");
		
		String st = "1.�α��� 2.ȸ������ 3.������";
		
		boolean flag = true;
		int mode;
		
		while (flag) {
			System.out.println(st);
			
			String menu = sc.next();
			
			switch(menu) {
			case "1":
				mode = service.login(sc);
				if (mode == 1) {
					// ������ ȭ������ �̵�
					ManagerRun(sc);
				} else if (mode == 2) {
					// ���� ȭ������ �̵�
					userRun(sc);
				}
				break;

			case "2":
				service.join(sc);
				break;
				
			case "3":
				System.out.println("���α׷� ����");
				flag = false;
				break;
			}
		}
	}
	
	public void userRun(Scanner sc) throws InterruptedException {
		
		System.out.printf(" ������������������������������������������������\n");
		System.out.printf("     ����������������������������������������\n");
		Thread.sleep(1000);
		System.out.printf("       �������������� PLAYDATA �������������\n");
		Thread.sleep(1000);
		System.out.printf("          ��������� SHOPPING MALL ��������\n");
		Thread.sleep(1000);
		System.out.printf("             ��������   OPEN   ���������\n");
		System.out.printf("                 ������������������\n");
		System.out.printf("                    �����������\n");
		System.out.printf("                       �����\n");
		String st = "1.������Ȯ�� 2.���� 3.��ٱ��� 4.�������� 5.���� 6.�α׾ƿ�";		
		boolean flag = true;
		
		while (flag) {
			System.out.println(st);
			String menu = sc.next();
			
			switch (menu) {
			case "1":
				// ������Ȯ�� ���
				service.printMyInfo();
				break;
				
			case "2":
				// ����
				service.shopping(sc);
				break;
				
			case "3":
				// ��ٱ��� ���
				service.myCart(sc);
				break;
				
			case "4":
				// �������� ���
				service.printPayments();
				break;
				
			case "5":
				// ���� ���
				service.charging(sc);
				break;
				
			case "6":
				service.logout();
				flag = false;
				break;
			}
		}
	}
	
	public void ManagerRun(Scanner sc) {
		System.out.println("\n���������������������������������������� ���θ� �����޴� ��������������������������������������������\n");
		
		String st = "1.��ǰ��ϰ��� 2.��������Ȯ�� 3.��ü����Ȯ�� 4.�α׾ƿ�";
		boolean flag = true;
		
		while (flag) {
			System.out.println(st);
			String menu = sc.next();
			
			switch (menu) {
			case "1":
				// ��ǰ��ϰ��� ���
				// ���� �޴� - ���, ����, ����
				service.productListManage(sc);
				break;
				
			case "2":
				// ��������Ȯ�� ���
				service.orderPrintAll();
				break;
				
			case "3":
				service.userManage();
				break;
				
			case "4":
				service.logout();
				flag = false;
				break;
				
			}
		}

		
	}
	
}
