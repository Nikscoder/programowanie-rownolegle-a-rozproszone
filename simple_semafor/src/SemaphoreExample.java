import java.util.concurrent.Semaphore;

// That is an example PRINTER program to explain
// how two users can use the same print machine at the same time

class Printer {
    private final Semaphore semaphore;
    // constructor with slots attribute to create a semaphore with 2 limits
    // slots == MAX_AVAILABLE(from orcale doc)
    public Printer(int slots) {
        this.semaphore = new Semaphore(slots);
    }

    //main method
    public void printDocument(String documentName){
        try {
            semaphore.acquire(); // receive the access to the resource
            System.out.println(Thread.currentThread().getName() + " drukuje: " + documentName);
            Thread.sleep(1000); // simulation of time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " zakonczyl drukowanie: " + documentName);
            semaphore.release(); // release resource
        }
    }
}

class Worker implements Runnable {
    private final Printer printer;
    private final String documentName;

    public Worker(Printer printer, String documentName){
        this.printer = printer;
        this.documentName = documentName;
    }

    @Override
    public void run() {
        printer.printDocument(documentName);
    }
}




public class SemaphoreExample {
    public static void main(String[] args) {
        // only 2 threads can use print machine at the same time
        Printer printer = new Printer(2);

        // create and run threads
        Thread worker1 = new Thread(new Worker(printer, "Document_1"), "Pracownik_1");
        Thread worker2 = new Thread(new Worker(printer, "Document_2"), "Pracownik_2");
        Thread worker3 = new Thread(new Worker(printer, "Document_3"), "Pracownik_3");
        Thread worker4 = new Thread(new Worker(printer, "Document_4"), "Pracownik_4");

        worker1.start();
        worker2.start();
        worker3.start();
        worker4.start();
    }
}