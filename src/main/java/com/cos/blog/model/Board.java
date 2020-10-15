package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인됨.
	
	@ColumnDefault("0")
	private int count;
	
	// Board 들고올 땐 이걸 무조건 들고와야된다. fetch=FetchType.EAGER
	@ManyToOne(fetch=FetchType.EAGER) // Many = Board , One = User 한명의 유저는 여러 개의 게시글을 쓸 수 있다.
	@JoinColumn(name="userId") // 필드 값은 userId로 만들어지고 연관관계는 Many To One
	private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다. 외래키로 만들어짐!!!
	
	// 필요할 때 땡겨온다. fetch=FetchType.LAZY
	@OneToMany(mappedBy = "board", fetch=FetchType.EAGER) // mappedBy 연관관계의 주인이 아니다(난 FK가 아님) DB에 칼럼을 만들지 말아라. 
	//@JoinColumn(name="replyId") FK가 필요가 없다. FK로 쓰게 될 경우 1원자성에 위반됨 한 칼럼은 하나의 원소만을 가진다. 
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
