package jpashop.domain.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ANNI")
@Getter
@Setter
public class AnniversaryCoupon extends Coupon {
// 가입 1주년 마다 제공 되는 쿠폰 
// 근데 일단 1분마다 제공 되는 쿠폰이다.

	
	public AnniversaryCoupon() {
		this.kind = "1주년쿠폰";
	}
	
}
