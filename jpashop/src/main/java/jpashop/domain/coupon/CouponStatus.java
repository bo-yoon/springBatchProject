package jpashop.domain.coupon;


// 배치 프로그램 작동시 기간이 지난 쿠폰은 EXPIRATION으로 변경 
public enum CouponStatus {
	UNUSED, USED, EXPIRATION
}
