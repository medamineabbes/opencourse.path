package com.opencourse.path.dtos;

import com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions.AcssDebit.Currency;

import lombok.Data;

@Data
public class SubscriptionRequestDto {
    private String stripeToken;
    private String stripeEmail;
    private Currency currency;
}
