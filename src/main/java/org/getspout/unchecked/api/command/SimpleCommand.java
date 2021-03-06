package org.getspout.unchecked.api.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gnu.trove.procedure.TCharProcedure;
import gnu.trove.set.TCharSet;
import gnu.trove.set.hash.TCharHashSet;

import org.bukkit.util.Java15Compat;

import org.getspout.unchecked.api.util.Named;
import org.getspout.unchecked.api.util.StringUtil;

public class SimpleCommand implements Command {

	protected final Map<String, Command> children = new HashMap<String, Command>();
	protected Command parent;
	private final Named owner;
	private boolean locked;
	protected List<String> aliases = new ArrayList<String>();
	protected CommandExecutor executor;
	protected String help;
	protected String usage;
	protected final TCharSet valueFlags = new TCharHashSet();
	protected final TCharSet flags = new TCharHashSet();

	public SimpleCommand(Named owner, String... names) {
		aliases.addAll(Arrays.asList(names));
		this.owner = owner;
	}

	public Command addSubCommand(Named owner, String primaryName) {
		if (isLocked()) {
			return this;
		}
		SimpleCommand sub = new SimpleCommand(owner, primaryName);
		while (children.containsKey(primaryName)) {
			primaryName = owner.getName() + ":" + primaryName;
		}
		children.put(primaryName, sub);
		sub.parent = this;
		return sub;
	}

	public Command registerAsSub(Named owner, Command command) {
		if (isLocked()) {
			return this;
		}
		String primaryName = command.getPreferredName();
		while (children.containsKey(primaryName)) {
			primaryName = owner.getName() + ":" + primaryName;
		}
		children.put(primaryName, command);
		command.setParent(this);
		command.lock(owner);
		return command;
	}

	public Command sub(Named owner, String primaryName) {
		return addSubCommand(owner, primaryName);
	}

	public <T> Command addSubCommands(Named owner, T object, CommandRegistrationsFactory<T> factory) {
		factory.create(owner, object, this);
		return this;
	}

	public Command closeSubCommand() {
		if (parent == null) {
			throw new UnsupportedOperationException("This command has no parent");
		}
		lock(owner);
		return parent;
	}

	public Command closeSub() {
		return closeSubCommand();
	}

	public Command addAlias(String... names) {
		if (!isLocked()) {
			if (parent != null) {
				boolean changed = false;
				for (String name : names) {
					if (!parent.hasChild(name)) {
						aliases.add(name);
						changed = true;
					}
				}
				if (changed) {
					parent.updateAliases(this);
				}
			} else {
				aliases.addAll(Arrays.asList(names));
			}
		}
		return this;
	}

	public Command alias(String... names) {
		return addAlias(names);
	}

	public Command setHelp(String help) {
		if (!isLocked()) {
			this.help = help;
		}
		return this;
	}

	public Command help(String help) {
		return setHelp(help);
	}

	public Command setUsage(String usage) {
		if (!isLocked()) {
			this.usage = usage;
		}
		return this;
	}

	public Command usage(String usage) {
		return setUsage(usage);
	}

	public Command setExecutor(CommandExecutor executor) {
		if (!isLocked()) {
			this.executor = executor;
		}
		return this;
	}

	public Command executor(CommandExecutor executor) {
		return setExecutor(executor);
	}

	public Command addFlags(String flagString) {
		if (!isLocked()) {
			char[] raw = flagString.toCharArray();
			for (int i = 0; i < raw.length; ++i) {
				if (raw.length > i + 1 && raw[i + 1] == ':') {
					valueFlags.add(raw[i]);
					++i;
				}
				flags.add(raw[i]);
			}
		}
		return this;
	}

	public Command flags(String flags) {
		return addFlags(flags);
	}

	public boolean execute(CommandSource source, String[] args, int baseIndex, boolean fuzzyLookup) throws CommandException {
		if (args.length > 1 && children.size() > 0) {
			Command sub = getChild(args[0], fuzzyLookup);
			if (sub == null) {
				return false;
			}
			return sub.execute(source, args, ++baseIndex, fuzzyLookup);
		}

		if (executor == null || baseIndex >= args.length) {
			return false;
		}
		args = Java15Compat.Arrays_copyOfRange(args, baseIndex, args.length);

		CommandContext context = new CommandContext(args, valueFlags);
		if (!context.getFlags().forEach(new TCharProcedure() {

			public boolean execute(char c) {
				return flags.contains(c);
			}
		})) {
			return false;
		}

		try {
			executor.processCommand(source, this, context);
		} catch (CommandException e) {
			throw e;
		} catch (Throwable t) {
			throw new WrappedCommandException(t);
		}
		return true;
	}

