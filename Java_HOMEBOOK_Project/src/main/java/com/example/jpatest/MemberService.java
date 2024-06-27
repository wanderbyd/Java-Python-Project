package com.example.jpatest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member addMember(Member member) {
        // 회원 정보 추가
        return memberRepository.save(member);
    }

    public Member getMemberById(String memberId) {
    	return memberRepository.findById(memberId)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found with id " + memberId));
    }
 
    

    public Member updateMember(Member member) {
        // 회원 정보 업데이트
        return memberRepository.save(member);
    }

    public void deleteMember(String memberId) {
        // 회원 삭제
        memberRepository.deleteById(memberId);
    }

    public List<Member> getAllMembers() {
        // 모든 회원 조회
        return memberRepository.findAll();
    }
    
    public Member findByMemberId(String memberId) {
    	Optional<Member> mem = memberRepository.findById(memberId);
    	return mem.get();
    }
    
}