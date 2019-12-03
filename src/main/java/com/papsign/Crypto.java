package com.papsign;

public class Crypto {

	public static native byte[] encrypt(byte[] key, byte[] data);
	public static native byte[] decrypt(byte[] key, byte[] data);
	public static native int getBlockSizeFromKey(byte[] key);

}
