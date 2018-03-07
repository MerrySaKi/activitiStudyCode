package com.yuka.activiti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询demo练习
 */
@RestController
public class Test4controller {

  private static final Logger logger = LoggerFactory.getLogger("Test4controller");
  //创建流程引擎
  ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

  // 得到身份服务组件
  IdentityService identityService = engine.getIdentityService();

  @RequestMapping("/newGroup")
  public void newGroup() {
    // 写入用户数据
    createGroup(identityService, "1", "GroupA", "typeA");
    createGroup(identityService, "2", "GroupB", "typeB");
    createGroup(identityService, "3", "GroupC", "typeC");
    createGroup(identityService, "4", "GroupD", "typeD");
    createGroup(identityService, "5", "GroupE", "typeE");
  }

  /**
   * 查询测试
   * ${params}
   * type: 1: 正序查询
   *       2：反序查询
   */
  @GetMapping("/queryGroup")
  public Map<String, Object> queryGroup(@RequestParam(value = "type") Integer type) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("number", identityService.createGroupQuery().count());
    if (type == 1) {
      // 正序查询
      List<Group> datas = identityService.createGroupQuery().orderByGroupId().asc().list();
      for (Group data : datas) {
        logger.info(data.getId() + "-----" + data.getName() + data.getType());
      }
      map.put("data", datas);
      return map;
    } else if (type == 2) {
      // 反序查询
      List<Group> datas = identityService.createGroupQuery().orderByGroupId().desc().list();
      for (Group data : datas) {
        logger.info(data.getId() + "-----" + data.getName() + data.getType());
      }
      map.put("data", datas);
      return map;
    } else {
      return map;
    }
  }

  /**
  * 查询全部测试
  */
  @GetMapping("/queryAll")
  public List<Group> queryAll() {
    List<Group> datas = identityService.createGroupQuery().list();
    return datas;
  }

  /**
   * 分页查询测试
   * ${params}
   * firstResult: 查询起始序号
   * maxResults：查询结果终止序号
   */
  @GetMapping("/queryGroupPage")
  public List<Group> queryGroupPage(@RequestParam(value = "firstResult") Integer firstResult,
      @RequestParam(value = "maxResults") Integer maxResults) {
    List<Group> datas = identityService.createGroupQuery().listPage(firstResult, maxResults);
    return datas;
  }

  public void createGroup(IdentityService identityService, String id, String name, String type) {
    Group group = identityService.newGroup(id);
    group.setName(name);
    group.setType(type);
    identityService.saveGroup(group);
  }
}
