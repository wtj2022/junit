package com.sample.practice1;

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
import com.exception.BusinessException;

//作者：胡文灏
//创建日期：2023年6月20日
//最后修改：2023年6月20日

@SuppressWarnings("rawtypes")

@RunWith(SpringRunner.class)

@SpringBootTest
public class MarketStoreService_Mock_Test {
	@InjectMocks
	private MarketStoreServiceImpl service;
	@Mock
	private MarketStoreMapper mapper;

	//準備bean數據
	private MarketStoreBean createBean() {
		MarketStoreBean mockBean = new MarketStoreBean();
		mockBean.setStoreId("1");
		mockBean.setStoreName("上野1支店");
		mockBean.setAddress("sdgdfhrdOOO");
		mockBean.setPhone("07023186768");
		mockBean.setStartDay("2023-05-17");
		mockBean.setFinishDay("2023-05-17");
		mockBean.setRegistDay("2023-05-17");

		return mockBean;
	}

	// モックの値をBeanからFormに変換する
	public void copyBeanToForm(MarketStoreBean bean, MarketStoreForm form) {
		form.setStoreId(bean.getStoreId());
		form.setStoreName(bean.getStoreName());
		form.setAddress(bean.getAddress());
		form.setPhone(bean.getPhone());
		form.setStartDay(bean.getStartDay());
		form.setFinishDay(bean.getFinishDay());
	}

	/*
	 * ケース：No1 検索機能（検索結果あり）
	 * 販売店名入力：空
	 * アドレス入力；空
	 */
	@Test
	public void test_select_01() {
		// 検索結果
		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
		mockResults.add(createBean());		
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm mockform = new MarketStoreListForm();
		MarketStoreListForm form = null;
			form = service.select(mockform);
	    	
	    	// 項目比較
	    	//从form中获取results的第一个索引，并从该索引中获取storeId的值。
	    	assertEquals("1", form.getResults().get(0).getStoreId());
	    	assertEquals("上野1支店", form.getResults().get(0).getStoreName());
	    	assertEquals("07023186768", form.getResults().get(0).getPhone());
	    	assertEquals("sdgdfhrdOOO", form.getResults().get(0).getAddress());
	    	assertEquals("2023-05-17", form.getResults().get(0).getStartDay());
	    	assertEquals("2023-05-17", form.getResults().get(0).getFinishDay());
	    	assertEquals("2023-05-17", form.getResults().get(0).getRegistDay());
	}
	/*
	 * ケース：No2 検索機能（検索結果あり）
	 * 販売店名入力：全体
	 * アドレス入力；全体
	 */
	@Test
	public void test_select_02() {
		// 検索結果
				List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
				mockResults.add(createBean());		
				// 设置模拟的Mapper行为
				Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
				MarketStoreListForm mockform = new MarketStoreListForm();
				mockform.setAddress("sdgdfhrdOOO");
				mockform.setStoreName("上野1支店");
				MarketStoreListForm form = null;

					form = service.select(mockform);
			    	
			    	// 項目比較
			    	//从form中获取results的第一个索引，并从该索引中获取storeId的值。
			    	assertEquals("1", form.getResults().get(0).getStoreId());
			    	assertEquals("上野1支店", form.getResults().get(0).getStoreName());
			    	assertEquals("07023186768", form.getResults().get(0).getPhone());
			    	assertEquals("sdgdfhrdOOO", form.getResults().get(0).getAddress());
			    	assertEquals("2023-05-17", form.getResults().get(0).getStartDay());
			    	assertEquals("2023-05-17", form.getResults().get(0).getFinishDay());
			    	assertEquals("2023-05-17", form.getResults().get(0).getRegistDay());
			}
	
