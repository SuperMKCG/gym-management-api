package com.management.gym.controller;

import com.management.gym.dto.request.member.MemberCreateRequestDTO;
import com.management.gym.dto.request.member.MemberUpdateRequestDTO;
import com.management.gym.dto.response.MemberResponse;
import com.management.gym.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberCreateRequestDTO memberCreateRequestDTO){
        MemberResponse response = memberService.createMember(memberCreateRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers(){
        List<MemberResponse> membersResponse = memberService.getAllMembers();
        return new ResponseEntity<>(membersResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberByID(@PathVariable Long id){
        MemberResponse response = memberService.getMemberByID(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMemberByID(@Valid
            @RequestBody MemberUpdateRequestDTO memberUpdateRequestDTO, @PathVariable Long id){
        MemberResponse response = memberService.updateMemberByID(memberUpdateRequestDTO,id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberResponse> deleteMemberByID(@PathVariable Long id){
        MemberResponse response = memberService.deleteMemberByID(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/plan{idPlan}")
    public List<MemberResponse> membersByPlan(@RequestParam Long idPlan, @RequestParam(required = false)
                                                              Boolean active){
        return memberService.membersByPlan(idPlan, active);
    }

}
