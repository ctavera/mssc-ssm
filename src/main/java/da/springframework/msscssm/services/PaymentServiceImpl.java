package da.springframework.msscssm.services;

import da.springframework.msscssm.domain.Payment;
import da.springframework.msscssm.domain.PaymentEvent;
import da.springframework.msscssm.domain.PaymentState;
import da.springframework.msscssm.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    @Override
    public Payment newPayment(Payment payment) {
        payment.setPaymentState(PaymentState.NEW);
        return paymentRepository.save(payment);
    }

    @Override
    public StateMachine<PaymentService, PaymentEvent> preAuth(Long paymentId) {

        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);

        return null;
    }

    @Override
    public StateMachine<PaymentService, PaymentEvent> authorizePayment(Long paymentId) {

        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);

        return null;
    }

    @Override
    public StateMachine<PaymentService, PaymentEvent> declineAuth(Long paymentId) {

        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);

        return null;
    }

    private StateMachine<PaymentState, PaymentEvent> build (Long paymentId){
        Payment payment = paymentRepository.getOne(paymentId);

        StateMachine<PaymentState, PaymentEvent> stateMachine = stateMachineFactory.getStateMachine(Long.toString(payment.getId()));

        stateMachine.stop();

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.resetStateMachine(new DefaultStateMachineContext<>(payment.getPaymentState(), null, null, null));
                });

        stateMachine.start();

        return stateMachine;
    }
}
