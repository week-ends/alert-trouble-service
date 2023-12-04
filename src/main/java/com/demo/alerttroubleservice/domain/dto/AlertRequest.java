package com.demo.alerttroubleservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequest {

    private List<String> target;

    private String severity;

    private String message;
}
