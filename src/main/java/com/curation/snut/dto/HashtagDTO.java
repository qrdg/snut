package com.curation.snut.dto;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HashtagDTO {
    String tag;
    int count;
}
