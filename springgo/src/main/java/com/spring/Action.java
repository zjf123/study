package com.spring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import com.mongodb.gridfs.GridFSDBFile;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.*;

@Controller
@RequestMapping(value = "/spring")
public class Action {
	private static Logger logger = Logger.getLogger(Action.class);
	@Autowired
	Teacher teacher;

	@Autowired
	GridFsOperations operations;

	// spring 支持restful的格式

	@ResponseBody
	@RequestMapping(value = "/rest/{ownerId}.do", method = RequestMethod.GET)
	public String findOwner(@PathVariable String ownerId, Model model,
			HttpServletResponse rep) throws IOException {
		return ownerId;
	}

	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public String testa(Model model, HttpServletResponse rep)
			throws IOException {
		model.addAttribute("abc", "efd");
		model.addAttribute(teacher);
		return "a";
	}

	@ResponseBody
	// 理论上可以@ResponseBody 支持直接返回teacher对象 但是3.2里有问题 我们还是老实返回字符串吧
	@RequestMapping(value = "/testb.do", method = RequestMethod.GET)
	public String testb(Model model, HttpServletResponse rep,
			HttpServletRequest req, String ex) throws IOException {
		// WEB中获得SPRING容器
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(req.getServletContext());
		String s = new JSONObject(wac.getBean(Teacher.class)).toString()
				+ System.getProperty("webName.root");
		logger.info(s);
		return s;
	}

	@ResponseBody
	@RequestMapping(value = "/post.do", method = RequestMethod.POST)
	public String post(Model model, HttpServletResponse rep,
			HttpServletRequest req, String ex) throws IOException {
		return new JSONObject(req.getParameterMap()).toString();
	}

	@RequestMapping(value = "/pic.do")
	public void post2(Model model, HttpServletResponse rep,
			HttpServletRequest req, String ex) throws IOException {
		GridFSDBFile gr = operations.findOne(Query.query(whereFilename().is(
				"1.jpg")));
		System.out.println();
		gr.writeTo(rep.getOutputStream());
	}

}
