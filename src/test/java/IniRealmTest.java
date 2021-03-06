import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author Coder-Leon
 * @date 2018/5/19 20:01
 */
public class IniRealmTest {

    IniRealm iniRealm = new IniRealm("classpath:users.ini");

    @Test
    public void testAuthentication(){
        // 1、创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Leon", "1001");

        // 2、主体认证
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");

    }
}
