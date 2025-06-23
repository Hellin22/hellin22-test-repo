package com.hellin22.rabbitmq_amqp_test_user_service.dto;

public class MemberDto {
    private String memberId;
    private String memberName;

    public MemberDto() {
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public MemberDto(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
