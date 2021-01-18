package jpashop.api;

import jpashop.domain.Member;
import jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation(value = "회원조회 ", notes = "모든 회원 조회 ")
    @GetMapping("/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @ApiOperation(value = "회원조회 ", notes = "모든 회원 조회 ")
    @GetMapping("/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        public T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    @ApiOperation(value = "회원 등록  ", notes = "신규 회원 생성v1  ")
    @PostMapping("/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
     
        return new CreateMemberResponse(id);
    }

    @ApiOperation(value = "회원 등록   ", notes = "신규 회원 생성v2  ")
    @PostMapping("/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setJoinDate(LocalDateTime.now());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    //별도의 DTO를 사용하면 직접 Entity를 변경하지 않아도 API의 다양한 스펙을 맞출 수 있는 장점이 있다.
    //실무에서는 api를 사용할때 엔티티를 직접 반환하거나 엔티티로 파라미터를 반환 받으면 안된다.

    @ApiOperation(value = "회원 수정 ", notes = "기존 회원 수정   ")
    @PutMapping("/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


}
