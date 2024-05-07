package com.green.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.green.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {
	
	
	// @Query @findById를 실행한다
	// @Query 애노테이션은 Jpa 기능사용하지 않고 @Query 안의 sql을 사용한다
	// findByArticleId() 함수는 Comments테이블 칼럼을 사용해서 만든다
	// Native Query Method - oracle전용 문법으로 작성된 쿼리를 입력라여 조회
	// nativeQuery = true : oracle전용함수
	// nativeQuert = false: JPQL문법의 JPA 함수
	// :articleId (파라미터)로 조회한다 //파라미터는 : 뒤에 작성한다
	// JPQL - jpa용 sql - db 종류과 관계없이
	// QueryDsl - JPA에서 oracle전용 sql문ㅇ르 실행하는 기술
	@Query(value="SELECT * FROM comments WHERE article_id=:articleId",
			nativeQuery=true)
		
	List<Comments> findByArticleId(Long articleId);
	
	// native query xml : 
	// src/main/resources/META-INF/orm.xml // 폴더와 파일 생성해야한다
	// orm.xml에 sql을 저장해서 findByNickname() 함수 호출
	List<Comments> findByNickname(String nickname);

}
