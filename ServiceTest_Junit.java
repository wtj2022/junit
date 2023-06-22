package com.sample.practice5MarketStore_ServiceTest_Junit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cms.entity.marketstore.MarketStoreBean;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.mapper.marketstore.MarketStoreMapper;
import com.cms.service.marketstore.MarketStoreServiceImpl;

//指定运行测试的测试运行器。在这里，SpringRunner.class 是 Spring Boot 提供的测试运行器，用于在测试期间启动 Spring 容器。
@RunWith(SpringRunner.class)
//指定当前类是一个 Spring Boot 测试类。它告诉 Spring Boot 创建一个测试环境，并加载应用程序的上下文。

@SpringBootTest
public class ServiceTest_Junit {
	// 标记 service 对象，用于自动将 MarketStoreServiceImpl 类的实例注入到该对象中。
	@InjectMocks
	private MarketStoreServiceImpl service;

	// 标记了 mapper 对象，表示它是一个模拟对象，用于模拟 MarketStoreMapper 类的行为。
	@Mock
	private MarketStoreMapper mapper;

	/**
	 * 正常終了
	 * 
	 *
	 */
	@Test
	public void test_01_select() {
		// 创建·一个·listbean 用于·接收 mapper。select的信息
		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
		// 创建一个list里面的bean 用于给bean 赋值
		MarketStoreBean result = new MarketStoreBean();
		// 给bean赋值·
		result.setAddress("1");
		result.setFinishDay("2");
		result.setPhone("3");
		result.setRegistDay("4");
		result.setStartDay("5");
		result.setStoreName("6");
		result.setUpdateDay("7");
		// 把赋值后·的·bean返回给List
		mockResults.add(result);
		// 当调用select的方法运行mapper的时候返刚刚做好的list数据
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		// 当select方法被调用时传入我们准备好的数据。
		// 我们准备好的数据要放在MarketStoreListForm里面所以得给他new一下来进行数据的放入·。
		MarketStoreListForm marketStoreListForm = new MarketStoreListForm();
		// 因为实现类有两个数据判断所以给刚才new出来的MarketStoreListForm放这两条数据
		marketStoreListForm.setAddress("7");
		marketStoreListForm.setStoreName("7");
		// new 一个form 用于接收实现类·里·return的·form

		MarketStoreListForm form = new MarketStoreListForm();
		form = service.select(marketStoreListForm);

		// form的list里面有一个bean ，bean里面有我们想要的数据·。
		// 为什么get（0）因为在集合list里面只有一个bean 所以得用索引0去get
		assertEquals("1", form.getResults().get(0).getAddress());
		assertEquals("2", form.getResults().get(0).getFinishDay());
		assertEquals("3", form.getResults().get(0).getPhone());
		assertEquals("4", form.getResults().get(0).getRegistDay());
		assertEquals("5", form.getResults().get(0).getStartDay());
		assertEquals("6", form.getResults().get(0).getStoreName());
		assertEquals("7", form.getResults().get(0).getUpdateDay());
	}
	
}