package cn.campusapp.rog.exception;

/**
 * Created by kris on 16/3/29.
 */
public class NoInterfaceLimitationException extends RuntimeException{
    public NoInterfaceLimitationException(){
        super("The interface generator must set subclass set or object to generate");
    }
}
