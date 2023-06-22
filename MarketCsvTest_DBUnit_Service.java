package com.sample.junit0614;

import static org.assertj.core.api.Assertions.fail;

//贩卖店增删改查service测试
//作成者：赵晨辰
//测试方法：DBunit+Junit

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.base.UnitTestBase;
import com.cms.form.marketstore.MarketStoreForm;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.service.marketstore.MarketStoreServiceImpl;

@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MarketCsvTest_DBUnit_Service {
	private static IDatabaseConnection conn;
	private final static String path = "src\\test\\data\\practice4\\";

	/* テストデータを登録する */
	@BeforeAll
	public static void init() throws Exception {
		conn = UnitTestBase.connect();

		// 実施前にテーブルの既存データをクリアする
		UnitTestBase.clearData(path, conn);
		// CSVデータを取り込む
		UnitTestBase.initData(path, conn);
	}

	@AfterAll
	public static void closeConnection() throws Exception {
		UnitTestBase.clearData(path, conn);
		// CSVデータを取り込む
		UnitTestBase.closeConnection(conn);
	}

	@Autowired
	MarketStoreServiceImpl service;

	/**
	 * 空表单传入，全检索
	 * 
	 * 検索件数：6件
	 */
	@Test
	public void test_marketCRUD_select001_OK() {
		MarketStoreListForm inForm = new MarketStoreListForm();

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			assertEquals(7, resultForm.getResults().size());
//	    	}
		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}

	/**
	 * 传入错误参数，验证异常
	 * 
	 * 検索件数：0件
	 */
	@Test
	public void test_marketCRUD_select002_OK() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("1");
		inForm.setAddress("2");

		MarketStoreListForm resultForm;
		try {
			service.select(inForm);
			fail("test failed");
		} catch (Exception e) {
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}

	/**
	 * 测试insert方法
	 * 
	 * 検索件数：1件
	 */
	@Test
	public void test_marketCRUD_insert003_OK() {
		MarketStoreForm inForm = new MarketStoreForm();
		inForm.setStoreName("Ayaka");
		inForm.setAddress("1-11-1");
		inForm.setPhone("090804812");
		inForm.setStartDay("2000-11-11");
		inForm.setFinishDay("2011-11-11");

		MarketStoreListForm checkAfter = new MarketStoreListForm();
		checkAfter.setStoreName("Ayaka");

		try {
			service.insert(inForm);
			checkAfter = service.select(checkAfter);
			assertEquals("Ayaka", checkAfter.getResults().get(0).getStoreName());
			assertEquals("1-11-1", checkAfter.getResults().get(0).getAddress());
			assertEquals("090804812", checkAfter.getResults().get(0).getPhone());
			assertEquals("2000-11-11", checkAfter.getResults().get(0).getStartDay());
			assertEquals("2011-11-11", checkAfter.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			fail("test failed");
		}
	}

	/**
	 * 编集画面初始化测试 正常 検索件数：1件
	 */
	@Test
	public void test_marketCRUD_editInit001_OK() {
		MarketStoreForm inForm = new MarketStoreForm();
		inForm.setStoreId("00000004");

		MarketStoreForm resultForm;
		try {
			resultForm = service.editInit(inForm);
			assertEquals("00000004", resultForm.getStoreId());
			assertEquals("上原店", resultForm.getStoreName());
			assertEquals("东京都下草区洼洼村", resultForm.getAddress());
			assertEquals("08035451504", resultForm.getPhone());
			assertEquals("2023-05-04", resultForm.getStartDay());
			assertEquals("2023-05-14", resultForm.getFinishDay());
		} catch (Exception e) {
			fail("test failed");
		}
	}

	/**
	 * 编集画面初始化测试 异常 検索件数：1件
	 */
	@Test
	public void test_marketCRUD_editInit002_NO() {
		MarketStoreForm inForm = new MarketStoreForm();
		inForm.setStoreId("00000020");

		MarketStoreForm resultForm;
		try {
			resultForm = service.editInit(inForm);
			assertEquals(null, resultForm.getAddress());

		} catch (Exception e) {
			fail("test failed");
		}
	}

	/**
	 * update
	 * 
	 * 検索件数：1件
	 */
	@Test
	public void test_marketCRUD_update001_OK() {
		MarketStoreForm inForm = new MarketStoreForm();
		MarketStoreListForm checkForm = new MarketStoreListForm();
		inForm.setStoreId("00000003");
		checkForm.setStoreName("Asaka");
		inForm.setStoreName("Asaka");
		inForm.setAddress("Nakano");
		inForm.setPhone("123412345");
		inForm.setStartDay("2000-11-11");
		inForm.setFinishDay("2020-11-11");

		MarketStoreListForm resultForm;
		try {
			service.update(inForm);
			resultForm = service.select(checkForm);
			assertEquals("Asaka", resultForm.getResults().get(0).getStoreName());
			assertEquals("Nakano", resultForm.getResults().get(0).getAddress());
			assertEquals("123412345", resultForm.getResults().get(0).getPhone());
			assertEquals("2000-11-11", resultForm.getResults().get(0).getStartDay());
			assertEquals("2020-11-11", resultForm.getResults().get(0).getFinishDay());
		} catch (Exception e) {
			fail("test failed");
		}
	}

	/**
	 * 参照画面初始化测试
	 * 
	 * 検索件数：1件
	 */
	@Test
	public void test_marketCRUD_readinit001_OK() {
		MarketStoreForm inForm = new MarketStoreForm();
		inForm.setStoreId("00000004");

		MarketStoreForm resultForm;
		try {
			resultForm = service.readInit(inForm);
			assertEquals("00000004", resultForm.getStoreId());
			assertEquals("上原店", resultForm.getStoreName());
			assertEquals("东京都下草区洼洼村", resultForm.getAddress());
			assertEquals("08035451504", resultForm.getPhone());
			assertEquals("2023-05-04", resultForm.getStartDay());
			assertEquals("2023-05-14", resultForm.getFinishDay());
		} catch (Exception e) {
			fail("test failed");
		}
	}

	/**
	 * 编集画面初始化测试 异常 検索件数：1件
	 */
	@Test
	public void test_marketCRUD_readInit002_NO() {
		MarketStoreForm inForm = new MarketStoreForm();
		inForm.setStoreId("00000020");

		MarketStoreForm resultForm;
		try {
			resultForm = service.readInit(inForm);
			assertEquals(null, resultForm.getAddress());

		} catch (Exception e) {
			fail("test failed");
		}
	}

	/**
	 * 测试单条删除方法
	 * 
	 * 検索件数：0件
	 */
	@Test
	public void test_marketCRUD_delete001_OK() {

		MarketStoreListForm checkAfter = new MarketStoreListForm();
		checkAfter.setStoreName("上原店");

		try {
			service.delete("00000004");
			service.select(checkAfter);
			fail("test failed");

		} catch (Exception e) {
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}

	/**
	 * 复选删除测试
	 * 
	 * 検索件数：0件
	 */
	@Test
	public void test_marketCRUD_deleteAll001_OK() {

		Map<String, String> checkmap = new HashMap<String, String>();
		checkmap.put("00000001", "东京店,东京都深草区洼洼村");
		checkmap.put("00000005", "下原店, 东京都左草区洼洼村");
		checkmap.put("00000006", "左原店,东京都右草区洼洼村");
		service.deleteAll("00000001,00000005,00000006");
		Set<Entry<String, String>> es = checkmap.entrySet();
			for(Entry<String, String> entry : es) {
				//取得键值对的值，拆分出店名和地址
				String[] checkValue = entry.getValue().split(",");
				//设置传入表单，第一位是店名，第二位是地址
				MarketStoreListForm checkForm  = new MarketStoreListForm();
				checkForm.setStoreName(checkValue[0]);
				checkForm.setAddress(checkValue[1]);
		
			try {
				service.select(checkForm);
				fail("test failed");
				break;
			} catch (Exception e) {
				assertEquals("検索結果はありません。", e.getMessage());
			}
		
		}
	}

}