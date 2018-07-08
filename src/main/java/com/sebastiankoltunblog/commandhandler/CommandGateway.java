package com.sebastiankoltunblog.commandhandler;

import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandGateway {
    private static final Logger log = Logger.getLogger(CommandGateway.class);
    private Map<Class<? extends CommandRequest>, CommandHandler> registry;

    public CommandGateway() {
        registry = new HashMap<>();
    }

    public <R> R execute(CommandRequest<R> command) {
        CommandHandler<CommandRequest<R>, R> handler = getHandler(command);
        return handler.handle(command);
    }

    public void registerHandler(Class<? extends CommandRequest> commandClass, CommandHandler handler) {
        log.info("Put in registry " + handler);
        registry.put(commandClass, handler);
    }

    private <R> CommandHandler<CommandRequest<R>, R> getHandler(CommandRequest<R> command) {
        log.info("Find for " + command);
        log.info(Arrays.toString(registry.entrySet().toArray()));
        CommandHandler handler = registry.get(command.getClass());
        validateConfiguration(command, handler);
        return handler;
    }

    private boolean validateConfiguration(CommandRequest request, CommandHandler handler) {
        Type responseType = request.getResponseType();
        boolean valid = handler != null
                && handler.getCommandRequestType().equals(request.getClass())
                && responseType.equals(handler.getResponseType());
        if (!valid) {
            throw new IllegalArgumentException(request.getClass().getName() + " has no matching handler");
        }
        return true;
    }
}
