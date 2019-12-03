package com.papsign;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Certificate {
	static {
		PapSignSDK.load();
	}

	private long _ptr;
	private final boolean _unmanaged;

	private Certificate(long ptr) {
		_ptr = ptr;
		_unmanaged = false;
	}
	protected Certificate(long ptr, boolean unmanaged) {
		_ptr = ptr;
		_unmanaged = unmanaged;
	}

	private native int cpp_getHashcode(long ptr);
	private native boolean cpp_equals(long ptr, long optr);
	private native void cpp_delete(long ptr);
	private static native long cpp_decode(byte[] data);
	private native boolean cpp_isTest(long ptr);
	private native int cpp_getType(long ptr);
	private native byte[] cpp_getUid(long ptr);
	private native int cpp_getCountryCode(long ptr);
	private native char[] cpp_getPropertyName(long ptr);
	private native String cpp_getPropertyUtf8(long ptr, char name);
	private native byte[] cpp_getPropertyRaw(long ptr, char name);
	private native byte[] cpp_getKey(long ptr);
	private native int cpp_getKeySize(long ptr);
	private native long cpp_getDate(long ptr);
	private native int cpp_getValidity(long ptr);
	private native boolean cpp_isPrivateMatch(long ptr, byte[] privateKey);
	private native boolean cpp_isValidAt(long ptr, long datetime, boolean testmode);

	public enum Property {
		Email('E'),
		IP_Address('d'),
		Personal_Name('N'),
		Personal_Id('P'),
		Company_Name('C'),
		Company_Id('I'),
		Company_Type('T'),
		Content_Hash('h'),
		Address('A');

		private final char value;

		Property(char value){
			this.value = value;
		}
		public char getValue(){
			return value;
		}
		public static Property[] getName(char[] name){
			Property[] ret = null;
			if(name != null){
				int size = 0;
				for(int i=0; i<name.length;i++){
					if(get(name[i]) != null){
						size++;
					}
				}
				int x = 0;
				ret = new Property[size];
				for(int i=0; i<name.length;i++){
					Property p = get(name[i]);
					if(p != null){
						ret[x++] = p;
					}
				}
			}
			return ret;
		}

		public static Property get(char name) {
			for (Property l : Property.values()) {
				if (l.value == name) return l;
			}
			return null;
		}
	}

	public enum Type {
		BASIC,
		STANDARD,
		ADVANCED;
	}

    @Override
	public int hashCode(){
		return cpp_getHashcode(_ptr);
    }
    @Override
    public boolean equals(Object obj){
    	if(obj != null && this.getClass().equals(obj.getClass())){
    		Certificate o = (Certificate)obj;
    		return cpp_equals(_ptr, o._ptr);
    	}
    	return false;
    }
    @Override
    protected void finalize() throws Throwable{
		long ptr = _ptr;
		_ptr = 0;
		if(_unmanaged == false)
			cpp_delete(ptr);
	}

	public static Certificate decode(final byte[] data){
		long ptr = cpp_decode(data);
		if(ptr != 0)
			return new Certificate(ptr);
		return null;
	}

	public boolean isTest(){
		return cpp_isTest(_ptr);
	}

	public Type getType(){
		Type ret = Type.BASIC;
		int type = cpp_getType(_ptr);
		if(type == 1)
			ret = Type.STANDARD;
		if(type == 2)
			ret = Type.ADVANCED;
		return ret;
	}

	public int getCountryCode(){
		return cpp_getCountryCode(_ptr);
	}

	public BigInteger getUid(){
		BigInteger ret = null;
		byte[] raw = getRawUid();
		if(raw != null){
			ret = new BigInteger(1,raw);
		}
		return ret;
	}
	public byte[] getRawUid(){
		return cpp_getUid(_ptr);
	}

	public byte[] getKey(){
		return cpp_getKey(_ptr);
	}
	public int getKeySize(){
		return cpp_getKeySize(_ptr);
	}
	public Date getDate(){
		return new Date(cpp_getDate(_ptr) * 1000L);
	}
	public Date getValidityDate(){
		return new Date((long)((cpp_getDate(_ptr)+cpp_getValidity(_ptr))*1000l));
	}
	public boolean isPrivateMatch(final byte[] privateKey){
		return cpp_isPrivateMatch(_ptr, privateKey);
	}
	public boolean isValidAt(final Date datetime){
		if(datetime != null){
			long value = datetime.getTime()/1000;
			return cpp_isValidAt(_ptr, value, false);
		}
		return false;
	}
	public boolean isValidAt(final Date datetime, final boolean testmode){
		if(datetime != null){
			long value = datetime.getTime()/1000;
			return cpp_isValidAt(_ptr, value, testmode);
		}
		return false;
	}

	@Deprecated
	public Property[] getPropertyNames(){
		char[] prop = cpp_getPropertyName(_ptr);
		return Property.getName(prop);
	}
	@Deprecated
	public String getProperty(Property name){
		if(name != null)
			return cpp_getPropertyUtf8(_ptr, name.getValue());
		return null;
	}

	public Property[] getAvailableProperty(){
		char[] prop = cpp_getPropertyName(_ptr);
		return Property.getName(prop);
	}

	public String getPersonalName(){
		return cpp_getPropertyUtf8(_ptr, Property.Personal_Name.getValue());
	}
	public String getPersonalId(){
		return cpp_getPropertyUtf8(_ptr, Property.Personal_Id.getValue());
	}
	public String getCompanyName(){
		return cpp_getPropertyUtf8(_ptr, Property.Company_Name.getValue());
	}
	public String getCompanyId(){
		return cpp_getPropertyUtf8(_ptr, Property.Company_Id.getValue());
	}
	public String getCompanyType(){
		return cpp_getPropertyUtf8(_ptr, Property.Company_Type.getValue());
	}
	public String getAddress(){
		return cpp_getPropertyUtf8(_ptr, Property.Address.getValue());
	}
	public String getEmail(){
		return cpp_getPropertyUtf8(_ptr, Property.Email.getValue());
	}
	public byte[] getContentHash(){
		return cpp_getPropertyRaw(_ptr, Property.Content_Hash.getValue());
	}
	public InetAddress getIPAddress() throws UnknownHostException{
		byte[] raw = cpp_getPropertyRaw(_ptr, Property.IP_Address.getValue());
		if(raw != null && raw.length > 0){
			return InetAddress.getByAddress(raw);
		}
		return null;
	}

}
