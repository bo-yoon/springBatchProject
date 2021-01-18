package jpashop.api;

import java.util.List;




import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import jpashop.domain.Member;
import jpashop.domain.coupon.Coupon;
import jpashop.service.CouponService;
import jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class CouponApiController {
	
	private final CouponService couponService;
	private final MemberService memberService;
	
	 @ApiOperation(value = "쿠폰 조회  ", notes = "모든 쿠폰 조회 ")
	 @GetMapping("/v1/coupons")
	 public List<Coupon> couponsV1() {
	       return couponService.findCoupons();
	 }
	 
	 @ApiOperation(value = "쿠폰 조회  ", notes = "맴버 당 쿠폰  조회 ")
	 @GetMapping("/v1/coupons/member/{id}")
	 public List<Coupon> MembercouponsV1(@PathVariable("id") Long id) {
	     Member mb =  memberService.findOne(id);
		 return couponService.findMemberCoupons(mb);
	 }
	 
	 @ApiOperation(value = "쿠폰 조회  ", notes = " 쿠폰 상세 조회 ")
	 @GetMapping("/v1/coupons/{id}")
	 public Coupon couponsV1(@PathVariable("id") Long id) {
	       return couponService.findOne(id);
	 }
	 
	 
// 생성은 배치 프로그램이 만들어주는 것이기 때문에 필요하지 않음 .
	 

	 
	 
}
