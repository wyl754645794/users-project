package org.users.util;
import org.users.model.Role;
import org.users.model.User;

import java.util.*;

/**
 * StorageUtil
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 9:43 PM:56
 */
public class StorageUtil {

    private final static HashMap<String, User> USER_MAP = new HashMap<>();
    private final static HashMap<String, String> USER_TOKEN = new HashMap<>();
    private final static List<Role> ROLES = new ArrayList<>();

    public static void setUserMap(User user) {
        if (user == null){
            System.err.println("user is exist!");
            return;
        }
        String userId = user.getUserId();
        if (USER_MAP.containsKey(userId)){
            System.err.println("userId is exist!");
            return;
        }
        USER_MAP.put(userId,user);
        System.out.println(user.toString());
    }

    public static User getUserMap(String userId) {
        if (USER_MAP.containsKey(userId)){
            return USER_MAP.get(userId);
        }
        System.err.println("user is not exist");
        return null;
    }

    public static String setToken(String userId){
        String token = StringUtil.getRandomString(32);
        USER_TOKEN.put(userId,token);
        return token;
    }

    public static String getToken(String userId){
        return USER_TOKEN.get(userId);
    }

    public static String getUserIdByToken(String token){
        for (String key : USER_TOKEN.keySet()) {
            String value = USER_TOKEN.get(key);
            if (value.equals(token)){
                return key;
            }
        }
        return null;
    }

    public static void setRole(Role role) {
        for (Role r : ROLES) {
            if(r.getRoleName().equals(role.getRoleName())){
                System.err.println("role is exist!");
                return;
            }
        }
        ROLES.add(role);
    }

    public static Role getRole(String roleId) {
        for (Role r : ROLES) {
            if(r.getRoleId().equals(roleId)){
                System.out.println(r.toString());
                return r;
            }
        }
        return null;
    }

    public static HashMap<String, User> getUserMap() {
        return USER_MAP;
    }

    public static List<Role> getRoles() {
        return ROLES;
    }

    public static HashMap<String, String> getUserToken() {
        return USER_TOKEN;
    }

    public static boolean checkLoginTime(String userId){
        User user = USER_MAP.get(userId);
        if (user.getLastLoginDate() == null ||
                System.currentTimeMillis() - user.getLastLoginDate().getTime() > 7200000) {
            user.setLastLoginDate(null);
            USER_MAP.put(userId,user);
            return false;
        }
        return true;
    }
}
