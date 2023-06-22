package com.sample.practice6;

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
//テストランナーを指定することができるアノテーション、特定のランナーを使用する必要がある時に使う。
@RunWith(SpringRunner.class)
//テストメソッド内で必要なテストケースを実装、実際の実行環境に近い状態でテストを行う。
@SpringBootTest
/*
 * テスト者：崔 徳斌
 * 開始日：2023/6/15
 * 終了日：2023/6/20
 * 更新日：2023/6/20
 * 
 * このクラスは店舗のサービス層をテストする目的とし、データベースはまだ未準備で
 * mapperをモックする対象にして、テスト対象クラスのインスタンスに注入する。
 */
public class MarketStoreServiceMock {
//テスト対象クラスのインスタンスを作成して、定義した対象を作成したインスタンスに自動的に注入する
	@InjectMocks
	private MarketStoreServiceImpl service;
//mapperをモックする対象に定義する
	@Mock
	private MarketStoreMapper mapper;

	/**
	 * 正常終了
	 * 
	 * 検索件数：１件
	 */
//	JUnitやTestNGなどのテストフレームワークで使用されるアノテーション
	@Test
	public void test_select_01() {

		// 検索結果Mock
		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
//mockResultsに引数ををつける
		  MarketStoreBean result = new MarketStoreBean();
		    result.setStoreId("1");
		    result.setStoreName("aaa");
		    result.setPhone("111111");
		    result.setAddress("abcd");
		    result.setStartDay("2023-5-1");
			result.setFinishDay("2023-06-30");
			result.setRegistDay("2023-06-13");
			result.setUpdateDay("2023-06-30");
		    mockResults.add(result);

		// 引数
		MarketStoreListForm  input = new MarketStoreListForm ();
		input.setStoreName("aaa");
		input.setAddress("abcd");
		
		
		
		Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
	
		MarketStoreListForm msf=null;
		
		msf =  service.select(input);
			//項目比較
    		assertEquals("1", msf.getResults().get(0).getStoreId());
    		assertEquals("aaa", msf.getResults().get(0).getStoreName());
     		assertEquals("111111", msf.getResults().get(0).getPhone());
     		assertEquals("abcd", msf.getResults().get(0).getAddress());
     		assertEquals("2023-5-1", msf.getResults().get(0).getStartDay());
     		assertEquals("2023-06-30", msf.getResults().get(0).getFinishDay());
     		assertEquals("2023-06-13", msf.getResults().get(0).getRegistDay());
     		assertEquals("2023-06-30", msf.getResults().get(0).getUpdateDay());
	
	}
	
	/**
	 * 異常終了（検索結果がありません。）
	 * 
	 */
	@Test
	public void test_select_02() {
		List<MarketStoreBean> mockResults = new ArrayList<>();
		when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm form = new MarketStoreListForm();
		 form.setAddress("aaa");
	        form.setStoreName("111");
	        try {
	            // 使われるメゾットを呼び出す
	            service.select(form);
	            
	            //  BusinessExceptionがなければ、異常
	            fail("应该抛出 BusinessException");
	        } catch (BusinessException e) {
	            //  メッセージを比較する
	            assertEquals("検索結果はありません。", e.getMessage());
	        } catch (Exception e) {
	            // 他の異常が出たら失敗。
	            fail("应该抛出 BusinessException");
	        }

	}
	/**
	 * 異常終了（検索結果がありません。）
	 * 
	 */
	@Test
	public void test_select_03() {
		List<MarketStoreBean> mockResults = null;
		when(this.mapper.select(any())).thenReturn(mockResults);
		MarketStoreListForm form = new MarketStoreListForm();
		 form.setAddress("aaa");
	        form.setStoreName("111");
	        try {
	            // 使われるメゾットを呼び出す
	            service.select(form);
	            
	            //  BusinessExceptionがなければ、異常
	            fail("应该抛出 BusinessException");
	        } catch (BusinessException e) {
	            //  メッセージを比較する
	            assertEquals("検索結果はありません。", e.getMessage());
	        } catch (Exception e) {
	            // 他の異常が出たら失敗。
	            fail("应该抛出 BusinessException");
	        }
	}
	
