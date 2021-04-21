package com.github.netsugo.cpabe.cli;

import java.util.Base64;

public class Base64EncDec {
    public final Base64.Decoder decoder = Base64.getDecoder();
    public final Base64.Encoder encoder = Base64.getEncoder();

    public byte[] decode(String str) {
        return decoder.decode(str.replaceAll("\\s", ""));
    }

    public byte[] decode(byte[] bytes) {
        return decoder.decode(bytes);
    }

    public byte[] encode(byte[] binary) {
        return encoder.encode(binary);
    }

    public String encodeToString(byte[] binary) {
        return encoder.encodeToString(binary);
    }
}
