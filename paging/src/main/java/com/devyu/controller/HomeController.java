package com.devyu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devyu.domain.Board;
import com.devyu.domain.Pagination;
import com.devyu.repository.BoardRepository;

@Controller
public class HomeController {
	
	private  BoardRepository boardRepository;

	public HomeController(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	@GetMapping("/")
	public String home(Model model,
								@RequestParam(defaultValue = "1") int page) {
		
		// 총 게시물 수 
		int totalListCnt = boardRepository.findAllCnt();
		
		// 생성인자로  총 게시물 수, 현재 페이지를 전달
		Pagination pagination = new Pagination(totalListCnt, page);
		
		// DB select start index
		int startIndex = pagination.getStartIndex();
		// 페이지 당 보여지는 게시글의 최대 개수
		int pageSize = pagination.getPageSize();
		
		List<Board> boardList = boardRepository.findListPaging(startIndex, pageSize);

		System.err.println(pagination);
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagination", pagination);
		
		return "index";
	}
}
