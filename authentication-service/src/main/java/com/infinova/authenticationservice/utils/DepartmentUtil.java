package com.infinova.authenticationservice.utils;
/**
 * @Project:CooperativeProductMS_server
 * @File:DepartmentUtil
 * @Package:com.infinova.pcms.pcmssecurity.utils
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/4/20 11:16
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.infinova.authenticationservice.entity.Department;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName:DepartmentUtil
 * @Description:
 * @author:ldw
 * @date:2019/4/20 11:16
 *
 *
 */
public class DepartmentUtil {

    /**
     * 将使用sort排序好的集合转成树
     * @param list
     * @return
     */
    public static Department listToTree(List<Department> list) {
        if (list.size() > 0) {
            for (int i = list.size() - 1; i > 0; i--) {
                Department p = list.get(i);
                for (int j = i - 1; j >= 0; j--) {
                    Department q = list.get(j);
                    if (q.getLever() + 1 == p.getLever()) {
                        if (q.getChildren() == null) {
                            q.setChildren(new LinkedList<>());
                        }
                        q.getChildren().add(0, p);
                        break;
                    }
                }
            }
            return list.get(0);
        }
        return null;
    }

    public static Department listGetTree(List<Department> list) {
        Department treeList = new Department();
        for (Department tree : list) {
            //找到根
            if (tree.getParentId().equals("0")) {
                treeList=tree;
            }
            //找到子
            for (Department treeNode : list) {
                if (tree.getId()==Integer.valueOf(treeNode.getParentId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new LinkedList<Department>());
                    }
                    tree.getChildren().add(0,treeNode);
                }
            }
        }
        return treeList;
    }
}
