package ch05.ex05_02;

import static org.junit.Assert.*;

import org.junit.Test;

import ch05.ex05_02.BankAccount.History;

public class BankAccountTest {

	@Test
	public void historyがActionインスタンスを10個保存できてきているかの確認() {
		BankAccount bankAccount = new BankAccount();
		for (int i = 0; i < 10; i++) {
			bankAccount.deposit(i + 1);
		}
		
		History history = bankAccount.history();
		assertEquals(10, history.size());
	}
	
	@Test
	public void historyがActionインスタンスを10個以上保存していないことの確認() {
		BankAccount bankAccount = new BankAccount();
		for (int i = 0; i < 100; i++) {
			bankAccount.deposit(i + 1);
		}
		
		History history = bankAccount.history();
		assertEquals(10, history.size());
	}
	
	@Test
	public void historyのnextが次の要素を持たない場合nullを返すことの確認() {
		BankAccount bankAccount = new BankAccount();
		for (int i = 0; i < 1; i++) {
			bankAccount.deposit(i + 1);
		}
		
		History history = bankAccount.history();
		history.next();
		assertNull(history.next());
	}
	
}
