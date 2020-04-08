package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	/*function search(a) {
	    var b = "http://localhost:8082/search.html?q=" + encodeURIComponent(document.getElementById(a).value);
	    return window.location.href = b;
	}*/
	
	@RequestMapping("/search")
	public String search(@RequestParam("q") String queryString,@RequestParam(defaultValue="1") Integer page,Model model){
		//处理乱码
		if (queryString != null) {
			try {
				queryString = new String(queryString.getBytes("iso-8859-1"), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//查询并返回结果
		SearchResult searchResult = searchService.search(queryString, page);
		//向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		
		//把值返回给search.jsp页面
		return "search";
	}
}
