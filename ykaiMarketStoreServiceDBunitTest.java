package com.sample.practice4;

import static org.junit.jupiter.api.Assertions.*;

import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.base.UnitTestBase;
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
public class MarketStoreServiceDBunitTest {	

	@Mock
	private MarketstoreMapper mapper;
	//链接数据库
    private static IDatabaseConnection idconn;	
    private final static String path = "src\\test\\data\\practice4\\";
    private MarketstoreServiceImpl service;
	@BeforeAll
	 public static void init() throws Exception {
		idconn = UnitTestBase.connect();

		// 実施前にテーブルの既存データをクリアする
		 UnitTestBase.clearData(path, idconn);
		// CSVデータを取り込む
		UnitTestBase.initData(path, idconn);
	}

	@AfterAll
	  public static void closeConnection() throws Exception {
		UnitTestBase.clearData(path, idconn);
		// CSVデータを取り込む
		UnitTestBase.closeConnection(idconn);
	}
	
	
	/**
	 * 第1：販売店　販売店Id検索
	 * select
	 * 検索件数：1件
	 */
	@Test
	public void MarketstoreIDTest_01_OK() {

		// 販売店Id検索 要从listform中找值
		MarketstoreListForm form = new MarketstoreListForm();
		 form.setStoreName("秋葉原支店");
		 form.setAddress("qq495370081@gmail.com");


		try {
			
			MarketstoreListForm result = service.select(form);
			// 件数比較
			assertEquals(1, result.getResults().size());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 第2：販売店　販売店Id検索
	 * 
	 * 検索件数：1件(※検索結果と条件が不一致です。)
	 */
	@Test
	public void MarketstoreIDTest_02_no() {

			// 販売店Id検索 要从list中找值
			MarketstoreListForm form = new MarketstoreListForm();
			 form.setStoreName("上野支店");
			 form.setAddress("qq495370081@gmail.com");



			try {
				
				MarketstoreListForm result = service.select(form);
				result.getStoreName();
				result.getAddress();
				MarketstoreListForm result2 = service.select(result);

						
				// 件数比較
				assertEquals(form.getStoreName(),result2.getStoreName());
				assertEquals(form.getAddress(),result2.getAddress());

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 
	/**第3：販売店　販売店Id検索
	 * 異常終了（検索結果がありません。）
	 * 
	 */
	@Test
	public void MarketstoreIDTest_03_NO() {

		// 販売店Id検索 要从list中找值
		MarketstoreListForm form = new MarketstoreListForm();
		 form.setStoreName("東京支店");
		 form.setAddress(null);


		try {
			
			MarketstoreListForm result = service.select(form);
			// 件数比較
			assertEquals(1, result.getResults().size());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 第4：販売店　販売店新规
	 * 
	 * 検索件数：1件
	 */
	@Test
	public void MarketstoreinsertTest_04() {
	    MarketstoreForm form = new MarketstoreForm();
	    form.setStoreName("秋葉原支店");
	    form.setAddress("qq495370081@gmail.com");
	    form.setPhone("09060199939");
	    form.setStartDay("2023-06-16 ");
	    form.setFinishDay("2023-06-16");

	    MarketstoreListForm checkBefore = new MarketstoreListForm();
	    MarketstoreListForm checkAfter = new MarketstoreListForm();
	    
	    // 初始化 checkBefore 和 checkAfter 对象
	    checkBefore.setStoreName("秋葉原支店");
	    checkAfter.setStoreName("東京支店");

	    try {
	        // 调用被测试的方法
	        int beforeSize = service.select(checkBefore).getResults().size();
	        service.insert(form);
	        int afterSize = service.select(checkAfter).getResults().size();

	        // 件数比较
	        assertEquals(beforeSize + 1, afterSize);

	        // 项目比较
	        MarketstoreListForm selectForm = new MarketstoreListForm();
	        selectForm.setStoreName("東京支店");
	        MarketstoreListForm checkForm = service.select(selectForm);
	        assertEquals("東京支店", checkForm.getResults().get(0).getStoreName());
	        assertEquals("qq495370081@gmail.com", checkForm.getResults().get(0).getAddress());
	        assertEquals("09060199939", checkForm.getResults().get(0).getPhone());
	        assertEquals("2023-06-16", checkForm.getResults().get(0).getStartDay());
	        assertEquals("2023-06-16", checkForm.getResults().get(0).getFinishDay());
	    } catch (BusinessException e) {
	        // 如果运行到这里，说明没有检索到数据
	        assertEquals("検索結果はありません。", e.getMessage());
	    } 
	}

	/**
	 * 第5：販売店editInit
	 * 　販売店更新画面【初期化】
	 * 検索件数：1件
	 */
	@Test
	    public void MarketstoreeditInitTest_05() {
		 MarketstoreForm form = new MarketstoreForm();
		 form.setStoreId("9");
		 form.setStoreName("秋葉原支店");
		 form.setAddress("qq495370081@gmail.com");
		 form.setPhone("09060199939");
		 form.setStartDay("2023-06-16");
		 form.setFinishDay("2023-06-16");

//		MarketstoreBean input = new MarketstoreBean();
//		input.setStoreId("0");
//		input.setStoreName("于凯");
//		input.setAddress("1160014");
//		input.setPhone("09060199939");
//		input.setStartDay("2022-03-01");
//		input.setFinishDay("2022-03-01");
//		Mockito.when(mapper.selectMaxId()).thenReturn("1");
   
		 try {
			MarketstoreForm resultForm = service.editInit(form);
			assertEquals("9",resultForm.getStoreId());
			assertEquals("秋葉原支店", resultForm.getStoreName());
		    assertEquals("qq495370081@gmail.com", resultForm.getAddress());
		    assertEquals("09060199939", resultForm.getPhone());
		    assertEquals("2023-06-16", resultForm.getStartDay());
		    assertEquals("2023-06-16", resultForm.getFinishDay());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		
	}

	
	/**
	 * 第6：販売店upDate
	 * 　販売店更新画面【データ保存】
	 * 検索件数：1件
	 */
	@Test
	    public void MarketstoreupDateTest_06() {
	    	MarketstoreForm form = new MarketstoreForm();
			 form.setStoreId("9");
			 form.setStoreName("秋葉原支店");
			 form.setAddress("qq495370081@gmail.com");
			 form.setPhone("09060199939");
			 form.setStartDay("2022-03-01");
			 form.setFinishDay("2022-03-01");
			 MarketstoreListForm listfrom = new MarketstoreListForm();
			 listfrom.setStoreName("秋葉原支店");
			 listfrom.setAddress("qq495370081@gmail.com");
			 
//			 // 模拟 select() 方法的返回结果
//	    	List<MarketstoreBean> update = new ArrayList<MarketstoreBean>();
//	        MarketstoreBean result = new MarketstoreBean();
//	        result.setStoreId("9");
//	        result.setStoreName("于凯");
//	        result.setAddress("1160014");
//	        result.setPhone("09060199939");
//	        result.setStartDay("2022-03-01");
//	        result.setFinishDay("2022-03-01");
//	        update.add(result);        
//		    List<MarketstoreBean> beanList = null;
		
			try {
				service.update(form);
				MarketstoreListForm listform2 = service.select(listfrom);	
				
				assertEquals(1, listform2.getResults().size());
//	        assertEquals("于凯", result.getStoreName());
//	        assertEquals("1160014", result.getAddress());
//	        assertEquals("1234567890", result.getPhone());
//	        assertEquals("2022-03-01", result.getStartDay());
//	        assertEquals("2022-03-01", result.getFinishDay());
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	 }
	
	/**
	 * 第7：販売店delete
	 * 　販売店削除画面【データ削除】
	 * 検索件数：1件(※もし値が“9”ある場合)
	 */
	 
	@Test
	    public void MarketstoredeleteTest_07() {
//	    	// readInit結果Mock
//			List<MarketstoreBean> mockResults = new ArrayList<MarketstoreBean>();
//			mockResults.add(createBean("2"));
//	    	 MarketstoreBean readinit = new MarketstoreBean();
//	    	 readinit.setStoreId("2");
//	    	 readinit.setStoreName("于凯");
//	    	 readinit.setAddress("1160014");
//	    	 readinit.setPhone("09060199939");
//	    	 readinit.setStartDay("2022-03-01");
//	    	 readinit.setFinishDay("2022-03-01");
//	    	 Mockito.when(mapper.select(any())).thenReturn(mockResults);
//	    	  List<MarketstoreBean> beanList = null;
	    	String storeId ="9";
	    	 
	    	 
	    	 try {
	    	 MarketstoreListForm form = new MarketstoreListForm();
	    	 MarketstoreListForm  result= service.select(form);
	 
				service.delete(storeId);
				assertEquals(1, result.getResults().size());
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	     
	    	
	    }
	
	/**
	 * 第8：販売店deleteAll
	 * 　販売店削除全画面【データ削除】
	 * 検索件数：1件(※もし値が9個ある場合)
	 */
	
	@Test
	    public void MarketstoredeleteallTest_08() {
	    String abcStrings = "1,2,3,4,5,6,7,8,9";
	    try {
			service.deleteAll(abcStrings);
			MarketstoreListForm form = new MarketstoreListForm();
			MarketstoreListForm  result= service.select(form);
			assertEquals(0, result.getResults().size());
		} catch (Exception e) {
			String message = e.getMessage();
			
		 assertNull(message);

	    
	     
}
}
}