package unibs.ing.progettosw.exceptions;

import unibs.ing.progettosw.utilities.TXTFileWriter;

public class ErrorLogger {
    private static ErrorDialog error;

    public static ErrorDialog getInstance() {
        if (error == null)
            error = new ErrorDialog();
        return error;
    }

    public void logError(String errorMessage) {
        TXTFileWriter jfw = new TXTFileWriter();
        jfw.scriviStringaSuFile(errorMessage, "logs\\errorlogs.log", true);
    }
}
