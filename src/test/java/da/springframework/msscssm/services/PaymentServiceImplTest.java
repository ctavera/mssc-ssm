package da.springframework.msscssm.services;

import da.springframework.msscssm.domain.Payment;
import da.springframework.msscssm.domain.PaymentEvent;
import da.springframework.msscssm.domain.PaymentState;
import da.springframework.msscssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        System.out.println("Should be NEW");
        System.out.println(payment.getPaymentState());

        StateMachine<PaymentState, PaymentEvent> stateMachine = paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment = paymentRepository.getReferenceById(savedPayment.getId());

        System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR");
        System.out.println(stateMachine.getState().getId());

        System.out.println(preAuthedPayment);
    }

    @Transactional
    @RepeatedTest(10)
    void testAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        StateMachine<PaymentState, PaymentEvent> preAuthStateMachine = paymentService.preAuth(payment.getId());

        if (preAuthStateMachine.getState().getId() == PaymentState.PRE_AUTH) {
            System.out.println("Payment is Pre Authorized");

            StateMachine<PaymentState, PaymentEvent> authStateMachine = paymentService.authorizePayment(savedPayment.getId());

            System.out.println("Result of Auth: " + authStateMachine.getState().getId());
        } else {
            System.out.println("Payment failed pre-auth...");
        }
    }
}