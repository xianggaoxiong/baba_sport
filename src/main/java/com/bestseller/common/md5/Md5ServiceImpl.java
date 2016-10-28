package com.bestseller.common.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Md5ServiceImpl implements Md5Service {
	
	public String md5Encrypt(String password){
		String algorithm="md5";
		MessageDigest instance=null;
		try {
			instance=MessageDigest.getInstance(algorithm);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] digest = instance.digest(password.getBytes());
		char[] encodeHex = Hex.encodeHex(digest);
		return new String(encodeHex);
	}
}
