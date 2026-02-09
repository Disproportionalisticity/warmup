package Behavioral;

interface IMediator {
    void notify(String event);
}

class LoginPageMediator implements IMediator {
    private TextInputField usernameField;
    private PasswordInputField passwordField;
    private ErrorPopUp errorPopUp; 
    private LoginButton loginButton;

    public void registerComponents(TextInputField usernameField, PasswordInputField passwordField, ErrorPopUp errorPopUp, LoginButton loginButton) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.errorPopUp = errorPopUp;
        this.loginButton = loginButton;
    }

    public void notify(String event) { 
        // I decided to go with Hybrid Mediator approach, but since every time an update happens- both change-able components are affected, they both receive notification
        // If the system becomes more complicated- I will have a switch that decides which components are affected
        switch (event) {
            case "USERNAME_VALID":
            case "USERNAME_NOT_VALID":
                errorPopUp.notifyComponent(event);
                loginButton.notifyComponent(event);
                break;
            case "PASSWORD_VALID":
            case "PASSWORD_NOT_VALID":
                errorPopUp.notifyComponent(event);
                loginButton.notifyComponent(event);
                break;
        }
    }

    public void print() { // this method here is not part of mediator pattern, but I need a method to print all the elements, so ignore it.
        System.out.println("Username input value: \"" + this.usernameField.getTextInput() + "\", valid: " + String.valueOf(this.usernameField.isValid()));
        System.out.println("Password input value: \"" + this.passwordField.getPasswordInput() + "\", valid: " + String.valueOf(this.passwordField.isValid()));
        System.out.println("Error PopUp value: shown: " + String.valueOf(this.errorPopUp.isShown()));
        System.out.println("Username Error: " + this.errorPopUp.getUsernameErrorText());
        System.out.println("Password Error: " + this.errorPopUp.getPasswordErrorText());
        System.out.print("LoginButton name: \"" + this.loginButton.getButtonText() + "\", clickable: " + String.valueOf(this.loginButton.isClickable()));
        System.out.println(", LoginError: " + String.valueOf(this.loginButton.getUsernameError()) + ", PasswordError: " + String.valueOf(this.loginButton.getPasswordError()));
        System.out.println("");
    }
}

abstract class AComponent {
    protected IMediator mediator;

    public AComponent(IMediator mediator) {
        this.mediator = mediator;
    }
}

interface IComponentNotificationable {
    public void notifyComponent(String event);
}

class TextInputField extends AComponent {
    private String textInput;
    private boolean valid;

    public TextInputField(IMediator mediator) {
        super(mediator);
        this.textInput = "";
        this.valid = false;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
        this.ValidateInput();
    }

    private void ValidateInput() {
        // For the sake of the example, we do only allow the input to be alpha-numerical
        if (!this.textInput.matches("[a-zA-Z0-9]+")) {
            this.mediator.notify("USERNAME_NOT_VALID");
            this.valid = false;
        } else {
            this.mediator.notify("USERNAME_VALID");
            this.valid = true;
        }
    }

    public String getTextInput() {
        return textInput;
    }

    public boolean isValid() {
        return valid;
    }
}

class PasswordInputField extends AComponent {
    private String passwordInput;
    private boolean valid;

    public PasswordInputField(IMediator mediator) {
        super(mediator);
        this.passwordInput = "";
        this.valid = false;
    }

    public void setPassword(String passwordInput) {
        this.passwordInput = passwordInput;
        this.ValidateInput();
    }

    private void ValidateInput() {
        // For the sake of the example, we have to have at least one lowercase, at least one uppercase, at least one number and the string to be at least 8 characters long
        if (!(this.passwordInput.matches(".*[a-z].*") && this.passwordInput.matches(".*[A-Z].*") && this.passwordInput.matches(".*[0-9].*") && this.passwordInput.length() >= 8)) {
            this.mediator.notify("PASSWORD_NOT_VALID");
            this.valid = false;
        } else {
            this.mediator.notify("PASSWORD_VALID");
            this.valid = true;
        }
    }

    public String getPasswordInput() {
        return passwordInput;
    }

    public boolean isValid() {
        return valid;
    }
}

