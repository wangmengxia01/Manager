/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.bisystem.modules.sys.controller;

import com.bisystem.common.utils.R;
import com.bisystem.modules.sys.entity.SysUserEntity;
import com.bisystem.modules.sys.form.SysLoginForm;
import com.bisystem.modules.sys.service.SysCaptchaService;
import com.bisystem.modules.sys.service.SysUserService;
import com.bisystem.modules.sys.service.SysUserTokenService;
import com.sun.xml.internal.ws.streaming.PrefixFactory;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	/**
	 * 欢迎页面
	 */

	@RequestMapping("/")
	public String  welcome(){
		return  "welcome";
	}
	/**
	 * 验证码
	 */
//	@GetMapping("captcha.jpg")
//	public void captcha(HttpServletResponse response, String uuid)throws IOException {
//		response.setHeader("Cache-Control", "no-store, no-cache");
//		response.setContentType("image/jpeg");
//
//		//获取图片验证码
//		BufferedImage image = sysCaptchaService.getCaptcha(uuid);
//
//		ServletOutputStream out = response.getOutputStream();
//		ImageIO.write(image, "jpg", out);
//		IOUtils.closeQuietly(out);
//	}

	/**
	 * 跳转到登陆页面
	 */
	@RequestMapping("/user/login")
	public String  loginhtml(){
		return  "pages/login.html";
	}
	/**
	 * 登录
	 */
	@RequestMapping("/sys/login")
	@ResponseBody
	public Map<String, Object> login(SysLoginForm form)throws IOException {
//		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
//		if(!captcha){
//			return R.error("验证码不正确");
//		}

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());
         System.out.println("用户信息"+user);
		System.out.println("form信息"+form);
		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}
	
}
