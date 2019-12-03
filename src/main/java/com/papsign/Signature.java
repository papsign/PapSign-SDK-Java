package com.papsign;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Signature {
	static {
		PapSignSDK.load();
	}

	private long _ptr;

	private Signature(long ptr) {
    	_ptr = ptr;
	}

	private native int[] cpp_getImage(long ptr, int module);
	private native int cpp_getHashcode(long ptr);
	private native boolean cpp_equals(long ptr, long optr);
	private native void cpp_delete(long ptr);
	private static native long cpp_decode(byte[] data);
	private static native long cpp_decode(int[] rgbImage);
	private static native long cpp_decode(byte[] yuvData, int width, int height);
	private static native byte[] cpp_encodeToArray(Map<String, Object> infos, byte[] signerCert, byte[] key);
	private static native int[] cpp_encodeToRGBImage(Map<String, Object> infos, byte[] signerCert, byte[] key, int module);
	private static native int cpp_getFreeSpace(Map<String, Object> infos, byte[] signerCert);
	private native Map<String, Object> cpp_getContent(long ptr, Map<String, Object> base);
	private native long cpp_getDate(long ptr);
	private native boolean cpp_isValid(long ptr, boolean testmode);
	private native long cpp_getCertificate(long ptr);

    @Override
	public int hashCode(){
		return cpp_getHashcode(_ptr);
    }
    @Override
    public boolean equals(Object obj){
    	if(obj != null && this.getClass().equals(obj.getClass())){
    		Signature o = (Signature)obj;
    		return cpp_equals(_ptr, o._ptr);
    	}
    	return false;
    }
    @Override
    protected void finalize() throws Throwable{
		long ptr = _ptr;
		_ptr = 0;
		cpp_delete(ptr);
	}

	public static Signature decode(byte[] data){
		long ptr = cpp_decode(data);
		if(ptr != 0)
			return new Signature(ptr);
		return null;
	}

	public static Signature decode(int[] rgbPixels, int width, int height){
		Signature ret = null;
		if(rgbPixels != null){
	    	int[] rgbImage = new int[rgbPixels.length + 2];
	    	rgbImage[0] = width;
	    	rgbImage[1] = height;
	    	System.arraycopy(rgbPixels, 0, rgbImage, 2, rgbPixels.length);
			long ptr = cpp_decode(rgbImage);
			if(ptr != 0)
				ret = new Signature(ptr);
		}
		return ret;
	}

	public static Signature decode(byte[] luma, int width, int height){
		Signature ret = null;
		long ptr = cpp_decode(luma, width, height);
		if(ptr != 0)
			ret = new Signature(ptr);
		return ret;
	}

	public static Signature decode(Object image){
		if(image != null){
			try{
				if(image instanceof android.graphics.Bitmap){
				    int w = ((android.graphics.Bitmap)image).getWidth();
				    int h = ((android.graphics.Bitmap)image).getHeight();
				    int[] img = new int[w*h];
				    ((android.graphics.Bitmap)image).getPixels(img, 0, w, 0, 0, w, h);
				    return decode(img, w, h);
				}
			}catch(Throwable e){
			}
			try{
				if(image instanceof java.awt.image.BufferedImage){
				    int w = ((java.awt.image.BufferedImage)image).getWidth();
				    int h = ((java.awt.image.BufferedImage)image).getHeight();
				    int[] img = ((java.awt.image.BufferedImage)image).getRGB(0, 0, w, h, null, 0, w);
				    return decode(img, w, h);
				}
			}catch(Throwable e){
			}
		}
		return null;
	}

	public static <T> T encode(final Map<String, Object> content, final byte[] signerCert, final byte[] signerPrivateKey, Class<T> format){
		return encode(content, signerCert, signerPrivateKey, format, 9);
	}
	@SuppressWarnings("unchecked")
	public static <T> T encode(final Map<String, Object> content, final byte[] signerCert, final byte[] signerPrivateKey, Class<T> format, int module){
		if(format == byte[].class){
			return (T) cpp_encodeToArray(content, signerCert, signerPrivateKey);
		}
		try{
			if(format == java.awt.image.BufferedImage.class){
				int[] rgbImage = cpp_encodeToRGBImage(content, signerCert, signerPrivateKey, module);
				if(rgbImage != null && rgbImage.length >= 2){
				    int w = rgbImage[0];
				    int h = rgbImage[1];
				    if(rgbImage.length >= ((w*h) + 2)){
					    int[] img = new int[w*h];
				    	System.arraycopy(rgbImage, 2, img, 0, img.length);
			    		java.awt.image.BufferedImage bI = new java.awt.image.BufferedImage(w,h,java.awt.image.BufferedImage.TYPE_INT_RGB);
			    		bI.setRGB(0, 0, w, h, img, 0, w);
			    		return (T) bI;
				    }
				}
			}
		}catch(Throwable e){
		}
		try{
			if(format == android.graphics.Bitmap.class){
				int[] rgbImage = cpp_encodeToRGBImage(content, signerCert, signerPrivateKey, module);
				if(rgbImage != null && rgbImage.length >= 2){
				    int w = rgbImage[0];
				    int h = rgbImage[1];
				    if(rgbImage.length >= ((w*h) + 2)){
					    int[] img = new int[w*h];
				    	System.arraycopy(rgbImage, 2, img, 0, img.length);
				    	return (T)android.graphics.Bitmap.createBitmap(img, w, h, android.graphics.Bitmap.Config.ARGB_8888);
				    }
				}
			}
		}catch(Throwable e){
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T getImage(Class<T> format, int module){
		try{
			if(format == java.awt.image.BufferedImage.class){
				int[] rgbImage = cpp_getImage(_ptr, module);
				if(rgbImage != null && rgbImage.length >= 2){
				    int w = rgbImage[0];
				    int h = rgbImage[1];
				    if(rgbImage.length >= ((w*h) + 2)){
					    int[] img = new int[w*h];
				    	System.arraycopy(rgbImage, 2, img, 0, img.length);
			    		java.awt.image.BufferedImage bI = new java.awt.image.BufferedImage(w,h,java.awt.image.BufferedImage.TYPE_INT_RGB);
			    		bI.setRGB(0, 0, w, h, img, 0, w);
			    		return (T) bI;
				    }
				}
			}
		}catch(Throwable e){
		}
		try{
			if(format == android.graphics.Bitmap.class){
				int[] rgbImage = cpp_getImage(_ptr, module);
				if(rgbImage != null && rgbImage.length >= 2){
				    int w = rgbImage[0];
				    int h = rgbImage[1];
				    if(rgbImage.length >= ((w*h) + 2)){
					    int[] img = new int[w*h];
				    	System.arraycopy(rgbImage, 2, img, 0, img.length);
				    	return (T)android.graphics.Bitmap.createBitmap(img, w, h, android.graphics.Bitmap.Config.ARGB_8888);
				    }
				}
			}
		}catch(Throwable e){
		}
		return null;
	}
	public static native int getCapacityFromKey(int keyLength);
	
	public static int getFreeSpace(final Map<String, Object> content, final byte[] signerCert){
		return cpp_getFreeSpace(content, signerCert);
	}

	public Map<String, Object> getContent(){
		return cpp_getContent(_ptr, new LinkedHashMap<String, Object>());
	}

	public Date getDate(){
		return new Date((long)(cpp_getDate(_ptr) * 1000L));
	}

	public boolean isValid(){
		return cpp_isValid(_ptr, false);
	}

	public boolean isValid(boolean testmode){
		return cpp_isValid(_ptr, testmode);
	}

	public Certificate getCertificate(){
		long ptr = cpp_getCertificate(_ptr);
		if(ptr != 0)
			return new Certificate(ptr, true);
		return null;
	}

}
