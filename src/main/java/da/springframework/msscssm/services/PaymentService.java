package da.springframework.msscssm.services;

import da.springframework.msscssm.domain.Payment;
import da.springframework.msscssm.domain.PaymentEvent;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {

    Payment newPayment (Payment payment);

    StateMachine<PaymentService, PaymentEvent> preAuth(Long paymentId);

    StateMachine<PaymentService, PaymentEvent> authorizePayment(Long paymentId);

    StateMachine<PaymentService, PaymentEvent> declineAuth(Long paymentId);
}
