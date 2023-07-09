package com.ibrahim.jadaan;

public class ParallelService extends Thread {

    public void userInformationWindow() {

        // Create an instance from UseInfo class (form)
        UserInfo userInfo = new UserInfo(); userInfo.setVisible(true);

    }


    public void secretInformationWindow() {

        // Create an instance from SecretInfo class (form)
        SecretInfo secretInfo = new SecretInfo(); secretInfo.setVisible(true);

    }



} // ParallelService class end