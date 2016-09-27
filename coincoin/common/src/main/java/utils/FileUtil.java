package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Thibault on 27/09/16.
 * Static util methods on files
 */
public class FileUtil {
    private FileUtil() {

    }

    public static String readFile(String uri) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(uri));
        return new String(encoded, Charset.defaultCharset());
    }
}
