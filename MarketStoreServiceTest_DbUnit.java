/**qqqqqqqqqqqqqqqqqq
     * 
     * DbUnit
     * 
     * 要测试的Service
     * MarketStoreServiceImpl
     * 
     */
package com.sample.practice6MarketStore_ServiceTest_DBunit;

import static org.junit.jupiter.api.Assertions.*;

import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.base.UnitTestBase;
import com.cms.entity.marketstore.MarketStoreBean;
import com.cms.form.marketstore.MarketStoreForm;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.service.marketstore.MarketStoreServiceImpl;
import com.exception.BusinessException;


@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MarketStoreServiceTest_DbUnit {
	//声明IDatabaseConnection类型的变量conn，用于与数据库建立连接
	private static IDatabaseConnection conn;
	//定义常量path，表示测试数据文件所在的路径。
	private final static String path = "src\\test\\java\\com\\sample\\practice6MarketStore_DB\\";
	
	/* テストデータを登録する */
	@BeforeAll
	//init()在所有测试方法运行之前初始化数据库连接和加载测试数据。
	public static void init() throws Exception {
		//通过UnitTestBase.connect()方法建立数据库连接并将连接赋值给conn变量。
		conn = UnitTestBase.connect();
		
		// 実施前にテーブルの既存データをクリアする 
		//使用UnitTestBase.clearData(path, conn)方法清空表中的既有数据。
		//conn是数据库，path下面是测试用的数据 
		UnitTestBase.clearData(path, conn);
		// CSVデータを取り込む
		//使用UnitTestBase.initData(path, conn)方法加载CSV格式的测试数据到数据库中。
		UnitTestBase.initData(path, conn);
	}

	@AfterAll
	public static void closeConnection() throws Exception {
		UnitTestBase.clearData(path, conn);
		// CSVデータを取り込む
		//通过UnitTestBase.closeConnection(conn)方法关闭数据库连接。
		UnitTestBase.closeConnection(conn);
	}

	@Autowired
	MarketStoreServiceImpl service;
	
    @Captor
    private ArgumentCaptor<MarketStoreBean> captor;

	/**
     * 正常終了
     * 
     * 検索件数：5件
     */
	@Test
	public void test_001_select_001() {
		MarketStoreListForm inForm = new MarketStoreListForm();

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			assertEquals(6, resultForm.getResults().size());
	    	
	    	// 項目比較
	    	assertEquals("00000001", resultForm.getResults().get(0).getStoreId());
	    	assertEquals("东京店", resultForm.getResults().get(0).getStoreName());
	    	assertEquals("东京都深草区洼洼村", resultForm.getResults().get(0).getAddress());
	    	assertEquals("08035451501", resultForm.getResults().get(0).getPhone());
	    	assertEquals("2023-05-01", resultForm.getResults().get(0).getStartDay());
	    	assertEquals("2023-05-11", resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * 異常終了
     * 
     * 模拟搜索结果为空的情况
     */
	@Test
	public void test_001_select_002() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("BusinessException");
		inForm.setAddress("BusinessException");

		try {
            // 调用被测试的方法
            service.select(inForm);
            
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
	 * insert
	 * 
	 * 新规件数：1件
	 */
	@Test
	public void test_002_insert_001() {
		
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreName("Test Store");
        form.setAddress("Test Address");
        form.setPhone("Test Phone");
        form.setStartDay("2023-06-13");
        form.setFinishDay("2023-06-30");

		MarketStoreListForm checkBefore = new MarketStoreListForm();
		MarketStoreListForm checkAfter = new MarketStoreListForm();
		try {
			int beforeSize = service.select(checkBefore).getResults().size();
			
			// 调用被测试的方法
			service.insert(form);
			int afterSize = service.select(checkAfter).getResults().size();
			
			//件数比较
			assertEquals(beforeSize + 1, afterSize);
			
			//项目比较
			MarketStoreListForm selectForm = new MarketStoreListForm();
			selectForm.setStoreName("Test Store");
			MarketStoreListForm checkForm;
			checkForm = service.select(selectForm);
			assertEquals("Test Store", checkForm.getResults().get(0).getStoreName());
			assertEquals("Test Address", checkForm.getResults().get(0).getAddress());
			assertEquals("Test Phone", checkForm.getResults().get(0).getPhone());
			assertEquals("2023-06-13", checkForm.getResults().get(0).getStartDay());
			assertEquals("2023-06-30", checkForm.getResults().get(0).getFinishDay());

		} catch (BusinessException e) {
            // 如果运行到这里，说明没有检索到数据
            assertEquals("検索結果はありません。", e.getMessage());
            
        } catch (Exception e) {
			fail("test failed");
		}
	}

	/**
	 * editinitl
	 * 
	 * 検索件数：1件
	 */
	@Test
	public void test_003_editInit_001() {
		
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("00000002");
		MarketStoreForm resultForm;

		try {
			// 调用被测试的方法
			resultForm = service.editInit(form);
			
			//项目比较
			assertEquals("00000002", resultForm.getStoreId());
			assertEquals("千叶店", resultForm.getStoreName());
			assertEquals("东京都浅草区洼洼村", resultForm.getAddress());
			assertEquals("08035451502", resultForm.getPhone());
			assertEquals("2023-05-02", resultForm.getStartDay());
			assertEquals("2023-05-12", resultForm.getFinishDay());
			
		} catch (Exception e) {
			fail("test failed");
		}
		
	}

	/**
	 * update
	 * 
	 * 更新件数：1件
	 */
	@Test
	public void test_004_update_001() {
		
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("00000002");
		form.setStoreName("Test Store");
		form.setAddress("Test Address");
		form.setPhone("Test Phone");
		form.setStartDay("2023-06-13");
		form.setFinishDay("2023-06-30");

        // 调用被测试的方法
        service.update(form);
        
        //项目比较
		MarketStoreListForm selectForm = new MarketStoreListForm();
		selectForm.setStoreName("Test Store");
		MarketStoreListForm checkForm;
		checkForm = service.select(selectForm);
		assertEquals("Test Store", checkForm.getResults().get(0).getStoreName());
		assertEquals("Test Address", checkForm.getResults().get(0).getAddress());
		assertEquals("Test Phone", checkForm.getResults().get(0).getPhone());
		assertEquals("2023-06-13", checkForm.getResults().get(0).getStartDay());
		assertEquals("2023-06-30", checkForm.getResults().get(0).getFinishDay());
	}

	
	/**
	 * delete
	 * 
	 * 删除一件
	 */
	@Test
	public void test_005_delete_001() {
		
		
		String storeId = "00000002";

		try {
			service.delete(storeId);
			
			// 项目比较
			MarketStoreListForm selectForm = new MarketStoreListForm();
			selectForm.setStoreName("千叶店");
			MarketStoreListForm checkForm; 
			checkForm = service.select(selectForm);
			// 如果运行到这里，测试失败
            fail("应该抛出 BusinessException");
            
		}  catch (BusinessException e) {
            // 捕捉 BusinessException 异常
            assertEquals("検索結果はありません。", e.getMessage());
            
        } catch (Exception e) {
            // 如果抛出了其他类型的异常，测试失败
            fail("应该抛出 BusinessException");
        }
	}
	
	/**
	 * delete_all
	 * 
	 * 删除件数:3件
	 */
	@Test
	public void test_006_deleteAll_001() {

		String storeId = "00000001,00000002,00000003";

		try {
			service.deleteAll(storeId);
			// 验证
			MarketStoreListForm selectForm = new MarketStoreListForm();
			selectForm.setStoreName("东京店");
			MarketStoreListForm checkForm;
			checkForm = service.select(selectForm);
			// 如果运行到这里，测试失败
			fail("应该抛出 BusinessException");

		} catch (BusinessException e) {
			// 验证第一条数据
			assertEquals("検索結果はありません。", e.getMessage());
			try {
				// 验证第二条数据
				MarketStoreListForm selectForm = new MarketStoreListForm();
				selectForm.setStoreName("千叶店");
				MarketStoreListForm checkForm;
				checkForm = service.select(selectForm);
				fail("应该抛出 BusinessException");
			} catch (BusinessException ee) {
				// 验证第二条数据
				assertEquals("検索結果はありません。", e.getMessage());
				try {
					MarketStoreListForm selectForm = new MarketStoreListForm();
					selectForm.setStoreName("浅草店");
					MarketStoreListForm checkForm;
					checkForm = service.select(selectForm);
					fail("应该抛出 BusinessException");
				} catch (BusinessException eee) {
					// 验证第三条数据
					assertEquals("検索結果はありません。", e.getMessage());
				} catch (Exception aaa) {
					fail("应该抛出 BusinessException");
				}
			} catch (Exception aa) {
				fail("应该抛出 BusinessException");
			}
		} catch (Exception a) {
			fail("应该抛出 BusinessException");
		}
	}
	
	/** 
	 * readinitl
	 * 
	 * 更新件数:1件
	 */
	@Test
	public void test_007_readinit_001() {

		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("00000002");
		MarketStoreForm resultForm;
		try {
			resultForm = service.readInit(form);
			// 项目比较
			assertEquals("00000002", resultForm.getStoreId());
			assertEquals("千叶店", resultForm.getStoreName());
			assertEquals("东京都浅草区洼洼村", resultForm.getAddress());
			assertEquals("08035451502", resultForm.getPhone());
			assertEquals("2023-05-02", resultForm.getStartDay());
			assertEquals("2023-05-12", resultForm.getFinishDay());

		} catch (Exception e) {
			fail("test failed");
		}
	}

}