	/*
	 * ケース：No3 検索機能（検索結果あり）
	 * 販売店名入力：空
	 * アドレス入力；全体
	 */
	@Test
	public void test_select_03() {
		// 検索結果
				List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
				mockResults.add(createBean());		
				// 设置模拟的Mapper行为
				Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
				MarketStoreListForm mockform = new MarketStoreListForm();
				mockform.setAddress("sdgdfhrdOOO");
				mockform.setStoreName("");
				MarketStoreListForm form = null;

					form = service.select(mockform);
			    	
			    	// 項目比較
			    	//从form中获取results的第一个索引，并从该索引中获取storeId的值。
			    	assertEquals("1", form.getResults().get(0).getStoreId());
			    	assertEquals("上野1支店", form.getResults().get(0).getStoreName());
			    	assertEquals("07023186768", form.getResults().get(0).getPhone());
			    	assertEquals("sdgdfhrdOOO", form.getResults().get(0).getAddress());
			    	assertEquals("2023-05-17", form.getResults().get(0).getStartDay());
			    	assertEquals("2023-05-17", form.getResults().get(0).getFinishDay());
			    	assertEquals("2023-05-17", form.getResults().get(0).getRegistDay());
			}

	/*
	 * ケース：No4 検索機能（検索結果あり）
	 * 販売店名入力：空
	 * アドレス入力；一部
	 */
	@Test
	public void test_select_04() {
		// 検索結果
		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
		mockResults.add(createBean());		
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm mockform = new MarketStoreListForm();
		mockform.setAddress("sdgd");
		mockform.setStoreName("");
		MarketStoreListForm form = null;

			form = service.select(mockform);
	    	
	    	// 項目比較
	    	//从form中获取results的第一个索引，并从该索引中获取storeId的值。
	    	assertEquals("1", form.getResults().get(0).getStoreId());
	    	assertEquals("上野1支店", form.getResults().get(0).getStoreName());
	    	assertEquals("07023186768", form.getResults().get(0).getPhone());
	    	assertEquals("sdgdfhrdOOO", form.getResults().get(0).getAddress());
	    	assertEquals("2023-05-17", form.getResults().get(0).getStartDay());
	    	assertEquals("2023-05-17", form.getResults().get(0).getFinishDay());
	    	assertEquals("2023-05-17", form.getResults().get(0).getRegistDay());
	}
	
