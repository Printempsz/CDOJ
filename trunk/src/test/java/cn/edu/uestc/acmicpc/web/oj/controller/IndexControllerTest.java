package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.config.WebMVCConfig;
import cn.edu.uestc.acmicpc.web.oj.controller.index.IndexController;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Test cases for {@link IndexController}. */
@WebAppConfiguration
@ContextConfiguration(classes = { TestContext.class, WebMVCConfig.class })
public class IndexControllerTest extends ControllerTest {

  @Autowired
  private IndexController indexController;

  @Override
  @BeforeMethod
  public void init() {
    Mockito.reset(departmentService);
    mockMvc = initControllers(indexController);
  }

  @Test
  public void testVisitIndex() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index/index"))
        .andExpect(model().attribute("message", "home page."))
        .andExpect(model().attribute("departmentList", departmentList))
        .andExpect(model().attribute("authenticationTypeList", authenticationTypeList));
  }
}
