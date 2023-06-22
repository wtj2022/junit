/**
 * 这个类测试service的一个测试类，用于确认service的CRUD方法的相关操作。
 * 作者: suyitong
 * 版本：2.0
 * 更新说明：添加注释与修改部分代码
 */


package com.sample.practice1.MarketStoreTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
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

@SuppressWarnings("rawtypes")//用于抑制编译器警告的注解
@RunWith(SpringRunner.class)//指定使用 Spring TestContext Framework 运行测试
@SpringBootTest //指示该类是一个 Spring Boot的测试类
public class MarketStoreServiceTest {
	//表示要创建一个被注入mock对象的实例 也就是要测的东西
	@InjectMocks 
	
	private MarketStoreServiceImpl service;
	//通过 mapper里的变量可以访问和控制这个模拟对象的行为，而不是调用真实的 MarketStoreMapper对象。
	@Mock
	
	private MarketStoreMapper mapper;

	@SuppressWarnings("unchecked")
	@Test
	//测试了select方法 正确输入信息检索的时候确保返回的结果与预期的模拟结果 数据是否相匹配。
	public void testselect_001() {
		//创建一个空列表，存放mock接收的结果
		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
		 //讲bean里的对象添加到mockresult的列表中
		mockResults.add(createBean("1"));
		mockResults.add(createBean("2"));
		mockResults.add(createBean("3"));
		//创建一个名为 parambean的 MarketStoreBean的对象
		MarketStoreBean paramBean = new MarketStoreBean(); 
		 //当检测到mapper里面调用了select方法传值的时候 虚拟机模拟去接受结果
		Mockito.when(this.mapper.select(paramBean)).thenReturn(mockResults);
    
		MarketStoreListForm ListForm =  new MarketStoreListForm();
		ListForm.setStoreName("社員1");
		ListForm.setAddress("aaa");
		
		MarketStoreListForm B = service.select(ListForm);
		assertEquals("社員1", B.getResults().get(0).getStoreName());
		assertEquals("社員2", B.getResults().get(1).getStoreName());
		assertEquals("社員3", B.getResults().get(2).getStoreName());		
	}
	@Test
	//测试 不输入的时候 检索的情况 检索结果是全检索的结果
	public void testselect_002() {

		List<MarketStoreBean> mockResults = new ArrayList<MarketStoreBean>();
     //创建一个空列表，存放mock接收的结果
		mockResults.add(createBean("1"));
		mockResults.add(createBean("2"));
		mockResults.add(createBean("3"));
		//讲bean里的对象添加到mockresult的列表中
		MarketStoreBean paramBean = new MarketStoreBean(); 
		//创建一个名为 parambean的 MarketStoreBean的对象
		Mockito.when(this.mapper.select(paramBean)).thenReturn(mockResults);
     //当检测到mapper里面调用了select方法传值的时候 虚拟机模拟去接受结果
		MarketStoreListForm ListForm =  new MarketStoreListForm();
		MarketStoreListForm B = service.select(ListForm);
		assertEquals("社員1", B.getResults().get(0).getStoreName());
		assertEquals("社員2", B.getResults().get(1).getStoreName());
		assertEquals("社員3", B.getResults().get(2).getStoreName());
	}
	
	/**
	 * 登録正常終了
	 * 测试insert方法的正常执行情况
	 * 检测inset方法是否能正确地插入数据 并插入后返回的属性是否一致
	 * 検索件数：1件
	 */
	@Test
	public void test_insert_01() {
		MarketStoreBean bean = createBean(null);
		
		MarketStoreForm form = new MarketStoreForm();

		form.setStoreId("001");
		form.setStoreName("susu");
		form.setAddress("toukyo");
		form.setPhone("07035403239");
		form.setStartDay("2023-06-18");
		form.setFinishDay("2023-06-18");
		//测试假设数据库中最大的id值为0
		String mockid = "0";
		Mockito.when(this.mapper.selectMaxId()).thenReturn(mockid);
		String maxId = String.valueOf(mockid);
		String maxStoreId = String.valueOf(Integer.valueOf(maxId) + 1);//刷新数据

		service.insert(form);

		assertEquals(bean.getStoreId(), form.getStoreId());
		assertEquals(bean.getStoreName(), form.getStoreName());
		assertEquals(bean.getAddress(), form.getAddress());
		assertEquals(bean.getPhone(), form.getPhone());
		assertEquals(bean.getStartDay(), form.getStartDay());

	}

	private MarketStoreBean createBean(Object object) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	/**
	 * 入力正常終了
	 * 检测update方法是否能正确地更新数据 并更新后返回的属性是否一致
	 * 検索件数：1件
	 */
	@Test
	public void test_update_01() {
		MarketStoreForm form = new MarketStoreForm();

		form.setStoreId("001");
		form.setStoreName("susu");
		form.setAddress("toukyo");
		form.setPhone("07035403239");
		form.setStartDay("2023-06-18");
		form.setFinishDay("2023-06-18");

		List<MarketStoreBean> ChangeResults = new ArrayList<>();

		MarketStoreBean result = new MarketStoreBean();
		result.setStoreId("001");
		result.setStoreName("susu");
		result.setAddress("toukyo");
		result.setPhone("07035403239");
		result.setStartDay("2023-06-18");
		result.setFinishDay("2023-06-18");
		ChangeResults.add(result);

		Mockito.when(mapper.select(Mockito.any(MarketStoreBean.class))).thenReturn(ChangeResults);
//检索
		service.update(form);
//更新
		ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);

		Mockito.verify(mapper).update(captor.capture());
		MarketStoreBean capturedBean = captor.getValue();
		assertEquals("001", capturedBean.getStoreId());
		assertEquals("susu", capturedBean.getStoreName());
		assertEquals("toukyo", capturedBean.getAddress());
		assertEquals("07035403239", capturedBean.getPhone());
		assertEquals("2023-06-18", capturedBean.getStartDay());
		assertEquals("2023-06-18", capturedBean.getFinishDay());
	}

	/**
     * 削除正常終了
     */
    @Test
    public void test_08_delete() {
        // 准备删除的ID
        String storeId = "1";

        // 调用删除方法
        service.delete(storeId);

        // 捕获
        ArgumentCaptor<MarketStoreBean> captor = ArgumentCaptor.forClass(MarketStoreBean.class);

        //最后验证
        Mockito.verify(mapper).delete(captor.capture());
        MarketStoreBean capturedBean = captor.getValue();
        assertEquals("1", capturedBean.getStoreId());
    }
    
    
    /**
     * 正常終了deleteAll
     * 
     */
    @Test
    public void test_09_deleteAll() {
        // 准备删除的多个ID
        String storeIds = "1,2,3";

        // 模拟 split() 方法的返回结果
        String[] delIds = storeIds.split(",");

        // 调用删除方法
        service.deleteAll(storeIds);

        // 捕获
        ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);

        // 验证
        Mockito.verify(mapper).deleteAll(captor.capture());
        String[] capturedIds = captor.getValue();
        assertArrayEquals(delIds, capturedIds);
    }
}
