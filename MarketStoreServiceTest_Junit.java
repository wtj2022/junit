/**
     * 
     * Junit
     * 
     * 要测试的Service
     * MarketStoreServiceImpl
     * 
     */
package com.sample.practice5MarketStore_ServiceTest_Junit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cms.entity.marketstore.MarketStoreBean;
import com.cms.form.marketstore.MarketStoreForm;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.mapper.marketstore.MarketStoreMapper;
import com.cms.service.marketstore.MarketStoreServiceImpl;
import com.exception.BusinessException;

//指定运行测试的测试运行器。在这里，SpringRunner.class 是 Spring Boot 提供的测试运行器，用于在测试期间启动 Spring 容器。
@RunWith(SpringRunner.class)
//指定当前类是一个 Spring Boot 测试类。它告诉 Spring Boot 创建一个测试环境，并加载应用程序的上下文。
aa
@SpringBootTest
public class MarketStoreServiceTest_Junit {
	// 标记 service 对象，用于自动将 MarketStoreServiceImpl 类的实例注入到该对象中。
	@InjectMocks
	private MarketStoreServiceImpl service;

	// 标记了 mapper 对象，表示它是一个模拟对象，用于模拟 MarketStoreMapper 类的行为。
	@Mock
	private MarketStoreMapper mapper;

	/** 
	 * 正常終了
	 * 
	 * 検索件数：2件
	 */
	@Test
	public void test_01_select() {

		// 検索結果Mock
		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
		MarketStoreBean result = new MarketStoreBean();
		result.setStoreId("1");
		result.setStoreName("Test Store");
		result.setPhone("Test Phone");
		result.setAddress("Test Address");
		result.setStartDay("2023-5-1");
		result.setFinishDay("2023-06-30");
		result.setRegistDay("2023-06-13");
		result.setUpdateDay("2023-06-30");
		mockResults.add(result);

		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);

		MarketStoreListForm listForm = new MarketStoreListForm();
		listForm.setStoreName("上野1支店");
		listForm.setAddress("Test Address");

		MarketStoreListForm form = null;
		try {
			form = service.select(listForm);
			// 件数比較
			assertEquals(1, mockResults.size());

			// 項目比較
			assertEquals("1", form.getResults().get(0).getStoreId());
			assertEquals("Test Store", form.getResults().get(0).getStoreName());
			assertEquals("Test Phone", form.getResults().get(0).getPhone());
			assertEquals("Test Address", form.getResults().get(0).getAddress());
			assertEquals("2023-5-1", form.getResults().get(0).getStartDay());
			assertEquals("2023-06-30", form.getResults().get(0).getFinishDay());
			assertEquals("2023-06-13", form.getResults().get(0).getRegistDay());
			assertEquals("2023-06-30", form.getResults().get(0).getUpdateDay());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 異常終了（検索結果がありません。）
	 * 
	 * 模拟搜索结果为空的情况
	 */
	@Test
	public void test_02_select() {
		// 模拟搜索结果为空的情况
		List<MarketStoreBean> mockResults = new ArrayList<>();

		// 设置模拟的Mapper行为
		Mockito.when(mapper.select(any())).thenReturn(mockResults);

		// 创建一个 MarketStoreListForm 对象并设置需要测试的字段
		MarketStoreListForm form = new MarketStoreListForm();
		form.setAddress("Some Address");
		form.setStoreName("Some Store Name");

		try {
			// 调用被测试的方法
			service.select(form);

			// 如果代码执行到这里，说明没有抛出 BusinessException，测试失败
			fail("应该抛出 BusinessException");
		} catch (BusinessException e) {
			// 捕捉 BusinessException 异常
			assertEquals("検索結果はありません。", e.getMessage());

		} catch (Exception e) {
			// 如果抛出了其他类型的异常，测试失败
			fail("应该抛出 BusinessException");
		}
	}

	/**
	 * 異常終了（検索結果がありません。）
	 * 
	 * 模拟搜索结果为null的情况
	 */
	@Test
	public void test_03_select() {
		// 模拟搜索结果为空的情况
		List<MarketStoreBean> mockResults = null;

		// 设置模拟的Mapper行为
		Mockito.when(mapper.select(any())).thenReturn(mockResults);

		// 创建一个 MarketStoreListForm 对象并设置需要测试的字段
		MarketStoreListForm form = new MarketStoreListForm();
		form.setAddress("Some Address");
		form.setStoreName("Some Store Name");

		try {
			// 调用被测试的方法
			service.select(form);

			// 如果代码执行到这里，说明没有抛出 BusinessException，测试失败
			fail("应该抛出 BusinessException");
		} catch (BusinessException e) {
			// 捕捉 BusinessException 异常
			assertEquals("検索結果はありません。", e.getMessage());
		} catch (Exception e) {
			// 如果抛出了其他类型的异常，测试失败
			fail("应该抛出 BusinessException");
		}
	}

	/**
	 * 正常終了insert
	 * 
	 * 测试MaxId()有值的情况
	 */
	@Test
	public void test_04_insert() {
		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreName("Test Store");
		form.setAddress("Test Address");
		form.setPhone("Test Phone");
		form.setStartDay("2023-06-13");
		form.setFinishDay("2023-06-30");

		// 模拟 selectMaxId() 方法的返回值为 "10"
		Mockito.when(mapper.selectMaxId()).thenReturn("10");

		// 调用被测试的方法
		service.insert(form);

		// 创建一个 ArgumentCaptor 以捕获 insert() 方法的参数
		ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);
		verify(mapper).insert(captor.capture());
		MarketStoreBean capturedBean = captor.getValue();

		assertEquals("11", capturedBean.getStoreId());
		assertEquals("Test Store", capturedBean.getStoreName());
		assertEquals("Test Address", capturedBean.getAddress());
		assertEquals("Test Phone", capturedBean.getPhone());
		assertEquals("2023-06-13", capturedBean.getStartDay());
		assertEquals("2023-06-30", capturedBean.getFinishDay());
	}

