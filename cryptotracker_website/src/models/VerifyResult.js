export default class VerifyResult {
    constructor(success, status, message, token, time) {
        this._setSuccess(success);
        this._setStatus(status);
        this._setMessage(message);
        this._setTime(time);
    }

    _setSuccess(success) {
        if (success == null) success = false;
        this.success = success;
    }

    _setStatus(status) {
        if (status == null) status = 500;
        this.status = status;
    }

    _setMessage(message) {
        if (message == null) message = "";
        this.message = message;
    }

    _setTime(time) {
        if (time == null) time = new Date();
        this.time = time;
    }

    static fromJSON(json) {
        return new VerifyResult(
            json['success'],
            json['status'],
            json['message'],
            json['time'],
        );
    }
}