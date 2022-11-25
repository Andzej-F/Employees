package _8_employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst implements Employee {
  private String firstName;
  private String lastName;
  private LocalDate dob;
  private int projectCount = 0;

  private final String peopleRegex = """
          (?<lastName>\\w+),\\s*
          (?<firstName>\\w+),\\s*
          (?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*
          (?<role>\\w+)
          (?:,\\s*\\{(?<details>.*)\\})?\\n
          """;
  private final Pattern peoplePat = Pattern.compile(peopleRegex, Pattern.COMMENTS);
  private final String analystRegex = "\\w+=(?<projectCount>\\w+)";
  private final Pattern analystPat = Pattern.compile(analystRegex, Pattern.COMMENTS);
  private final NumberFormat mfUSA = NumberFormat.getCurrencyInstance(Locale.US);

  DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

  public Analyst(String personText) {
    Matcher peopleMat = peoplePat.matcher(personText);
    if (peopleMat.find()) {
      this.lastName = peopleMat.group("lastName");
      this.firstName = peopleMat.group("firstName");
      this.dob = LocalDate.from(dtFormatter.parse(peopleMat.group("dob")));
      Matcher analystMat = analystPat.matcher(peopleMat.group("details"));
      if (analystMat.find()) {
        this.projectCount = Integer.parseInt(analystMat.group("projectCount"));
      }
    }
  }

  public int getSalary() {
    return 3500 + projectCount * 2;
  }

  @Override
  public String toString() {
    return String.format("%s, %s: %s", lastName, firstName,
            mfUSA.format(getSalary()));
  }
}