	/**
	 * 正常終了insert
	 * 
	 * 测试MaxId()为null的情况
	 */
	@Test
	public void test_05_insert() {
		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreName("Test Store");
		form.setAddress("Test Address");
		form.setPhone("Test Phone");
		form.setStartDay("2023-06-13");
		form.setFinishDay("2023-06-30");

		// 模拟 selectMaxId() 方法的返回值为 null
		Mockito.when(mapper.selectMaxId()).thenReturn(null);

		// 调用被测试的方法
		service.insert(form);

		// 创建一个 ArgumentCaptor 以捕获 insert() 方法的参数
		ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);

		// 验证传递给 insert() 方法的参数是否符合预期
		verify(mapper).insert(captor.capture());
		MarketStoreBean capturedBean = captor.getValue();
		assertEquals("1", capturedBean.getStoreId());
		assertEquals("Test Store", capturedBean.getStoreName());
		assertEquals("Test Address", capturedBean.getAddress());
		assertEquals("Test Phone", capturedBean.getPhone());
		assertEquals("2023-06-13", capturedBean.getStartDay());
		assertEquals("2023-06-30", capturedBean.getFinishDay());
	}

	/**
	 * 正常終了editInit
	 * 
	 */
	@Test
	public void test_06_editInit() {

		// 模拟 select() 方法的返回结果
		List<MarketStoreBean> searchResults = new ArrayList<>();
		MarketStoreBean result = new MarketStoreBean();
		result.setStoreId("1");
		result.setStoreName("Test Store");
		result.setAddress("Test Address");
		result.setPhone("Test Phone");
		result.setStartDay("2023-06-13");
		result.setFinishDay("2023-06-30");
		searchResults.add(result);

		Mockito.when(mapper.select(any())).thenReturn(searchResults);

		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("1");

		// 调用被测试的方法
		MarketStoreForm resultForm = service.editInit(form);

		// 验证返回的 MarketStoreForm 对象是否符合预期
		assertEquals("1", resultForm.getStoreId());
		assertEquals("Test Store", resultForm.getStoreName());
		assertEquals("Test Address", resultForm.getAddress());
		assertEquals("Test Phone", resultForm.getPhone());
		assertEquals("2023-06-13", resultForm.getStartDay());
		assertEquals("2023-06-30", resultForm.getFinishDay());
	}

	/**
	 * 正常終了update
	 * 
	 */
	@Test
	public void test_07_update() {
		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("1");
		form.setStoreName("Updated Store");
		form.setAddress("Updated Address");
		form.setPhone("Updated Phone");
		form.setStartDay("2023-06-13");
		form.setFinishDay("2023-06-30");

		// 模拟 select() 方法的返回结果
		List<MarketStoreBean> searchResults = new ArrayList<>();
		MarketStoreBean result = new MarketStoreBean();
		result.setStoreId("1");
		result.setStoreName("Test Store");
		result.setAddress("Test Address");
		result.setPhone("Test Phone");
		result.setStartDay("2023-06-13");
		result.setFinishDay("2023-06-30");
		searchResults.add(result);

		Mockito.when(mapper.select(any())).thenReturn(searchResults);

		// 调用被测试的方法
		service.update(form);
		
		// 创建一个 ArgumentCaptor 以捕获 update() 方法的参数
		ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);

		// 验证传递给 update() 方法的参数是否符合预期
		Mockito.verify(mapper).update(captor.capture());
		MarketStoreBean capturedBean = captor.getValue();
		assertEquals("1", capturedBean.getStoreId());
		assertEquals("Updated Store", capturedBean.getStoreName());
		assertEquals("Updated Address", capturedBean.getAddress());
		assertEquals("Updated Phone", capturedBean.getPhone());
		assertEquals("2023-06-13", capturedBean.getStartDay());
		assertEquals("2023-06-30", capturedBean.getFinishDay());
	}

	/**
	 * 正常終了delete
	 * 
	 */
	@Test
	public void test_08_delete() {
		// 定义要删除的販売店ID
		String storeId = "1";
		
		// 调用被测试的方法
		service.delete(storeId);
		
		// 创建一个 ArgumentCaptor 以捕获 delete() 方法的参数
		ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);

		// 验证传递给 delete() 方法的参数是否符合预期
		Mockito.verify(mapper).delete(captor.capture());
		MarketStoreBean capturedBean = captor.getValue();
		assertEquals("1", capturedBean.getStoreId());
	}

	/**
	 * 正常終了deleteAll
	 * 
	 */
	@Test
	public void test_09_deleteAll() {
		// 定义要删除的多个販売店ID
		String storeIds = "1,2,3";

		// 模拟 split() 方法的返回结果
		String[] delIds = storeIds.split(",");

		// 调用被测试的方法
		service.deleteAll(storeIds);

		// 创建一个 ArgumentCaptor 以捕获 deleteAll() 方法的参数
		ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);

		// 验证传递给 deleteAll() 方法的参数是否符合预期
		Mockito.verify(mapper).deleteAll(captor.capture());
		String[] capturedIds = captor.getValue();
		assertArrayEquals(delIds, capturedIds);
	}

	/**
	 * 正常終了readInit
	 * 
	 */
	@Test
	public void test_10_readInit() {
		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("1");

		// 模拟 select() 方法的返回结果
		List<MarketStoreBean> searchResults = new ArrayList<>();
		MarketStoreBean result = new MarketStoreBean();
		result.setStoreId("1");
		result.setStoreName("Test Store");
		result.setAddress("Test Address");
		result.setPhone("Test Phone");
		result.setStartDay("2023-06-13");
		result.setFinishDay("2023-06-30");
		searchResults.add(result);
		
		Mockito.when(mapper.select(any())).thenReturn(searchResults);
		// 调用被测试的方法
		MarketStoreForm resultForm = service.readInit(form);
		
		// 验证返回的 MarketStoreForm 对象是否符合预期
		assertEquals("1", resultForm.getStoreId());
		assertEquals("Test Store", resultForm.getStoreName());
		assertEquals("Test Address", resultForm.getAddress());
		assertEquals("Test Phone", resultForm.getPhone());
		assertEquals("2023-06-13", resultForm.getStartDay());
		assertEquals("2023-06-30", resultForm.getFinishDay());
	}
}