package ch05.ex05_02;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

/*
 * Historyは、BankAccountインスタンスに常に関連付けされるので内部クラスとして設計すべき
 */
public class BankAccount {

	private long number;
	@SuppressWarnings("unused")
	private long balance; /* ch05.ex05_02では使用しない */
	private Action lastAct;
	private History history = new History();

	public class Action {
		private String act;
		private long amount;

		Action(String act, long amount) {
			this.act = act;
			this.amount = amount;
		}

		@Override
		public String toString() {
			return number + ": " + act + " " + amount;
		}
	}

	public final class History {
		private static final int MAX_NUMBER = 10;
		private Deque<Action> Actions = new LinkedBlockingDeque<>(MAX_NUMBER);
		private Iterator<Action> itr = Actions.iterator();

		void saveAction(Action action) {
			if (!Actions.offerLast(action)) {
				Actions.poll();
				Actions.offerLast(action);
			}
			updateIterator();
		}

		/**
		 * 呼び出す毎に保存してある履歴を古い方から順に返す。
		 * 前回の呼び出しが最新の履歴を返していた場合、nullを返す。
		 * nullを返した後の呼び出しは、最も古い履歴を返す。
		 * @return 保存してある履歴
		 */
		public Action next() {
			if (itr.hasNext()) {
				return itr.next();
			} else {
				updateIterator();
				return null;
			}
		}
		
		/**
		 * 
		 * @return 保存してある履歴の数
		 */
		public int size () {
			return Actions.size();
		}
		
		private void updateIterator(){
			itr = Actions.iterator();
		}
	}

	public void deposit(long amount) {
		balance += amount;
		lastAct = new Action("deposit", amount);
		history.saveAction(lastAct);
	}

	public void withdraw(long amount) {
		balance -= amount;
		lastAct = new Action("withdraw", amount);
		history.saveAction(lastAct);
	}

	public History history() {
		return history;
	}
}