	/*
	 * ケース：No5 検索機能（検索結果あり）
	 * 販売店名入力：全体	
	 * アドレス入力；空
	 */
	@Test
	public void test_select_05() {
		// 検索結果
				List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
				mockResults.add(createBean());		
				// 设置模拟的Mapper行为
				Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
				MarketStoreListForm mockform = new MarketStoreListForm();
				mockform.setAddress("");
				mockform.setStoreName("上野1支店");
				MarketStoreListForm form = null;

					form = service.select(mockform);
			    	
			    	// 項目比較
			    	//从form中获取results的第一个索引，并从该索引中获取storeId的值。
			    	assertEquals("1", form.getResults().get(0).getStoreId());
			    	assertEquals("上野1支店", form.getResults().get(0).getStoreName());
			    	assertEquals("07023186768", form.getResults().get(0).getPhone());
			    	assertEquals("sdgdfhrdOOO", form.getResults().get(0).getAddress());
			    	assertEquals("2023-05-17", form.getResults().get(0).getStartDay());
			    	assertEquals("2023-05-17", form.getResults().get(0).getFinishDay());
			    	assertEquals("2023-05-17", form.getResults().get(0).getRegistDay());
			}
	/*
	 * ケース：No6 検索機能（検索結果あり）
	 * 販売店名入力：一部
	 * アドレス入力；空
	 */
	@Test
	public void test_select_06() {
		// 検索結果
		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
		mockResults.add(createBean());		
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm mockform = new MarketStoreListForm();
		mockform.setAddress("");
		mockform.setStoreName("上野1");
		MarketStoreListForm form = null;

			form = service.select(mockform);
	    	
	    	// 項目比較
	    	//从form中获取results的第一个索引，并从该索引中获取storeId的值。
	    	assertEquals("1", form.getResults().get(0).getStoreId());
	    	assertEquals("上野1支店", form.getResults().get(0).getStoreName());
	    	assertEquals("07023186768", form.getResults().get(0).getPhone());
	    	assertEquals("sdgdfhrdOOO", form.getResults().get(0).getAddress());
	    	assertEquals("2023-05-17", form.getResults().get(0).getStartDay());
	    	assertEquals("2023-05-17", form.getResults().get(0).getFinishDay());
	    	assertEquals("2023-05-17", form.getResults().get(0).getRegistDay());
	}
	/*
	 * ケース：No7 検索機能（検索結果なし）
	 * 販売店名入力：あり（不存在）
	 * アドレス入力；あり（不存在）
	 */
	@Test
	public void test_select_07() {
		// 検索結果
		List<MarketStoreBean> mockResults = new ArrayList<>();
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm form = new MarketStoreListForm();
		form.setAddress("5555555");
		form.setStoreName("广州店");
		try {
			// 传递form给service.select()方法
			service.select(form);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}
	
	/*
	 * ケース：No8 検索機能（検索結果なし）
	 * 販売店名入力：あり（不存在）
	 * アドレス入力；空
	 */
	@Test
	public void test_select_08() {
		// 検索結果
		List<MarketStoreBean> mockResults = new ArrayList<>();
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm form = new MarketStoreListForm();
		form.setAddress("");
		form.setStoreName("广州店");
		try {
			// 传递form给service.select()方法
			service.select(form);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}

	/*
	 * ケース：No9 検索機能（検索結果なし）
	 * 販売店名入力：空
	 * アドレス入力；あり（不存在）
	 */
	@Test
	public void test_select_09() {
		// 検索結果
		List<MarketStoreBean> mockResults = new ArrayList<>();
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm form = new MarketStoreListForm();
		form.setAddress("5555555");
		form.setStoreName("");
		try {
			// 传递form给service.select()方法
			service.select(form);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}
	
	/*
	 * ケース：No10 登録機能
	 */
	@Test
	public void test_insert_01() throws Exception {
		//準備數據
		MarketStoreBean bean = createBean();
		String mockid = "0";
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.selectMaxId()).thenReturn(mockid);
		// 传递mockform给service.insert()方法
		MarketStoreForm insertform = new MarketStoreForm();
		copyBeanToForm(bean,insertform);
		service.insert(insertform);

        // 验证 mapper.insert() 方法是否被调用了一次
        Mockito.verify(mapper, Mockito.times(1)).insert(any());
        
        // 创建一个 ArgumentCaptor 以捕获 insert() 方法的参数
        ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);
        // 验证传递给 insert() 方法的参数是否符合预期
        Mockito.verify(mapper).insert(captor.capture());
        //通过captor.getValue()方法获取捕获的参数值。
        MarketStoreBean capturedBean = captor.getValue();

		//項目比較
		assertEquals("1", capturedBean.getStoreId());
		assertEquals("上野1支店", capturedBean.getStoreName());
		assertEquals("sdgdfhrdOOO", capturedBean.getAddress());
		assertEquals("07023186768", capturedBean.getPhone());
		assertEquals("2023-05-17", capturedBean.getStartDay());
		assertEquals("2023-05-17", capturedBean.getFinishDay());
	}

	/*
	 * ケース：No11 削除機能
	 */
	@Test
	public void test_delete_01() {
		//準備數據
		MarketStoreBean bean = createBean();
		String storeId = bean.getStoreId();
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.delete(bean)).thenReturn(1);

		// 传递storeId给service.delete()方法
		service.delete(storeId);
		//為檢查是否刪除成功再執行一次檢索
		MarketStoreListForm form = new MarketStoreListForm();
		form.setAddress("上野1支店");
		form.setStoreName("sdgdfhrdOOO");
		try {
			service.select(form);
		} catch (Exception e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}

	}

	/*
	 * ケース：No12 全削除機能
	 */
	@Test
	public void test_deleteall_01() {
		//準備數據
		MarketStoreBean mockBean = new MarketStoreBean();
		mockBean.setStoreId("1");
		mockBean.setStoreName("上野1支店");
		mockBean.setAddress("sdgdfhrdOO1");
		mockBean.setPhone("07023186768");
		mockBean.setStartDay("2023-05-17");
		mockBean.setFinishDay("2023-05-17");
		mockBean.setStoreId("2");
		mockBean.setStoreName("上野2支店");
		mockBean.setAddress("sdgdfhrdOO2");
		mockBean.setPhone("07023186768");
		mockBean.setStartDay("2023-05-17");
		mockBean.setFinishDay("2023-05-17");
		mockBean.setStoreId("3");
		mockBean.setStoreName("上野3支店");
		mockBean.setAddress("sdgdfhrdOO3");
		mockBean.setPhone("07023186768");
		mockBean.setStartDay("2023-05-17");
		mockBean.setFinishDay("2023-05-17");
		String Ids = "1,2,3";
		// 设置模拟的Mapper行为
		Mockito.when(this.mapper.deleteAll(any())).thenReturn(1);
		MarketStoreListForm form = new MarketStoreListForm();
		// 传递Ids给service.deleteAll()方法
		service.deleteAll(Ids);
		try {
			service.select(form);
		} catch (Exception e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}

	}

