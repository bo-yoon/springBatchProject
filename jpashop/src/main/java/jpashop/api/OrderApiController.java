package jpashop.api;

import jpashop.api.MemberApiController.CreateMemberRequest;
import jpashop.api.MemberApiController.CreateMemberResponse;
import jpashop.domain.Address;
import jpashop.domain.DormantStatus;
import jpashop.domain.Member;
import jpashop.domain.Order;
import jpashop.domain.OrderItem;
import jpashop.domain.OrderStatus;
import jpashop.repository.OrderRepository;
import jpashop.repository.OrderSearch;
import jpashop.repository.order.query.OrderFlatDto;
import jpashop.repository.order.query.OrderItemQueryDto;
import jpashop.repository.order.query.OrderQueryDto;
import jpashop.repository.order.query.OrderQueryRepository;
import jpashop.service.MemberService;
import jpashop.service.OrderService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final MemberService memberService;
    private final OrderService orderService;
    
    @ApiOperation(value = "주문 조회 ", notes = "주문 조회  ")
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            //order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream()
                    .forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @ApiOperation(value ="주문 조회 ", notes = "전체 주문 조회 ")
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return collect;
    }

    @ApiOperation(value ="주문 조회 ", notes = "전체 주문 조회 v3 ")
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return collect;
    }

    @ApiOperation(value ="주문 조회 ", notes = "전체 주문 조회 + offset, limit")
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        
    	List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List<OrderDto> collect = orders.stream()
                .map(OrderDto::new)
                .collect(toList());

        return collect;

    }

    @ApiOperation(value ="주문 조회 ", notes = "전체 주문 조회 v4 : 쿼리  ")
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @ApiOperation(value ="주문 조회 ", notes = "전체 주문 조회 v5 : 최적화  ")
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @ApiOperation(value ="주문 조회 ", notes = "전체 주문 조회 v6 ")
    @GetMapping("/api/v6/orders") public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())))
                .entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    
    @ApiOperation(value="주문 저장 ", notes ="주문 저장 ")
    @PostMapping("/api/v1/orders") 
    public CreateOrderResponse saveOrderV2(@RequestBody @Valid CreateOrderRequest request) {

        
    	Long id = orderService.order(request.memberId, request.itemId, request.count);
    	//Member member = memberService.findOne(request.memberId);
    	
        return new CreateOrderResponse(id);
    }
    
    @Data
    static class CreateOrderRequest {
        private Long itemId;
        private Long memberId;
        private int count;
    
    }

    @Data
    static class CreateOrderResponse {
        private Long id;

        public CreateOrderResponse(Long id) {
            this.id = id;
        }
    }
    
//Dto를 반환할때 안에 있는 필드 List도 Dto로 바꿔서 내보내야한다. 하지만 값타입은 상관없음.
@Getter
static class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItem;

    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        //address = order.getDelivery().getAddress();
        orderItem = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(toList());
    }
}

@Getter
static class OrderItemDto {

    private String itemName; //상품 명
    private int orderPrice; // 주문 가격
    private int count; //주문 수량

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}


}



