package jpashop.domain;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
//import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
//@SequenceGenerator(name="seq", initialValue=1001, allocationSize=1)
public class MemberDormant {

	@Id
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "dormant_id")
    private Long id;

	@OneToOne(fetch = LAZY)
	@NotNull
    @JoinColumn(name = "member_id")
    private Member member;
	
	
	private LocalDateTime dormantDate; // 휴면 계정 전환 시

	@Enumerated(EnumType.STRING)
	private DormantStatus status; // active, sleep
   
	
	//==생성 메서드 ==//
	
	public static MemberDormant MemberDormant(Member member) {
		MemberDormant dormant = new MemberDormant();
		dormant.setMember(member);
		dormant.setDormantDate(LocalDateTime.now());
		dormant.setStatus(DormantStatus.ACTIVE);
		
		return dormant;
	}
   

    //==비지니스 로직 ==//
	/*
     * 휴면 계정 다시 일반 계정으로 전환 
     */
	
	public void sleep() {		
		this.setStatus(DormantStatus.SLEEP);
	}
    
}







