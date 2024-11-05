package monitor;

class BankAccount {
    private int balance = 200;

    // Synchronized metoda wypłacająca pieniądze
    public synchronized void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName() + " chce wypłacić " + amount);

        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " realizuje wypłatę " + amount);
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " zakończył wypłatę. Nowe saldo: " + balance);
        } else {
            System.out.println("Brak wystarczających środków na koncie dla " + Thread.currentThread().getName());
        }
    }

    public int getBalance() {
        return balance;
    }
}

class WithdrawTask implements Runnable {
    private BankAccount account;
    private int amount;

    public WithdrawTask(BankAccount account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}

public class MonitorExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        // Tworzymy dwa wątki próbujące wypłacić pieniądze z tego samego konta
        Thread thread1 = new Thread(new WithdrawTask(account, 150), "Wątek 1");
        Thread thread2 = new Thread(new WithdrawTask(account, 100), "Wątek 2");

        thread1.start();
        thread2.start();
    }
}



