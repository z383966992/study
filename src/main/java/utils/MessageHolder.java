package utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 这个用来在内存中保存信息
 * @author liangliangzhou3
 */
public class MessageHolder<T> {

	private T instance = null;
	private Timer timer = new Timer();

	/**
	 * 刷新时间
	 * @param execFre
	 */
	public MessageHolder(Long execFre) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				instance = null;
			}
		}, execFre, execFre);
	}
	
	
	/**
	 * 返回hold的信息
	 * @return
	 */
	public T getHoldMessage() {
		return instance;
	}
	
	/**
	 * hold message操作
	 * @param t
	 * @return
	 */
	
	public T holdMessage(T t) {
		this.instance = t;
		return instance;
	}
	
	/**
	 * 清空hold的message
	 */
	public void clearHoldMessage() {
		instance = null;
	}
}
