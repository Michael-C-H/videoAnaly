package com.kingwant.videoAnaly.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang.StringUtils;
import com.kingwant.videoAnaly.util.JsoupUtil;
 
 
/** 
 * <code>{@link XssHttpServletRequestWrapper}</code>
 * @author
 */  
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {  
    HttpServletRequest orgRequest = null;  
    private boolean isIncludeRichText = false;
  
    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {  
        super(request);  
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }  
  
    /** 
    * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/> 
    * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/> 
    * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖 
    */  
    @Override  
    public String getParameter(String name) {
        
        String s = super.getParameter(name);  
        if (StringUtils.isNotBlank(s)) {
            //s = JsoupUtil.clean(s);
        	StringBuilder sb = new StringBuilder(s.length() + 16);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '>':
                        //全角大于号
                        sb.append("&gt;");
                        break;
                    case '<':
                        //全角小于号
                        sb.append("&lt;");
                        break;
                    case '\'':
                        //全角单引号
                        sb.append('‘');
                        break;
                    case '&':
                        //全角
                        sb.append('＆');
                        break;
                    case '\\':
                        //全角斜线
                        sb.append('＼');
                        break;
                    case '#':
                        //全角井号
                        sb.append('＃');
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
            return sb.toString();
        }
        return s;
    }  
    
    @Override
    public String[] getParameterValues(String name) {
    	String[] arr = super.getParameterValues(name);
    	if(arr != null){
    		for (int i=0;i<arr.length;i++) {
    			arr[i] = JsoupUtil.clean(arr[i]);
    		}
    	}
    	return arr;
    }
    
  
    /** 
    * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/> 
    * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/> 
    * getHeaderNames 也可能需要覆盖 
    */  
    @Override  
    public String getHeader(String name) {  
        name = JsoupUtil.clean(name);
        String value = super.getHeader(name);  
        if (StringUtils.isNotBlank(value)) {  
            value = JsoupUtil.clean(value); 
        }  
        return value;  
    }  
  
    /** 
    * 获取最原始的request 
    * 
    * @return 
    */  
    public HttpServletRequest getOrgRequest() {  
        return orgRequest;  
    }  
  
    /** 
    * 获取最原始的request的静态方法 
    * 
    * @return 
    */  
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {  
        if (req instanceof XssHttpServletRequestWrapper) {  
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();  
        }  
  
        return req;  
    }  
  
}  
