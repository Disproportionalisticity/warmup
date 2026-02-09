package Behavioral;

import java.util.HashMap;
import java.util.Map;

class Database {
    private Map<String, String> users;

    public Database() {
        this.users = new HashMap<>();
        this.users.put("admin_username", "admin_password");
        this.users.put("mod_username", "mod_password");
        this.users.put("user_username", "user_password");
    }

    public boolean isValidUser(String username) {
        return this.users.containsKey(username);
    }

    public boolean isValidPassword(String username, String password) {
        return this.users.get(username).equals(password);
    }
}

abstract class BaseHandler {
    private BaseHandler next;

    public BaseHandler setNextHandler(BaseHandler next) {
        this.next = next;
        return next;
    }

    public abstract boolean handle(String username, String password);

    public boolean handleNext(String username, String password) {
        if (this.next == null) {
            return true;
        }
        return this.next.handle(username, password);
    }
}

class UserExistsHandler extends BaseHandler {
    private Database db;

    public UserExistsHandler(Database db) {
        this.db = db;
    }

    @Override
    public boolean handle(String username, String password) {
        if (this.db.isValidUser(username)) {
            return this.handleNext(username, password);
        } else {
            System.out.println("User not found.");
            return false;
        }
    }
}

class PasswordValidatorHandler extends BaseHandler {
    private Database db;

    public PasswordValidatorHandler(Database db) {
        this.db = db;
    }

    @Override
    public boolean handle(String username, String password) {
        if (this.db.isValidPassword(username, password)) {
            return this.handleNext(username, password);
        } else {
            System.out.println("Invalid password");
            return false;
        }
    }
}

class RoleCheckHandler extends BaseHandler {
    @Override
    public boolean handle(String username, String password) {
        if ("admin_username".equals(username)) {
            System.out.println("Loading admin page");
            return true;
        } else {
            System.out.println("Loading default page");
            return this.handleNext(username, password);
        }
    }
}

class AuthService {
    private BaseHandler handler;

    public AuthService(BaseHandler handler) {
        this.handler = handler;
    }

    public void logIn(String username, String password) {
        if (this.handler.handle(username, password)) {
            System.out.println("User login successful");
        } else {
            System.out.println("User login failed");
        }
    }
}

class ChainOfResponsibilityTest {
    public static void main(String[] args) {
        Database database = new Database();
        BaseHandler handler = new UserExistsHandler(database);

        handler.setNextHandler(new PasswordValidatorHandler(database))
            .setNextHandler(new RoleCheckHandler());

        AuthService authService = new AuthService(handler);
        authService.logIn("admin_username", "admin_password");
        authService.logIn("user_username", "user_password");
        authService.logIn("admin_username", "user_password");
        authService.logIn("s_username", "user_password");
    }
}
