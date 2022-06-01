package da.springframework.msscssm.actions;

import da.springframework.msscssm.domain.PaymentEvent;
import da.springframework.msscssm.domain.PaymentState;
import da.springframework.msscssm.services.PaymentServiceImpl;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Random;

@Component
public class AuthAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        System.out.println("Auth was called!!!");
        if (new Random().nextInt(10) < 8) {
            System.out.println("Auth Approved!!!");
            stateContext.getStateMachine().sendEvent(Mono.just(MessageBuilder.withPayload(PaymentEvent.AUTH_APPROVED)
                    .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER, stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                    .build())).subscribe();
        } else {
            System.out.println("Auth Declined! No Credit!!!!!!");
            stateContext.getStateMachine().sendEvent(Mono.just(MessageBuilder.withPayload(PaymentEvent.AUTH_DECLINED)
                    .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER, stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                    .build())).subscribe();
        }
    }
}
