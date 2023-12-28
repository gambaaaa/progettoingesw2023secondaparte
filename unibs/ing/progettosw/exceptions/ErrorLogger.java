package unibs.ing.progettosw.exceptions;

import unibs.ing.progettosw.utilities.TXTFileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorLogger {
    private static ErrorDialog error;

    public static ErrorDialog getInstance() {
        if (error == null)
            error = new ErrorDialog();
        return error;
    }

    public void logError(String errorMessage) {
        TXTFileWriter jfw = new TXTFileWriter();
        try {
            jfw.scriviStringaSuFile(errorMessage, "logs\\errorlogs.log", true);
        } catch (IOException e) {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError("Il programma non può scrivere sul file errorlogs.log");
        }
    }
}
