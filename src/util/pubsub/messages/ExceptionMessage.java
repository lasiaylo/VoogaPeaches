package util.pubsub.messages;

import util.exceptions.ValueException;
import util.pubsub.PubSub;

public class ExceptionMessage extends Message {
    public enum Type {
        IOException(java.io.IOException.class),
        ValueException(util.exceptions.ValueException.class);

        private Class<? extends Exception> clazz;

        Type(Class<? extends Exception> clazz) {
            this.clazz = clazz;
        }

        static Type typeOf(Class<? extends Exception> clazz) {
            for (Type type : Type.values())
                if (type.clazz == clazz)
                    return type;

            return null;
        }
    }

    private Type type;
    private Exception e;

    public ExceptionMessage(Exception e) {
        Type type = Type.typeOf(e.getClass());
        if (type == null) {
            PubSub.getInstance().publish("EXCEPTION_MESSAGE",
                    new ExceptionMessage(new ValueException("Exception type does not exist for class " + e.getClass())));
            return;
        }

        this.type = type;
        this.e = e;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return e.getMessage();
    }

    public StackTraceElement[] getStackTrace() {
        return e.getStackTrace();
    }

    public String toString() {
        return e.toString();
    }
}
