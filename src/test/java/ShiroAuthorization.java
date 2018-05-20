import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Coder-Leon
 * @date 2018/5/19 19:30
 */

public class ShiroAuthorization {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("Leon", "1001","admin","user");
    }

    @Test
    public void testAuthentication(){
        // 1、创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Leon", "1001");

        // 2、主体授权
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkRoles("admin","user");
        System.out.println("hasRole: " + subject.hasRole("admin"));
        List<String> list = new ArrayList<String>();
        list.add("admin");
        list.add("user1");
        System.out.println("hasRoles: " + subject.hasRoles(list)[1]);
    }
}
