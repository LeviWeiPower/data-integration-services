package com.aimspeed.common;

/**
 * 多线程工具操作类
 * @author AimSpeed
 */
public class ThreadUtils {
	
	/**
	 * 根据Id获取到线程
	 * @author AimSpeed
	 * @param threadId
	 * @return  Thread
	 */
	public static Thread getThreadById(Long threadId) {

		ThreadGroup group = Thread.currentThread().getThreadGroup();  
		ThreadGroup topGroup = group;  
		
		// 遍历线程组树，获取根线程组  
		while (group != null) {  
		    topGroup = group;  
		    group = group.getParent();  
		}  
		
		// 激活的线程数加倍  
		int estimatedSize = topGroup.activeCount() * 2;  
		Thread[] slackList = new Thread[estimatedSize];  
		
		// 获取根线程组的所有线程  
		int actualSize = topGroup.enumerate(slackList);  
		
		//复制到一个精确大小的列表中
		Thread[] list = new Thread[actualSize];  
		System.arraycopy(slackList, 0, list, 0, actualSize);  
		
		if(null != list) {
			for (Thread thread : list) {
				if(thread.getId() == threadId) {
					return thread;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 中断线程，因为如果使用stop方式进行中断会造成执行到一半的线程直接中断，会有脏数据
	 * @author AimSpeed
	 * @param thread
	 * @return boolean
	 */
	public static boolean interruptThread(Thread thread) {
		while (!thread.isInterrupted()) {
			thread.interrupt();
		}
		return true;
	}
	
}
