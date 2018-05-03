package com.fly.credentialsmatcher;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private Cache<String, AtomicInteger> passwordRetryCache;  
	
	private AtomicInteger retryCount;
	
	//private CacheManager cacheManager;
	  
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {  
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");  //匹配ehcache.xml对应名字的缓存
    }  
  
    public RetryLimitHashedCredentialsMatcher() {
		// TODO Auto-generated constructor stub
	}
    
   /* public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}*/
    
    @Override  
    public boolean doCredentialsMatch(AuthenticationToken token,  
            AuthenticationInfo info) {  
        String username = (String) token.getPrincipal();  
        // retry count + 1  
        // passwordRetryCache.get(username);  
        retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {  
            retryCount = new AtomicInteger(0);  
            passwordRetryCache.put(username, retryCount);  
        }  
        if (retryCount.incrementAndGet() > 5) {  
            // if retry count > 5 throw  
            throw new ExcessiveAttemptsException();  
        }  
       // setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        boolean matches = super.doCredentialsMatch(token, info);  
        if (matches) {  
            // clear retry count
        	//当匹配成功时，清除之前的缓存  
            passwordRetryCache.remove(username);  
        } 
        return matches;  
    }  
}
