package unibs.ing.progettosw.exceptions;

import unibs.ing.progettosw.utilities.JSONFileWriter;
import unibs.ing.progettosw.utilities.TXTFileWriter;

import java.io.IOException;

public class ErrorLogger {
    private static ErrorDialog error;
    public static ErrorDialog getInstance(){
        if(error == null)
            error = new ErrorDialog();
        return error;
    }

    public void logError(String errorMessage) throws IOException {
        TXTFileWriter jfw = new TXTFileWriter();
        jfw.scriviStringaSuFile(errorMessage, "logs\\errorlogs.txt",true);
    }
}
