package com.github.netsugo.cpabe.cli;

import picocli.CommandLine.*;
import picocli.CommandLine.Command;
import java.util.concurrent.Callable;

@Command(subcommands = { Setup.class, Pubkey.class, GenerateKey.class, Encrypt.class, Decrypt.class })
public class Cli implements Callable<Integer>, IExitCodeExceptionMapper, IVersionProvider {
    public Integer call() {
        return ExitCode.OK;
    }

    public int getExitCode(Throwable exception) {
        return ExitCode.OK;
    }

    public String[] getVersion() {
        return new String[] {};
    }
}