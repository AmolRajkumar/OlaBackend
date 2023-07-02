package com.cabbooking.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

  @NotBlank(message = "Email is Required")
  @Email(message = "Email should be Valid")
  private String email;
  private String fullName;
  private String password;
  private String mobile;


}
