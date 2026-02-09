package Behavioral;

interface IPaymentDetails {}

class CardDetails implements IPaymentDetails {
    private String cardNumber;
    private String expiry;
    private String cvv;

    public CardDetails(String cardNumber, String expiry, String cvv) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpiry() {
        return expiry;
    }
}

class PayPalDetails implements IPaymentDetails {
    private String username;
    private String password;

    public PayPalDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

class CryptoDetails implements IPaymentDetails {
    private String walletAddress;

    public CryptoDetails(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getWalletAddress() {
        return walletAddress;
    }    
}

interface IPaymentStrategy<T extends IPaymentDetails> {
    void pay(T details, double amount);
}

class CardPayment implements IPaymentStrategy<CardDetails> {
    private boolean validate(CardDetails details) {
        return (details.getCardNumber().length() == 16 && details.getExpiry().length() == 5 && details.getCvv().length() == 3);
    }

    private boolean payByCreditCard() {
        // some internal logic related to payment by credit card
        return true;
    }

    @Override
    public void pay(CardDetails details, double amount) {
        if (!this.validate(details)) {
            System.out.println("Unable to validate card. Abort mission.");
        } else {
            if (this.payByCreditCard()) {
                System.out.println("Payment by credit card for amount " + amount + " successful");
            } else {
                System.out.println("Payment by credit card failed due to ...");
            }
        }
    }
}

class PayPalPayment implements IPaymentStrategy<PayPalDetails> {
    private boolean validate(PayPalDetails details) {
        return true; // for the sake of the example
    }

    private boolean payByPayPal() {
        // some internal logic related to paying by paypal
        return true;
    }

    @Override
    public void pay(PayPalDetails details, double amount) {
        if (!this.validate(details)) {
            System.out.println("Unable to validate email and/or password. Abort mission.");
        } else {
            if (this.payByPayPal()) {
                System.out.println("Payment by PayPal for amount " + amount + " successful");
            } else {
                System.out.println("Payment by PayPal failed due to ...");
            }
        }        
    }
}

class CryptoPayment implements IPaymentStrategy<CryptoDetails> {
    private boolean validate(CryptoDetails details) {
        return true; // for the sake of the example
    }

    private boolean payByCrypto() {
        // some internal logic related to paying by crypto
        return true;
    }
    
    @Override
    public void pay(CryptoDetails details, double amount) {
        if (!this.validate(details)) {
            System.out.println("Unable to validate crypto wallet. Abort mission.");
        } else {
            if (this.payByCrypto()) {
                System.out.println("Payment by Crypto for amount " + amount + " successful");
            } else {
                System.out.println("Payment by Crypto failed due to ...");
            }
        }      
    }
}

class CheckoutService<T extends IPaymentDetails> {
    private IPaymentStrategy<T> strategy;

    public CheckoutService(IPaymentStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void pay(T details, double amount) {
        strategy.pay(details, amount);
    }
}

public class StrategyDifferentInputTest {
    public static void main(String[] args) {
        CheckoutService<CardDetails> cardPayment = new CheckoutService<>(new CardPayment());
        cardPayment.pay(new CardDetails("1234567891234567", "05/22", "067"), 1256);

        CheckoutService<PayPalDetails> payPalPayment = new CheckoutService<>(new PayPalPayment());
        payPalPayment.pay(new PayPalDetails("username", "password"), 2458);

        CheckoutService<CryptoDetails> cryptoPayment = new CheckoutService<>(new CryptoPayment());
        cryptoPayment.pay(new CryptoDetails("waller"), 3698);
    }
}