	/**
	 * 正常終了
	 * 
	 * 挿入
	 */
	@Test
	public void test_insert_01() {

		 // Formを作り値を入れる
	    MarketStoreForm form = new MarketStoreForm();
	    form.setStoreName("aaa");
	    form.setAddress("abcd");
	    form.setPhone("09080");
	    form.setStartDay("2000-11-11");
	    form.setFinishDay("2011-11-11");

	    // selectMaxId()の帰り値をモックする。
	    String maxId = "100"; 
	    Mockito.when(mapper.selectMaxId()).thenReturn(maxId);

	    // 使われるメゾットを呼ぶ
	    service.insert(form);

	    // mapper.selectMaxId() が使われたかどうかを調べる
	    Mockito.verify(mapper, Mockito.times(1)).selectMaxId();

	    // 挿入したMarketStoreBeanの値を予想値と比較する
	    ArgumentCaptor<MarketStoreBean> argument = ArgumentCaptor.forClass(MarketStoreBean.class);
	    Mockito.verify(mapper).insert(argument.capture());
	    MarketStoreBean insertedBean = argument.getValue();

	    assertEquals("101", insertedBean.getStoreId()); 
	    assertEquals("aaa", insertedBean.getStoreName());
	    assertEquals("abcd", insertedBean.getAddress());
	    assertEquals("09080", insertedBean.getPhone());
	    assertEquals("2000-11-11", insertedBean.getStartDay());
	    assertEquals("2011-11-11", insertedBean.getFinishDay());
	

	}
	/**
	 * 正常終了
	 * 店舗情報検索 
	 * 
	 * 検索結果
	 */
	@Test
	public void test_editInit_OK() {

		    // select() メゾットの帰り値をモックする
		    List<MarketStoreBean> searchResults = new ArrayList<>();
		    MarketStoreBean result = new MarketStoreBean();
		    result.setStoreId("1");
		    result.setStoreName("aaa");
		    result.setAddress("abcd");
		    result.setPhone("09080");
		    result.setStartDay("2023-07-20");
		    result.setFinishDay("2023-12-30");
		    searchResults.add(result);

		    Mockito.when(mapper.select(any())).thenReturn(searchResults);
		 // MarketStoreForm を作り値を入れる
		    MarketStoreForm form = new MarketStoreForm();
		    form.setStoreId("1");
		    // 使われるメゾットを呼ぶ
		    MarketStoreForm resultForm = service.editInit(form);

		    //帰り値と予想値を比較
		    assertNotNull(resultForm);
		    assertEquals("1", resultForm.getStoreId());
		    assertEquals("aaa", resultForm.getStoreName());
		    assertEquals("abcd", resultForm.getAddress());
		    assertEquals("09080", resultForm.getPhone());
		    assertEquals("2023-07-20", resultForm.getStartDay());
		    assertEquals("2023-12-30", resultForm.getFinishDay());
		}

	
	/**
	 * 正常終了
	 * 
	 * 更新
	 */ 
	@Test
	public void test_update_OK() {
		 // MarketStoreForm を作り値を入れる
        MarketStoreForm form = new MarketStoreForm();
        form.setStoreId("1");
        form.setStoreName("bbb");
        form.setAddress("nnn");
        form.setPhone("222");
        form.setStartDay("2023-05-20");
        form.setFinishDay("2023-07-20");

        // select() の帰り値をモックする
        List<MarketStoreBean> searchResults = new ArrayList<>();
        MarketStoreBean result = new MarketStoreBean();
        result.setStoreId("1");
        result.setStoreName("aaa");
        result.setAddress("abc");
        result.setPhone("123");
        result.setStartDay("2023-05-20");
        result.setFinishDay("2023-07-20");
        searchResults.add(result);

        Mockito.when(mapper.select(any())).thenReturn(searchResults);

        // 使われるメゾットを呼ぶ
        service.update(form);


        ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);

