package Model;

import Helper.Json;

import java.time.LocalDate;

public class User {

    public static int idCount = 1;

    private String TCId;
    private String password;
    private int id;
    private long balance;
    private LocalDate birthDate;

    private User(String TCId, String password, LocalDate birthDate, long balance, boolean create) {
        this.TCId = TCId;
        this.password = password;
        this.birthDate = birthDate;
        this.balance = balance;
        if (create) this.id = idCount++;
    }


    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getTCId() {
        return TCId;
    }

    public void setTCId(String TCId) {
        this.TCId = TCId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public static User create(String TCId, String password, long balance, LocalDate birthDate)
    {
        if (password.contains(String.valueOf(birthDate.getYear())))
        {
            System.out.println("Şifre doğum tarihini içeremez.");
            return null;
        }

        User user = new User(TCId, password, birthDate, balance, true);

        if (Json.isExists(user))
        {
            System.out.println("Bu T.C.ye sahip kullanıcı mevcut.");
            return null;
        }

        return user;
    }

    public static User get(String TCId, String password, long balance, LocalDate birthDate)
    {
        if (password.contains(String.valueOf(birthDate.getYear())))
        {
            System.out.println("Şifre doğum tarihini içeremez.");
            return null;
        }
        return new User(TCId, password, birthDate, balance, false);
    }

    public static User login(String TCId, String password)
    {
        return Json.checkLogin(TCId,password);
    }

}
