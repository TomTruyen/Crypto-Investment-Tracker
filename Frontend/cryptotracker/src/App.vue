<style>
@import './style/style.css';
</style>

<template>
  <div>
    <b-nav align="right">
      <b-nav-item to="/">Home</b-nav-item>
      <b-nav-item to="/login">Login</b-nav-item>
      <b-nav-item to="/register">Register</b-nav-item>
    </b-nav>
    <router-view />
  </div>
</template>
