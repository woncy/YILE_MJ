package mj.data;

public class UtilByteTransfer{
	
	public static String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv);   
	    }   
	    return stringBuilder.toString();   
	} 
	
	/**  
	 * Convert hex string to byte[]  
	 * @param hexString the hex string  
	 * @return byte[]  
	 */  
	public static byte[] hexStringToBytes(String hexString) {   
	    if (hexString == null || hexString.equals("")) {   
	        return null;   
	    }   
	    hexString = hexString.toUpperCase();   
	    int length = hexString.length() / 2;   
	    char[] hexChars = hexString.toCharArray();   
	    byte[] d = new byte[length];   
	    for (int i = 0; i < length; i++) {   
	        int pos = i * 2;   
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
	    }   
	    return d;   
	}   
	
	/**  
	 * Convert char to byte  
	 * @param c char  
	 * @return byte  
	 */  
	 private static byte charToByte(char c) {   
	    return (byte) "0123456789ABCDEF".indexOf(c);   
	}  
	
	public static void main(String[] args){
		String str = "小刘通知";
//		byte[]  byteArr = str.getBytes();
//		for(int i = 0 ; i < byteArr.length ; i++){
//			System.out.printf("%x\n",byteArr[i]);
//		}
//		String byteString = bytesToHexString(byteArr);
//		System.out.println(byteString);
		String byteStr ="e5b08fe58898e9809ae79fa5000000000000";
		byte[]  byteArr =  hexStringToBytes(byteStr);
		for(int i = 0 ; i < byteArr.length ; i++){
			System.out.printf("%x\n",byteArr[i]);
		}
		
	}

}
