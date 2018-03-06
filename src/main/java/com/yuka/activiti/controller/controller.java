package com.yuka.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

	// 流程部署测试
	@RequestMapping("/query")
	@ResponseBody
	public String queryCount() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		  .addClasspathResource("processes/test1.bpmn")
		  .deploy();
	   return "查询流程部署数量：" + repositoryService.createDeploymentQuery().count();    
	}

}