	public String getPreferredName() {
		return aliases.get(0);
	}

	public Set<Command> getChildCommands() {
		return new HashSet<Command>(children.values());
	}

	public Set<String> getChildNames() {
		return new HashSet<String>(children.keySet());
	}

	public List<String> getNames() {
		return new ArrayList<String>(aliases);
	}

	public String getUsage(String[] input) {
		return help + " - " + usage;
	}

	public Command getChild(String name, boolean fuzzyLookup) {
		Command command = children.get(name);
		if (command == null && fuzzyLookup) {
			int minDistance = -1;
			for (Map.Entry<String, Command> entry : children.entrySet()) {
				int distance = StringUtil.getLevenshteinDistance(name, entry.getKey());
				if (minDistance < 0 || distance < minDistance) {
					command = entry.getValue();
					minDistance = distance;
				}
			}
			if (minDistance > 0 && minDistance < 2) {
				return command;
			} else {
				return null;
			}
		}
		return command;
	}

	public Command removeChild(Command cmd) {
		if (isLocked()) {
			return this;
		}
		Map<String, Command> removeAliases = new HashMap<String, Command>();
		for (Iterator<Command> i = children.values().iterator(); i.hasNext();) {
			Command registered = i.next();
			if (registered.equals(cmd)) {
				i.remove();
				for (String alias : cmd.getNames()) {
					Command aliasCmd = children.get(alias);
					if (cmd.equals(aliasCmd)) {
						removeAliases.put(alias, aliasCmd);
					}
				}
			}
		}
		for (Map.Entry<String, Command> entry : removeAliases.entrySet()) {
			entry.getValue().removeAlias(entry.getKey());
		}
		return this;
	}

	public Command removeChild(String name) {
		if (isLocked()) {
			return this;
		}
		Command command = getChild(name, false);
		if (command == null) {
			return this;
		}
		Map<String, Command> removeAliases = new HashMap<String, Command>();
		for (Iterator<Command> i = children.values().iterator(); i.hasNext();) {
			Command cmd = i.next();
			if (command.equals(cmd)) {
				i.remove();
				for (String alias : cmd.getNames()) {
					Command aliasCmd = children.get(alias);
					if (cmd.equals(aliasCmd)) {
						removeAliases.put(alias, aliasCmd);
					}
				}
			}
		}
		for (Map.Entry<String, Command> entry : removeAliases.entrySet()) {
			entry.getValue().removeAlias(entry.getKey());
		}
		return this;
	}

	public Command removeAlias(String name) {
		if (isLocked()) {
			return this;
		}
		aliases.remove(name);
		if (parent != null) {
			parent.updateAliases(this);
		}
		return this;
	}

	public boolean lock(Named owner) {
		if (owner == this.owner) {
			locked = true;
			return true;
		}
		return false;
	}

	public boolean unlock(Named owner) {
		if (owner == this.owner) {
			locked = false;
			return true;
		}
		return false;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean updateAliases(Command child) {
		boolean changed = false;
		List<String> names = child.getNames();
		synchronized (children) {
			for (Iterator<Map.Entry<String, Command>> i = children.entrySet().iterator(); i.hasNext();) {
				Map.Entry<String, Command> entry = i.next();
				if (entry.getValue() != child) {
					continue;
				}
				if (!names.contains(entry.getKey())) {
					i.remove();
					changed = true;
				}
			}
			for (String alias : names) {
				if (!children.containsKey(alias)) {
					children.put(alias, child);
				}
			}
			return changed;
		}
	}

	public boolean hasChild(String name) {
		return children.containsKey(name);
	}

	public Command setParent(Command parent) {
		if (this.parent == null) {
			this.parent = parent;
			parent.updateAliases(this);
		}
		return this;
	}
}
