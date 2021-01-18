package jpashop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpashop.domain.Member;
import jpashop.domain.coupon.Coupon;
import jpashop.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

    @Transactional
    public Long saveCoupon(Coupon coupon) {
        couponRepository.save(coupon);
        return coupon.getId();
    }

    @Transactional
    public void updateCoupon(Long couponId, Member member,  String status){
        Coupon findCoupon = (Coupon) couponRepository.findOne(couponId);
        findCoupon.setCreatedDate(LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();
        //한달 뒤 -> 실제 적용용  
        //LocalDateTime OneMonthAfter = now.plusMonths(1);
        
        //1분 뒤 -> test용
        LocalDateTime oneMinAfter = now.plusMinutes(1);
        
        //실제 용 
        //findCoupon.setEndDate(OneMonthAfter);
        
        //test용 -> 나중에 주석처리 
        findCoupon.setEndDate(oneMinAfter);
        findCoupon.setMember(member);
        //findCoupon.setStatus(status);
        
        
    }

    public List<Coupon> findCoupons() {
        return couponRepository.findAll();
    }

    public Coupon findOne(Long couponId){
        return couponRepository.findOne(couponId);
    }
    
    public List<Coupon> findMemberCoupons(Member member){
    	return couponRepository.findByMember(member);
    }
}
