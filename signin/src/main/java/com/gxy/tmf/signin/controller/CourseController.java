package com.gxy.tmf.signin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gxy.tmf.signin.model.Course;
import com.gxy.tmf.signin.service.CourseService;
import com.gxy.tmf.signin.util.MessageBean;
import com.gxy.tmf.signin.util.Util;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("course")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@ApiOperation("不带分页查询全部课程信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "课程名称",  dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "week", value = "星期",  dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "startTime", value = "开始时间",  dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "endTime", value = "结束时间",  dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "gradeId", value = "课程id",  dataType = "int", paramType = "query"),
	})
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public ResponseEntity<MessageBean<Course>> getAll(
			@RequestParam(defaultValue = "", value = "name", required = false) String name,
			@RequestParam(defaultValue = "", value = "week", required = false) String week,
			@RequestParam(defaultValue = "", value = "startTime", required = false) String startTime,
			@RequestParam(defaultValue = "", value = "endTime", required = false) String endTime,
			@RequestParam(defaultValue = "", value = "gradeId", required = false) Integer gradeId
			) {
		try {
			MessageBean<Course> response = courseService.findAll(name,week,startTime,endTime,gradeId);
			return new ResponseEntity<MessageBean<Course>>(response, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<MessageBean<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "利用实体添加课程信息")
	@ApiImplicitParam(name = "course", value = "课程实体类", required = true, dataType = "Course", paramType = "body")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<MessageBean<Course>> create(@RequestBody Course course) {
		try {
			if (Util.isEmpty(course)) {
				return new ResponseEntity<MessageBean<Course>>(new MessageBean<Course>("error", "未获取到课程信息"), HttpStatus.OK);
			}
			MessageBean<Course> response = courseService.save(course);
			return new ResponseEntity<MessageBean<Course>>(response, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<MessageBean<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="根据课程id删除课程信息")
	@ApiImplicitParam(name = "courseId", value = "课程信息id", required = true, dataType = "int", paramType = "path")
	@RequestMapping(value="/delete/{courseId}",method=RequestMethod.DELETE)
	public ResponseEntity<MessageBean<Void>> delete(@PathVariable("courseId")Integer courseId){
		try {
			if (Util.isEmpty(courseId)) {
				return new ResponseEntity<MessageBean<Void>>(new MessageBean<Void>("error", "未获取到课程信息"), HttpStatus.OK);
			}
			MessageBean<Void> response = courseService.delete(courseId);
			return new ResponseEntity<MessageBean<Void>>(response, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<MessageBean<Void>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("查询单个课程信息")
	@ApiImplicitParam(name = "courseId", value = "课程Id",  dataType = "int", paramType = "query")
	@RequestMapping(value="/getOne",method=RequestMethod.GET)
	public ResponseEntity<MessageBean<Course>> getOne(
			@RequestParam(defaultValue = "", value = "courseId", required = false) Integer courseId
			) {
		try {
			MessageBean<Course> response = courseService.findOne(courseId);
			return new ResponseEntity<MessageBean<Course>>(response, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<MessageBean<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@ApiOperation(value="根据课程id和课程实体类，更新课程信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name="course",value="课程实体类",required=true,dataType="Course",paramType="body"),
			@ApiImplicitParam(name="courseId",value="课程id",required=true,dataType="int",paramType="query")
	})
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResponseEntity<MessageBean<Course>> update(@RequestBody Course course,@RequestParam("courseId") Integer courseId
			) {
		try {
			MessageBean<Course> response = courseService.update(course, courseId);
			return new ResponseEntity<MessageBean<Course>>(response, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<MessageBean<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
}
