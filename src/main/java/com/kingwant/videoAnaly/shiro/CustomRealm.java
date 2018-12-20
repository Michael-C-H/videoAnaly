package com.kingwant.videoAnaly.shiro;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.kingwant.videoAnaly.entity.User;
import com.kingwant.videoAnaly.util.ComUtil;



/**
 * 工程师登陆认证、授权realm
 * @author MichaelCH
 *
 */
public class CustomRealm extends AuthorizingRealm {
    @Value("${sys.user.name}")
    private String username;
 
    @Value("${sys.user.password}")
    private String password;
    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
    	//一般来说工程师是无须授权的，留在此处做后续扩展
    	
    	return null;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {    	
        //String token = (String) auth.getCredentials();
        //判断用户名密码是否正确
        // 获取页面登录用户的 账号密码
     	UsernamePasswordToken upt = (UsernamePasswordToken) auth;
     	String loginusername = upt.getUsername();
     	char[] cloginpassword = upt.getPassword();
     	if(ComUtil.isEmpty(loginusername)||ComUtil.isEmpty(cloginpassword)){
     		throw new AuthenticationException("账号或者密码为空"); 
     	}
     	String strloginpassword = new String(cloginpassword);
     	//获取配置文件中的账号密码
     	Properties prop = new Properties();     
     	if(!loginusername.equals(username)){
     		throw new UnknownAccountException("账号错误");
     	}
     	if(!strloginpassword.equals(password)){
     		throw new UnknownAccountException("密码错误");
     	}
     	User user = new User(username,password);
     	AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(), user.getUsername());
     	Subject subject1 = SecurityUtils.getSubject();
        if (null != subject1) {
            Session session = subject1.getSession();
            if (null != session) {
                session.setAttribute("currentUser", user);
            }
        }
        return authcInfo;
	}
}
