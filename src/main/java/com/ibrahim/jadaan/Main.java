package com.ibrahim.jadaan;

import com.ibrahim.jadaan.SQLConnection.SQLServerConnector;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        SQLServerConnector sql = new SQLServerConnector();

        sql.getLastId();

        // First Thread (userInformationThread)
        ParallelService userInformationThread = new ParallelService();
        userInformationThread.userInformationWindow();

        userInformationThread.start();
        userInformationThread.join();

        // Second thread will be created after SUBMIT button clicked

    }

}
