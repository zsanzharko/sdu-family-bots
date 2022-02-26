package kz.sdu.bot.exception;


public class NotFoundException extends BaseException{
    public NotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
