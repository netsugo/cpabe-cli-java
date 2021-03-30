/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.netsugo.cpabe.cli;

import picocli.CommandLine;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static Boolean hasSubcommand(ParseResult parsed) {
        return parsed.subcommand() != null || parsed.isUsageHelpRequested() || parsed.isVersionHelpRequested();
    }

    public static void main(String[] args) {
        var cli = new Cli();
        var commandLine = new CommandLine(cli);

        try {
            var parsed = commandLine.parseArgs(args);
            if (!hasSubcommand(parsed)) {
                var list = new String[] { "Missing a command.", commandLine.getUsageMessage() };
                for (var str : list) {
                    System.err.println(str);
                }
                System.exit(ExitCode.USAGE);
            } else {
                // progress
                System.exit(commandLine.execute(args));
            }
        } catch (ParameterException ignored) {
            var errorMessage = ignored.getMessage();
            var subcommandName = args[0];
            var subcommand = commandLine.getSubcommands().get(subcommandName);
            var usage = subcommand == null ? commandLine.getUsageMessage() : subcommand.getUsageMessage();

            var list = new String[] { errorMessage, usage };
            for (var str : list) {
                System.err.println(str);
            }
            System.exit(ExitCode.USAGE);
        }
    }
}
