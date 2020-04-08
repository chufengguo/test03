package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, int page) {
		//查询参数
		Map<String, String> params = new HashMap<>();
		params.put("q", queryString);
		params.put("page", page+"");
		//调用服务
		try {
			//查询后返回json格式的字符串
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL,params);
			//把json格式字符串转换为pojo的封装类型，存储在taotaoresult的data里
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
			if (taotaoResult.getStatus() == 200) {
				SearchResult result = (SearchResult) taotaoResult.getData();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return null;
	}

}
