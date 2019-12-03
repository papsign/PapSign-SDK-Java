package com.papsign;

public class KeyPair {
	static {
		PapSignSDK.load();
	}

	private long _ptr;

	private KeyPair(long ptr) {
    	_ptr = ptr;
	}

	private native int cpp_getHashcode(long ptr);
	private native boolean cpp_equals(long ptr, long optr);
	private native void cpp_delete(long ptr);
	private static native long cpp_generate(int keySize);
	private native byte[] cpp_getPrivate(long ptr);
	private native byte[] cpp_getPublic(long ptr);

    @Override
	public int hashCode(){
		return cpp_getHashcode(_ptr);
    }
    @Override
    public boolean equals(Object obj){
    	if(obj != null && this.getClass().equals(obj.getClass())){
    		KeyPair o = (KeyPair)obj;
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

	public static KeyPair generate(final int keySize){
		long ptr = cpp_generate(keySize);
		if(ptr != 0)
			return new KeyPair(ptr);
		return null;
	}
	
	public byte[] getPrivate(){
		return cpp_getPrivate(_ptr);
	}

	public byte[] getPublic(){
		return cpp_getPublic(_ptr);
	}
}
