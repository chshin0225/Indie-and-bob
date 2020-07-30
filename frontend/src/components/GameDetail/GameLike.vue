<template>
<div v-if="likedRender">
  <ul>
    <li v-for="likedUser in likedUsers" :key="likedUser.id">
       <p>{{likedUser.nickname}}</p>
    </li>
  </ul>
</div>
</template>

<script>
import axios from "axios";
import SERVER from "../../api/base";
export default {
  props: ['project'],
  mounted() {
    axios
      .get(SERVER.BASE + SERVER.LIKEBYGAME + this.project.gameId)
      .then((res) => {
        console.log(res.data)
        this.likedUsers = res.data.object;
        this.likedRender = true;
      
      })
      .catch((err) => console.error(err));
      
  },
  data() {
      return {
          likedUsers : [],
          likedRender: false,
      }
  }
};
</script>

<style>
</style>