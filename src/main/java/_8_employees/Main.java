package _8_employees;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  public static void main(String[] args) {
    String peopleText = """
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=1300,yoe=14,iq=100}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2300,yoe=8,iq=105}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=1630,yoe=3,iq=115}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=5,yoe=10,iq=100}
            Rubble, Barney, 2/2/1905, Manager, {orgSize=300,dr=10}
            Rubble, Barney, 2/2/1905, Manager, {orgSize=100,dr=4}
            Rubble, Barney, 2/2/1905, Manager, {orgSize=200,dr=2}
            Rubble, Barney, 2/2/1905, Manager, {orgSize=500,dr=8}
            Rubble, Barney, 2/2/1905, Manager, {orgSize=175,dr=20}
            Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=3}
            Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=4}
            Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=5}
            Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=6}
            Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=9}
            Rubble, Betty, 4/4/1915, CEO, {avgStockPrice=300}
                    """;

    String peopleRegex = """
            (?<lastName>\\w+),\\s*
            (?<firstName>\\w+),\\s*
            (?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*
            (?<role>\\w+)
            (?:,\\s*\\{(?<details>.*)\\})?\\n
            """;

    Pattern peoplePat = Pattern.compile(peopleRegex, Pattern.COMMENTS);
    Matcher peopleMat = peoplePat.matcher(peopleText);

    int totalSalaries = 0;
    Employee employee = null;
    while (peopleMat.find()) {
      String details = peopleMat.group("details");

      employee = switch (peopleMat.group("role")) {
        case "Programmer" -> new Programmer(peopleMat.group());
        case "Manager" -> new Manager(peopleMat.group());
        case "Analyst" -> new Analyst(peopleMat.group());
        case "CEO" -> new CEO(peopleMat.group());
        default -> new Nobody();
      };
      System.out.println(employee.toString());
      totalSalaries += employee.getSalary();
    }
    NumberFormat mfUSA = NumberFormat.getCurrencyInstance(Locale.US);
    System.out.printf("The total payout should be %s%n", mfUSA.format(totalSalaries));

  }
}

//    \\w == a-zA-Z0-9_
//    \\d == 0-9
//    \\s == space
//    [-.\s]+ == 1 or more
//    [-.\s]* == 0 or more
//    [-.\s]? == 0 or 1
//    d{3,4} == 3 to 4 digits
//    d{3,} == >=3, at least 3 digits, no upper limit
//    (\\d{3}[-.\s]?){2} == match this pattern 2 times
//    (\\d{3}[-.\s]?){1,2} == match this pattern 1 or 2 times
//    The metacharacters that we usually need to escape
//    < ( [ { \ ^ - = $ ! | ] } ) ? * + . >
/* You can create a non-capturing group by following
   the opening parenthesis with ?:, as in (?:\s*) */

/*
    while (peopleMat.find()) {
      System.out.printf("%s %s %s %s %s%n",
              peopleMat.group("firstName"),
              peopleMat.group("lastName"),
              peopleMat.group("dob"),
              peopleMat.group("role"),
              peopleMat.group("details"));
    }

    while (coderMat.find()) {
      System.out.printf("Programmer locpd: %s yoe: %s iq: %s",
              coderMat.group("locpd"),
              coderMat.group("yoe"),
              coderMat.group("iq"));
    }
*/