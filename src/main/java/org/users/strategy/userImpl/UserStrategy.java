package org.users.strategy.userImpl;

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
 * UserStrategy
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 9:29 PM:26
 */
public class UserStrategy implements Strategy {

    @Override
    public boolean judge(Object condition) {
        return ProjectEnum.USER.getValue().equals(condition);
    }

    public Object createUser(HashMap<String, String> params) {
        User user = (User) ModelUtil.createModelByParams(params, ClassNameEnum.USER.getValue());
        if (!Optional.ofNullable(user)
                .isPresent()) {
            System.err.println("createUser is fail");
            return null;
        }
        if (StringUtil.isEmpty(user.getUserId()) || StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
            System.err.println("userId or userName or password is null");
            return null;
        }
        user.setRoles(null);
        StorageUtil.setUserMap(user);
        System.out.println("user create is success");
        return "user create is success";
    }

    public Object deleteUser(HashMap<String, String> params) {
        String userId = params.get("userId");
        if (StringUtil.isEmpty(userId)) {
            System.err.println("userId param is null");
            return null;
        }
        User user = StorageUtil.getUserMap(userId);
        if (!Optional.ofNullable(user)
                .isPresent()) {
            System.err.println("user is not exist");
            return null;
        }
        StorageUtil.getUserMap().remove(userId);
        System.out.println("user delete is success");
        return "user delete is success";
    }

    public Object authenticate(HashMap<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(password)) {
            System.err.println("userId or password is null");
            return null;
        }
        User userMap = StorageUtil.getUserMap(userId);
        if (!Optional.ofNullable(userMap)
                .isPresent()) {
            System.err.println("user is not exist");
            return null;
        }
        if (!userMap.getPassword().equals(password)) {
            System.err.println("wrong password");
            return null;
        }
        if (StringUtil.isNotEmpty(StorageUtil.getToken(userId))) {
            if (userMap.getLastLoginDate() != null &&
                    System.currentTimeMillis() - userMap.getLastLoginDate().getTime() < 7200000) {
                System.err.println("The current user has logged in");
                return null;
            }
        }
        userMap.setLastLoginDate(new Date());
        String token = StorageUtil.setToken(userId);
        System.out.println("token: "+token);
        return token;
    }

    public Object invalidate(HashMap<String, String> params){
        String token = params.get("token");
        if (StringUtil.isEmpty(token) ) {
            System.err.println("token is null");
            return null;
        }
        String userId = StorageUtil.getUserIdByToken(token);
        if (StringUtil.isEmpty(userId)){
            System.err.println("token is not exist");
            return null;
        }
        User user = StorageUtil.getUserMap(userId);
        if (!Optional.ofNullable(user)
                .isPresent()) {
            System.err.println("user is not exist");
            return null;
        }
        user.setLastLoginDate(null);
        StorageUtil.getUserMap().put(userId,user);
        StorageUtil.getUserToken().remove(userId);
        System.out.println("token destruction succeeded");
        return "token destruction succeeded";
    }

    public Object checkRole(HashMap<String, String> params) {
        String token = params.get("token");
        String roleId = params.get("roleId");
        if (StringUtil.isEmpty(roleId) || StringUtil.isEmpty(token)) {
            System.err.println("roleId or token is null");
            return null;
        }
        String userId = StorageUtil.getUserIdByToken(token);
        if (StringUtil.isEmpty(userId)) {
            System.err.println("user is not login");
            return null;
        }
        if (!StorageUtil.checkLoginTime(userId)) {
            System.err.println("Logon failure");
            return null;
        }
        User user = StorageUtil.getUserMap(userId);
        if (!Optional.ofNullable(user)
                .isPresent()){
            System.err.println("user is not exist");
            return null;
        }
        List<Role> roles = user.getRoles();
        if (!Optional.ofNullable(roles)
                .isPresent() || roles.size() ==0){
            System.err.println("The user role does not exist");
            return null;
        }
        for (Role role : roles) {
            if (role.getRoleId().equals(roleId)){
                System.out.println("The user matches the role");
                return "The user matches the role";
            }
        }
        System.err.println("The user does not match the role");
        return null;
    }

    public Object allRoles(HashMap<String, String> params) {
        String token = params.get("token");
        if (StringUtil.isEmpty(token)) {
            System.err.println("token is null");
            return null;
        }
        String userId = StorageUtil.getUserIdByToken(token);
        if (StringUtil.isEmpty(userId)) {
            System.err.println("user is not login");
            return null;
        }
        if (!StorageUtil.checkLoginTime(userId)) {
            System.err.println("Logon failure");
            return null;
        }
        User user = StorageUtil.getUserMap(userId);
        if (!Optional.ofNullable(user)
                .isPresent()){
            System.err.println("user is not exist");
            return null;
        }
        List<Role> roles = user.getRoles();
        if (!Optional.ofNullable(roles)
                .isPresent() || roles.size() ==0){
            System.err.println("role is not exist");
            return null;
        }
        roles.forEach(System.out::println);
        return null;
    }

    public Object addRoleToUser(HashMap<String, String> params) {
        String roleId = params.get("roleId");
        String userId = params.get("userId");
        if (StringUtil.isEmpty(roleId) || StringUtil.isEmpty(userId)) {
            System.err.println("roleId or userId param is null");
            return null;
        }
        Role role = StorageUtil.getRole(roleId);
        if (!Optional.ofNullable(role)
                .isPresent()) {
            System.err.println("role is not exist");
            return null;
        }
        User user = StorageUtil.getUserMap(userId);
        if (!Optional.ofNullable(user)
                .isPresent()) {
            System.err.println("user is not exist");
            return null;
        }
        List<Role> roles = Optional.ofNullable(user.getRoles())
                .orElseGet(ArrayList::new);
        for (Role r : roles) {
            if (r.getRoleId().equals(roleId)){
                return "The user already has the role";
            }
        }
        roles.add(role);
        user.setRoles(roles);
        HashMap<String, User> userMap = StorageUtil.getUserMap();
        userMap.put(userId,user);
        System.out.println("add role to user is success");
        return "add role to user is success";
    }

}
