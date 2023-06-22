 /*
 * テスト作成者：テイ
 * 開始日：2023/6/15
 * 終了日：2023/6/20
 * 更新日：2023/6/20
 * 
 * 販売店店舗のサービス層をテスト  Mock方法
 */
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

import com.cms.entity.marketstore.MarketstoreBean;
import com.cms.form.marketstore.MarketstoreForm;
import com.cms.form.marketstore.MarketstoreListForm;
import com.cms.mapper.marketstore.MarketStoreMapper;
import com.cms.service.marketstore.MarketstoreServiceImpl;
import com.exception.BusinessException;


@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)  
@SpringBootTest
public class ServiceTest_Mock_Service {
	
	@InjectMocks
	private MarketstoreServiceImpl service;
    @Mock
    private MarketStoreMapper mapper;
    
//    テスト用データ
    private MarketstoreBean createBean() {
    	
    	MarketstoreBean mockBean = new MarketstoreBean();
    	mockBean.setStoreId("1");
    	mockBean.setStoreName("tsetName");
    	mockBean.setAddress("tsetAddress");
    	mockBean.setPhone("07012341234");
    	mockBean.setStartDay("2023-05-20");
    	mockBean.setFinishDay("2023-05-20");
    	
    	return mockBean;
    }
    
    /**
     * 検索正常終了
     * 
     * 検索件数：1件
     */
    @Test
    public void test_select_01() {
    	
    	// 検索結果Mock
    	List<MarketstoreBean> mockResults = new ArrayList<MarketstoreBean>();
    	mockResults.add(createBean());

        //MarketstoreBean Bean = new MarketstoreBean();
    	// Mapperの行動を設置する
    	Mockito.when(this.mapper.select(any())).thenReturn(mockResults);
    	MarketstoreListForm marketStoreListForm = new MarketstoreListForm();
    	
    	try {
    		
    	marketStoreListForm = service.select(new MarketstoreListForm());
    	// 項目比較
        assertEquals(1, marketStoreListForm.getResults().size());
        assertEquals("tsetName", marketStoreListForm.getResults().get(0).getStoreName());
        assertEquals("tsetAddress", marketStoreListForm.getResults().get(0).getAddress());
        assertEquals("07012341234", marketStoreListForm.getResults().get(0).getPhone());
        assertEquals("2023-05-20", marketStoreListForm.getResults().get(0).getStartDay());
        assertEquals("2023-05-20", marketStoreListForm.getResults().get(0).getFinishDay());
        
    	}
    	
    	catch (Exception e) {
    		
			e.printStackTrace();
			
		}
		
    }
    
    /**
     * 検索異常
     * 
     * 検索件数：1件
     */
    @Test
    public void test_select_02()  {
    	
        List<MarketstoreBean> mockResults = new ArrayList<>();

        Mockito.when(mapper.select(any())).thenReturn(mockResults);

        MarketstoreListForm form = new MarketstoreListForm();
        form.setStoreName("tsetName");
        form.setAddress("tsetAddress");
        
        try {
            service.select(form);
         // 他の異常が出たら,テスト失敗。
            fail(" BusinessException");
        } catch (BusinessException e) {
        	 //  メッセージを比較する
            assertEquals("検索結果はありません。", e.getMessage());
        } catch (Exception e) {
        	// 他の異常が出たら,テスト失敗。
            fail(" BusinessException");
        }
    }
    
//    /**
//     * 検索異常終了
//     * 
//     * 検索件数：1件
//     */
//    @Test
//    public void test_select_02_01() {
//        List<MarketstoreBean> mockResults = null;
//
//        Mockito.when(mapper.select(Mockito.any(MarketstoreBean.class))).thenReturn(mockResults);
//
//        MarketstoreListForm form = new MarketstoreListForm();
//
//        try {
//            service.select(form);
//            
//            fail("应该抛出 BusinessException");
//        } catch (BusinessException e) {
//            assertEquals("検索結果はありません。", e.getMessage());
//        } catch (Exception e) {
//            fail("应该抛出 BusinessException");
//        }
//    }
    
