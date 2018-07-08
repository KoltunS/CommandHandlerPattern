package com.sebastiankoltunblog.commandhandler;

import org.apache.log4j.Logger;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
/**
 CDI producer for {@link CommandGateway}
 Injects all instances of {@link Handler} and {@link Command} class implementations
 Also verifies that each command has it's handler class
 @author Sebastian Koltun
 */
public class CommandGatewayProducer {
    private static final Logger log = Logger.getLogger(CommandGatewayProducer.class);
    @Inject
    private Instance<Command> commands;
    @Inject
    private Instance<Handler> handlers;
    @Inject
    private CommandGateway gateway;

    @Produces
    @Gateway
    public CommandGateway produce() {
        commands.forEach(log::debug);
        handlers.forEach(log::debug);
        commands.forEach(this::addToRegistry);
        log.debug(gateway);
        return gateway;
    }

    private void addToRegistry(Command command) {
        CommandHandler handler = findHandler(command);
        gateway.registerHandler(((CommandRequest) command).getClass(), handler);
    }

    private CommandHandler findHandler(Command command) {
        for (Handler handler1 : handlers) {
            CommandHandler handler = (CommandHandler) handler1;
            if (matches(handler, command)) {
                log.debug(handler);
                return handler;
            }
        }
        throw new IllegalStateException("Could not find handler for " + command.getClass().getName());
    }

    private boolean matches(CommandHandler handler, Command command) {
        return handler.getCommandRequestType().equals(command.getClass()) &&
                handler.getResponseType().equals(((CommandRequest) command).getResponseType());
    }

}
