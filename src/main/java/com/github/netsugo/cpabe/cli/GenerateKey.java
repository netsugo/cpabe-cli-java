package com.github.netsugo.cpabe.cli;

import picocli.CommandLine.*;
import java.util.concurrent.Callable;

@Command(name = "keygen", mixinStandardHelpOptions = true, description = { Description.Command.keygen })
public class GenerateKey implements Callable<Integer> {
    @Option(names = { "--public" }, required = true, description = { Description.publicKey })
    private String pubKey;

    @Option(names = { "--master" }, required = true, description = { Description.master })
    private String master;

    @Option(names = { "--attr" }, required = true, description = { Description.attr })
    private String attribute;

    @Override
    public Integer call() {
        System.out.println(pubKey + " " + master + " " + attribute);
        return ExitCode.OK;
    }
}