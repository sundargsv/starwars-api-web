<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h3>
      Search Films by Planets, Spaceships, Vehicles, People, Films and Species
    </h3>
    <ul>
      <li>
        <input v-model="type" placeholder="Vehicles" />
      </li>
      <li>
        <input v-model="name" placeholder="Sand Crawler" />
      </li>
    </ul>
    <ul>
      <li>
        <button @click="searchFilms">Search</button>
      </li>
    </ul>
    <br />
    <br />
    <div v-if="dataLoaded">
      <p>Type: {{ this.starWars.type }}</p>
      <p>Count: {{ this.starWars.count }}</p>
      <p>Name: {{ this.starWars.name }}</p>
      <p>
        Below are the list of Films for type {{ this.starWars.type }} and name
        {{ this.starWars.name }}
      </p>
      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Director</th>
            <th>Producer</th>
            <th>Episode</th>
            <th>Opening crawl</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="film in this.starWars.films" :key="film.title">
            <td>{{ film.title }}</td>
            <td>{{ film.director }}</td>
            <td>{{ film.producer }}</td>
            <td>{{ film.episodeId }}</td>
            <td>{{ film.openingCrawl }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <p>Please enter a details and wait for data to load.</p>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import { getStarWarsDetails } from "@/api/Api";
import ResponseData from "@/api/models/ResponseData";

@Component
export default class StarWarsFilms extends Vue {
  @Prop() private msg!: string;
  private type = "Vehicles";
  private name = "Sand Crawler";
  private starWars: any;
  private dataLoaded = false;

  async searchFilms() {
    this.dataLoaded = false;
    this.starWars = undefined;
    let message = "Server error to fetch star wars details. Please try again.";
    console.log(
      `Searching data from star wars API for the given ${this.type} and ${this.name}`
    );

    const [error, starWarsResponse, options] = await getStarWarsDetails(
      this.type,
      this.name
    );
    if (error) {
      console.error(`Error in fetch data for the search type and name`);
      if (options?.code === 401) message = "Auth Error. Login again.";
      if (options?.code === 400) message = "Bad Request. Please try again.";
      if (options?.code === 404)
        message = "Requested Data Not Found. Please try clicking search again.";
      if (options?.code === 500) message;
      this.dataLoaded = false;
      alert(message);
    } else {
      this.starWars = starWarsResponse;
      console.log(this.starWars);
      this.dataLoaded = true;
      const customHeader = options?.headers?.customHeader;
    }
  }

  checkToLoad() {
    let result = this.starWars != undefined && this.starWars.count > 0;
    console.log(result);
    return result;
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>

<style src="./StarWarsFilms.scss" lang="scss" />
