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
                    String line = queueRead.take();
                    if (line.equals("Exit")) {
                        queueRead.put("Exit");
                        queueWrite.put("Exit");
                        break;
                    }
                    if(!(line.startsWith("84") || line.startsWith("084")) || line.contains("fuck")){
                        queueWrite.put(line);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
