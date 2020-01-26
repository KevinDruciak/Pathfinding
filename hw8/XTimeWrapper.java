package hw8;

import java.io.IOException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * <p>Wrapper around <code>XTime</code> class to run it in an IDE without
 * the need for using the command-line.</p>
 *
 * <p>See <code>XTime.java</code> for more information on what it does.</p>
 *
 * <p>
 * To Run:<br>
 * (1) update <code>ABSOLUTE_PATH_DATA_DIRECTORY</code>,
 * <code>DATA_FILENAME</code>, and <code>DRIVER_CLASS</code> accordingly.<br>
 * (2) run the <code>main</code> method.
 * </p>
 */
public final class XTimeWrapper {
    /** Absolute path to the folder that contains the data files. */
    private static final String ABSOLUTE_PATH_DATA_DIRECTORY =
            "/Users/kevindruciak/Desktop/cs226/homework/hw8/";
    /** Name of the data file. */
    private static final String DATA_FILENAME = "data/baltimore.streets.txt/";
    /** Name of the driver class (a class with main method). */
    private static final String DRIVER_CLASS = "hw8.StreetSearcher";

    private XTimeWrapper(){

    }

    /**
     * Main method.
     * @param args See usage.
     * @throws IOException If could not open data file.
     * @throws ClassNotFoundException If could not load class.
     * @throws InvocationTargetException If could not invoke main.
     * @throws IllegalAccessException If could not invoke main.
     */
    public static void main(String[] args) throws IOException,
            ClassNotFoundException,
            InvocationTargetException, IllegalAccessException {
        File dataFile = new File(ABSOLUTE_PATH_DATA_DIRECTORY + DATA_FILENAME);
        Scanner sc = new Scanner(dataFile);

        List<String> lines = new ArrayList<>();
        lines.add(DRIVER_CLASS); // First arg must be the driver class name.
        while (sc.hasNext()) {
            lines.add(sc.next());
        }
        sc.close();

        args = lines.toArray(new String[0]);
        XTime.main(args);
    }
}
