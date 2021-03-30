package com.github.netsugo.cpabe.cli;

import picocli.CommandLine.*;
import java.util.concurrent.Callable;

@Command(name = "decrypt", mixinStandardHelpOptions = true, description = { Description.Command.decrypt })
public class Decrypt implements Callable<Integer> {
    @Option(names = { "-s", "--source" }, required = true, description = { Description.encrypted })
    private String encrypted;

    @Option(names = { "-P", "--public" }, required = true, description = { Description.publicKey })
    private String pubkey;

    @Option(names = { "-p", "--private" }, required = true, description = { Description.privateKey })
    private String privkey;

    @Override
    public Integer call() {
        System.out.println(encrypted + " " + pubkey + " " + privkey);
        return ExitCode.OK;
    }
}