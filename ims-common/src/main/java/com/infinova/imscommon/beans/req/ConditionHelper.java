package com.infinova.imscommon.beans.req;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.infinova.imscommon.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件帮助类
 * 
 * @author yzhb
 * 
 */
public class ConditionHelper {

    /**
     * 根据filter条件获取查询条件hql和values example:
     * filter={"ctype":"and","conditions":[{"name":"pseudo","op":"=","type":"string","value":"1111"
     * },{"name":"cameraName" ,"op":"like","type":"string","value":"222"}]}
     * &sort=[{"name":"pseudo","order":"desc"},{"name" :"cameraName","order":"asc"}]
     * 
     * @param filter
     * @return
     * 
     */
    public static FilterInfo getFilterHql(String filter) {
        return getFilterHql(filter, "");
    }

    /**
     * 根据filter条件获取查询条件hql和values，可以指定表名或表别名
     * 
     * @param filter
     * @param tableName
     * @return
     */
    public static FilterInfo getFilterHql(String filter, String tableName) {
        FilterInfo filterInfo = new FilterInfo();
        if (StringUtil.isNullOrEmpty(filter)) {
            return filterInfo;
        }
        JSONObject filterObj = JSON.parseObject(filter);
        String ctype = filterObj.getString("ctype");
        if (ctype.trim().equalsIgnoreCase("or")) {
            ctype = " or ";
        } else {
            ctype = " and ";
        }

        JSONArray conditions = filterObj.getJSONArray("conditions");
        StringBuffer sb = new StringBuffer();
        List<Object> vals = new ArrayList<Object>();
        for (int i = 0; i < conditions.size(); i++) {
            if (vals.size() > 0) {
                sb.append(ctype);
            }

            JSONObject cond = conditions.getJSONObject(i);
            if (StringUtil.isNullOrEmpty(tableName)) {
                sb.append(" " + cond.getString("name"));
            } else {
                sb.append(" " + tableName + "." + cond.getString("name"));
            }
            sb.append(" " + cond.getString("op"));
            sb.append(" ?");

            if (cond.getString("type").equalsIgnoreCase("int")) {
                vals.add(cond.getInteger("value"));
            } else if (cond.getString("type").equalsIgnoreCase("double")) {
                vals.add(cond.getDouble("value"));
            } else if (cond.getString("type").equalsIgnoreCase("bool")) {
                vals.add(cond.getBoolean("value"));
            } else {
                if (cond.getString("op").equalsIgnoreCase("like")) {
                    vals.add("%" + cond.getString("value") + "%");
                } else {
                    vals.add(cond.getString("value"));
                }
            }
        }

        filterInfo.setHql(sb.toString());
        filterInfo.setValues(vals);
        return filterInfo;
    }

    /**
     * 根据sort条件获取排序方式hql
     * 
     * @param sort
     * @return
     */
    public static String getOrderHql(String sort) {
        if (StringUtil.isNullOrEmpty(sort)) {
            return "";
        }
        JSONArray orders = JSON.parseArray(sort);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orders.size(); i++) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            JSONObject cond = orders.getJSONObject(i);
            sb.append(" " + cond.getString("name"));
            String ascOrDesc = cond.getString("order");
            if (ascOrDesc.equalsIgnoreCase("asc")) {
                sb.append(" asc");
            } else {
                sb.append(" desc");
            }
        }
        return sb.toString();
    }
}
