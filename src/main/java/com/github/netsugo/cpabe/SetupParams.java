package com.github.netsugo.cpabe;

public class SetupParams {
    public final byte[] master;
    public final byte[] pubkey;

    public SetupParams(byte[] master, byte[] pubkey) {
        this.master = master;
        this.pubkey = pubkey;
    }
}
