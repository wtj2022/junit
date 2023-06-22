package com.sample.practice4;

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

import com.cms.entity.marketstore.MarketstoreBean;
import com.cms.form.marketstore.MarketstoreForm;
import com.cms.form.marketstore.MarketstoreListForm;
import com.cms.mapper.marketstore.MarketstoreMapper;
import com.cms.service.marketstore.MarketstoreServiceImpl;
import com.exception.BusinessException;


//java注解，告知编译器忽略警告
@SuppressWarnings("rawtypes")
//spring框架的运行器
@RunWith(SpringRunner.class)
//测试类注解
@SpringBootTest
public class MockServiceMockTest {

	@InjectMocks
	private MarketstoreServiceImpl service;

	@Mock
	private MarketstoreMapper mapper;

	/**
	 * 第1：販売店　販売店Id検索
	 * select
	 * 検索件数：1件
	 */
	@Test
	public void MarketstoreIDTest_01_OK() {

		// 検索結果Mock
		List<MarketstoreBean> mockResults = new ArrayList<MarketstoreBean>();


		// 引数
		MarketstoreBean paramBean = new MarketstoreBean();
		paramBean.setStoreId("1");
		paramBean.setStoreName("于凯");
		paramBean.setAddress("1160014");
		paramBean.setPhone("09060199939");
		 paramBean.setStartDay("2022-03-01");
		 paramBean.setFinishDay("2022-03-01");
		 mockResults.add(paramBean);
		 //当mapper里select中是任何值的话，返回它
		 Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		 
		 MarketstoreListForm listForm = new MarketstoreListForm();
	    	listForm.setStoreName("秋葉原支店");
	    	listForm.setAddress("qq495370081@gmail.com");

	    	MarketstoreListForm msfrom = null;
		try {
			msfrom = service.select(listForm);
			// 件数比較
			assertEquals(1, mockResults.size());

			
				// 項目比較
			
				assertEquals("1", msfrom.getResults().get(0).getStoreId());
				assertEquals("于凯",msfrom.getResults().get(0).getStoreName());
				assertEquals("1160014", msfrom.getResults().get(0).getAddress());
				assertEquals("09060199939", msfrom.getResults().get(0).getPhone());
				assertEquals("2022-03-01", msfrom.getResults().get(0).getStartDay());
				assertEquals("2022-03-01", msfrom.getResults().get(0).getFinishDay());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 第2：販売店　販売店Id検索
	 * 当查询的东西为0 的情况
	 * 検索件数：1件(※検索結果と条件が不一致です。)
	 */
	@Test
	public void MarketstoreIDTest_02_no() {

		
		    // 模拟查询结果为0的情况
		    List<MarketstoreBean> mockResults = new ArrayList<>();

		    // 引数为0的情况
		    MarketstoreBean paramBean = new MarketstoreBean();
		    mockResults.add(paramBean);

		    // 当调用 mapper 的 select 方法时，返回模拟结果 mockResults
		    Mockito.when(this.mapper.select(any())).thenReturn(mockResults);

		    // 创建 MarketstoreListForm 对象并设置属性值
		    MarketstoreListForm listForm = new MarketstoreListForm();
		    listForm.setStoreName("东京支店");
		    listForm.setAddress("qq495370081@gmail.com");

		    try {
		        // 调用 service 的 select 方法
		        service.select(listForm);

		        // 如果代码执行到这里，说明没有抛出 BusinessException，测试失败
//		        fail("应该抛出 BusinessException");
		    } catch (BusinessException e) {
		        // 捕捉 BusinessException 异常
		        assertEquals("検索結果はありません。", e.getMessage());
		    } 
		}

	/**第3：販売店　販売店Id検索
	 * 異常終了（検索結果がありません。）
	 * 
	 */
	@Test
	public void MarketstoreIDTest_03_no() {

		// 検索結果Mock
		List<MarketstoreBean> mockResults = null;


		 //当mapper里select中是任何值的话，返回它
		 Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		 
		 MarketstoreListForm listForm = new MarketstoreListForm();
	    	listForm.setStoreName("秋葉原支店");
	    	listForm.setAddress("qq495370081@gmail.com");

	    	
		try {
			 service.select(listForm);
			  
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

	/**第4：販売店　販売店新规
	 * 正常終了 insert
	 * 测试正常可以加1的情况
	 */
	@Test
	public void MarketstoreinsertTest_04() {
		// 検索結果Mock
		
		 MarketstoreForm form = new MarketstoreForm();
		 form.setStoreName("于凯");
		 form.setAddress("1160014");
		 form.setPhone("09060199939");
		 form.setStartDay("2022-03-01");
		 form.setFinishDay("2022-03-01");
		 
		// 引数
//		MarketstoreBean input = new MarketstoreBean();
//		input.setStoreId("9");
//		input.setStoreName("于凯");
//		input.setAddress("1160014");
//		input.setPhone("09060199939");
//		input.setStartDay("2022-03-01");
//		input.setFinishDay("2022-03-01");
		 //当调用selectMaxId时返回一个1
			Mockito.when(mapper.selectMaxId()).thenReturn("1");

	       // 把form里的东西存到要测的insert中
			service.insert(form);
			// 利用ArgumentCaptor捕获insert方法参数进行验证 
			ArgumentCaptor<MarketstoreBean> argument = ArgumentCaptor.forClass(MarketstoreBean.class);

			// 利用verify方法验证传递给 insert 方法的参数是否符合预期
			verify(mapper).insert(argument.capture());
			MarketstoreBean capturedBean = argument.getValue();
			
		    // 断言捕获到的参数与预期值是否一致
			assertEquals("2", capturedBean.getStoreId());
			assertEquals("于凯", capturedBean.getStoreName());
			assertEquals("1160014", capturedBean.getAddress());
			assertEquals("09060199939", capturedBean.getPhone());
			assertEquals("2022-03-01", capturedBean.getStartDay());
			assertEquals("2022-03-01", capturedBean.getFinishDay());
		

	}
	
	/**第5 ：販売店　販売店新规
	 *  insert中maxid为null的情况
	 * 错误情况
	 */
	@Test
	public void MarketstoreeditInitTest_05() {

		// 模拟 select() 方法的返回结果
		List<MarketstoreBean> searchResults = new ArrayList<>();
		MarketstoreBean result = new MarketstoreBean();
		result.setStoreId("1");
		result.setStoreName("Test Store");
		result.setAddress("Test Address");
		result.setPhone("Test Phone");
		result.setStartDay("2023-06-13");
		result.setFinishDay("2023-06-30");
		searchResults.add(result);

		Mockito.when(mapper.select(any())).thenReturn(searchResults);

		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketstoreForm form = new MarketstoreForm();
		form.setStoreId("1");

		// 调用被测试的方法
		MarketstoreForm resultForm = service.editInit(form);

		// 验证返回的 MarketStoreForm 对象是否符合预期
		assertEquals("1", resultForm.getStoreId());
		assertEquals("Test Store", resultForm.getStoreName());
		assertEquals("Test Address", resultForm.getAddress());
		assertEquals("Test Phone", resultForm.getPhone());
		assertEquals("2023-06-13", resultForm.getStartDay());
		assertEquals("2023-06-30", resultForm.getFinishDay());
	}
		
	
	/**
	 * 第6：販売店upDate
	 * 　販売店更新画面【データ保存】
	 * 検索件数：1件
	 */ 
	@Test
	public void MarketstoreupDateTest_06() {
		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketstoreForm form = new MarketstoreForm();
		form.setStoreId("1");
		form.setStoreName("Updated Store");
		form.setAddress("Updated Address");
		form.setPhone("Updated Phone");
		form.setStartDay("2023-06-13");
		form.setFinishDay("2023-06-30");

		// 模拟 select() 方法的返回结果
		List<MarketstoreBean> listform = new ArrayList<>();
		MarketstoreBean result = new MarketstoreBean();
		result.setStoreId("1");
		result.setStoreName("Test Store");
		result.setAddress("Test Address");
		result.setPhone("Test Phone");
		result.setStartDay("2023-06-13");
		result.setFinishDay("2023-06-30");
		listform.add(result);

		Mockito.when(mapper.select(any())).thenReturn(listform);

		// 调用被测试的方法
		service.update(form);
		
		// 创建一个 ArgumentCaptor 以捕获 update() 方法的参数
		ArgumentCaptor<MarketstoreBean> captor = ArgumentCaptor.forClass(MarketstoreBean.class);

		// 验证传递给 update() 方法的参数是否符合预期
		Mockito.verify(mapper).update(captor.capture());
		MarketstoreBean capturedBean = captor.getValue();
		assertEquals("1", capturedBean.getStoreId());
		assertEquals("Updated Store", capturedBean.getStoreName());
		assertEquals("Updated Address", capturedBean.getAddress());
		assertEquals("Updated Phone", capturedBean.getPhone());
		assertEquals("2023-06-13", capturedBean.getStartDay());
		assertEquals("2023-06-30", capturedBean.getFinishDay());
	}

	/**
	 * 第7：販売店delete
	 * 　販売店削除画面【データ削除】
	 * 検索件数：1件(※もし値が“9”ある場合)
	 */
	 
	@Test
	public void MarketstoredeleteTest_07() {
		// 定义要删除的販売店ID
		String storeId = "1";
		
		// 调用被测试的方法
		service.delete(storeId);
		
		// 创建一个 ArgumentCaptor 以捕获 delete() 方法的参数
		ArgumentCaptor<MarketstoreBean> captor = ArgumentCaptor.forClass(MarketstoreBean.class);

		// 验证传递给 delete() 方法的参数是否符合预期
		Mockito.verify(mapper).delete(captor.capture());
		MarketstoreBean capturedBean = captor.getValue();
		assertEquals("1", capturedBean.getStoreId());
	}
	/**
	 * 第8：販売店deleteAll
	 * 　販売店削除全画面【データ削除】
	 * 検索件数：1件(※もし値が9個ある場合)
	 */
	@Test
	public void MarketstoredeleteallTest_08() {
		// 定义要删除的多个販売店ID
		String storeIds = "1,2,3,4,5,6,7,8,9";

		// 模拟 split() 方法的返回结果
		String[] delIds = storeIds.split(",");

		// 调用被测试的方法
		service.deleteAll(storeIds);

		// 创建一个 ArgumentCaptor 以捕获 deleteAll() 方法的参数
		ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);

		// 验证传递给 deleteAll() 方法的参数是否符合预期
		Mockito.verify(mapper).deleteAll(captor.capture());
		String[] delallid = captor.getValue();
		assertArrayEquals(delIds, delallid);
	}

	/**
	 * 正常終了readInit
	 * 
	 */
	@Test
	public void test_10_readInit() {
		// 创建一个 MarketStoreForm 对象并设置需要测试的字段
		MarketstoreForm form = new MarketstoreForm();
		form.setStoreId("1");

		// 模拟 select() 方法的返回结果
		List<MarketstoreBean> searchResults = new ArrayList<>();
		MarketstoreBean result = new MarketstoreBean();
		result.setStoreId("1");
		result.setStoreName("Test Store");
		result.setAddress("Test Address");
		result.setPhone("Test Phone");
		result.setStartDay("2023-06-13");
		result.setFinishDay("2023-06-30");
		searchResults.add(result);
		
		Mockito.when(mapper.select(any())).thenReturn(searchResults);
		// 调用被测试的方法
		MarketstoreForm resultForm = service.readInit(form);
		
		// 验证返回的 MarketStoreForm 对象是否符合预期
		assertEquals("1", resultForm.getStoreId());
		assertEquals("Test Store", resultForm.getStoreName());
		assertEquals("Test Address", resultForm.getAddress());
		assertEquals("Test Phone", resultForm.getPhone());
		assertEquals("2023-06-13", resultForm.getStartDay());
		assertEquals("2023-06-30", resultForm.getFinishDay());
	}
	
}