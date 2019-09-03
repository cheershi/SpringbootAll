package com.flycode.dofilter;

import com.flycode.exception.MyException;
import com.flycode.utils.IPWhiteList;
import com.flycode.web.RestSenderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.didispace.com
 *
 */
@WebFilter(urlPatterns = "/*")
public class IPFilter  implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(RestSenderController.class);
    private static String filePath="classpath:ipFilter.properties";
    private static Set<String> allowRegexList=new HashSet<>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            //在过滤器初始化的时候初始化白名单列表
            initAllowList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //1.获取访问者的IP地址
        String remoteAddr = servletRequest.getRemoteAddr();
        if (null == remoteAddr || remoteAddr.trim().equals("")) {
            throw new RuntimeException("IP地址为空,拒绝访问!");
        }
        // 如果白名单为空,则认为没做限制,放行
        if (null == allowRegexList || allowRegexList.size() == 0) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 检查用户IP是否在白名单
        if (IPWhiteList.checkLoginIP(remoteAddr, allowRegexList)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            throw new RuntimeException("您的IP:" + remoteAddr + ",不在白名单中,拒绝访问!");
        }
    }

    @Override
    public void destroy() {

    }

    public void initAllowList() throws IOException{
        File file = ResourceUtils.getFile(filePath);
        FileInputStream is = null;
        StringBuilder stringBuilder = null;
        try {
            if (file.length() != 0) {
                /**
                 * 文件有内容才去读文件
                 */
                is = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    if(!line.startsWith("#")){
                        stringBuilder.append(line);
                    }

                }
                reader.close();
                is.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
      if(stringBuilder!=null){
          String allowRegex=String.valueOf(stringBuilder);
          allowRegexList= IPWhiteList.getAvaliIpList(allowRegex);
      }


    }



}