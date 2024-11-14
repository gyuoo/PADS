package com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    @JsonProperty("batch_id")
    private String batchId;

    @JsonProperty("csv_path")
    private String csvPath;

    @JsonProperty("image_path")
    private String imagePath;

    public Message() {
    }

    public Message(String batchId, String csvPath) {
        this.batchId = batchId;
        this.csvPath = csvPath;
    }
}
