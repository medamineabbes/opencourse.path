package com.opencourse.path.entities;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class SubscriptionType {
    
    @Id
    private String id;
    
    @NotNull(message = "duration is mandatory")
    private int durationMonth;

    @NotNull(message = "price is manddatory")
    private float price;

    @NotBlank(message = "description is mandatory")
    @NotEmpty(message = "description is mandatory")
    private String description;

    //subscriptions
    @DBRef
    private List<Subscription> subscription;
}
