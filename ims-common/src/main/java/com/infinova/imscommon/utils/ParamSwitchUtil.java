package com.infinova.imscommon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ParamSwitchUtil {

	public static Map<String,Object> timeStampSwitch(Map<String,Object> params) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(params.containsKey("beginTime")){
			String beginTime = params.get("beginTime").toString();
			if(null !=  beginTime && !"".equals(beginTime)){
				long lt = new Long(beginTime);
				Date date = new Date(lt);
				String res = simpleDateFormat.format(date);
				params.put("beginTime", res);
			}

		}
		if(params.containsKey("endTime")){
			String endTime = params.get("endTime").toString();
			if(null !=  endTime && !"".equals(endTime)){
				long lt = new Long(endTime);
				Date date = new Date(lt);
				String res = simpleDateFormat.format(date);
				params.put("endTime", res);
			}



		}
		//视频监控报警类型包含一个‘所有报警’类型,对于这个报警类型，将alarmType设置为空
		if(params.containsKey("systemCode") && params.containsKey("alarmType")){
			if("cms".equals(params.get("systemCode").toString()) && "alarm.all".equalsIgnoreCase(params.get("alarmType").toString())){
				params.put("alarmType", "");
			}
		}
		return params;
	}

}
