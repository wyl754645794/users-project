package org.users.control;

import org.users.strategy.roleImpl.RoleStrategy;
import org.users.strategy.StrategyHandle;
import org.users.strategy.userImpl.UserStrategy;
import org.users.util.StringUtil;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

/**
 * ProjectEntrance
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 9:27 PM:55
 */
public class ProjectUserEntrance {

    private static StrategyHandle userHandle;
    private static Scanner sc;

    public static void initStrategy(){
        sc = new Scanner(System.in);
        userHandle = new StrategyHandle();
        userHandle.addStrategy(new UserStrategy())
                .addStrategy(new RoleStrategy());
    }

    /**
     *  selectable condition: user, role
     *  selectable params: userId,userName,roleId,roleName,token
     *  selectable command: createUser, deleteUser, authenticate, invalidate, checkRole, allRoles,
     *      *  addRoleToUser, createRole, deleteRole
     *  5/4/2022 9:28 PM
     * @return void
     */
    public static void main(String[] args) {
        initStrategy();
        while (true){
            String condition;
            String paramString;
            String command;
            System.out.print("condition: ");
            condition = sc.nextLine();
            System.out.print("command: ");
            command = sc.nextLine();
            /**
             * example
             * input: userId:xxxxx,userName:xxxxx,password:xxxxx,roleId:xxxxx,roleName
             * For method flexibility, parameter names are not fixed,
             * if this parameter is not needed, do not enter it
             */
            System.out.print("params: ");
            paramString = sc.nextLine();
            HashMap<String, String> params = StringUtil.paramsAnalysis(paramString);
            if (!Optional.ofNullable(params)
                    .isPresent()||params.size()==0){
                System.err.println("params is null");
                return;
            }
            userHandle.doStrategy(condition, command, params);
//            System.out.println(doStrategy);
        }
    }

}
