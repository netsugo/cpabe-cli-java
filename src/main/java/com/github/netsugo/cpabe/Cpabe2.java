package com.github.netsugo.cpabe;

import co.junwei.bswabe.Bswabe;
import co.junwei.bswabe.BswabeMsk;
import co.junwei.bswabe.BswabePub;
import co.junwei.bswabe.SerializeUtils;
import co.junwei.cpabe.AESCoder;
import co.junwei.cpabe.Common;
import co.junwei.cpabe.policy.LangPolicy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public final class Cpabe2 {
    public static SetupParams setup() {
        var pub = new BswabePub();
        var msk = new BswabeMsk();
        Bswabe.setup(pub, msk);

        var publicKey = SerializeUtils.serializeBswabePub(pub);
        var masterKey = SerializeUtils.serializeBswabeMsk(msk);

        return new SetupParams(masterKey, publicKey);
    }

    public static byte[] keygen(byte[] publicKey, byte[] masterKey, String attribute) throws NoSuchAlgorithmException {
        var pub = SerializeUtils.unserializeBswabePub(publicKey);
        var msk = SerializeUtils.unserializeBswabeMsk(pub, masterKey);
        var parsedAttribute = LangPolicy.parseAttribute(attribute);
        var prv = Bswabe.keygen(pub, msk, parsedAttribute);
        return SerializeUtils.serializeBswabePrv(prv);
    }

    private static byte[] packCpabe(byte[] cphBuf, byte[] aesBuf) throws IOException {
        // store data
        // mlen(4byte:int),mbuf,cphlen(4byte),cphbuf,aeslen(4byte),aesBuf
        var mBuf = new byte[0];
        try (var stream = Common.writeCpabeData(mBuf, cphBuf, aesBuf)) {
            return stream.toByteArray();
        }
    }

    public static byte[] encrypt(byte[] publicKey, String policy, byte[] plain) throws Exception {
        var pub = SerializeUtils.unserializeBswabePub(publicKey);
        var keyCph = Bswabe.enc(pub, policy);
        var cph = keyCph.cph;
        var element = keyCph.key;
        if (cph == null) {
            throw new RuntimeException("Error happened while encrypting");
        } else {
            var cphBuf = SerializeUtils.bswabeCphSerialize(cph);
            var aesBuf = AESCoder.encrypt(element.toBytes(), plain);
            return packCpabe(cphBuf, aesBuf);
        }
    }

    private static byte[][] UnpackCpabe(byte[] packed) throws IOException {
        var inputStream = new ByteArrayInputStream(packed);
        return Common.readCpabeData(inputStream);
    }

    public static byte[] decrypt(byte[] publicKey, byte[] privateKey, byte[] encrypted) throws Exception {
        var BUF_AES = 0;
        var BUF_CPH = 1;
        var tmp = UnpackCpabe(encrypted);
        var aesBuf = tmp[BUF_AES];
        var cphBuf = tmp[BUF_CPH];
        var pub = SerializeUtils.unserializeBswabePub(publicKey);
        var cph = SerializeUtils.bswabeCphUnserialize(pub, cphBuf);
        var prv = SerializeUtils.unserializeBswabePrv(pub, privateKey);

        var beb = Bswabe.dec(pub, prv, cph);

        if (beb.b) {
            return AESCoder.decrypt(beb.e.toBytes(), aesBuf);
        } else {
            throw new RuntimeException("Decrypt error: " + beb.e.toString());
        }
    }
}