package jpashop.domain.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("CON")
@Getter
@Setter
public class ConsumptionCoupon extends Coupon{
//5개 소비 할때마다 발행되는 쿠폰 
	
	public ConsumptionCoupon() {
		this.kind = "소비 쿠폰";
	}
}
