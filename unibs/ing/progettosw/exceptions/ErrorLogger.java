package unibs.ing.progettosw.exceptions;

import unibs.ing.progettosw.utilities.TXTFileWriter;

public class ErrorLogger {
    private static ErrorLogger error;

    public static ErrorLogger getInstance() {
        if (error == null)
            error = new ErrorLogger();
        return error;
    }

    public void logError(String errorMessage) {
        TXTFileWriter jfw = new TXTFileWriter();
        jfw.scriviStringaSuFile(errorMessage, "logs\\errorlogs.log", true);
    }
}
