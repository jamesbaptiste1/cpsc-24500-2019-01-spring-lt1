import java.util.ArrayList;

class OneSecondSleeperR implements Runnable {
    private int sleepNumber;

    OneSecondSleeperR(int sleepNumberIn) {
        sleepNumber = sleepNumberIn;
    }
    
    public void Sleep() {
        System.out.println(sleepNumber + " - Going to sleep");

        try {
            Thread.sleep(1000); // Sleep for one second.
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        System.out.println(" ... " + sleepNumber + " - Done sleeping.");
    }

    public void run() {
        Sleep();
    }
}

public class SleepFastR {
    public static void main(String[] args) {
        System.out.println("Starting SleepFast...");

        // Sleep x2
        System.out.println("\nNon-threaded sleep (x2):");
        long start = System.currentTimeMillis();
        OneSecondSleeperR sleeper0 = new OneSecondSleeperR(0);
        OneSecondSleeperR sleeper1 = new OneSecondSleeperR(1);

        sleeper0.Sleep();
        sleeper1.Sleep();
        System.out.println("Elapsed time = "+(System.currentTimeMillis()-start));

        // Threaded "implements Runnable" Sleep x2
        System.out.println("\nThreaded \"implements Runnable\" sleep (x2):");
        start = System.currentTimeMillis();
        Thread t0 = new Thread(sleeper0);
        Thread t1 = new Thread(sleeper1);

        t0.start();
        t1.start();
        try {
            t0.join();
            t1.join();

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        System.out.println("Elapsed time = "+(System.currentTimeMillis()-start));

        // Sleeping ArrayList x10
        System.out.println("\nNon-threaded ArrayList sleep (x10):");
        ArrayList<OneSecondSleeperR> sleeperList = new ArrayList<OneSecondSleeperR>();

        for (int i=2; i<12; i++) {
            sleeperList.add(new OneSecondSleeperR(i));
        }

        start = System.currentTimeMillis();
        for (OneSecondSleeperR s: sleeperList) {
            s.Sleep();
        }
        System.out.println("Elapsed time = "+(System.currentTimeMillis()-start));
        
        // Threaded "implements Runnable" Sleep x10
        System.out.println("\nThreaded \"implements Runnable\" ArrayList sleep (x10):");
        start = System.currentTimeMillis();

        // Wrap each OneSecondSleeperR in a Thread object and add the Thread object to threadList.
        ArrayList<Thread> threadList = new ArrayList<Thread>();
        for (OneSecondSleeperR s: sleeperList) {
            threadList.add(new Thread(s));
        }

        for (Thread t: threadList) {
            t.start();
        }

        try {
            for (Thread t: threadList) {
                t.join();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        System.out.println("Elapsed time = "+(System.currentTimeMillis()-start));
    }
}