	/*
	 * ケース：No13 更新機能
	 */
	@Test
	public void test_update_01() {
		//準備新數據
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("2");
		form.setStoreName("上野2支店");
		form.setAddress("sdgdfhrdOO2");
		form.setPhone("07023186762");
		form.setStartDay("2023-05-20");
		form.setFinishDay("2023-05-20");
		List<MarketStoreBean> databean = new ArrayList<>();
		//準備已有數據
		MarketStoreBean bean = new MarketStoreBean();
		bean.setStoreId("2");
		bean.setStoreName("上野1支店");
		bean.setAddress("sdgdfhrdOO1");
		bean.setPhone("07023186761");
		bean.setStartDay("2023-05-10");
		bean.setFinishDay("2023-05-10");
		bean.setRegistDay("2023-05-10");
		databean.add(bean);
		// 设置模拟的Mapper行为
		Mockito.when(mapper.select(any())).thenReturn(databean);
		// 传递form给service.update()方法
		service.update(form);
		//ArgumentCaptor捕获方法调用时传递的参数
		ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);
		//验证mapper的update方法是否被调用，参数為captor.capture()方法
		Mockito.verify(mapper).update(captor.capture());
		//captor.getValue()将返回最近一次调用mapper.update()方法时传递的参数值，即MarketStoreBean
		MarketStoreBean capturedBean = captor.getValue();
		//項目比較
		assertEquals("2", capturedBean.getStoreId());
		assertEquals("上野2支店", capturedBean.getStoreName());
		assertEquals("sdgdfhrdOO2", capturedBean.getAddress());
		assertEquals("07023186762", capturedBean.getPhone());
		assertEquals("2023-05-20", capturedBean.getStartDay());
		assertEquals("2023-05-20", capturedBean.getFinishDay());
	}

	/*
	 * ケース：No14 参照機能
	 */
	@Test
	public void test_read_01() {
		//準備數據
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("1");
		List<MarketStoreBean> Results = new ArrayList<>();
		MarketStoreBean result = createBean();
		Results.add(result);
		// 设置模拟的Mapper行为
		Mockito.when(mapper.select(any())).thenReturn(Results);
		// 传递form给service.readInit()方法
		MarketStoreForm resultForm = service.readInit(form);
		//验证mapper的select方法是否被调用,参数為任意类型
		Mockito.verify(mapper, Mockito.times(1)).select(any());
		//項目比較
		assertEquals("1", resultForm.getStoreId());
		assertEquals("上野1支店", resultForm.getStoreName());
		assertEquals("sdgdfhrdOOO", resultForm.getAddress());
		assertEquals("07023186768", resultForm.getPhone());
		assertEquals("2023-05-17", resultForm.getStartDay());
		assertEquals("2023-05-17", resultForm.getFinishDay());
	}

	/*
	 * ケース：No15 編集機能
	 */
	@Test
	public void test_edit_01() {
		//準備數據
				MarketStoreForm form = new MarketStoreForm();
				form.setStoreId("1");
				List<MarketStoreBean> Results = new ArrayList<>();
				MarketStoreBean result = createBean();
				Results.add(result);
				// 设置模拟的Mapper行为
				Mockito.when(mapper.select(any())).thenReturn(Results);
				// 传递form给service.editInit()方法
				MarketStoreForm resultForm = service.editInit(form);
				//验证mapper的select方法是否被调用,参数為任意类型
				Mockito.verify(mapper, Mockito.times(1)).select(any());
				//項目比較
				assertEquals("1", resultForm.getStoreId());
				assertEquals("上野1支店", resultForm.getStoreName());
				assertEquals("sdgdfhrdOOO", resultForm.getAddress());
				assertEquals("07023186768", resultForm.getPhone());
				assertEquals("2023-05-17", resultForm.getStartDay());
				assertEquals("2023-05-17", resultForm.getFinishDay());
	}
}
