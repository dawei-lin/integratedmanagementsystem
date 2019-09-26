package com.infinova.authenticationservice.utils;



import com.infinova.authenticationservice.entity.Permission;
import com.infinova.authenticationservice.vo.PermissionVO;

import java.util.LinkedList;
import java.util.List;

public class PermissionUtil {

	/**
	 * 将使用sort排序好的权限集合转成权限树
	 * 
	 * @param list
	 * @return
	 */
	public static Permission listToTree(List<Permission> list) {
		if (list.size() > 0) {
			for (int i = list.size() - 1; i > 0; i--) {
				Permission p = list.get(i);
				for (int j = i - 1; j >= 0; j--) {
					Permission q = list.get(j);
					if (q.getLevel() + 1 == p.getLevel()) {
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


	public static PermissionVO VOlistToTree(List<PermissionVO> list) {
		if (list.size() > 0) {
			for (int i = list.size() - 1; i > 0; i--) {
				PermissionVO p = list.get(i);
				for (int j = i - 1; j >= 0; j--) {
					PermissionVO q = list.get(j);
					if (q.getLevel() + 1 == p.getLevel()) {
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

}
