package com.infinova.imscommon.utils;
/**
 * @Project:CooperativeProductMS_server
 * @File:BeanUtils
 * @Package:com.infinova.pcms.pcmscommon.utils
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/8/10 15:07
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:BeanUtils
 * @Description:
 * @author:ldw
 * @date:2019/8/10 15:07
 *
 *
 */
public class ListBeanUtils {

//    public static <T> List<T> castList(List<T> list, Class<T> clazz){
//        for (T t : list){
//            clazz clazz1;
//            BeanUtils.copyProperties(t,t1);
//
//        }
//    }

    public static <T> List<T> castMapToBean(List<Map<String, Object>> list, Class<T> clazz) throws Exception {
        if(list == null || list.size()==0) {
            return null;
        }
        List<T> tList = new ArrayList<T>();
        // 获取类中声明的所有字段
        Field[] fields = clazz.getDeclaredFields();

        T t;
        for(Map<String, Object> map : list) {
            // 每次都先初始化一遍,然后再设置值
            t = clazz.newInstance();
            for(Field field : fields) {
                // 把序列化的字段去除掉
                if(!"serialVersionUID".equals(field.getName())){
                    // 由于Field都是私有属性，所有需要允许修改
                    field.setAccessible(true);

                    // 设置值, 类型要和vo对应好,不然会报类型转换错误
                    field.set(t, map.get(field.getName()));
                }
            }
            tList.add(t);
        }

        return tList;
    }


    /**
     * 把一个List<Object[]>转换成一个List<T>
     * @param objList
     * @param clz
     * @param <T>
     * @throws Exception
     */
    public static <T> List<T> objectToBean(List<Object[]> objList, Class<T> clz) throws Exception{
        if(objList==null || objList.size()==0) {
            return null;
        }

        Class<?>[] cz = null;
        Constructor<?>[] cons = clz.getConstructors();
        for(Constructor<?> ct : cons) {
            Class<?>[] clazz = ct.getParameterTypes();
            if(objList.get(0).length == clazz.length) {
                cz = clazz;
                break;
            }
        }

        List<T> list = new ArrayList<T>();
        for(Object[] obj : objList) {
            Constructor<T> cr = clz.getConstructor(cz);
            list.add(cr.newInstance(obj));
        }
        return list;
    }

}
