// TASK Reimplement the TreasureBox class without synchronization. Pick a proper locking class - Barrier, Semaphore, CountDownLatch, etc.
import java.util.concurrent.*

class TreasureBox {
    def countDownLatch = new CountDownLatch(3)
    
    def treasure = "golden rings"
    
    def key1=false
    def key2=false
    def key3=false
            
    def enterPrezidentKey(key) {
        if (!key1 && key == "1122") {
           key1 = true
           println "Unlocking with the president key"
           Thread.sleep(1000)
           countDownLatch.countDown()
        }
    }

    def enterPrimeMinisterKey(key) {
        if (!key2 && key == "1133") {
           key2 = true
           println "Unlocking with the prime minister key"
           Thread.sleep(1000)
            countDownLatch.countDown()
        }
    }

    def enterPoliceDirectorKey(key) {
        if (!key3 && key == "1144") {
           key3 = true        
           println "Unlocking with the police director key"
           Thread.sleep(1000)
            countDownLatch.countDown()
        }
    }
    
    def open() {
        countDownLatch.await()
       if (key1 && key2 && key3) {
           println "Opening the box and showing " + this.treasure
       } else {
           println "Box is locked"
       }
    }
}

def box = new TreasureBox()

Thread.start {
    box.open()
}

Thread.start {
    box.open()
}
Thread.start {
    box.open()
}

box.enterPoliceDirectorKey("1144")
box.enterPrezidentKey("1122")
box.enterPrimeMinisterKey("invalid")
box.enterPrimeMinisterKey("1133")