package com.sebastiankoltunblog.commandhandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 Abstract command class to be implemented by end user.
 Should define {@link Response} type
 @author Sebastian Koltun
 */
public abstract class CommandRequest<Response> implements Command {
    public Type getResponseType() {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        return ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
    }
}
