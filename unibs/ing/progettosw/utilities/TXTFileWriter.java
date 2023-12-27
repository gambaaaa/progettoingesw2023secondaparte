package unibs.ing.progettosw.utilities;

import unibs.ing.progettosw.exceptions.ErrorDialog;
import unibs.ing.progettosw.exceptions.ErrorLogger;

import java.io.*;

public class TXTFileWriter {
    public void scriviGiornoSuFile(int giorniPassati) {
        File giornoFile = new File("initFiles\\giorniPassati.txt");
        try {
            FileWriter fw = new FileWriter(giornoFile);
            fw.write(String.valueOf(giorniPassati));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError("Errore nella scrittura del file.");
            ErrorLogger.getInstance().logError(sWriter.toString());
        }
    }

    public void scriviStringaSuFile(String message, String path, boolean append) throws IOException {
        File filePath = new File(path);
        FileWriter fw = new FileWriter(filePath, append);
        fw.write(message);
        fw.flush();
        fw.close();
    }
}
