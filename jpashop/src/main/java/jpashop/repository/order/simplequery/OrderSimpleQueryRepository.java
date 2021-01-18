package jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {

        return em.createQuery("select new jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderSimpleQueryDto.class) //Dto로 조회 결과를 반환하려면 new 오퍼레이션을 꼭 써야한다.
                .getResultList();
    } // 최적화는 맞지만 재사용성 X
    //V3 와 V4를 선택하는건 각각 트레이드오프가 있어 선택해야됨
}
