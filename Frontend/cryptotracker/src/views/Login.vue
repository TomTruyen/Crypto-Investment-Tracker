<template>
  <div class="login">
    <b-form>
      <h3>Login</h3>
      <hr />
      <b-form-group label="Email address:" label-for="email">
        <b-form-input id="email" type="text" placeholder="Email"></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" label-for="password">
        <b-form-input id="password" type="password" placeholder="Password"></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Login</b-button>
    </b-form>
  </div>
</template>
