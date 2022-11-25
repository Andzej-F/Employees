package _8_employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager implements Employee {
  private String firstName;
  private String lastName;
  private LocalDate dob;
  private int orgSize = 0;
  private int directReports = 0;

  private final String peopleRegex = """
          (?<lastName>\\w+),\\s*
          (?<firstName>\\w+),\\s*
          (?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*
          (?<role>\\w+)
          (?:,\\s*\\{(?<details>.*)\\})?\\n
          """;
  private final Pattern peoplePat = Pattern.compile(peopleRegex, Pattern.COMMENTS);
  private final String managerRegex = "\\w+=(?<orgSize>\\w+),\\w+=(?<dr>\\w+)";
  private final Pattern managerPat = Pattern.compile(managerRegex, Pattern.COMMENTS);
  private final NumberFormat mfUSA = NumberFormat.getCurrencyInstance(Locale.US);

  DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

  public Manager(String personText) {
    Matcher peopleMat = peoplePat.matcher(personText);
    if (peopleMat.find()) {
      this.lastName = peopleMat.group("lastName");
      this.firstName = peopleMat.group("firstName");
      this.dob = LocalDate.from(dtFormatter.parse(peopleMat.group("dob")));
      Matcher managerMat = managerPat.matcher(peopleMat.group("details"));
      if (managerMat.find()) {
        this.orgSize = Integer.parseInt(managerMat.group("orgSize"));
        this.directReports = Integer.parseInt(managerMat.group("dr"));
      }
    }
  }

  public int getSalary() {
    return 2500 + orgSize * directReports;
  }

  @Override
  public String toString() {
    return String.format("%s, %s: %s", lastName, firstName,
            mfUSA.format(getSalary()));
  }
}