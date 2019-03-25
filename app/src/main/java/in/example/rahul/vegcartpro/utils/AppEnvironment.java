package in.example.rahul.vegcartpro.utils;

public enum AppEnvironment {
    SANDBOX{
        @Override
        public String merchant_Key(){
            return "LLKwG0";
        } //eKzMFoJW

        @Override
        public String merchant_ID() {
            return "393463";
        } // 6461383

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "qauKbEAJ";
        } // KklrjlYgUe

        @Override
        public boolean debug() {
            return true;
        }

    },
    PRODUCTION{
        @Override
        public String merchant_Key(){
            return "dcwQGU";
        } // eKzMFoJW

        @Override
        public String merchant_ID() {
            return "4931752";
        }  // 6461383

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "dzC2i5pp";
        } // KklrjlYgUe

        @Override
        public boolean debug() {
            return false;
        }

    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract String salt();

    public abstract boolean debug();
}
