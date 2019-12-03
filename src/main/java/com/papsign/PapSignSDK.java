package com.papsign;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public final class PapSignSDK {
	static{
		load();
	}
	static boolean loaded = false;
	static void load() {
		final String name = "PapSignSDKjni";
//		if(loaded == false){
//			System.load("C:\\Users\\lex\\git\\PapSignSDKjni\\Release\\PapSignSDKjni.dll");
//			loaded = true;
//		}
		if(loaded == false){
			try{
				System.loadLibrary(name);
				loaded = true;
			}catch(Throwable e){
				loaded = false;
			}
		}
		if(loaded == false){
			try{
				String os = System.getProperty("os.name").toLowerCase();
				String prefix = "lib";
				String ext = null;
				if((os.indexOf("win") >= 0)){
					prefix = "";
					ext="dll";
				}else if((os.indexOf("mac") >= 0)){
					ext="dylib";
				}else if((os.indexOf("nux") >= 0)){
					ext="so";
				}
				if(ext != null){
					if ("64".equals(System.getProperty("sun.arch.data.model"))){
						loadNativeTemporary("/lib/x86_64/", prefix+name+"."+ext);
					}else{
						loadNativeTemporary("/lib/x86/", prefix+name+"."+ext);
					}
					loaded = true;
				}
			}catch(Throwable e){
				loaded = false;
			}
		}
	}

	private static void loadNativeTemporary(String jarPath, String name) throws Exception {
	    InputStream in = PapSignSDK.class.getResourceAsStream(jarPath+name);
	    byte[] buffer = new byte[1024];
	    int read = -1;
	    File temp = File.createTempFile(name, "");
	    FileOutputStream fos = new FileOutputStream(temp);

	    while((read = in.read(buffer)) != -1) {
	        fos.write(buffer, 0, read);
	    }
	    fos.close();
	    in.close();
	    System.load(temp.getAbsolutePath());
	    temp.deleteOnExit();
	}

	public static native int getVersion();
	public static native boolean isOutdatedAt(long date);
	public static native long getExpirationDate();
}
