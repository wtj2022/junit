package com.sample.junitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
public class serviceDBUnitTest {

	//	private MarketStoreMapper mapper;

	private static IDatabaseConnection conn;
	private final static String path = "src\\test\\data\\junitTest\\";

	/* テストデータを登録する */
	@BeforeEach
	public void init() throws Exception {
		conn = UnitTestBase.connect();

		// 実施前にテーブルの既存データをクリアする
		//		UnitTestBase.clearData(path, conn);
		// CSVデータを取り込む
		UnitTestBase.initData(path, conn);
	}

	@AfterEach
	public  void closeConnection() throws Exception {
		UnitTestBase.clearData(path, conn);
		// CSVデータを取り込む
		UnitTestBase.closeConnection(conn);
	}

	@Autowired
	MarketStoreServiceImpl service;

	/*
	 * 検索機能正常系
	 */

	@Test
	public void testSelect001() {

		try {

			// 创建输入对象
			MarketStoreListForm form = new MarketStoreListForm();
			form.setAddress("神奈川県");
			form.setStoreName("神奈川店");
			// 调用被测试的方法
			MarketStoreListForm resultForm = service.select(form);

			// 验证结果
			List<MarketStoreBean> results = resultForm.getResults();
			//検査結果のサイズ
			assertEquals(1, results.size());
	

		} catch (BusinessException ex) {

		
			
		}
	}

	/*
	 * 検索機能異常系
	 */
	@Test
	public void testSelect002() {

		try {

			// 创建输入对象
			MarketStoreListForm form = new MarketStoreListForm();
			form.setAddress("1");
			form.setStoreName("1");
			// 调用被测试的方法
			service.select(form);

		} catch (BusinessException ex) {
			String message = ex.getMessage();
			assertEquals("検索結果はありません。", message);
		}
	}

	/*
	 * 新規機能
	 */
	@Test
	public void insertTest_001() {

		try {

			MarketStoreForm form = new MarketStoreForm();
			form.setStoreName("千葉店");
			form.setAddress("千葉");
			form.setPhone("08022223333");
			form.setStartDay("2022-6-1");
			form.setFinishDay("2023-6-1");

			service.insert(form);
			

			// 创建输入对象
			MarketStoreListForm reform = new MarketStoreListForm();

			// 调用被测试的方法
			MarketStoreListForm resultForm = service.select(reform);

			// 验证结果
			List<MarketStoreBean> results = resultForm.getResults();
			//検査結果のサイズ
			assertEquals(7, results.size());


		} catch (Exception e) {
			System.out.println();
		}
	}

	/*
	 * 編集機能正常系
	 */
	@Test
	public void editTest_001() {

		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("3");
		try {
			MarketStoreForm reform = service.editInit(form);

			//検索結果確認
			assertEquals("京都", reform.getAddress());
			assertEquals("京都店", reform.getStoreName());
			assertEquals("0451000003", reform.getPhone());
			assertEquals("2011-02-14", reform.getStartDay());
			assertEquals("2011-02-14", reform.getFinishDay());

		} catch (Exception e) {
			System.out.println();
		}
	}


	/*
	 * 更新機能正常系
	 */
	@Test
	public void updateTest_001() {

		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("2");
		form.setStoreName("千葉支店");
		form.setAddress("千葉市");
		form.setPhone("08011223344");
		form.setStartDay("2022-3-1");
		form.setFinishDay("2023-3-1");
		try {
			
			service.update(form);
			// 创建输入对象

			MarketStoreListForm resultForm = service.select(new MarketStoreListForm());

			// 验证结果
			
			
			assertEquals("千葉市", resultForm.getResults().get(1).getAddress());
			assertEquals("千葉支店", resultForm.getResults().get(1).getStoreName());
			assertEquals("08011223344", resultForm.getResults().get(1).getPhone());
			assertEquals("2022-03-01", resultForm.getResults().get(1).getStartDay());
			assertEquals("2023-03-01", resultForm.getResults().get(1).getFinishDay());

		} catch (Exception e) {
			System.out.println();
		}
	}

	/*
	 * 参照画面正常系
	 */
	@Test
	public void readTest_001() {

		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId("4");
		try {
			MarketStoreForm reform = service.readInit(form);
			

			assertEquals("埼玉県", reform.getAddress());
			assertEquals("埼玉店", reform.getStoreName());
			assertEquals("0451000004", reform.getPhone());
			assertEquals("2011-02-14", reform.getStartDay());
			assertEquals("2011-02-14", reform.getFinishDay());

		} catch (Exception e) {
			System.out.println();
		}
	}


	/*
	 * 削除画面正常系
	 */
	@Test
	public void deleteTest_001() {

		List<MarketStoreBean> marketStoreBeanList = new ArrayList<MarketStoreBean>();
		MarketStoreBean deleteBean = new MarketStoreBean();
		String storeId = "2";
		deleteBean.setStoreId(storeId);
		marketStoreBeanList.add(deleteBean);
	
		
		try {

			service.delete(storeId);
			

			MarketStoreListForm form = new MarketStoreListForm();
			form.setStoreName("神奈川店");
			form.setAddress("神奈川県");
			service.select(form);
			
			

		} catch (Exception e) {
			String message = e.getMessage();
			assertEquals("検索結果はありません。", message);
		}
	}

	/*
	 * 削除画面正常系
	 */
	@Test
	public void deleteTest_002() {

		List<MarketStoreBean> marketStoreBeanList = new ArrayList<MarketStoreBean>();

		
		String storeIds = "1,3,4,5,6";
		String[] storeIdArray = storeIds.split(",");

		String[] addresses = {"東京", "京都", "埼玉県", "千葉県", "北海道"};
		String[] storeNames = {"東京店", "京都店", "埼玉店", "千葉店", "北海道店"};

		for (int i = 0; i < storeIdArray.length; i++) {
			
			MarketStoreBean deleteBean = new MarketStoreBean();
			deleteBean.setStoreId(storeIdArray[i]);

		    
		    marketStoreBeanList.add(deleteBean);
		}

		service.deleteAll(storeIds); // 修改为delete方法，传入要删除的storeId
		
		for (int i = 0; i < 5; i++) {
			
	        try {
	        	
	            MarketStoreListForm form = new MarketStoreListForm();
	            form.setStoreName(storeNames[i]); 
	            form.setAddress(addresses[i]);
	            service.select(form); // 修改为select方法，传入查询条件
	       


	        } catch (Exception e) {
	        	
	        	String message = e.getMessage();
	            assertEquals("検索結果はありません。", message);
	            
	            continue;
	        }
		}
	}
		

}