class ErrorPopUp extends AComponent implements IComponentNotificationable{
    private String usernameErrorText;
    private String passwordErrorText;
    private boolean shown;

    public ErrorPopUp(IMediator mediator) {
        super(mediator);
        this.usernameErrorText = "";
        this.passwordErrorText = "";
        this.shown = false;
    }

    public void setUsernameErrorText(String usernameErrorText) {
        this.usernameErrorText = usernameErrorText;
    }

    public void setPasswordErrorText(String passwordErrorText) {
        this.passwordErrorText = passwordErrorText;
    }

    private void updateShownStatus() {
        if (this.usernameErrorText.isEmpty() && this.passwordErrorText.isEmpty()){
            this.shown = false; 
        } else {
            this.shown = true;
        }
    }

    public void clearUsernameErrorText() {
        this.usernameErrorText = "";
    }

    public void clearPasswordErrorText() {
        this.passwordErrorText = "";
    }

    public boolean isShown() {
        return shown;
    }

    public String getUsernameErrorText() {
        return usernameErrorText;
    }

    public String getPasswordErrorText() {
        return passwordErrorText;
    }

    @Override
    public void notifyComponent(String event) {
        switch(event) {
            case "USERNAME_NOT_VALID":
                this.setUsernameErrorText("Username is invalid! Please use alphanumeric value.");
                break;
            case "PASSWORD_NOT_VALID":
                this.setPasswordErrorText("Password is invalid! Please use at least one lowercase character, at least one uppercase characted, at least one number and have it at least 8 characters long.");
                break;
            case "USERNAME_VALID":
                this.clearUsernameErrorText();
                break;
            case "PASSWORD_VALID":
                this.clearPasswordErrorText();
                break;
        }
        this.updateShownStatus();
    }
}

class LoginButton extends AComponent implements IComponentNotificationable {
    private String buttonText;
    private boolean clickable;
    private boolean usernameError;
    private boolean passwordError;

    public LoginButton(IMediator mediator, String buttonText) {
        super(mediator);
        this.buttonText = buttonText;
        this.clickable = false;
        this.usernameError = true;
        this.passwordError = true;
    }

    private void setUsernameError() {
        this.usernameError = true;
    }

    private void setPasswordError() {
        this.passwordError = true;
    }

    private void clearUsernameError() {
        this.usernameError = false;
    }

    private void clearPasswordError() {
        this.passwordError = false;
    }

    private void updateClickableStatus() {
        if (!this.usernameError && !this.passwordError) {
            this.clickable = true;
        } else {
            this.clickable = false;
        }
    }

    public String getButtonText() {
        return buttonText;
    }

    public boolean isClickable() {
        return clickable;
    }

    public boolean getUsernameError() {
        return this.usernameError;
    }

    public boolean getPasswordError() {
        return this.passwordError;
    }

    @Override
    public void notifyComponent(String event) {
        switch(event) {
            case "USERNAME_NOT_VALID":
                this.setUsernameError();
                break;
            case "PASSWORD_NOT_VALID":
                this.setPasswordError();
                break;
            case "USERNAME_VALID":
                this.clearUsernameError();
                break;
            case "PASSWORD_VALID":
                this.clearPasswordError();
                break;
        }
        this.updateClickableStatus();
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isUsernameError() {
        return usernameError;
    }

    public void setUsernameError(boolean usernameError) {
        this.usernameError = usernameError;
    }

    public boolean isPasswordError() {
        return passwordError;
    }

    public void setPasswordError(boolean passwordError) {
        this.passwordError = passwordError;
    }
}

class MediatorTest {
    public static void main(String[] args) {
        LoginPageMediator mediator = new LoginPageMediator();

        TextInputField loginField = new TextInputField(mediator);
        PasswordInputField passwordField = new PasswordInputField(mediator);
        ErrorPopUp popup = new ErrorPopUp(mediator);
        LoginButton loginButton = new LoginButton(mediator, "Login");

        mediator.registerComponents(loginField, passwordField, popup, loginButton);

        mediator.print();
        loginField.setTextInput("A A");
        mediator.print();
        loginField.setTextInput("AA");
        mediator.print();
        passwordField.setPassword("aA0");
        mediator.print();
        passwordField.setPassword("aaaaaaA0");
        mediator.print();
    }
}
