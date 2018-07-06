package ext.test.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import ext.spring.bean.Autowire;
import ext.spring.mvc.Controller;
import ext.spring.mvc.RequestMapping;
import ext.test.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowire
	private UserService userService;

	@RequestMapping("list")
	public Map<String, Map<String, String>> list() {
		return this.userService.userList();
	}

	@RequestMapping("one")
	public void one(PrintWriter out, HttpServletRequest req) {
		out.write(JSON.toJSONString(this.userService.getUser(req.getParameter("id"))));
		out.flush();
		out.close();

	}

}
