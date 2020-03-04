
package com.gang.exchangesource.type;

public class OperationType {

    public enum OperType {
        ENABLEMAIL("Enable-Mailbox"),
        DISABLEMAIL("Disable-Mailbox"),
        GETMAILBOX("Get-Mailbox"),
        ENABLECOMGROUP("Enable-DistributionGroup"),
        DISABLECOMGROUP("Disable-DistributionGroup"),
        SETCOMGROUP("set-DistributionGroup"),
        GETCOMGROUP("Get-DistributionGroup");
        private String val;

        public String getVal() {
            return val;
        }

        private OperType(String val) {
            this.val = val;
        }


    }

    public enum ExchangeProName {
        IDENTITY("identity"),
        ALIAS("alias"),//邮箱别名
        NAME("name"),
        MAILBOX("mailbox"),
        PRIMARYSMTPADDRESS("primarySMTPAddress"),
        DISPLAYNAME("displayname");//显示名称
        private String val;

        public String getVal() {
            return val;
        }

        private ExchangeProName(String val) {
            this.val = val;
        }

    }

    public enum ObjectClass {
        MAIL("__MAIL__"),
        ADDRESSLIST("__ADDRESSLIST__");

        private String val;

        public String getVal() {
            return val;
        }

        private ObjectClass(String val) {
            this.val = val;
        }



    }
}
