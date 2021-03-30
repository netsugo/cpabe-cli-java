package com.github.netsugo.cpabe.cli;

import picocli.CommandLine.*;
import java.util.concurrent.Callable;

@Command(name = "encrypt", mixinStandardHelpOptions = true, description = { Description.Command.encrypt })
public class Encrypt implements Callable<Integer> {
    @Option(names = { "-s", "--source" }, description = { Description.decrypted }, required = true)
    private String plain;

    @Option(names = { "-P", "--public" }, description = { Description.publicKey }, required = true)
    private String pubkey;

    @Option(names = { "-p", "--policy" }, description = { Description.policy, Description.policyExample }, required = true)
    private String policy;

    @Override
    public Integer call() {
        System.out.println(plain + " " + pubkey + " " + policy);
        return ExitCode.OK;
    }
}
