<template>
    <div class="resetpassword">
        <b-form v-on:submit.prevent="resetPassword" v-if="!isConfirm">
            <b-alert variant="success" show v-if="success != ''">{{ success }}</b-alert>
            <b-alert variant="danger" show v-if="success == '' && error != ''">{{ error }}</b-alert>
            <h3>Reset Password</h3>
            <br />
            <b-form-group label="Email address:" class="info-value" label-for="email">
                <b-form-input id="email" class="info-title" type="text" placeholder="Email" v-model="email"></b-form-input>
            </b-form-group>
            <b-button type="submit" class="btn-custom primary width-150px">Reset password</b-button>
        </b-form>

        <b-form v-on:submit.prevent="resetPasswordConfirm" v-if="isConfirm">
            <b-alert variant="success" show v-if="success != ''">{{ success }}</b-alert>
            <b-alert variant="danger" show v-if="success == '' && error != ''">{{ error }}</b-alert>
            <h3>Enter New Password</h3>
            <br />
            <b-form-group label="Password:" class="info-value" label-for="password">
                <b-form-input id="password" class="info-title" type="password" placeholder="Password" v-model="password"></b-form-input>
            </b-form-group>
            <b-form-group label="Repeat password:" class="info-value" label-for="repeatPassword">
                <b-form-input id="repeatPassword" class="info-title" type="password" placeholder="Repeat password" v-model="repeatPassword"></b-form-input>
            </b-form-group>
            <b-button type="submit" class="btn-custom primary width-150px">Change password</b-button>
        </b-form>
    </div>
</template>

<script>
    export default {
        mounted() {
            let email = this.$route.params.email;

            if(email != undefined) {
                this.$data.email = this.decodeBase64(email);
                this.$data.isConfirm = true;
            }
        },
        data() {
            return {
                email: '',
                password: '',
                repeatPassword: '',
                success: '',
                error: '',
                isConfirm: false,
            };
        },
        methods: {
            decodeBase64: function (string) {
                return atob(string);
            },
            resetPassword() {
                this.$store.dispatch('resetPassword', this.email).then((result) => {
                    if(result.success) {
                        this.success = result.message;
                        this.email = '';
                    } else {
                        this.error = result.message;
                        this.success = '';
                        this.email = '';
                    }
                });
            },
            resetPasswordConfirm() {
                if(this.password !== this.repeatPassword) {
                    this.success = '';
                    this.error = "Password don't match";
                } else {
                    this.$store.dispatch('resetPasswordConfirm', {
                        email: this.email,
                        password: this.password,
                    }).then((result) => {
                        if(result.success) {
                            this.success = result.message;
                            this.password = '';
                            this.repeatPassword = '';
                        } else {
                            this.error = result.message;
                            this.success = '';
                            this.password = '';
                            this.repeatPassword = '';
                        }
                    });
                }

                
            }
        }
    }
</script>