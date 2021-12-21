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

    static numberWithCommas(x, decimals = 2, withFranctionDigits = false, isCurrency = false) {
        if (Math.abs(x) < 1 && !isCurrency) decimals = 6;

        x = parseFloat(x.toFixed(decimals));

        if (Math.abs(x) < 1) return x;

        if (withFranctionDigits) {
            return x.toLocaleString('en-US', { minimumFractionDigits: 2 });
        }

        const nonDecimals = x.toString().split('.')[0].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        if (x.toString().split('.')[1] == undefined) return nonDecimals;

        return `${nonDecimals}.${x.toString().split('.')[1]}`;
    }

    static hexToRgb(hex) {
        var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
        return result ? {
            r: parseInt(result[1], 16),
            g: parseInt(result[2], 16),
            b: parseInt(result[3], 16)
        } : null;
    }

    static hexToRGBA(hex, alpha = 1) {
        const rgb = this.hexToRgb(hex);

        return `rgba(${rgb.r},${rgb.g},${rgb.b},${alpha})`;
    }

    static toFormatDate(d) {
        return [
            ('0' + d.getDate()).slice(-2),
            ('0' + (d.getMonth() + 1)).slice(-2),
            d.getFullYear()
        ].join('-');
    }
}