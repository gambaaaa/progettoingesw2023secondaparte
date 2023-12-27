package unibs.ing.progettosw.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ErrorDialog {
    private static ErrorDialog error;
    public static ErrorDialog getInstance(){
        if(error == null)
            error = new ErrorDialog();
        return error;
    }

    public void logError(String errorMessage){
        notifyMessage(errorMessage);
    }

    private void notifyMessage(String message){
        System.out.println(message);
    }
}
