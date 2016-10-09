import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by DEV on 10/8/2016.
 */

public class ListFile {
    private File folder;

    public ListFile(File folder) {
        this.folder = folder;
    }

    public List<File> getListFile() {
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
