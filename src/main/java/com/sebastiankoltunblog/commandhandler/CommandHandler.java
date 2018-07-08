package com.sebastiankoltunblog.commandhandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 Abstract handler class to be implemented by end user.
 Gets {@link CommandRequest} type and {@link Response} type

 {@link CommandRequest} should be {@link com.sebastiankoltunblog.commandhandler.CommandRequest}
 {@link Response} can be any Java object
 @author Sebastian Koltun
 */
public abstract class CommandHandler<CommandRequest, Response> implements Handler {

    /**
     * Method called on invokers CommandGateway execute
     * @param commandRequest {@link Command} passed by invoker
     * @return {@link Response} value
     */
    public abstract Response handle(CommandRequest commandRequest);

    /**
     * @return {@link CommandRequest} type of this object
     */
    public Type getCommandRequestType() {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        return ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
    }

    /**
     * @return {@link Response} type of this object
     */
    public Type getResponseType() {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        return ((ParameterizedType) mySuperclass).getActualTypeArguments()[1];
    }

    @Override
    public String toString() {
        return this.getClass() + "<" +
                getCommandRequestType().getTypeName() + "," +
                getResponseType() + ">";
    }
}
