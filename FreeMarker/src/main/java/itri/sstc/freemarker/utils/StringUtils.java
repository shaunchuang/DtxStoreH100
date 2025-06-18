package itri.sstc.freemarker.utils;

import java.util.Collection;  
 
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String replace(String old, int start, char c){
		if(old==null) return old;
		return replace(old, start, old.length(), c);
	}
	
	public static String replace(String old, int start, int end, char c){
		if(old==null || start>end || start>=old.length()) return old;
		StringBuilder sb = new StringBuilder(old);
		for(int i=start; i<end; i++){
			sb.setCharAt(i, c);
		}
		return sb.toString();
	}
	
	public static String null2Empty(String str){
		if(str==null) return "";
		return str;
	}
	
	public static boolean contains(String str, String keyword){
		return isNotEmpty(str) && str.contains(keyword);
	}
	
	public static <T> String buildInClause(Collection<T> list){
		if(list==null || list.size()==0) return "";
		if(list.size()==1) return "?";
		return "?"+StringUtils.repeat(",?", list.size()-1);
	}
	
	public static <T> String buildInClause(T[] arr){
		if(arr==null || arr.length==0) return "";
		if(arr.length==1) return "?";
		return "?"+StringUtils.repeat(",?", arr.length-1);
	}
	
	public static int utf8LenCounter (CharSequence sequence) {
		int count = 0;
	    for (int i = 0, len = sequence.length(); i < len; i++) {
	      char ch = sequence.charAt(i);
	      if (ch <= 0x7F) {
	        count++;
	      } else if (ch <= 0x7FF) {
	        count += 2;
	      } else if (Character.isHighSurrogate(ch)) {
	        count += 4;
	        ++i;
	      } else {
	        count += 3;
	      }
	    }
	    return count;
	}
}
