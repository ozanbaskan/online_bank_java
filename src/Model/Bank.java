package Model;

import Helper.*;
import org.json.simple.JSONArray;


import java.util.Scanner;

public class Bank {

    private Scanner s = new Scanner(System.in);

    private static boolean started = false;

    private static long safe = 100000000;
    private static String name;


    private Bank(long safe, String name)
    {
        this.safe = Math.max(0, safe);
        this.name = name;
    }

    public static long getSafe() {
        return safe;
    }

    public static void setSafe(long safe) {
        safe = Math.max(0, safe);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static void start()
    {
        if (started) {
            System.out.println("Banka daha önce başlatıldı.");
            return;
        };


        System.out.println("Bankaya Hoşgeldiniz.");
        menu();
    }

    private static int menu()
    {
        boolean endBank = false;

        while (!endBank) {
            System.out.println("Ne yapmak istersiniz?");
            System.out.print("0- Çıkış Yap\n1 - Giriş Yap\n");
            int chosen = Helper.takeInputInteger(0, 1);

            switch (chosen) {
                case 0 -> {
                    endBank = true;
                    started = false;
                }
                case 1 -> {
                    return loginInterface();
                }
            }
        }
        return 0;
    }

    private static int loginInterface()
    {
            System.out.print("T.C. kimlik numaranızı giriniz: ");
            String TCId = Helper.takeInputString();
            System.out.print("Şifrenizi giriniz: ");
            String password = Helper.takeInputString();

            User user = Json.checkLogin(TCId,password);

            if (user != null)
            {
                return transactions(user);
            }
            else
            {
                return menu();
            }

    }

    private static int transactions(User user)
    {
        System.out.println("Merhaba, T.C. Kimlik Numaranız: " + user.getTCId());
        System.out.println("Hesabınızdaki para: " + user.getBalance() + " TL");

            System.out.println("Yapmak istediğiniz işlemi seçiniz.");
            System.out.print("0- Çıkış Yap\n1 - Para Transfer Et\n2 - Para Çek\n3 - Para Yatır\n4 - Kredi Öde\n5 - Kredi Ekstresi Öde\n");

            int chosen = Helper.takeInputInteger(0,5);

            switch(chosen)
            {
                case 0 -> {
                    return menu();
                }
                case 1 -> {
                    System.out.println("Göndermek istediğiniz para miktarını giriniz. ");
                    long money = Helper.takeMoneyInput();
                    if (money > user.getBalance())
                    {
                        System.out.println("Bu kadar paranız yok.");
                        break;
                    }

                    System.out.print("Transferi yapmak istediğiniz T.C. Kimlik numarasını giriniz: ");
                    String TC = Helper.takeInputString();

                    if (TC.equals(user.getTCId()))
                    {
                        System.out.println("Kendinize para yollayamazsınız.");
                        break;
                    }

                    if (sendMoney(user, TC, money)) System.out.println("İşlem başarılı.");
                    else System.out.println("İşlem tamamlanamadı.");
                }
                case 2 -> {
                    System.out.println("Çekmek istediğiniz para miktarını giriniz.");
                    long money = Helper.takeMoneyInput();

                    if (withdrawMoney(user, money)) System.out.println("İşlem başarılı.");
                    else System.out.println("İşlem tamamlanamadı.");

                }
                case 3 -> {
                    System.out.println("Yatırmak istediğiniz para miktarını giriniz.");
                    long money = Helper.takeMoneyInput();

                    if (depositMoney(user, money)) System.out.println("İşlem başarılı.");
                    else System.out.println("İşlem tamamlanamadı.");

                }
                case 4 -> {
                    System.out.println("Ödemek istediğiniz tutarı giriniz.");
                    long money = Helper.takeMoneyInput();
                    if (payCredit(user, money)) System.out.println("İşlem başarılı.");
                    else System.out.println("İşlem tamamlanamadı.");
                }
            }

            Json.upgradeUser(user);
            return transactions(user);
    }

    private static boolean withdrawMoney(User user, long money)
    {
        if (money <= 0) return false;

        if (money <= user.getBalance()) user.setBalance(user.getBalance() - money);
        else {
            System.out.println("Yeterli paranız yok.");
            return false;
        }

        return true;
    }

    private static boolean depositMoney(User user, long money)
    {
        if (money <= 0) return false;

        user.setBalance(user.getBalance() + money);

        return true;
    }

    private static boolean sendMoney(User userSend, String TCReceived, long sent)
    {
        User userReceived = Json.getByTC(TCReceived);

        if (userReceived == null) {
            System.out.println("Göndermek istediğiniz hesap bulunamadı.");
            return false;
        }

        if (sent <= userSend.getBalance()) {
            userSend.setBalance(userSend.getBalance() - sent);
            userReceived.setBalance(userReceived.getBalance() + sent);
            return Json.upgradeUser(userReceived) && Json.upgradeUser(userSend);
        }
        else {
            System.out.println("Yeterli paranız yok.");
            return false;
        }
    }

    private static boolean payCredit(User user, long money)
    {
        if (money <= 0) return false;

        if (money <= user.getBalance()) user.setBalance(user.getBalance() - money);
        else {
            System.out.println("Yeterli paranız yok.");
            return false;
        }

        Bank.setSafe(Bank.getSafe() + money);
        return true;

    }

    private static boolean payCreditReceipt(User user, long money)
    {
        if (money <= 0) return false;

        if (money <= user.getBalance()) user.setBalance(user.getBalance() - money);
        else {
            System.out.println("Yeterli paranız yok.");
            return false;
        }

        Bank.setSafe(Bank.getSafe() + money);
        return true;

    }


}
