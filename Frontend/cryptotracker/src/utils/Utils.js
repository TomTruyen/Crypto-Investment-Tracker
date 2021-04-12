export default class Utils {
    static isValidEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    static isValidPassword(password) {
        if (password.length < 8) {
            return false;
        }
        if (password.search(/[a-z]/) < 0) {
            return false;
        }
        if (password.search(/[A-Z]/) < 0) {
            return false;
        }
        if (password.search(/[0-9]/) < 0) {
            return false;
        }

        return true;
    }

    static numberWithCommas(x, decimals = 2, withFranctionDigits = false) {
        x = Number(x.toFixed(decimals));

        if (x < 1) return x;

        if (withFranctionDigits) {
            x = x.toLocaleString('en-US', { minimumFractionDigits: 2 });
        }

        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
}