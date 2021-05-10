package jpashop.domain.coupon;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpashop.domain.DormantStatus;
import jpashop.domain.Member;
import jpashop.domain.MemberDormant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //디비 상 상속 구현을 위한 어노테이션 
@DiscriminatorColumn(name = "dtype") // 부모클래스 선언 
@Getter
@Setter
public abstract class Coupon {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "coupon_id")
	    private Long id; // 쿠폰 코드 
	    
	    private LocalDateTime createdDate; // 생성시간
	    
	    private LocalDateTime endDate; // 유효 날짜 
	    
	    @Enumerated(EnumType.STRING)
	    private CouponStatus status; // UNUSED, USED
	    
	    @ManyToOne(fetch = LAZY)
	    @JoinColumn(name = "member_id")
	    private Member member;
	    
	    public String kind;
	    
	    public Coupon() {
	    	
	    }
	    
}
