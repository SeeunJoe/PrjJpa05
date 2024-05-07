package com.green.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.green.entity.Article;

// extends CrudRepository<Article, Long>
// JPA의  Crud 기능을 동작시키는 클래스
public interface ArticleRepository 
    extends CrudRepository<Article, Long> {
    	
	// alt+shitf+s : OIverride/Implement method 사용가능한 함수목록확인
	
	@Override
	ArrayList<Article> findAll();

	// ArticleController 83 line 형변환 오류 해결 2번째 방법
	// 상속관계를 이용하여 List를 Iterable 로 UpCasting 하여 형변하지 않음
	// Iterable(I) <- Collection(C) <- List(I) <- ArrayList(C)
	// 아래 내용 추가
	
}





