package org.getspout.unchecked.api.command.annotated;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.getspout.unchecked.api.command.Command;
import org.getspout.unchecked.api.command.CommandContext;
import org.getspout.unchecked.api.command.CommandException;
import org.getspout.unchecked.api.command.CommandExecutor;
import org.getspout.unchecked.api.command.CommandSource;
import org.getspout.unchecked.api.command.WrappedCommandException;

public abstract class AnnotatedCommandExecutor implements CommandExecutor {
	private final Object instance;
	private final Method method;

	public AnnotatedCommandExecutor(Object instance, Method method) {
		this.instance = instance;
		this.method = method;
	}

	public boolean processCommand(CommandSource source, Command command, CommandContext args) throws CommandException {
		try {
			List<Object> commandArgs = new ArrayList<Object>(4);
			commandArgs.add(source);
			commandArgs.add(args);
			commandArgs.addAll(getAdditionalArgs(source, command));
			method.invoke(instance, commandArgs.toArray(new Object[commandArgs.size()]));
		} catch (IllegalAccessException e) {
			throw new WrappedCommandException(e);
		} catch (InvocationTargetException e) {
			if (e.getCause() == null) {
				throw new WrappedCommandException(e);
			} else {
				Throwable cause = e.getCause();
				if (cause instanceof CommandException) {
					throw (CommandException) cause;
				} else {
					throw new WrappedCommandException(cause);
				}
			}
		}
		return true;
	}

	public abstract List<Object> getAdditionalArgs(CommandSource source, Command command);
}
