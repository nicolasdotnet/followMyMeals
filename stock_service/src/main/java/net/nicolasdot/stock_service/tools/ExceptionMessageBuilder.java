package net.nicolasdot.stock_service.tools;

import net.nicolasdot.stock_service.entity.ExceptionMessage;

/**
 *
 * @author nicolasdotnet
 */
public class ExceptionMessageBuilder {

    private String message;
    private String className;
    private String path;
    private String date;

    public ExceptionMessageBuilder() {
    }

    public ExceptionMessageBuilder(ExceptionMessage exceptionMessage) {
        this.message = exceptionMessage.getMessage();
        this.className = exceptionMessage.getClassName();
        this.path = exceptionMessage.getPath();
        this.date = exceptionMessage.getDate();
    }

    public ExceptionMessageBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ExceptionMessageBuilder className(String className) {
        this.className = className;
        return this;
    }

    public ExceptionMessageBuilder path(String path) {
        this.path = path;
        return this;
    }

    public ExceptionMessageBuilder date(String date) {
        this.date = date;
        return this;
    }

    public ExceptionMessage build() {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setMessage(message);
        exceptionMessage.setClassName(className);
        exceptionMessage.setPath(path);
        exceptionMessage.setDate(date);
        return exceptionMessage;
    }

}
