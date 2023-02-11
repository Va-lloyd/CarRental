package com.valloyd.carrental.car;

import java.math.BigDecimal;

public record CarDataRequest(
        String registrationNumber,
        BigDecimal dailyRentalPrice
) {
}
