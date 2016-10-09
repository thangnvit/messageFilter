import java.io.File;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by DEV on 10/5/2016.
 */
public class Main {
    public static void main(String[] args) {
        File folder = new File("D:\\test");

        new TrackFolder(folder).track();
    }
}
