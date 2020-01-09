package `in`.example.rahul.vegcartpro.utils

enum class AppEnvironment {
    SANDBOX {
        override fun merchant_Key(): String {
            return "LLKwG0"
        } //eKzMFoJW

        override fun merchant_ID(): String {
            return "393463"
        } // 6461383

        override fun furl(): String {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php"
        }

        override fun surl(): String {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php"
        }

        override fun salt(): String {
            return "qauKbEAJ"
        } // KklrjlYgUe

        override fun debug(): Boolean {
            return true
        }
    },
    PRODUCTION {
        override fun merchant_Key(): String {
            return "dcwQGU"
        } // eKzMFoJW

        override fun merchant_ID(): String {
            return "4931752"
        } // 6461383

        override fun furl(): String {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php"
        }

        override fun surl(): String {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php"
        }

        override fun salt(): String {
            return "dzC2i5pp"
        } // KklrjlYgUe

        override fun debug(): Boolean {
            return false
        }
    };

    abstract fun merchant_Key(): String
    abstract fun merchant_ID(): String
    abstract fun furl(): String
    abstract fun surl(): String
    abstract fun salt(): String
    abstract fun debug(): Boolean
}