package jpashop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jpashop.domain.DormantStatus;
import jpashop.domain.Member;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor //스프링 부트만 @PersistenceContext 대신 @Autowird를 넣어도 EntityManager를 인젝션 해줄 수 있음. 그래서 가능함
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
    	member.setJoinDate(LocalDateTime.now());
    	member.setDormantStatus(DormantStatus.ACTIVE);
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name =:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
