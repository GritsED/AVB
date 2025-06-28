package org.example.users.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PhoneNormalizer {
    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public String normalize(String rawPhone, String region) {
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(rawPhone, region);
            if (!phoneUtil.isValidNumber(number)) {
                throw new IllegalArgumentException("Некорректный номер телефона");
            }
            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            throw new IllegalArgumentException("Невозможно разобрать номер: " + rawPhone, e);
        }
    }
}
