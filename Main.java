import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by DEV on 10/5/2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        BlockingQueue<String> queueRead = new LinkedBlockingQueue<>();
        BlockingQueue<String> queueErrorMsg = new LinkedBlockingQueue<>();

        List<File> listFile = getListFile(new File("D:\\test"));
        for (File file : listFile) {
            new ReadFile(file,queueRead).start();
        }

        new HandleMesseage(queueRead,queueErrorMsg).start();

        new WriteToFile(queueErrorMsg, new FileWriter("D:\\error.txt")).start();
    }

    private static List<File> getListFile(File folder){
        if (folder.isFile()){
            return Arrays.asList(folder);
        }

        BlockingQueue<File> fileQueue = new LinkedBlockingQueue<>();
        Collections.addAll(fileQueue, folder.listFiles());
        List<File> files = new ArrayList<>();
        File file = null;
        while ((file = fileQueue.poll()) != null){
            if (file.isDirectory()){
                fileQueue.addAll(Arrays.asList(file.listFiles()));
                continue;
            }
            files.add(file);
        }
        return files;
    }
}
