import java.util.concurrent.BlockingQueue;

/**
 * Created by DEV on 10/6/2016.
 */
public class HandleMesseage extends Thread {

        private final BlockingQueue<String> queueRead;
        private final BlockingQueue<String> queueWrite;

        public HandleMesseage(BlockingQueue<String> queueRead, BlockingQueue<String> queueWrite) {
            this.queueRead = queueRead;
            this.queueWrite = queueWrite;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String s = queueRead.take();
                    if (s.equals("Exit")) {
                        queueRead.put("Exit");
                        queueWrite.put("Exit");
                        break;
                    }
                    if(!(s.startsWith("84") || s.startsWith("084")) || s.contains("fuck")){
                        queueWrite.put(s);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
