# user-project

本项目使用JDK8, 无依赖其他jar包, gradle搭建

**程序入口main方法: org.users.control.ProjectUserEntrance, 项目开箱即用, 导入项目后启动main方法就可以进行测试**

参数condition, 可选user, role

参数command, 可选createUser, deleteUser, authenticate, invalidate, checkRole, allRoles,
addRoleToUser, createRole, deleteRole

参数params, 可选userId,userName,roleId,roleName,token,password



其中: condition: role, 对应的command只有createRole, deleteRole. 其余命令均为user



**本项目使用策略命令设计的方式调用方法**

以下为请求示例的传参标准: 

condition: user

command: createUser

params: userId:123,userName:张三,password:321

以上即创建名为张三的user



**对params的声明**: 

1. createUser, 可选userId,userName,password

2. deleteUser, 可选userId

3. authenticate, 可选userId, password

4. invalidate, 可选token

5. checkRole, 可选token,roleId

6. allRoles, 可选token

7. addRoleToUser, 可选userId,roleId

8. createRole, 可选roleId,roleName

9. deleteRole, 可选roleId

   







