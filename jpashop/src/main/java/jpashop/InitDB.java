package jpashop;

import jpashop.domain.*;
import jpashop.domain.coupon.AnniversaryCoupon;
import jpashop.domain.coupon.Coupon;
import jpashop.domain.coupon.CouponStatus;
import jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
        initService.dbInit4();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
        	//유저 
            Member member = createMember("userA", "서울", "1","1111");
            em.persist(member);

            // 책 - 이름, 가격, 수량 
            Book book1 = createBook("Design Pattern", 20000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA BOOK", 40000, 100);
            em.persist(book2);
            
            Book book3 = createBook("Spring batch BOOK", 50000, 100);
            em.persist(book3);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Member createMember(String userA , String city, String s, String z) {
            Member member = new Member();
            member.setName(userA);
            member.setJoinDate(LocalDateTime.now());
            member.setAddress(new Address( city, s,z));
            member.setDormantStatus(DormantStatus.ACTIVE);
            return member;
        }

        private Member createMember2(String userA , String city, String s, String z, int num) {
            Member member = new Member();
            member.setName(userA);
            member.setJoinDate(LocalDateTime.now());
            member.setAddress(new Address( city, s,z));
            member.setDormantStatus(DormantStatus.ACTIVE);
            return member;
        }
        
        private MemberDormant createDormant(Member member) {
        	MemberDormant dormant1 = new MemberDormant();
        	dormant1.setMember(member);
        	
        	return dormant1;
        }
        
        
        private Coupon createAnniCoupon(Member member) {
        	Coupon coupon = new AnniversaryCoupon();
        	coupon.setCreatedDate(LocalDateTime.now());
        	LocalDateTime now = LocalDateTime.now();
        	
        	//실제 모드는 - 1달로 잡기 
        	//test모드 
        	LocalDateTime endDate = now.plusMinutes(1);
        	coupon.setEndDate(endDate);
        	coupon.setMember(member);
        	coupon.setStatus(CouponStatus.UNUSED);
        	return coupon;
        	
        }
        
        private Coupon createConCoupon(Member member) {
        	Coupon coupon = new AnniversaryCoupon();
        	coupon.setCreatedDate(LocalDateTime.now());
        	LocalDateTime now = LocalDateTime.now();
        	
        	//실제 모드는 - 1달로 잡기 
        	//test모드 
        	LocalDateTime endDate = now.plusMinutes(1);
        	coupon.setEndDate(endDate);
        	coupon.setMember(member);
        	coupon.setStatus(CouponStatus.UNUSED);
        	return coupon;
        	
        }
        
        
        public void dbInit2() {
            Member member = createMember("userB", "서울", "1","1111");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
            
            Member member2 = createMember("unusedUser1", "서울", "111","1111");
            em.persist(member2);
            
            Member member3 = createMember("unusedUser2", "분당", "111","2222");
            em.persist(member3);
        }
        
        //휴면 계정 default 데이
        public void dbInit3() {
        	
        	//휴면 계정으로 쓸 계정 추가 
        	Member member = createMember("dormantUserA", "서울", "3", "1111");
        	member.setDormantStatus(DormantStatus.SLEEP);
        	em.persist(member);
        	
        	//휴면 계정 디비 추가 
            MemberDormant dormantMember = createDormant(member);
            
            dormantMember.setStatus(DormantStatus.ACTIVE);
            dormantMember.setDormantDate(LocalDateTime.now());
            em.persist(dormantMember);
            
        }
        
        // 쿠폰 기본 데이터 
        public void dbInit4() {
        	
        	// 쿠폰 가진 회원 추가 
        	Member memberHaveCoupon = createMember("couponUser1","서울","444","3333");
        	em.persist(memberHaveCoupon);
        	
        	// 쿠폰 추가
        	Coupon coupon1 = createAnniCoupon(memberHaveCoupon);
        	em.persist(coupon1);
        	Coupon coupon2 = createAnniCoupon(memberHaveCoupon);
        	em.persist(coupon2);
        	
        	
        	// 쿠폰 가진 회원 추가 
        	Member memberHaveCoupon2 = createMember("couponUser2","분당","444","3333");
        	em.persist(memberHaveCoupon2);
        	
        	// 쿠폰 추가
        	Coupon coupon3 = createAnniCoupon(memberHaveCoupon2);
        	em.persist(coupon3);
        	
        	
        	
        	
        			
        }
    }

}

