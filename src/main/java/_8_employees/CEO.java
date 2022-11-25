package _8_employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO implements Employee {
  private String firstName;
  private String lastName;
  private LocalDate dob;
  private int avgStockPrice = 0;

  private final String peopleRegex = """
          (?<lastName>\\w+),\\s*
          (?<firstName>\\w+),\\s*
          (?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*
          (?<role>\\w+)
          (?:,\\s*\\{(?<details>.*)\\})?\\n
          """;
  private final Pattern peoplePat = Pattern.compile(peopleRegex, Pattern.COMMENTS);
  private final String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
  private final Pattern ceoPat = Pattern.compile(ceoRegex, Pattern.COMMENTS);
  private final NumberFormat mfUSA = NumberFormat.getCurrencyInstance(Locale.US);

  DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

  public CEO(String personText) {
    Matcher peopleMat = peoplePat.matcher(personText);
    if (peopleMat.find()) {
      this.lastName = peopleMat.group("lastName");
      this.firstName = peopleMat.group("firstName");
      this.dob = LocalDate.from(dtFormatter.parse(peopleMat.group("dob")));
      Matcher ceoMat = ceoPat.matcher(peopleMat.group("details"));
      if (ceoMat.find()) {
        this.avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
      }
    }
  }

  public int getSalary() {
    return 5000 * avgStockPrice;
  }

  @Override
  public String toString() {
    return String.format("%s, %s: %s", lastName, firstName,
            mfUSA.format(getSalary()));
  }
}
