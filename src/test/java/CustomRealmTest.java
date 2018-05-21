import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author Coder-Leon
 * @date 2018/5/20 15:09
 */
public class CustomRealmTest {

    CustomRealm customRealm = new CustomRealm();

    @Test
    public void testAuthentication(){
        // 1、创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        // 使用加密类matcher
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5"); //加密方法
        matcher.setHashIterations(1); //加密次数
        customRealm.setCredentialsMatcher(matcher);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Leon", "1001");

        // 2、主体认证
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");
        subject.checkPermission("user:delete");
    }
}
