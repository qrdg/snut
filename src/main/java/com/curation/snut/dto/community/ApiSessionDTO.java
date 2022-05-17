package com.curation.snut.dto.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ApiSessionDTO {
  private String username;
  private String email;
  private String name;
  private String mobile;
  private String nickname;
  private String birth;
  private String gender;
  private String token;
  private String curl;

}
