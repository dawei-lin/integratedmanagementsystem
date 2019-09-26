package com.infinova.imscommon.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具集合
 * 
 * @author mx
 * 
 */
public class StringUtil {
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** 空字符串 */
    private static final String NULLSTR = "";

    /** 下划线 */
    private static final char SEPARATOR = '_';


    /**
     * 判断字符串是NULL或者空字符串
     * 
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(String value) {
        if (value != null && !value.isEmpty())
            return false;
        else
            return true;
    }

    public static boolean isEmpty(String value) {
        if (value != null && !value.isEmpty())
            return false;
        else
            return true;
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 判断是否包含汉字
     * 
     * @param strGBK
     * @return
     */
    public static boolean isContantsGBK(String strGBK) {
        if (isNullOrEmpty(strGBK))
            return false;
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(strGBK);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        if (count > 0)
            return true;
        else
            return false;
    }

    /**
     * 判断是数字
     */
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        } 
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是汉字
     * 
     * @param str
     * @return
     */
    public static boolean isGBK(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 统计字符串source中出现子字符串regexNew的次数
     * 
     * @param source
     * @param regexNew
     * @return 返回出现次数
     */
    public static int finder(String source, String regexNew) {
        String regex = "[a-zA-Z]+";
        if (regexNew != null && !regexNew.equals("")) {
            regex = regexNew;
        }
        Pattern expression = Pattern.compile(regex);
        Matcher matcher = expression.matcher(source);
        TreeMap<Object, Integer> myTreeMap = new TreeMap<Object, Integer>();
        int n = 0;
        Object word = null;
        Object num = null;
        while (matcher.find()) {
            word = matcher.group();
            n++;
            if (myTreeMap.containsKey(word)) {
                num = myTreeMap.get(word);
                Integer count = (Integer) num;
                myTreeMap.put(word, new Integer(count.intValue() + 1));
            } else {
                myTreeMap.put(word, new Integer(1));
            }
        }
        return n;
    }


    public static String padLeft(String sourceString, int length) {
        String resultString = "";
        String replaceString = "0";
        if (sourceString != null) {
            if (sourceString.length() >= length)
                resultString = sourceString;
            else {
                int count = length - sourceString.length();
                for (int i = 0; i < count; i++) {
                    resultString += replaceString;
                }
                resultString += sourceString;
            }
        } else {
            for (int i = 0; i < length; i++) {
                resultString += replaceString;
            }
        }
        return resultString;
    }

    /**
     * 字符串按指定长度左补齐
     * @Title:padLeft
     * @Description:TODO
     * @param:@param sourceString  目标字符串
     * @param:@param replace 用于补齐的字符串
     * @param:@param length 补齐后的长度
     * @param:@return
     * @return:String
     * @throws
     */
    public static String padLeft(String sourceString, String replace, int length) {
    	if(isNullOrEmpty(replace))
    		replace = "0";
        String resultString = "";
//        String replaceString = "0";
        if (sourceString != null) {
            if (sourceString.length() >= length)
                resultString = sourceString;
            else {
                int count = length - sourceString.length();
                for (int i = 0; i < count; i++) {
                    resultString += replace;
                }
                resultString += sourceString;
            }
        } else {
            for (int i = 0; i < length; i++) {
                resultString += replace;
            }
        }
        return resultString;
    }
    /**
     * 特殊字符处理:用"%25"替换"%(?![0-9a-fA-F]{2})"中的字符；用"%2B"替换"+"字符.示例: }{X2ST59%W$X]01@38L[S[G.jpg
     * 
     * @param outBuffer
     * @return
     */
    public static String replacer(StringBuffer outBuffer) {
        String data = outBuffer.toString();
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\n+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    // 首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 格式化model的xml结点值的首字母为大写
     * 
     * @param xml
     *            ，例:<?xml version="1.0"?> <OrgUnit><id>117</id><code>OrgUnit_201402201121485625</code>
     *            <name>新建组织机构117</name><description></description><parentid>1</parentid><IsSubCenterRootOrgUnit>false</IsSubCenterRootOrgUnit><ext></ext><
     *            orderNum>0</orderNum><byName></byName></OrgUnit>
     * @return
     */
    public static String toXmlUpperCase(String xml) {
		//xml="<orderNum>0</orderNum><byName></byName><ext></ext></OrgUnit>";
		Pattern pattern = Pattern.compile("(<\\/?)([A-Z/a-z\\d\\:]+)((\\s+.+?)?/?>)");
		StringBuilder res = new StringBuilder();
		try{
			Matcher matchr = pattern.matcher(xml);
			int lastIdx = 0;
			while (matchr.find()) {
				res.append(xml.substring(lastIdx, matchr.start()));
				String item = matchr.group(2);
				item = toUpperCaseFirstOne(item);
				String group1= matchr.group(1);//判断<和</
				String group3 = matchr.group(3);//判断>
				res.append(matchr.group(1) + item + matchr.group(3));
				lastIdx = matchr.end();
	
			}
			res.append(xml.substring(lastIdx));
		}
		catch(Exception ex){
			
			logger.info("format xml node value error:"+ex.getMessage());
		}
		return res.toString();
	}
    
    /**
     * Convert Object to JSON String by jackson object mapper
     * 
     * @param obj
     * @return
     */
    public static String toJSONStr(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据分号分割字符串中英文分号都可以
     *
     * @param str
     * @return
     */
    public static String[] splitF(String str) {
        if (!isNullOrEmpty(str)) {
            Set<String> split = splitBySetp(str, ";", "；");
            if (split != null && split.size() > 0) {
                return split.toArray(new String[split.size()]);
            }
        }
        return new String[] {};
    }

    /**
     * 分割方法支持英文或者中文符号
     *
     * @param str
     * @param step
     * @param step2
     * @return
     */
    public static Set<String> splitBySetp(String str, String step, String step2) {
        Set<String> result = new LinkedHashSet<String>();
        if (!isNullOrEmpty(str)) {
            String[] splt = str.split(step);
            for (String string : splt) {
                String[] split1 = string.split(step2);
                for (String str1 : split1) {
                    if (!isNullOrEmpty(str1)) {
                        result.add(str1);
                    }
                }
            }
        }
        return result;
    }
    
    
    /**
	 * 
	 * comparePermission:对比两个list.<br>
	 * entry.getValue() == 1 new中存在old中不存在 <br>  
	 * entry.getValue() == 3 new中不存在old中存在  
	 * @author xrx
	 * @param newStr 准备入库的新的数据
	 * @param oldStr 数据库中存在的数据
	 * @return mapValue 1-add,2-update,3-del
	 */
    public static Map<String,Integer> comparePermission(List<String> newStr ,List<String> oldStr){
		//return map，使用map保存，后续若要把相同的数据返回的话，兼容性较好.
		Map<String,Integer> compareResule = new HashMap<String, Integer>();
		
		
		for (String str : newStr) {
			compareResule.put(str, 1);
		}
		
		for (String str : oldStr) {
			Integer val = compareResule.get(str);
			if(val !=null){
				compareResule.put(str, 2);
			}else if(val == null){
				compareResule.put(str, 3);
			}
		}
		
		return compareResule;
		
	}
    
    /**
     * 比较两个对象是否相等
     * @param obj1
     * @param obj2
     * @return
     * boolean
     */
    public static boolean compareObj(Object obj1, Object obj2) {
        if (obj2 != null && obj1 != null) {
            if (obj2.equals(obj1)) {
                return true;
            } else {
                return false;
            }
        } else if (obj2 == null && obj1 == null) {
            return true;
        } else {
            return false;
        }
    }

	/**
	 * 获取字符串中第N次出现特定字符的位置
	 * 
	 * @param src
	 * @param regex
	 * @param n
	 * @return
	 */
	public static int getCharPos(String src, String regex, int n) {
		// src在字符串查找regex
		Matcher slashMatcher = Pattern.compile(regex).matcher(src);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			// 当"/"符号第几次次出现的位置
			if (mIdx == n) {
				break;
			}
		}
		return slashMatcher.start();
	}

	/**
	 * 统计字符regex在src出现的次数
	 * @param src
	 * @param regex
	 * @return
	 */
	public static int countRegex(String src, String regex) {
		int count = 0;
		int index = 0;
		while ((index = src.indexOf(regex, index)) != -1) {
			index = index + regex.length();
			count++;
		}
		return count;
	}

    /**
     * 多个Code并用分号隔开组装成Name并用分号隔开
     *
     * @param str
     * @return
     */
    public static String appendF(String str, Map<String, String> map) {
        String result = "";
        if (!isNullOrEmpty(str)) {
            String[] split = splitF(str);
            if (null != split && split.length > 1) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < split.length; i++) {
                    if (i == split.length - 1) {
                        if (null != map.get(i) && map.containsKey(i)) {
                            sb.append(map.get(i));
                        }
                    } else {
                        if (null != map.get(i) && map.containsKey(i)) {
                            sb.append(map.get(i) + ";");
                        }
                    }
                }
                result = sb.toString();
            }
        } else {
            result = str;
        }

        return result;
    }
}
