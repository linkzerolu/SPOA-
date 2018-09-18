package com.chuangyu.util;

import java.util.Comparator;


/**
 * list排序实现Comparator接口,也就是定义排序规则,你几乎可以定义任何规则
 *   
 * @author: rqwang     
 * @date: 2018年6月20日 上午10:57:30   
 *
 */
public class ContentComparator  implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		int p1 = Integer.parseInt((String)o1);
		int p2 = Integer.parseInt((String)o2);
		if (p1 < p2){
			return 0;
		}else{
			return 1;
		}
	}
}
