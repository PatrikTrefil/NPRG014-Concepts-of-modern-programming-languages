class Counter {
    static long counter = 0
    static Object lock = new Object()
    Counter() {
        synchronized (lock) {
            counter++
        }
    }
}

final threads = (1..50).collect {
    Thread.start {
        new Counter()
    }
}

threads*.join()
println Counter.counter

//TASK Properly synchronize
//TASK Replace thread creation with a thread pool (e.g. using java.util.concurrent.Executors.newFixedThreadPool(8))
