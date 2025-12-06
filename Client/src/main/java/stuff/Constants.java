package stuff;

public interface Constants
{

    final int maxLoginLength = 20;
    final int minLoginLength = 5;
    final int maxPasswordLength = 20;
    final int minPasswordLength = 5;

    final String loginOrPasswordLengthIsNotOkay = "Login and password should be from " + minLoginLength + " to " + maxLoginLength + " by length";
    final String errorOnServer = "An error on server occurred!";
    final String accountBanned = "Account was blocked!";
    final String accountDeactivated = "Account was deactivated!";
    final String passwordsDoesntMatch = "Passwords do not match!";
    final String fieldsShouldntBeEmpty = "Fields should not be empty!";
    final String shouldBeNumbersInFields = "Certain fields must contain numbers!";
    final String checkEnteredData = "Check that the data entered is correct!";
    final String checkEnteredTIme = "Check whether the time entered is correct!";
    final String someError = "An error occurred!";
    final String wrongPassword = "Wrong password!";
    final String passwordsWontMatch = "Passwords do not match!";
    final String cannotReserveTicket = "Cannot book this ticket!";

    final String dateFormat = "dd.mm.yyyy";

}
