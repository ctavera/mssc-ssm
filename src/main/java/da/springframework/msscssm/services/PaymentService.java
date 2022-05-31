package da.springframework.msscssm.services;

import da.springframework.msscssm.domain.Payment;
import org.springframework.statemachine.StateMachine;

import java.awt.event.PaintEvent;

public interface PaymentService {

    Payment newPayment (Payment payment);

    StateMachine<PaymentService, PaintEvent> preAuth(Long paymentId);

    StateMachine<PaymentService, PaintEvent> authorizePayment(Long paymentId);

    StateMachine<PaymentService, PaintEvent> declineAuth(Long paymentId);
}
