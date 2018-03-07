package com.yuka.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test3controller {
	private static final Logger logger = LoggerFactory.getLogger("Test3controller");
	// 创建流程引擎
	@Autowired
	private ProcessEngine engine;

	// 得到流程存储服务组件
	@Autowired
	private	RepositoryService repositoryService;

	// 获取运行时服务组件
	@Autowired
	private RuntimeService runtimeService;

	// 获取流程任务组件
	@Autowired
	TaskService taskService;
	
	// 流程部署第一个demo练习
	@RequestMapping("/start")
	@ResponseBody
	public String startprocess() {
		// 创建流程引擎
		engine = ProcessEngines.getDefaultProcessEngine();

		// 得到流程存储服务组件
		repositoryService = engine.getRepositoryService();

		// 获取运行时服务组件
		runtimeService = engine.getRuntimeService();

		// 获取流程任务组件
		taskService = engine.getTaskService();
		repositoryService.createDeployment().addClasspathResource("processes/test3.bpmn").deploy();
		// 启动流程
		runtimeService.startProcessInstanceByKey("HelloWorld");

		// 查询第一个流程
		Task task = taskService.createTaskQuery().singleResult();
		logger.info( "第一个任务完成前，当前任务名称：" + task.getName() + "当前任务Id" + task.getId());
		// 完成第一个任务流程
		taskService.complete(task.getId());

		// 查询第二个任务
		task = taskService.createTaskQuery().singleResult();
		logger.info("第二个任务完成前，当前任务名称：" + task.getName() + "当前任务Id" + task.getId());
		taskService.complete(task.getId());
		task = taskService.createTaskQuery().singleResult();

		// 查询第三个任务
		task = taskService.createTaskQuery().singleResult();
		logger.info("第三个任务完成前，当前任务名称：" + task.getName() + "当前任务Id" + task.getId());
		taskService.complete(task.getId());
		task = taskService.createTaskQuery().singleResult();
		logger.info("流程结束，查询任务：" + task);
		return "运行成功";
	}

}
