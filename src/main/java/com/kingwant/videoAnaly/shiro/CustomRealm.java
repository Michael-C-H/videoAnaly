package com.kingwant.videoAnaly.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 工程师登陆认证、授权realm
 * @author MichaelCH
 *
 */
public class CustomRealm extends AuthorizingRealm {

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
        
        String token = (String) auth.getCredentials();

        //判断用户名密码是否正确
       
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
