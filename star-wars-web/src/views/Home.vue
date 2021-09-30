<template>
  <div class="home">
    <img
      alt="Vue logo"
      width="200"
      height="200"
      src="../assets/star-wars-logo.png"
    />
    <!-- <HelloWorld msg="Welcome to Your Vue.js + TypeScript App" /> -->
    <StarWarsFilms msg="Star Wars Web App" /> -->
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import StarWarsFilms from "@/components/StarWarsFilms/StarWarsFilms.vue"; // @ is an alias to /src

@Component({
  components: {
    StarWarsFilms,
  },
})
export default class Home extends Vue {}
</script>