    /**
     * 登録正常終了
     * 
     * 検索件数：1件
     */
    @Test
    public void test_insert_01() {
    	 MarketstoreBean bean = createBean();
    	 MarketstoreForm form = new MarketstoreForm(); 	
    	 
    	 form.setStoreId("1");
    	 form.setStoreName("tsetName");
    	 form.setAddress("tsetAddress");
    	 form.setPhone("07012341234");
    	 form.setStartDay("2023-05-20");
    	 form.setFinishDay("2023-05-20");
    	 
     	String mockid = "0";
     	Mockito.when(this.mapper.selectMaxId()).thenReturn(mockid);
 		String maxId = String.valueOf(mockid);
 		String maxStoreId = String.valueOf(Integer.valueOf(maxId) + 1);
 		
 		
 		try {
    		
 			service.insert(form);
 			
 		// 比較
 	 		assertEquals(bean.getStoreId(), form.getStoreId());
 	 		assertEquals(bean.getStoreName(), form.getStoreName());
 	 		assertEquals(bean.getAddress(), form.getAddress());
 	 		assertEquals(bean.getPhone(), form.getPhone());
 	 		assertEquals(bean.getStartDay(), form.getStartDay());
 	    	}
 	    	
 	    	catch (Exception e) {
 	    		
 				e.printStackTrace();
 				
 			}
 		
    	 
 }
    
    
    /**
     * 入力正常終了
     * 
     * 検索件数：1件
     */
    @Test
    public void test_update_01() {
        MarketstoreForm form = new MarketstoreForm();
        
        
        form.setStoreId("1");
        form.setStoreName("tsetName");
        form.setAddress("tsetAddress");
        form.setPhone("07012341234");
        form.setStartDay("2023-05-20");
        form.setFinishDay("2023-05-20");

        List<MarketstoreBean> ChangeResults = new ArrayList<>();

        MarketstoreBean result = new MarketstoreBean();
        
        result.setStoreId("1");
        result.setStoreName("tsetName");
        result.setAddress("tsetAddress");
        result.setPhone("07012341234");
        result.setStartDay("2023-05-20");
        result.setFinishDay("2023-05-20");
        ChangeResults.add(result);

        Mockito.when(mapper.select(any())).thenReturn(ChangeResults);

        service.update(form);
  
        ArgumentCaptor<MarketstoreBean> captor = ArgumentCaptor.forClass(MarketstoreBean.class);

        Mockito.verify(mapper).update(captor.capture());
        try {
        	
        	MarketstoreBean capturedBean = captor.getValue();
        
     // 比較
        assertEquals("1", capturedBean.getStoreId());
        assertEquals("tsetName", capturedBean.getStoreName());
        assertEquals("tsetAddress", capturedBean.getAddress());
        assertEquals("07012341234", capturedBean.getPhone());
        assertEquals("2023-05-20", capturedBean.getStartDay());
        assertEquals("2023-05-20", capturedBean.getFinishDay());
        
        }catch (Exception e) {
	    		
				e.printStackTrace();
				
			}
		
    }
    
    /**
     * 削除正常終了
     * 
     * 検索件数：1件
     */
    
    @Test
    public void test_delete_01() {
       	MarketstoreBean mockBean =  createBean();

       	String storeId = mockBean.getStoreId();
       	
        Mockito.when(this.mapper.delete(mockBean)).thenReturn(0);
        service.delete(storeId);
        
      //削除正常終了を検証するため、もう一回検索する
        MarketstoreListForm form = new MarketstoreListForm();
		form.setAddress("上野1支店");
		form.setStoreName("sdgdfhrdOOO");
		try {
			service.select(form);
			// 空になったかをチエック
			assertNull(form.getResults());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    /**
     * 全削除正常終了
     * 
     * 検索件数：1件
     */
    @Test
    public void test_deleteall_01() {
    	
    	MarketstoreBean mockBean =  createBean();
    	
    	String Ids = "1";
    	
    	Mockito.when(this.mapper.deleteAll(any())).thenReturn(0);
    	
    	  MarketstoreListForm form = new MarketstoreListForm();
    	  service.deleteAll(Ids);

    	  try {
  			service.select(form);
  			// 空になったかをチエック
  			assertNull(form.getResults());
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
    }
    
    /**
     * 編集正常終了
     * 
     * 検索件数：1件
     */
    
    @Test
    public void test_editInit_01() {
        MarketstoreForm form = new MarketstoreForm();
        form.setStoreId("1");
        form.setStoreName("tsetName");
        form.setAddress("tsetAddress");
        form.setPhone("07012341234");
        form.setStartDay("2023-05-20");
        form.setFinishDay("2023-05-20");

        List<MarketstoreBean> results = new ArrayList<>();
        MarketstoreBean result = new MarketstoreBean();
        result.setStoreId("1");
        result.setStoreName("tsetName");
        result.setAddress("tsetAddress");
        result.setPhone("07012341234");
        result.setStartDay("2023-05-20");
        result.setFinishDay("2023-05-20");
        results.add(result);

        Mockito.when(mapper.select(any())).thenReturn(results);

        MarketstoreForm resultForm = service.editInit(form);

     // 比較
        assertEquals("1", resultForm.getStoreId());
        assertEquals("tsetName", resultForm.getStoreName());
        assertEquals("tsetAddress", resultForm.getAddress());
        assertEquals("07012341234", resultForm.getPhone());
        assertEquals("2023-05-20", resultForm.getStartDay());
        assertEquals("2023-05-20", resultForm.getFinishDay());
    }
    
	 @Test
	    public void test_read_01() {
	        MarketstoreForm form = new MarketstoreForm();
	        form.setStoreId("1");
	        form.setStoreName("tsetName");
	        form.setAddress("tsetAddress");
	        form.setPhone("07012341234");
	        form.setStartDay("2023-05-20");
	        form.setFinishDay("2023-05-20");

	        List<MarketstoreBean> searchResults = new ArrayList<>();
	        MarketstoreBean result = new MarketstoreBean();
	        result.setStoreId("1");
	        result.setStoreName("tsetName");
	        result.setAddress("tsetAddress");
	        result.setPhone("07012341234");
	        result.setStartDay("2023-05-20");
	        result.setFinishDay("2023-05-20");
	        searchResults.add(result);

	        Mockito.when(mapper.select(any())).thenReturn(searchResults);

	        MarketstoreForm resultForm = service.readInit(form);
	     // 比較
	        assertEquals("1", resultForm.getStoreId());
	        assertEquals("tsetName", resultForm.getStoreName());
	        assertEquals("tsetAddress", resultForm.getAddress());
	        assertEquals("07012341234", resultForm.getPhone());
	        assertEquals("2023-05-20", resultForm.getStartDay());
	        assertEquals("2023-05-20", resultForm.getFinishDay());
	        
	    }
    
}