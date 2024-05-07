package com.green.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.dto.ArticleDto;
import com.green.dto.ArticleForm;
import com.green.dto.CommentDto;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;
import com.green.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
	
	@Autowired
	private  ArticleRepository  articleRepository;
	
	@Autowired
	private CommentService commentService;
	
	// data 입력
	@GetMapping("/articles/WriteForm")
	public  String  writeForm() {
		return "articles/write"; 
		// /stc/main/resources/templates/   articles/write  .mustache
	}
	
	// data 저장
	// 405 에러 : method="post" -> @GetMapping 
	//  에러 : @GetMapping("/articles/Write") , DTO == VO
	// FormData - title : aaa content: 가가가
	@PostMapping("/articles/Write")
	public  String  write( ArticleDto articleDto ) {
		// 넘어온 데이터 확인
		System.out.println( "결과:" + articleDto.toString() );  // 책 : ArticleForm
		// db 에 저장 h2 article 테이블에 저장
		// Entity : db 의 테이블이다
		// 1.  Dto -> Entity 
		Article  article = articleDto.toEntity();
		// 2.  리포지터리(인터페이스)를 사용하여 엔티티을  저장
		Article  saved   = articleRepository.save( article );    // INSERT 
		System.out.println("saved:" + saved);
		
		return "redirect:/articles/List";
	}
	
	// 1번 데이터 조회 : PathVariable -> GET
	// java.lang.IllegalArgumentException: Name for argument of type
	// 1번 방법. @PathVariable(value="id") 추가
	// 2번 방법. sts 설정 추가 
	// 프로젝트 -> properties  -> Java Compiler -> 
	// -> ✅ project specific settings 체크
	// -> ✅ store infomation ... 체크
	
	// No default constructor for entity 'com.green.entity.Article' 
	// Article 에 @NoArgsConstructor  추가
	// localhost:9090/articles/1
	@GetMapping("/articles/{id}")
	public  String  view(
			@PathVariable(value="id")  Long id,  
			Model   model) {
		// Article  articleEntity  = articleRepository.findById(id); 
		// 1번방법  // Type mismatch error
		// Optional<Article>  articleEntity  = articleRepository.findById(id);
		// 값이 있으면 Article 을 리턴, 값이 없으면 null 리턴
		
		// 2번 방법
		// id에 해당하는 게시글 조회
		Article  articleEntity  = articleRepository.findById(id).orElse(null);
		System.out.println( "1번 조회 결과:" + articleEntity );
		model.addAttribute("article", articleEntity ); // 조회한 게시글 결과 -> model
		
		// 댓글 목록 조회 4번 게시글의 댓글 목록 -> model에 추가
		List<CommentDto> commentDtos = commentService.comments(id);
		model.addAttribute("commentDtos",commentDtos);
		
		
		return "articles/view";  // articles/view.mustache
	}
	
	@GetMapping("/articles/List")
	public  String   list(Model model) {
		// List<Article> articleEntityList =  articleRepository.findAll();
		// 1. 오류처리 1번 방법
		// List<Article> articleEntityList 
		//     =  (List<Article>) articleRepository.findAll();				
		// 2. ArticleRepository interface 에 함수를 등록
		
		List<Article> articleEntityList =  articleRepository.findAll();
		System.out.println( "전체목록:" +  articleEntityList );
		model.addAttribute("articleList",  articleEntityList);		
		
		return  "articles/list"; // /templates/articles/list.mustache
		
	}

	// 데이터 수정페이지로 이동
	@GetMapping("/articles/{id}/EditForm")
	public  String  editForm(
		@PathVariable(value="id") Long id,
		Model                          model ) {
		// 수정할 데이터를 조회한다
		Article  articleEntity =  articleRepository.findById(id).orElse(null);
		
		// 조회한 데이터를 model 에 저장
		model.addAttribute("article", articleEntity);
		
		// 수정페이지롤 이동	
		return  "articles/edit";
	}
	
	// 데이터 수정
	@PostMapping("/articles/Edit")
	public  String  edit( ArticleForm  articleForm ) {
		log.info( "수정용 데이터:" +  articleForm.toString() );
		// db 수정
		// 1. DTO -> Entity 로 변환
		/*
		Long    id      =  articleForm.getId();
		String  title   =  articleForm.getTitle();
		String  content =  articleForm.getContent();
		Article   articleEntity   =  
			new Article( id, title, content   );
		*/
		Article   articleEntity   = articleForm.toEntity();
		// 2. entity 를 db 에 수정한다
		// 2-1. 수정할 데이터를 찾아서 (db 의 data를 가져온다)
		Long     id      = articleForm.getId();
		Article  target  = articleRepository.findById(id).orElse(null);
		// 2-2. 필요한 데이터를 변경한다
		if(target != null) { // 자료가 있으면 저장한다(수정)
			articleRepository.save( articleEntity );
		}
		return  "redirect:/articles/List";
	}
	
	// 데이터 삭제
	@GetMapping("/articles/{id}/Delete")
	public  String   delete(
			@PathVariable  Long  id, 
			RedirectAttributes  rttr ) {
		// 1. 삭제 할 대상을 검색한다
		Article  target = articleRepository.findById(id).orElse(null);
		// 2. 대상 Entity를 삭제한다
		if( target != null ) {
			articleRepository.delete( target );
			// RedirectAttributes : 리다이렉트 페이지에서 사용할 데이터를 넘김
			// 한번쓰면 사라지는 휘발성 데이터
			// 삭제후 임시메세지를 list.mustache가 출력한다
			rttr.addFlashAttribute("msg", id + "번 자료가 삭제되었습니다");
			// header.mustache 에 출력
		}
		return "redirect:/articles/List";
	}
}

















