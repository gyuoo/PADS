package com.ssafy.s103.gspadsmock.domain.anomalyproduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AckMessage {
    @JsonProperty("batch_id")
    String batchId;

    public AckMessage(String batchId) {
        this.batchId = batchId;
    }

    public AckMessage() {
    }
}
