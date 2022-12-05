package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    /**
     * 역활과 구현을 충실하게 분리했다.->OK
     * 다형성도 활용하고 인터페이스와 구현 객체를 분리했다 ->OK
     * OCP,DIP같은 객체 지향 설계 원칙을 충실히 준수했다
     * -> 그렇게 보이지만 사실은 아니다.
     * 클래스 의존관계를 분석해보면 추상인터페이스 뿐만아니라 구현 객체도 의존하고 있다. -> DIP 위반
     * 인터페이스 : DiscountPolicy
     * 구현객체: FixDiscountPolicy,RateDiscountPolicy
     * OCP: 변경하지 않고 확장할 수 있어야하는데
     * 지금은 코드를 확장하면 클라이언트 코드에 영향을 준다. 따라서 OCP를 위반한다.
     *
     * 왜 클라이언트 코드를 변경해야할까?
     * 
     */
    
    /** 관심사를 분리하자
     * 1.인터페이스를 의존하게 변경
     * 구체적인 클래스를 전혀모름
     * AppConfig에서 구현객체를 할당해주기만하면됨.
     */
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository,DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
    };
    
    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
