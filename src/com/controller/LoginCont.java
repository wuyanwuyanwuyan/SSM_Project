package com.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.dao.LoginMapper;
import com.pojo.Districts;
import com.pojo.Login;
import com.pojo.Userstu;

@Controller
public class LoginCont {
	@Resource
	LoginMapper dao;
	
	
	@RequestMapping("/login")
	public String getlogin(HttpServletRequest request,Login lo,HttpSession session){
			Login list = dao.login(lo);
			if(list==null){
				System.out.println("登录失败");
				session.setAttribute("qq","登录失败");
				return "";
			}else if(list.getUserType()==1){
				System.out.println("进入学生页面");
				System.out.println(list.getStuid());
				session.setAttribute("qq", list);
				session.setAttribute("shen", "学生");
				return "student-index";
			}else if(list.getUserType()==2){
				session.setAttribute("qq", list);
				session.setAttribute("shen", "教师");
				String img = request.getParameter("img");
				request.setAttribute("img", img);
				System.out.println("进入教师页面");
				return "teacher-index";
			}else if(list.getUserType()==3){
				System.out.println("进入超级管理员页面");
				session.setAttribute("shen", "admin");
				return "index";
			}
		return "";
	}
	@RequestMapping(value="/login1",method=RequestMethod.GET,produces="application/json;charset=utf-8;")
	 @ResponseBody
	 public Object getlogin(int ty,String ph,String pa){
		Login list=dao.login1(ty, ph,pa);
		String json = JSONArray.toJSONString(list);
		return json;
	 }
	//查询个人信息
	@RequestMapping(value="/selgeren",method=RequestMethod.GET,produces="application/json;charset=utf-8;")
	 @ResponseBody
	 public Object getListsf(int id){
		Userstu list=dao.stu(id);
		String json = JSONArray.toJSONString(list);
		return json;
	 }
	//判断有没有这名学生
		@RequestMapping(value="/selid",method=RequestMethod.GET,produces="application/json;charset=utf-8;")
		 @ResponseBody
		 public Object getList(int id,int type){
			int list=dao.selid(id,type);
			String json = JSONArray.toJSONString(list);
			return json;
		 }
		//判断这名学生有没有注册过
		@RequestMapping(value="/stuid",method=RequestMethod.GET,produces="application/json;charset=utf-8;")
		 @ResponseBody
		 public Object getLis(int id,int type){
			int list=dao.stuid(id,type);
			String json = JSONArray.toJSONString(list);
			return json;
		 }
		//注册成功后直接跳转学生页面
		@RequestMapping(value="/addlo",method=RequestMethod.GET)
		public ModelAndView getzhuce(Login login){
			ModelAndView and=new ModelAndView();
			dao.addlo(login);
			and.setViewName("login");
			return and;
		}
		//修改密码
		@RequestMapping(value="/updpass",method=RequestMethod.GET,produces="application/json;charset=utf-8;")
		 @ResponseBody
		 public Object updpass(String user,String pass,int id){
			int row=dao.updpasss(user, pass, id);
			String json = JSONArray.toJSONString(row);
			return json;
		 }
		
}
