package com.sample.junitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.base.UnitTestBase;
import com.cms.entity.marketstore.MarketStoreBean;
import com.cms.form.marketstore.MarketStoreForm;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.service.marketstore.MarketStoreServiceImpl;

//販売店service junitテスト
@SpringBootTest
public class serviceTest {
	@Autowired
	private MarketStoreServiceImpl service;
	
	private static IDatabaseConnection conn;
	private final static String path = "src\\test\\data\\practice1\\";
	@BeforeAll
	public static void init() throws Exception {
		//データベースに接続
		conn = UnitTestBase.connect();
		// CSVデータを取り込む
		UnitTestBase.initData(path, conn);
	}

	@AfterAll
	public static void closeConnection() throws Exception {
		// データをクリア
		UnitTestBase.clearData(path, conn);
		UnitTestBase.closeConnection(conn);
	}

	/*
	 * ケース：No1 検索機能正常系 
	 * 販売店店名アドレス検索
	 */
	@Test
	public void serviceTest_001() {
		MarketStoreListForm form = new MarketStoreListForm();
		form.setStoreName("東京店");
		form.setAddress("東京");
		try {
			MarketStoreListForm result = service.select(form);
			assertEquals(1, result.getResults().size());
			assertEquals("1", result.getResults().get(0).getStoreId());
			assertEquals("東京店", result.getResults().get(0).getStoreName());
			assertEquals("東京", result.getResults().get(0).getAddress());
			assertEquals("08011112222", result.getResults().get(0).getPhone());
			assertEquals("2023-05-17", result.getResults().get(0).getStartDay());
			assertEquals("2023-05-17", result.getResults().get(0).getFinishDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ケース：No2 検索機能異常系 
	 * 検索結果ありません 
	 */
	@Test
	public void serviceTest_002() {
		MarketStoreListForm form = new MarketStoreListForm();
		form.setStoreName("123");
		form.setAddress("321");
		try {
			service.select(form);
		} catch (Exception e) {
			String message = e.getMessage();
			assertEquals("検索結果はありません。", message);
		}
	}


	/*
	 * ケース：No3 新規機能
	 */
	@Test
	public void serviceTest_003() {
		MarketStoreForm inform = new MarketStoreForm();
		inform.setStoreName("名古屋店");
		inform.setAddress("名古屋");
		inform.setPhone("08022223333");
		inform.setStartDay("2022-6-1");
		inform.setFinishDay("2023-6-1");
		MarketStoreListForm form = new MarketStoreListForm();
		try {
			service.insert(inform);
			MarketStoreListForm result = service.select(form);
			assertEquals(7, result.getResults().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ケース：No4 編集機能
	 */
	@Test
	public void serviceTest_004() {
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("1");
		try {
			MarketStoreForm result = service.editInit(form);
			assertEquals("1", result.getStoreId());
			assertEquals("東京店", result.getStoreName());
			assertEquals("東京", result.getAddress());
			assertEquals("08011112222", result.getPhone());
			assertEquals("2023-05-17", result.getStartDay());
			assertEquals("2023-05-17", result.getFinishDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ケース：No5 更新機能
	 */
	@Test
	public void serviceTest_005() {
		MarketStoreForm updateform = new MarketStoreForm();
		updateform.setStoreId("2");
		updateform.setStoreName("千葉店");
		updateform.setAddress("千葉");
		updateform.setPhone("08011223344");
		updateform.setStartDay("2022-3-1");
		updateform.setFinishDay("2023-3-1");
		try {
			service.update(updateform);
			MarketStoreListForm result = service.select(new MarketStoreListForm());
			assertEquals("2", result.getResults().get(1).getStoreId());
			assertEquals("千葉店", result.getResults().get(1).getStoreName());
			assertEquals("千葉", result.getResults().get(1).getAddress());
			assertEquals("08011223344", result.getResults().get(1).getPhone());
			assertEquals("2022-03-01", result.getResults().get(1).getStartDay());
			assertEquals("2023-03-01", result.getResults().get(1).getFinishDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ケース：No6 参照画面
	 */
	@Test
	public void serviceTest_006() {
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("1");
		try {
			MarketStoreForm result = service.readInit(form);
			assertEquals("1", result.getStoreId());
			assertEquals("東京店", result.getStoreName());
			assertEquals("東京", result.getAddress());
			assertEquals("08011112222", result.getPhone());
			assertEquals("2023-05-17", result.getStartDay());
			assertEquals("2023-05-17", result.getFinishDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ケース：No7 削除
	 */
	@Test
	public void serviceTest_007() {
		String storeId = "1";
		try {
			service.delete(storeId);
			MarketStoreListForm form = new MarketStoreListForm();
			MarketStoreListForm result = service.select(form);
			assertEquals(6, result.getResults().size());
			// 検索結果のIDリスト
			Object[] ids = result.getResults().stream().map(MarketStoreBean::getStoreId).collect(Collectors.toList()).toArray();
			// 予想値
			Object[] expectedIds = { "2", "3", "4", "5", "6","7" };
			assertArrayEquals(ids, expectedIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ケース：No8 全削除
	 * 全て削除
	 */
	@Test
	public void serviceTest_008() {
		String storeIds = "2,3,4,5,6,7";
		try {	
			service.deleteAll(storeIds);
			MarketStoreListForm form = new MarketStoreListForm();
			service.select(form);
		} catch (Exception e) {
			String message = e.getMessage();
			assertEquals("検索結果はありません。", message);
		}
	}
	
	/*
	 * ケース：No9 全削除
	 * 選択テータ削除
	 */
	@Test
	public void serviceTest_009() {
		String storeIds = "3,4,6";
		service.deleteAll(storeIds);
		Map<String, String> marketStore = new HashMap<String, String>();
		marketStore.put("京都店", "京都");
		 marketStore.put("大阪店", "大阪");
		 marketStore.put("埼玉店", "埼玉");
		for (Map.Entry<String, String> entry : marketStore.entrySet()) {
			try {
				MarketStoreListForm form = new MarketStoreListForm();
				form.setStoreName(entry.getKey());
				form.setAddress(entry.getValue());
				service.select(form);
			} catch (Exception e) {
				String message = e.getMessage();
				assertEquals("検索結果はありません。", message);
			} 
		}
	}

}
