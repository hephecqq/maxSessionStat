package calMaxSessionModule;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

/**
 * ��������ͳ����
 * 
 * @author hephe
 *
 */
public class RequestStat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5460908271177136038L;

	AtomicInteger curUserNum = new AtomicInteger(0);// ��ǰ����û���

	AtomicInteger lastUserNum = new AtomicInteger(0);// �ϴ�����û���

	BlockingQueue<RequestInfo> curBlockingQueue = new LinkedBlockingQueue<RequestInfo>();

	/**
	 * �û��뿪���䷽��
	 * 
	 * @param request
	 * @return
	 */
	public synchronized int leaveRoom(HttpServletRequest request) {
		if(null!=request) {
			curUserNum.set(curUserNum.get() - 1);//�û�������1
		}
		if(curBlockingQueue.size()>0) {
			curBlockingQueue.remove();
		}else {
			throw new RuntimeException("�����쳣");
		}
		
		return 0;
	}

	/**
	 * �û����뷿�䷽��
	 * 
	 * @param request
	 * @return
	 */
	public synchronized int enterRoom(HttpServletRequest request) {
		// ���request��Ϊ��
		if (null != request) {
			curUserNum.set(curUserNum.get() + 1);
			// ���curUserNum����lastUserNumֵ,�������ֵ
			if (curUserNum.get() > lastUserNum.get()) {
				lastUserNum.set(curUserNum.get());
			}
			// ��װrequest��Ϣ
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
