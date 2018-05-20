import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author Coder-Leon
 * @date 2018/5/19 20:16
 */

public class JdbcRealmTest {

    // 创建数据源
    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shirojdbcrealmtest");
        dataSource.setUsername("root");
        dataSource.setPassword("1001");
    }

    @Test
    public void testAuthentication(){
        // 设置JdbcRealm数据源
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        // 启动权限开关，默认false
        jdbcRealm.setPermissionsLookupEnabled(true);

        // 自定义SQL，查询自建test_users表
        String sql = "select password from test_users where username = ?";
        String roleSql = "select rolename from test_user_roles where username = ?";
        String perSql = "select permission from test_roles_permissions where rolename = ?";
        // 使用自定义授权查询语句
        jdbcRealm.setAuthenticationQuery(sql);
        jdbcRealm.setUserRolesQuery(roleSql);
        jdbcRealm.setPermissionsQuery(perSql);

        // 1、创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //UsernamePasswordToken token = new UsernamePasswordToken("Leon", "1001");
        UsernamePasswordToken token = new UsernamePasswordToken("test", "test");

        // 2、主体认证
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        //subject.checkRole("admin");
        subject.checkRole("user");

        // check前要先启动权限开关
        // subject.checkPermission("user:select");
        subject.checkPermission("system:login");

    }
}
