package pro.hexa.backend.main.api.domain.login.service;

public class emailService {

    public static void sendVerificationCodeByEmail(String email, String verificationCode) {

        System.out.println("Sending verification code to: " + email);
        System.out.println("Verification code: " + verificationCode);
    }
}