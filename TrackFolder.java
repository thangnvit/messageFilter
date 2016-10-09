import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TrackFolder {
    private File trackFolder;

    public TrackFolder(File folder) {
        this.trackFolder = folder;
    }
    
    public void track(){
        List<File> listFile = new ListFile(trackFolder).getListFile();

        while (true) {
            List<File> updateOfListFile = new ListFile(trackFolder).getListFile();

            if (updateOfListFile.size() > listFile.size()) {
                for (File file : updateOfListFile) {
                    if (!listFile.contains(file)) {
                        new FileCleanUp(file).start();
                        listFile.add(file);
                        System.out.println("1 File moi !");
                    }
                }
            }
        }
    }

    private class FileCleanUp extends Thread{
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