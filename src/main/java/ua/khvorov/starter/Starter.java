package ua.khvorov.starter;


import ua.khvorov.service.ServerUpdateService;

public class Starter {
    public static void main(String[] args) {
        new ServerUpdateService().initUserUI();
    }
}
