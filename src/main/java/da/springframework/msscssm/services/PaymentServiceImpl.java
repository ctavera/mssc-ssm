package da.springframework.msscssm.services;

import da.springframework.msscssm.domain.Payment;
import da.springframework.msscssm.domain.PaymentState;
import da.springframework.msscssm.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.awt.event.PaintEvent;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final StateMachineFactory<PaymentService, PaintEvent> stateMachineFactory;

    @Override
    public Payment newPayment(Payment payment) {
        payment.setPaymentState(PaymentState.NEW);
        return paymentRepository.save(payment);
    }

    @Override
    public StateMachine<PaymentService, PaintEvent> preAuth(Long paymentId) {
        return null;
    }

    @Override
    public StateMachine<PaymentService, PaintEvent> authorizePayment(Long paymentId) {
        return null;
    }

    @Override
    public StateMachine<PaymentService, PaintEvent> declineAuth(Long paymentId) {
        return null;
    }
}
