package calMaxSessionModule;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * request��װ��
 * 
 * @author hephe
 *
 */
public class RequestInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "RequestInfo [request=" + request + ", requestUri=" + requestUri + ", curtimestamp=" + curtimestamp
				+ "]";
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public long getCurtimestamp() {
		return curtimestamp;
	}

	public void setCurtimestamp(long curtimestamp) {
		this.curtimestamp = curtimestamp;
	}

	/**
	 * �������request
	 */
	private HttpServletRequest request;
	
	/**
	 * �����ַ
	 */
	private String requestUri;
	
	/**
	 * ʱ���
	 */
	private long curtimestamp;
}
