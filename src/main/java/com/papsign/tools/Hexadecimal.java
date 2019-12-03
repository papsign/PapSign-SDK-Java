package com.papsign.tools;


/**
 * A utility class for Hexadecimal data
 * 
 */

public final class Hexadecimal {
	private final static char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * Returns the byte array as hexadecimal string presentation
	 * 
	 * @param  src the raw byte array
	 * @return  the hexadecimal string presentation
	 */
	public static String fromBytes(byte[] src) {
		if(src!=null){
			char[] hexChars = new char[src.length * 2];
			int v;
			for (int j = 0; j < src.length; j++) {
				v = src[j] & 0xFF;
				hexChars[j * 2] = hexArray[v >>> 4];
				hexChars[j * 2 + 1] = hexArray[v & 0x0F];
			}
			return new String(hexChars);
		}
		return null;
	}

	/**
	 * Returns the byte as hexadecimal string presentation
	 * 
	 * @param  src the byte
	 * @return  the hexadecimal string presentation
	 */
	public static String fromByte(byte src) {
		char[] hexChars = new char[2];
		int v = src & 0xFF;
		hexChars[0] = hexArray[v >>> 4];
		hexChars[1] = hexArray[v & 0x0F];
		return new String(hexChars);
	}
	
	
	/**
	 * Returns the hexadecimal string as byte array
	 * 
	 * @param  src the hexadecimal string presentation
	 * @return  the raw byte array
	 */
	public static byte[] toBytes(String src) {
		if(src!=null){
			int len = src.length();
			byte[] data = new byte[len / 2];
			for (int i = 0; i < len; i += 2) {
				data[i / 2] = (byte) ((Character.digit(src.charAt(i), 16) << 4) + Character
						.digit(src.charAt(i + 1), 16));
			}
			return data;
		}
		return null;
	}
	
	
	/**
	 * Returns the hexadecimal string (2 characters used) as byte
	 * 
	 * @throws NumberFormatException  if the String does not contain a parsable byte
	 * @param  src the hexadecimal string presentation
	 * @param  firstCharacterIndex the first hexadecimal character
	 * @return  the byte
	 */
	public static byte toByte(String src, int firstCharacterIndex) throws NumberFormatException{
		return (byte) Integer.parseInt(src.substring(firstCharacterIndex, firstCharacterIndex + 2), 16);
	}


}
