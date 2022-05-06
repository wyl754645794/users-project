package org.users.strategy.roleImpl;

import org.users.constant.ClassNameEnum;
import org.users.constant.ProjectEnum;
import org.users.model.Role;
import org.users.model.User;
import org.users.strategy.Strategy;
import org.users.util.ModelUtil;
import org.users.util.StorageUtil;
import org.users.util.StringUtil;

import java.util.*;

/**
 * RoleStrategy
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 9:22 PM:09
 */
public class RoleStrategy implements Strategy {

    @Override
    public boolean judge(Object condition) {
        return ProjectEnum.ROLE.getValue().equals(condition);
    }

    public Object createRole(HashMap<String, String> params) {
        Role role = (Role) ModelUtil.createModelByParams(params, ClassNameEnum.ROLE.getValue());
        if (!Optional.ofNullable(role)
                .isPresent()) {
            System.err.println("createRole is fail");
            return null;
        }
        if (StringUtil.isEmpty(role.getRoleId()) || StringUtil.isEmpty(role.getRoleName())) {
            System.err.println("RoleId or RoleName or password is null");
            return null;
        }
        StorageUtil.setRole(role);
        System.out.println("role create is success");
        return "role create is success";
    }

    public Object deleteRole(HashMap<String, String> params) {
        String roleId = params.get("roleId");
        if (StringUtil.isEmpty(roleId)) {
            System.err.println("roleId param is null");
            return null;
        }
        Role role = StorageUtil.getRole(roleId);
        if (!Optional.ofNullable(role)
                .isPresent()) {
            System.err.println("role is not exist");
            return null;
        }
        StorageUtil.getRoles()
                .removeIf(r->r.getRoleId().equals(roleId));
        StorageUtil.getUserMap().forEach((k,v)->
                v.getRoles().removeIf(r->r.getRoleId().equals(roleId)));
        System.out.println("role delete is success");
        return "role delete is success";
    }

    public static void main(String[] args) {

        List<Role> roles = new ArrayList<>();
        Role r1 = new Role();
        r1.setRoleId("123");
        r1.setRoleName("灌灌灌灌");
        Role r2 = new Role();
        r2.setRoleId("321");
        r2.setRoleName("的烦烦烦");
        roles.add(r2);
        roles.add(r1);
        StorageUtil.setUserMap(new User(){{
            setUserId("15345345");
            setRoles(roles);
        }});
        StorageUtil.getUserMap().forEach((k,v)->v.getRoles().removeIf(r->r.getRoleId().equals("123")));
        System.out.println(roles);
    }

}
