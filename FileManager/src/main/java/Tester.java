
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Tester {

    public static void main(String[] args) {
        Charset CHARSET = StandardCharsets.UTF_8;
        String LINE_SEPARATOR = "\r\n"; // System.lineSeparator();
        FileInputStream fis = null;
        try {
            File file = new File("./location.png");
            fis = new FileInputStream(file);
            byte[] data = fis.readAllBytes();
            fis.close();
            System.out.println("SRC: " + data.length);
            //
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(new BufferedInputStream(new ByteArrayInputStream(data)), CHARSET));
            StringBuffer sb = new StringBuffer();
            String temp;
            int count = 0;
            parse_loop:
            while ((temp = bfrd.readLine()) != null) {
                int len = temp.getBytes().length;
                count += len + 2;
                sb.append(temp).append(LINE_SEPARATOR);
            }
            System.out.println(count);
            //
            System.out.println("DEST: " + sb.toString().getBytes(CHARSET).length);
        } catch (Exception ex) {
        }

    }

}
