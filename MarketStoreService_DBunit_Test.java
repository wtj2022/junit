package com.sample.practice1;

import static org.junit.jupiter.api.Assertions.*;

import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.base.UnitTestBase;
import com.cms.form.marketstore.MarketStoreForm;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.service.marketstore.MarketStoreServiceImpl;
import com.exception.BusinessException;

@SuppressWarnings("rawtypes")
@SpringBootTest
public class MarketStoreService_DBunit_Test {
    private static IDatabaseConnection conn;
    private final static String path = "src\\test\\data\\dbunit_test\\";

    @Autowired
    private MarketStoreServiceImpl service;

    @BeforeEach
    public void init() throws Exception {
        conn = UnitTestBase.connect();

        // 実施前にテーブルの既存データをクリアする
        UnitTestBase.clearData(path, conn);
        // CSVデータを取り込む
        UnitTestBase.initData(path, conn);
    }

    @AfterEach
    public void closeConnection() throws Exception {
    	// 実施後にテーブルの既存データをクリアする
        UnitTestBase.clearData(path, conn);
        // 実行後にデータベース接続を切断する
        UnitTestBase.closeConnection(conn);
    }

	/*
	 * ケース：No1 検索機能（検索結果あり）
	 * 販売店名入力：空
	 * アドレス入力；空
	 * 検索結果：7件
	 */
	@Test
	public void test_select_01() {
		MarketStoreListForm inForm = new MarketStoreListForm();

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			assertEquals(7, resultForm.getResults().size());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}
	/*
	 * ケース：No2 検索機能（検索結果あり）
	 * 販売店名入力：東京第1支店(全部)
	 * アドレス入力；船橋市駿河台（全部）
	 * 検索結果：1件
	 */
	@Test
	public void test_select_02() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("東京第1支店");
		inForm.setAddress("船橋市駿河台");
		

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			//从form中获取results的第一个索引，并从该索引中获取對應的值。
			assertEquals("1",  resultForm.getResults().get(0).getStoreId());
			assertEquals("東京第1支店",  resultForm.getResults().get(0).getStoreName());
			assertEquals("07023186768",  resultForm.getResults().get(0).getPhone());
			assertEquals("船橋市駿河台",  resultForm.getResults().get(0).getAddress());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getStartDay());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}

	/*
	 * ケース：No3 検索機能（検索結果あり）
	 * 販売店名入力：空
	 * アドレス入力；船橋市駿河台(全部)
	 * 検索結果：1件
	 */
	@Test
	public void test_select_03() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("");
		inForm.setAddress("船橋市駿河台");
		

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			//从form中获取results的第一个索引，并从该索引中获取對應的值。
			assertEquals("1",  resultForm.getResults().get(0).getStoreId());
			assertEquals("東京第1支店",  resultForm.getResults().get(0).getStoreName());
			assertEquals("07023186768",  resultForm.getResults().get(0).getPhone());
			assertEquals("船橋市駿河台",  resultForm.getResults().get(0).getAddress());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getStartDay());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}
	
	/*
	 * ケース：No4 検索機能（検索結果あり）
	 * 販売店名入力：空
	 * アドレス入力；駿河台(一部）
	 * 検索結果：1件
	 */
	@Test
	public void test_select_04() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("");
		inForm.setAddress("駿河台");
		

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			//从form中获取results的第一个索引，并从该索引中获取對應的值。
			assertEquals("1",  resultForm.getResults().get(0).getStoreId());
			assertEquals("東京第1支店",  resultForm.getResults().get(0).getStoreName());
			assertEquals("07023186768",  resultForm.getResults().get(0).getPhone());
			assertEquals("船橋市駿河台",  resultForm.getResults().get(0).getAddress());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getStartDay());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}
	/*
	 * ケース：No5 検索機能（検索結果あり）
	 * 販売店名入力：東京第1支店(全部)	
	 * アドレス入力；空
	 * 検索結果：1件
	 */
	@Test
	public void test_select_05() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("東京第1支店");
		inForm.setAddress("");
		

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			//从form中获取results的第一个索引，并从该索引中获取對應的值。
			assertEquals("1",  resultForm.getResults().get(0).getStoreId());
			assertEquals("東京第1支店",  resultForm.getResults().get(0).getStoreName());
			assertEquals("07023186768",  resultForm.getResults().get(0).getPhone());
			assertEquals("船橋市駿河台",  resultForm.getResults().get(0).getAddress());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getStartDay());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}
	/*
	 * ケース：No6 検索機能（検索結果あり）
	 * 販売店名入力：一部
	 * アドレス入力；空
	 * 検索結果：1件
	 */
	@Test
	public void test_select_06() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("東京第1");
		inForm.setAddress("");
		

		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			//从form中获取results的第一个索引，并从该索引中获取對應的值。
			assertEquals("1",  resultForm.getResults().get(0).getStoreId());
			assertEquals("東京第1支店",  resultForm.getResults().get(0).getStoreName());
			assertEquals("07023186768",  resultForm.getResults().get(0).getPhone());
			assertEquals("船橋市駿河台",  resultForm.getResults().get(0).getAddress());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getStartDay());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}
	/*
	 * ケース：No7 検索機能（検索結果なし）
	 * 販売店名入力：あり（不存在）
	 * アドレス入力；あり（不存在）
	 * 検索結果：1件
	 */
	@Test
	public void test_select_07() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("東京第555555");
		inForm.setAddress("西京");
		try {
			// 传递form给service.select()方法
			service.select(inForm);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}
	
	/*
	 * ケース：No8 検索機能（検索結果なし）
	 * 販売店名入力：あり（不存在）
	 * アドレス入力；空
	 * 検索結果：1件
	 */
	@Test
	public void test_select_08() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("東京第555555");
		inForm.setAddress("");
		try {
			// 传递form给service.select()方法
			service.select(inForm);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}

	/*
	 * ケース：No9 検索機能（検索結果なし）
	 * 販売店名入力：空
	 * アドレス入力；あり（不存在）
	 * 検索結果：1件
	 */
	@Test
	public void test_select_09() {
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("");
		inForm.setAddress("西京");
		try {
			// 传递form给service.select()方法
			service.select(inForm);
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
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreName("东莞店");
		form.setAddress("东莞第一人民医院");
		form.setPhone("08054889654");
		form.setStartDay("2023-05-17");
		form.setFinishDay("2023-05-17");
		service.insert(form);
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("东莞店");
		inForm.setAddress("东莞第一人民医院");
		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			//从form中获取results的第一个索引，并从该索引中获取對應的值。
			assertEquals("8",  resultForm.getResults().get(0).getStoreId());
			assertEquals("东莞店",  resultForm.getResults().get(0).getStoreName());
			assertEquals("08054889654",  resultForm.getResults().get(0).getPhone());
			assertEquals("东莞第一人民医院",  resultForm.getResults().get(0).getAddress());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getStartDay());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}

	/*
	 * ケース：No11 削除機能
	 */
	@Test
	public void test_delete_01() {
		service.delete("1");
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("東京第1支店");
		inForm.setAddress("船橋市駿河台");
		try {
			// 传递form给service.select()方法
			service.select(inForm);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}

	/*
	 * ケース：No12 全削除機能
	 */
	@Test
	public void test_deleteall_01() {
		String storeIds = "1234567";
		service.deleteAll(storeIds);
		MarketStoreListForm inForm = new MarketStoreListForm();
		try {
			// 传递form给service.select()方法
			service.select(inForm);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
	}

	/*
	 * ケース：No13 更新機能
	 */
	@Test
	public void test_update_01() {
		//準備數據
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("1");
		form.setStoreName("东莞店");
		form.setAddress("东莞第一人民医院");
		form.setPhone("08054889654");
		form.setStartDay("2023-05-17");
		form.setFinishDay("2023-05-17");
		
		service.update(form);
		MarketStoreListForm inForm = new MarketStoreListForm();
		inForm.setStoreName("東京第1支店");
		inForm.setAddress("船橋市駿河台");
		try {
			// 传递form给service.select()方法
			service.select(inForm);
		} catch (BusinessException e) {
			//項目比較
			assertEquals("検索結果はありません。", e.getMessage());
		}
		inForm.setStoreName("东莞店");
		inForm.setAddress("东莞第一人民医院");
		MarketStoreListForm resultForm;
		try {
			resultForm = service.select(inForm);
			//从form中获取results的第一个索引，并从该索引中获取對應的值。
			assertEquals("1",  resultForm.getResults().get(0).getStoreId());
			assertEquals("东莞店",  resultForm.getResults().get(0).getStoreName());
			assertEquals("08054889654",  resultForm.getResults().get(0).getPhone());
			assertEquals("东莞第一人民医院",  resultForm.getResults().get(0).getAddress());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getStartDay());
			assertEquals("2023-05-17",  resultForm.getResults().get(0).getFinishDay());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}

	/*
	 * ケース：No14 参照機能
	 */
	@Test
	public void test_read_01() {
		//準備數據
		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("2");
		// 传递form给service.readInit()方法
		MarketStoreForm resultForm = service.readInit(form);
		//項目比較
		assertEquals("2", resultForm.getStoreId());
		assertEquals("東京第2支店", resultForm.getStoreName());
		assertEquals("東京", resultForm.getAddress());
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
				form.setStoreId("2");
				// 传递form给service.editInit()方法
				MarketStoreForm resultForm = service.editInit(form);
				//項目比較
				assertEquals("2", resultForm.getStoreId());
				assertEquals("東京第2支店", resultForm.getStoreName());
				assertEquals("東京", resultForm.getAddress());
				assertEquals("07023186768", resultForm.getPhone());
				assertEquals("2023-05-17", resultForm.getStartDay());
				assertEquals("2023-05-17", resultForm.getFinishDay());
			}
}