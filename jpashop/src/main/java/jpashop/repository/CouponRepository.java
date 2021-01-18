package jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpashop.domain.Member;
import jpashop.domain.coupon.Coupon;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponRepository {
	private final EntityManager em;

	//쿠폰 저장 
    public void save(Coupon coupon) {
        em.persist(coupon);
    }

    //쿠폰 상세 조회 
    public Coupon findOne(Long id) {
        return em.find(Coupon.class, id);
    }

    // 쿠폰 전체 조회 
    public List<Coupon> findAll() {
       return em.createQuery("select c from Coupon c where c.status = 'UNUSED' ", Coupon.class)
    		   .getResultList();
    }

    // 쿠폰 맴버당 조회 
    public List<Coupon> findByMember(Member member) {
        return em.createQuery("select c from Coupon c where c.member =:member and c.status = 'UNUSED'", Coupon.class)
                .setParameter("member", member)
                .getResultList();
    }
}
