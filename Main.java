import java.io.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by DEV on 10/5/2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        File folder = new File("C:\\Program Files\\Adobe");

        List<File> listFile = new ListFile(folder).getListFile();

        while (true){
            List<File> updateOfListFile = new ListFile(folder).getListFile();

            if(updateOfListFile.size() > listFile.size()){
                for (File file : updateOfListFile) {
                    if(!listFile.contains(file)){
                       new FileCleanUp(file).start();
                        listFile.add(file);
                        System.out.println("1 File moi !");
                    }
                }
            }
        }
    }

    private static class FileCleanUp extends Thread{
        private File path;

        public FileCleanUp(File path) {
            this.path = path;
        }

        public void run() {
            BlockingQueue<String> queueRead = new LinkedBlockingQueue<>();
            BlockingQueue<String> queueErrorMsg = new LinkedBlockingQueue<>();

            List<File> listFile = new ListFile(path).getListFile();
            for (File file : listFile) {
                new ReadFile(file,queueRead).start();
            }

            new HandleMesseage(queueRead,queueErrorMsg).start();

            try {
                new WriteToFile(queueErrorMsg, new FileWriter("D:\\error.txt",true)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
