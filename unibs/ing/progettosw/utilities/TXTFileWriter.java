package unibs.ing.progettosw.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TXTFileWriter {

    public void scriviStringaSuFile(String message, String path, boolean append) throws IOException {
        File filePath = new File(path);
        FileWriter fw = new FileWriter(filePath, append);
        fw.write(message);
        fw.flush();
        fw.close();
    }
}
