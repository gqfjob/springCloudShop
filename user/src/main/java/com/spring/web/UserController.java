package com.spring.web;

import com.spring.common.model.StatusCode;
import com.spring.domain.model.User;
import com.spring.domain.model.UserAuth;
import com.spring.domain.model.VO.UserRoleVO;
import com.spring.domain.model.response.ObjectDataResponse;
import com.spring.domain.model.response.UserResponse;
import com.spring.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 用户Controller
 * @Author ErnestCheng
 * @Date 2017/5/27.
 */
@RestController
public class UserController {

    Logger logger= Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @ApiOperation(value="添加用户")
    @PostMapping(value="addUser")
    public UserResponse addUser(@RequestBody User user){
        UserResponse userResponse=new UserResponse();
        if(user==null|| "".equals(user.getUserName())||"".equals(user.getPassword())){
            userResponse.setCode(StatusCode.Fail_Code);
            userResponse.setMessage("参数不正确");
        }
        logger.info(user);
        userService.addUser(user);
        return userResponse;
    }
    


    @ApiOperation(value="获得用户")
  //  @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="getUserById",method = RequestMethod.GET)
    public ObjectDataResponse<User> getUserById(@RequestParam Integer userId){
        ObjectDataResponse objectDataResponse=new ObjectDataResponse();
        if(userId==null || userId<1){
            objectDataResponse.setCode(StatusCode.Param_Error);
            objectDataResponse.setMessage("参数不对");
        }
        User user=userService.getUserById(userId);
        if(user==null) {
            objectDataResponse.setCode(StatusCode.Data_Not_Exist);
            objectDataResponse.setMessage("数据不存在");
        }else{
            objectDataResponse.setData(user);
        }
        return objectDataResponse;
    }

    @ApiOperation(value = "获得所有用户")
    @GetMapping(value="listUser")
 //   @PreAuthorize("hasRole('ADMIN')")
    public ObjectDataResponse<List<UserAuth>> listUser(){
        ObjectDataResponse objectDataResponse=new ObjectDataResponse();
        List<UserAuth> userAuthList=userService.listUser();
        if(userAuthList.size()>0){
            objectDataResponse.setData(userAuthList);
        }else{
            objectDataResponse.setMessage("暂无记录");
        }
        return objectDataResponse;
    }


    @ApiOperation(value="获得用户角色列表")
    @PostMapping(value="/listUserRole")
    public UserRoleVO listUserRoleVO(@RequestParam Integer userId){
        return userService.listUserRoleVO(userId);
    }
}
