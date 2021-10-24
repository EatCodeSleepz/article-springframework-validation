package merx.article.springframework.validation.chapter05;

import lombok.Getter;
import lombok.Setter;
import merx.article.springframework.validation.chapter05.validation.Name;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        String specialCharacters = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        String[] arrSpecialCharacters = specialCharacters.split("");
        Model m = new Model();

        for (String specialCharacter : arrSpecialCharacters) {
            m.setValue(" " + specialCharacter + " a ");
            Set<ConstraintViolation<Model>> validate = validator.validate(m);

            if (validate.isEmpty()) {
                System.out.printf("xXx ok [%s]%n",
                        m.getValue());
            } else {
                System.out.printf("xXx not ok [%s] ", m.getValue());
                for (ConstraintViolation<Model> v : validate) {
                    System.out.printf("[%s]", v.getMessage());
                }
                System.out.println();
            }
        }
    }

    @Getter
    @Setter
    public static class Model {
        @Name
        private String value;
    }
}
