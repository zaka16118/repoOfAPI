package com.yupi.project.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.TokenUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserMapper;
import com.yupi.project.service.UserService;
import com.yupi.yuapicommon.Utils.CookieUtils;
import com.yupi.yuapicommon.constant.CookieConstant;
import com.yupi.yuapicommon.model.entity.User;
import com.yupi.yuapicommon.model.vo.LoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.yupi.project.constant.UserConstant.ADMIN_ROLE;
import static com.yupi.project.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户服务实现类
 *
 * @author yupi
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private TokenUtils tokenUtils;


    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yupi";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 给用户分配accessKey、secretKey
            String accessKey = DigestUtil.md5Hex(SALT + userAccount + RandomUtil.randomNumbers(5));
            String secretKey = DigestUtil.md5Hex(SALT + userAccount + RandomUtil.randomNumbers(8));
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setAccessKey(accessKey);
            user.setSecretKey(secretKey);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user;
    }

//    @Override
//    public BaseResponse checkUserLogin(HttpServletRequest request, HttpServletResponse response) {
//        // 先判断是否已登录
//        Principal userPrincipal = request.getUserPrincipal();
//        if (userPrincipal != null) {
//            String name = userPrincipal.getName();
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            User currentUser = (User) authentication.getPrincipal();
//            if (currentUser == null || currentUser.getId() == null || null == name || !currentUser.getUserAccount().equals(name)) {
//                throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//            }
//            LoginUserVo loginUserVo = new LoginUserVo();
//            BeanUtils.copyProperties(currentUser, loginUserVo);
//            return ResultUtils.success(loginUserVo);
//        } else {
//            Cookie[] cookies = request.getCookies();
//            if (null == cookies || cookies.length == 0) {
//                return ResultUtils.success(null);
//            }
//            String authorization = null;
//            String remember = null;
//            for (Cookie cookie : cookies) {
//                String name = cookie.getName();
//                if (CookieConstant.headAuthorization.equals(name)) {
//                    authorization = cookie.getValue();
//                }
//                if (CookieConstant.autoLoginAuthCheck.equals(name)) {
//                    remember = cookie.getValue();
//                }
//            }
//            if (null == authorization || null == remember) {
//                throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//            }
//            CookieUtils cookieUtils = new CookieUtils();
//            String[] strings = cookieUtils.decodeAutoLoginKey(remember);
//            if (strings.length != 3) {
//                throw new BusinessException(ErrorCode.ILLEGAL_ERROR, "请重新登录");
//            }
//            String sId = strings[0];
//            String sUserAccount = strings[1];
//            JWT jwt = JWTUtil.parseToken(authorization);
//            String id = (String) jwt.getPayload("id");
//            String userAccount = (String) jwt.getPayload("userAccount");
//            if (!sId.equals(id) || !sUserAccount.equals(userAccount)) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "请重新登录");
//            }
//            User byId = this.getById(id);
//            byId.setUserPassword(null);
//            //todo 还未修改User类型
////            String phone = DesensitizedUtil.mobilePhone(byId.getMobile());
////            byId.setMobile(phone);
//            return ResultUtils.success(initUserLogin(byId, response));
//        }
//    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && ADMIN_ROLE.equals(user.getUserRole());
    }


    /**
     * 用户注销
     *
     * @param request
     */
    //TODO  ErrorCode 也应该归入common模块
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    /**
     * 初始化用户登录状态
     * @param user
     */
    private LoginUserVo initUserLogin(UserDetails user, HttpServletResponse response){
        //设置到Security 全局对象中去
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(user,loginUserVo);
        //生成token并存入redis
        String token = tokenUtils.generateToken(String.valueOf(loginUserVo.getId()),loginUserVo.getUserAccount());
        loginUserVo.setToken(token);
        Cookie cookie = new Cookie(CookieConstant.headAuthorization,token);
        cookie.setPath("/");
        cookie.setMaxAge(CookieConstant.expireTime);
        response.addCookie(cookie);
        CookieUtils cookieUtils = new CookieUtils();
        String autoLoginContent = cookieUtils.generateAutoLoginContent(loginUserVo.getId().toString(), loginUserVo.getUserAccount());
        Cookie cookie1 = new Cookie(CookieConstant.autoLoginAuthCheck, autoLoginContent);
        cookie1.setPath("/");
        cookie.setMaxAge(CookieConstant.expireTime);
        response.addCookie(cookie1);
        return loginUserVo;
    }
}




