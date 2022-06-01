package da.springframework.msscssm.config;

import da.springframework.msscssm.domain.PaymentEvent;
import da.springframework.msscssm.domain.PaymentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    @Test
    void testNewStateMachine() {
        StateMachine<PaymentState, PaymentEvent> stateMachine = stateMachineFactory.getStateMachine(UUID.randomUUID());

        stateMachine.startReactively();

        System.out.println(stateMachine.getState().toString());

        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(PaymentEvent.PRE_AUTHORIZE).build())).doOnComplete(() -> System.out.println("Event handling complete")).subscribe();

        System.out.println(stateMachine.getState().toString());

        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_APPROVED).build())).doOnComplete(() -> System.out.println("Event handling complete")).subscribe();

        System.out.println(stateMachine.getState().toString());

        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_DECLINED).build())).doOnComplete(() -> System.out.println("Event handling complete")).subscribe();

        System.out.println(stateMachine.getState().toString());
    }
}