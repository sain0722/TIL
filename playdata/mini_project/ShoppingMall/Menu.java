package ShoppingMall;

import java.util.Scanner;

public class Menu {

	private Service service;

	public Menu() {
		service = new Service();
	}
	
	public void run(Scanner sc) throws InterruptedException {
		
		System.out.println("＃─────────────────── 쇼핑몰 초기화면 ─────────────────────＃\n");
		
		String st = "1.로그인 2.회원가입 3.나가기";
		
		boolean flag = true;
		int mode;
		
		while (flag) {
			System.out.println(st);
			
			String menu = sc.next();
			
			switch(menu) {
			case "1":
				mode = service.login(sc);
				if (mode == 1) {
					// 관리자 화면으로 이동
					ManagerRun(sc);
				} else if (mode == 2) {
					// 유저 화면으로 이동
					userRun(sc);
				}
				break;

			case "2":
				service.join(sc);
				break;
				
			case "3":
				System.out.println("프로그램 종료");
				flag = false;
				break;
			}
		}
	}
	
	public void userRun(Scanner sc) throws InterruptedException {
		
		System.out.printf(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		System.out.printf("     ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		Thread.sleep(1000);
		System.out.printf("       ■■■■■■■■■■■■■ PLAYDATA ■■■■■■■■■■■■\n");
		Thread.sleep(1000);
		System.out.printf("          ■■■■■■■■ SHOPPING MALL ■■■■■■■\n");
		Thread.sleep(1000);
		System.out.printf("             ■■■■■■■   OPEN   ■■■■■■■■\n");
		System.out.printf("                 ■■■■■■■■■■■■■■■■■\n");
		System.out.printf("                    ■■■■■■■■■■\n");
		System.out.printf("                       ■■■■\n");
		String st = "1.내정보확인 2.쇼핑 3.장바구니 4.결제내역 5.충전 6.로그아웃";		
		boolean flag = true;
		
		while (flag) {
			System.out.println(st);
			String menu = sc.next();
			
			switch (menu) {
			case "1":
				// 내정보확인 기능
				service.printMyInfo();
				break;
				
			case "2":
				// 쇼핑
				service.shopping(sc);
				break;
				
			case "3":
				// 장바구니 기능
				service.myCart(sc);
				break;
				
			case "4":
				// 결제내역 기능
				service.printPayments();
				break;
				
			case "5":
				// 충전 기능
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
		System.out.println("\n＃─────────────────── 쇼핑몰 관리메뉴 ─────────────────────＃\n");
		
		String st = "1.상품목록관리 2.결제내역확인 3.전체유저확인 4.로그아웃";
		boolean flag = true;
		
		while (flag) {
			System.out.println(st);
			String menu = sc.next();
			
			switch (menu) {
			case "1":
				// 상품목록관리 기능
				// 하위 메뉴 - 등록, 수정, 삭제
				service.productListManage(sc);
				break;
				
			case "2":
				// 결제내역확인 기능
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
