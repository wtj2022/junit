package com.sample.practice4;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

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

@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MarketStoreTest1 { 

	@InjectMocks
	private MarketStoreServiceImpl service;

	@Mock
	private MarketStoreMapper mapper;

	/**
	 * 終了
	 * select方法
	 * 検索件数：1件
	 */
	@Test
	public void test_select_01() {
		
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		 TestResults.add(createBean());
		 
		 //如果調了mapper.select(),就返回到TestResults
		Mockito.when(this.mapper.select(any())).thenReturn(TestResults);

		//創了MarketStoreListForm類的form, 並且放入兩個數據
		MarketStoreListForm form = new MarketStoreListForm();
		form.setStoreName("三月俱樂部");
		form.setAddress("sangatsulove");

		//調用select方法,並且傳form裡面的兩個數據到select方法裡進行操作,返回後復值到markettestBean
		MarketStoreListForm markettestBean = service.select(form);

		assertEquals(1, TestResults.size());
		
		

		// 項目比較(數據少的時候用,多的話請用for循環遍歷)
		assertEquals("sangatsulove", markettestBean.getResults().get(0).getAddress());
		assertEquals("三月俱樂部", markettestBean.getResults().get(0).getStoreName());
		assertEquals("1", markettestBean.getResults().get(0).getStoreId());

		
	}  
	/**
	 * 終了
	 * select方法
	 * 検索件数：1件
	 */	
	
	
	@Test
	public void test_select_02() {
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		 //TestResults.add(createBean());

		Mockito.when(this.mapper.select(any())).thenReturn(TestResults);

		MarketStoreListForm form = new MarketStoreListForm();
		form.setStoreName("三月俱樂部");
		form.setAddress("sangatsulove");

	//因為處理時throw有拋出預期的異常,所以這裡使用try catch去捕獲他並進行單獨處理	
	try {
		MarketStoreListForm markettestBean = service.select(form);

//		assertEquals(1, TestResults.size());
//
//		for (int index = 0; index < TestResults.size(); index++) {
//			// 項目比較
//
//			MarketStoreBean bean = TestResults.get(index);
//			assertEquals(bean.getStoreId(), TestResults.get(index).getStoreId());
//			assertEquals(bean.getStoreName(), TestResults.get(index).getStoreName());
//			assertEquals(bean.getAddress(), TestResults.get(index).getAddress());
		
		
		} catch (Exception e) {
			   e.printStackTrace();
		  }

	}
	/**
	 * 終了
	 * select方法
	 * 検索件数：1件
	 */	
	
	@Test
	public void test_select_03() {
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		 //TestResults.add(createBean());

		Mockito.when(this.mapper.select(any())).thenReturn(null);

		MarketStoreListForm form = new MarketStoreListForm();
		form.setStoreName("三月俱樂部");
		form.setAddress("sangatsulove");

		MarketStoreListForm markettestBean = service.select(form);

		assertEquals(1, TestResults.size());

		for (int index = 0; index < TestResults.size(); index++) {
			// 項目比較 

			MarketStoreBean bean = TestResults.get(index);
			assertEquals(bean.getStoreId(), TestResults.get(index).getStoreId());
			assertEquals(bean.getStoreName(), TestResults.get(index).getStoreName());
			assertEquals(bean.getAddress(), TestResults.get(index).getAddress());
			index++;
		}
	}
	/**
	 * 終了
	 * insert方法
	 * 検索件数：1件
	 */	
	
	@Test
	public void test_insert_04() { 
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		Mockito.when(this.mapper.selectMaxId()).thenReturn("123");
		
		MarketStoreForm form = new MarketStoreForm();
		service.insert(form);
		
		
	}
	/**
	 * 終了
	 * insert方法
	 * 検索件数：1件
	 */		
	
	@Test
	public void test_insert_05() { 
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		Mockito.when(this.mapper.selectMaxId()).thenReturn(null);
		
		MarketStoreForm form = new MarketStoreForm();
		service.insert(form);
		
		
	}
	/**
	 * 終了
	 * editInit方法
	 * 検索件数：1件
	 */		
	
	@Test
	public void test_editInit_06() {  
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		List<MarketStoreBean> searchResults = new ArrayList<MarketStoreBean>();
		MarketStoreBean aBean = new MarketStoreBean();
		aBean.setStoreId("159651");
		aBean.setStoreName("賴");
		aBean.setAddress("東京");
		aBean.setPhone("08012345678");
		aBean.setStartDay("20230101");
		aBean.setFinishDay("20230601");
		searchResults.add(aBean);
		
		Mockito.when(this.mapper.select(any())).thenReturn(searchResults);
		
		MarketStoreForm form = new MarketStoreForm();
		service.editInit(form);
	}
	/**
	 * 終了
	 * update方法
	 * 検索件数：1件
	 */		
	
	@Test
	public void test_update_07() {   
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		List<MarketStoreBean> searchResults = new ArrayList<MarketStoreBean>();
		MarketStoreBean aBean = new MarketStoreBean();
		aBean.setStoreId("159651");
		aBean.setStoreName("賴");
		aBean.setAddress("東京");
		aBean.setPhone("08012345678");
		aBean.setStartDay("20230101");
		aBean.setFinishDay("20230601");
		searchResults.add(aBean);
		
		Mockito.when(this.mapper.select(any())).thenReturn(searchResults);
		
		MarketStoreForm form = new MarketStoreForm();
		service.update(form);
	}
	/**
	 * 終了
	 * delete方法
	 * 検索件数：1件
	 */		
	
	@Test
	public void test_delete_08() {    

		String storeId = "123";
		
	    service.delete(storeId);
	    
	    ArgumentCaptor<MarketStoreBean> s = ArgumentCaptor.forClass(MarketStoreBean.class);
	    
	    Mockito.verify(mapper).delete(s.capture());
	    
	    MarketStoreBean bean = s.getValue();
	    assertEquals("1",bean.getStoreId());
	}
	/**
	 * 終了
	 * deleteAll方法
	 * 検索件数：1件
	 */		
	
	@Test
	public void test_deleteAll_09() {    
		

		String storeId = "1,2,3";
	
	    service.deleteAll(storeId);
	    

	}
	/**
	 * 終了
	 * readInit方法
	 * 検索件数：1件
	 */		
	 
	@Test
	public void test_readInit_010() {   
		List<MarketStoreBean> TestResults = new ArrayList<MarketStoreBean>();

		List<MarketStoreBean> searchResults = new ArrayList<MarketStoreBean>();
		MarketStoreBean aBean = new MarketStoreBean();
		aBean.setStoreId("159651");
		aBean.setStoreName("賴");
		aBean.setAddress("東京");
		aBean.setPhone("08012345678");
		aBean.setStartDay("20230101");
		aBean.setFinishDay("20230601");
		searchResults.add(aBean);
		
		Mockito.when(this.mapper.select(any())).thenReturn(searchResults);
		
		MarketStoreForm form = new MarketStoreForm();
		service.readInit(form);
	}
	

	
//	@Override
//	public MarketStoreForm readInit(MarketStoreForm form) {
//		
//		// ログイン情報を検索する
//		MarketStoreBean sqlBean = new MarketStoreBean();
//		sqlBean.setStoreId(form.getStoreId());
//
//		List<MarketStoreBean> results = mapper.select(sqlBean);
//		if (!CollectionUtils.isEmpty(results)) {
//		MarketStoreBean result = results.get(0);
//
//		form.setStoreId(result.getStoreId());			
//		form.setStoreName(result.getStoreName());			
//		form.setAddress(result.getAddress());			
//		form.setPhone(result.getPhone());			
//		form.setStartDay(result.getStartDay());			
//		form.setFinishDay(result.getFinishDay());			
//		}
//		return null;
//	}
	
	

	private MarketStoreBean createBean() {
		MarketStoreBean mockBean = new MarketStoreBean();
		mockBean.setStoreId("1");
		mockBean.setStoreName("三月俱樂部");
		mockBean.setAddress("sangatsulove");

		return mockBean;
	}
}