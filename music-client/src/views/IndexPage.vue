<template>
  <div class="app">
    <JezerHeader/>
    <div class="wrapper">
      <JezerAside/>
      <div class="main-container">
        <div class="content-wrapper">
          <router-view  />
        </div>
        <JezerPlayer/>
      </div>
    </div>
    <div class="overlay-app"></div>
  </div>
</template>

<script>


</script>

<style lang="scss">
@import "@/static/css/main";
</style>