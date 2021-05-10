package jpabook.jpashop.service;

import jpashop.domain.Member;
import jpashop.repository.MemberRepository;
import jpashop.service.MemberService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //spring Transactional은 무조건 rollback을 하기 때문에 flush가 일어나지 않는다.
//  그렇기 때문에 Rollback false나 EntityManger를 생성한다음 코드안에 flush()를 해줘야 insert쿼리가 보여짐
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

   @Test
   public void 회원가입() throws Exception{
       //given
       Member member = new Member();
       member.setName("kim");

       //when
       Long savedId = memberService.join(member);

       em.flush();
       //then
       assertEquals(member, memberRepository.findOne(savedId));
   }

   @Test(expected = IllegalStateException.class)
   public void 중복_회원_예외() throws Exception{
       //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

       //when
       memberService.join(member1);
       memberService.join(member2);

       //then
       fail("예외가 발생해야 한다.");
   }
}