package jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;
    
    //@NotEmpty
    private LocalDateTime joinDate; // 주문시간

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member" ,fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private DormantStatus dormantStatus;
}
