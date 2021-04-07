package ru.netology.rest.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Data
@Value
@RequiredArgsConstructor

public class RegistrationDto implements Serializable {
    String login;
    String password;
    String status;
}
