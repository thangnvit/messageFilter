import java.io.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by DEV on 10/7/2016.
 */
public class WriteToFile extends Thread {

    private final BlockingQueue<String> queueErrorMsg;
    private final Writer writer;

    public WriteToFile(BlockingQueue<String> queueErrorMsg, Writer writer) {
        this.queueErrorMsg = queueErrorMsg;
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String s = queueErrorMsg.take();
                if(s.equals("Exit")){
                    break;
                }
                writer.write(s+"\n");
            }
            writer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