        Mockito.verify(mapper).update(captor.capture());
        MarketStoreBean capturedBean = captor.getValue();
        assertEquals("1", capturedBean.getStoreId());
        assertEquals("bbb", capturedBean.getStoreName());
        assertEquals("nnn", capturedBean.getAddress());
        assertEquals("222", capturedBean.getPhone());
        assertEquals("2023-05-20", capturedBean.getStartDay());
        assertEquals("2023-07-20", capturedBean.getFinishDay());
    }
    
	
	
	
	/**
	 * 正常終了
	 * 
	 * 削除
	 */
	@Test
	public void test_delete_OK() {

//		削除する店舗IDを１に定義する。
		 String storeId = "1";
		 
//		 テストするメゾットを呼び出す
		 service.delete(storeId);
		 
//		テストされるメゾットが呼び出されたかを検証し、相応した引数を渡す。
		 Mockito.verify(mapper, Mockito.times(1)).delete(any());
//	ArgumentCaptorを使いdeleteメゾットの引数を捕獲する。
		 ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);
//		 捕獲した引数は予期通りになっているかどうかを検証する
		 Mockito.verify(mapper).delete(captor.capture());
		    MarketStoreBean deleteBean = captor.getValue();
		    assertEquals("1", deleteBean.getStoreId());
	}
/*
 * 正常終了
 * 
 * 全削除
 */
	@Test
	public void test_deleteAll_OK() {

//		削除する店舗IDの文字列を定義する。
		 String storeIds = "1,2,3";
		 
//店舗idの文字列を店舗id配列にする
		 String[] delIds = storeIds.split(",");
		
		 //		 テストするメゾットを呼び出す
		 service.deleteAll(storeIds);
		 
//		テストされるメゾットが呼び出されたかを検証し、相応した引数を渡す。
		 Mockito.verify(mapper, Mockito.times(1)).deleteAll(delIds);
//			ArgumentCaptorを使いdeleteallメゾットの引数を捕獲する。
		 ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);
//		 捕獲した引数は予期通りになっているかどうかを検証する
		 Mockito.verify(mapper).deleteAll(captor.capture());
		 String[] deleteBean = captor.getValue();
		 assertArrayEquals(delIds, deleteBean);
	}
/*
 * 正常終了
 * 
 * 社員情報を初期化する
 * 
 * 
 */
	@Test
	public void test_readInit_OK() {
		
	
		    // MarketStoreForm を作り値を入れる
		    MarketStoreForm form = new MarketStoreForm();
		    form.setStoreId("1");

		    // select()メゾットの帰り値をモックする
		    List<MarketStoreBean> searchResults = new ArrayList<>();
		    MarketStoreBean result = new MarketStoreBean();
		    result.setStoreId("1");
		    result.setStoreName("aaa");
		    result.setAddress("abc");
		    result.setPhone("123");
		    result.setStartDay("2023-10-13");
		    result.setFinishDay("2023-11-30");
		    searchResults.add(result);

		    Mockito.when(mapper.select(any())).thenReturn(searchResults);

		    // 使われるメゾットを呼ぶ
		    MarketStoreForm resultForm = service.readInit(form);

		    // 予想値と比較
		    assertEquals("1", resultForm.getStoreId());
		    assertEquals("aaa", resultForm.getStoreName());
		    assertEquals("abc", resultForm.getAddress());
		    assertEquals("123", resultForm.getPhone());
		    assertEquals("2023-10-13", resultForm.getStartDay());
		    assertEquals("2023-11-30", resultForm.getFinishDay());
	}

	
	/*private MarketStoreBean createBean(String key) {
		MarketStoreBean mockBean = new MarketStoreBean();
		mockBean.setStoreId(key);
		mockBean.setStoreName("東京支店" + key);
		mockBean.setAddress("aaaaaaa");

		return mockBean;
	
	}
	*/
}