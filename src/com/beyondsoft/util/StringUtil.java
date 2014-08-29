package com.beyondsoft.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	   public static final String TAG = "StringUtil";

	    public static final String CHARSET_NAME_UTF8        = "UTF-8";
	    public static final char[] digital                  = "0123456789ABCDEF".toCharArray();

	    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	                    "\\@" +
	                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	                    "(" +
	                    "\\." +
	                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	                    ")+"
	    );
	    public static final String EMPTY_STRING = "";
	    
	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(String obj) {
		if (null == obj || "".equals(obj)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 数组是否为空
	 * 
	 * @param values
	 * @return
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 判断是否符合邮箱格式
	 * 
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为固定电话
	 * 
	 * @param phone
	 * @return
	 */
	// Pattern pattern =
	// Pattern.compile("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
	public static boolean isTel(String tel) {
		// Pattern pattern = Pattern.compile("([0-9]{4})([0-9]{4})([0-9]{4})");
//		Pattern pattern = Pattern.compile("^(0[0-9]{2,3}\\-)?([0-9]{6,7})+(\\-[0-9]{1,4})?$");
//		Matcher m = pattern.matcher(tel.trim());
//		if (m.matches()) {
//			return true;
//		}
//		return false;
		String regex = "(\\d{3,4}\\-?)\\d{8}$"; 
        return Pattern.matches(regex, tel); 
	}

	/**
	 * 是否包含特殊字符
	 * @param tel
	 * @return
	 */
	public static boolean isN(String tel) {
		// Pattern pattern =
		// Pattern.compile("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
		// String regEx =
		// "[`~#$&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）—|{}【】‘；：”“’。，、？]";

		String regEx = "[#$&*()|]";

		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(tel.trim());
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为特殊字符
	 * @param pInput
	 * @return
	 */
	public static boolean isSpecialChar(String pInput) {
		if (pInput == null) {
			return false;
		}
		String regEx = ".*[&|'|>|<|\\\\|/].*$";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(Pattern.compile("[\\r|\\n]").matcher(pInput).replaceAll(""));
		return matcher.matches();
	}

	public static String StringFilter(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~#$&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）—|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断是否为手机号码
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		// Pattern pattern =
		// Pattern.compile("^((\\(\\d{3}\\))|(\\d{3}\\-))?13[0-9]\\d{8}|15[089]\\d{8}");
		Pattern pattern = Pattern.compile("([0-9]{3})([0-9]{4})([0-9]{4})");
		Matcher m = pattern.matcher(phone.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		String strPattern = "[0-9]*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查是不是生日类型 支持闰年,2月特殊判断等等
	 */
	public static boolean isBirthday(String birth) {
		// Pattern pt =
		// Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})(0?[13578]|1[02])(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})(0?[13456789]|1[012])(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})0?2(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))0?229))$");
		// Pattern pt =
		// Pattern.compile("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))");
		Pattern pt = Pattern
				.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$");
		if (pt.matcher(birth).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入是否是数字或者字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumOrLetter(String str) {
		String strPattern = "^[A-Za-z0-9]+$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 匹配身份证
	 * 
	 * @param idCard
	 * @return true 如果匹配，false 不匹配
	 */
	public static boolean isIDCard(String idCard) {
		String pattern = "^\\d{10}|\\d{13}|\\d{15}|\\d{17}(\\d|x|X)$";
		return idCard.matches(pattern);
	}

	/**
	 * 判断字符长度
	 * 
	 * @param str
	 * @param maxLen
	 * @return
	 */
	public static boolean isLength(String str, int maxLen) {
		char[] cs = str.toCharArray();
		int count = 0;
		int last = cs.length;
		for (int i = 0; i < last; i++) {
			if (cs[i] > 255)
				count += 2;
			else
				count++;
		}
		if (count >= maxLen)
			return true;
		else
			return false;
	}


    public static String encodeHexStr(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        char[] result = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            result[i * 2] = digital[(bytes[i] & 0xf0) >> 4];
            result[i * 2 + 1] = digital[bytes[i] & 0x0f];
        }
        return new String(result);
    }

    public static byte[] decodeHexStr(final String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length % 2 != 0) {
            throw new RuntimeException("hex str length must can mod 2, str:" + str);
        }
        byte[] bytes = new byte[charArray.length / 2];
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            int b;
            if (c >= '0' && c <= '9') {
                b = (c - '0') << 4;
            } else if (c >= 'A' && c <= 'F') {
                b = (c - 'A' + 10) << 4;
            } else {
                throw new RuntimeException("unsport hex str:" + str);
            }
            c = charArray[++i];
            if (c >= '0' && c <= '9') {
                b |= c - '0';
            } else if (c >= 'A' && c <= 'F') {
                b |= c - 'A' + 10;
            } else {
                throw new RuntimeException("unsport hex str:" + str);
            }
            bytes[i / 2] = (byte) b;
        }
        return bytes;
    }

    public static String encodeBase64Str(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        //Base64Coder.encode(in)
        return new String(Base64Coder.encode(bytes));
    }

    public static byte[] decodeBase64Str(final String str) {
        if (str == null) {
            return null;
        }
        return Base64Coder.decode(str);
    }

    public static String toString(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, CHARSET_NAME_UTF8);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String toString(final byte[] bytes, String charset) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charset);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static byte[] toBytes(final String str) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(CHARSET_NAME_UTF8);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 得到一个字符串的'字符长度',
     * 约定英文半角字符长度为1，中文全角字符长度为2。
     * @param str
     * @return 字符长度
     */
    public static int getCharLength(String str) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 255) {
                counter++;
            } else {
                counter = counter + 2;
            }
        }
        return counter;
    }

    /**
     * 按指定长度截断中英文混合字符串，并以后缀…结尾，此后缀长度为2。
     * 约定英文半角字符长度为1，中文全角字符长度为2。
     * 如果字符串‘字符长度’等于len，不截断
     * @param str
     * @param len
     * @return
     */
    public static String ShortenCn(String str, int len) {
        return ShortenCn(str, len, "…", 2);
    }

    /**
     * 按指定长度截断中英文混合字符串，并以指定后缀结尾。
     * 约定英文半角字符长度为1，中文全角字符长度为2。
     * 如果字符串‘字符长度’等于len，不截断
     * @param str
     * @param len
     * @param suffix
     * @return
     */
    public static String ShortenCn(String str, int len, String suffix, int suffLen) {
        if ("".equals(str) || str == null || str.trim().equals(""))
            return "";
        if (suffix.length() >= str.length())
            suffix = "";

        StringBuffer sb = new StringBuffer();
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            sb.append(c);
            if (c < 255) {
                counter++;
            } else {
                counter = counter + 2;
            }
            if (counter > len - suffLen) {
                if (i < str.length()-1) {
                    sb.delete(sb.length() - 1, sb.length());
                    sb.append(suffix);
                }
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 截取str字符串的前charLen个字符，这里的charLen为字符长度
     * 约定英文半角字符长度为1，中文全角字符长度为2。
     * @param str
     * @param charLen
     * @return
     */
    public static String charSubString(String str, int charLen) {
        StringBuffer sb = new StringBuffer();
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            sb.append(c);
            if (c < 255) {
                counter++;
            } else {
                counter = counter + 2;
            }
            if (counter > charLen) {
                if (i < str.length()-1) {
                    sb.delete(sb.length() - 1, sb.length());
                }
                break;
            } else if (counter == charLen) {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 将某个字符串取相应长度返回（若有截断则已suffix结尾），字符长度均为1
     * @param str
     * @param length
     * @param suffix
     * @return 不可能返回null
     */
    public static String trimString(String str, int length, String suffix){
        if (suffix != null && suffix.length()>0) {
            int suffixLength = suffix.length();
            if (length < suffixLength) {
                return ".";
            }

            if (str==null) {
                return ".";
            } else if (str.length() > length) {
                return str.subSequence(0, length - suffixLength) + suffix;
            } else {
                return str;
            }
        } else {
            return ".";
        }
    }

    /**
     * 以…结尾的trim（若有截断则已suffix结尾），字符长度均为1
     * @param str
     * @param length
     * @return 不可能返回null
     */
    public static String trimString(String str, int length){
        return trimString(str, length, "…");
    }

    /**
     * 判断是否英文,含正常的英文符号
     * @return
     */
    public static boolean isMessageEnglish(String msg) {
        if (Pattern.matches("^[\\x00-\\x7F]*$", msg)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * URLEncoder.encode(url, "UTF-8")的封装
     * @param url
     * @return
     */
    public static String urlEncode(String url) {
        if (url == null) return null;
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //default
            return "";
        }
    }

    public static boolean isEmailFormat(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
 

	/**
	 * 剪切字符串
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String cutString(String str, int len) {
		if (!StringUtil.isNull(str)) {
			if (str.length() >= len)
				str = str.substring(0, len) + "...";
		}
		return str;
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 * @return
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}



	/**
	 * 返回指定格式的字符
	 * 
	 * @param list
	 * @return
	 */
	public static String getValue(HashMap<String, String> list) {
		String value;
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = list.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			// buffer.append("'"+entry.getValue().toString()+"'");
			// buffer.append(",");
			buffer.append(entry.getValue().toString());
		}
		value = buffer.substring(0, buffer.lastIndexOf(","));
		return value;
	}

	public static int compareDate(String start, String end) {
		int result = 0;
		if (!"".equals(start) && !"".equals(end)) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date startDate = sFormat.parse(start);
				Date endDate = sFormat.parse(end);
				result = startDate.compareTo(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		if (fileName != null && fileName != "") {
			int typeIndex = fileName.lastIndexOf(".");
			if (typeIndex != -1) {
				String fileType = fileName.substring(typeIndex + 1).toLowerCase();
				return fileType;
			}
		}
		return null;
	}
	
	/**
	 * Unicode 转 GBK
	 * @param s
	 * @return
	 */
	public static String UnicodeToGBK2(String s){
        String[] k = s.split(";") ;
        String rs="";
        for(int i=0;i<k.length;i++) {
            int strIndex=k[i].indexOf("&#");
            String newstr=k[i];
            if(strIndex>-1) {
                String kstr = "";
                if(strIndex>0) {
                    kstr = newstr.substring(0,strIndex);
                    rs+=kstr;
                    newstr = newstr.substring(strIndex);
                }
                int m = Integer.parseInt(newstr.replace("&#",""));
                char c = (char)m ;
                rs+= c ;
            } else {
                rs+=k[i];
            }
        }
        return rs;
    }
	

}
