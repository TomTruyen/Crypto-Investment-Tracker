<template>
  <div class="register">
    <b-form>
      <h3>Register</h3>
      <hr />
      <b-form-group label="Email address:" label-for="email">
        <b-form-input id="email" type="text" placeholder="Email"></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" label-for="password">
        <b-form-input id="password" type="password" placeholder="Password"></b-form-input>
      </b-form-group>
      <b-form-group label="Repeat password:" label-for="repeat-password">
        <b-form-input id="repeat-password" type="password" placeholder="Repeat password"></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Register</b-button>
    </b-form>
  </div>
</template>
