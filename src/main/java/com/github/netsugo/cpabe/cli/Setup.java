package com.github.netsugo.cpabe.cli;

import picocli.CommandLine.*;
import java.util.concurrent.Callable;

@Command(name = "setup", mixinStandardHelpOptions = true, description = { Description.Command.setup })
public class Setup implements Callable<Integer> {
    @Option(names = { "-P", "--public" }, required = true, description = { "target of " + Description.publicKey })
    private String pubkey;

    @Option(names = { "-m", "--master" }, required = true, description = { "target of" + Description.master })
    private String privkey;

    @Override
    public Integer call() {
        System.out.println(pubkey + " " + privkey);
        return ExitCode.OK;
    }
}