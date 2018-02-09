package calMaxSessionModule;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

/**
 * 在线人数统计类
 * 
 * @author hephe
 *
 */
public class RequestStat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5460908271177136038L;

	AtomicInteger curUserNum = new AtomicInteger(0);// 当前最大用户数

	AtomicInteger lastUserNum = new AtomicInteger(0);// 上次最大用户数

	BlockingQueue<RequestInfo> curBlockingQueue = new LinkedBlockingQueue<RequestInfo>();

	/**
	 * 用户离开房间方法
	 * 
	 * @param request
	 * @return
	 */
	public synchronized int leaveRoom(HttpServletRequest request) {
		if(null!=request) {
			curUserNum.set(curUserNum.get() - 1);//用户数减少1
		}
		if(curBlockingQueue.size()>0) {
			curBlockingQueue.remove();
		}else {
			throw new RuntimeException("队列异常");
		}
		
		return 0;
	}

	/**
	 * 用户进入房间方法
	 * 
	 * @param request
	 * @return
	 */
	public synchronized int enterRoom(HttpServletRequest request) {
		// 如果request不为空
		if (null != request) {
			curUserNum.set(curUserNum.get() + 1);
			// 如果curUserNum大于lastUserNum值,保存最大值
			if (curUserNum.get() > lastUserNum.get()) {
				lastUserNum.set(curUserNum.get());
			}
			// 封装request信息
			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setCurtimestamp(new Date().getTime());
			requestInfo.setRequest(request);
			requestInfo.setRequestUri(request.getRequestURI());

			curBlockingQueue.add(requestInfo);

			if (curBlockingQueue.size() != curUserNum.get()) {
				curUserNum.set(curBlockingQueue.size());
			}

		}
		return curUserNum.get();
	}

}
