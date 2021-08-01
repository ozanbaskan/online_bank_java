package Helper;

import java.util.Scanner;

public class Helper {


    public static String takeInputString()
    {
        Scanner s = new Scanner(System.in);

        String chosen = "";

        do {
            chosen = s.nextLine();
            if (chosen.length() == 0 || chosen.length() > 16) {
                System.out.println();
                System.out.print("Çok uzun veya kısa bir giriş yaptınız. Tekrar deneyiniz: ");
            }
        } while (chosen.length() == 0 || chosen.length() > 16);

        System.out.println();

        return chosen;
    }

    public static int takeInputInteger(int start, int end)
    {
        Scanner s = new Scanner(System.in);

        int chosen = start - 1;
        boolean outOfBoundry = false;
        boolean mistake = false;

        do {
            try {
                if (outOfBoundry) System.out.println("Değer aralığında bir giriş yapmadınız.");
                if (mistake) System.out.println("Lütfen bir sayı giriniz.");
                System.out.print("Seçiminizi Giriniz: ");
                chosen = s.nextInt();
                if (chosen < start || chosen > end)
                {
                    outOfBoundry = true;
                    mistake = false;
                }
            } catch (Exception ignored) { s.next();mistake = true;outOfBoundry = false; }
        } while (chosen < start || chosen > end);

        return chosen;
    }

    public static int takeMoneyInput()
    {
        Scanner s = new Scanner(System.in);

        int chosen = -1;
        boolean outOfBoundry = false;
        boolean mistake = false;

        do {
            try {
                if (outOfBoundry) System.out.println("Negatif bir sayı girilemez.");
                if (mistake) System.out.println("Lütfen bir sayı giriniz.");
                System.out.print("Seçiminizi Giriniz: ");
                chosen = s.nextInt();
                if (chosen < 0)
                {
                    outOfBoundry = true;
                    mistake = false;
                }
            } catch (Exception ignored) { s.next();mistake = true;outOfBoundry = false; }
        } while (chosen < 0);

        return chosen;
    }

}
