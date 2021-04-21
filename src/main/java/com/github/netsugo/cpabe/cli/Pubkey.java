package com.github.netsugo.cpabe.cli;

import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "pubkey", mixinStandardHelpOptions = true, description = {Description.Command.pubkey})
public class Pubkey implements Callable<Integer> {
    @CommandLine.Option(names = {"-o", "--out"})
    private String pubfile;

    private OutputStream getOutputStream() throws FileNotFoundException {
        return pubfile == null
                ? System.out
                : new FileOutputStream(pubfile);
    }

    @Override
    public Integer call() throws IOException {
        var keyPairBinary = Util.readStream(System.in);
        var keyPair = new String(keyPairBinary).split("\n");
        var pubkey = keyPair[1];
        Util.writeStream(System.out, pubkey.getBytes());
        return CommandLine.ExitCode.OK;
    }
}
