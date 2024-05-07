package com.green.entity;

import com.green.dto.CommentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //클래스를 테이블로 생성한다 - @entity 하나에 테이블 하나로 생성
@Data //@Getter, @Setter, @ToString, hashCode, equals
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자 생성자
@SequenceGenerator(name = "COMMNETS_SEQ_GENERATOR", sequenceName = "COMMENTS_SEQ", initialValue = 1, allocationSize = 1)
//이름                        시퀀스 이름                초기값            증가치
public class Comments {
   @Id //기본키(jakarta.perisit)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMNETS_SEQ_GENERATOR")
   private Long id;
   
   @ManyToOne //다대일 설정 (Comments <-> Article)
   @JoinColumn(name = "article_id") // 외래키(부모키 Article id column)
   private Article article; //연결될 entity 객체의 이름 - 컬럼이름이 아니다
   
   //@Column(name = "nick_name", nullable = false, length = 225)
   //
   @Column
   private String nickname;
   
   @Column
   private String body;

public static Comments createComment(CommentDto dto, Article article) {
	
	if(dto.getId() != null) {  // 입력된 댓글에 id가 이미 존재하면 예외처리
		throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야합니다.");
	}
	// dto.getArticleId() :  입력받은 게시글의 id
	// article.getId()    :  조회한 게시글의 id
	if(dto.getArticleId() != article.getId()) { 
		throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다");
	}
	
	return new Comments(
						dto.getId(),		 // 입력받은 댓글 아이디	
						article,		     // 조회한 부모 게시글 정보
						dto.getNickname(),	 // 입력받은 댓글 닉네임
						dto.getBody());		 // 입력받은 댓글 내용
}
public void patch(CommentDto dto) {
	if(this.id != dto.getId()) {
		throw new IllegalArgumentException("댓글 수정 실패! 잘못된 아이디가 입력되었습니다.");
	}
	if(dto.getNickname() != null) { // 입력받은 수정할 닉네임이 존재하면
		this.nickname = dto.getNickname();}
	if(dto.getBody() != null) {		// 입력받은 수정할 댓글 내용이 존재하면
		this.body = dto.getBody();}
}
}