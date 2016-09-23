package bookstore.laidu.org.bookstore.uitl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by laidu on 16-9-21.
 */
public class StreamUtil {
    private static StreamUtil ourInstance = new StreamUtil();

    public static StreamUtil getInstance() {
        return ourInstance;
    }

    private StreamUtil() {
    }

    public byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        while ( (n=in.read(buffer)) !=-1) {
            out.write(buffer,0,n);
        }
        return out.toByteArray();
    }

}
