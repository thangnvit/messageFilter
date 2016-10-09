import sun.security.krb5.internal.TGSRep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by DEV on 10/7/2016.
 */
public class ReadFile extends Thread {

    private File fileInput;
    private BlockingQueue<String> queueRead;

    public ReadFile(File fileInput, BlockingQueue<String> queueRead) {
        this.fileInput = fileInput;
        this.queueRead = queueRead;
    }

    @Override
    public synchronized void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileInput));
            String line;
            while ((line = reader.readLine()) != null) {
                queueRead.put(line);
            }
            queueRead.put("Exit");
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
